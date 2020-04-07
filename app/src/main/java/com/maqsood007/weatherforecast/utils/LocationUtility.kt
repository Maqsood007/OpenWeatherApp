package com.maqsood007.weatherforecast.utils

import android.Manifest
import android.content.IntentSender
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.ActivityCompat
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.*
import com.maqsood007.weatherforecast.utils.LocationUtility.Companion.TAG

/**
 * Created by Muhammad Maqsood on 07/04/2020.
 */

/**
 * The callback invoked when
 * [com.google.android.gms.location.SettingsApi.checkLocationSettings] is called. Examines the
 * [com.google.android.gms.location.LocationSettingsResult] object and determines if
 * location settings are adequate. If they are not, begins the process of presenting a location
 * settings dialog to the user.
 */

class LocationUtility(private val mainActivity: MainActivity) {



    public var mGoogleApiClient: GoogleApiClient? = null
    protected var mLocationRequest: LocationRequest? = null
    protected var mLocationSettingsRequest: LocationSettingsRequest? = null
    public var mRequestingLocationUpdates: Boolean? = null


    fun startLocationClient() {

        // Kick off the process of building the GoogleApiClient, LocationRequest, and
        // LocationSettingsRequest objects.
        buildGoogleApiClient()
        createLocationRequest()
        buildLocationSettingsRequest()
        checkLocationSettings()
    }


    /**
     * Builds a GoogleApiClient. Uses the `#addApi` method to request the
     * LocationServices API.
     */
    @Synchronized
    protected fun buildGoogleApiClient() {
        Log.i(
            TAG,
            "Building GoogleApiClient"
        )
        mGoogleApiClient = GoogleApiClient.Builder(mainActivity)
            .addConnectionCallbacks(mainActivity)
            .addOnConnectionFailedListener(mainActivity)
            .addApi(LocationServices.API)
            .build()
    }


    protected fun createLocationRequest() {
        mLocationRequest = LocationRequest()

        // Sets the desired interval for active location updates. This interval is
        // inexact. You may not receive updates at all if no location sources are available, or
        // you may receive them slower than requested. You may also receive updates faster than
        // requested if other applications are requesting location at a faster interval.
        mLocationRequest!!.interval = UPDATE_INTERVAL_IN_MILLISECONDS

        // Sets the fastest rate for active location updates. This interval is exact, and your
        // application will never receive updates faster than this value.
        mLocationRequest!!.fastestInterval = FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS
        mLocationRequest!!.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
    }


    protected fun buildLocationSettingsRequest() {
        val builder = LocationSettingsRequest.Builder()
        builder.addLocationRequest(mLocationRequest!!)
        mLocationSettingsRequest = builder.build()
    }

    /**
     * Check if the device's location settings are adequate for the app's needs using the
     * [com.google.android.gms.location.SettingsApi.checkLocationSettings] method, with the results provided through a `PendingResult`.
     */
    protected fun checkLocationSettings() {
        val result =
            LocationServices.SettingsApi.checkLocationSettings(
                mGoogleApiClient,
                mLocationSettingsRequest
            )
        result.setResultCallback(mainActivity)
    }


    /**
     * Requests location updates from the FusedLocationApi.
     */
    fun startLocationUpdates() {

        if (checkPermissions()) {

            LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient,
                mLocationRequest,
                mainActivity
            ).setResultCallback {
                mRequestingLocationUpdates = true
            }
        } else {
            requestPermissions()
        }


    }


    private fun checkPermissions(): Boolean {
        if (ActivityCompat.checkSelfPermission(
                mainActivity,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                mainActivity,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            return true
        }
        return false
    }

     fun requestPermissions() {
        ActivityCompat.requestPermissions(
            mainActivity,
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            ),
            REQUEST_CHECK_SETTINGS
        )
    }

    /**
     * Removes location updates from the FusedLocationApi.
     */
     fun stopLocationUpdates() {
        // It is a good practice to remove location requests when the activity is in a paused or
        // stopped state. Doing so helps battery performance and is especially
        // recommended in applications that request frequent location updates.
        LocationServices.FusedLocationApi.removeLocationUpdates(
            mGoogleApiClient,
            mainActivity
        ).setResultCallback {
            mRequestingLocationUpdates = false
        }
    }


    companion object {
        protected const val TAG = "MainActivity"

        /**
         * Constant used in the location settings dialog.
         */
        const val REQUEST_CHECK_SETTINGS = 0x1

        /**
         * The desired interval for location updates. Inexact. Updates may be more or less frequent.
         */
        const val UPDATE_INTERVAL_IN_MILLISECONDS: Long = 10000

        /**
         * The fastest rate for active location updates. Exact. Updates will never be more frequent
         * than this value.
         */
        const val FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS =
            UPDATE_INTERVAL_IN_MILLISECONDS / 2

    }

}