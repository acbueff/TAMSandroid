package com.example.andreas.mapprototype;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,ConnectionCallbacks, OnConnectionFailedListener, LocationListener {

    private static final String TAG = MapsActivity.class.getSimpleName();

    protected GoogleApiClient mGoogleApiClient;
    protected GoogleMap map;
    protected Location mCurrentLocation;
    protected LatLng mCurrentLatLng;
    protected boolean mRequestingLocationUpdates;
    protected LocationRequest mLocationRequest;

    protected  ArrayList<LatLng> markerArray = new ArrayList<LatLng>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                                                              .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        buildGoogleApiClient();

        Button addAssetButton = (Button)findViewById(R.id.addAsset);
        addAssetButton.setOnClickListener(
                new Button.OnClickListener(){
                 public void onClick(View v){

                     addAsset(v);
                 }
                }
        );

        //Restoring the markers on configuration changes
        if(savedInstanceState != null){
            if(savedInstanceState.containsKey("points")){
                markerArray =  savedInstanceState.getParcelableArrayList("points");

            }
        }
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
        //map.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        try {
            if (map == null) {
                map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
            }

            LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
            Criteria criteria = new Criteria();
            String bestProvider = locationManager.getBestProvider(criteria, true);
            Location location = locationManager.getLastKnownLocation(bestProvider);

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


        /**
         * adds marker to map with touch
         */
        if (map != null) {
            final GoogleMap finalMap = map;

            map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                public void onMapClick(LatLng point) {
                    MarkerOptions newMarker = new MarkerOptions().position(point);
                    finalMap.addMarker(newMarker);
                    markerArray.add(newMarker.getPosition());
                }
            });

            if(markerArray != null) {
                for (int i = 0; i < markerArray.size(); i++) {
                    if (markerArray.get(i) != null) {
                        drawMarker(markerArray.get(i));
                    }
                }
            }
        }
    }

    private  void drawMarker(LatLng point){
        MarkerOptions markerOptions = new MarkerOptions().position(point);
        //markerOptions.position(point);
        if(map != null) {
               this.map.addMarker(markerOptions);
        }
    }


    /**
     * this is the button to get the present location
     * @param view
     */
    public void addAsset(View view){
        Asset newAsset =  new Asset(mCurrentLatLng);
        MarkerOptions mMarker = new MarkerOptions().position(newAsset.getLatLng());
        map.addMarker(mMarker);
        markerArray.add(mMarker.getPosition());
    }

    public Object onRetainCustomNonConfigurationInstance(){
        return markerArray;
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




    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putParcelableArrayList("points",markerArray);
        super.onSaveInstanceState(savedInstanceState);
    }
     /**
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