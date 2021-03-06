package com.alkutkar.doordash.ui;

/**
 * Created by harshalkutkar on 6/22/17.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alkutkar.doordash.R;
import com.alkutkar.doordash.models.Favorites;
import com.alkutkar.doordash.models.Restaurant;
import com.bumptech.glide.Glide;

import java.text.NumberFormat;
import java.util.Locale;

public class DetailFragment extends Fragment {

    private Restaurant restaurant;
    private Favorites favorites;


    public static DetailFragment newInstance(Restaurant passedRestaurant) {
        DetailFragment fragment = new DetailFragment();
        fragment.restaurant = passedRestaurant;
        return fragment;
    }

    // The onCreateView method is called when Fragment should create its View object hierarchy,
    // either dynamically or via XML layout inflation.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        return inflater.inflate(R.layout.fragment_rest_detail, parent, false);
    }

    // This event is triggered soon after onCreateView().
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Setup any handles to view objects here
        TextView name = (TextView) view.findViewById(R.id.txtName);
        TextView description = (TextView) view.findViewById(R.id.txtDescription);
        TextView status = (TextView) view.findViewById(R.id.txtStatus);
        TextView deliveryFee = (TextView) view.findViewById(R.id.txtDeliveryFee);
        ImageView logo = (ImageView) view.findViewById(R.id.imgLogo);
        final Button addToFavorites = (Button) view.findViewById(R.id.btnAddToFavorites);
        favorites = new Favorites(getActivity());
        addToFavorites.setEnabled(true);

        if (favorites.getFavorites().contains(restaurant.getId())) {
            addToFavorites.setText("Remove From Favorites");
        }
        addToFavorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (favorites.getFavorites().contains(restaurant.getId())) {
                    favorites.remove(restaurant.getId());
                    Toast.makeText(getActivity(),"Removed from Favorites",Toast.LENGTH_SHORT);
                } else {
                    favorites.add(restaurant.getId());
                    Toast.makeText(getActivity(),"Added To Favorites",Toast.LENGTH_SHORT);
                }
                favorites.save();
                addToFavorites.setEnabled(false);
            }
        });

        if (restaurant != null) {
            name.setText(restaurant.getName());
            description.setText(restaurant.getDescription());
            status.setText(restaurant.getStatus());
            deliveryFee.setText(centsToDollars(restaurant.getDeliveryFee()));
            Glide.with(getContext()).load(restaurant.getCoverImageUrl()).into(logo);
        }

    }

    public String centsToDollars(String cents)
    {
        long cLong = Long.parseLong(cents);
        NumberFormat n = NumberFormat.getCurrencyInstance(Locale.US);
        String s = n.format(cLong / 100.0);
        return s;
    }
}