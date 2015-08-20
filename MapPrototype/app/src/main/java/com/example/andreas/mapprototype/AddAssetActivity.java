package com.example.andreas.mapprototype;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by Andreas on 8/15/2015.
 */
public class AddAssetActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        if(savedInstanceState == null){
           // getSupportFragmentManager().beginTransaction()
             //       .add(R.id.maps_activity,new PlaceHolderf )
        }
    }
}
