package uz.ali.kurstvalyuta

import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import uz.ali.kurstvalyuta.databinding.FragmentNastroykaBinding


class NastroykaFragment : Fragment() {
    private lateinit var binding: FragmentNastroykaBinding
    lateinit var prefs: SharedPreferences
    lateinit var til: String
    lateinit var tema: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNastroykaBinding.inflate(inflater, container, false)
        prefs = PreferenceManager.getDefaultSharedPreferences(binding.root.context)
        return binding.root
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        til = prefs.getString("til", "CcyNm_UZ").toString()
        tema = prefs.getString("tema", "kun").toString()

        setChange()


        var bottomSheet = BottomSheetBehavior.from(binding.sheetNastroyka).apply {
            this.peekHeight = 0
            this.state = BottomSheetBehavior.STATE_COLLAPSED
        }

        binding.cardTema.setOnClickListener {
            binding.visibileTil.visibility = View.GONE
            binding.visibileTema.visibility = View.VISIBLE

            bottomSheet.apply {
                this.state = BottomSheetBehavior.STATE_COLLAPSED
                this.peekHeight = 600
                this.isHideable = true
            }
        }
        binding.cardKun.setOnClickListener {

            if (!prefs.getString("tema", "kun").equals("kun")) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                prefs.edit().putString("tema", "kun").apply()
            }
            tema = prefs.getString("tema","kun").toString()

            setChange()
            bottomSheet.state = BottomSheetBehavior.STATE_HIDDEN
            Toast.makeText(view.context, "card kun", Toast.LENGTH_SHORT).show()
        }
        binding.cardTun.setOnClickListener {

            if (!prefs.getString("tema", "tun").equals("tun")) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                prefs.edit().putString("tema", "tun").apply()
            }
            tema = prefs.getString("tema","tun").toString()

            setChange()
            bottomSheet.state = BottomSheetBehavior.STATE_HIDDEN
            Toast.makeText(view.context, "card tun", Toast.LENGTH_SHORT).show()
        }

        binding.cardTil.setOnClickListener {

            binding.visibileTema.visibility = View.GONE
            binding.visibileTil.visibility = View.VISIBLE
            bottomSheet.apply {
                this.state = BottomSheetBehavior.STATE_COLLAPSED
                this.peekHeight = 600
                this.isHideable = true
            }

        }
        binding.cardUzLat.setOnClickListener {
            Toast.makeText(view.context, "card  uz lat", Toast.LENGTH_SHORT).show()
            prefs.edit().putString("til", "CcyNm_UZ").apply()
            til=prefs.getString("til","CcyNm_UZ").toString()
            setChange()
            bottomSheet.state = BottomSheetBehavior.STATE_HIDDEN
        }
        binding.cardEng.setOnClickListener {
            Toast.makeText(view.context, "card eng", Toast.LENGTH_SHORT).show()
            prefs.edit().putString("til", "CcyNm_EN").apply()
            til=prefs.getString("til","CcyNm_EN").toString()
            setChange()
            bottomSheet.state = BottomSheetBehavior.STATE_HIDDEN
        }
        binding.cardRu.setOnClickListener {
            Toast.makeText(view.context, "card rus", Toast.LENGTH_SHORT).show()
            prefs.edit().putString("til", "CcyNm_RU").apply()
            til=prefs.getString("til","CcyNm_RU").toString()
            setChange()
            bottomSheet.state = BottomSheetBehavior.STATE_HIDDEN
        }
        binding.cardUzKir.setOnClickListener {
            Toast.makeText(view.context, "card uz kiril", Toast.LENGTH_SHORT).show()
            prefs.edit().putString("til", "CcyNm_UZC").apply()
            til=prefs.getString("til","CcyNm_UZC").toString()
            setChange()
            bottomSheet.state = BottomSheetBehavior.STATE_HIDDEN
        }

    }

    private fun setChange() {
        if (til.equals("CcyNm_UZ")) {
            binding.toolbarNastroyka.title = "Sozlamalar"
            binding.txtAsosiy.text = "Asosiy"
            binding.txtQoshimcha.text = "Qoshimcha"
            binding.txtTema.text = "Tema"
            binding.txtTil.text = "Til"
            binding.txtTill.text = binding.txtUzLat.text.toString()
            if (tema.equals("kun")) {
                binding.txtTemaa.text = "kundizgi tema"
            } else {
                binding.txtTemaa.text = "tungi tema"
            }
        } else if (til.equals("CcyNm_UZC")) {
            binding.toolbarNastroyka.title = "Созламалар"
            binding.txtAsosiy.text = "Асосий"
            binding.txtQoshimcha.text = "Кошимча"
            binding.txtTema.text = "тема"
            binding.txtTil.text = "тил"
            binding.txtTill.text = binding.txtUzKir.text.toString()
            if (tema.equals("kun")) {
                binding.txtTemaa.text = "кундизги тема"
            } else {
                binding.txtTemaa.text = "тунги тема"
            }
        } else if (til.equals("CcyNm_RU")) {
            binding.toolbarNastroyka.title = "Настройки"
            binding.txtAsosiy.text = "Главние"
            binding.txtQoshimcha.text = "Дополнително"
            binding.txtTema.text = "тема"
            binding.txtTil.text = "язик"
            binding.txtTill.text = binding.txtRu.text.toString()
            if (tema.equals("kun")) {
                binding.txtTemaa.text = "светлий тема"
            } else {
                binding.txtTemaa.text = "начной тема"
            }
        } else if (til.equals("CcyNm_EN")) {
            binding.toolbarNastroyka.title = "Setting"
            binding.txtAsosiy.text = "Basically"
            binding.txtQoshimcha.text = "Aditonial"
            binding.txtTema.text = "theme"
            binding.txtTil.text = "language"
            binding.txtTill.text = binding.txtEn.text.toString()
            if (tema.equals("kun")) {
                binding.txtTemaa.text = "day theme"
            } else {
                binding.txtTemaa.text = "hight theme"
            }
        }
    }




    private fun getSheet(view: View) {
//        var bottomSheetDialog = BottomSheetDialog(view.context)
//        var sheetView =
//            LayoutInflater.from(view.context).inflate(R.layout.bottom_sheet_share, null)
//        // var calendar = sheetView.findViewById<CalendarView>(R.id.kalendarView)
//        bottomSheetDialog.setContentView(sheetView)
//        bottomSheetDialog.show()
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


    companion object {

    }
}