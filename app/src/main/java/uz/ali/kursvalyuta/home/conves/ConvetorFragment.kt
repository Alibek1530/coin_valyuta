package uz.ali.kursvalyuta.home.conves

import android.content.Context
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import uz.ali.kursvalyuta.ModelServer.DataModel2
import uz.ali.kursvalyuta.ModelServer.DataModelDate
import uz.ali.kursvalyuta.ModelServer.DataModelItem2
import uz.ali.kursvalyuta.R
import uz.ali.kursvalyuta.adapters.AdaprerHomeCon
import uz.ali.kursvalyuta.network.ApiService
import uz.ali.kursvalyuta.network.NetworkConnection
import uz.ali.kursvalyuta.room.AppDatabase
import uz.ali.kursvalyuta.room.UserDao
import java.text.SimpleDateFormat


class ConvetorFragment : Fragment(R.layout.fragment_convetor) {

    lateinit var recyclerView: RecyclerView
    lateinit var roomDao: UserDao
    lateinit var api: ApiService

    lateinit var list: ArrayList<DataModelItem2>
    lateinit var listss: ArrayList<DataModelItem2>


    lateinit var rub: String
    lateinit var rubQiymat: String
    lateinit var rubNomi: String
    var tekshir: Boolean = false

    lateinit var prefs: SharedPreferences

    lateinit var EditTxt: EditText
    lateinit var TxtRub: TextView
    lateinit var TxtnaSum: TextView
    lateinit var TxtSum: TextView
    lateinit var ImageReplase: ImageView

    lateinit var toolbarCon: Toolbar

    lateinit var til: String
    lateinit var sum: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        list = arrayListOf()
        listss = arrayListOf()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        toolbarCon = view.findViewById(R.id.toolbar_con)
        api = NetworkConnection.getInstance().getApiClient()
        roomDao = AppDatabase.getInstance()!!

        prefs = view.context.getSharedPreferences("app", Context.MODE_PRIVATE)
        til = prefs.getString("til", "uz").toString()

        prefs.edit().putBoolean("back", true).apply()

        recyclerView = view.findViewById(R.id.RecyclerViewCon)
        EditTxt = view.findViewById(R.id.edit_txt)
        TxtRub = view.findViewById(R.id.text_rub)
        TxtSum = view.findViewById(R.id.som_txt)
        TxtnaSum = view.findViewById(R.id.text)
        ImageReplase = view.findViewById(R.id.image)


        var dat = arguments?.getString("date")
        rub = arguments?.getString("rub").toString()
        rubQiymat = arguments?.getString("qiymat").toString()
        rubNomi = arguments?.getString("rubnomi").toString()
        var date = dat?.let { getDateFarmat(it) }

        toolbarCon.title = rubNomi

        EditTxt.setText("1")
        TxtSum.text = rubQiymat
        TxtRub.text = rubNomi
        if (tekshir) {
            TxtRub.text = getString(R.string.uzbeksom)
            TxtnaSum.text = rubNomi
        } else {
            TxtnaSum.text = getString(R.string.uzbeksom)
            TxtRub.text = rubNomi
        }
        ImageReplase.setOnClickListener {
            if (!tekshir) {
                TxtRub.text = getString(R.string.uzbeksom)
                TxtnaSum.text = rubNomi
            } else {
                TxtnaSum.text = getString(R.string.uzbeksom)
                TxtRub.text = rubNomi
            }
            EditTxt.setText("0")
            TxtSum.text = "0"
            tekshir = !tekshir
        }

