package com.perpeer.bitcointicker.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.perpeer.bitcointicker.data.cache.firestore.FireStoreRepository
import com.perpeer.bitcointicker.data.network.ApiService
import com.perpeer.bitcointicker.data.repository.BitcoinRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by Pelşin KAPLAN on 9.01.2025.
 */
@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideBitcoinRepository(
        apiService: ApiService
    ): BitcoinRepository {
        return BitcoinRepository(apiService)
    }

    @Provides
    @Singleton
    fun provideFireStoreRepository(
        db: FirebaseFirestore,
        auth: FirebaseAuth
    ): FireStoreRepository {
        return FireStoreRepository(db, auth)
    }

}
