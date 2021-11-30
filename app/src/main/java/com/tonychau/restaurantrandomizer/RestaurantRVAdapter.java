package com.tonychau.restaurantrandomizer;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RestaurantRVAdapter extends RecyclerView.Adapter<RestaurantRVAdapter.ViewHolder> {

    private ArrayList<Restaurant> restaurants = new ArrayList<>();
    private Context mContext;

    public RestaurantRVAdapter(Context mContext, ArrayList<Restaurant> restaurants) {
        this.mContext = mContext;
        this.restaurants = restaurants;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_retaurant, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d("TAG", "onBindViewHolder: Called");
        holder.txtName.setText(restaurants.get(position).getName());
        Picasso.get().load(restaurants.get(position).getImageUrl()).into(holder.imgRestaurant);

        switch(restaurants.get(position).getYelpStars()) {
            case "0":
                holder.yelpStars.setImageResource(R.mipmap.stars_small_0);
                break;
            case "1":
                holder.yelpStars.setImageResource(R.mipmap.stars_small_1);
                break;
            case "1.5":
                holder.yelpStars.setImageResource(R.mipmap.stars_small_1_half);
                break;
            case "2":
                holder.yelpStars.setImageResource(R.mipmap.stars_small_2);
                break;
            case "2.5":
                holder.yelpStars.setImageResource(R.mipmap.stars_small_2_half);
                break;
            case "3":
                holder.yelpStars.setImageResource(R.mipmap.stars_small_3);
                break;
            case "3.5":
                holder.yelpStars.setImageResource(R.mipmap.stars_small_3_half);
                break;
            case "4":
                holder.yelpStars.setImageResource(R.mipmap.stars_small_4);
                break;
            case "4.5":
                holder.yelpStars.setImageResource(R.mipmap.stars_small_4_half);
                break;
            case "5":
                holder.yelpStars.setImageResource(R.mipmap.stars_small_5);
                break;
            default:
                break;
        }
        holder.cruisineType.setText(restaurants.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return restaurants.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private CardView parent;
        private ImageView imgRestaurant, yelpStars;
        private TextView txtName, cruisineType;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            parent = itemView.findViewById(R.id.parent);
            imgRestaurant = itemView.findViewById(R.id.imgRestaurant);
            txtName = itemView.findViewById(R.id.txtRestaurantName);
            yelpStars = itemView.findViewById(R.id.idIVYelpStars);
            cruisineType = itemView.findViewById(R.id.idTVCruisineType);
        }
    }
}
