package com.example.maplife2;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class AddFriendsActivity extends AppCompatActivity implements PossibleFriendsGetRequest.Callback, FriendsPutRequest.Callback{

    ArrayList<Friend> friends = new ArrayList<>();
    String TAG = "friendsget";
    int userID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle("add friends");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friends);

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
        getPossibleFriends();
    }

    @Override
    public void gotPossibleFriends(ArrayList<Friend> possiblefriends) {
        Log.d(TAG, "friends: " + possiblefriends);
        FriendsAdapter adapter = new FriendsAdapter(this, R.layout.friend_grid_item, possiblefriends);
        GridView grid_view = findViewById(R.id.gridview);
        grid_view.setAdapter(adapter);
        GridItemClickListener onclick = new GridItemClickListener();
        grid_view.setOnItemClickListener(onclick);

    }

    @Override
    public void gotPossibleFriendsError(String message) {
        Log.d(TAG, "whooops");
    }

    public void getPossibleFriends() {
        PossibleFriendsGetRequest req = new PossibleFriendsGetRequest( this);
        req.getPossibleFriends(AddFriendsActivity.this);
    }

    @Override
    public void postedFriends(ArrayList<Friend> FriendArraylist) {
        FriendsPutRequest request = new FriendsPutRequest(this, userID, friends);
        request.putfriends(this);
    }

    @Override
    public void postedFriendsError(String message) {

    }

    private class GridItemClickListener implements AdapterView.OnItemClickListener  {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            final Friend clickedFriend = (Friend) parent.getItemAtPosition(position);


            AlertDialog.Builder addFriend = new AlertDialog.Builder(AddFriendsActivity.this);
            addFriend.setMessage("Are you sure you want to add this friend");

            addFriend.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {

                    // Create an intent to HighscoreActivity and include Highscore object.
                    friends.add(clickedFriend);
                    Intent intent = new Intent(AddFriendsActivity.this, FriendsViewActivity.class);
                    intent.putExtra("id", userID);
                    intent.putExtra("friends", friends.toString());
                    startActivity(intent);
                }
            });
            addFriend.show();
            // setContentView(R.layout.activity_profile);

            // does not seem to get here, question
            Log.d("allright", "waw" + clickedFriend);
        }



    }
}
