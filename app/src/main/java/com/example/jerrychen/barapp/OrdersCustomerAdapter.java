package com.example.jerrychen.barapp;

import android.app.Activity;
import android.content.Context;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
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
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jerrychen on 5/10/18.
 */

public class OrdersCustomerAdapter extends BaseAdapter {
    private Map<String, ArrayList<String>> mData = new HashMap<String, ArrayList<String>>();
    private String[] mKeys;
    private int quantity;
    private int totalPrice;
    private int singlePrice;
    private Order myOrder;

    public OrdersCustomerAdapter(Map<String,ArrayList<String>>data,Order order) {
        mData=data;
        mKeys = mData.keySet().toArray(new String[data.size()]);
        myOrder=order;
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

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
// Get the data item for this position
        final String key=mKeys[position];
        final ArrayList<String>value=mData.get(mKeys[position]);
        final Product product;
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.current_orderrs_layout_customer, parent, false);
        }
        // Lookup view for data population
        final  ImageView imageView=convertView.findViewById(R.id.imageView);
        final  TextView  textViewName=convertView.findViewById(R.id.textViewName);
        final  TextView  textViewPrice=convertView.findViewById(R.id.textViewPrice);
        final  EditText  editText=convertView.findViewById(R.id.editTextQuantity);
        FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
        DatabaseReference dbRef=firebaseDatabase.getReference();
       // textViewName.setText("qwd");
        final View finalConvertView = convertView;
        totalPrice=myOrder.getPrice();
        dbRef.child("Products").child(value.get(0)).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                    Iterable<DataSnapshot>children=dataSnapshot.getChildren();
                    for (DataSnapshot child:children){
                        final Product temp=(child.getValue(Product.class));
                        if (temp.getID().equals(key)){
                         //   Log.d("Tag","TAG NAME"+ temp.getName());
                            quantity=Integer.parseInt(value.get(1));
                            singlePrice=quantity*temp.getPrice();
                            textViewPrice.setText(singlePrice+" krr");
                            textViewName.setText(temp.getName());
                            Picasso.get().load(temp.getPictureUrl()).into(imageView);
                            editText.setText(value.get(1));
                            editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                                @Override
                                public void onFocusChange(View view, boolean b) {
                                    quantity=Integer.parseInt(editText.getText().toString());
                                    totalPrice=totalPrice-singlePrice+Integer.parseInt(editText.getText().toString())*temp.getPrice();
                                    myOrder.setPrice(totalPrice);
                                    value.set(1,editText.getText().toString());
                                    Log.d("TAG","Tagvalue: "+myOrder.getPrice());
                                    singlePrice=quantity*temp.getPrice();
                                    textViewPrice.setText(singlePrice+" krr");

                                }
                            });
                        }
                    }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) { }
        });
        return convertView;
    }

}
