package dev.droid.chargingstationsapp.di.component

import android.content.Context
import dagger.Component
import dev.droid.chargingstationsapp.StationsApplication
import dev.droid.chargingstationsapp.data.api.OpenChargeMapService
import dev.droid.chargingstationsapp.data.repository.StationRepository
import dev.droid.chargingstationsapp.di.ApplicationContext
import dev.droid.chargingstationsapp.di.module.ApplicationModule
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {

    fun inject(application : StationsApplication)

    @ApplicationContext
    fun getContext() : Context

    fun getOpenChargeMapService() : OpenChargeMapService

    fun getStationRepository() : StationRepository

}