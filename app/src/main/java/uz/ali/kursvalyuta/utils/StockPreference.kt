package uz.ali.kursvalyuta.utils

import android.content.Context
import android.content.Context.MODE_PRIVATE

class StockPreference(val context: Context) {

    private val mode = MODE_PRIVATE

    var lang: String
        get() = context.getSharedPreferences("app", mode).getString("til", "uz") ?: "uz"
        set(value) {
            context.getSharedPreferences("app", mode).edit().putString("til", value).apply()
        }
}