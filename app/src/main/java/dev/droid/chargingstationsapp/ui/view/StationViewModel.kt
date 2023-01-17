package dev.droid.chargingstationsapp.ui.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.droid.chargingstationsapp.data.model.Station
import dev.droid.chargingstationsapp.data.repository.StationRepository
import dev.droid.chargingstationsapp.utils.AppConstant.DISTANCE
import dev.droid.chargingstationsapp.utils.AppConstant.DISTANCEUNIT
import dev.droid.chargingstationsapp.utils.AppConstant.LATITUDE
import dev.droid.chargingstationsapp.utils.AppConstant.LONGITUDE
import dev.droid.chargingstationsapp.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class StationViewModel(private val stationRepository : StationRepository) : ViewModel() {
    private val _stationsList = MutableStateFlow<Resource<List<Station>>>(Resource.loading())

    val stationsList : StateFlow<Resource<List<Station>>> = _stationsList

    init {
        getStations()
    }

    private fun getStations() {
        viewModelScope.launch(Dispatchers.IO) {
            while (isActive) {
                _stationsList.value = Resource.loading()
                stationRepository
                        .getStations(LATITUDE, LONGITUDE, DISTANCE, DISTANCEUNIT)
                        .catch { e ->
                            _stationsList.value = Resource.error(e.toString())
                        }
                        .collect {
                            _stationsList.value = Resource.success(it)
                        }
                delay(30 * 1000)
            }
        }
    }
}