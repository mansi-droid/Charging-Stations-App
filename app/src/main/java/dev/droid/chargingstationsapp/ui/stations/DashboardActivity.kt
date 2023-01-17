package dev.droid.chargingstationsapp.ui.stations

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.maps.android.ktx.addMarker
import dev.droid.chargingstationsapp.R
import dev.droid.chargingstationsapp.StationsApplication
import dev.droid.chargingstationsapp.data.model.Station
import dev.droid.chargingstationsapp.databinding.ActivityDashboardBinding
import dev.droid.chargingstationsapp.databinding.StationDetailLayoutBinding
import dev.droid.chargingstationsapp.di.component.DaggerActivityComponent
import dev.droid.chargingstationsapp.di.module.ActivityModule
import dev.droid.chargingstationsapp.ui.view.StationViewModel
import dev.droid.chargingstationsapp.utils.AppConstant.LATITUDE
import dev.droid.chargingstationsapp.utils.AppConstant.LONGITUDE
import dev.droid.chargingstationsapp.utils.Status
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class DashboardActivity : AppCompatActivity(), GoogleMap.OnMarkerClickListener, OnMapReadyCallback {
    private lateinit var binding : ActivityDashboardBinding

    @Inject
    lateinit var stationViewModel : StationViewModel

    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_dashboard)
        injectDependencies()

        if (StationsApplication.isInternetAvailable(this@DashboardActivity)) {
            Handler(Looper.getMainLooper()).postDelayed({
                binding.ivWelcome.visibility = View.GONE
                binding.mapView.visibility = View.VISIBLE
                val mapFragment = supportFragmentManager
                        .findFragmentById(R.id.mapView) as SupportMapFragment
                mapFragment.getMapAsync(this)
                setupObserver()
            }, 2000)
        } else {
            Toast.makeText(this@DashboardActivity, getString(R.string.no_server_found), Toast.LENGTH_LONG)
                    .show()
        }
    }

    override fun onMapReady(googleMap : GoogleMap) {
        mMap = googleMap
        val location = LatLng(LATITUDE, LONGITUDE)
        mMap?.animateCamera(
            CameraUpdateFactory.newLatLngZoom(
                location, 15F
            )
        )
    }

    private fun setupObserver() {
        lifecycleScope.launch(Dispatchers.Main) {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                stationViewModel.stationsList.collect {
                    when (it.status) {
                        Status.SUCCESS -> {
                            binding.progressBar.visibility = View.GONE
                            it.data?.let { stationsList ->
                                addMarkers(stationsList)
                            }
                        }
                        Status.LOADING -> {
                            binding.progressBar.visibility = View.VISIBLE
                        }
                        Status.ERROR -> {
                            binding.progressBar.visibility = View.GONE
                            Log.e("Error", it.message.toString())
                        }
                    }
                }

            }
        }
    }

    private fun injectDependencies() {
        DaggerActivityComponent
                .builder()
                .applicationComponent((application as StationsApplication).applicationComponent)
                .activityModule(ActivityModule(this))
                .build()
                .inject(this)
    }

    private fun addMarkers(stations : List<Station>) {
        stations.forEach { station ->
            val marker = mMap?.let {
                it.addMarker {
                    title(station.AddressInfo.Title)
                    position(LatLng(station.AddressInfo.Latitude, station.AddressInfo.Longitude))
                    icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                }
            }
            marker?.tag = station
        }
        mMap?.setOnMarkerClickListener(this)
    }

    override fun onMarkerClick(marker : Marker) : Boolean {
        val station = marker.tag as Station
        val dialog = BottomSheetDialog(this)
        val bindingSheet = DataBindingUtil.inflate<StationDetailLayoutBinding>(
            layoutInflater, R.layout.station_detail_layout, null, false
        )
        dialog.setContentView(bindingSheet.root)
        bindingSheet.station = station
        dialog.setCancelable(true)
        dialog.show()
        return false
    }

    companion object {
        var mMap : GoogleMap? = null
    }
}