package com.example.jerrychen.barapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class OrdersCustomerDetailAdapter extends ArrayAdapter<String> {
    Order myOrder;
    public OrdersCustomerDetailAdapter(@NonNull Context context, @NonNull List<String> orderId) {
        super(context, 0, orderId);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Get the data item for this position
        final String orderId = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.customer_order_list_item, parent, false);
        }
        // Lookup view for data population
        final TextView tvOrderCode=convertView.findViewById(R.id.orderCode);
        final TextView tvOrderStatus=convertView.findViewById(R.id.orderStatus);
        final ListView listView=convertView.findViewById(R.id.listViewOrderMap);
        FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
        DatabaseReference dbRef=firebaseDatabase.getReference();
        dbRef.child("orders").child(orderId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                myOrder=dataSnapshot.getValue(Order.class);
                if (myOrder!=null) {
                    if (myOrder.getStatus() == Status.started || myOrder.getStatus() == Status.paid) {
                        ProductOrderAdapter productOrderAdapter = new ProductOrderAdapter(myOrder.getOrderMap());
                        listView.setAdapter(productOrderAdapter);
                        tvOrderCode.setText(myOrder.getCode());
                        tvOrderStatus.setText(myOrder.getStatus().toString());
                    }
                }
                
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

            return convertView;
    }
}
