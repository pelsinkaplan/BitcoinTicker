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
}

