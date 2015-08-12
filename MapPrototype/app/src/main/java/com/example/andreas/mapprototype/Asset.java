package com.example.andreas.mapprototype;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Andreas on 8/2/2015.
 */
public class Asset {

    private LatLng assetLatLng;

    public Asset(LatLng latLng){
        assetLatLng = latLng;

    }



    public LatLng getLatLng(){
        return assetLatLng;
    }
}
