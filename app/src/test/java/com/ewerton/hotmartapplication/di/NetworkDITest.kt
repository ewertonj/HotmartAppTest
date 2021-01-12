package com.ewerton.hotmartapplication.di

import com.ewerton.hotmartapplication.network.LocationsAPIService
import com.google.gson.GsonBuilder
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


fun configureNetworkModuleForTest(baseApi: String)
        = module{
    single {
        Retrofit.Builder()
            .baseUrl(baseApi)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
    }
    factory{ get<Retrofit>().create(LocationsAPIService::class.java) }
}