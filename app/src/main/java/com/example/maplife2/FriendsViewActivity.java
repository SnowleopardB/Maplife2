package com.example.maplife2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FriendsViewActivity extends AppCompatActivity {

    ArrayList<Friend> friends = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle("FriendsList");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_view);

        Intent intent = getIntent();
        String jsonified = intent.getStringExtra("friends");

        try {
            JSONArray JSONfriend = new JSONArray(jsonified);

            for (int i=0; i <JSONfriend.length(); i++) {
                JSONObject friendobject = JSONfriend.getJSONObject(i);

                Friend friend = new Friend(
                        friendobject.getInt("id"),
                        friendobject.getString("name")
                );

                friends.add(friend);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        FriendsAdapter adapter = new FriendsAdapter(this, R.layout.friend_grid_item, friends);
        GridView grid_view = findViewById(R.id.gridview);
        grid_view.setAdapter(adapter);


    }
}
