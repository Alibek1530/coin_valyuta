package uz.ali.kursvalyuta.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import uz.ali.kursvalyuta.ModelServer.DataModelItem2
import uz.ali.kursvalyuta.R
import uz.ali.kursvalyuta.home.conves.ConvetorFragment

class AdaprerHomeCon(var dataVertical: List<DataModelItem2>, var mContext: ConvetorFragment) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

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
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_home_con, parent, false)
        return MyViewHolder(v, mContext)
    }

    class MyViewHolder(var v: View, var main: ConvetorFragment) : RecyclerView.ViewHolder(v) {

        var HomeTitle = v.findViewById<TextView>(R.id.HomeTextFlag)
        var HomeTextSom = v.findViewById<TextView>(R.id.HomeTextSom)
        var HomeTitleMin = v.findViewById<TextView>(R.id.HomeTextSomMinus)
        var HomeFlagPlus = v.findViewById<ImageView>(R.id.HomeImagePlus)


        fun bind(model: DataModelItem2) {
            HomeTitle.text = model.Date
            HomeTextSom.text = model.Rate +  main.getString(R.string.som)
            if (!model.Diff.equals("")) {
                if (model.Diff.toFloat() > 0) {
                    HomeFlagPlus.setImageResource(R.drawable.ic_baseline_plus)
                } else {
                    HomeFlagPlus.setImageResource(R.drawable.ic_baseline_minus)
                }
                HomeTitleMin.text=model.Diff
            }else{
                HomeFlagPlus.setImageResource(R.drawable.ic_baseline_west)
            }

        }
    }
}