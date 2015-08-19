package com.example.android.fabexample;

import android.graphics.Bitmap;

import java.util.UUID;

/**
 * Created by Javier G on 8/18/2015.
 */
public class Asset {
    private UUID mId;

    private double mLatitude, mLongitude;
    private String mName;
    private Bitmap mPicture;

    public Asset() {
        mId = UUID.randomUUID();
        mName = "";
    }

    public UUID getId() {
        return mId;
    }

    public double getLatitude() {
        return mLatitude;
    }

    public void setLatitude(double latitude) {
        mLatitude = latitude;
    }

    public double getLongitude() {
        return mLongitude;
    }

    public void setLongitude(double longitude) {
        mLongitude = longitude;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public Bitmap getPicture() {
        return mPicture;
    }

    public void setPicture(Bitmap picture) {
        mPicture = picture;
    }
}
