package com.example.maplife2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

public class AddChooseActivity extends AppCompatActivity {

    User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_choose);
        Intent intent = getIntent();
        currentUser = (User) intent.getSerializableExtra("user");

    }

    public void addFriendClick(View view) {
        Intent intent = new Intent(AddChooseActivity.this, AddFriendsActivity.class);
        ArrayList<Friend> friends = currentUser.getFriends();
        intent.putExtra("friends", friends);
        startActivity(intent);
    }

    public void addLocationClick(View view) {
        Intent intent = new Intent(AddChooseActivity.this, AddLocationActivity.class);
        ArrayList<Location> locations = currentUser.getLocations();
        intent.putExtra("user", currentUser);
        startActivity(intent);
    }
}
