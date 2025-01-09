package com.perpeer.bitcointicker.data.model

/**
 * Created by Pelşin KAPLAN on 8.01.2025.
 */
data class CoinMarketData(
    val prices: List<List<Double>> // İlk değer timestamp, ikinci değer fiyat.
)
