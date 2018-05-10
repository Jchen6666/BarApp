package com.example.jerrychen.barapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

/**
 * Created by jerrychen on 5/10/18.
 */

public class OrdersCustomerAdapter extends ArrayAdapter<Order> {
    public OrdersCustomerAdapter(@NonNull Context context, ArrayList<Order>orders) {
        super(context,0,orders);
    }
//    @NonNull
//    @Override
//    public View getView(int position, View converView, ViewGroup parent){
//
//    }
}
