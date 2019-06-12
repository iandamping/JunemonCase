package com.junemon.junemoncase.api

import com.junemon.junemoncase.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 *
Created by Ian Damping on 26/04/2019.
Github = https://github.com/iandamping
 */
object NetworkConfig {
    const val GET_PROVINCE = "province"
    const val GET_CITY = "city"
    const val GET_COST = "cost"
    private const val BASE_RAJA_ONGKIR_URL = "https://api.rajaongkir.com/starter/"
    private const val API_KEY = "4590d8d407d79a2a789088ee8bf53847"

    fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_RAJA_ONGKIR_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(getOkHttpClient(API_KEY))
            .build()
    }

    private fun getOkHttpClient(header: String): OkHttpClient {
        val timeOut = 60L
        return OkHttpClient.Builder()
            .readTimeout(timeOut, TimeUnit.SECONDS)
            .connectTimeout(timeOut, TimeUnit.SECONDS)
            .writeTimeout(timeOut, TimeUnit.SECONDS)
            .addInterceptor(getInterceptor())
            .addInterceptor { chain ->
                val ongoing = chain.request().newBuilder()
                ongoing.addHeader("Content-Type", "application/json")
                ongoing.addHeader("key", header)
                chain.proceed(ongoing.build())
            }
            .build()
    }

    private fun getInterceptor(): Interceptor {
        return HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        }
    }


}