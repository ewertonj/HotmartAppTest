package com.ewerton.hotmartapplication.di

import com.ewerton.hotmartapplication.repository.LocationsRepository
import org.koin.dsl.module

val RepoDependency = module {
    factory {
        LocationsRepository()
    }
}