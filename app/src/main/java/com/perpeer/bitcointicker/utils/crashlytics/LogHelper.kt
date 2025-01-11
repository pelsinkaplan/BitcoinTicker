package com.perpeer.bitcointicker.utils.crashlytics

import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase
import com.perpeer.bitcointicker.utils.Constants.StringParameter.COLON

object LogHelper {
    fun setFatalError(tag: String, message: String) {
        Firebase.crashlytics.recordException(Throwable(tag + COLON + COLON + message))
    }
}