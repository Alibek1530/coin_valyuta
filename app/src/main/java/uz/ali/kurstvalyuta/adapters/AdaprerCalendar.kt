package uz.ali.kurstvalyuta.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import uz.ali.kurstvalyuta.ModelServer.DataModelItem
import uz.ali.kurstvalyuta.R


class AdaprerCalendar(var dataVertical: List<DataModelItem>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var map: MutableMap<String, Int> = HashMap()

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
        //inflate your layout and pass it to view holder
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_calendar, parent, false)
        return MyViewHolder(v)
    }

    inner class MyViewHolder(var v: View) : RecyclerView.ViewHolder(v) {

        var HomeTitle = v.findViewById<TextView>(R.id.HomeTextFlag1)
        var HomeTextSom = v.findViewById<TextView>(R.id.HomeTextSom1)
        var HomeTitlePlus = v.findViewById<TextView>(R.id.HomeTextSomPlus1)
        var HomeTitleMin = v.findViewById<TextView>(R.id.HomeTextSomMinus1)
        var HomeFlagPlus = v.findViewById<ImageView>(R.id.HomeImagePlus1)

        fun bind(model: DataModelItem) {
            HomeTitle.text = model.CcyNm_UZ
            HomeTextSom.text = model.Rate + " so'm"

            if (!model.Diff.equals("")) {
                if (model.Diff.toFloat() > 0) {
                    HomeFlagPlus.setImageResource(R.drawable.ic_baseline_plus)
                    HomeTitlePlus.text = model.Diff
                    HomeTitleMin.text = ""
                } else {
                    HomeFlagPlus.setImageResource(R.drawable.ic_baseline_minus)
                    HomeTitleMin.text = model.Diff
                    HomeTitlePlus.text = ""
                }
            } else {
                HomeFlagPlus.setImageResource(R.drawable.ic_baseline_west)
                HomeTitleMin.text = ""
                HomeTitlePlus.text = ""
            }
        }

    }
}