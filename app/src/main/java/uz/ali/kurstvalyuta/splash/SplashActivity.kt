package uz.ali.kurstvalyuta.splash

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import uz.ali.kurstvalyuta.MainActivity
import uz.ali.kurstvalyuta.ModelServer.DataModel
import uz.ali.kurstvalyuta.R
import uz.ali.kurstvalyuta.network.ApiService
import uz.ali.kurstvalyuta.network.NetworkConnection
import uz.ali.kurstvalyuta.room.AppDatabase
import uz.ali.kurstvalyuta.room.UserDao
import uz.ali.kurstvalyuta.utils.RuntimeLocaleChanger

class SplashActivity : AppCompatActivity() {

    lateinit var api: ApiService
    lateinit var roomDao: UserDao
    lateinit var prefs: SharedPreferences

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
        setContentView(R.layout.activity_splash)

        apiGet()

    }
    fun apiGet() {
        roomDao = AppDatabase.getInstance()!!
        api = NetworkConnection.getInstance().getApiClient()
        api.getHomeListAllFinishDay().enqueue(object : Callback<DataModel> {
            override fun onResponse(call: Call<DataModel>, response: Response<DataModel>) {
                if (response.isSuccessful && response.code() == 200) {
                    roomDao?.insert(response.body())
                    setIntent()
                }
            }
            override fun onFailure(call: Call<DataModel>, t: Throwable) {
                setIntent()
            }
        })
    }
    fun setIntent(){
        var mainActivity = Intent(this,MainActivity::class.java)
        Toast.makeText(this,getString(R.string.netOff),Toast.LENGTH_SHORT).show()
        startActivity(mainActivity)
        finish()
    }

}