package com.jczm.dataloader.util.helper

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import java.util.Locale

enum class LangEnum {
    en,
    ar
}

class LocaleUtilHelper(val context : Context) {
    val pref : SharedPreferences = context.getSharedPreferences(LOCAL_CACHE, Context.MODE_PRIVATE)

    fun setLocale(context : Context?) : Context?{
        context?.let {
            val language = pref[LANG, "en"]?: "en"
            val locale = Locale(language)
            Locale.setDefault(locale)
            val config = Configuration()
            config.setLocale(locale)
            config.setLayoutDirection(locale)
            context.createConfigurationContext(config)
            pref[LANG] = language
        }
        return context
    }

    fun isArabic() : Boolean{
        val language = pref[LANG, ""]?:""
        return language == LangEnum.ar.name
    }

    companion object{
        const val LOCAL_CACHE = "locale_cache"
        const val LANG = "lang"


        private inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
            val editor = this.edit()
            operation(editor)
            editor.apply()
        }

        operator fun SharedPreferences.set(key: String, value: Any?) {
            when (value) {
                is String? -> edit { it.putString(key, value) }
                is Int -> edit { it.putInt(key, value) }
                is Boolean -> edit { it.putBoolean(key, value) }
                is Double -> edit { it.putLong(key, value.toRawBits()) }
                is Float -> edit { it.putFloat(key, value) }
                is Long -> edit { it.putLong(key, value) }
                else -> throw UnsupportedOperationException("Not yet implemented")
            }
        }

        inline operator fun <reified T : Any> SharedPreferences.get(key: String, defaultValue: T? = null): T? {
            return when (T::class) {
                String::class -> getString(key, defaultValue as? String) as T?
                Int::class -> getInt(key, defaultValue as? Int ?: -1) as T?
                Boolean::class -> getBoolean(key, defaultValue as? Boolean ?: false) as T?
                Double::class -> Double.fromBits(getLong(key, defaultValue as? Long ?: -1)) as T?
                Float::class -> getFloat(key, defaultValue as? Float ?: -1f) as T?
                Long::class -> getLong(key, defaultValue as? Long ?: -1) as T?
                else -> throw UnsupportedOperationException("Not yet implemented")
            }
        }
    }
}