package com.perpeer.bitcointicker.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.github.mikephil.charting.data.CandleEntry
import com.perpeer.bitcointicker.data.cache.firestore.FireStoreRepository
import com.perpeer.bitcointicker.data.model.CoinDetail
import com.perpeer.bitcointicker.data.model.FirestoreCoin
import com.perpeer.bitcointicker.data.repository.BitcoinRepository
import com.perpeer.bitcointicker.data.work.PriceCheckWorker
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * Created by Pelşin KAPLAN on 7.01.2025.
 */

@HiltViewModel
class CoinDetailViewModel @Inject constructor(
    private val repository: BitcoinRepository,
    private val fireStoreRepository: FireStoreRepository,
) : ViewModel() {
    private val _coinDetail = MutableStateFlow<CoinDetail?>(null)
    val coinDetail: StateFlow<CoinDetail?> = _coinDetail

    private val _isFavorite = MutableStateFlow(false)
    val isFavorite: StateFlow<Boolean> get() = _isFavorite

    private var refreshJob: Job? = null

    fun fetchCoinDetail(coinId: String) {
        viewModelScope.launch {
            try {
                val detail = repository.getCoinDetail(coinId)
                _coinDetail.value = detail
            } catch (e: Exception) {
                e.printStackTrace()
                _coinDetail.value = null
            }
        }
    }

    fun startAutoRefresh(coinId: String, intervalSeconds: Long) {
        stopAutoRefresh() // Eski işi iptal et
        refreshJob = viewModelScope.launch {
            while (isActive) {
                fetchCoinDetail(coinId)
                delay(intervalSeconds * 1000 * 60)
            }
        }
    }

    fun stopAutoRefresh() {
        refreshJob?.cancel()
        refreshJob = null
    }

    fun scheduleWork(context: Context, coinId: String, intervalMinutes: Long) {
        val workRequest = PeriodicWorkRequestBuilder<PriceCheckWorker>(
            intervalMinutes, TimeUnit.MINUTES
        )
            .setInputData(
                workDataOf(
                    "favorite_coin_id" to coinId
                )
            )
            .build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            coinId, // Coin'e özgü bir iş ismi
            ExistingPeriodicWorkPolicy.REPLACE, // Önceki iş varsa değiştir
            workRequest
        )
    }

    fun checkItemIsFavorite(coinId: String) {
        viewModelScope.launch {
            val result = fireStoreRepository.checkItemIsFavorite(coinId)
            _isFavorite.value = result
        }
    }

    fun toggleFavorite(context: Context, coin: FirestoreCoin) {
        viewModelScope.launch {
            if (_isFavorite.value) {
                WorkManager.getInstance(context).cancelUniqueWork(coin.id)
                fireStoreRepository.removeCoinFromFavorites(coin.id)
            } else {
                scheduleWork(context, coin.id, coin.timeInterval)
                fireStoreRepository.addCoinToFavorites(coin)
            }
            _isFavorite.value = !_isFavorite.value
        }
    }

    private val _marketData = MutableStateFlow<List<Pair<Double, Double>>>(emptyList())
    val marketData: StateFlow<List<Pair<Double, Double>>> = _marketData

    private val _candleChartData = MutableStateFlow<List<CandleStick>>(emptyList())
    val candleChartData: StateFlow<List<CandleStick>> = _candleChartData

    fun fetchMarketDataDaily(coinId: String, days: Int) {
        viewModelScope.launch {
            val data = repository.fetchCoinMarketDataDaily(coinId, days)
            _marketData.value = data
            _candleChartData.value = createCandleData(data, (days * 24 * 60 * 60 * 1000).toLong())
        }
    }

    fun fetchMarketDataHourly(coinId: String, hours: Int) {
        viewModelScope.launch {
            val data = repository.getCoinMarketDataHourly(coinId)
            _marketData.value = data
            _candleChartData.value = createCandleData(data, (hours * 60 * 60 * 1000).toLong())
        }
    }

    fun createCandleData(prices: List<Pair<Double, Double>>, interval: Long): List<CandleStick> {
        return prices.groupBy { it.first / interval }.map { (_, group) ->
            val sortedGroup = group.sortedBy { it.first }
            CandleStick(
                open = sortedGroup.first().second.toFloat(),
                close = sortedGroup.last().second.toFloat(),
                high = sortedGroup.maxOf { it.second }.toFloat(),
                low = sortedGroup.minOf { it.second }.toFloat(),
                timestamp = sortedGroup.first().first.toLong()
            )
        }
    }


    fun generateCandleData(
        data: List<Pair<Float, Float>>,
        intervalMillis: Long
    ): List<CandleStick> {
        val sortedData = data.sortedBy { it.first }
        val groupedData = sortedData.groupBy { it.first / intervalMillis }

        val candleData = groupedData.mapNotNull { (_, group) ->
            if (group.isNotEmpty()) {
                val open = group.first().second
                val close = group.last().second
                val high = group.maxOf { it.second }
                val low = group.minOf { it.second }
                val timestamp = group.first().first // Grup içerisindeki ilk elemanın zaman damgası
                CandleStick(open, close, high, low, timestamp.toLong())
            } else {
                null
            }
        }

        val candleEntries = mutableListOf<CandleEntry>()
        candleData.forEachIndexed { index, data ->
            candleEntries.add(
                CandleEntry(
                    index.toFloat(),
                    data.high,
                    data.low,
                    data.open,
                    data.close
                )
            )
        }
        return candleData
    }

    data class CandleStick(
        val open: Float,
        val close: Float,
        val high: Float,
        val low: Float,
        val timestamp: Long // Eklenen zaman damgası
    )


}
