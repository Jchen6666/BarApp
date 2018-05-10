package com.example.jerrychen.barapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Kangur on 09.05.2018.
 */

public class OrdersStaffAdapter extends ArrayAdapter<Order> {
    public OrdersStaffAdapter(Context context, ArrayList<Order> orders) {
        super(context, 0, orders);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Get the data item for this position
        final Order order = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.current_orders_layout_staff, parent, false);
        }
        // Lookup view for data population
        TextView tvOrderNumber=(TextView)convertView.findViewById(R.id.textViewNumber);
        TextView tvOrderTime=(TextView)convertView.findViewById(R.id.textViewTime);
        RelativeLayout relativeLayout=(RelativeLayout)convertView.findViewById(R.id.relativeLayoutOrders);
        String id="#"+order.getCode();
        tvOrderNumber.setText(id);
        SimpleDateFormat dateFormat=new SimpleDateFormat("HH:mm dd/MM");
        tvOrderTime.setText(dateFormat.format(order.getDate().getTime()));
        relativeLayout.setBackgroundColor(Color.parseColor(order.getColor()));

        // Return the completed view to render on screen
        return convertView;
    }
}
