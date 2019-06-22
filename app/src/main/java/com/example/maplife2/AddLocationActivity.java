package com.example.maplife2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class AddLocationActivity extends AppCompatActivity implements LocationPutRequest.Callback, OnMapReadyCallback{

    private GoogleMap mMap;
    ArrayList<Location> locations = new ArrayList<>();

    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle("add location");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_location);


        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("user");



//        LocationGetRequestForAdd req = new LocationGetRequestForAdd( this);
//        req.getLocations(AddLocationActivity.this, "HIER MISS USERNAME OFZO");
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void postedLocations(ArrayList<Location> LocationArraylist) {

        Log.d("waw", "postedLocations: HET IS GELUKT ");

    }

    @Override
    public void postedLocationsError(String message) {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        onMapClicklistener onclick = new onMapClicklistener();
        mMap.setOnMapLongClickListener(onclick);

        locations = user.getLocations();
        Location loc = new Location(-34, 150, "Starbucks", "waaw");
        locations.add(loc);

        for (int i = 0; i < locations.size(); i++) {
            Log.d("maploc", "onMapReady: " + i);
            Log.d("maploc", "onMapReady: " + loc.getName() );
            Location location = locations.get(i);
            LatLng thislocation = new LatLng(location.getLatitude(), location.getLongitude());
            mMap.addMarker(new MarkerOptions().position(thislocation).title(location.getName()));
        }

            // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-34.500, 150.99999);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));



    }

    private class onMapClicklistener implements GoogleMap.OnMapLongClickListener{

        @Override
        public void onMapLongClick(LatLng point) {

            mMap.addMarker(new MarkerOptions()
                    .position(point)
                    .title(point.toString()));

            Toast.makeText(getApplicationContext(),
                    "New marker added@" + point.toString(), Toast.LENGTH_LONG)
                    .show();

        }
    }


}
