package com.example.jerrychen.barapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Kangur on 12.04.2018.
 */

public class ProductStaffAdapter extends ArrayAdapter<Product> {
    public ProductStaffAdapter(Context context, ArrayList<Product> products) {
        super(context, 0, products);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        final Product product = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.product_layout_staff, parent, false);
        }
        // Lookup view for data population
        ImageView ivPicture=(ImageView) convertView.findViewById(R.id.imageViewPhoto);
        TextView tvName = (TextView) convertView.findViewById(R.id.textViewNumber);
        ImageButton ibInfo=(ImageButton) convertView.findViewById(R.id.imageButtonInfo);
        ImageButton ibEdit=(ImageButton) convertView.findViewById(R.id.imageButtonEdit);
        ImageButton ibDelete=(ImageButton) convertView.findViewById(R.id.imageButtonDelete);

        ibEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(getContext(), EditProductActivity.class);
                myIntent.putExtra("Product",product);
                getContext().startActivity(myIntent);
            }
        });

        ibInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(getContext(), ItemDetailsActivity.class);
                myIntent.putExtra("Product",product);
                getContext().startActivity(myIntent);
            }
        });

        ibDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(getContext())
                        .setTitle("")
                        .setMessage("Do you want to remove this product from the menu?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference();
                                databaseReference.child("Products").child(product.getCategory().toString()).child(product.getID()).removeValue();
                                Toast.makeText(getContext(), "Product deleted successfully", Toast.LENGTH_SHORT).show();
                            }})
                        .setNegativeButton(android.R.string.no, null).show();
            }
        });
        // Populate the data into the template view using the data object
        tvName.setText(product.getName());
        try {
            Picasso.get().load(product.getPictureUrl()).into(ivPicture);
        }catch(IllegalArgumentException e){
            e.printStackTrace();
        }

        // Return the completed view to render on screen
        return convertView;
    }
}

