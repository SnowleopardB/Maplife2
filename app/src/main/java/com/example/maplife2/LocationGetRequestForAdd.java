//package com.example.maplife2;
//
//
//import android.content.Context;
//import android.util.Log;
//
//import com.android.volley.RequestQueue;
////import com.android.volley.Response;
//import com.android.volly.VolleyError;
//import com.android.volley.toolbox.JsonObjectRequest;
//import com.android.volley.toolbox.Volley;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.util.ArrayList;
//
//public class LocationGetRequestForAdd implements Response.Listener<JSONObject>, Response.ErrorListener {
//
//    public static final String TAG = "LocationRequest";
//
//    private Context context2;
//    private Callback callback;
//    private String categorysearch;
//
//    public LocationGetRequestForAdd(Context context) {
//        context2 = context;
//    }
//
//    public void getLocations(AddLocationActivity activity, String categoryname) {
//
//        callback = activity;
//        categorysearch = categoryname;
//        String url = "https://ide50-bart1997.legacy.cs50.io:8080/Maplife2/10";
//        RequestQueue queue = Volley.newRequestQueue(context2);
//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null, this, this);
//        queue.add(jsonObjectRequest);
//    }
//
//    public interface Callback {
//        void gotLocations(ArrayList<Location> LocationArraylist);
//        void gotLocationsError(String message);
//    }
//
//    @Override
//    public void onErrorResponse(VolleyError error) {
//        String errorMessage = error.getMessage();
//        error.printStackTrace();
//        callback.gotLocationsError(errorMessage);
//    }
//
//    @Override
//    public void onResponse(JSONObject response) {
//
//        Log.d(TAG, "onResponse: " + response);
//        ArrayList<Location> locationArrayList = new ArrayList<>();
//        try {
//            String json = response.getString("location");
//            JSONArray JSONlocations = new JSONArray(json);
//            for (int i = 0; i < JSONlocations.length(); i++) {
//                JSONObject onelocation = JSONlocations.getJSONObject(i);
//                Long latitude = onelocation.getLong("latitude");
//                Long longitude = onelocation.getLong("longitude");
//                String name = onelocation.getString("name");
//                String description = onelocation.getString("description");
//                Location location = new Location(latitude, longitude, name, description);
//                locationArrayList.add(location);
//
//                String locOnderdeel = onelocation.getString("description");
//                Log.d(TAG, "onResponse: " + locOnderdeel );
//
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        callback.gotLocations(locationArrayList);
//
//
//    }
//}
//
//
//
