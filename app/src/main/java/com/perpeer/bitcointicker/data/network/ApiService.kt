package com.perpeer.bitcointicker.data.network

import com.perpeer.bitcointicker.data.model.Coin
import com.perpeer.bitcointicker.data.model.CoinDetail
import retrofit2.http.GET
import retrofit2.http.Path

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
}

