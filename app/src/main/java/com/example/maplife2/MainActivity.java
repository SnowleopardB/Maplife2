package com.example.maplife2;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements UserGetRequest.Callback, NavigationView.OnNavigationItemSelectedListener {

    int userID = 1;
    int logginCheck = 0;
    public static User currentUser;
    private DrawerLayout drawer;


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

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        Intent intent = getIntent();
        userID = (int) intent.getSerializableExtra("loggedinID");
        logginCheck = (int) intent.getSerializableExtra("loggedinCheck");
        Log.d("userID", String.valueOf(userID));
        if (logginCheck != 1) {
            Intent noLoginIntent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(noLoginIntent);
        }
        UserGetRequest req = new UserGetRequest( this, userID);
        req.getUser(MainActivity.this, userID);
    }

    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
        //do nothing
        }
    }

    @Override
    public void gotUser(User user) {
        currentUser = user;
        Log.d("currentuser", "currentuser: " + currentUser);
        if (currentUser == null) {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void gotUserError(String message) {

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        Log.d("huh", "onNavigationItemSelected: " + currentUser.getFriends().toString());
        switch (menuItem.getItemId()) {
            case R.id.nav_friends:
                Intent intent = new Intent(MainActivity.this, FriendsViewActivity.class);
                ArrayList<Friend> friends = currentUser.getFriends();
                intent.putExtra("friends", friends.toString());
                intent.putExtra("id", userID);
                startActivity(intent);
                break;
            case R.id.nav_location:
                Intent intentmap = new Intent(MainActivity.this, MapsActivity.class);
//        intent.putExtra("user", currentUser);
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
        }
        return true;
    }
}
