package com.perpeer.bitcointicker.data.model

import java.io.Serializable

/**
 * Created by Pelşin KAPLAN on 6.01.2025.
 */

data class Coin(
    val id: String = "",
    val symbol: String = "",
    val name: String = ""
) : Serializable
