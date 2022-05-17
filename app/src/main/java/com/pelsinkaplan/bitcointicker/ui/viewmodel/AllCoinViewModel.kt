package com.pelsinkaplan.bitcointicker.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.pelsinkaplan.bitcointicker.data.CoinList
import com.pelsinkaplan.bitcointicker.service.network.RetrofitAPI
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Created by Pelşin KAPLAN on 13.05.2022.
 */

@HiltViewModel
class AllCoinViewModel @Inject constructor(
    private val retrofitAPI: RetrofitAPI
) : ViewModel() {

    suspend fun service(): CoinList? {
        val dataResponse = retrofitAPI.getCoinsList()
        if (dataResponse.isSuccessful)
            return dataResponse.body()
        return null
    }
}