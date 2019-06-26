package com.example.maplife2;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    // define global variable(s)
    private GoogleMap mMap;

    // set up layout and set a onmapreadylistener.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle("map");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


    }


     // This callback is triggered when the map is ready to be used.
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (MainActivity.currentUser != null) {
            ArrayList<Location> locationlist = MainActivity.currentUser.getLocations();

            // add markers to the map for all the saved locations and set an onMarkerClickListener.
            for (int i = 0; i < locationlist.size(); i++) {
                Location location = locationlist.get(i);
                LatLng thislocation = new LatLng(Double.valueOf(location.getLatitude()), Double.valueOf(location.getLongitude()));
                MarkerOptions marker = new MarkerOptions().position(thislocation).title(location.getName());
                mMap.addMarker(marker);
            }
            mMap.setOnMarkerClickListener(this);
        }
    }

    // calls showLocationInfo();
    @Override
    public boolean onMarkerClick(Marker marker) {

        showLocationInfo();

        // Return false to indicate that we have not consumed the event and that we wish
        // for the default behavior to occur (which is for the camera to move such that the
        // marker is centered and for the marker's info window to open, if it has one).
        return false;
    }

    // show alertdialog with info about the place.
    public void showLocationInfo() {

        // Create alertDialog with info about the place
        AlertDialog.Builder giveInfo = new AlertDialog.Builder(MapsActivity.this);

        giveInfo.setTitle("Place Info")
                .setView(R.layout.dialog_location_view)
                .setCancelable(false);

        giveInfo.setPositiveButton("Back", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                TextView name = findViewById(R.id.nameText);
                TextView description = findViewById(R.id.descriptionText);
                TextView user = findViewById(R.id.userText);
                name.setText("waw");
                description.setText("waw2");
                user.setText("waw3");
            }
        });
        giveInfo.show();
    }
}
