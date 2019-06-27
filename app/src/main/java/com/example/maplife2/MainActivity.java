package com.example.maplife2;

import android.accounts.Account;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements UserGetRequest.Callback, NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    // define global variables
    private GoogleMap mMap;
    int userID = 1;
    int logginCheck = 0;
    public static User currentUser;
    private DrawerLayout drawer;

    // set up layout, define the drawer and set values and listeners.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("menu");
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        setSupportActionBar(toolbar);

        // setListener on weather the drawer is open or closed.
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        // get useful information from intent
        Intent intent = getIntent();
        userID = (int) intent.getSerializableExtra("loggedinID");
        logginCheck = (int) intent.getSerializableExtra("loggedinCheck");

        // if not loggedin, go to LoginActivity
        if (logginCheck != 1) {
            Intent noLoginIntent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(noLoginIntent);
        }
        // otherwise, ask the getrequestHelper for all the information about the user with
        // current userID.
        else {
            UserGetRequest req = new UserGetRequest(this, userID);
            req.getUser(MainActivity.this, userID);
        }
    }

    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
        //do nothing, we dont want to go back further than mainActivity.
        }
    }

    // when User info has succesfully downloaded, set values and the onmapreadylistener.
    @Override
    public void gotUser(User user) {
        currentUser = user;
        if (currentUser == null) {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        }
        TextView userName = findViewById(R.id.textViewNameHeader);
        TextView email = findViewById(R.id.textViewEmailHeader);
        userName.setText(currentUser.getName());
        email.setText(currentUser.getEmail());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    // When the user has not downloaded, show message.
    @Override
    public void gotUserError(String message) {
        Toast.makeText(getApplicationContext(), "something went wrong loading your info" +
                ", try again later", Toast.LENGTH_LONG)
                .show();
    }

    // a switch case method that finds the Clicked item by its ID and then makes an intent
    // to the right activity with the necessary information.
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId()) {
            case R.id.nav_friends:
                Intent intent = new Intent(MainActivity.this, FriendsViewActivity.class);
                ArrayList<Friend> friends = currentUser.getFriends();
                intent.putExtra("friends", friends.toString());
                intent.putExtra("id", userID);
                startActivity(intent);
                break;
            case R.id.nav_location:
                Intent intentmap = new Intent(MainActivity.this, LocationViewActivity.class);
                startActivity(intentmap);
                break;
            case R.id.nav_logout:
                Intent intentlogout = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intentlogout);
                break;
            case R.id.nav_add_location:
                Intent intentaddlocation = new Intent(MainActivity.this, AddLocationActivity.class);
                intentaddlocation.putExtra("user", currentUser);
                startActivity(intentaddlocation);
                break;
            case R.id.nav_add_friend:
                Intent intentaddfriend = new Intent(MainActivity.this, AddFriendsActivity.class);
                ArrayList<Friend> friends1 = currentUser.getFriends();
                intentaddfriend.putExtra("friends", friends1.toString());
                intentaddfriend.putExtra("id", userID);
                startActivity(intentaddfriend);
                break;
            case R.id.nav_account:
                Intent intentAccount = new Intent(MainActivity.this, AccountActivity.class);
                intentAccount.putExtra("name", currentUser.getName());
                intentAccount.putExtra("email", currentUser.getEmail());
                startActivity(intentAccount);
        }
        return true;
    }

    // when the map is ready, isolate the locationlist from userdata and add markers
    // to the map.
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (currentUser != null) {
            ArrayList<Location> locationlist = currentUser.getLocations();
            for (int i = 0; i < locationlist.size(); i++) {
                Location location = locationlist.get(i);
                LatLng thislocation = new LatLng(Double.valueOf(location.getLatitude()), Double.valueOf(location.getLongitude()));
                MarkerOptions marker = new MarkerOptions().position(thislocation).title(location.getName()).snippet(location.getDescription());
                mMap.addMarker(marker);
            }
            // Set onmarkerClickListener on the map.
            mMap.setOnMarkerClickListener(this);
        }
    }

    // onmarkerClicklistner, that calls showLocationInfo.
    @Override
    public boolean onMarkerClick(Marker marker) {

        String title = marker.getTitle();
        showLocationInfo(marker.getTitle(), marker.getSnippet());

        // Return false to indicate that we have not consumed the event and that we wish
        // for the default behavior to occur (which is for the camera to move such that the
        // marker is centered and for the marker's info window to open, if it has one).
        return false;
    }

    // when called, this method shows an alertdialog with information about the clickedmarker.
    public void showLocationInfo(String title, String snippet) {
        // Create alertDialog with info about the place
        AlertDialog.Builder giveInfo = new AlertDialog.Builder(MainActivity.this);

        giveInfo.setTitle(title)
                .setMessage(snippet)
                .setCancelable(false);

        giveInfo.setPositiveButton("Back", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        giveInfo.show();
    }
}
