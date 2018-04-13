package com.example.jerrychen.barapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by Kangur on 11.04.2018.
 */

public class PagerAdapter extends FragmentStatePagerAdapter {

    int mNrOfTabs;
    public PagerAdapter(FragmentManager fm,int numberOfTabs){
        super(fm);
        this.mNrOfTabs=numberOfTabs;
    }
    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0:
                FavouriteFragment favouriteFragment=new FavouriteFragment();
                return favouriteFragment;
            case 1:
                BeerFragment beerFragment=new BeerFragment();
                return beerFragment;
            case 2:
                WineFragment wineFragment=new WineFragment();
                return wineFragment;
            case 3:
                ShotsFragment shotsFragment=new ShotsFragment();
                return shotsFragment;
            case 4:
                DrinksFragment drinksFragment=new DrinksFragment();
                return drinksFragment;
            case 5:
                SnacksFragment snacksFragment=new SnacksFragment();
                return snacksFragment;
            case 6:
                SoftDrinksFragment softDrinksFragment=new SoftDrinksFragment();
                return softDrinksFragment;
            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return mNrOfTabs;
    }
}
