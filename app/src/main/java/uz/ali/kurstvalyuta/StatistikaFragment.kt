package uz.ali.kurstvalyuta

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.jjoe64.graphview.DefaultLabelFormatter
import com.jjoe64.graphview.GraphView
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries
import uz.ali.kurstvalyuta.room.AppDatabase
import uz.ali.kurstvalyuta.room.UserDao
import uz.ali.kurstvalyuta.utils.DataMoonDay


class StatistikaFragment : Fragment(R.layout.fragment_statistika) {
    lateinit var graphView: GraphView
    lateinit var series: LineGraphSeries<DataPoint>


    lateinit var roomDao: UserDao

    lateinit var day: String
    lateinit var dateMoonDay: DataMoonDay


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


    lateinit var oy: String
    lateinit var yil: String
    lateinit var davlat: String

    lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        toolbar = view.findViewById(R.id.toolbar_statistika)
        // (activity as AppCompatActivity?)!!.setSupportActionBar(toolbar)
        graphView = view.findViewById(R.id.graph)


        spinnerDavlat = view.findViewById(R.id.spinner)
        spinnerYil = view.findViewById(R.id.spinnerYil)
        spinnerOy = view.findViewById(R.id.spinnerOy)
        textOy = view.findViewById(R.id.spinText)
        textYil = view.findViewById(R.id.YilText)

        listDavlatlar = arrayListOf()

        listYillll = arrayListOf()
        listOy = arrayListOf("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12","13")
        listOyyy = arrayListOf(
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

        roomDao = AppDatabase.getInstance()!!


        day = roomDao.getDayAllDavlat()[0].Date
        listYillll = roomDao.getAllYil() as ArrayList<String>
        listDavlatlar = roomDao.getAllDavlat() as ArrayList<String>
        toolbar.title = listDavlatlar[0]
        var i = listYillll.size - 1
        listYil = arrayListOf()
        Log.d("ddd", "onViewCreated:nfdbdf " + i)
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
        setTextOy(oy)

        adapterDavlat(view)
        adapterYil(view)
        adapterOy(view)

    }

