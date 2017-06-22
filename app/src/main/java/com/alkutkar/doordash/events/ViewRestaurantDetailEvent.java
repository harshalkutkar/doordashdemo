package com.alkutkar.doordash.events;

/**
 * Created by harshalkutkar on 6/22/17.
 */

public class ViewRestaurantDetailEvent {

    int id;

    public ViewRestaurantDetailEvent(int id)
    {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
