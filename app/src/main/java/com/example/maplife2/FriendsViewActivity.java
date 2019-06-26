package com.example.maplife2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FriendsViewActivity extends AppCompatActivity {

    int userID = 0;
    ArrayList<Friend> friends = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle("FriendsList");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_view);

        Intent intent = getIntent();
        userID = (int) intent.getSerializableExtra("id");
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

        FriendsViewActivity.GridItemClickListener onclick = new FriendsViewActivity.GridItemClickListener();
        grid_view.setOnItemClickListener(onclick);
    }

    public void floatingbuttonOnClick(View view) {
        Intent intent = new Intent(FriendsViewActivity.this, AddFriendsActivity.class);
        intent.putExtra("friends", friends.toString());
        intent.putExtra("id", userID);
        startActivity(intent);

    }

    public void onBackPressed() {
        Intent intent = new Intent(FriendsViewActivity.this, MainActivity.class);
        intent.putExtra("loggedinID", userID);
        intent.putExtra("loggedinCheck", 1);
        startActivity(intent);
    }

    private class GridItemClickListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            final Friend clickedFriend = (Friend) parent.getItemAtPosition(position);
            Intent intent = new Intent(FriendsViewActivity.this, FriendMapsActivity.class);
            intent.putExtra("friendID", clickedFriend.getId());
            startActivity(intent);
        }
    }

}
