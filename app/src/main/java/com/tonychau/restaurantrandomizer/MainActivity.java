package com.tonychau.restaurantrandomizer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;

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
                getRestaurantInfo("Boston");
            }
        });
        getRestaurantInfo("Boston");

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, LoginPageActivity.class);
                startActivity(intent);
            }
        });
    }

    private void getRestaurantInfo(String cityName) {
//        String url = "https://documenu.p.rapidapi.com/restaurant/4072702673999819";
        String url = "https://api.documenu.com/v2/restaurants/zip_code/02111?size=100&amp;page=2";
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("TAG", "onResponse: " + response.toString());
//                        textView.setText("Response: " + response.toString());
                        restaurantArrayList.add(new Restaurant("TTTT","CCCC", "https://i.imgur.com/DvpvklR.png"));
                        restaurantRVAdapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Log.d("TAG", "onResponse: FAILED - " + error.toString());
                    }
                }){
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String, String>  params = new HashMap<String, String>();
                        params.put("x-api-key", "a1c3d3ebab7e65307da7a104eff99512");
                        params.put("x-rapidapi-key", "b8a789798cmsha2cf72d2dc37a90p1f2794jsnf88eae2250d4");
                        params.put("x-rapidapi-host", "documenu.p.rapidapi.com");

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