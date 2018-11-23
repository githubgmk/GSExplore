package com.gmk.geisa.fragment.tab;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.gmk.geisa.fragment.ProductTabPriceViewFragment;
import com.gmk.geisa.fragment.ProductTabStockViewFragment;
import com.gmk.geisa.fragment.ProductTabViewFragment;
import com.gmk.geisa.model.mProduct;
import com.gmk.geisa.model.mProductPriceDiskon;
import com.gmk.geisa.model.mStockBranch;

import java.util.ArrayList;

/**
 * Created by kenjin on 10/24/2015.
 */
public class TabFragmentProdukPagerAdapter extends FragmentPagerAdapter {
    private Bundle bundle;
    private ArrayList<mProduct> products=new ArrayList<>();
    private ArrayList<mProductPriceDiskon> productPrice=new ArrayList<>();
    private ArrayList<mStockBranch> stock=new ArrayList<>();

    String[] title = new String[]{
            "List Product", "Price List","Stock"
    };


    public TabFragmentProdukPagerAdapter(FragmentManager fm, Bundle bundle, ArrayList<mProduct> products,ArrayList<mProductPriceDiskon> productPriceDiskon,ArrayList<mStockBranch> stock) {
        super(fm);
        this.bundle=bundle;
        this.products=products;
        this.productPrice=productPriceDiskon;
        this.stock=stock;
    }

    @Override
    public Fragment getItem(int position){
        Fragment fragment=null;
        switch (position){
            case 0:
               // fragment=new ProductTabViewFragment();
                //Log.e("select tab","tab O"+products.size());
                fragment=ProductTabViewFragment.newInstant(products);
                //fragment.setArguments(bundle);
                break;
            case 1:
               // fragment=new CustomerPropectListFragment();
                fragment= ProductTabPriceViewFragment.newInstant(productPrice);
                //fragment.setArguments(bundle);
                break;
            case 2:
                // fragment=new CustomerPropectListFragment();
                fragment= ProductTabStockViewFragment.newInstant(stock);
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
