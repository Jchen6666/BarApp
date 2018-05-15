package com.example.jerrychen.barapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Kangur on 12.04.2018.
 */

public class ProductAdapter extends ArrayAdapter<Product> {
    private String quantity;
    private String uid;
    private boolean created,repeated;
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
        TextView tvName = (TextView) convertView.findViewById(R.id.textViewNumber);
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
                Log.d("Log_Tag"," clicked");
                FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
                FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
                FirebaseUser user=firebaseAuth.getCurrentUser();
                final DatabaseReference dbRef= firebaseDatabase.getReference();

                uid =user.getUid();
                quantity=etAmount.getText().toString();
                Log.d("Log_Tag","Userid: "+user.getUid());
                dbRef.child("users").child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.child("currentOrder").exists()){

                            myOrder=dataSnapshot.child("currentOrder").getValue(Order.class);
                            if (myOrder.getOrderMap().containsKey(product.getID())){
                                ArrayList<String> myOrderList=myOrder.getOrderMap().get(product.getID());
                                int price=Integer.parseInt(quantity)*product.getPrice()+myOrder.getPrice();
                                int newAmount=Integer.parseInt(quantity)+Integer.parseInt(myOrder.getOrderMap().get(product.getID()).get(1));
                                myOrderList.remove(1);
                                myOrderList.add(1,Integer.toString(newAmount));
                                Log.d("Log_tag","AMOUNT: "+quantity);
                                myOrder.getOrderMap().put(product.getID(),myOrderList);
                                myOrder.setPrice(price);
                                dbRef.child("users").child(uid).child("cart").setValue(myOrder);
                            }
                            else {
                                ArrayList<String>myOrderList=new ArrayList<>();
                               int price=Integer.parseInt(quantity)*product.getPrice()+myOrder.getPrice();
                               myOrderList.add(product.getCategory().toString());
                               myOrderList.add(quantity);
                               myOrder.getOrderMap().put(product.getID(), myOrderList);
                               myOrder.setPrice(price);
                               dbRef.child("users").child(uid).child("cart").setValue(myOrder);
                            }
                            Log.d("Log_Tag"," created: "+repeated+" "+created);

                        }
                        else {
                            Date date=new Date();
                            int price=Integer.parseInt(quantity)*product.getPrice();
                            ArrayList<String> myOrderList=new ArrayList<>();
                            myOrderList.add(product.getCategory().toString());
                            myOrderList.add(quantity);
                            Map<String, ArrayList<String>> myOrderMap = new HashMap<>();
                            myOrderMap.put(product.getID(),myOrderList);
                            Order myNewOrder=new Order(date,myOrderMap,Status.unpaid,price);
                            dbRef.child("users").child(uid).child("cart").setValue(myNewOrder);
                            etAmount.setText(null);
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }

        });
        // Return the completed view to render on screen
        return convertView;
    }

}

