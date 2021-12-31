package com.tonychau.restaurantrandomizer.viewmodels;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.tonychau.restaurantrandomizer.models.Restaurant;
import com.tonychau.restaurantrandomizer.repositories.RestaurantsRepository;

import java.util.List;

public class MainActivityViewModel extends ViewModel {

    private MutableLiveData<List<Restaurant>> mRestaurants;
    private RestaurantsRepository mRepo;

    public void init(Context mContext, String cityName) {
        if(mRestaurants != null){
            return;
        }
        mRepo = RestaurantsRepository.getInstance();
        mRestaurants = mRepo.getRestaurants(mContext, cityName);
    }

    public LiveData<List<Restaurant>> getRestaurants() {
        return this.mRestaurants;
    }

    public void searchRestaurants(Context mContext, String cityName) {
        mRestaurants = mRepo.getRestaurants(mContext, cityName);
    }
}
