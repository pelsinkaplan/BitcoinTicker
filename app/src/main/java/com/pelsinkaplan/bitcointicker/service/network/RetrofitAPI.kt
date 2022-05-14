package com.pelsinkaplan.bitcointicker.service.network

import com.pelsinkaplan.bitcointicker.data.CoinDetail
import com.pelsinkaplan.bitcointicker.data.CoinList
import retrofit2.Response
import retrofit2.http.*

/**
 * Created by Pelşin KAPLAN on 13.05.2022.
 */

interface RetrofitAPI {

    @GET("coins/list")
    suspend fun getCoinsList(
    ): Response<CoinList>

    @GET("coins/{id}")
    suspend fun getCoinsById(
        @Path("id") id: String,
    ): Response<CoinDetail>

}