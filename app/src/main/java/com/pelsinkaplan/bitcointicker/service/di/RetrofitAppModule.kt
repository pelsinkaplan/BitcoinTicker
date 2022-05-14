package com.pelsinkaplan.bitcointicker.service.di

import com.pelsinkaplan.bitcointicker.service.utils.Constants.BASE_URL
import com.pelsinkaplan.bitcointicker.service.network.RetrofitAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * Created by Pelşin KAPLAN on 13.05.2022.
 */

@Module
@InstallIn(SingletonComponent::class)
object RetrofitAppModule {

    @Provides
    fun provideOkhttp(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(5, TimeUnit.SECONDS)
            .writeTimeout(5, TimeUnit.SECONDS)
            .readTimeout(5, TimeUnit.SECONDS)
            .build()
    }

    @Singleton
    @Provides
    fun injectRetrofitAPI(): RetrofitAPI {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .client(provideOkhttp())
            .baseUrl(BASE_URL).build().create(RetrofitAPI::class.java)
    }
}