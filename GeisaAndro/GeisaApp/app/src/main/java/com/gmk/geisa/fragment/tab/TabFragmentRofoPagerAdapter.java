package com.gmk.geisa.fragment.tab;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.gmk.geisa.fragment.RofoTabCustomerListViewFragment;
import com.gmk.geisa.fragment.RofoTabProductListViewFragment;
import com.gmk.geisa.model.mCustomer;
import com.gmk.geisa.model.mRofo;

import java.util.ArrayList;

/**
 * Created by kenjin on 10/24/2015.
 */
public class TabFragmentRofoPagerAdapter extends FragmentStatePagerAdapter {
    private Bundle bundle;
    private ArrayList<mRofo> rofoApprove=new ArrayList<>();
    private ArrayList<mRofo> rofoNotApprove=new ArrayList<>();
    private ArrayList<mCustomer> customerlist=new ArrayList<>();

    String[] title = new String[]{
            "Customer List", "List Approved","List Not Approved"
    };


    public TabFragmentRofoPagerAdapter(FragmentManager fm, Bundle bundle, ArrayList<mCustomer> customerlist, ArrayList<mRofo> rofoApprove,ArrayList<mRofo> rofoNotApprove) {
        super(fm);
        this.bundle=bundle;
        this.customerlist=customerlist;
        this.rofoApprove=rofoApprove;
        this.rofoNotApprove=rofoNotApprove;
    }

    @Override
    public Fragment getItem(int position){
        Fragment fragment=null;
        switch (position){
            case 0:
                ///fragment=null;// CustomerRealListFragment.newInstant(customerlist);
                fragment= RofoTabCustomerListViewFragment.newInstant(customerlist);
                break;
            case 1:
               // fragment=new CustomerPropectListFragment();
                fragment= RofoTabProductListViewFragment.newInstant(rofoApprove);
                //fragment.setArguments(bundle);
                break;
            case 2:
                // fragment=new CustomerPropectListFragment();
                fragment= RofoTabProductListViewFragment.newInstant(rofoNotApprove);
                //fragment.setArguments(bundle);
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
    }
    @Override
    public  int getCount(){
        return  title.length;
    }


}
