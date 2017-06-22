package com.alkutkar.doordash.events;

import com.alkutkar.doordash.models.Restaurant;

import java.util.ArrayList;

/**
 * Created by harshalkutkar on 6/21/17.
 */

public class RestaurantListUpdatedEvent {

    ArrayList<Restaurant> restaurantArrayList;

    public RestaurantListUpdatedEvent(ArrayList<Restaurant> restaurantArrayList) {
        this.restaurantArrayList = restaurantArrayList;
    }

    public ArrayList<Restaurant> getRestaurantList() {
        return restaurantArrayList;
    }

}
