package uz.ali.kursvalyuta

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.widget.Toolbar
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import uz.ali.kursvalyuta.ModelServer.DataModel
import uz.ali.kursvalyuta.ModelServer.DataModelItem
import uz.ali.kursvalyuta.adapters.AdaprerCalendar
import uz.ali.kursvalyuta.network.ApiService
import uz.ali.kursvalyuta.network.NetworkConnection
import uz.ali.kursvalyuta.room.AppDatabase
import uz.ali.kursvalyuta.room.UserDao
import java.text.SimpleDateFormat
import java.util.*


class KalendarFragment : Fragment(R.layout.fragment_kalendar) {
    lateinit var api: ApiService
    lateinit var recyclerView: RecyclerView

    lateinit var list: List<DataModelItem>
    lateinit var frameLayout: FrameLayout
    lateinit var calendar: Calendar
    lateinit var toolbarCalendar: Toolbar

    lateinit var roomDao: UserDao

    lateinit var progress: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        roomDao = AppDatabase.getInstance()!!
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        api = NetworkConnection.getInstance().getApiClient()
        progress = view.findViewById(R.id.progres_calendar)
        recyclerView = view.findViewById(R.id.RecyclerKalendar)
        toolbarCalendar = view.findViewById(R.id.toolbar_kalendar)
        list=roomDao.getDayAllDavlat()
        if (!list.size.equals(0)) {
            if (!NetworkOn()){
                Toast.makeText(view.context, getString(R.string.netOff), Toast.LENGTH_SHORT).show()
            }
            getListDav(roomDao.getDayAllDavlat())
        }else{
            if (!NetworkOn()){
                Toast.makeText(view.context, getString(R.string.netOff), Toast.LENGTH_SHORT).show()
            }
        }
        frameLayout = view.findViewById<FrameLayout>(R.id.sheet)

        //   frameLayout=FrameLayout(view.context)
        calendar = Calendar.getInstance()
        BottomSheetBehavior.from(frameLayout).apply {
            this.peekHeight = 170
            this.state = BottomSheetBehavior.STATE_COLLAPSED
        }
        val date = SimpleDateFormat("dd-MM-yyyy").parse("05-01-2010")
        var calendarView = frameLayout.get(1) as CalendarView
        calendarView.maxDate = calendar.timeInMillis
        calendarView.minDate = date.time
        calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->

            recyclerView.visibility = View.GONE
            progress.visibility = View.VISIBLE


            var mon: String
            if (month < 10) {
                mon = "0" + (month + 1)
            } else {
                mon = (month + 1).toString()
            }
            if (NetworkOn()) {
                setApi("$year-$mon-$dayOfMonth")
            } else {
                Toast.makeText(view.context, getString(R.string.netOff), Toast.LENGTH_SHORT).show()
            }
        }


    }

    fun getListDav(list: List<DataModelItem>) {
        recyclerView.layoutManager = LinearLayoutManager(view?.context)
        recyclerView.adapter = AdaprerCalendar(list)
        if (!list.size.equals(0)) {
            toolbarCalendar.title = list.get(0).Date
        }
    }

    fun setApi(date: String) {
        api.getAllDavlatListData(date).enqueue(object : Callback<DataModel> {
            override fun onResponse(call: Call<DataModel>, response: Response<DataModel>) {
                if (response.isSuccessful && response.code() == 200) {
                    //    Log.d("tt", "getDate:  "+response.body())
                    getListDav(response.body()!!)
                    recyclerView.visibility = View.VISIBLE
                    progress.visibility = View.GONE
                }
            }

            override fun onFailure(call: Call<DataModel>, t: Throwable) {

            }
        })
    }

    fun NetworkOn(): Boolean {
        val cm = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        val netInfo = cm!!.activeNetworkInfo
        return netInfo != null && netInfo.isConnectedOrConnecting
    }

}