package com.ewerton.hotmartapplication.screens.locations

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.ewerton.hotmartapplication.base.BaseTest
import com.ewerton.hotmartapplication.di.configureTestAppComponent
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.koin.core.context.startKoin
import java.net.HttpURLConnection

@RunWith(JUnit4::class)
class LocationsUseCaseTest: BaseTest() {

    private lateinit var mLocationUseCase: LocationsUseCase

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    val mNextValue = "Café Escritório"

    @Before
    fun start(){
        super.setUp()
        startKoin{ modules(configureTestAppComponent(getMockWebServerUrl()))}
    }

    @Test
    fun test_locations_use_case_returns_expected_value()= runBlocking{

        mockNetworkResponseWithFileContent("locations_result.json", HttpURLConnection.HTTP_OK)
        mLocationUseCase = LocationsUseCase()

        val dataReceived = mLocationUseCase.processLocationsUseCase()

        assertNotNull(dataReceived)
        assertEquals(dataReceived.listLocations[0].name, mNextValue)
    }
}