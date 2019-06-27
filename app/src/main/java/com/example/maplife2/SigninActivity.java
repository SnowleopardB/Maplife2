package com.example.maplife2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class SigninActivity extends AppCompatActivity implements UserPostRequest.Callback, IdGetRequestbyEmail.Callback {

    // define global variables
    String emailString;
    int checker = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle("Register");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
    }

    // define onCLicklistener on Register button.
    public void signInClick(View view) {
        EditText name = findViewById(R.id.userName);
        EditText email = findViewById(R.id.email);
        EditText password = findViewById(R.id.passWord);
        EditText passwordCheck = findViewById(R.id.passWordCheck);

        String nameString = String.valueOf(name.getText());
        emailString = String.valueOf(email.getText());
        String passwordString = String.valueOf(password.getText());
        String passwordcheckString = String.valueOf(passwordCheck.getText());

        ArrayList<Location> locationlist = new ArrayList<>();
        ArrayList<Friend> friendslist = new ArrayList<>();

        // Check if password and check password are the same. Then call postUser.
        if (passwordString.equals(passwordcheckString)) {
            postUser(nameString, emailString, passwordString, locationlist, friendslist);
        } else {
            Toast.makeText(this, "Password and check password are not the same", Toast.LENGTH_SHORT).show();
        }
    }

    // calls the postRequest helper for a new request.
    public void postUser(String name, String email, String password, ArrayList<Location> locationlist, ArrayList<Friend> friendslist) {
        UserPostRequest request = new UserPostRequest(this, name, email, password, locationlist, friendslist);
        request.postUser(this);
    }

    // When the userdata has succesfully been added to the database, call the
    // usergetrequesthelperbyemail to retreive the users id.
    @Override
    public void postedUser() {
        IdGetRequestbyEmail req = new IdGetRequestbyEmail(this, emailString);
        req.getId(this);
    }

    // show message when something went wrong with the server.
    @Override
    public void postedUserError(String message) {
        Toast.makeText(this, "Could not connect to the server" +
                ", try another time.", Toast.LENGTH_SHORT).show();
    }

    // when the ID of the user has successfully been downloaded, make new intent to MainActivity
    // and include all the necessary information.
    @Override
    public void gotId(int id) {
        if (id != 0) {
            checker = 1;
        }
        Intent loginIntent = new Intent(SigninActivity.this, MainActivity.class);
        loginIntent.putExtra("loggedinID", id);
        loginIntent.putExtra("loggedinCheck", checker);
        startActivity(loginIntent);
    }

    // post errormessage when something went wrong with the server.
    @Override
    public void gotIdError(String message) {
        Toast.makeText(this, "Could not connect to the server" +
                ", try another time.", Toast.LENGTH_SHORT).show();
    }
}
