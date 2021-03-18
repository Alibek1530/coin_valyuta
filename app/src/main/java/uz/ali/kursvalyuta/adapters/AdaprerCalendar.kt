package uz.ali.kursvalyuta.adapters

import android.content.Context
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import uz.ali.kursvalyuta.ModelServer.DataModelItem
import uz.ali.kursvalyuta.R


class AdaprerCalendar(var dataVertical: List<DataModelItem>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var map: MutableMap<String, Int> = HashMap()

    lateinit var prefs: SharedPreferences
    lateinit var til: String
    lateinit var sum: String



    override fun getItemCount(): Int {
        return dataVertical.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is MyViewHolder) {
            val model = dataVertical.get(position)
            holder.bind(model)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_calendar, parent, false)
        prefs = parent.context.getSharedPreferences("app", Context.MODE_PRIVATE)
        til = prefs.getString("til", "uz").toString()
        return MyViewHolder(v)
    }

    inner class MyViewHolder(var v: View) : RecyclerView.ViewHolder(v) {

        var HomeTitle = v.findViewById<TextView>(R.id.HomeTextFlag1)
        var HomeTextSom = v.findViewById<TextView>(R.id.HomeTextSom1)
        var HomeTitleMin = v.findViewById<TextView>(R.id.HomeTextSomMinus1)
        var HomeFlagPlus = v.findViewById<ImageView>(R.id.HomeImagePlus1)

        fun bind(model: DataModelItem) {
            HomeTitle.text = setChange(model)
            HomeTextSom.text = model.Rate + sum

            if (!model.Diff.equals("")) {
                if (model.Diff.toFloat() > 0) {
                    HomeFlagPlus.setImageResource(R.drawable.ic_baseline_plus)
                } else {
                    HomeFlagPlus.setImageResource(R.drawable.ic_baseline_minus)
                }
                HomeTitleMin.text = model.Diff
            } else {
                HomeFlagPlus.setImageResource(R.drawable.ic_baseline_west)
            }
        }

    }
    private fun setChange(model: DataModelItem): String {
        var temp = ""
        if (til.equals("uz")) {
            sum = " so'm"
            temp = model.CcyNm_UZ
        } else if (til.equals("kz")) {
            sum = "  сўм"
            temp = model.CcyNm_UZC
        } else if (til.equals("ru")) {
            sum = " сум"
            temp = model.CcyNm_RU
        } else if (til.equals("en")) {
            sum = " sum"
            temp = model.CcyNm_EN
        }
        return temp
    }
}