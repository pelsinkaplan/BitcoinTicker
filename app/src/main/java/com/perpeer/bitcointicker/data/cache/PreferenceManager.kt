package com.perpeer.bitcointicker.data.cache

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.perpeer.bitcointicker.data.cache.SharedPreferenceKeys.ALL_COINS
import com.perpeer.bitcointicker.data.cache.SharedPreferenceKeys.COINS_SAVE_TIME
import com.perpeer.bitcointicker.data.cache.SharedPreferenceKeys.FAVORITE_COINS
import com.perpeer.bitcointicker.data.cache.SharedPreferenceKeys.PREFS
import com.perpeer.bitcointicker.data.model.Coin
import com.perpeer.bitcointicker.data.model.FavoriteCoins
import java.time.LocalDate
import java.time.LocalDateTime
import javax.inject.Inject

/**
 * Created by Pelşin KAPLAN on 6.01.2025.
 */
class PreferenceManager @Inject constructor(val context: Context) {

    private val prefs: SharedPreferences by lazy {
        val masterKey = MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

        EncryptedSharedPreferences.create(
            context,
            PREFS,
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }


    fun getDataWithKey(key: String): Any? {
        return prefs.get(key)
    }

    fun setDataWithKey(key: String, value: Any) {
        prefs.set(key, value)
    }

    var coinsSaveTime:LocalDateTime?
        get() = prefs.get(COINS_SAVE_TIME)
        set(value) {
            prefs.set(COINS_SAVE_TIME, value)
        }

    var allCoins: FavoriteCoins?
        get() = prefs.get(ALL_COINS)
        set(value) {
            prefs.set(ALL_COINS, value)
        }

    var favoriteCoins: FavoriteCoins?
        get() = prefs.get(FAVORITE_COINS)
        set(value) {
            prefs.set(FAVORITE_COINS, value)
        }

    fun addNewFavoriteCoin(coin: Coin) {
        if (favoriteCoins?.favoriteCoinList?.none { it.id == coin.id } == true) {
            val tempList = favoriteCoins?.favoriteCoinList
            tempList?.add(coin)
            favoriteCoins = tempList?.let { FavoriteCoins(it) }
        }
    }

    fun deleteFavoriteCoinWithId(coinId: String) {
        if (favoriteCoins?.favoriteCoinList?.any { it.id == coinId } == true) {
            val tempList = favoriteCoins?.favoriteCoinList
            tempList?.removeIf { it.id == coinId }
            favoriteCoins = tempList?.let { FavoriteCoins(it) }
        }
    }

    companion object {
        val preferenceManager: PreferenceManager by lazy {
            PreferenceManager(applicationContext)
        }
        private lateinit var applicationContext: Context

        fun initialize(context: Context) {
            applicationContext = context
        }
    }
}