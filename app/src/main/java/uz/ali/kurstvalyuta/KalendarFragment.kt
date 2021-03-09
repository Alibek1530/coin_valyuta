package uz.ali.kurstvalyuta

import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.CalendarView
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.Toolbar
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import uz.ali.kurstvalyuta.ModelServer.DataModel
import uz.ali.kurstvalyuta.ModelServer.DataModelItem
import uz.ali.kurstvalyuta.adapters.AdaprerCalendar
import uz.ali.kurstvalyuta.network.ApiService
import uz.ali.kurstvalyuta.network.NetworkConnection
import java.text.SimpleDateFormat
import java.util.*


class KalendarFragment : Fragment(R.layout.fragment_kalendar) {
    lateinit var api: ApiService
    lateinit var recyclerView: RecyclerView
    lateinit var tv_click_me: TextView

    lateinit var list: List<DataModelItem>
    lateinit var frameLayout: FrameLayout
    lateinit var calendar: Calendar
    lateinit var toolbarCalendar: Toolbar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Toast.makeText(this.context, "bek", Toast.LENGTH_SHORT).show()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


//        val prefs = PreferenceManager.getDefaultSharedPreferences(view.context)
//        if (prefs.getString("key","kun").equals("kun")){
//            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
//        }else{
//            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
//        }




        frameLayout = view.findViewById<FrameLayout>(R.id.sheet)
        toolbarCalendar = view.findViewById(R.id.toolbar_kalendar)
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
            var mon: String
            if (month < 10) {
                mon = "0" + (month + 1)
            } else {
                mon = (month + 1).toString()
            }
            setApi("$year-$mon-$dayOfMonth")
            toolbarCalendar.title = "$year-$mon-$dayOfMonth"
        }
        api = NetworkConnection.getInstance().getApiClient()
        Toast.makeText(view?.context, "ali", Toast.LENGTH_SHORT).show()
        recyclerView = view.findViewById(R.id.RecyclerKalendar)




//             list = arrayListOf()
//          tv_click_me = view.findViewById(R.id.tv_click_me)
//
//            tv_click_me.setOnClickListener {
//            var bottomSheetDialog = BottomSheetDialog(view.context)
//            var sheetView =
//                LayoutInflater.from(view.context).inflate(R.layout.bottom_sheet_share, null)
//           // var calendar = sheetView.findViewById<CalendarView>(R.id.kalendarView)
//            bottomSheetDialog.setContentView(sheetView)
//            bottomSheetDialog.show()
//
//
//            calendar.setOnDateChangeListener { view, year, month, dayOfMonth ->
//                Log.d("tt", "getDate:cdscdscdscdscsdcsd  " + year)
//                var mon: String
//                if (month < 10) {
//                    mon = "0" + (month + 1)
//                } else {
//                    mon = (month + 1).toString()
//                }
//                bottomSheetDialog.dismiss()
//                setApi("$year-$mon-$dayOfMonth")
//            }
    }
//    //   }
//



    fun getListDav(list: List<DataModelItem>) {
        recyclerView.layoutManager = LinearLayoutManager(view?.context)
        recyclerView.adapter = AdaprerCalendar(list)

    }

    fun setApi(date: String) {
        api.getAllDavlatListData(date).enqueue(object : Callback<DataModel> {
            override fun onResponse(call: Call<DataModel>, response: Response<DataModel>) {
                if (response.isSuccessful && response.code() == 200) {
                    //    Log.d("tt", "getDate:  "+response.body())
                    getListDav(response.body()!!)
                }
            }

            override fun onFailure(call: Call<DataModel>, t: Throwable) {

            }
        })
    }

}