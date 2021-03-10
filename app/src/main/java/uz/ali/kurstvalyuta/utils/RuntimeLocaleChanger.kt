package uz.ali.kurstvalyuta.utils

import android.content.Context
import android.os.Build
import java.util.*

object RuntimeLocaleChanger {
    fun setLocale(c: Context): Context? {
        return updateResources(c, BaseRepository.getLang(c))
    }

    fun getLocale(c: Context): String {
        return BaseRepository.getLang(c)
    }

    fun setNewLocale(c: Context, language: String) {
        persistLanguage(c, language)
        updateResources(c, language)
    }

    private fun persistLanguage(c: Context, language: String) {
        BaseRepository.setLang(language, c)
    }

    private fun updateResources(context: Context, language: String): Context? {
        var c = context
        val locale = Locale(language)
        Locale.setDefault(locale)
        val resources = c.resources
        val configuration = resources.configuration

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            configuration.setLocale(locale)
            c = c.createConfigurationContext(configuration)
            resources.updateConfiguration(configuration, resources.displayMetrics)
        } else {
            configuration.locale = locale
            resources.updateConfiguration(configuration, resources.displayMetrics)
        }
        return c
    }

}