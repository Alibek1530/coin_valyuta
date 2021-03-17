package uz.ali.kurstvalyuta

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import uz.ali.kurstvalyuta.ModelServer.DataModel
import uz.ali.kurstvalyuta.network.ApiService
import uz.ali.kurstvalyuta.network.NetworkConnection
import uz.ali.kurstvalyuta.room.AppDatabase
import uz.ali.kurstvalyuta.room.UserDao
import uz.ali.kurstvalyuta.utils.RuntimeLocaleChanger

class MainActivity : AppCompatActivity() {
    lateinit var navController: NavController
    lateinit var bottomNavigationView: BottomNavigationView

    lateinit var prefs: SharedPreferences
    var counter = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        prefs = getSharedPreferences("app", Context.MODE_PRIVATE)

        RuntimeLocaleChanger.setLocale(this)
        if (prefs.getString("tema", "kun").equals("kun")) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            prefs.edit().putString("tema", "kun").apply()
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            prefs.edit().putString("tema", "tun").apply()
        }

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navController = findNavController(R.id.frag_oyna)
        bottomNavigationView = findViewById(R.id.Bottom_Menu)
        bottomNavigationView.setOnNavigationItemSelectedListener OnNavigationItemSelectedListener@{

            when (it.itemId) {
                R.id.page_1 -> {
                    navController.popBackStack()
                    navController.navigate(R.id.homeFragment)

                    var q = prefs.getBoolean("back", false)
                    if (q) {
                        navController.popBackStack()
                        prefs.edit().putBoolean("back", false).apply()
                    }
                    return@OnNavigationItemSelectedListener true
                }
                R.id.page_2 -> {
                    var q = prefs.getBoolean("back", false)
                    if (q) {
                        prefs.edit().putBoolean("back", false).apply()
                        super.onBackPressed()
                    }
                    navController.popBackStack()
                    navController.navigate(R.id.kalendarFragment)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.page_3 -> {
                    var q = prefs.getBoolean("back", false)
                    if (q) {
                        prefs.edit().putBoolean("back", false).apply()
                        super.onBackPressed()
                    }
                    navController.popBackStack()
                    navController.navigate(R.id.statistikaFragment)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.page_4 -> {

                    var q = prefs.getBoolean("back", false)
                    if (q) {
                        prefs.edit().putBoolean("back", false).apply()
                        super.onBackPressed()
                    }
                    navController.popBackStack()
                    navController.navigate(R.id.nastroykaFragment)


                    return@OnNavigationItemSelectedListener true
                }
                else -> {
                    return@OnNavigationItemSelectedListener false
                }
            }
        }
    }

    override fun onBackPressed() {
        var q = prefs.getBoolean("back", false)
        if (q) {
            prefs.edit().putBoolean("back", false).apply()
            super.onBackPressed()
        } else {
            counter++
            if (counter > 1) {
                super.onBackPressed()
                finish()
            } else {
                Toast.makeText(this, "${getString(R.string.yana_bosing)}", Toast.LENGTH_SHORT)
                    .show()
            }
            val DELAY_TIME = 3000L
            Thread(Runnable {
                try {
                    Thread.sleep(DELAY_TIME)
                    counter = 0
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }).start()
        }
    }

}