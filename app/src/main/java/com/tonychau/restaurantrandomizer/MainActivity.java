package com.tonychau.restaurantrandomizer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageView;

import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {

    private TextInputEditText cityEdt;
    private ImageView searchIV;
    private RecyclerView restaurantRV;

    private RestaurantRVAdapter restaurantRVAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
    }

    private void initViews() {
        cityEdt = findViewById(R.id.idEdtCity);
        searchIV = findViewById(R.id.idIVSearch);
        restaurantRV = findViewById(R.id.idRvRestaurant);

        restaurantRVAdapter = new RestaurantRVAdapter(this);
        restaurantRV.setAdapter(restaurantRVAdapter);
    }
}