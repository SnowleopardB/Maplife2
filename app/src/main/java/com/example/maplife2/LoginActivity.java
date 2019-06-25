package com.example.maplife2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity implements LoginDataGetRequest.Callback{

    EditText userName;
    EditText password;
    int checker = 0;
    String loginUsername;
    String loginPassword;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        userName = findViewById(R.id.userNameText);
        password = findViewById(R.id.passwordText);
    }

    public void goToSignInClick(View view) {
        Intent intent = new Intent(LoginActivity.this, SigninActivity.class);
        startActivity(intent);
    }

    public void logInClick(View view) {
        LoginDataGetRequest req = new LoginDataGetRequest(this);
        req.getLoginData(this);
    }


    @Override
    public void gotLoginData(ArrayList<User> loginData) {
        loginUsername = String.valueOf(userName.getText());

        loginPassword = String.valueOf(password.getText());

        for (int i = 0; i < loginData.size(); i++){

            User tempUser = loginData.get(i);
            String tempUsername = tempUser.getName();
            Toast.makeText(this, tempUsername, Toast.LENGTH_LONG).show();
            Log.d("loginwaaaw", tempUsername);
            if (loginUsername.equals(tempUsername)){
                String tempUserpassword = tempUser.getPassword();
                if (loginPassword.equals(tempUserpassword)){

                    checker = 1;
                    Toast.makeText(this , "Login succesful, welcome back " + tempUsername + "!", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);

//                    Log.d(TAG, "retreived liftspots:" + retrievedLiftspots.get(0).getName());
                    intent.putExtra("loggedinID", tempUser.getId());
                    intent.putExtra("loggedinCheck", checker);
                    startActivity(intent);
                }
            }
        }

        if(checker == 0) {
            Toast.makeText(this, "Incorrect username/password." + loginPassword + loginUsername, Toast.LENGTH_LONG).show();

        }

    }

    @Override
    public void gotLoginDataError(String message) {

    }
}
