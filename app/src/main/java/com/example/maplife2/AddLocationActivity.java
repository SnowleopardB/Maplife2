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

    private GoogleMap mMap;
    ArrayList<Location> locations = new ArrayList<>();
    private String latitude;
    private String longitude;
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
        Log.d("waaw", "whoops");
    }

    public void postLocation() {
        LocationPutRequest request = new LocationPutRequest(this, user.getId(), locations);
        request.postLocation(this);
    }

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
            mMap.addMarker(new MarkerOptions().position(thislocation).title(location.getName()));
        }

        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-34.500, 150.99999);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));


    }

    private class onMapClicklistener implements GoogleMap.OnMapLongClickListener {

        @Override
        public void onMapLongClick(LatLng point) {

            mMap.addMarker(new MarkerOptions()
                    .position(point)
                    .title(point.toString()));

            String coordinates = point.toString();
            Log.d("waw1", "point" + point);
            String[] latlong = coordinates.split(",");
            Log.d("waw", "latlong" + latlong);
            String[] lat = latlong[0].split("\\(");
            String[] longit = latlong[1].split("\\)");
            latitude = String.valueOf(lat[1]);
            longitude = String.valueOf(longit[0]);
            Log.d("aw", "latitude longitude" + String.valueOf(latitude) + String.valueOf(longitude));
            Toast.makeText(getApplicationContext(),
                    "New marker added@" + point.toString(), Toast.LENGTH_LONG)
                    .show();
            getNameAndDescription();
        }
    }

    public void getNameAndDescription() {
        // Create alertDialog with a textEdit for user to give his name.

        final String[] name = new String[1];
        final String[] description = new String[1];
        final EditText nameEdit = new EditText(this);
        final EditText descriptionEdit = new EditText(this);


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

                Location newLocation = new Location(latitude, longitude, stringName, stringDescription);
                ArrayList<Location> locationlist = user.getLocations();
                locationlist.add(newLocation);
                postLocation();

                // Create an intent to HighscoreActivity and include Highscore object.
                Intent intent = new Intent(AddLocationActivity.this, MainActivity.class);
                intent.putExtra("loggedinID", user.getId());
                intent.putExtra("loggedinCheck", 1);
                startActivity(intent);

            }
        });
        giveName.show();
    }
}





