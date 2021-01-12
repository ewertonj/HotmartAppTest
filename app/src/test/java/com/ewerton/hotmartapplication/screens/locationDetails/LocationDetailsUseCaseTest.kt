package com.ewerton.hotmartapplication.screens.locationDetails

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.ewerton.hotmartapplication.base.BaseTest
import com.ewerton.hotmartapplication.di.configureTestAppComponent
import com.ewerton.hotmartapplication.screens.locationdetails.LocationDetailsUseCase
import com.ewerton.hotmartapplication.screens.locations.LocationsUseCase
import junit.framework.Assert
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.koin.core.context.startKoin
import java.net.HttpURLConnection

@RunWith(JUnit4::class)
class LocationDetailsUseCaseTest: BaseTest() {
    private lateinit var mLocationDetailsUseCase: LocationDetailsUseCase

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()
    val id = 2
    val mNextValue = "Hangar"
    val mScheduleSaturdayClose = "1h"


    @Before
    fun start(){
        super.setUp()
        startKoin{ modules(configureTestAppComponent(getMockWebServerUrl()))}
    }

    @Test
    fun test_location_details_use_case_returns_expected_value()= runBlocking{

        mockNetworkResponseWithFileContent("location_detail_result.json", HttpURLConnection.HTTP_OK)
        mLocationDetailsUseCase = LocationDetailsUseCase()

        val dataReceived = mLocationDetailsUseCase.processLocationDetailsUseCase(id)

        assertNotNull(dataReceived)
        assertEquals(dataReceived.name, mNextValue)
        Assert.assertEquals(dataReceived.schedule.saturday!!.close, mScheduleSaturdayClose)
    }
}