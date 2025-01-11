package com.perpeer.bitcointicker.utils.analytics

import androidx.core.bundle.Bundle
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase

object AnalyticsHelper {
    fun logEvent(eventName: String, params: Bundle) {
        Firebase.analytics.logEvent(eventName, params)
    }
}