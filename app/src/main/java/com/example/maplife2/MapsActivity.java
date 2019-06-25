package com.example.maplife2;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;
//    private User currentUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle("map");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
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
                LatLng thislocation = new LatLng(Double.valueOf(location.getLatitude()), Double.valueOf(location.getLongitude()));
                MarkerOptions marker = new MarkerOptions().position(thislocation).title(location.getName());
                mMap.addMarker(marker);
            }
            mMap.setOnMarkerClickListener(this);
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {

        // Retrieve the data from the marker.
        Integer clickCount = (Integer) marker.getTag();
        Toast.makeText(this, marker.getTitle() + " has been clicked " + clickCount + " times.", Toast.LENGTH_SHORT).show();
        showLocationInfo();

        // Return false to indicate that we have not consumed the event and that we wish
        // for the default behavior to occur (which is for the camera to move such that the
        // marker is centered and for the marker's info window to open, if it has one).
        return false;
    }

    public void showLocationInfo() {
        // Create alertDialog with a textEdit for user to give his name.


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
