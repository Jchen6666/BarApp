package com.example.jerrychen.barapp;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;
/**
 * Main activity containing most UI elements and {@link Fragment}s
 * @see AppCompatActivity
 */
public class StaffInterfaceActivity extends AppCompatActivity implements SettingsFragment.OnFragmentInteractionListener,OrdersFragment.OnFragmentInteractionListener,CategoryFragment.OnFragmentInteractionListener,MenuFragment.OnFragmentInteractionListener {

    private DrawerLayout mDrawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_interface);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);

        mDrawerLayout = findViewById(R.id.drawer_layout);

        //Fragments for bottom navigation bar
        Fragment menuFragment=new MenuFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, menuFragment);
        transaction.commit();

        //Bottom navigation bar
        BottomNavigationView bottomNavigationView=(BottomNavigationView)findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.action_menu:
                        Toast.makeText(getApplicationContext(),"Just clicked MENU navigation button",Toast.LENGTH_SHORT).show();
                        switchToMenuFragment();
                        break;
                    case R.id.action_orders:
                        Toast.makeText(getApplicationContext(),"Just clicked ORDERS navigation button",Toast.LENGTH_SHORT).show();
                        switchToOrdersFragment();
                        break;
                    case R.id.action_settings:
                        Toast.makeText(getApplicationContext(),"Just clicked SETTINGS navigation button",Toast.LENGTH_SHORT).show();
                        switchToSettingsFragment();
                        break;
                }
                return true;
            }
        });




        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // set item as selected to persist highlight
                        menuItem.setChecked(true);
                        // close drawer when item is tapped
                        mDrawerLayout.closeDrawers();
                        Intent myIntent = new Intent(StaffInterfaceActivity.this, AddProductActivity.class);
                        StaffInterfaceActivity.this.startActivity(myIntent);
                        // Add code here to update the UI based on the item selected
                        // For example, swap UI fragments here

                        return true;
                    }
                });



    }
    public void switchToMenuFragment() {
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.fragment_container, new MenuFragment()).commit();
    }
    public void switchToOrdersFragment() {
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.fragment_container, new OrdersFragment()).commit();
    }
    public void switchToSettingsFragment() {
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.fragment_container, new SettingsFragment()).commit();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
