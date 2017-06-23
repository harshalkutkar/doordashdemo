package com.alkutkar.doordash.events;

/**
 * Created by harshalkutkar on 6/21/17.
 */

public class FetchRestaurantsEvent {

    private double latitude = 0.0;
    private double longitude = 0.0;

    private boolean shouldFilterFavorites = false;

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public FetchRestaurantsEvent(double latitude,double longitude, boolean shouldFilterFavorites) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.shouldFilterFavorites = shouldFilterFavorites;
    }

    public boolean shouldFilterFavorites() {
        return shouldFilterFavorites;
    }

    public void setShouldFilterFavorites(boolean shouldFilterFavorites) {
        this.shouldFilterFavorites = shouldFilterFavorites;
    }
}