    private fun adapterOy(view: View) {
        val adapter: ArrayAdapter<String> =
            ArrayAdapter<String>(view.context, android.R.layout.simple_spinner_item, listOyyy)

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerOy.adapter = adapter

        spinnerOy.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                oy = listOy.get(position)
            //   var oyyy = listOyyy.get(position)
                setTextOy(oy)
                Log.d("www", "onViewCreated: " + oy)
                //  val item = parent!!.getItemAtPosition(position) as String
                //   spinText.text = item
                //23.01.2010
                if (oy.equals("13")) {
                    Log.d("www", "wwwwwww: " + "______"+ yil + "%")
                    getYearDateShow(
                        davlat,
                        "______"+ yil + "%"
                    )
                } else {
                    getMoonDateShow(
                        davlat,
                        "___" + oy + "." + yil + "%"
                    )
                }
                Toast.makeText(
                    view?.context,
                    "" + roomDao.getDayAllDavlat().get(position).Ccy,
                    Toast.LENGTH_SHORT
                ).show()
            }

        }
    }

    private fun adapterYil(view: View) {
        val adapter: ArrayAdapter<String> =
            ArrayAdapter<String>(view.context, android.R.layout.simple_spinner_item, listYil)

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerYil.adapter = adapter

        spinnerYil.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                yil = listYil.get(position)
                setTextYil(yil + " - ")
                Log.d("www", "onViewCreated: " + yil)
                //         val item = parent!!.getItemAtPosition(position) as String
                //         spinText.text = item
                if (oy.equals("13")) {
                    Log.d("www", "wwwwwww: " + "______"+ yil + "%")
                    getYearDateShow(
                        davlat,
                        "______"+ yil + "%"
                    )
                } else {
                    getMoonDateShow(
                        davlat,
                        "___" + oy + "." + yil + "%"
                    )
                }
                Toast.makeText(
                    view?.context,
                    "" + roomDao.getDayAllDavlat().get(position).Ccy,
                    Toast.LENGTH_SHORT
                ).show()
            }

        }
    }

    private fun adapterDavlat(view: View) {
        val adapter: ArrayAdapter<String> =
            ArrayAdapter<String>(view.context, android.R.layout.simple_spinner_item, listDavlatlar)

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerDavlat.adapter = adapter

        spinnerDavlat.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {

                davlat = roomDao.getDayAllDavlat().get(position).Ccy
                toolbar.title = listDavlatlar.get(position)
                Log.d("www", "onViewCreated: " + davlat)
                //         val item = parent!!.getItemAtPosition(position) as String
                //         spinText.text = item

                if (oy.equals("13")) {
                    Log.d("www", "wwwwwww: " + "______"+ yil + "%")
                    getYearDateShow(
                        davlat,
                        "______"+ yil + "%"
                    )
                } else {
                    getMoonDateShow(
                       davlat,
                        "___" + oy + "." + yil + "%"
                    )
                }

                Toast.makeText(
                    view?.context,
                    "" + roomDao.getDayAllDavlat().get(position).Ccy,
                    Toast.LENGTH_SHORT
                ).show()
            }

        }
    }

    fun getWeekDateShow() {

    }

    fun getYearDateShow(rub: String, date: String) {
        var listMoon = roomDao.geGraphDavlatAllDay(rub, date)


        //  16.01.2021
        val dataPoints = arrayOfNulls<DataPoint>(listMoon.size)
        Log.d("www", "onViewCreated: " + rub + "  " + date)

        var i: Int = 0
        listMoon.forEach {
            dataPoints[i] = DataPoint(i.toDouble(), it.Rate.toDouble())
            i++
        }
        //  var first=15
//        if (dataPoints.size!=0){
//            first =dataPoints[0]!!.x.toInt()
//        }

        //  Toast.makeText(view?.context,"ali  "+dataPoints[0]!!.x.toInt(),Toast.LENGTH_SHORT).show()

        series = LineGraphSeries<DataPoint>(dataPoints)
        graphView.removeAllSeries()
        graphView.addSeries(series)
        // graphView.removeSeries(series)

        series.isDrawDataPoints = true
        series.dataPointsRadius = 6.0f
        series.title = rub
        graphView.legendRenderer.isVisible = true
        // graphView.legendRenderer.setFixedPosition(14,25)
        //  graphView.legendRenderer.textColor=ali rang
        Toast.makeText(view?.context, "size  " + listMoon.size, Toast.LENGTH_SHORT).show()
          //   graphView.gridLabelRenderer.numHorizontalLabels =12
        //    Toast.makeText(view?.context,"sizewhen  "+first,Toast.LENGTH_SHORT).show()
        graphView.gridLabelRenderer.numVerticalLabels = 10
        graphView.gridLabelRenderer.textSize = 17.0f


    }

    fun setTextYil(yil: String) {
        textYil.text = yil
    }

    fun setTextOy(oy: String) {
        textOy.text = oy
    }


    fun getMoonDateShow(rub: String, date: String) {
        var listMoon = roomDao.geGraphDavlatAllDay(rub, date)


        //  16.01.2021
        val dataPoints = arrayOfNulls<DataPoint>(listMoon.size)
        Log.d("www", "onViewCreated: " + rub + "  " + date)

        var i: Int = 0
        listMoon.forEach {
            dataPoints[i] = DataPoint(it.Date.substring(0, 2).toDouble(), it.Rate.toDouble())
            i++
        }
        var first = 15
        if (dataPoints.size != 0) {
            first = dataPoints[0]!!.x.toInt()
        }

        //  Toast.makeText(view?.context,"ali  "+dataPoints[0]!!.x.toInt(),Toast.LENGTH_SHORT).show()

        series = LineGraphSeries<DataPoint>(dataPoints)
        graphView.removeAllSeries()
        graphView.addSeries(series)
        // graphView.removeSeries(series)

        series.isDrawDataPoints = true
        series.dataPointsRadius = 6.0f
        series.title = rub
        graphView.legendRenderer.isVisible = true
        // graphView.legendRenderer.setFixedPosition(14,25)
        //  graphView.legendRenderer.textColor=ali rang
        Toast.makeText(view?.context, "size  " + listMoon.size, Toast.LENGTH_SHORT).show()
        graphView.gridLabelRenderer.numHorizontalLabels = firstFun(first)
        Toast.makeText(view?.context, "sizewhen  " + first, Toast.LENGTH_SHORT).show()
        graphView.gridLabelRenderer.numVerticalLabels = listMoon.size + 2
        graphView.gridLabelRenderer.textSize = 17.0f


    }

    fun firstFun(first: Int): Int {
        var Mon: Int
        Log.d("sss", "firstFun   : $first")
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


}