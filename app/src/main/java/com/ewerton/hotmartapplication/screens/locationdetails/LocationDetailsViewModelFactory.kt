package com.ewerton.hotmartapplication.screens.locationdetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.CoroutineDispatcher

class LocationDetailsViewModelFactory(
    private val mainDispather: CoroutineDispatcher,
    private val ioDispatcher: CoroutineDispatcher,
    private val useCase: LocationDetailsUseCase,
    private val id: Int?

) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LocationDetailsViewModel::class.java)) {
            return LocationDetailsViewModel(mainDispather, ioDispatcher, useCase, id) as T
        }
        throw IllegalArgumentException("Unknow view model class")
    }
}