package com.perpeer.bitcointicker.data.cache

import android.content.SharedPreferences
import com.google.gson.Gson
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/**
 * Created by Pelşin KAPLAN on 3.08.2024.
 */
inline fun <reified T> SharedPreferences.get(key: String): T? {
    if (!this.contains(key)) {
        return null
    }

    return when (T::class) {
        Boolean::class -> this.getBoolean(key, false) as T
        Float::class -> this.getFloat(key, 0f) as T
        Int::class -> this.getInt(key, 0) as T
        Long::class -> this.getLong(key, 0) as T
        String::class -> this.getString(key, null) as T
        LocalDateTime::class -> LocalDateTime.parse(
            this.getString(key, null),
            DateTimeFormatter.ISO_LOCAL_DATE_TIME
        ) as T

        else -> {
            Gson().fromJson(getString(key, ""), T::class.java)
        }
    }
}

inline fun <reified T> SharedPreferences.set(key: String, value: T?): Boolean {
    val editor = this.edit()

    return if (value == null) {
        editor.remove(key)
        editor.commit()
    } else {
        when (T::class) {
            Boolean::class -> editor.putBoolean(key, value as Boolean)
            Float::class -> editor.putFloat(key, value as Float)
            Int::class -> editor.putInt(key, value as Int)
            Long::class -> editor.putLong(key, value as Long)
            String::class -> editor.putString(key, value as String)
            LocalDateTime::class -> editor.putString(
                key,
                (value as LocalDateTime).format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
            )

            else -> {
                editor.putString(key, Gson().toJson(value))
            }
        }

        editor.commit()
    }
}