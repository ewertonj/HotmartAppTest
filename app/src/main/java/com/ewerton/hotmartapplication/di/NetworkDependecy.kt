package com.ewerton.hotmartapplication.di

import com.ewerton.hotmartapplication.network.LocationsAPIService
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

const val TIME_OUT : Long = 120

val NetworkDependency = module {

    single {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BASIC
        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
            .readTimeout(TIME_OUT, TimeUnit.SECONDS)
            .addInterceptor(logging)
            .build()
        Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .baseUrl("http://hotmart-mobile-app.herokuapp.com/").build()
    }
    factory{ get<Retrofit>().create(LocationsAPIService::class.java) }
}