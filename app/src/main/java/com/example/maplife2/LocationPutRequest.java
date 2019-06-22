package com.example.maplife2;

import android.content.Context;
import android.util.Log;

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

    private ArrayList<Location> locations;
    private Context context;
    private Callback callback;

    public LocationPutRequest(AddLocationActivity aContext, ArrayList<Location> aLocations) {

        locations = aLocations;
        context = (Context) aContext;

    }
    public interface Callback {
        void postedLocations(ArrayList<Location> LocationArraylist);
        void postedLocationsError(String message);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        callback.postedLocationsError(error.getMessage());
    }

    @Override
    public void onResponse(Object response){
        Log.d("waw", "onResponse: " + response);
        ArrayList<Location> locationArrayList = new ArrayList<>();
        try {


            JSONObject responseJson = new JSONObject(response.toString());

            String json = responseJson.getString("location");
            JSONArray JSONlocations = new JSONArray(json);
            for (int i = 0; i < JSONlocations.length(); i++) {
                JSONObject onelocation = JSONlocations.getJSONObject(i);
                Long latitude = onelocation.getLong("latitude");
                Long longitude = onelocation.getLong("longitude");
                String name = onelocation.getString("name");
                String description = onelocation.getString("description");
                Location location = new Location(latitude, longitude, name, description);
                locationArrayList.add(location);

                String locOnderdeel = onelocation.getString("description");
                Log.d("waw", "onResponse: " + locOnderdeel );

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        callback.postedLocations(locationArrayList);
    }




    private class ActualPostRequest extends StringRequest {

        public ActualPostRequest(int method, String url, LocationPutRequest listener, LocationPutRequest errorListener) {

            super(method, url, listener, errorListener);
        }

        @Override
        protected Map<String, String> getParams () {

            Map<String, String> params = new HashMap<>();
            params.put("location", locations.toString());
            return params;
        }
    }

    public void postLocation(Callback activity, User user) {

        callback = activity;
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = "https://ide50-bart1997.c9users.io:8080/Maplife2/10";
        ActualPostRequest postRequest = new ActualPostRequest(Request.Method.PUT, url, this, this);
        queue.add(postRequest);
    }
}
