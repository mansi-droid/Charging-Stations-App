package dev.droid.chargingstationsapp.di.component

import dagger.Component
import dev.droid.chargingstationsapp.di.ActivityScope
import dev.droid.chargingstationsapp.di.module.ActivityModule
import dev.droid.chargingstationsapp.ui.stations.DashboardActivity

@ActivityScope
@Component(dependencies = [ApplicationComponent::class], modules = [ActivityModule::class])
interface ActivityComponent {
    fun inject(activity : DashboardActivity)
}