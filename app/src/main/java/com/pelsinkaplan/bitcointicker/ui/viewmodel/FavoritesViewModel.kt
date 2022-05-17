package com.pelsinkaplan.bitcointicker.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.FirebaseDatabase
import com.pelsinkaplan.bitcointicker.R
import com.pelsinkaplan.bitcointicker.data.CoinDetail
import com.pelsinkaplan.bitcointicker.service.network.RetrofitAPI
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val retrofitAPI: RetrofitAPI
) : ViewModel() {

    var favList = MutableLiveData<List<String>>()
    val favCoinList = MutableLiveData<List<CoinDetail>>()


    fun getFavoritesCoinIdList(userId: String) {
        val database =
            FirebaseDatabase.getInstance().getReference("favorites").child(userId)

        database.get().addOnSuccessListener {
            if (it.exists())
                favList.postValue(
                    it.value as List<String>
                )
        }
    }

    suspend fun getFavoritesCoinList(favCoinIdList: List<String>) {
        val tempList = ArrayList<CoinDetail>()
        for (id in favCoinIdList) {
            val dataResponse = retrofitAPI.getCoinsById(id)
            if (dataResponse.isSuccessful) {
                tempList.add(dataResponse.body()!!)
            }

        }
        favCoinList.postValue(tempList)
    }
}