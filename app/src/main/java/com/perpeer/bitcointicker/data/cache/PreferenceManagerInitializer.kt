package com.perpeer.bitcointicker.data.cache

import android.content.Context
import androidx.startup.Initializer

/**
 * Created by Pelşin KAPLAN on 6.01.2025.
 */
class PreferenceManagerInitializer : Initializer<Unit> {

    override fun create(context: Context) {
        PreferenceManager.initialize(context)
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return listOf()
    }
}