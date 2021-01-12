package com.ewerton.hotmartapplication.screens.locations

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.CoroutineDispatcher

class LocationsViewModelFactory(
    private val mainDispather: CoroutineDispatcher,
    private val ioDispatcher: CoroutineDispatcher,
    private val useCase: LocationsUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LocationsViewModel::class.java)) {
            return LocationsViewModel(mainDispather, ioDispatcher, useCase) as T
        }
        throw IllegalArgumentException("Unknow view model class")
    }
}