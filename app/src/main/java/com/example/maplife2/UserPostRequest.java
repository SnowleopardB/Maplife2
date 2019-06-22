package com.example.maplife2;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UserPostRequest implements Response.Listener, Response.ErrorListener{

    private String name;
    private String email;
    private String password;
    private ArrayList<Location> locations;
    private ArrayList<Friend> friends;
    private Context context;
    private Callback callback;

    public interface Callback {

        void postedScore();
        void postedScoreError(String message);
    }

    public UserPostRequest(SigninActivity aContext, String aName, String aEmail, String aPassword, ArrayList<Location> aLocations, ArrayList<Friend> aFriends) {

        name = aName;
        email = aEmail;
        password = aPassword;
        locations = aLocations;
        friends = aFriends;

        context = (Context) aContext;

    }

    @Override
    public void onErrorResponse(VolleyError error) {
        callback.postedScoreError(error.getMessage());
    }

    @Override
    public void onResponse(Object response) {
        callback.postedScore();
    }

    private class PostRequest extends StringRequest {

        public PostRequest(int method, String url, UserPostRequest listener, UserPostRequest errorListener) {

            super(method, url, listener, errorListener);
        }

        @Override
        protected Map<String, String> getParams () {

            Map<String, String> params = new HashMap<>();
            params.put("name", name);
            params.put("email", email);
            params.put("password", password);
            params.put("location", locations.toString());
            params.put("friends", friends.toString());
            return params;
        }
    }

    public void postUser(Callback activity) {

        callback = activity;
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = "https://ide50-bart1997.c9users.io:8080/Maplife2";
        PostRequest postRequest = new PostRequest(Request.Method.POST, url, this, this);
        queue.add(postRequest);
    }
}
