package dev.droid.chargingstationsapp.data.repository

import dev.droid.chargingstationsapp.data.api.OpenChargeMapService
import dev.droid.chargingstationsapp.data.model.Station
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StationRepository @Inject constructor(private val openChargeMapService : OpenChargeMapService) {

    suspend fun getStations(
            latitude : Number, longitude : Number, distance : Number, distanceunit : String
    ) : Flow<List<Station>> {
        return flow {
            emit(openChargeMapService.getStations(latitude, longitude, distance, distanceunit))
        }.map {
            it
        }
    }
}
