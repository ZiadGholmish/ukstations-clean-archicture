package com.citymapper.app.presentation.views.nearbystations

import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.citymapper.app.R
import com.citymapper.app.app.CitymapperApp
import com.citymapper.app.dagger.ViewModelFactory
import com.citymapper.app.data.remote.models.stops.StopPoint
import com.citymapper.app.util.toClusterItem
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.Marker
import com.google.maps.android.clustering.ClusterManager
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_nearby_stations.*
import javax.inject.Inject
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.CameraUpdateFactory


class NearbyStationsActivity : AppCompatActivity(), NearbyStationsController, OnMapReadyCallback,
        GoogleMap.OnInfoWindowClickListener, GoogleMap.InfoWindowAdapter {

    @Inject
    lateinit var mPresenter: NearbyStationsPresenter

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var mMap: GoogleMap

    private lateinit var mClusterManager: ClusterManager<StopPointClusterMapItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nearby_stations)
        setupPresenterAndVM()
        setupGoogleMap()
    }

    private fun setupPresenterAndVM() {
        (application as CitymapperApp).appComponent.inject(this)
        mPresenter.attachView(this)
        val nearbyStationsVM = ViewModelProviders.of(this,
                viewModelFactory).get(NearbyStationsVM::class.java)
        mPresenter.initPresenter(nearbyStationsVM)
    }

    /**
     *  Obtain the SupportMapFragment and get notified when
     *  the map is ready to be used.
     */
    private fun setupGoogleMap() {
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        setUpCluster()
        mMap.setOnInfoWindowClickListener(this)
        mMap.setInfoWindowAdapter(this)
    }


    override fun onInfoWindowClick(p0: Marker?) {
    }

    override fun getInfoWindow(p0: Marker?): View {
        val infoView = layoutInflater
                .inflate(R.layout.stop_point_arrivals_times_layout, null)
        return infoView
    }

    override fun getInfoContents(marker: Marker): View? {
        return null
    }

    private fun setUpCluster() {
        mClusterManager = ClusterManager(this, mMap)
        mMap.setOnCameraIdleListener(mClusterManager)
        mMap.setOnMarkerClickListener(mClusterManager)
    }

    override fun showStopPoints(stopPoints: List<StopPoint>) {
        mClusterManager.addItems(stopPoints.map { it.toClusterItem() })
    }

    override fun zoomToStations(stopPoint: StopPoint?) {
        stopPoint?.let {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(it.lat, it.lon), 10f))
        }
    }

    override fun showNoPointsAvailable() {
        Toast.makeText(this, getString(R.string.no_points_available), Toast.LENGTH_LONG).show()
    }

    override fun showFetchingError(errorMessage: String) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
    }

    override fun showMessage(resId: Int) {
        Toast.makeText(this, getString(resId), Toast.LENGTH_LONG).show()
    }

    override fun showLoading() {
        loadingView.show()
    }

    override fun hideLoading() {
        loadingView.hide()
    }
}
