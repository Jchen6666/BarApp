package com.example.jerrychen.barapp;

import android.os.Bundle;
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
        Bundle bundle = new Bundle();
        String category;

        switch(position){
            case 0:
                category="Favourite";
                break;
            case 1:
                category="Beer";
                break;
            case 2:
                category="Wine";
                break;
            case 3:
                category="Shots";
                break;
            case 4:
                category="Drinks";
                break;
            case 5:
                category="Snacks";
                break;
            case 6:
                category="Soft Drinks";
                break;
            default:
                category="Favourite";
                break;
        }
        bundle.putString("category",category);
        CategoryFragment categoryFragment=new CategoryFragment();
        categoryFragment.setArguments(bundle);
        return categoryFragment;
    }

    @Override
    public int getCount() {
        return mNrOfTabs;
    }
}
