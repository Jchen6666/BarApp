package com.example.jerrychen.barapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class OrdersCustomerDetailActivity extends AppCompatActivity {
   private ListView listView;
  private  FirebaseDatabase firebaseDatabase;
   private FirebaseAuth firebaseAuth;
    private ArrayList<String> CURRENT_ORDERSID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders_customer_detail);
        listView=findViewById(R.id.listViewCurrentOrders);
        firebaseDatabase=FirebaseDatabase.getInstance();
        firebaseAuth=FirebaseAuth.getInstance();
        DatabaseReference dbRef=firebaseDatabase.getReference();
        FirebaseUser user=firebaseAuth.getCurrentUser();
        dbRef.child("users").child(user.getUid()).child("currentOrder").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot>children=dataSnapshot.getChildren();
                CURRENT_ORDERSID=new ArrayList<>();
                for (DataSnapshot child:children){
                    String id=child.getValue(String.class);
                    CURRENT_ORDERSID.add(id);
                }
                if (listView!=null){
                    updateListView(listView,CURRENT_ORDERSID);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        if (listView!=null&&CURRENT_ORDERSID!=null) {
            OrdersCustomerDetailAdapter ordersCustomerDetailAdapter = new OrdersCustomerDetailAdapter(OrdersCustomerDetailActivity.this, CURRENT_ORDERSID);
             listView.setAdapter(ordersCustomerDetailAdapter);
        }
    }
    public void updateListView(ListView listView,ArrayList<String> CURRENT_ORDERSID){

        OrdersCustomerDetailAdapter ordersCustomerDetailAdapter=new OrdersCustomerDetailAdapter(OrdersCustomerDetailActivity.this,CURRENT_ORDERSID);
        listView.setAdapter(ordersCustomerDetailAdapter);


    }

}
