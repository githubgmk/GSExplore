package com.gmk.geisa.fragment.tab;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.gmk.geisa.fragment.SampleTabProductListViewFragment;
import com.gmk.geisa.model.mPO;
import com.gmk.geisa.model.mPoLine;
import com.gmk.geisa.model.mProductPriceDiskon;
import com.gmk.geisa.model.mSample;
import com.gmk.geisa.model.mSession;

import java.util.ArrayList;

/**
 * Created by kenjin on 10/24/2015.
 */
public class TabFragmentSampleItemPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<mSample.mProductSample> productPriceRequest = new ArrayList<>();
    private ArrayList<mSample.mProductSample> productPriceRealisasi = new ArrayList<>();
    private SampleTabProductListViewFragment tab1;
    private SampleTabProductListViewFragment tab2;
    private mSession session;
    private String StatusSample;
    boolean isPP = false;

    String[] title = new String[]{
            "Item Request", "Item Realisasi"
    };


    public TabFragmentSampleItemPagerAdapter(FragmentManager fm, String sampleId,mSample sample, mSession sesion,String statusSample, ArrayList<mSample.mProductSample> productPriceDiskonRequest,ArrayList<mSample.mProductSample> productPriceDiskonRealisasi) {
        super(fm);
        this.productPriceRequest = productPriceDiskonRequest;
        this.productPriceRealisasi = productPriceDiskonRealisasi;
        this.session = sesion;
        this.StatusSample=statusSample;
        tab1 = SampleTabProductListViewFragment.newInstant(sampleId,sample, sesion,"draft",StatusSample.toLowerCase(), productPriceRequest);
        tab2 = SampleTabProductListViewFragment.newInstant(sampleId,sample, sesion,"requested",StatusSample.toLowerCase(), productPriceRealisasi);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = tab1;
                break;
            case 1:
                fragment = tab2;
                break;
            default:
                fragment = null;
                break;
        }
        return fragment;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return title[position];
    }

    @Override
    public int getCount() {
        return title.length;
    }


    public ArrayList<mSample.mProductSample> getItemRequest() {
        return tab1.getItem();
    }

    public void setItemRequest(mSample po) {
        tab1.setItem(po);
    }

    public ArrayList<mSample.mProductSample> getItemRealisasi() {
        return tab2.getItem();
    }

    public void setItemRealisasi(mSample po) {
        tab2.setItem(po);
    }

    public void clearItemRequset() {
        tab1.setRecyclerView();
    }

    public void clearItemRelease() {
        tab2.setRecyclerView();
    }


}
