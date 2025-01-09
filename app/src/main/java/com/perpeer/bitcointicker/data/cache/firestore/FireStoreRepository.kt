package com.perpeer.bitcointicker.data.cache.firestore

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.perpeer.bitcointicker.data.model.Coin
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

/**
 * Created by Pelşin KAPLAN on 11.01.2025.
 */

class FireStoreRepository @Inject constructor(
    private val db: FirebaseFirestore,
    private val auth: FirebaseAuth
) {

    private val userId: String?
        get() = auth.currentUser?.uid

    private fun getUserFavoritesCollection() = userId?.let {
        db.collection("users").document(it).collection("favorites")
    }

    // this func gets favorite coins from firestore
    suspend fun getFavoriteCoins(): List<Coin> {
        val favoritesCollection = getUserFavoritesCollection() ?: return emptyList()
        return try {
            val snapshot = favoritesCollection.get().await()
            snapshot.documents.mapNotNull { doc ->
                try {
                    doc.toObject(Coin::class.java)
                } catch (e: Exception) {
                    // Hangi döküman hatalı dönüştürülüyor, loglayın
                    println("Error converting document: ${doc.id}, ${e.message}")
                    null
                }
            }
        } catch (e: Exception) {
            // Hata mesajını loglayın
            println("Error fetching favorite coins: ${e.message}")
            emptyList()
        }
    }

    // this func checks the item is favorite or not according to coinId
    suspend fun checkItemIsFavorite(coinId: String): Boolean {
        val favoritesCollection = getUserFavoritesCollection() ?: return false
        return try {
            val document = favoritesCollection.document(coinId).get().await()
            document.exists()
        } catch (e: Exception) {
            false
        }
    }

    // this func adds coin to favorites
    suspend fun addCoinToFavorites(coin: Coin): Boolean {
        val favoritesCollection = getUserFavoritesCollection() ?: return false
        return try {
            favoritesCollection.document(coin.id).set(coin).await()
            true
        } catch (e: Exception) {
            false
        }
    }

    // this func removes coin from favorites
    suspend fun removeCoinFromFavorites(coinId: String): Boolean {
        val favoritesCollection = getUserFavoritesCollection() ?: return false
        return try {
            favoritesCollection.document(coinId).delete().await()
            true
        } catch (e: Exception) {
            false
        }
    }
}
