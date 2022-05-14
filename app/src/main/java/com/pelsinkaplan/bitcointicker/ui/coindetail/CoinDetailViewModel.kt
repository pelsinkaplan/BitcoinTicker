package com.pelsinkaplan.bitcointicker.ui.coindetail

import androidx.lifecycle.ViewModel
import com.pelsinkaplan.bitcointicker.data.CoinDetail
import com.pelsinkaplan.bitcointicker.service.network.RetrofitAPI
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Created by Pelşin KAPLAN on 13.05.2022.
 */
@HiltViewModel
class CoinDetailViewModel @Inject constructor(
    private val retrofitAPI: RetrofitAPI
) : ViewModel() {

    suspend fun service(id: String): CoinDetail? {
        val dataResponse = retrofitAPI.getCoinsById(id)
        if (dataResponse.isSuccessful)
            return dataResponse.body()
        return null
    }
}