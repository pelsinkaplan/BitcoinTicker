package com.perpeer.bitcointicker.data.network

import com.perpeer.bitcointicker.data.model.Coin
import com.perpeer.bitcointicker.data.model.CoinDetail
import com.perpeer.bitcointicker.data.model.CoinMarketData
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by Pelşin KAPLAN on 6.01.2025.
 */

interface ApiService {
    @GET("coins/list")
    suspend fun getCoinList(): List<Coin>

    @GET("coins/{id}")
    suspend fun getCoinsById(
        @Path("id") id: String,
    ): CoinDetail

    @GET("coins/{coin_id}/market_chart")
    suspend fun getCoinMarketDataDaily(
        @Path("coin_id") coinId: String,
        @Query("vs_currency") currency: String = "usd",
        @Query("days") days: Int = 7
    ): CoinMarketData

    @GET("coins/{coin_id}/market_chart")
    suspend fun getCoinMarketDataHourly(
        @Path("coin_id") coinId: String,
        @Query("vs_currency") currency: String = "usd",
        @Query("days") days: Int = 1,
        @Query("interval") interval: String = "hourly"
    ): CoinMarketData
}

