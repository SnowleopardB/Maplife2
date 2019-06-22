package com.example.maplife2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SigninActivity extends AppCompatActivity implements UserPostRequest.Callback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle("Login");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
    }

    public void signInClick(View view) {
        EditText name = findViewById(R.id.userName);
        EditText email = findViewById(R.id.email);
        EditText password = findViewById(R.id.passWord);
        EditText passwordCheck = findViewById(R.id.passWordCheck);

        String nameString = String.valueOf(name.getText());
        String emailString = String.valueOf(email.getText());
        String passwordString = String.valueOf(password.getText());
        String passwordcheckString = String.valueOf(passwordCheck.getText());

        ArrayList<Location> locationlist = new ArrayList<>();
        Location loc = new Location(1, 2, "hoi", "leuk");
        Location loc2 = new Location(3, 4, "hoi2", "leuk2");

        locationlist.add(loc);
        locationlist.add(loc2);

        ArrayList<Friend> friendslist = new ArrayList<>();
        Friend friend1= new Friend(1, "bart");
        Friend friend2 = new Friend(2, "Jan");

        friendslist.add(friend1);
        friendslist.add(friend2);



        if (passwordString.equals(passwordcheckString)) {
            postUser(emailString, nameString, passwordString, locationlist, friendslist);
        } else {
            Toast.makeText(this, "Password and check password are not the same", Toast.LENGTH_SHORT).show();
        }

    }

    public void postUser(String name, String email, String password, ArrayList<Location> locationlist, ArrayList<Friend> friendslist) {
        UserPostRequest request = new UserPostRequest(this, name, email, password, locationlist, friendslist);
        request.postUser(this);
    }

    @Override
    public void postedScore() {
        Intent scoreIntent = new Intent(SigninActivity.this, MainActivity.class);
        startActivity(scoreIntent);
    }

    @Override
    public void postedScoreError(String message) {
        Toast.makeText(this, "Signup failed", Toast.LENGTH_SHORT).show();
    }

    public void onBackPressed() {
        // Not able to go back after login
    }
}
