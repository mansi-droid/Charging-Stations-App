package dev.droid.chargingstationsapp.di.module

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import dagger.Module
import dagger.Provides
import dev.droid.chargingstationsapp.data.repository.StationRepository
import dev.droid.chargingstationsapp.di.ActivityContext
import dev.droid.chargingstationsapp.ui.base.ViewModelProviderFactory
import dev.droid.chargingstationsapp.ui.view.StationViewModel

@Module
class ActivityModule(private val activity : AppCompatActivity) {

    @ActivityContext
    @Provides
    fun provideContext() : Context {
        return activity
    }

    @Provides
    fun provideStationViewModel(
            stationRepository : StationRepository
    ) : StationViewModel {
        return ViewModelProvider(activity,
            ViewModelProviderFactory(StationViewModel::class) {
                StationViewModel(stationRepository)
            })[StationViewModel::class.java]
    }
}