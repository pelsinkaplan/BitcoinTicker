package com.perpeer.bitcointicker.data.repository

import com.perpeer.bitcointicker.data.model.Coin
import com.perpeer.bitcointicker.data.model.CoinDetail
import com.perpeer.bitcointicker.data.network.ApiService
import javax.inject.Inject

/**
 * Created by Pelşin KAPLAN on 6.01.2025.
 */

class BitcoinRepository @Inject constructor(
    private val apiService: ApiService
) {
    suspend fun getCoinList(): List<Coin> {
        return apiService.getCoinList()
    }

    suspend fun getCoinDetail(id: String): CoinDetail {
        return apiService.getCoinsById(id)
    }

    suspend fun fetchCoinMarketDataDaily(coinId: String, days: Int): List<Pair<Double, Double>> {
        return try {
            val response = apiService.getCoinMarketDataDaily(coinId, days = days)
            response.prices.map { Pair(it[0], it[1]) } // Timestamp ve fiyat çiftleri.
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun getCoinMarketDataHourly(coinId: String): List<Pair<Double, Double>> {
        return try {
            val response = apiService.getCoinMarketDataHourly(coinId)
            response.prices.map { Pair(it[0], it[1]) } // Timestamp ve fiyat çiftleri.
        } catch (e: Exception) {
            emptyList()
        }
    }
}

