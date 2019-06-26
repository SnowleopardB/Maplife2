package com.example.maplife2;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LocationPutRequest implements Response.Listener, Response.ErrorListener {

    // define global variables
    private int userid;
    private Context context;
    private ArrayList<Location> locations;
    private Callback callback;


    public LocationPutRequest(AddLocationActivity aContext, int userID, ArrayList<Location> aLocations) {

        userid = userID;
        context = (Context) aContext;
        locations = aLocations;

    }

    // define callback
    public interface Callback {
        void postedLocations(ArrayList<Location> LocationArraylist);
        void postedLocationsError(String message);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        callback.postedLocationsError(error.getMessage());
    }

    // onResponse, transcribe the JSONobject retreived from the database to a ArrayList of locations.
    @Override
    public void onResponse(Object response){
        ArrayList<Location> locationArrayList = new ArrayList<>();

        try {
            JSONObject responseJson = new JSONObject(response.toString());

            String json = responseJson.getString("location");
            JSONArray JSONlocations = new JSONArray(json);
            for (int i = 0; i < JSONlocations.length(); i++) {
                JSONObject onelocation = JSONlocations.getJSONObject(i);
                String latitude = onelocation.getString("latitude");
                String longitude = onelocation.getString("longitude");
                String name = onelocation.getString("name");
                String description = onelocation.getString("description");
                Location location = new Location(latitude, longitude, name, description);
                locationArrayList.add(location);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        callback.postedLocations(locationArrayList);
    }

    // the actual request to override the old location data with the new one in the database.
    private class ActualPostRequest extends StringRequest {

        public ActualPostRequest(int method, String url, LocationPutRequest listener, LocationPutRequest errorListener) {

            super(method, url, listener, errorListener);
        }

        // define the locationslist that has to be uploaded
        @Override
        protected Map<String, String> getParams () {

            Map<String, String> params = new HashMap<>();
            params.put("location", locations.toString());
            return params;
        }
    }

    // call the ActualPutRequest and add to the queue.
    public void postLocation(Callback activity) {

        callback = activity;
        RequestQueue queue = Volley.newRequestQueue(context);
        int number = userid;
        String url = "https://ide50-bart1997.c9users.io:8080/Maplife7/" + number;
        ActualPostRequest postRequest = new ActualPostRequest(Request.Method.PUT, url, this, this);
        queue.add(postRequest);
    }
}
