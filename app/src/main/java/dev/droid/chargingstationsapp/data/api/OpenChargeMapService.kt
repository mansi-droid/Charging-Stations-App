package dev.droid.chargingstationsapp.data.api

import dev.droid.chargingstationsapp.data.model.Station
import dev.droid.chargingstationsapp.utils.AppConstant.API_KEY
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface OpenChargeMapService {
    @Headers("X-Api-Key: $API_KEY")
    @GET("poi")
    suspend fun getStations(
            @Query("latitude") latitude : Number,
            @Query("longitude") longitude : Number,
            @Query("distance") distance : Number,
            @Query("distanceunit") distanceunit : String,
    ) : List<Station>
}