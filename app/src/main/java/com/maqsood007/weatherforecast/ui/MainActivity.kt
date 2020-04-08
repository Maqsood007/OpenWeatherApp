package com.maqsood007.weatherforecast.ui

import android.app.Activity
import android.content.Intent
import android.content.IntentSender.SendIntentException
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener
import com.google.android.gms.common.api.ResultCallback
import com.google.android.gms.location.*
import com.test.nyt_most_viewed.ui.base.BaseActivity
import com.maqsood007.weatherforecast.R
import com.maqsood007.weatherforecast.utils.LocationUtility
import com.maqsood007.weatherforecast.utils.LocationUtility.Companion.REQUEST_CHECK_SETTINGS


class MainActivity : BaseActivity(),
    ConnectionCallbacks, OnConnectionFailedListener,
    LocationListener,
    ResultCallback<LocationSettingsResult> {


    protected var mCurrentLocation: MutableLiveData<Location?>? = null


    protected val TAG = "MainActivity"


    private var locationUtility: LocationUtility? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)


        val navController: NavController = findNavController(R.id.fragmentContainer)

        setupActionBarWithNavController(navController)


        locationUtility =
            LocationUtility(this)
        locationUtility?.startLocationClient()
        locationUtility?.mRequestingLocationUpdates = false

    }


    // Location dialog result call backs.
    override fun onResult(locationSettingsResult: LocationSettingsResult) {
        val status = locationSettingsResult.status
        when (status.statusCode) {
            LocationSettingsStatusCodes.SUCCESS -> {
                Log.i(
                    TAG,
                    "All location settings are satisfied."
                )
                locationUtility?.startLocationUpdates()
            }
            LocationSettingsStatusCodes.RESOLUTION_REQUIRED -> {
                Log.i(
                    TAG,
                    "Location settings are not satisfied. Show the user a dialog to" +
                            "upgrade location settings "
                )
                try {
                    // Show the dialog by calling startResolutionForResult(), and check the result
                    // in onActivityResult().
                    status.startResolutionForResult(
                        this@MainActivity,
                        REQUEST_CHECK_SETTINGS
                    )
                } catch (e: SendIntentException) {
                    Log.i(
                        TAG,
                        "PendingIntent unable to execute request."
                    )
                }
            }
            LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE -> Log.i(
                TAG,
                "Location settings are inadequate, and cannot be fixed here. Dialog " +
                        "not created."
            )
        }
    }

    protected override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REQUEST_CHECK_SETTINGS -> when (resultCode) {
                Activity.RESULT_OK -> {
                    Log.i(
                        TAG,
                        "User agreed to make required location settings changes."
                    )
                    locationUtility?.startLocationUpdates()
                }
                Activity.RESULT_CANCELED -> Log.i(
                    TAG,
                    "User chose not to make required location settings changes."
                )
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (requestCode == REQUEST_CHECK_SETTINGS) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                // Granted. Start getting the location information
                locationUtility?.startLocationUpdates()
            }
        }
    }

    protected override fun onStart() {
        super.onStart()
        locationUtility?.mGoogleApiClient!!.connect()
    }

    override fun onResume() {
        super.onResume()
        // Within {@code onPause()}, we pause location updates, but leave the
        // connection to GoogleApiClient intact.  Here, we resume receiving
        // location updates if the user has requested them.
        if (locationUtility?.mGoogleApiClient!!.isConnected && locationUtility?.mRequestingLocationUpdates!!) {
            locationUtility?.startLocationUpdates()
        }
    }

    protected override fun onPause() {
        super.onPause()
        // Stop location updates to save battery, but don't disconnect the GoogleApiClient object.
        if (locationUtility?.mGoogleApiClient!!.isConnected) {
            locationUtility?.stopLocationUpdates()
        }
    }

    protected override fun onStop() {
        super.onStop()
        locationUtility?.mGoogleApiClient!!.disconnect()
    }

    /**
     * Runs when a GoogleApiClient object successfully connects.
     */
    override fun onConnected(connectionHint: Bundle?) {
        Log.i(
            TAG,
            "Connected to GoogleApiClient"
        )
        if (mCurrentLocation?.value == null) {
            mCurrentLocation?.value =
                LocationServices.FusedLocationApi.getLastLocation(locationUtility?.mGoogleApiClient)
//            Log.d("LAT_LNG_onConnected",  "".plus(mCurrentLocation?.value?.latitude).plus("::").plus(mCurrentLocation?.value!!.longitude));
        }
    }

    /**
     * Callback that fires when the location changes.
     */
    override fun onLocationChanged(location: Location) {
        mCurrentLocation?.value = location
        Log.d("LAT_LNG_onChanged",  "".plus(mCurrentLocation?.value?.latitude).plus("::").plus(mCurrentLocation?.value?.longitude));
    }

    override fun onConnectionSuspended(cause: Int) {
        Log.i(
            TAG,
            "Connection suspended"
        )
    }

    override fun onConnectionFailed(result: ConnectionResult) {
        // onConnectionFailed.
        Log.i(
            TAG,
            "Connection failed: ConnectionResult.getErrorCode() = " + result.errorCode
        )
    }


    override fun onSupportNavigateUp(): Boolean {

        val navController = findNavController(R.id.fragmentContainer)
        return navController.navigateUp() || super.onSupportNavigateUp()

    }
}