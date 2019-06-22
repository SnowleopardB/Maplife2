package com.example.maplife2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements UserGetRequest.Callback {

    boolean loggedin = false;
    int userID = 1;
    public static User currentUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("menu");
        setContentView(R.layout.activity_main);
        UserGetRequest req = new UserGetRequest( this);
        req.getUser(MainActivity.this);
    }


    public void addOnClick(View view) {
        Intent intent = new Intent(MainActivity.this, AddChooseActivity.class);
        intent.putExtra("user", currentUser);
        startActivity(intent);
    }

    public void mapOnClick(View view) {
        Intent intent = new Intent(MainActivity.this, MapsActivity.class);
//        intent.putExtra("user", currentUser);
        startActivity(intent);
    }

    public void friendsViewClick(View view) {
        Intent intent = new Intent(MainActivity.this, FriendsViewActivity.class);
        ArrayList<Friend> friends = currentUser.getFriends();
        intent.putExtra("friends", friends.toString());
        startActivity(intent);
    }

    public void logoutClick(View view) {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
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

    private class onItemClick {

        @Override
        protected Object clone() throws CloneNotSupportedException {
            return super.clone();
        }
    }
}
