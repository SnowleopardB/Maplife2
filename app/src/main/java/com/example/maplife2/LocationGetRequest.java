//package com.example.maplife2;
//
//
//import android.content.Context;
//import android.util.Log;
//
//import com.android.volley.RequestQueue;
//import com.android.volley.Response;
//import com.android.volley.VolleyError;
//import com.android.volley.toolbox.JsonObjectRequest;
//import com.android.volley.toolbox.Volley;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.util.ArrayList;
//
//public class LocationGetRequest implements Response.Listener<JSONObject>, Response.ErrorListener {
//
//    public static final String TAG = "LocationRequest";
//
//    private Context context2;
//    private Callback callback2;
//    private String categorysearch;
//
//    public LocationGetRequest(Context context) {
//        context2 = context;
//    }
//
//    public void getLocations(MapsActivity activity, int userID) {
//
//        callback2 = activity;
//        int number = userID;
//        String url = "https://ide50-bart1997.legacy.cs50.io:8080/Maplife5/" + number;
//        RequestQueue queue = Volley.newRequestQueue(context2);
//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null, this, this);
//        queue.add(jsonObjectRequest);
//    }
//
//    public interface Callback {
//        void gotLocations(ArrayList<Location> menuItemArrayList);
//        void gotLocationsError(String message);
//   }
//
//    @Override
//    public void onErrorResponse(VolleyError error) {
//        String errorMessage = error.getMessage();
//        error.printStackTrace();
//        callback2.gotLocationsError(errorMessage);
//    }
//
//    @Override
//    public void onResponse(JSONObject response) {
//
//        Log.d(TAG, "onResponse: " + response);
//
//        try {
//            String json = response.getString("location");
//            JSONArray test = new JSONArray(json);
//
//            JSONObject loc = test.getJSONObject(0);
//            String locOnderdeel = loc.getString("description");
//
//            Log.d(TAG, "onResponse: " + locOnderdeel );
//
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//
//
//    }
//}
//
//
//
