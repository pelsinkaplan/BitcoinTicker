package com.perpeer.bitcointicker.data.model

import java.io.Serializable

/**
 * Created by Pelşin KAPLAN on 9.01.2025.
 */
class FirestoreCoin(
    val id: String = "",
    val symbol: String = "",
    val name: String = "",
    val timeInterval: Long = 5,
    val price: String = "0.0"
) : Serializable