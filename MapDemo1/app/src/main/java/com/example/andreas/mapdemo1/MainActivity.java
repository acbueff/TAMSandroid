package com.example.andreas.mapdemo1;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;


public class MainActivity extends ActionBarActivity {

    static final LatLng MyPos = new LatLng(50,50);

    EditText addressEditText, finalAddressEditText;

    private GoogleMap googleMap;

    LatLng addressPos, finalAddressPos;

    Marker addressMarker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialize my EditTexts
        addressEditText = (EditText)findViewById(R.id.addressEditText);
        finalAddressEditText = (EditText)findViewById(R.id.finalAddressEditText);

        try{
            if(googleMap == null){
                googleMap = ((MapFragment)getFragmentManager().findFragmentById(R.id.map)).getMap();
            }

            googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

            // Place dot on current location
            googleMap.setMyLocationEnabled(true);

            // Turns traffic layer on
            googleMap.setTrafficEnabled(true);

            // Enables indoor maps
            googleMap.setIndoorEnabled(true);

            // Turns on 3D buildings
            googleMap.setBuildingsEnabled(true);

            // Show Zoom buttons
            googleMap.getUiSettings().setZoomControlsEnabled(true);

            // Create a marker in the map at a given position with a title
           // Marker marker = googleMap.addMarker(new MarkerOptions().
              //      position(MyPos).title("Hello"));


        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //called when getAddressButton is clicked
    public void showAddressMarker(View view){
        //get the street address entered
        String newAddress = addressEditText.getText().toString();
        if(newAddress != null){
            //call for the asynctask to place a marker
            new PlaceAMarker().execute(newAddress);
        }
    }

    //called when getDirectionsButton is clicked
    public void getDirections(View view){
        //get the start and ending address
        String startingAddress =  addressEditText.getText().toString();
        String finalAddress = finalAddressEditText.getText().toString();

        //verify that they aren't empty
        if((startingAddress.equals("")) || (finalAddress.equals(""))){
            Toast.makeText(this, "Enter a Starting & Ending Address", Toast.LENGTH_SHORT);
        }
        else{
            //get the getdirections asynctaskto call for the directions
            new GetDirections().execute(startingAddress, finalAddress);
        }
    }

    class PlaceAMarker extends AsyncTask<String, String, String>{

        @Override
        protected String doInBackground(String... params) {

            //Get the first address passed
            String startAddress = params[0];

            //Replace the spaces with %20
            startAddress = startAddress.replaceAll(" ", "%20");

            //call for the lat and long and pass in
            //we dont want directions
            getLatLong(startAddress,false);

            return null;
        }

        protected void onPostExecute(String s){
            super.onPostExecute(s);

            //draw the marker on the screen
            addressMarker = googleMap.addMarker(new MarkerOptions()
                                .position(addressPos)
                                .title("Address"));
        }
    }

    class GetDirections extends AsyncTask<String, String, String>{

        @Override
        protected String doInBackground(String... params) {

            //Get the starting address
            String startAddress = params[0];

            //Replace the spaces with %20
            startAddress = startAddress.replaceAll(" ","%20");

            //Get the lat and long for our address
            getLatLong(startAddress, false);

            //Get the destination address
            String endingAddress = params[1];

            //Replace the spaces with %20
            endingAddress = endingAddress.replaceAll(" ", "%20");

            //get lat and long for the destination address and pass true
            getLatLong(endingAddress, true);



            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            //create the url for google maps to get the directions
            String geoUriString = "http://maps.google.com/maps?saddr=" +
                    addressPos.latitude + "," +
                    addressPos.longitude + "&addr=" +
                    finalAddressPos.latitude+ "," +
                    finalAddressPos.longitude;

            //Call for Google Maps to open
            Intent mapCall = new Intent(Intent.ACTION_VIEW, Uri.parse(geoUriString));
            startActivity(mapCall);
        }
    }

    protected void getLatLong(String address, boolean setDestination){
        //Define the uri that is used to get lat and long for our address
        String uri = "http://maps.google.com/maps/api/geocode/json?address=" +
                address + "&sensor=false";

        //use the get method to retrieve our data
        HttpGet httpGet = new HttpGet(uri);

        //acts as the client which executes HTTP requests
        HttpClient client = new DefaultHttpClient();

        //acts as the client which executes HTTP requests
        HttpResponse response;

        //Will hold the data received
        StringBuilder stringBuilder = new StringBuilder();
        try {
            //get the response of our query
            response = client.execute(httpGet);

            //Receive the entity information sent with the HTTP message
            HttpEntity entity = response.getEntity();

            //Holds the sent bytes of data
            InputStream stream = entity.getContent();
            int byteData;

            //Continue reading data while abailable
            while ((byteData = stream.read()) != -1) {
                stringBuilder.append((char) byteData);
            }
        }catch(ClientProtocolException e){
                e.printStackTrace();
        }catch(IOException e){
                e.printStackTrace();
        }

        double lat = 0.0, lng =  0.0;

        JSONObject jsonObject;
        try {

            jsonObject = new JSONObject(stringBuilder.toString());

            //get the returned latitude and longitude
            lng = ((JSONArray) jsonObject.get("results")).getJSONObject(0)
                    .getJSONObject("geometry").getJSONObject("location")
                    .getDouble("lng");

            lat = ((JSONArray) jsonObject.get("results")).getJSONObject(0)
                    .getJSONObject("geometry").getJSONObject("location")
                    .getDouble("lat");

            //change the lat and long depending on if we want to set the starting
            //or end destination

            if (setDestination) {
                finalAddressPos = new LatLng(lat, lng);
            } else {
                addressPos = new LatLng(lat, lng);
            }

        }catch(JSONException e) {
            e.printStackTrace();
        }

     }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
