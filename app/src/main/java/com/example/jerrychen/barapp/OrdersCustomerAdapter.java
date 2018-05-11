package com.example.jerrychen.barapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by jerrychen on 5/10/18.
 */

public class OrdersCustomerAdapter extends ArrayAdapter<Order> {
    public OrdersCustomerAdapter(@NonNull Context context, ArrayList<Order>orders) {
        super(context,0,orders);
    }
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
// Get the data item for this position
        final Order order = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.current_orderrs_layout_customer, parent, false);
        }
        // Lookup view for data population
        ImageView imageView=convertView.findViewById(R.id.imageView);
        final TextView  textViewName=convertView.findViewById(R.id.textViewName);
        TextView  textViewPrice=convertView.findViewById(R.id.textViewPrice);
        EditText  editText=convertView.findViewById(R.id.editTextQuantity);
        FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
        DatabaseReference dbRef=firebaseDatabase.getReference();
        FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
        FirebaseUser user=firebaseAuth.getCurrentUser();
        dbRef.child("products").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //dataSnapshot.child(order.getOrderMap())
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return convertView;
    }
}
