package com.gmk.geisa.activities.customer;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.gmk.geisa.R;
import com.gmk.geisa.fragment.tab.TabFragmentCustomerPagerAdapter;
import com.gmk.geisa.controller.CustomerController;
import com.gmk.geisa.model.mCustomer;
import com.gmk.geisa.model.mSession;

import java.util.ArrayList;

public class CustomerActivity extends AppCompatActivity {
    private boolean isTwoPane = false;
    private TabLayout tabs;
    private ViewPager pager;
    CustomerController customerController;
    private ProgressDialog pDialog;
    public final static String sessionUser="sessionLogin";
    private mSession session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Customer");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        pager=(ViewPager) findViewById(R.id.pager);
        tabs=(TabLayout) findViewById(R.id.tabs);
        this.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        //determinePaneLayout();
        Bundle bundle = new Bundle();

        customerController=CustomerController.getInstance(this);
        customerController.setCallBackGetCustomer(callbackCustomer);
        customerController.setCallBackGetUpdateCustomerMain(callbackUpdateCustomerMain);
        if (getIntent().getSerializableExtra(sessionUser) != null) {
            session = (mSession) getIntent().getSerializableExtra(sessionUser);
        }

        pager.setAdapter(new TabFragmentCustomerPagerAdapter(getSupportFragmentManager(),bundle));

        tabs.setupWithViewPager(pager);
        setupTabIcons();
        tabs.setTabGravity(TabLayout.GRAVITY_FILL);



    }

    CustomerController.CallBackGetUpdateCustomerMain callbackUpdateCustomerMain = new CustomerController.CallBackGetUpdateCustomerMain() {
        @Override
        public void resultReady(ArrayList<mCustomer> customers, boolean hasil) {
            //Log.e("hasil","jadi"+hasil);
            if(hasil) {
                if (null != customers && customers.size() > 0) {

                    updateTabFragment();
                }
                if (pDialog != null)
                    pDialog.dismiss();
            }else{
                if (pDialog != null)
                    pDialog.dismiss();
            }
        }


    };
    CustomerController.CallBackGetCustomer callbackCustomer = new CustomerController.CallBackGetCustomer() {
        @Override
        public void resultReady(ArrayList<mCustomer> customers,boolean hasil) {
            if(hasil) {
                if (null != customers && customers.size() > 0) {
                    updateTabFragment();
                }
                if (pDialog != null)
                    pDialog.dismiss();
            }else{
                if (pDialog != null)
                    pDialog.dismiss();
            }

        }

    };


    private void setupTabIcons() {

        TextView tabOne = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabOne.setText("List Outlet");
        tabOne.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_cust, 0, 0);
        tabs.getTabAt(0).setCustomView(tabOne);

        TextView tabTwo = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabTwo.setText("List Prospect");
        tabTwo.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_cust_prospek, 0, 0);
        tabs.getTabAt(1).setCustomView(tabTwo);

    }
    //crete menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_customer, menu);
        return true;
    }

    @Override
    public  boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId()==android.R.id.home){
            //getFragmentManager().popBackStack();
            finish();
        }else if(item.getItemId()==R.id.menuAddCst){
            Toast.makeText(getApplicationContext(), "Add New Customer", Toast.LENGTH_SHORT).show();
            Intent inten= new Intent(getApplicationContext(),CustomerAddActivity.class);//berpindah ke activity yang lain dgn data
            inten.putExtra(CustomerAddActivity.sessionUser,session);
            startActivityForResult(inten, 11);
            return  true;
        }else if(item.getItemId()==R.id.menuRefresh) {
            if(pDialog==null)
                pDialog = new ProgressDialog(this);
            pDialog.setMessage("Getting Data From Server ...\n Please Wait...");
            pDialog.show();
            if(null!=session) {
                customerController.checkUpdateAllCustomerFromServerMain(String.valueOf(session.getId()));
            }else{
                pDialog.dismiss();
            }
            return true;
        }
        return  super.onOptionsItemSelected(item);
    }

    public void updateTabFragment() {
        tabs.setupWithViewPager(pager);
        setupTabIcons();
        tabs.setTabGravity(TabLayout.GRAVITY_FILL);
    }

}
