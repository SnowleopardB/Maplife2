package com.example.maplife2;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class AddLocationActivity extends AppCompatActivity implements LocationPutRequest.Callback, OnMapReadyCallback {

    // define global variables
    private GoogleMap mMap;
    ArrayList<Location> locations = new ArrayList<>();
    private String latitude;
    private String longitude;
    User user;

    // startup activity, that saves info from intent and listens when the map is ready.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle("add location");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_location);

        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("user");

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    // when the locations have succesfully been updated in the database,
    // finish the AddLocationActivity.
    @Override
    public void postedLocations(ArrayList<Location> LocationArraylist) {

        // Create an intent to Mainactivity and include necessary information.
        Intent intent = new Intent(AddLocationActivity.this, MainActivity.class);
        intent.putExtra("loggedinID", user.getId());
        intent.putExtra("loggedinCheck", 1);
        startActivity(intent);
    }

    // when the locations have not been succesfully updated, give message.
    @Override
    public void postedLocationsError(String message) {
        Toast.makeText(getApplicationContext(), "something went wrong uploading the locations" +
                " , try again later", Toast.LENGTH_LONG)
                .show();
    }

    // method called to update the database
    public void postLocation() {
        LocationPutRequest request = new LocationPutRequest(this, user.getId(), locations);
        request.postLocation(this);
    }

    // when the map is ready, create markers on the locations in the account.
    // Also set an onMapLongClickListener on the map for adding locations.
    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        onMapClicklistener onclick = new onMapClicklistener();
        mMap.setOnMapLongClickListener(onclick);

        locations = user.getLocations();

        for (int i = 0; i < locations.size(); i++) {
            Location location = locations.get(i);
            double lat = Double.valueOf(location.getLatitude());
            double longit = Double.valueOf(location.getLongitude());
            LatLng thislocation = new LatLng(lat, longit);
            mMap.addMarker(new MarkerOptions().position(thislocation).title(location.getName()).snippet(location.getDescription()));
        }
    }

    // onmapClicklistener method to add markers to the map.
    private class onMapClicklistener implements GoogleMap.OnMapLongClickListener {

        @Override
        public void onMapLongClick(LatLng point) {

            mMap.addMarker(new MarkerOptions()
                    .position(point)
                    .title(point.toString()));

            // transform the coordinates into the right format for upload into the database.
            String coordinates = point.toString();
            String[] latlong = coordinates.split(",");
            String[] lat = latlong[0].split("\\(");
            String[] longit = latlong[1].split("\\)");
            latitude = String.valueOf(lat[1]);
            longitude = String.valueOf(longit[0]);

            // call giveNameAndDescription to get all the necessary information about a place
            // for uploading to the database.
            getNameAndDescription();
        }
    }

    //method to get the name and description of the chosen place.
    public void getNameAndDescription() {

        // Create alertDialog with textEdits for
        // user to give the name and description of the place.
        AlertDialog.Builder giveName = new AlertDialog.Builder(AddLocationActivity.this);

        LayoutInflater layoutinflater = LayoutInflater.from(AddLocationActivity.this);
        final View view = layoutinflater.inflate(R.layout.dialog_location, null);

        giveName.setTitle("name and description")
                .setView(view)
                .setCancelable(false);

        giveName.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                EditText name = view.findViewById(R.id.nameEdit);
                EditText description = view.findViewById(R.id.descriptionEdit);
                String stringName = String.valueOf(name.getText());
                String stringDescription = String.valueOf(description.getText());

                // add location to the Arraylist of locations, and call postLocation
                Location newLocation = new Location(latitude, longitude, stringName, stringDescription);
                ArrayList<Location> locationlist = user.getLocations();
                locationlist.add(newLocation);
                postLocation();
            }
        });
        giveName.show();
    }
}





