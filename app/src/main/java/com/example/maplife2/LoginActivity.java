package com.example.maplife2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity implements LoginDataGetRequest.Callback{

    // define global variables
    EditText userName;
    EditText password;
    int checker = 0;
    String loginUsername;
    String loginPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        userName = findViewById(R.id.userNameText);
        password = findViewById(R.id.passwordText);
    }

    // define the onclicklistener of the I have no account button.
    public void goToSignInClick(View view) {
        Intent intent = new Intent(LoginActivity.this, SigninActivity.class);
        startActivity(intent);
    }

    // define the onclicklistnere of the login button.
    public void logInClick(View view) {
        LoginDataGetRequest req = new LoginDataGetRequest(this);
        req.getLoginData(this);
    }

    // when the loginData has succesfully been retreived from the database, compare them to
    // the filled in data.
    @Override
    public void gotLoginData(ArrayList<User> loginData) {

        loginUsername = String.valueOf(userName.getText());
        loginPassword = String.valueOf(password.getText());

        // Search for matching username and password in database.
        for (int i = 0; i < loginData.size(); i++){
            User tempUser = loginData.get(i);
            String tempUsername = tempUser.getName();
            if (loginUsername.equals(tempUsername)){
                String tempUserpassword = tempUser.getPassword();
                if (loginPassword.equals(tempUserpassword)){
                    checker = 1;
                    Toast.makeText(this , "Login succesful, welcome back " + tempUsername + "!", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra("loggedinID", tempUser.getId());
                    intent.putExtra("loggedinCheck", checker);
                    startActivity(intent);
                }
            }
        }

        // if login was not succesful, show message.
        if(checker == 0) {
            Toast.makeText(this, "Incorrect username/password.", Toast.LENGTH_LONG).show();
        }
    }

    // show message if something went wrong retreiving data
    @Override
    public void gotLoginDataError(String message) {
        Toast.makeText(this, "Problem with retreiving data, " +
                "please try again later!", Toast.LENGTH_LONG).show();
    }

    public void onBackPressed() {
        // Not able to go back to anywhere
    }
}
