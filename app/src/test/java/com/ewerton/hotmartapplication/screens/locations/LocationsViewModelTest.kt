package com.ewerton.hotmartapplication.screens.locations

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.ewerton.hotmartapplication.base.BaseTest
import com.ewerton.hotmartapplication.di.configureTestAppComponent
import com.ewerton.hotmartapplication.models.AllLocations
import com.ewerton.hotmartapplication.platform.LiveDataWrapper
import com.google.gson.Gson
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.koin.core.context.startKoin

@RunWith(JUnit4::class)
class LocationsViewModelTest : BaseTest(){

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    lateinit var mLocationsViewModel: LocationsViewModel
    val mDispatcher = Dispatchers.Unconfined

    @MockK
    lateinit var mLocationsUseCase: LocationsUseCase

    val mNextValue = "Café Escritório"

    @Before
    fun start() {
        super.setUp()
        MockKAnnotations.init(this)
        startKoin { modules(configureTestAppComponent(getMockWebServerUrl())) }
    }

    @Test
    fun test_locations_view_model_data_populates_expected_value() {

        mLocationsViewModel = LocationsViewModel(mDispatcher, mDispatcher, mLocationsUseCase)
        val sampleResponse = getJson("locations_result.json")
        val jsonObj = Gson().fromJson(sampleResponse, AllLocations::class.java)
        coEvery { mLocationsUseCase.processLocationsUseCase() } returns jsonObj
        mLocationsViewModel.mLocationsResponse.observeForever { }

        mLocationsViewModel.getLocations()

        assert(mLocationsViewModel.mLocationsResponse.value != null)
        assert(
            mLocationsViewModel.mLocationsResponse.value!!.responseStatus
                    == LiveDataWrapper.RESPONSESTATUS.SUCCESS
        )
        val testResult =
            mLocationsViewModel.mLocationsResponse.value as LiveDataWrapper<AllLocations>
        Assert.assertEquals(testResult.response!!.listLocations[0].name, mNextValue)
    }
}