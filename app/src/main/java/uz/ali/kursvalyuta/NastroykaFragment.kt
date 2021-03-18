package uz.ali.kursvalyuta

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetBehavior
import uz.ali.kursvalyuta.databinding.FragmentNastroykaBinding
import uz.ali.kursvalyuta.utils.RuntimeLocaleChanger


class NastroykaFragment() : Fragment() {
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
        return binding.root
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        prefs = view.context.getSharedPreferences("app", Context.MODE_PRIVATE)
        til = prefs.getString("til", "uz").toString()
        tema = prefs.getString("tema", "kun").toString()

        setChange()


        var bottomSheet = BottomSheetBehavior.from(binding.sheetNastroyka).apply {
            this.peekHeight = 0
            this.state = BottomSheetBehavior.STATE_COLLAPSED
        }

        binding.cardTema.setOnClickListener {
            binding.visibileTil.visibility = View.GONE
            binding.visibileMalumot.visibility = View.GONE
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
            tema = prefs.getString("tema", "kun").toString()

            setChange()
            bottomSheet.state = BottomSheetBehavior.STATE_HIDDEN
            Toast.makeText(view.context, "card kun", Toast.LENGTH_SHORT).show()
        }
        binding.cardTun.setOnClickListener {

            if (!prefs.getString("tema", "tun").equals("tun")) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                prefs.edit().putString("tema", "tun").apply()
            }
            tema = prefs.getString("tema", "tun").toString()

            setChange()
            bottomSheet.state = BottomSheetBehavior.STATE_HIDDEN
        }



        binding.cardTil.setOnClickListener {

            binding.visibileTema.visibility = View.GONE
            binding.visibileMalumot.visibility = View.GONE
            binding.visibileTil.visibility = View.VISIBLE
            bottomSheet.apply {
                this.state = BottomSheetBehavior.STATE_COLLAPSED
                this.peekHeight = 600
                this.isHideable = true
            }

        }
        binding.cardUzLat.setOnClickListener {
            RuntimeLocaleChanger.setNewLocale(view.context, "uz")
            setChange()
            bottomSheet.state = BottomSheetBehavior.STATE_HIDDEN
            activity?.recreate()
        }
        binding.cardEng.setOnClickListener {
            RuntimeLocaleChanger.setNewLocale(view.context, "en")
            setChange()
            bottomSheet.state = BottomSheetBehavior.STATE_HIDDEN
            activity?.recreate()
        }
        binding.cardRu.setOnClickListener {
            RuntimeLocaleChanger.setNewLocale(view.context, "ru")
            setChange()
            bottomSheet.state = BottomSheetBehavior.STATE_HIDDEN
            activity?.recreate()
        }
        binding.cardUzKir.setOnClickListener {

            RuntimeLocaleChanger.setNewLocale(view.context, "kz")
            setChange()
            bottomSheet.state = BottomSheetBehavior.STATE_HIDDEN
            activity?.recreate()
        }


        binding.cardMalumot.setOnClickListener {
            binding.visibileTema.visibility = View.GONE
            binding.visibileTil.visibility = View.GONE
            binding.visibileMalumot.visibility = View.VISIBLE

            bottomSheet.apply {
                this.state = BottomSheetBehavior.STATE_COLLAPSED
                this.peekHeight = 1500
                this.isHideable = true
            }
        }




        binding.cardJonat.setOnClickListener {
            ShareF()
        }
        binding.cardBaxo.setOnClickListener {
            PlayMarketStar()
        }
        binding.cardGmail.setOnClickListener {
            EAccount()
        }

    }

    fun ShareF() {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, getString(R.string.shareApp))
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }

    fun PlayMarketStar() {
        var intent = Intent(Intent.ACTION_VIEW)
        // baholash google playda
        intent.setData(Uri.parse(getString(R.string.urlPlayApp)))
        startActivity(intent)
    }

    fun EAccount() {
        var email = Intent(Intent.ACTION_SEND)
        email.putExtra(Intent.EXTRA_EMAIL, arrayOf(getString(R.string.urlGmail)))
        email.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name))
        email.putExtra(Intent.EXTRA_TEXT, getString(R.string.hello))
        email.type = "application/octet-stream"
        startActivity(email)
    }


    private fun setChange() {
        binding.toolbarNastroyka.title = getString(R.string.nastroyka)
        binding.txtAsosiy.text = getString(R.string.asosiy)
        binding.txtQoshimcha.text = getString(R.string.qoshimcha)
        binding.txtTema.text = getString(R.string.theme)
        binding.txtTil.text = getString(R.string.til_usti)
        binding.txtGmail.text = getString(R.string.gmail)
        binding.txtBaxo.text = getString(R.string.baxo)
        binding.txtJonat.text = getString(R.string.share)
        binding.txtTill.text = getString(R.string.til)

        binding.txtMal.text = getString(R.string.dastur_haqida)
        binding.txtMall.text = getString(R.string.versiya)
        if (tema.equals("kun")) {
            binding.txtTemaa.text = getString(R.string.tema_kun)
        } else {
            binding.txtTemaa.text = getString(R.string.tema_tun)
        }
    }

}