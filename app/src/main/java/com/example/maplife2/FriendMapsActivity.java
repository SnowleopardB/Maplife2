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

public class FriendMapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener, UserGetRequest.Callback {

    private GoogleMap mMap;
    private User friendUser;
    private int friendID;

    // initiate the activity, get intent with info and call for a new UsergetRequest.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle("map");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_maps);
        Intent intent = getIntent();
        friendID = (int) intent.getSerializableExtra("friendID");

        UserGetRequest req = new UserGetRequest( this, friendID);
        req.getUser(FriendMapsActivity.this, friendID);
    }



    // Manipulates the map once available.
    // This callback is triggered when the map is ready to be used.
    // adds markers to the map for every saved location.
    // Set header indicating to which user the map belongs.
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (MainActivity.currentUser != null) {
            ArrayList<Location> locationlist = friendUser.getLocations();
            for (int i = 0; i < locationlist.size(); i++) {
                Location location = locationlist.get(i);
                LatLng thislocation = new LatLng(Double.valueOf(location.getLatitude()), Double.valueOf(location.getLongitude()));
                MarkerOptions marker = new MarkerOptions().position(thislocation).title(location.getName()).snippet(location.getDescription());
                mMap.addMarker(marker);
            }
            mMap.setOnMarkerClickListener(this);

            TextView username = findViewById(R.id.textViewFriendName);
            username.setText("map of " + friendUser.getName());
        }
    }

    // Retrieve the data from the marker by calling showLocationInfo.
    @Override
    public boolean onMarkerClick(Marker marker) {

        showLocationInfo(marker.getTitle(), marker.getSnippet());

        // Return false to indicate that we have not consumed the event and that we wish
        // for the default behavior to occur.
        return false;
    }

    // create allertdialog with info about the marker that has been clicked.
    public void showLocationInfo(String title, String snippet) {

        // Create alertDialog with the name of the location as title and a description
        AlertDialog.Builder giveInfo = new AlertDialog.Builder(FriendMapsActivity.this);

        giveInfo.setTitle(title)
                .setMessage(snippet)
                .setCancelable(false);

        giveInfo.setNegativeButton("Back", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
            }
        });
        giveInfo.show();
    }

    // When the information about the friend has succesfully been downloaded,
    // safe this information in the global variable friendUser.
    // Obtain the SupportMapFragment and get notified when the map is ready to be used.
    @Override
    public void gotUser(User user) {
        friendUser = user;
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    // when the information could not be loaded, show error message.
    @Override
    public void gotUserError(String message) {
        Toast.makeText(getApplicationContext(), "something went wrong loading your friends info" +
                " , try again later", Toast.LENGTH_LONG)
                .show();
    }

    // finish current activity and go back to FriendsViewActivity.
    public void onBackPressed() {
       finish();
   }
}
