package com.example.maplife2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;

import java.util.ArrayList;

public class AddFriendsActivity extends AppCompatActivity implements PossibleFriendsGetRequest.Callback {

    String TAG = "friendsget";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle("add friends");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_view);
        getPossibleFriends();
    }

    @Override
    public void gotPossibleFriends(ArrayList<Friend> possiblefriends) {
        Log.d(TAG, "friends: " + possiblefriends);
        FriendsAdapter adapter = new FriendsAdapter(this, R.layout.friend_grid_item, possiblefriends);
        GridView grid_view = findViewById(R.id.gridview);
        grid_view.setAdapter(adapter);

    }

    @Override
    public void gotPossibleFriendsError(String message) {
        Log.d(TAG, "whooops");
    }

    public void getPossibleFriends() {
        PossibleFriendsGetRequest req = new PossibleFriendsGetRequest( this);
        req.getPossibleFriends(AddFriendsActivity.this);
    }
}
