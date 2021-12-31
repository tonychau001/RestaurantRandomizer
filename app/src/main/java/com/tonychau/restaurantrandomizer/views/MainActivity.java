package com.tonychau.restaurantrandomizer.views;

import static com.tonychau.restaurantrandomizer.views.UserActivity.LOGGED_IN;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.tonychau.restaurantrandomizer.R;
import com.tonychau.restaurantrandomizer.adapter.RestaurantRVAdapter;
import com.tonychau.restaurantrandomizer.models.Restaurant;
import com.tonychau.restaurantrandomizer.viewmodels.MainActivityViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextInputEditText cityEdt;
    private ImageView searchIV, idIVAccountBox, imgWinnerRestaurant, idIVWinnerYelpStars;
    private RecyclerView restaurantRV;
    private Button btnLogin;
    private TextView txtWinnerRestaurantName, idTVWinnerCruisineType;

    private RestaurantRVAdapter restaurantRVAdapter;

    private MainActivityViewModel mainActivityViewModel;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

        // ViewModel
        // TODO: Need to get phone location instead of boston
        mainActivityViewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);
        mainActivityViewModel.init(this, "Boston");
        mainActivityViewModel.getRestaurants().observe(this, new Observer<List<Restaurant>>() {
            @Override
            public void onChanged(@Nullable List<Restaurant> restaurants) {
                restaurantRVAdapter.notifyDataSetChanged();
            }
        });
        recyclerInit();

        searchIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String city = cityEdt.getText().toString();
                if(city.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please enter city name", Toast.LENGTH_SHORT).show();
                } else {
                    mainActivityViewModel.searchRestaurants(MainActivity.this, city);
                }
            }
        });

        idIVAccountBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: If logged in, go to UserActivity. Else, LoginPageActivity.
                if(LOGGED_IN) {
                    Intent intent = new Intent(MainActivity.this, UserActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(MainActivity.this, LoginPageActivity.class);
                    startActivity(intent);
                }
            }
        });

        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectLeakedClosableObjects()
                .penaltyLog()
                .build());

        SystemClock.sleep(1000);
    }

//    private void getRestaurantInfo(String cityName) {
//        String url = "https://api.yelp.com/v3/businesses/search?location="+ cityName +"&term=restaurants";
//        Log.d("TAG", "onResponse: " + url.toString());
//        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
//
//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
//                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
//
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        try {
//                            Random rand = new Random();
//                            int total = Integer.parseInt(response.getString("total"));
//                            JSONArray businessesObj = response.getJSONArray("businesses");
//                            restaurantArrayList.clear();
//
//                            // THIS is the winner BOX
//                            JSONObject restaurantPick = businessesObj.getJSONObject(rand.nextInt(20));
//                            txtWinnerRestaurantName.setText(restaurantPick.getString("name"));
//                            String winnerCrusineType = restaurantPick.getString("price") + " - " +
//                                    restaurantPick.getJSONArray("categories").getJSONObject(0).getString("alias");
//                            idTVWinnerCruisineType.setText(winnerCrusineType);
//                            Picasso.get().load(restaurantPick.getString("image_url")).into(imgWinnerRestaurant);
//
//                            // This is all the other restaurants.
//                            for(int i = 0; i < 10; i++) {
//                                JSONObject restaurantOne = businessesObj.getJSONObject(rand.nextInt(20));
//
//                                String id = restaurantOne.getString("id");
//                                String name = restaurantOne.getString("name");
//                                String imageUrl = restaurantOne.getString("image_url");
//                                String yelpStars = restaurantOne.getString("rating");
//                                String cruisineType = restaurantOne.getString("price") + " - " +
//                                        restaurantOne.getJSONArray("categories").getJSONObject(0).getString("alias");
//
//                                restaurantArrayList.add(new Restaurant(id, name, imageUrl, yelpStars, cruisineType));
//                            }
//                            restaurantRVAdapter.notifyDataSetChanged();
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }, new Response.ErrorListener() {
//
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        // TODO: Handle error
//                        Log.d("TAG", "onResponse: FAILED - " + error.toString());
//                    }
//                }) {
//                    @Override
//                    public Map<String, String> getHeaders() throws AuthFailureError {
//                        Map<String, String>  params = new HashMap<String, String>();
//                        params.put("Authorization", "Bearer EDTlBnp0YIVTu0MOPfC0KfxU0Pld1UZbDMye9jhXMvDYN_P27oGoKpTJowehJ_OkmzAWfvHcAVOMUG5LIjDoz3eTiJvskJ2TQj9Z0zPPjI0S0HmClw4WP0Vxy5iJYXYx");
//                        return params;
//                    }
//        };
//        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(500000,
//                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        requestQueue.add(jsonObjectRequest);
//    }

    private void initViews() {
        cityEdt = findViewById(R.id.idEdtCity);
        searchIV = findViewById(R.id.idIVSearch);
        restaurantRV = findViewById(R.id.idRvRestaurant);
        idIVAccountBox = findViewById(R.id.idIVAccountBox);

        imgWinnerRestaurant = findViewById(R.id.imgWinnerRestaurant);
        txtWinnerRestaurantName = findViewById(R.id.txtWinnerRestaurantName);
        idIVWinnerYelpStars = findViewById(R.id.idIVWinnerYelpStars);
        idTVWinnerCruisineType = findViewById(R.id.idTVWinnerCruisineType);

//        txtWinnerRestaurantName.setText(mainActivityViewModel.getRestaurants().getValue().get(1).getName());
//        idTVWinnerCruisineType.setText(mainActivityViewModel.getRestaurants().getValue().get(0).getCruisineType());
//        Picasso.get().load(mainActivityViewModel.getRestaurants().getValue().get(0).getImageUrl()).into(imgWinnerRestaurant);
    }

    private void recyclerInit(){
        restaurantRVAdapter = new RestaurantRVAdapter(this, mainActivityViewModel.getRestaurants().getValue());
        restaurantRV.setAdapter(restaurantRVAdapter);
    }
}