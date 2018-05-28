package com.example.jerrychen.barapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Adapter class extending {@link BaseAdapter}
 * interprets the data passed for a listView
 */

public class ProductOrderAdapter extends BaseAdapter {

    private Map<String, ArrayList<String>> mData = new HashMap<String, ArrayList<String>>();
    private String[] mKeys;
    public ProductOrderAdapter(Map<String, ArrayList<String>> data){
        mData  = data;
        mKeys = mData.keySet().toArray(new String[data.size()]);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(mKeys[position]);
    }

    @Override
    public long getItemId(int arg0) {
        return arg0;
    }

    @Override
    public View getView(int pos, View convertView, ViewGroup parent) {
        final String key = mKeys[pos];
        ArrayList<String> Value = mData.get(mKeys[pos]);
        final Product product;
        //do your view stuff here
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.staff_order_list_item, parent, false);
        }
        final TextView tvName=(TextView)convertView.findViewById(R.id.textViewName);
        final TextView tvQuantity=(TextView)convertView.findViewById(R.id.textViewQuantity);
        FirebaseDatabase database=FirebaseDatabase.getInstance();
        DatabaseReference databaseReference=database.getReference();
        databaseReference.child("Products").child(Value.get(0)).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Iterable<DataSnapshot> children = dataSnapshot.getChildren();

                    for (DataSnapshot child : children) {
                        Product temp = (child.getValue(Product.class));
                        if (temp.getID().equals(key)) {
                            tvName.setText(temp.getName());
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        tvQuantity.setText(Value.get(1));






        return convertView;
    }
}