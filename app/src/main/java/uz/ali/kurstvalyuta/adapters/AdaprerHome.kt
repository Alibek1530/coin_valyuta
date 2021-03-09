package uz.ali.kurstvalyuta.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import uz.ali.kurstvalyuta.ModelServer.DataModelItem
import uz.ali.kurstvalyuta.R
import uz.ali.kurstvalyuta.home.HomeFragment


class AdaprerHome(var dataVertical: List<DataModelItem>, var mContext: HomeFragment) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var map: MutableMap<String, Int> = HashMap()

    init {
        map["784"] = R.drawable.ae
        map["971"] = R.drawable.af
        map["051"] = R.drawable.am
        map["032"] = R.drawable.ar
        map["036"] = R.drawable.au
        map["944"] = R.drawable.az
        map["050"] = R.drawable.bd
        map["975"] = R.drawable.bg
        map["048"] = R.drawable.bh
        map["096"] = R.drawable.bn
        map["986"] = R.drawable.br
        map["933"] = R.drawable.by
        map["048"] = R.drawable.ba
        map["124"] = R.drawable.ca
        map["756"] = R.drawable.ch
        map["156"] = R.drawable.cn
        map["192"] = R.drawable.cu
        map["203"] = R.drawable.cz
        map["208"] = R.drawable.dk
        map["012"] = R.drawable.dz
        map["818"] = R.drawable.eg
        map["978"] = R.drawable.eu
        map["826"] = R.drawable.gb
        map["981"] = R.drawable.ge
        map["344"] = R.drawable.hk
        map["348"] = R.drawable.hu
        map["360"] = R.drawable.id
        map["376"] = R.drawable.il
        map["356"] = R.drawable.`in`
        map["368"] = R.drawable.iq
        map["364"] = R.drawable.ir
        map["352"] = R.drawable.`is`
        map["400"] = R.drawable.jo
        map["392"] = R.drawable.jp
        map["417"] = R.drawable.kg
        map["116"] = R.drawable.kh
        map["410"] = R.drawable.kr
        map["414"] = R.drawable.kw
        map["398"] = R.drawable.kz
        map["418"] = R.drawable.la
        map["422"] = R.drawable.lb
        map["434"] = R.drawable.ly
        map["504"] = R.drawable.ma
        map["498"] = R.drawable.md
        map["104"] = R.drawable.mm
        map["496"] = R.drawable.mn
        map["484"] = R.drawable.mx
        map["458"] = R.drawable.my
        map["578"] = R.drawable.no
        map["554"] = R.drawable.nz
        map["512"] = R.drawable.om
        map["608"] = R.drawable.ph
        map["586"] = R.drawable.pk
        map["634"] = R.drawable.qa
        map["946"] = R.drawable.ro
        map["941"] = R.drawable.rs
        map["643"] = R.drawable.ru
        map["682"] = R.drawable.sa
        map["938"] = R.drawable.sd
        map["752"] = R.drawable.se
        map["702"] = R.drawable.sg
        map["760"] = R.drawable.sy
        map["764"] = R.drawable.th
        map["972"] = R.drawable.tj
        map["934"] = R.drawable.tm
        map["788"] = R.drawable.tn
        map["949"] = R.drawable.tr
        map["980"] = R.drawable.ua
        map["840"] = R.drawable.us
        map["858"] = R.drawable.uy
        map["928"] = R.drawable.ve
        map["704"] = R.drawable.vn
        map["960"] = R.drawable.xd
        map["886"] = R.drawable.ye
        map["710"] = R.drawable.za
        Log.d("nn", "bind: tayor ")
    }

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
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_home, parent, false)
        return MyViewHolder(v, mContext)
    }

    inner class MyViewHolder(var v: View, var main: HomeFragment) : RecyclerView.ViewHolder(v) {

        var HomeFlag = v.findViewById<ImageView>(R.id.HomeImageFlag)
        var HomeTitle = v.findViewById<TextView>(R.id.HomeTextFlag)
        var HomeTextSom = v.findViewById<TextView>(R.id.HomeTextSom)
        var HomeTitlePlus = v.findViewById<TextView>(R.id.HomeTextSomPlus)
        var HomeTitleMin = v.findViewById<TextView>(R.id.HomeTextSomMinus)
        var HomeFlagPlus = v.findViewById<ImageView>(R.id.HomeImagePlus)

        init {
            v.setOnClickListener {
                main.getData(adapterPosition)
            }
        }

        fun bind(model: DataModelItem) {

//            Glide.with(v.context)
//                .load(getR).centerCrop()
//                .into(HomeFlag)

            var a: Int? = map.get(model.Code)
            //   Log.d("nn", "bind: "+flag)
//          var flag=model.Ccy.substring(0,2).toLowerCase()
//
//            var a=v.resources.getIdentifier(flag,"drawable",v.context.packageName)
//           // v.resources.getDrawable(a)
//            if (model.Code.equals(m)){
//
//            }
            if (a != null) {
                HomeFlag.setImageResource(a)
            }

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


//            v.setOnClickListener {
//                main.datazak(model.title, model.id)
//            }
        }

    }
}