package com.example.jerrychen.barapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kangur on 12.04.2018.
 */

public class ProductAdapter extends ArrayAdapter<Product> {
    public ProductAdapter(Context context, ArrayList<Product> users) {
        super(context, 0, users);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Product product = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.product_layout, parent, false);
        }
        // Lookup view for data population
        ImageView ivPicture=(ImageView) convertView.findViewById(R.id.imageView);
        TextView tvName = (TextView) convertView.findViewById(R.id.textViewName);
        TextView tvPrice = (TextView) convertView.findViewById(R.id.textViewPrice);
        // Populate the data into the template view using the data object
        tvName.setText(product.getName());
        tvPrice.setText(product.getPrice()+"DKK");
        Picasso.get().load("https://i.imgur.com/x8C8WDO.jpg").into(ivPicture);
        // Return the completed view to render on screen
        return convertView;
    }
}

