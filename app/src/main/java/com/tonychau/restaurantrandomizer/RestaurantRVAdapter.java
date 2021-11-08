package com.tonychau.restaurantrandomizer;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RestaurantRVAdapter extends RecyclerView.Adapter<RestaurantRVAdapter.ViewHolder> {

    private ArrayList<Restaurant> restaurants = new ArrayList<>();
    private Context mContext;

    public RestaurantRVAdapter(Context mContext) {
        this.mContext = mContext;

        restaurants.add(new Restaurant("89", "Zaz0","https://i.imgur.com/DvpvklR.png"));
        restaurants.add(new Restaurant("89", "Zaz1","https://i.imgur.com/DvpvklR.png"));
        restaurants.add(new Restaurant("89", "Zaz2","https://i.imgur.com/DvpvklR.png"));
        restaurants.add(new Restaurant("89", "Zaz2","https://i.imgur.com/DvpvklR.png"));
        restaurants.add(new Restaurant("89", "Zaz2","https://i.imgur.com/DvpvklR.png"));


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
//        Glide.with(mContext)
//                .asBitmap()
//                .load(restaurants.get(position).getImageUrl())
//                .dontAnimate()
//                .into(holder.imgBook);

        Picasso.get().load(restaurants.get(position).getImageUrl()).into(holder.imgBook);
    }

    @Override
    public int getItemCount() {
        return restaurants.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private CardView parent;
        private ImageView imgBook;
        private TextView txtName;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            parent = itemView.findViewById(R.id.parent);
            imgBook = itemView.findViewById(R.id.imgBook);
            txtName = itemView.findViewById(R.id.txtRestaurantName);
        }
    }
}
