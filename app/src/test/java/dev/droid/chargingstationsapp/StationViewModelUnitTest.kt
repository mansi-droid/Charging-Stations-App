package dev.droid.chargingstationsapp

import app.cash.turbine.test
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dev.droid.chargingstationsapp.data.api.OpenChargeMapService
import dev.droid.chargingstationsapp.data.model.Station
import dev.droid.chargingstationsapp.data.repository.StationRepository
import dev.droid.chargingstationsapp.ui.view.StationViewModel
import dev.droid.chargingstationsapp.utils.AppConstant
import dev.droid.chargingstationsapp.utils.Status
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.doReturn
import org.mockito.MockitoAnnotations
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalCoroutinesApi::class) class StationViewModelUnitTest {
    @Mock
    lateinit var mockOpenChargeMapService : OpenChargeMapService

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @OptIn(ExperimentalTime::class)
    @Test
    fun test_view_model_fetch_stations_returns_list_of_stations() = runTest {
        doReturn(mockStationsList()).`when`(mockOpenChargeMapService)
                .getStations(AppConstant.LATITUDE, AppConstant.LONGITUDE, AppConstant.DISTANCE, AppConstant.DISTANCEUNIT)

        val stationViewModel = StationViewModel(StationRepository(mockOpenChargeMapService))
        val stateFlow = stationViewModel.stationsList
        stateFlow.test {
            val firstItem = awaitItem()
            assertEquals(Status.LOADING, firstItem.status)
            assertEquals(true, firstItem.data.isNullOrEmpty())
            val secondItem = awaitItem()
            assertEquals(Status.SUCCESS, secondItem.status)
            assertEquals(false, secondItem.data.isNullOrEmpty())
            cancel()
        }
    }

    private fun mockStationsList() : List<Station> {
        val inputStream = ClassLoader.getSystemResource("station.json").readText()
        val station : Station = Gson().fromJson(inputStream, object : TypeToken<Station>() {}.type)
        val list = arrayListOf<Station>()
        list.add(station)
        return list
    }
}
