package com.ewerton.hotmartapplication.screens.locationDetails

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.ewerton.hotmartapplication.base.BaseTest
import com.ewerton.hotmartapplication.di.configureTestAppComponent
import com.ewerton.hotmartapplication.models.AllLocations
import com.ewerton.hotmartapplication.models.LocationsDetailsResult
import com.ewerton.hotmartapplication.platform.LiveDataWrapper
import com.ewerton.hotmartapplication.screens.locationdetails.LocationDetailsUseCase
import com.ewerton.hotmartapplication.screens.locationdetails.LocationDetailsViewModel
import com.ewerton.hotmartapplication.screens.locations.LocationsUseCase
import com.ewerton.hotmartapplication.screens.locations.LocationsViewModel
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
class LocationDetailsViewModelTest: BaseTest() {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    lateinit var mLocationDetailsViewModel: LocationDetailsViewModel
    val mDispatcher = Dispatchers.Unconfined

    @MockK
    lateinit var mLocationDetailsUseCase: LocationDetailsUseCase

    val id = 2
    val mNextValue = "Hangar"
    val mType = "Restaurante"

    @Before
    fun start() {
        super.setUp()
        MockKAnnotations.init(this)
        startKoin { modules(configureTestAppComponent(getMockWebServerUrl())) }
    }

    @Test
    fun test_locations_view_model_data_populates_expected_value() {

        mLocationDetailsViewModel = LocationDetailsViewModel(mDispatcher, mDispatcher, mLocationDetailsUseCase, id)
        val sampleResponse = getJson("location_detail_result.json")
        val jsonObj = Gson().fromJson(sampleResponse, LocationsDetailsResult::class.java)
        coEvery { mLocationDetailsUseCase.processLocationDetailsUseCase(id) } returns jsonObj
        mLocationDetailsViewModel.mLocationDetailsResponse.observeForever { }

        mLocationDetailsViewModel.getLocationDetailsById(id)

        assert(mLocationDetailsViewModel.mLocationDetailsResponse.value != null)
        assert(
            mLocationDetailsViewModel.mLocationDetailsResponse.value!!.responseStatus
                    == LiveDataWrapper.RESPONSESTATUS.SUCCESS
        )
        val testResult =
            mLocationDetailsViewModel.mLocationDetailsResponse.value as LiveDataWrapper<LocationsDetailsResult>
        Assert.assertEquals(testResult.response!!.name, mNextValue)
        Assert.assertEquals(testResult.response!!.type, mType)
    }
}