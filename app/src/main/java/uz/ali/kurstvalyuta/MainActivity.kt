package uz.ali.kurstvalyuta

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.android.material.bottomnavigation.BottomNavigationView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import uz.ali.kurstvalyuta.ModelServer.DataModel
import uz.ali.kurstvalyuta.network.ApiService
import uz.ali.kurstvalyuta.network.NetworkConnection
import uz.ali.kurstvalyuta.room.AppDatabase
import uz.ali.kurstvalyuta.room.UserDao

class MainActivity : AppCompatActivity() {
    lateinit var navController: NavController
    lateinit var bottomNavigationView: BottomNavigationView
    lateinit var api: ApiService
    lateinit var roomDao: UserDao
    //lateinit var list:ArrayList<DataModel>
    lateinit var prefs:SharedPreferences

    fun aa() {
//commit new
        roomDao = AppDatabase.getInstance()!!
        api = NetworkConnection.getInstance().getApiClient()

        api.getHomeListAllFinishDay().enqueue(object : Callback<DataModel> {

            override fun onResponse(call: Call<DataModel>, response: Response<DataModel>) {
                if (response.isSuccessful && response.code() == 200) {
                    roomDao?.insert(response.body())
//                    var a=response.body()!!
//                    list.addAll(listOf(a))
                    //  list.add(response.body()!!)
//                    Toast.makeText(this@MainActivity, "" + response.body(), Toast.LENGTH_SHORT)
//                        .show()
                }
            }

            override fun onFailure(call: Call<DataModel>, t: Throwable) {

            }

        })
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        prefs = PreferenceManager.getDefaultSharedPreferences(this)

        if (prefs.getString("tema","kun").equals("kun")){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            prefs.edit().putString("tema", "kun").apply()
        }else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            prefs.edit().putString("tema", "tun").apply()
        }





        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //       list= arrayListOf()
        navController = Navigation.findNavController(this, R.id.frag_oyna)
        bottomNavigationView = findViewById(R.id.Bottom_Menu)

        aa()

        //  roomDao?.insert(list)
    //    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        //  setTheme(R.style.Theme_MaterialComponents_DayNight_DarkActionBar)


        bottomNavigationView.setOnNavigationItemSelectedListener OnNavigationItemSelectedListener@{

            when (it.itemId) {
                R.id.page_1 -> {

                    navController.navigate(R.id.homeFragment)
                    return@OnNavigationItemSelectedListener true

                }
                R.id.page_2 -> {
                    navController.navigate(R.id.kalendarFragment)
                    return@OnNavigationItemSelectedListener true

                }
                R.id.page_3 -> {


                    navController.navigate(R.id.statistikaFragment)
                    return@OnNavigationItemSelectedListener true

                }
                R.id.page_4 -> {
                    navController.navigate(R.id.nastroykaFragment)
                    return@OnNavigationItemSelectedListener true
                }
                else -> {
                    return@OnNavigationItemSelectedListener false
                }
            }
        }
    }

}