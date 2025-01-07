package com.perpeer.bitcointicker.data.model

/**
 * Created by Pelşin KAPLAN on 6.01.2025.
 */

data class BitcoinResponse(
    val time: Time,
    val disclaimer: String,
    val chartName: String,
    val bpi: Bpi
)

data class Time(
    val updated: String,
    val updatedISO: String,
    val updateduk: String
)

data class Bpi(
    val USD: Currency,
    val GBP: Currency,
    val EUR: Currency
)

data class Currency(
    val code: String,
    val symbol: String,
    val rate: String,
    val description: String,
    val rate_float: Double
)
