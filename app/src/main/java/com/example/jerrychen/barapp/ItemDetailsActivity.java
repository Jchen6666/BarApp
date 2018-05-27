package com.example.jerrychen.barapp;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
/**
 * A simple activity showing details of a product
 * @see AppCompatActivity
 */
public class ItemDetailsActivity extends AppCompatActivity {
    private ImageView ivPicture;
    private TextView tvName, tvPrice, tvDescription, tvVolume, tvAvailability;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        android.support.v7.app.ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);

        tvName = (TextView) findViewById(R.id.textViewDetailName);
        tvPrice = (TextView) findViewById(R.id.textViewDetailPrice);
        tvDescription = (TextView) findViewById(R.id.textViewDetailDescription);
        tvVolume = (TextView) findViewById(R.id.textViewDetailVolume);
        tvAvailability = (TextView) findViewById(R.id.textViewDetailAvailability);
        ivPicture=(ImageView)findViewById(R.id.imageViewDetailPicture);
        //Receive a product from a previous activity
        if (getIntent().getSerializableExtra("Product") != null) {
            Product product = (Product) getIntent().getSerializableExtra("Product");
            tvName.setText(product.getName());
            tvPrice.setText(String.valueOf(product.getPrice()));
            tvVolume.setText(String.valueOf(product.getVolume()));
            tvDescription.setText((product.getDescription()));
            try {
                Picasso.get().load(product.getPictureUrl()).into(ivPicture);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
            if (product.getAvailability()) {
                tvAvailability.setText("Available");
                tvAvailability.setTextColor(Color.GREEN);
            } else {
                tvAvailability.setText("Unavailable");
                tvAvailability.setTextColor(Color.RED);
            }
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }
}
