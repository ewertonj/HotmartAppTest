package com.ewerton.hotmartapplication.di

import com.ewerton.hotmartapplication.screens.locations.LocationsUseCase
import com.ewerton.hotmartapplication.screens.locationdetails.LocationDetailsUseCase
import org.koin.dsl.module

val UseCaseDependency = module {
    factory {
        LocationsUseCase()
    }
    factory {
        LocationDetailsUseCase()
    }
}