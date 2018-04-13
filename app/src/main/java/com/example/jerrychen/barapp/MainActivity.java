package com.example.jerrychen.barapp;

import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements BeerFragment.OnFragmentInteractionListener,FavouriteFragment.OnFragmentInteractionListener,WineFragment.OnFragmentInteractionListener,SoftDrinksFragment.OnFragmentInteractionListener,SnacksFragment.OnFragmentInteractionListener,ShotsFragment.OnFragmentInteractionListener,DrinksFragment.OnFragmentInteractionListener {

    private DrawerLayout mDrawLay;
    private ActionBarDrawerToggle mToggle;
    private static ArrayList<Product> PRODUCTS=new ArrayList<>();

    public static ArrayList<Product> getPRODUCTS() {
        return PRODUCTS;
    }

    public static void setPRODUCTS(ArrayList<Product> PRODUCTS) {
        MainActivity.PRODUCTS = PRODUCTS;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ArrayList<Product>tempArrayList=new ArrayList<>();
        tempArrayList.add(new Product("https://www.google.com/url?sa=i&rct=j&q=&esrc=s&source=images&cd=&cad=rja&uact=8&ved=2ahUKEwjm4Pnr6rTaAhXqE5oKHTbKBoMQjRx6BAgAEAU&url=https%3A%2F%2Fwww.flickr.com%2Fphotos%2Fkenbe%2F5987775682&psig=AOvVaw2tCKbSQCIgX--EOkD3p4MF&ust=1523625875482684","TUBORG",50));
        tempArrayList.add(new Product("https://www.google.com/url?sa=i&rct=j&q=&esrc=s&source=images&cd=&cad=rja&uact=8&ved=2ahUKEwjm4Pnr6rTaAhXqE5oKHTbKBoMQjRx6BAgAEAU&url=https%3A%2F%2Fwww.flickr.com%2Fphotos%2Fkenbe%2F5987775682&psig=AOvVaw2tCKbSQCIgX--EOkD3p4MF&ust=1523625875482684","TUBORG 2",30));
        setPRODUCTS(tempArrayList);
        //Retrieving data from firebase
//        FirebaseDatabase database=FirebaseDatabase.getInstance();
//        DatabaseReference databaseReference=database.getReference();
//        databaseReference.child("Product").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
//                for(DataSnapshot child:children){
//                    PRODUCTS.add(child.getValue(Product.class));
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });


        //Handling swipeable tabs
        TabLayout tabLayout=(TabLayout)findViewById(R.id.tablayout);
        tabLayout.addTab(tabLayout.newTab().setText("Favourite"));
        tabLayout.addTab(tabLayout.newTab().setText("Beer"));
        tabLayout.addTab(tabLayout.newTab().setText("Wine"));
        tabLayout.addTab(tabLayout.newTab().setText("Shots"));
        tabLayout.addTab(tabLayout.newTab().setText("Drinks"));
        tabLayout.addTab(tabLayout.newTab().setText("Snacks"));
        tabLayout.addTab(tabLayout.newTab().setText("Soft Drinks"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);


        final ViewPager viewPager=(ViewPager)findViewById(R.id.pager);
        final PagerAdapter pagerAdapter=new PagerAdapter(getSupportFragmentManager(),tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);
        viewPager.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        //Handling Action bar
        mDrawLay = (DrawerLayout) findViewById(R.id.drawer);
        mToggle = new ActionBarDrawerToggle(this, mDrawLay, R.string.Open, R.string.Close);
        mDrawLay.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}

