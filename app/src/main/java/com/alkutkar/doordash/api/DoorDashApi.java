package com.alkutkar.doordash.api;

import android.content.Context;
import android.util.Log;

import com.alkutkar.doordash.events.FetchRestaurantsEvent;
import com.alkutkar.doordash.events.RestaurantDetailFetchSuccessEvent;
import com.alkutkar.doordash.events.RestaurantListUpdatedEvent;
import com.alkutkar.doordash.events.ViewRestaurantDetailEvent;
import com.alkutkar.doordash.manager.CacheManager;
import com.alkutkar.doordash.models.Restaurant;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import de.greenrobot.event.EventBus;

/**
 * Created by harshalkutkar on 6/21/17.
 */

public class DoorDashApi {

    private static final String TAG = "DOORDASH-API";
    private static final String RESTAURANT_FILE_KEY = "restaurants";
    public static String baseUrl = "https://api.doordash.com/v2/";
    // Instantiate the RequestQueue.
    private RequestQueue queue;
    private Context mContext;


    public DoorDashApi(Context context)
    {
        EventBus.getDefault().register(this);
        queue = Volley.newRequestQueue(context);
    }

    /*
        Event Listeners Start Here
     */

    public void onEvent(FetchRestaurantsEvent event)
    {
        Log.i("EVENT","Fetch Restaurants Event");
        fetchRestaurantsInArea(event.getLatitude(),event.getLongitude());
    }

    public void onEvent(ViewRestaurantDetailEvent event)
    {
        Log.i("EVENT","Fetch Restaurants Details");
        fetchRestaurantDetailsForId(event.getId());
    }



    /*
        API Implementation Methods Start Here
     */

    private void fetchRestaurantDetailsForId(int id) {
        String uri = String.format(baseUrl+"restaurant/%1$s",
                Integer.toString(id)
        );

        GsonRequest<Restaurant> myReq = new GsonRequest<Restaurant>(uri,
                Restaurant.class, null,
                createRestaurantDetailSuccessListener(), // listener for success
                createRestaurantDetailErrorListener());  // listener for failure
        queue.add(myReq);
    }

    public void fetchRestaurantsInArea(double latitude, double longitude)
    {
        String uri = String.format(baseUrl+"restaurant?lat=%1$s&lng=%2$s",
                Double.toString(latitude),
                Double.toString(longitude)
        );

        GsonRequest<Restaurant[]> myReq = new GsonRequest<Restaurant[]>(uri,
                Restaurant[].class, null,
                createRestaurantListSuccessListener(), // listener for success
                createRestaurantListErrorListener());  // listener for failure
        queue.add(myReq);
    }

    private Response.Listener<Restaurant> createRestaurantDetailSuccessListener() {

        return new Response.Listener<Restaurant>() {
            @Override
            public void onResponse(Restaurant response) {
                try {
                    EventBus.getDefault().post(new RestaurantDetailFetchSuccessEvent(response));
                } catch (Exception e) {

                }
            }
        };

    }

    private Response.Listener<Restaurant[]> createRestaurantListSuccessListener() {

        return new Response.Listener<Restaurant[]>() {
            @Override
            public void onResponse(Restaurant[] response) {
                try {
                    ArrayList<Restaurant> restaurants = new ArrayList<>(Arrays.asList(response));
                    EventBus.getDefault().post(new RestaurantListUpdatedEvent(restaurants));
                } catch (Exception e) {

                }
            }
        };

    }

    private Response.ErrorListener createRestaurantListErrorListener() {

        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i(TAG, "Error : " + error.getLocalizedMessage());
            }
        };

    }

    private Response.ErrorListener createRestaurantDetailErrorListener() {

        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i(TAG, "Error : " + error.getLocalizedMessage());
            }
        };

    }


}
