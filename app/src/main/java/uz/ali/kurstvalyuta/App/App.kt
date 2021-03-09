package uz.ali.kurstvalyuta.App

import android.app.Application
import uz.ali.kurstvalyuta.room.AppDatabase

class App : Application() {
    companion object {
        private lateinit var instances: App

        fun getInstance() = instances
    }

    override fun onCreate() {
        super.onCreate()
        instances = this
        AppDatabase.init(this)
    }
}