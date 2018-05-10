package com.example.jerrychen.barapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

/**
 * Created by jerrychen on 5/10/18.
 */

public class OrdersAdapter extends ArrayAdapter<Order> {
    public OrdersAdapter(@NonNull Context context, ArrayList<Order>orders) {
        super(context,0,orders);
    }
}
