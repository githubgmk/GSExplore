package com.gmk.geisa.fragment.tab;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.gmk.geisa.fragment.CustomerDetailTabClasificationViewFragment;
import com.gmk.geisa.fragment.CustomerDetailTabCustViewFragment;
import com.gmk.geisa.fragment.CustomerToDoTabViewFragment;
import com.gmk.geisa.fragment.ProductTabViewFragment;
import com.gmk.geisa.model.mCustomer;
import com.gmk.geisa.model.mCustomerClasification;
import com.gmk.geisa.model.mProduct;

import java.util.ArrayList;

/**
 * Created by kenjinsan on 5/22/17.
 */

public class CustomerDetailTabPageAdapter extends FragmentStatePagerAdapter {
    mCustomer customer;
    mCustomerClasification clasification;
    ArrayList<mProduct> product = new ArrayList<>();
    ArrayList<mCustomerClasification.ChannelStagingApproach> approaches = new ArrayList<>();
    String[] title = new String[]{
            "Detail", "Clasification", "PDS", "To Do"
    };

    public CustomerDetailTabPageAdapter(FragmentManager fm, mCustomer customer, mCustomerClasification clasification) {
        super(fm);
        this.customer = customer;
        this.clasification = clasification;
        if (clasification.getProduk() != null && clasification.getProduk().size() > 0)
            for (mCustomerClasification.ChannelProduk cu : clasification.getProduk()) {
                this.product.add(new mProduct(cu.getProdukId(), cu.getProductName(), "", "", cu.getProductSimpleDescription(), 1));
            }
        if (clasification.getStagingApproach() != null && clasification.getStagingApproach().size() > 0)
            approaches.addAll(clasification.getStagingApproach());
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                ///fragment=null;// CustomerRealListFragment.newInstant(customerlist);
                fragment = CustomerDetailTabCustViewFragment.newInstant(customer);
                break;
            case 1:
                // fragment=new CustomerPropectListFragment();
                fragment = CustomerDetailTabClasificationViewFragment.newInstance(clasification);
                //fragment.setArguments(bundle);
                break;
            case 2:
                // fragment=new CustomerPropectListFragment();
                fragment = ProductTabViewFragment.newInstant(product);
                //fragment.setArguments(bundle);
                break;
            case 3:
                // fragment=new CustomerPropectListFragment();
                fragment = CustomerToDoTabViewFragment.newInstant(approaches);
                //fragment.setArguments(bundle);
                break;
            default:
                fragment = null;
                break;
        }
        return fragment;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // return null;
        return title[position];
    }


    @Override
    public int getCount() {
        return title.length;
    }

}
