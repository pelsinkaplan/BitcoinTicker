package com.perpeer.bitcointicker.data.model

/**
 * Created by Pelşin KAPLAN on 6.01.2025.
 */
data class CoinDetail(
    val id: String,
    val symbol: String,
    val name: String,
    val description: Description,
    val image: Image,
    val market_data: MarketData,
    val hashing_algorithm: String,
    val last_updated: String
)

data class Description(
    val en: String,
    // Diğer diller eklenebilir
)

data class Image(
    val thumb: String,
    val small: String,
    val large: String
)

data class MarketData(
    val current_price: Map<String, Double>,
    val market_cap: Map<String, Double>,
    val total_volume: Map<String, Double>,
    val high_24h: Map<String, Double>,
    val low_24h: Map<String, Double>,
    val price_change_24h: Double,
    val price_change_percentage_24h: Double,
    val market_cap_change_24h: Double,
    val market_cap_change_percentage_24h: Double,
    // Diğer piyasa verileri eklenebilir
)
