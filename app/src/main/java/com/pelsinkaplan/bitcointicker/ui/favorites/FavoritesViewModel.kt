package com.pelsinkaplan.bitcointicker.ui.favorites

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.FirebaseDatabase
import com.pelsinkaplan.bitcointicker.R
import com.pelsinkaplan.bitcointicker.service.network.RetrofitAPI
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val retrofitAPI: RetrofitAPI
) : ViewModel() {

    var favList = MutableLiveData<List<String>>()


    fun service(userId: String): RetrofitAPI {
        val database =
            FirebaseDatabase.getInstance().getReference("favorites").child(userId)

        database.get().addOnSuccessListener {
            if (it.exists()) {
                favList.postValue(
                    it.value as List<String>
                )
            }
        }
        return retrofitAPI
    }
}