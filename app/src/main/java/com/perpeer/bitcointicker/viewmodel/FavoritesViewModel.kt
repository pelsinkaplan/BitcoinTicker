package com.perpeer.bitcointicker.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.perpeer.bitcointicker.data.cache.firestore.FireStoreRepository
import com.perpeer.bitcointicker.data.model.Coin
import com.perpeer.bitcointicker.data.model.FirestoreCoin
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Pelşin KAPLAN on 7.01.2025.
 */
@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val repository: FireStoreRepository
) : ViewModel() {

    private val _favoriteCoins = MutableStateFlow<List<FirestoreCoin>>(emptyList())
    val favoriteCoins: StateFlow<List<FirestoreCoin>> = _favoriteCoins

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun fetchFavoriteCoins() {
        viewModelScope.launch {
            _isLoading.value = true
            _favoriteCoins.value = repository.getFavoriteCoins()
            _isLoading.value = false
        }
    }

    fun removeCoinFromFavorites(coinId: String) {
        viewModelScope.launch {
            repository.removeCoinFromFavorites(coinId)
            fetchFavoriteCoins() // update favorite coins list
        }
    }
}
