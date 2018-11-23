package com.gmk.geisa.fragment;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;

import com.gmk.geisa.Class.Konversi;
import com.gmk.geisa.R;
import com.gmk.geisa.activities.customer.CustomerDetailActivity;
import com.gmk.geisa.adapter.CustomerListAdapter;
import com.gmk.geisa.controller.CustomerController;
import com.gmk.geisa.controller.ProductController;
import com.gmk.geisa.model.mCustomer;
import com.gmk.geisa.model.mCustomerAndDistBranch;

import java.util.ArrayList;
import java.util.List;



/**
 * A simple {@link Fragment} subclass.
 */
public class CustomerDetailTabCustViewFragment extends Fragment {

    mCustomer customer;

    CustomerController customerController;
    ProductController productController;

    TextView cstNama, cstNamaAlias, cstIdGeisa, cstLevel, cstChannel, cstArea, cstZone, cstStatusMember,
            cstLocation, cstPIC, cstPICJabatan, cstEmail, cstTelp, cstHP, cstAlamat, cstWebsite,
            cstJoinDate, cstStatusPKP, cstLimit, cstTOP, cstNPWP, cstGroupPrice, cstGroupDiskon,
            cstDistributorName, cstDistributorCustomerCode, cstDistributorArea, cstDistributorPic, cstDistributorEmail,
            cstDistributorTelp, cstDistributorAddress;
    View view;
    TabLayout mTabLayout;
    ViewPager mViewPager;
    FragmentAdapter mPagerAdapter;
    List<String> tabItems = new ArrayList<>();
    private Konversi konversi = new Konversi();
    ArrayList<mCustomerAndDistBranch> customerAndDistBranches = new ArrayList<>();
    // boolean cancelStartStop = false;
    // int countwork;

    public static CustomerDetailTabCustViewFragment newInstant(mCustomer customer) {
        CustomerDetailTabCustViewFragment customerDetailFragment = new CustomerDetailTabCustViewFragment();
        // Log.e("isi call prod"," isinya"+callPlen.size());
        Bundle args = new Bundle();
        args.putSerializable("cust", customer);
        customerDetailFragment.setArguments(args);
        return customerDetailFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        customerController = CustomerController.getInstance(getActivity());
        productController = ProductController.getInstance(getActivity());
        Bundle arguments = getArguments();
        if (arguments != null) {
            if (null != arguments.getSerializable("cust"))
                customer = (mCustomer) arguments.getSerializable("cust");
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_customer_detail, container, false);
        mViewPager = (ViewPager) view.findViewById(R.id.pager);
        mTabLayout = (TabLayout) view.findViewById(R.id.tabs);

        cstNama = (TextView) view.findViewById(R.id.cstNama);
        cstNamaAlias = (TextView) view.findViewById(R.id.cstNamaAlias);
        cstIdGeisa = (TextView) view.findViewById(R.id.cstCode);//id geisa
        cstLevel = (TextView) view.findViewById(R.id.cstLevel);
        cstChannel = (TextView) view.findViewById(R.id.cstChannel);
        cstArea = (TextView) view.findViewById(R.id.cstArea);
        cstZone = (TextView) view.findViewById(R.id.cstZone);
        cstStatusMember = (TextView) view.findViewById(R.id.cstStatusMember);
        cstLocation = (TextView) view.findViewById(R.id.cstLocation);
        cstPIC = (TextView) view.findViewById(R.id.cstPIC);
        cstPICJabatan = (TextView) view.findViewById(R.id.cstPICJabatan);
        cstEmail = (TextView) view.findViewById(R.id.cstEmail);

        cstTelp = (TextView) view.findViewById(R.id.cstTelp);
        cstHP = (TextView) view.findViewById(R.id.cstHP);
        cstAlamat = (TextView) view.findViewById(R.id.cstAlamat);
        cstWebsite = (TextView) view.findViewById(R.id.cstWebsite);
        cstJoinDate = (TextView) view.findViewById(R.id.cstJoinDate);
        cstStatusPKP = (TextView) view.findViewById(R.id.cstStatusPKP);
        cstLimit = (TextView) view.findViewById(R.id.cstLimit);
        cstTOP = (TextView) view.findViewById(R.id.cstTOP);
        cstNPWP = (TextView) view.findViewById(R.id.cstNPWP);
        //distributor and price
        cstGroupPrice = (TextView) view.findViewById(R.id.cstGroupPrice);
        cstGroupDiskon = (TextView) view.findViewById(R.id.cstGroupDiskon);
        cstDistributorName = (TextView) view.findViewById(R.id.cstDistributorName);
        cstDistributorCustomerCode = (TextView) view.findViewById(R.id.cstDistributorCustomerCode);
        cstDistributorArea = (TextView) view.findViewById(R.id.cstDistributorArea);
        cstDistributorPic = (TextView) view.findViewById(R.id.cstDistributorPic);
        cstDistributorEmail = (TextView) view.findViewById(R.id.cstDistributorEmail);
        cstDistributorTelp = (TextView) view.findViewById(R.id.cstDistributorTelp);
        cstDistributorAddress = (TextView) view.findViewById(R.id.cstDistributorAddress);
        addData();
        return view;
    }


