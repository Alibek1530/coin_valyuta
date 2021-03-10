package uz.ali.kurstvalyuta.utils

import android.content.Context

class BaseRepository {

    companion object {

        fun getLang(context: Context) = StockPreference(context).lang

        fun setLang(lang: String, context: Context) {
            StockPreference(context).lang = lang
        }
    }
}
