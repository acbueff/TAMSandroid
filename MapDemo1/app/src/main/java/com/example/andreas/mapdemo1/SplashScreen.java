package com.example.andreas.mapdemo1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;


/**
 * Created by Andreas on 6/20/2015.
 */
public class SplashScreen extends Activity{

    private static int SLASH_SCREEN_DELAY = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(new Runnable(){

            @Override
            public void run() {
                Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                        startActivity(intent);
                finish();
            }
        },SLASH_SCREEN_DELAY);
    }
}