package com.perpeer.bitcointicker.utils.crashlytics

/**
 * Created by Pelşin KAPLAN on 27.09.2024.
 */

sealed class LogWrapper {
    data class Log(val logHolder: LogHolder) : LogWrapper()

    data class LogHolder(val logMessage: String) {
        companion object {
            val GENERAL_WARNING = LogHolder("general warning")
        }
    }
}





