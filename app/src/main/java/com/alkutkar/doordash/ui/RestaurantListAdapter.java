package com.alkutkar.doordash.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.alkutkar.doordash.R;
import com.alkutkar.doordash.models.Restaurant;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by harshalkutkar on 6/21/17.
 */

public class RestaurantListAdapter extends ArrayAdapter<Restaurant> implements View.OnClickListener {

    private ArrayList<Restaurant> dataSet;
    Context mContext;

    @Override
    public void onClick(View v) {
        //TODO: Write code for on click
    }

    // View lookup cache
    private static class ViewHolder {
        TextView txtName;
        TextView txtDescription;
        TextView txtStatus;
        ImageView imgCoverImage;
    }

    public RestaurantListAdapter(ArrayList<Restaurant> data, Context context) {
        super(context, R.layout.restaurant_row, data);
        this.dataSet = data;
        this.mContext = context;

    }

    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Restaurant dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.restaurant_row, parent, false);
            viewHolder.txtName = (TextView) convertView.findViewById(R.id.name);
            viewHolder.txtDescription = (TextView) convertView.findViewById(R.id.description);
            viewHolder.txtStatus = (TextView) convertView.findViewById(R.id.status);
            viewHolder.imgCoverImage = (ImageView) convertView.findViewById(R.id.cover_image);

            result = convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        /*
        Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
        result.startAnimation(animation);
        */
        lastPosition = position;

        viewHolder.txtName.setText(dataModel.getName());
        viewHolder.txtStatus.setText(dataModel.getStatus());
        viewHolder.txtDescription.setText(dataModel.getDescription());
        Glide.with(mContext).load(dataModel.getCoverImageUrl()).into(viewHolder.imgCoverImage);
        // Return the completed view to render on screen
        return convertView;
    }
}
