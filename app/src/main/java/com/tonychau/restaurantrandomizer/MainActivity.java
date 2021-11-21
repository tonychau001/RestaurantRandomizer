package com.tonychau.restaurantrandomizer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private TextInputEditText cityEdt;
    private ImageView searchIV;
    private RecyclerView restaurantRV;
    private Button btnLogin;

    private RestaurantRVAdapter restaurantRVAdapter;
    private ArrayList<Restaurant> restaurantArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

        String url = "http://my-json-feed";

        searchIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String city = cityEdt.getText().toString();
                if(city.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please enter city name", Toast.LENGTH_SHORT).show();
                } else {
                    getRestaurantInfo(city);
                }
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, LoginPageActivity.class);
                startActivity(intent);
            }
        });
    }

    private void getRestaurantInfo(String cityName) {
        // This one is for per business with business ID
//        String url = "https://api.yelp.com/v3/businesses/WavvLdfdP6g8aZTtbBQHTw";
        String url = "https://api.yelp.com/v3/businesses/search?location="+ cityName +"&term=restaurants";
        Log.d("TAG", "onResponse: " + url.toString());
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String total = response.getString("total");
                            JSONArray businessesObj = response.getJSONArray("businesses");
                            JSONObject restaurantOne = businessesObj.getJSONObject(3);
                            Log.d("TAG", "onResponse: " + response.toString());
                            Log.d("TAG", "onResponse: " + total.toString());
                            Log.d("TAG", "onResponse: " + restaurantOne.toString());

                            String id = restaurantOne.getString("id");
                            String name = restaurantOne.getString("name");
                            String imageUrl = restaurantOne.getString("image_url");

//                        textView.setText("Response: " + response.toString());
                            restaurantArrayList.clear();
                            restaurantArrayList.add(new Restaurant(id, name, imageUrl));
                            restaurantArrayList.add(new Restaurant(id, name, imageUrl));
                            restaurantArrayList.add(new Restaurant(id, name, imageUrl));
                            restaurantArrayList.add(new Restaurant(id, name, imageUrl));
                            restaurantArrayList.add(new Restaurant(id, name, imageUrl));
                            restaurantArrayList.add(new Restaurant(id, name, imageUrl));
                            restaurantRVAdapter.notifyDataSetChanged();
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
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(500000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(jsonObjectRequest);
    }

    private void initViews() {
        cityEdt = findViewById(R.id.idEdtCity);
        searchIV = findViewById(R.id.idIVSearch);
        restaurantRV = findViewById(R.id.idRvRestaurant);
        btnLogin = findViewById(R.id.btnLogin);

        restaurantArrayList = new ArrayList<>();
        restaurantRVAdapter = new RestaurantRVAdapter(this, restaurantArrayList);
        restaurantRV.setAdapter(restaurantRVAdapter);
    }
}