package com.example.andreas.mapprototype;

import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,ConnectionCallbacks, OnConnectionFailedListener, LocationListener {

    protected GoogleApiClient mGoogleApiClient;

    protected GoogleMap map;

    protected Location mLastLocation;

    protected Location mCurrentLocation;
    protected LatLng mCurrentLatLng;

    protected boolean mRequestingLocationUpdates;

    protected LocationRequest mLocationRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        buildGoogleApiClient();
        //createLocationRequest();

    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        createLocationRequest();
    }

    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    @Override
    public void onMapReady(GoogleMap map) {

        this.map = map;
        // map.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        //map.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        try {
            if (map == null) {
                map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
            }

            LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
            Criteria criteria = new Criteria();
            String bestProvider = locationManager.getBestProvider(criteria, true);
            Location location = locationManager.getLastKnownLocation(bestProvider);
            //mCurrentLocation = location;
            //mCurrentLatLng = new LatLng(location.getLatitude(),location.getLongitude());
            if (location != null) {
                onLocationChanged(location);
            }
           // locationManager.requestLocationUpdates();

            // map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(map.getMyLocation().getLatitude(),map.getMyLocation().getLongitude()),13));
            map.setMapType(GoogleMap.MAP_TYPE_HYBRID);

            // Place dot on current location
            map.setMyLocationEnabled(true);

            // Turns traffic layer on
            map.setTrafficEnabled(true);

            // Enables indoor maps
            map.setIndoorEnabled(true);

            // Turns on 3D buildings
            map.setBuildingsEnabled(true);

            // Show Zoom buttons
            map.getUiSettings().setZoomControlsEnabled(true);

        } catch (Exception e) {
            e.printStackTrace();
        }

        if (map != null) {
            final GoogleMap finalMap = map;
            map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                public void onMapClick(LatLng point) {
                    //lstLatLngs.add(point);
                    finalMap.addMarker(new MarkerOptions().position(point));
                }
            });
        }
    }


    /**
     * this is the button to get the present location
     * @param view
     */
    public void addAsset(View view){
        Asset newAsset =  new Asset(mCurrentLatLng);
       // mCurrentLocation = mLastLocation;
        //mCurrentLatLng = new LatLng(mCurrentLocation.getLatitude(),mCurrentLocation.getLongitude());

        map.addMarker(new MarkerOptions().position(newAsset.getLatLng()));
    }

    @Override
    public void onConnected(Bundle connectionHint) {
        //this gets the last known location and sets it to mLastLocation
       if(mCurrentLocation == null) {

           mCurrentLocation = LocationServices.FusedLocationApi.getLastLocation(
                   mGoogleApiClient);
       }
       
            startLocationUpdates();

    }

    protected void startLocationUpdates() {

        LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient, mLocationRequest, this);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        mCurrentLocation = location;

        mCurrentLatLng = new LatLng(location.getLatitude(),location.getLongitude());
    }

    protected void onPause() {
        super.onPause();
        //stopLocationUpdates();
    }


    /**
     * purpose: to save battery life
     * by stopping continuous location updates
     * ERROR, causes app to crash on pause
     */
    protected void stopLocationUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates(
                mGoogleApiClient, this);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mGoogleApiClient.isConnected() && !mRequestingLocationUpdates) {
            startLocationUpdates();
        }
    }



    /**
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putBoolean(REQUESTING_LOCATION_UPDATES_KEY,
                mRequestingLocationUpdates);
        savedInstanceState.putParcelable(LOCATION_KEY, mCurrentLocation);
        //savedInstanceState.putString(LAST_UPDATED_TIME_STRING_KEY, mLastUpdateTime);
        super.onSaveInstanceState(savedInstanceState);
    }

    private void updateValuesFromBundle(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            // Update the value of mRequestingLocationUpdates from the Bundle, and
            // make sure that the Start Updates and Stop Updates buttons are
            // correctly enabled or disabled.
            if (savedInstanceState.keySet().contains(REQUESTING_LOCATION_UPDATES_KEY)) {
                mRequestingLocationUpdates = savedInstanceState.getBoolean(
                        REQUESTING_LOCATION_UPDATES_KEY);
                setButtonsEnabledState();
            }

            // Update the value of mCurrentLocation from the Bundle and update the
            // UI to show the correct latitude and longitude.
            if (savedInstanceState.keySet().contains(LOCATION_KEY)) {
                // Since LOCATION_KEY was found in the Bundle, we can be sure that
                // mCurrentLocationis not null.
                mCurrentLocation = savedInstanceState.getParcelable(LOCATION_KEY);
            }

           Update the value of mLastUpdateTime from the Bundle and update the UI.
            if (savedInstanceState.keySet().contains(LAST_UPDATED_TIME_STRING_KEY)) {
                mLastUpdateTime = savedInstanceState.getString(
                        LAST_UPDATED_TIME_STRING_KEY);
            }

        }
    }*/


}