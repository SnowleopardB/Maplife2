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
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class AddFriendsActivity extends AppCompatActivity implements PossibleFriendsGetRequest.Callback, FriendsPutRequest.Callback{

    // define global variables
    ArrayList<Friend> friends = new ArrayList<>();
    int userID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle("add friends");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friends);

        // get intent with necessary information from FriendsViewActivity or MainActivity
        Intent intent = getIntent();
        userID = (int) intent.getSerializableExtra("id");
        String jsonifiedFriends = intent.getStringExtra("friends");

        // transcribe jsonString of friends to a usable arraylist of friend objects.
        try {
            JSONArray JSONfriend = new JSONArray(jsonifiedFriends);

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

    // if users are loaded correctly, set a FriendsAdapter to visualize them on the screen
    @Override
    public void gotPossibleFriends(ArrayList<Friend> possiblefriends) {
        FriendsAdapter adapter = new FriendsAdapter(this, R.layout.friend_grid_item, possiblefriends);
        GridView grid_view = findViewById(R.id.gridview);
        grid_view.setAdapter(adapter);
        GridItemClickListener onclick = new GridItemClickListener();
        grid_view.setOnItemClickListener(onclick);

    }

    // give errormessage if something went wrong while downloading friends
    @Override
    public void gotPossibleFriendsError(String message) {
        Toast.makeText(getApplicationContext(), "something went wrong loading friends" +
                " , try again later", Toast.LENGTH_LONG)
                .show();
    }

    // this method asks the usersrequesthelper for a new usersrequest.
    public void getPossibleFriends() {
        PossibleFriendsGetRequest req = new PossibleFriendsGetRequest( this);
        req.getPossibleFriends(AddFriendsActivity.this);
    }

    // send an intent with necessary information to the FriendsViewActivity when
    // a new friend is chosen and the database is succesfully updated.
    @Override
    public void postedFriends(ArrayList<Friend> FriendArraylist) {
        Intent intent = new Intent(AddFriendsActivity.this, FriendsViewActivity.class);
        intent.putExtra("id", userID);
        intent.putExtra("friends", friends.toString());
        startActivity(intent);
    }

    // give errormessage if something went wrong while updating database.
    @Override
    public void postedFriendsError(String message) {
        Toast.makeText(getApplicationContext(), "something went wrong uploading friends" +
                " , try again later", Toast.LENGTH_LONG)
                .show();
    }

    // method that, when a new friend is chosen, asks the friendsputrequesthelper
    // to update the database.
    public void putFriends() {
        FriendsPutRequest request = new FriendsPutRequest(this, userID, friends);
        request.putfriends(this);
    }

    // when a friend is clicked, show an alertdialog
    private class GridItemClickListener implements AdapterView.OnItemClickListener  {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            final Friend clickedFriend = (Friend) parent.getItemAtPosition(position);


            //alertdialog that checks if the user meant to add the clicked friend.
            //when clicked OK, the putfriend method is called.
            AlertDialog.Builder addFriend = new AlertDialog.Builder(AddFriendsActivity.this);
            addFriend.setMessage("Are you sure you want to add this friend?");

            addFriend.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {

                    friends.add(clickedFriend);
                    putFriends();
                }
            });
            addFriend.show();
        }
    }
}
