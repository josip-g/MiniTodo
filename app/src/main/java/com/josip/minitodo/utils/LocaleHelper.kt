package com.josip.minitodo.utils

import android.content.Context
import com.josip.minitodo.R
import java.util.*
import androidx.core.content.edit
import com.josip.minitodo.utils.Constants.PREFS_NAME
import com.josip.minitodo.utils.Constants.KEY_LANGUAGE
object LocaleHelper {

    fun saveLanguage(context: Context, language: String) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit() { putString(KEY_LANGUAGE, language) }
    }

    fun getSavedLanguage(context: Context): String {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return prefs.getString(KEY_LANGUAGE, Constants.LANG_EN) ?: Constants.LANG_EN
    }

    fun applySavedLocale(context: Context): Context {
        val lang = getSavedLanguage(context)
        return wrapWithLocale(context, Locale(lang))
    }

    fun wrapWithLocale(context: Context, locale: Locale): Context {
        Locale.setDefault(locale)
        val config = context.resources.configuration
        config.setLocale(locale)
        return context.createConfigurationContext(config)
    }

    fun getDialogTitleForLang(context: Context): String {
        return context.getString(R.string.choose_language)
    }

    fun getLanguages(context: Context): List<Pair<String, String>> {
        return listOf(
            Constants.LANG_EN to context.getString(R.string.language_english),
            Constants.LANG_HR to context.getString(R.string.language_croatian),
            Constants.LANG_DE to context.getString(R.string.language_german)
        )
    }
}