package com.alkutkar.doordash.events;

import com.alkutkar.doordash.models.Restaurant;

/**
 * Created by harshalkutkar on 6/22/17.
 */

public class RestaurantDetailFetchSuccessEvent {

    private Restaurant restaurant;

    public RestaurantDetailFetchSuccessEvent(Restaurant restaurant)
    {
        this.restaurant = restaurant;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

}
