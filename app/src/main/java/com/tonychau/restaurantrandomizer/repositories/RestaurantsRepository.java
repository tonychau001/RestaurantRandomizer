package com.tonychau.restaurantrandomizer.repositories;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.tonychau.restaurantrandomizer.models.Restaurant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class RestaurantsRepository {

    private static RestaurantsRepository instance;
    private List<Restaurant> dataSet = new ArrayList<>();

    public static RestaurantsRepository getInstance() {
        if(instance == null) {
            instance = new RestaurantsRepository();
        }
        return instance;
    }

    public MutableLiveData<List<Restaurant>> getRestaurants(Context mContext, String cityName) {
        setRestaurants(mContext, cityName);
        MutableLiveData<List<Restaurant>> data = new MutableLiveData<>();
        data.setValue(dataSet);
        return data;
    }

    public void setRestaurants(Context mContext, String cityName) {
        String url = "https://api.yelp.com/v3/businesses/search?location="+ cityName +"&term=restaurants";
        Log.d("TAG", "onResponse: URL" + url.toString());
        RequestQueue requestQueue = Volley.newRequestQueue(mContext);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Random rand = new Random();
                            int total = Integer.parseInt(response.getString("total"));
                            JSONArray businessesObj = response.getJSONArray("businesses");
                            dataSet.clear();

                            // This is all the other restaurants.
                            for(int i = 0; i < 10; i++) {
                                JSONObject restaurantOne = businessesObj.getJSONObject(rand.nextInt(20));

                                String id = restaurantOne.getString("id");
                                String name = restaurantOne.getString("name");
                                String imageUrl = restaurantOne.getString("image_url");
                                String yelpStars = restaurantOne.getString("rating");
                                String cruisineType = "type";
//                                        restaurantOne.getString("price") + " - " +
//                                        restaurantOne.getJSONArray("categories").getJSONObject(0).getString("alias");

                                dataSet.add(new Restaurant(id, name, imageUrl, yelpStars, cruisineType));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Log.d("TAG", "onResponse: FAILED - " + error.toString());
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("Authorization", "Bearer EDTlBnp0YIVTu0MOPfC0KfxU0Pld1UZbDMye9jhXMvDYN_P27oGoKpTJowehJ_OkmzAWfvHcAVOMUG5LIjDoz3eTiJvskJ2TQj9Z0zPPjI0S0HmClw4WP0Vxy5iJYXYx");
                return params;
            }
        };
//        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(500000,
//                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(jsonObjectRequest);
    }
}
