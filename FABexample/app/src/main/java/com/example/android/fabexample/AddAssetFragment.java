package com.example.android.fabexample;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Javier G on 8/17/2015.
 */
public class AddAssetFragment extends Fragment {
    private static final String TAG = "AddAssetFragment";
    private static final int CAMERA_REQUEST = 1888;
    private ImageView mImageView;
    private EditText mNameEditField, mLatitudeEditField, mLongitudeField;
    private Asset mAsset;


    TextView textview;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add_asset, container, false);


        mAsset  = new Asset();

        Button mRecordButton = (Button)v.findViewById(R.id.recordButton);
        mRecordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AudioRecordTest.class);
                startActivity(intent);
            }
        });


        mNameEditField = (EditText)v.findViewById(R.id.nameEditText);
        mNameEditField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mAsset.setName(s.toString());
                Log.d(TAG, "Name: " + mAsset.getName());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        GPSTracker gpsTracker = new GPSTracker(getActivity());

        //Log.d(TAG, mAsset.getName());
        if (gpsTracker.getIsGPSTrackingEnabled())
        {


            mAsset.setLatitude(gpsTracker.latitude);
            mAsset.setLongitude(gpsTracker.longitude);

            /*String stringLatitude = String.valueOf(asset.getLatitude());
            String stringLongitude = String.valueOf(asset.getLongitude());*/

            textview = (TextView)v.findViewById(R.id.latitudeEditText);
            textview.setText(String.valueOf(mAsset.getLatitude()));

            textview = (TextView)v.findViewById(R.id.longitudeEditText);
            textview.setText(String.valueOf(mAsset.getLongitude()));


        }
        else
        {
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            gpsTracker.showSettingsAlert();
        }

        this.mImageView = (ImageView)v.findViewById(R.id.imageView);

        Button pictureButton = (Button)v.findViewById(R.id.pictureButton);
        pictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
        });


        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST) {
            mAsset.setPicture((Bitmap) data.getExtras().get("data"));
            mImageView.setImageBitmap(mAsset.getPicture());
        }
    }
}
