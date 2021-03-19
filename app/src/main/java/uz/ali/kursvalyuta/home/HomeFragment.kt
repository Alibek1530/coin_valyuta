package uz.ali.kursvalyuta.home

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.net.ConnectivityManager
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
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import uz.ali.kursvalyuta.Interface.Data
import uz.ali.kursvalyuta.ModelServer.DataModel
import uz.ali.kursvalyuta.ModelServer.DataModelItem
import uz.ali.kursvalyuta.R
import uz.ali.kursvalyuta.adapters.AdaprerHome
import uz.ali.kursvalyuta.network.ApiService
import uz.ali.kursvalyuta.network.NetworkConnection
import uz.ali.kursvalyuta.room.AppDatabase
import uz.ali.kursvalyuta.room.UserDao


class HomeFragment : Fragment(R.layout.fragment_home), Data, SearchView.OnQueryTextListener {
    lateinit var navController: NavController
    lateinit var recyclerView: RecyclerView

    lateinit var roomDao: UserDao
    lateinit var list: List<DataModelItem>
    lateinit var listret: List<DataModelItem>

    lateinit var toolbarHome: Toolbar

    lateinit var til: String
    lateinit var prefs: SharedPreferences

    lateinit var api: ApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)
        roomDao = AppDatabase.getInstance()!!

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        prefs = view.context.getSharedPreferences("app", Context.MODE_PRIVATE)

        til = prefs.getString("til", "uz").toString()
        toolbarHome = view.findViewById(R.id.toolbar_home)
        toolbarHome.inflateMenu(R.menu.menu_top)
        toolbarHome.title = getString(R.string.app_name)
        (activity as AppCompatActivity?)!!.setSupportActionBar(toolbarHome)

        navController = Navigation.findNavController(context as Activity, R.id.frag_oyna)
        listret = getDaoList()
        if (!listret.size.equals(0)) {
            recyclerView = view.findViewById(R.id.RecyclerView)
            recyclerView.layoutManager = LinearLayoutManager(view.context)
            recyclerView.adapter = AdaprerHome(listret, this)
        } else {
            apiGet()
        }
    }

    fun apiGet() {
        api = NetworkConnection.getInstance().getApiClient()
        api.getHomeListAllFinishDay().enqueue(object : Callback<DataModel> {
            override fun onResponse(call: Call<DataModel>, response: Response<DataModel>) {
                if (response.isSuccessful && response.code() == 200) {
                    roomDao?.insert(response.body())
                }
            }

            override fun onFailure(call: Call<DataModel>, t: Throwable) {
                Toast.makeText(view?.context, getString(R.string.netOff), Toast.LENGTH_SHORT).show()

            }
        })
    }


    fun NetworkOn(): Boolean {
        val cm = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        val netInfo = cm!!.activeNetworkInfo
        return netInfo != null && netInfo.isConnectedOrConnecting
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
        //navController = findNavController()
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