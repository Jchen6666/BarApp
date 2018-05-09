package com.example.jerrychen.barapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Kangur on 12.04.2018.
 */

public class ProductAdapter extends ArrayAdapter<Product> {
    private int amount;
    private String uid;
    private Date date;
    boolean created=false;
    Order myOrder;
    public ProductAdapter(Context context, ArrayList<Product> users) {
        super(context, 0, users);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Get the data item for this position
        final Product product = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.product_layout, parent, false);
        }

        // Lookup view for data population
        ImageView ivPicture=(ImageView) convertView.findViewById(R.id.imageView);
        TextView tvName = (TextView) convertView.findViewById(R.id.textViewName);
        TextView tvPrice = (TextView) convertView.findViewById(R.id.textViewPrice);
        final EditText etAmount=convertView.findViewById(R.id.editTextAmount);
        Button buttonOrder=convertView.findViewById(R.id.buttonOrder);
        // Populate the data into the template view using the data object
        tvName.setText(product.getName());
        tvPrice.setText(product.getPrice()+"DKK");
        try {
            Picasso.get().load(product.getPictureUrl()).into(ivPicture);
        }catch(IllegalArgumentException e){
            e.printStackTrace();
        }
        buttonOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
                FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
                FirebaseUser user=firebaseAuth.getCurrentUser();
                final DatabaseReference dbRef= firebaseDatabase.getReference();
                date=new Date();
                uid =user.getUid();
                amount=Integer.parseInt(etAmount.getText().toString());

                dbRef.child("users").child(uid).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.child("currentOrder").exists()){
                            created=true;
                            Log.d("Log_Tag"," created: true");
                            myOrder=dataSnapshot.child("currentOrder").getValue(Order.class);
                       }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

               if (created){
                   int price;
                   if (myOrder.getOrderMap().containsKey(product.getID())){
                       price=amount*product.getPrice();
                       amount=amount+myOrder.getOrderMap().get(product.getID());
                       Log.d("Log_tag","AMOUNT: "+amount);
                       myOrder.getOrderMap().put(product.getID(),amount);
                       myOrder.setPrice(price);
                       myOrder.setDate(date);
                       dbRef.child("users").child(uid).child("currentOrder").setValue(myOrder);

                   }else {
                     //  price=amount*product.getPrice()+myOrder.getPrice();
                       myOrder.getOrderMap().put(product.getID(), amount);
                       myOrder.setDate(date);
                       dbRef.child("users").child(uid).child("currentOrder").setValue(myOrder);
                   }

            }
 //           else {
//                   Map<String,Integer> myOrderMap=new HashMap<>();
//                   myOrderMap.put(product.getID(),amount);
//                   int price=amount*product.getPrice();
//                   Order myOrder=new Order(date,myOrderMap,Status.unpaid,price);
//                   dbRef.child("users").child(uid).child("currentOrder").setValue(myOrder);
//               }

            }
        });
        // Return the completed view to render on screen
        return convertView;
    }

}

