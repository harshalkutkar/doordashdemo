package com.alkutkar.doordash.ui;

/**
 * Created by harshalkutkar on 6/22/17.
 */

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alkutkar.doordash.GPSTracker;
import com.alkutkar.doordash.MainActivity;
import com.alkutkar.doordash.R;
import com.alkutkar.doordash.api.DoorDashApi;
import com.alkutkar.doordash.events.FetchRestaurantsEvent;
import com.alkutkar.doordash.events.RestaurantDetailFetchSuccessEvent;
import com.alkutkar.doordash.events.RestaurantListUpdatedEvent;
import com.alkutkar.doordash.events.ViewRestaurantDetailEvent;
import com.alkutkar.doordash.models.Restaurant;

import java.util.ArrayList;

import de.greenrobot.event.EventBus;

public class ListFragment extends Fragment {
    private ListView listView;
    private RestaurantListAdapter listAdapter;
    private ArrayList<Restaurant> restaurantArrayList;
    private boolean shouldFilterFavorites = false;
    private static final String SHOULD_FILTER_FAVORITES = "shouldFilterFavorites";

    DoorDashApi doorDashApi;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    public static final double doorDashHqLat = Double.parseDouble("37.422740");
    public static final double doorDashHqLon = Double.parseDouble("-122.139956");
    //Change this to false if you wish to use location provider
    private boolean usePresetValues = false;
    GPSTracker gpsTracker;
    private Activity mActivity;

    /**
     * Create a new instance of DetailsFragment, initialized to
     * show the text at 'index'.
     */
    public static ListFragment newInstance(boolean shouldFilterFavorites) {
        ListFragment f = new ListFragment();
        // Supply index input as an argument.
        Bundle args = new Bundle();
        args.putBoolean(SHOULD_FILTER_FAVORITES, shouldFilterFavorites);
        f.setArguments(args);
        return f;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(getActivity(),
                            Manifest.permission. ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {
                        gpsTracker = new GPSTracker(getActivity());

                        if(gpsTracker.canGetLocation()){
                            double latitude = gpsTracker.getLatitude();
                            double longitude = gpsTracker.getLongitude();
                            Toast.makeText(getActivity().getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
                        }else{
                            // can't get location
                            // GPS or Network is not enabled
                            // Ask user to enable GPS/network in settings
                            gpsTracker.showSettingsAlert();
                        }

                        if (usePresetValues == false) {
                            EventBus.getDefault().post(new FetchRestaurantsEvent(gpsTracker.getLatitude(), gpsTracker.getLongitude(), shouldFilterFavorites));
                        } else {
                            EventBus.getDefault().post(new FetchRestaurantsEvent(doorDashHqLat,doorDashHqLon, shouldFilterFavorites));
                        }
                    }

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.

                }
                return;
            }

        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = getActivity();
    }

    // The onCreateView method is called when Fragment should create its View object hierarchy,
    // either dynamically or via XML layout inflation.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        return inflater.inflate(R.layout.listview_fragment, parent, false);

    }

    // This event is triggered soon after onCreateView().
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Bundle args = getArguments();
        shouldFilterFavorites = args.getBoolean(SHOULD_FILTER_FAVORITES, false);
        listView=(ListView) getView().findViewById(R.id.list);
        doorDashApi = new DoorDashApi(mActivity);
        gpsTracker = new GPSTracker(getActivity());
        checkLocationPermission();
        EventBus.getDefault().register(this);
    }


    public boolean checkLocationPermission() {

        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission. ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission. ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(getActivity())
                        .setTitle(R.string.title_location_permission)
                        .setMessage(R.string.text_location_permission)
                        .setPositiveButton(R.string.string_ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        })
                        .create()
                        .show();


            } else {
                // No explanation needed, we can request the permission.
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            if(gpsTracker.canGetLocation()){

                double latitude = gpsTracker.getLatitude();
                double longitude = gpsTracker.getLongitude();

                // \n is for new line
                //Toast.makeText(getActivity().getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
            }else{
                // can't get location
                // GPS or Network is not enabled
                // Ask user to enable GPS/network in settings
                gpsTracker.showSettingsAlert();
            }

            if (usePresetValues == false) {
                EventBus.getDefault().post(new FetchRestaurantsEvent(gpsTracker.getLatitude(), gpsTracker.getLongitude(), shouldFilterFavorites));
            } else {
                EventBus.getDefault().post(new FetchRestaurantsEvent(doorDashHqLat,doorDashHqLon, shouldFilterFavorites));
            }
            return true;
        }
    }

    public void onEvent(final RestaurantDetailFetchSuccessEvent event) {
        // Begin the transaction
        FragmentTransaction ft = ((AppCompatActivity)mActivity).getSupportFragmentManager().beginTransaction();
        // Replace the contents of the container with the new fragment
        DetailFragment detailFragment = DetailFragment.newInstance(event.getRestaurant());
        ft.replace(R.id.fragment_placeholder, detailFragment);
        ft.commit();
        ((AppCompatActivity)mActivity).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    // This method will be called when the restaurant list is updated
    public void onEvent(final RestaurantListUpdatedEvent event) {
        if (event.getRestaurantList().size()==0) {
            Toast.makeText(getActivity(), "Sorry we do not have any results for you", Toast.LENGTH_LONG).show();
        } else {
            listAdapter = new RestaurantListAdapter(event.getRestaurantList(), mActivity.getApplicationContext());
            listView.setAdapter(listAdapter);
            this.restaurantArrayList = event.getRestaurantList();
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Restaurant restaurant = event.getRestaurantList().get(position);
                    EventBus.getDefault().post(new ViewRestaurantDetailEvent(restaurant.getId()));
                }
            });
        }
    }

}