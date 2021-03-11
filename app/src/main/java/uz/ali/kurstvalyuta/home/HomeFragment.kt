package uz.ali.kurstvalyuta.home

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import uz.ali.kurstvalyuta.Interface.Data
import uz.ali.kurstvalyuta.ModelServer.DataModelItem
import uz.ali.kurstvalyuta.R
import uz.ali.kurstvalyuta.adapters.AdaprerHome
import uz.ali.kurstvalyuta.room.AppDatabase
import uz.ali.kurstvalyuta.room.UserDao


class HomeFragment : Fragment(R.layout.fragment_home), Data, SearchView.OnQueryTextListener {
    lateinit var navController: NavController
    lateinit var recyclerView: RecyclerView

    lateinit var roomDao: UserDao
    lateinit var list: List<DataModelItem>

    lateinit var toolbarHome: Toolbar

    lateinit var til: String
    lateinit var prefs: SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)
        roomDao = AppDatabase.getInstance()!!

//        navController = Navigation.findNavController(context as Activity, R.id.frag_oyna)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        prefs = view.context.getSharedPreferences("app", Context.MODE_PRIVATE)
        til = prefs.getString("til", "uz").toString()
        toolbarHome = view.findViewById(R.id.toolbar_home)
        toolbarHome.inflateMenu(R.menu.menu_top)
        toolbarHome.title=getString(R.string.app_name)
        (activity as AppCompatActivity?)!!.setSupportActionBar(toolbarHome)
        //       val prefs = PreferenceManager.getDefaultSharedPreferences(view?.context)

//        if (prefs.getString("key","tun").equals("tun")){
//            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
//        }else{
//            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
//        }
        navController = Navigation.findNavController(context as Activity, R.id.frag_oyna)
//        var menu=toolbar.menu
//      var search=  menu.getItem(R.id.app_bar_search)
//        search.setOnMenuItemClickListener {
//
//        }
//        toolbar.setOnMenuItemClickListener {
//
//        }
        //     Search = view.findViewById(R.id.searchView)
//        Search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
//            override fun onQueryTextSubmit(query: String?): Boolean {
//                Log.d("i", "que: " + query)
//                return false
//            }
//
//            override fun onQueryTextChange(newText: String?): Boolean {
//                var searchList = getSearchList(newText.toString())
//                recyclerView.adapter = AdaprerHome(searchList, this@HomeFragment)
//                recyclerView.adapter?.notifyDataSetChanged()
//                Log.d("i", "new: " + newText)
//                return false
//            }
//        })


        recyclerView = view.findViewById(R.id.RecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(view.context)
        recyclerView.adapter = AdaprerHome(getDaoList(), this)

    }

    fun getDaoList(): List<DataModelItem> {
        list = roomDao.getDayAllDavlat()
        return list
    }

    fun getSearchList(search: String): List<DataModelItem> {
        search.trim()
        list = arrayListOf()
        list = roomDao.getSearchDayAllDavlat("%" + search + "%")
        return list
    }


    override fun getData(pos: Int) {
        var args = Bundle()
        args.putString("rub", list.get(pos).Ccy)
        args.putString("date", list.get(pos).Date)
        args.putString("qiymat", list.get(pos).Rate)
        var temp = getRub(pos)
        args.putString("rubnomi", temp)
        arguments = args
        Toast.makeText(view?.context, "" + pos, Toast.LENGTH_SHORT).show()
        navController.navigate(R.id.convetorFragment, arguments)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_top, menu)
        val searchItem = menu.findItem(R.id.app_bar_search)
       val searchView = searchItem.actionView as SearchView
        searchView.queryHint = getString(R.string.izlash)
        searchView.setOnQueryTextListener(this)
        // searchView.isIconified = false
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        var searchList = getSearchList(newText.toString())
        recyclerView.adapter = AdaprerHome(searchList, this@HomeFragment)
        recyclerView.adapter?.notifyDataSetChanged()
        return true
    }


    private fun getRub(pos: Int): String {
        var temp = ""
        if (til.equals("uz")) {
            temp = list.get(pos).CcyNm_UZ
        } else if (til.equals("kz")) {
            temp = list.get(pos).CcyNm_UZC
        } else if (til.equals("ru")) {
            temp = list.get(pos).CcyNm_RU
        } else if (til.equals("en")) {
            temp = list.get(pos).CcyNm_EN
        }
        return temp
    }

}