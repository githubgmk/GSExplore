package com.gmk.geisa.fragment.tab;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.gmk.geisa.fragment.CustomerPropectListFragment;
import com.gmk.geisa.fragment.CustomerRealListFragment;

/**
 * Created by kenjin on 10/24/2015.
 */
public class TabFragmentCustomerPagerAdapter extends FragmentPagerAdapter {
    private Bundle bundle;

    String[] title = new String[]{
            "List Outlet", "List Prospect"
    };


    public TabFragmentCustomerPagerAdapter(FragmentManager fm, Bundle bundle) {
        super(fm);
        this.bundle=bundle;
    }

    @Override
    public Fragment getItem(int position){
        Fragment fragment=null;
        switch (position){
            case 0:
                fragment=new CustomerRealListFragment();
                fragment.setArguments(bundle);
                break;
            case 1:
                fragment=new CustomerPropectListFragment();
                fragment.setArguments(bundle);
                break;
            default:
                fragment=null;
                break;
        }
        return  fragment;
    }
    @Override
    public CharSequence getPageTitle(int position){
       // return null;
        return  title[position];
        //return
    }
    @Override
    public  int getCount(){
        return  title.length;
    }
}
