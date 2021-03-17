package uz.ali.kurstvalyuta

import android.content.Context
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.jjoe64.graphview.DefaultLabelFormatter
import com.jjoe64.graphview.GraphView
import com.jjoe64.graphview.Viewport
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import uz.ali.kurstvalyuta.ModelServer.DataModel2
import uz.ali.kurstvalyuta.ModelServer.DataModelDate
import uz.ali.kurstvalyuta.ModelServer.DataModelItem2
import uz.ali.kurstvalyuta.network.ApiService
import uz.ali.kurstvalyuta.network.NetworkConnection
import uz.ali.kurstvalyuta.room.AppDatabase
import uz.ali.kurstvalyuta.room.UserDao
import uz.ali.kurstvalyuta.utils.DataMoonDay
import java.text.SimpleDateFormat
import android.view.View as View1


class StatistikaFragment : Fragment(R.layout.fragment_statistika) {
    lateinit var graphView: GraphView
    lateinit var series: LineGraphSeries<DataPoint>


    lateinit var roomDao: UserDao

    lateinit var day: String
    lateinit var dateMoonDay: DataMoonDay

    lateinit var api: ApiService
    lateinit var apiYil: ApiService
    lateinit var prefs: SharedPreferences
    lateinit var til: String


    lateinit var spinnerDavlat: Spinner
    lateinit var spinnerYil: Spinner
    lateinit var spinnerOy: Spinner

    lateinit var listDavlatlar: ArrayList<String>
    lateinit var listYil: ArrayList<String>
    lateinit var listYillll: ArrayList<String>
    lateinit var listOy: ArrayList<String>
    lateinit var listOyyy: ArrayList<String>
    lateinit var textOy: TextView


    lateinit var textYil: TextView

    lateinit var progress: ProgressBar

    lateinit var oy: String
    lateinit var yil: String
    lateinit var davlat: String

    lateinit var toolbar: Toolbar
    lateinit var davlatCode: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onViewCreated(view: View1, savedInstanceState: Bundle?) {
        toolbar = view.findViewById(R.id.toolbar_statistika)

        prefs = view.context.getSharedPreferences("app", Context.MODE_PRIVATE)
        til = prefs.getString("til", "uz").toString()

        graphView = view.findViewById(R.id.graph)

        progress = view.findViewById(R.id.progres_statistika)

        spinnerDavlat = view.findViewById(R.id.spinner)
        spinnerYil = view.findViewById(R.id.spinnerYil)
        spinnerOy = view.findViewById(R.id.spinnerOy)
        textOy = view.findViewById(R.id.spinText)
        textYil = view.findViewById(R.id.YilText)

        api = NetworkConnection.getInstance().getApiClient()
        listDavlatlar = arrayListOf()

        listYillll = arrayListOf()
        listOy = arrayListOf(
            "01",
            "02",
            "03",
            "04",
            "05",
            "06",
            "07",
            "08",
            "09",
            "10",
            "11",
            "12",
            "13"
        )
        listOyyy = getOy()
        roomDao = AppDatabase.getInstance()!!


        day = roomDao.getDayAllDavlat()[0].Date
        listYillll = roomDao.getAllYil() as ArrayList<String>

        listDavlatlar = getDavlatlar()
        toolbar.title = listDavlatlar[0]
        var i = listYillll.size - 1
        listYil = arrayListOf()
        listYillll.forEach {
            listYil.add(listYillll.get(i))
            i--
        }
        oy = day.substring(3, 5)
        yil = day.substring(6, 10)
        //  day = "16.01.2021"
        Log.d("ddd", "onViewCreated: " + oy)
        Log.d("ddd", "onViewCreated: " + yil)
        dateMoonDay = DataMoonDay()

        setTextYil(yil + " - ")
        listOyyy[oy.toInt() - 1]

        adapterDavlat(view)
        adapterYil(view)
        adapterOy(view)

    }

