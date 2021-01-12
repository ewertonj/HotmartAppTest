package com.ewerton.hotmartapplication.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.ewerton.hotmartapplication.base.BaseTest
import com.ewerton.hotmartapplication.di.configureTestAppComponent
import com.ewerton.hotmartapplication.network.LocationsAPIService
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockWebServer
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.koin.core.context.startKoin
import org.koin.test.inject
import java.net.HttpURLConnection

@RunWith(JUnit4::class)
class LocationsRepositoryTest: BaseTest() {

    private lateinit var mRepo: LocationsRepository

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()
    val mNextValue = "Café Escritório"
    val mNextValueDetail = "Hangar"
    val id = 2
    val mScheduleSaturdayClose = "1h"

    @Before
    fun start(){
        super.setUp()
        startKoin{ modules(configureTestAppComponent(getMockWebServerUrl()))}
    }

    @Test
    fun test_locations_repo_retrieves_expected_all_locations_data() =  runBlocking<Unit>{

        mockNetworkResponseWithFileContent("locations_result.json", HttpURLConnection.HTTP_OK)
        mRepo = LocationsRepository()

        val dataReceived = mRepo.getLocationsData()

        assertNotNull(dataReceived)
        assertEquals(dataReceived.listLocations[0].name, mNextValue)
    }

    @Test
    fun test_locations_repo_retrieves_expected_location_detail_data() =  runBlocking<Unit>{

        mockNetworkResponseWithFileContent("location_detail_result.json", HttpURLConnection.HTTP_OK)
        mRepo = LocationsRepository()

        val dataReceived = mRepo.getLocationDetailsData(id)

        assertNotNull(dataReceived)
        assertEquals(dataReceived.name, mNextValueDetail)
        assertEquals(dataReceived.schedule.saturday!!.close, mScheduleSaturdayClose)
    }
}