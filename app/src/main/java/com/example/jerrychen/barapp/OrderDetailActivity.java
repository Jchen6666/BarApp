package com.example.jerrychen.barapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
/**
 * A simple activity showing details of an order (from a staff perspective)
 * @see AppCompatActivity
 */
public class OrderDetailActivity extends AppCompatActivity {

    private ListView listViewProducts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        android.support.v7.app.ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        final Order order = (Order) getIntent().getSerializableExtra("Order");
        Log.d("CurrentOrder","CurrentOrderID: "+order.getId());

        TextView tvCode=(TextView)findViewById(R.id.textViewOrderCode);
        final TextView tvStatus=(TextView)findViewById(R.id.textViewOrderStatus);
        String code="Order #"+order.getCode();
        tvCode.setText(code);
        listViewProducts=(ListView)findViewById(R.id.listViewProductsOrder);
        ProductOrderAdapter productOrderAdapter=new ProductOrderAdapter(order.getOrderMap());
        listViewProducts.setAdapter(productOrderAdapter);

        final Button bStart=findViewById(R.id.buttonStart);
        final Button bFinish=findViewById(R.id.buttonFinish);
        bFinish.setEnabled(false);
        bStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase database=FirebaseDatabase.getInstance();
                DatabaseReference databaseReference=database.getReference();
                databaseReference.child("orders").child(order.getId()).child("status").setValue(Status.started);
                Toast.makeText(getApplicationContext(),"Order successfully started",Toast.LENGTH_SHORT).show();
                bFinish.setEnabled(true);
            }
        });
        bFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase database=FirebaseDatabase.getInstance();
                DatabaseReference databaseReference=database.getReference();
                databaseReference.child("orders").child(order.getId()).child("status").setValue(Status.finished);
                Toast.makeText(getApplicationContext(),"Order successfully finished",Toast.LENGTH_SHORT).show();
                bFinish.setEnabled(false);
            }
        });
        FirebaseDatabase database=FirebaseDatabase.getInstance();
        DatabaseReference databaseReference=database.getReference();
        databaseReference.child("orders").child(order.getId()).child("status").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                tvStatus.setText((String)dataSnapshot.getValue());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }
}
