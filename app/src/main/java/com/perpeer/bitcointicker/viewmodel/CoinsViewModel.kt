package com.perpeer.bitcointicker.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.perpeer.bitcointicker.data.cache.PreferenceManager.Companion.preferenceManager
import com.perpeer.bitcointicker.data.cache.firestore.FirebaseAuthRepository
import com.perpeer.bitcointicker.data.model.AllCoins
import com.perpeer.bitcointicker.data.model.Coin
import com.perpeer.bitcointicker.data.repository.BitcoinRepository
import com.perpeer.bitcointicker.utils.FilterUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Pelşin KAPLAN on 6.01.2025.
 */

@HiltViewModel
class CoinsViewModel @Inject constructor(
    private val repository: BitcoinRepository,
    private val firebaseRepository: FirebaseAuthRepository
) : ViewModel() {

    private val _coinList = MutableStateFlow<List<Coin>>(emptyList())
    val coinList: StateFlow<List<Coin>> = _coinList

    private val _filteredCoinList = MutableStateFlow<List<Coin>>(emptyList())
    val filteredCoinList: StateFlow<List<Coin>> = _filteredCoinList

    fun fetchCoinList(fetchFromAPI: Boolean) {
        viewModelScope.launch {
            try {
                val coins =
                    if (fetchFromAPI) repository.getCoinList() else preferenceManager.allCoins?.allCoinList
                        ?: emptyList()
                preferenceManager.allCoins = AllCoins(coins)
                _coinList.value = coins
                _filteredCoinList.value = coins
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
        _filteredCoinList.value = FilterUtils.filterCoins(query, _coinList.value)
    }

    fun signOut() {
        firebaseRepository.signOut()
    }
}

