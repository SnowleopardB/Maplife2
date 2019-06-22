package com.example.maplife2;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, LocationGetRequest.Callback{

    private GoogleMap mMap;
//    private User currentUser;

    @Override
    public void gotLocations(ArrayList<Location> locationArrayList) {
        Toast.makeText(this, "waw" + String.valueOf(locationArrayList), Toast.LENGTH_SHORT).show();
        LatLng positionone = new LatLng(120, 150.99999);
        mMap.addMarker(new MarkerOptions().position(positionone).title("Marker in Sydney"));
//mMap.addMarker()
    }

    @Override
    public void gotLocationsError(String message) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle("map");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
//        Intent intent = getIntent();
//        currentUser = (User) intent.getSerializableExtra("user");
        Toast.makeText(this, "waw" + String.valueOf(MainActivity.currentUser), Toast.LENGTH_SHORT).show();


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (MainActivity.currentUser != null) {
            ArrayList<Location> locationlist = MainActivity.currentUser.getLocations();
            Toast.makeText(this, "waw" + String.valueOf(locationlist), Toast.LENGTH_SHORT).show();

            for (int i = 0; i < locationlist.size(); i++) {
                Location location = locationlist.get(i);
                LatLng thislocation = new LatLng(location.getLatitude(), location.getLongitude());
                mMap.addMarker(new MarkerOptions().position(thislocation).title(location.getName()));
            }
            // Add a marker in Sydney and move the camera
            LatLng sydney = new LatLng(-34.500, 150.99999);
            mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        }

    }
}
