package com.perpeer.bitcointicker.data.work

/**
 * Created by Pelşin KAPLAN on 9.01.2025.
 */
import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.perpeer.bitcointicker.data.cache.firestore.FireStoreRepository
import com.perpeer.bitcointicker.data.repository.BitcoinRepository
import com.perpeer.bitcointicker.utils.NotificationUtils.showPriceChangeNotification
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class PriceCheckWorker @AssistedInject constructor(
    private val bitcoinRepository: BitcoinRepository,
    private val fireStoreRepository: FireStoreRepository,
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        try {
            val coinId = inputData.getString("favorite_coin_id") ?: return Result.failure()

            val coinDetail = bitcoinRepository.getCoinDetail(coinId)
            val favoriteCoins = fireStoreRepository.getFavoriteCoins()
            val targetCoin = favoriteCoins.find { it.id == coinId }

            coinDetail.market_data.let { marketData ->
                val currentPrice = marketData.current_price["usd"]

                if (currentPrice.toString() != targetCoin?.price) {
                    if (targetCoin != null) {
                        showPriceChangeNotification(
                            applicationContext,
                            targetCoin,
                            try {
                                if (targetCoin.price.toDouble() < currentPrice!!) true else false
                            } catch (e: Exception) {
                                false
                            }
                        )
                    }
                }
            }

            return Result.success()
        } catch (e: Exception) {
            Log.e("PriceCheckWorker", e.message ?: "Error")
            return Result.failure()
        }
    }
}





