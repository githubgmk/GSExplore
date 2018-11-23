package com.gmk.geisa.fragment.tab;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.gmk.geisa.fragment.POTabCustomerInfoViewFragment;
import com.gmk.geisa.fragment.POTabProductListViewFragment;
import com.gmk.geisa.fragment.POTabProductOtherListViewFragment;
import com.gmk.geisa.model.mPO;
import com.gmk.geisa.model.mPOCustInfo;
import com.gmk.geisa.model.mPoLine;
import com.gmk.geisa.model.mPoLineOther;
import com.gmk.geisa.model.mProductPriceDiskon;
import com.gmk.geisa.model.mSession;

import java.util.ArrayList;

/**
 * Created by kenjin on 10/24/2015.
 */
public class TabFragmentPoPagerAdapter extends FragmentPagerAdapter {
    private Bundle bundle;
    private mPOCustInfo products=null;
    private ArrayList<mProductPriceDiskon> productPrice=new ArrayList<>();
    private  POTabCustomerInfoViewFragment tab1;
    private POTabProductListViewFragment tab2;
    private POTabProductOtherListViewFragment tab3;
    private mSession session;
    private String PoPPRefId="";
    boolean isPP = false;

    String[] title = new String[]{
            "Customer Info*", "List Of Products*","Other Products"
    };


    public TabFragmentPoPagerAdapter(FragmentManager fm, Bundle bundle,String poid, mSession sesion, mPOCustInfo products, ArrayList<mProductPriceDiskon> productPriceDiskon) {
        super(fm);
        this.bundle=bundle;
        this.products=products;
        this.productPrice=productPriceDiskon;
        this.session=sesion;
        tab1=POTabCustomerInfoViewFragment.newInstant();
        tab2=POTabProductListViewFragment.newInstant(poid, sesion, productPrice);
        tab3=POTabProductOtherListViewFragment.newInstant(poid, sesion, productPrice);
    }

    @Override
    public Fragment getItem(int position){
        Fragment fragment=null;
        switch (position){
            case 0:
               // fragment=new ProductTabViewFragment();
               // Log.e("select tab","tab O"+products.size());
                fragment= tab1;
                //fragment.setArguments(bundle);
                break;
            case 1:
               // fragment=new CustomerPropectListFragment();
                fragment= tab2;
                //fragment.setArguments(bundle);
                break;
            case 2:
                fragment=tab3;
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


    public  mPOCustInfo getItemCust(){
        return tab1.getItem();
    }

    public  void setItemCust(mPO po){
        tab1.setItem(po);
    }

    public ArrayList<mPoLine> getItemProduct(){
        return tab2.getItem();
    }

    public  void setItemProduct(mPO po,int custLevelId){
        tab2.setItem(po,custLevelId);
    }

    public ArrayList<mPoLineOther> getItemProductOther(){
        return tab3.getItem();
    }

    public  void setItemProductOther(mPO po){
        tab3.setItem(po);
    }

    public double getDisFaktur1(){
        return tab2.getDiscFaktur1();
    }

    public double getDisFaktur2(){
        return tab2.getDiscFaktur2();
    }

    public void changeDiscFaktur2(boolean status){
         tab2.changeDiscFaktur2(status);
    }

    public void enableDisFaktur2(){
        tab2.getDiscFaktur2();
    }

    public  double getDisFakturCash(){ return  tab2.getDiscFakturCash();}

    public void setIsPP(boolean isPP){
        tab1.setIsPP(isPP);
        tab2.setIsPP(isPP);
    }

    public void setCustLevel(int level){
        tab2.setCustLevel(level);
    }
    public void setisCopyPP(boolean isCopy){
        tab1.setisCopyPP(isCopy);
        tab2.setisCopyPP(isCopy);
    }
    public  void setDetailInfo(String pic, String alamatkirim){
       // Log.e("alamat kirim",alamatkirim+",");
        tab1.setDetailInfo(pic,alamatkirim);
    }

    public  void  setPoPPRefId(String refId){
        //this.PoPPRefId=refId;
        tab2.setPoPPrefId(refId);
    }


    public void  clearProduct(){
        tab2.setRecyclerView();
    }


    public void setIsSellOut(boolean isSellOut) {
        tab1.setIsSellOut(isSellOut);
        tab2.setIsSellOut(isSellOut);
    }
}
