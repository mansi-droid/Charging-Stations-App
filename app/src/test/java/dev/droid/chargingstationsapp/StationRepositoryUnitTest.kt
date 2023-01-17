package dev.droid.chargingstationsapp

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dev.droid.chargingstationsapp.data.api.OpenChargeMapService
import dev.droid.chargingstationsapp.data.model.Station
import dev.droid.chargingstationsapp.data.repository.StationRepository
import dev.droid.chargingstationsapp.utils.AppConstant
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class StationRepositoryUnitTest {

    @Mock
    lateinit var mockOpenChargeMapService : OpenChargeMapService

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun test_stations_repository_get_stations_returns_list_of_stations_as_flow() = runTest {
        Mockito.`when`(
            mockOpenChargeMapService.getStations(
                AppConstant.LATITUDE, AppConstant.LONGITUDE, AppConstant.DISTANCE, AppConstant.DISTANCEUNIT
            )
        ).thenReturn(
            mockStationsList()
        )
        val stationRepository = StationRepository(mockOpenChargeMapService)
        val res = stationRepository.getStations(AppConstant.LATITUDE, AppConstant.LONGITUDE, AppConstant.DISTANCE, AppConstant.DISTANCEUNIT)

        res.collect {
            MatcherAssert.assertThat(it, CoreMatchers.`is`(mockStationsList()))
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