    private void addData() {
        //customer = customerController.getCustomerByCustIdAndSalesId(idOutlet, SalesId);
        //Log.e("refresh data", "gt " + customer.getCustId() + "," + idOutlet + "," + session.getId());
        //set value transaksi
        //idOutlet = String.valueOf(customer.getCustId());
        cstIdGeisa.setText(String.valueOf(customer.getCustId()));
        cstLevel.setText(customer.getCustLevelId() + " - " + customer.getCustLevelName());
        cstChannel.setText(customer.getChannelName());
        cstArea.setText(customer.getAreaName());
        cstZone.setText(customer.getCustZoneId() + " - " + customer.getCustZoneName());
        cstStatusMember.setText(customer.getCustStatusName());


        //set value detail
        String Latitude = String.valueOf(customer.getLat());
        String Longitude = String.valueOf(customer.getLng());
        cstLocation.setText(konversi.DataToString(Latitude) + "," + konversi.DataToString(Longitude) + "(" + customer.getRadius() + ")");
        cstPIC.setText(customer.getPic());
        cstPICJabatan.setText(customer.getPicJabatan());
        cstEmail.setText(customer.getEmail());
        cstTelp.setText(customer.getTelp());
        cstHP.setText(customer.getHp());
        cstAlamat.setText(customer.getAddress());
        cstWebsite.setText(customer.getWebsite());

        cstJoinDate.setText(customer.getJoinDate());
        cstStatusPKP.setText(customer.getStsPkpId() + " - " + customer.getStsPkpName());
        cstLimit.setText(customer.getCreditLimit());
        cstTOP.setText(customer.getTop());
        cstNPWP.setText(customer.getNpwp());

        adddistfragement();
    }

    private void adddistfragement() {
        customerAndDistBranches.clear();
        tabItems.clear();
        mTabLayout.removeAllTabs();
        mViewPager.removeAllViews();
        mPagerAdapter = new FragmentAdapter(getChildFragmentManager());
        mViewPager.setAdapter(mPagerAdapter);
        mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        if (null != customer.getCustomerAndBranch() && customer.getCustomerAndBranch().size() > 0) {

            for (int i = 0; i < customer.getCustomerAndBranch().size(); i++) {
                mCustomerAndDistBranch cs = customer.getCustomerAndBranch().get(i);
                customerAndDistBranches.add(cs);
                addTab("Distributor " + (i + 1));
                mPagerAdapter.notifyDataSetChanged();
                //Log.e("isi fragment","gt "+i);
            }


        } else {

            mCustomerAndDistBranch br = new mCustomerAndDistBranch();
            br.setDistBranchName("Need To Add");
            br.setDiscGroupId("Need To Add");
            br.setDiscGroupName("");
            br.setPriceGroupId("Need To Add");
            br.setPriceGroupName("");
            customerAndDistBranches.add(br);
            addTab("Empty Distributor List");
        }

        mTabLayout.setupWithViewPager(mViewPager);
    }

    private void addTab(String title) {
        mTabLayout.addTab(mTabLayout.newTab().setText(title));
        addTabPage(title);
    }

    public void addTabPage(String title) {
        tabItems.add(title);
        mPagerAdapter.notifyDataSetChanged();
    }

    class FragmentAdapter extends FragmentStatePagerAdapter {


        public FragmentAdapter(FragmentManager fm) {
            super(fm);

        }

        @Override
        public Fragment getItem(int position) {
            return CustomerDistributorFragment.newInstance(customerAndDistBranches.get(position));
        }


        @Override
        public int getCount() {
            return tabItems.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabItems.get(position);
        }

        @Override
        public Parcelable saveState() {
            return null;
        }

    }


}