        EditTxt.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (tekshir.equals(false)) {
                    rubnasum(start + count, s)
                } else {
                    sumnarub(start + count, s)
                }
            }
        })

        if (NetworkOn()) {
            date?.let { aa(it) }
        } else {
            Toast.makeText(view.context, getString(R.string.netOff), Toast.LENGTH_SHORT).show()
            list = roomDao.getDavlatAllDay(rub) as ArrayList<DataModelItem2>

            var date = SimpleDateFormat("dd.MM.yyyy")
            for (i in 0..list.size-1) {
                for (j in 0..list.size-1) {
                    if (date.parse(list[i].Date).time > date.parse(list[j].Date).time) {
                        var temp = list[j]
                        list[j] = list[i]
                        list[i] = temp
                    }
                }
            }
        }
        setRecycler()
    }

    private fun rubnasum(start: Int, s: CharSequence) {
        if (!start.equals(0)) {

            var temp = s.toString()
            var temppp = temp.toFloat()

            var rub = (temppp * ((rubQiymat.toFloat()) * 100.toLong())) / 100


            var valueWithoutEpsilon = rub.toString().toBigDecimal()

            TxtSum.text = valueWithoutEpsilon.toPlainString()


        } else {
            TxtSum.text = "0"
        }
    }

    private fun sumnarub(start: Int, s: CharSequence) {
        if (!start.equals(0)) {
            var temp = s.toString()
            var temppp = temp.toDouble()
            var rub = temppp /(rubQiymat.toDouble())
            var natija=(rub*100).toLong()
            var n=(natija.toDouble()/100)

            var valueWithoutEpsilon = n.toString().toBigDecimal()
           TxtSum.text =valueWithoutEpsilon.toPlainString()
        } else {
            TxtSum.text = "0"
        }
    }

    fun ListShowAdd() {
        if (!list.get(list.size - 1).equals(null)) {
            recyclerView.adapter?.notifyItemInserted(list.size - 1)
        }
    }

    fun NetworkOn(): Boolean {

        val cm = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        val netInfo = cm!!.activeNetworkInfo
        return netInfo != null && netInfo.isConnectedOrConnecting
    }

    fun setRecycler() {
        recyclerView.apply {
            layoutManager = LinearLayoutManager(view?.context)
            adapter = AdaprerHomeCon(list, this@ConvetorFragment)
        }
    }


    fun aa(date: String) {
        api.getHomeRubOneDay(rub, date).enqueue(object : Callback<DataModel2> {
            override fun onFailure(call: Call<DataModel2>, t: Throwable) {
            }

            override fun onResponse(call: Call<DataModel2>, response: Response<DataModel2>) {
                if (response.isSuccessful && response.code() == 200) {
                    var temp: DataModel2 = response.body()!!
                    var ttemp: DataModelItem2 = temp[0]

                    var b = roomDao.getBool(ttemp.Code, ttemp.Date)
                    if (b.equals(false) && ttemp.Date.substring(6, 10).toInt() != 2009) {
                        roomDao.insert2(ttemp)
                        roomDao.insertDate(DataModelDate(null, ttemp.Date.substring(6, 10)))
                    }
                    if (ttemp.Date.substring(6, 10).toInt() != 2009) {
                        list.add(ttemp)
                        ListShowAdd()
                    }
                    getDate(temp.get(0).Date)
                }
            }
        })
    }

    fun getDate(date: String) {
        //  2020-12-36 return

        //05.01.2021 date
        var day = date.substring(0..1).toInt()
        var moon = date.substring(3..4)

        var year = date.substring(6..9)

        if ((day - 1) >= 0 && year.toInt() != 2009) {
            var dayRet = year + "-" + moon + "-" + (day - 1)
            aa(dayRet)
        }
    }

    fun getDateFarmat(date: String): String {
        //  2020-12-36 return

        //05.01.2021 date
        var day = date.substring(0..1).toInt()
        var moon = date.substring(3..4)
        var year = date.substring(6..9)

        var dayRet = year + "-" + moon + "-" + day
        return dayRet
    }

    override fun onStop() {
        super.onStop()
        if (NetworkOn()) {
            prefs.edit().putBoolean(rub, false).apply()
        }
        //   Log.d("key", "key false")
    }
}