    private fun adapterOy(view: View1) {
        val adapter: ArrayAdapter<String> =
            ArrayAdapter<String>(view.context, android.R.layout.simple_spinner_item, listOyyy)

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerOy.adapter = adapter

        spinnerOy.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View1?,
                position: Int,
                id: Long
            ) {
                progress.visibility = View1.VISIBLE
                graphView.visibility = View1.INVISIBLE

                spinOy(view, position)

            }

        }
    }

    private fun spinOy(view: View1?, position: Int) {
        oy = listOy.get(position)


        setTextYil(yil + " - ")
        setTextOy(listOyyy[oy.toInt() - 1])


        if (oy.equals("13")) {
            Log.d("rosh o", "sartirovka  " + oy)
            if (NetworkOn()) {
                aabb(yil + "-" + "12" + "-31")
            } else {
                Log.d("www", "wwwwwww: " + "______" + yil + "%")
                getYearDateShow(
                    davlat,
                    "______" + yil + "%"
                )
            }
        } else {
            Log.d("rosh o", "sartirovka  " + oy)
            if (NetworkOn()) {
                if (oy.equals("02")) {
                    aa(yil + "-" + oy + "-28")
                } else {
                    aa(yil + "-" + oy + "-31")
                }
            } else {
                getMoonDateShow(
                    davlat,
                    "___" + oy + "." + yil + "%"
                )
            }
        }
    }

    private fun adapterYil(view: View1) {
        val adapter: ArrayAdapter<String> =
            ArrayAdapter<String>(view.context, android.R.layout.simple_spinner_item, listYil)

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerYil.adapter = adapter

        spinnerYil.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View1?,
                position: Int,
                id: Long
            ) {

                progress.visibility = View1.VISIBLE
                graphView.visibility = View1.INVISIBLE


                spinYil(view, position)
            }
        }
    }

    private fun spinYil(view: View1?, position: Int) {
        yil = listYil.get(position)

        setTextYil(yil + " - ")
        setTextOy(listOyyy[oy.toInt() - 1])

        if (oy.equals("13")) {
            if (NetworkOn()) {
                aabb(yil + "-" + "12" + "-31")
            } else {
                getYearDateShow(
                    davlat,
                    "______" + yil + "%"
                )
            }
        } else {
            Log.d("rosh y", "sartirovka  " + oy)
            if (NetworkOn()) {
                if (oy.equals("02")) {
                    aa(yil + "-" + oy + "-28")
                } else {
                    aa(yil + "-" + oy + "-31")
                }
            } else {
                getMoonDateShow(
                    davlat,
                    "___" + oy + "." + yil + "%"
                )
            }
        }
    }

    private fun adapterDavlat(view: View1) {
        val adapter: ArrayAdapter<String> =
            ArrayAdapter<String>(view.context, android.R.layout.simple_spinner_item, listDavlatlar)

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerDavlat.adapter = adapter

        spinnerDavlat.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View1?,
                position: Int,
                id: Long
            ) {
                progress.visibility = View1.VISIBLE
                graphView.visibility = View1.INVISIBLE

                davlatCode = roomDao.getDayAllDavlat().get(position).Code
                toolbar.title = listDavlatlar.get(position)
                setTextYil(yil + " - ")
                listOyyy[oy.toInt() - 1]

                spinDavlat(view, position)
            }
        }
    }

    private fun spinDavlat(view: View1?, position: Int) {
        davlat = roomDao.getDayAllDavlat().get(position).Ccy
        if (oy.equals("13")) {
            Log.d("rosh d", "sartirovka  " + oy)
            if (NetworkOn()) {
                aabb(yil + "-" + "12" + "-31")
            } else {
                getYearDateShow(
                    davlat,
                    "______" + yil + "%"
                )
            }
        } else {
            Log.d("rosh d", "sartirovka  " + oy)
            if (NetworkOn()) {
                if (oy.equals("02")) {
                    aa(yil + "-" + oy + "-28")
                } else {
                    aa(yil + "-" + oy + "-31")
                }
            } else {
                getMoonDateShow(
                    davlat,
                    "___" + oy + "." + yil + "%"
                )
            }
        }
    }


    fun getYearDateShow(rub: String, date: String) {
        var listMoon = roomDao.geGraphDavlatAllDay(rub, date) as ArrayList


        for (i in 0..listMoon.size - 1) {

            var date = SimpleDateFormat("dd.MM.yyyy").parse(listMoon[i].Date).time
            //   Log.d("saymov", "sartirovka  " + date)
            for (j in 0..listMoon.size - 1) {
                var date1 = SimpleDateFormat("dd.MM.yyyy").parse(listMoon[j].Date).time
                //   Log.d("saymov", "sartirovka sana  " + date1)
                if (date < date1) {
                    var temp = listMoon[i]
                    listMoon[i] = listMoon[j]
                    listMoon[j] = temp
                }
            }
        }
        var minY:Double=listMoon[0].Rate.toDouble()
        var maxY:Double=listMoon[0].Rate.toDouble()

        for (j in 0..listMoon.size - 2) {
            if (maxY < listMoon[j+1].Rate.toDouble()) {
                maxY=listMoon[j+1].Rate.toDouble()
            }
            if(minY > listMoon[j+1].Rate.toDouble()){
                minY=listMoon[j+1].Rate.toDouble()
            }
        }

        Toast.makeText(view?.context,"   "+maxY,Toast.LENGTH_SHORT).show()
        val dataPoints = arrayOfNulls<DataPoint>(listMoon.size)
        var i: Int = 0

        listMoon.forEach {
            var date = SimpleDateFormat("dd.MM.yyyy").parse(it.Date).time
            dataPoints[i] = DataPoint(date.toDouble(), it.Rate.toDouble())
            i++
        }

        series = LineGraphSeries<DataPoint>(dataPoints)
        graphView.removeAllSeries()
        graphView.addSeries(series)

        graphView.viewport.isXAxisBoundsManual = true
        graphView.viewport.setMinX(dataPoints[0]!!.x)
        graphView.viewport.setMaxX(dataPoints[dataPoints.size - 1]!!.x)

        graphView.viewport.isYAxisBoundsManual = true
        graphView.viewport.setMinY(minY)
        graphView.viewport.setMaxY(maxY)

        graphView.gridLabelRenderer.labelFormatter = object : DefaultLabelFormatter() {
            override fun formatLabel(value: Double, isValueX: Boolean): String {
                if (isValueX) {
                    return ""
                }
                return super.formatLabel(value, isValueX)
            }
        }
        //  super.formatLabel(value, isValueX)


        // graphView.removeSeries(series)

        series.isDrawDataPoints = true
        series.dataPointsRadius = 6.0f
        series.title = rub
        graphView.legendRenderer.isVisible = true
        // graphView.legendRenderer.setFixedPosition(14,25)
        //  graphView.legendRenderer.textColor=ali rang
        //   Toast.makeText(view?.context, "size  " + listMoon.size, Toast.LENGTH_SHORT).show()
        graphView.gridLabelRenderer.numHorizontalLabels = listMoon.size
        //    Toast.makeText(view?.context,"sizewhen  "+first,Toast.LENGTH_SHORT).show()
        graphView.gridLabelRenderer.numVerticalLabels = 6
        graphView.gridLabelRenderer.textSize = 17.0f
        graphView.viewport.isScalable = true





        series.setOnDataPointTapListener { series, dataPoint ->
            var date = SimpleDateFormat("dd.MM.yyyy")
            var a = date.format(dataPoint.x.toLong())
            Toast.makeText(
                view?.context,
                a + " --> " + dataPoint.y + " " + getString(R.string.som),
                Toast.LENGTH_SHORT
            ).show()
        }

        progress.visibility = View1.GONE
        graphView.visibility = View1.VISIBLE
    }

    fun setTextYil(yil: String) {
        textYil.text = yil
    }

    fun setTextOy(oy: String) {
        textOy.text = oy
    }

    fun getMoonDateShow(rub: String, date: String) {

        var listMoon = roomDao.geGraphDavlatAllDay(rub, date) as ArrayList


        for (i in 0..listMoon.size - 1) {

            var date = SimpleDateFormat("dd.MM.yyyy").parse(listMoon[i].Date).time
            for (j in 0..listMoon.size - 1) {
                var date1 = SimpleDateFormat("dd.MM.yyyy").parse(listMoon[j].Date).time
                //   Log.d("saymov", "sartirovka sana  " + date1)
                if (date < date1) {
                    var temp = listMoon[i]
                    listMoon[i] = listMoon[j]
                    listMoon[j] = temp
                }
            }
        }
        var minY:Double=listMoon[0].Rate.toDouble()
        var maxY:Double=listMoon[0].Rate.toDouble()

        for (j in 0..listMoon.size - 2) {
            if (maxY < listMoon[j+1].Rate.toDouble()) {
                maxY=listMoon[j+1].Rate.toDouble()
            }
            if(minY > listMoon[j+1].Rate.toDouble()){
                minY=listMoon[j+1].Rate.toDouble()
            }
        }

        //  16.01.2021
        val dataPoints = arrayOfNulls<DataPoint>(listMoon.size)

        var i: Int = 0
        listMoon.forEach {
            dataPoints[i] = DataPoint(it.Date.substring(0, 2).toDouble(), it.Rate.toDouble())
            i++
        }
        var first = 15
        if (dataPoints.size != 0) {
            first = dataPoints[0]!!.x.toInt()
        }

        series = LineGraphSeries<DataPoint>(dataPoints)
        graphView.removeAllSeries()
        graphView.addSeries(series)


        graphView.viewport.isYAxisBoundsManual = true
        graphView.viewport.setMinY(minY)
        graphView.viewport.setMaxY(maxY)

        graphView.viewport.isXAxisBoundsManual = true
        graphView.viewport.setMinX(dataPoints[0]!!.x)
        graphView.viewport.setMaxX(dataPoints[dataPoints.size - 1]!!.x)


        graphView.gridLabelRenderer.labelFormatter = object : DefaultLabelFormatter() {
            override fun formatLabel(value: Double, isValueX: Boolean): String {
                return super.formatLabel(value, isValueX)
            }
        }
        //  super.formatLabel(value, isValueX)


        // graphView.removeSeries(series)
        // series.resetData()
        graphView.viewport.isScalable = true
        series.isDrawDataPoints = true
        series.dataPointsRadius = 6.0f
        series.title = rub
        graphView.legendRenderer.isVisible = true
        // graphView.legendRenderer.setFixedPosition(14,25)
        //  graphView.legendRenderer.textColor=ali rang
        //    Toast.makeText(view?.context, "size  " + listMoon.size, Toast.LENGTH_SHORT).show()
        graphView.gridLabelRenderer.numHorizontalLabels = firstFun(first)
        graphView.gridLabelRenderer.numVerticalLabels = listMoon.size + 2
        graphView.gridLabelRenderer.textSize = 17.0f

        series.setOnDataPointTapListener { series, dataPoint ->
            Toast.makeText(
                view?.context,
                textOy.text.toString() + " " + dataPoint.x.toInt() + " --> " + dataPoint.y + " " + getString(
                    R.string.som
                ),
                Toast.LENGTH_SHORT
            ).show()
        }

        progress.visibility = View1.GONE
        graphView.visibility = View1.VISIBLE
    }

    fun firstFun(first: Int): Int {
        var Mon: Int
        when (first) {
            1 -> {
                Mon = 16
            }
            2 -> {
                Mon = 15
            }
            3 -> {
                Mon = 15
            }
            4 -> {
                Mon = 14
            }
            5 -> {
                Mon = 14
            }
            6 -> {
                Mon = 13
            }
            7 -> {
                Mon = 13
            }
            8 -> {
                Mon = 12
            }
            9 -> {
                Mon = 12
            }
            10 -> {
                Mon = 11
            }
            11 -> {
                Mon = 11
            }
            12 -> {
                Mon = 10
            }
            else -> {
                Mon = 15
            }
        }
        return Mon
    }

    private fun getDavlatlar(): ArrayList<String> {

        if (til.equals("en")) {
            return roomDao.getAllDavlatEn() as ArrayList<String>
        } else if (til.equals("kz")) {
            return roomDao.getAllDavlatUzRu() as ArrayList<String>
        } else if (til.equals("ru")) {
            return roomDao.getAllDavlatRu() as ArrayList<String>
        } else {
            return roomDao.getAllDavlatUz() as ArrayList<String>
        }
    }

    private fun getOy(): ArrayList<String> {
        var list: ArrayList<String>
        if (til.equals("en")) {
            list = arrayListOf(
                "January",
                "February",
                "March",
                "April",
                "May",
                "June",
                "July",
                "August",
                "September",
                "October",
                "November",
                "December",
                "All"
            )
            return list
        } else if (til.equals("kz")) {
            list = arrayListOf(
                "Январь",
                "Февраль",
                "Март",
                "Апрель",
                "Май",
                "Июнь",
                "Июль",
                "Август",
                "Сентябрь",
                "Октябрь",
                "Ноябрь",
                "Декабрь",
                "Хаммаси"
            )
            return list
        } else if (til.equals("ru")) {
            list = arrayListOf(
                "Январь",
                "Февраль",
                "Март",
                "Апрель",
                "Май",
                "Июнь",
                "Июль",
                "Август",
                "Сентябрь",
                "Октябрь",
                "Ноябрь",
                "Декабрь",
                "Все"
            )
            return list
        } else {
            list = arrayListOf(
                "Yanvar",
                "Fevral",
                "Mart",
                "Aprel",
                "May",
                "Iyun",
                "Iyul",
                "Avgust",
                "Sentabr",
                "Oktabr",
                "Noyabr",
                "Dekabr",
                "Hammasi"
            )
            return list
        }
    }


    fun aa(date: String) {
        api = NetworkConnection.getInstance().getApiClient()

        api.getHomeRubOneDay(davlat, date).enqueue(object : Callback<DataModel2> {
            override fun onFailure(call: Call<DataModel2>, t: Throwable) {
                Log.d("alibek", "alibek : $date")
            }

            override fun onResponse(call: Call<DataModel2>, response: Response<DataModel2>) {
                if (response.isSuccessful && response.code() == 200) {
                    var temp: DataModel2 = response.body()!!
                    var ttemp: DataModelItem2 = temp[0]
                    Log.d("alibek", "alibek : ${ttemp.Date}")
                    Log.d("ll", "onResponse:1111 " + ttemp)

                    var b = roomDao.getBool(ttemp.Code, ttemp.Date)
                    if (b.equals(false) && ttemp.Date.substring(3..4) == oy) {
                        roomDao.insert2(ttemp)
                        roomDao.insertDate(DataModelDate(null, ttemp.Date.substring(6, 10)))
                        Log.d("ll", "onResponse:1111lllllllllllllllllllllllllll " + ttemp)
                    }
                    if (ttemp.Date.substring(3..4) == oy) {
                        getDate(temp.get(0).Date)
                        Log.d("ll", "ss " + ttemp)
                        Log.d("salom", "salom : " + yil.toInt())
                    } else {
                        Log.d("ll", "ssasa " + ttemp)

                        getMoonDateShow(
                            davlat,
                            "___" + oy + "." + yil + "%"
                        )
                    }

                }
            }
        })
    }


    fun aabb(date: String) {
        apiYil = NetworkConnection.getInstance().getApiClient()
        Log.d("alibekandroid", "alibek : $date")
        Log.d("java", "alibek : $davlatCode")
        apiYil.getHomeRubOneDay(davlat, date).enqueue(object : Callback<DataModel2> {
            override fun onFailure(call: Call<DataModel2>, t: Throwable) {
                Log.d("uxladi", "alibek : $date")
            }

            override fun onResponse(call: Call<DataModel2>, response: Response<DataModel2>) {
                if (response.isSuccessful && response.code() == 200) {
                    var temp: DataModel2 = response.body()!!
                    var ttemp: DataModelItem2 = temp[0]
                    Log.d("qalay", "alibek : ${ttemp.Date}")
                    Log.d("ll", "onResponse:1111 " + ttemp)

                    var b = roomDao.getBool(ttemp.Code, ttemp.Date)
                    if (b.equals(false) && ttemp.Date.substring(6..9) == yil) {
                        roomDao.insert2(ttemp)
                        roomDao.insertDate(DataModelDate(null, ttemp.Date.substring(6, 10)))
                        Log.d("ll", "onResponse:1111lllllllllllllllllllllllllll " + ttemp)
                    }
                    Log.d("ll", "onResponse:1111llllllllllllllllllllllllkdjwkvggvyugvygvlll " + yil)
                    if (ttemp.Date.substring(6..9) == yil) {
                        getDateYil(temp.get(0).Date)
                        Log.d("kotln", "ssedfs " + ttemp)
                        Log.d("salom", "salom : " + yil.toInt())
                    } else {
                        Log.d("ll", "ssasa " + ttemp)
                        getYearDateShow(
                            davlat,
                            "______" + yil + "%"
                        )
                    }

                }
            }
        })
    }


    fun NetworkOn(): Boolean {

        val cm = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        val netInfo = cm!!.activeNetworkInfo
        return netInfo != null && netInfo.isConnectedOrConnecting
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

    fun getDateYil(date: String) {
        //  2020-12-36 return

        //05.01.2021 date
        var day = date.substring(0..1).toInt()
        var moon = date.substring(3..4)

        var year = date.substring(6..9)

        if ((day - 1) >= 0 && year.toInt() != 2009) {
            var dayRet = year + "-" + moon + "-" + (day - 1)
            aabb(dayRet)
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


}