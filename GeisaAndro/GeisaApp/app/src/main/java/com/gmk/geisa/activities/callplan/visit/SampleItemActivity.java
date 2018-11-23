package com.gmk.geisa.activities.callplan.visit;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
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
import com.gmk.geisa.controller.RofoController;
import com.gmk.geisa.controller.SessionController;
import com.gmk.geisa.fragment.tab.TabFragmentSampleItemPagerAdapter;
import com.gmk.geisa.model.mSample;
import com.gmk.geisa.model.mSession;

import java.io.Serializable;
import java.util.ArrayList;

public class SampleItemActivity extends AppCompatActivity {
    public final static String sessionSampleId = "sessionSampleId";
    public final static String sessionRequestDate = "sessionRequestDate";
    public final static String sessionPurpose = "sessionPurpose";
    public  final static  String sessionType="sessionType";
    public final static String sessionSample = "sessionSample";
    private TabLayout tabs;
    private ViewPager pager;
    private TextView sampleid, requestdate, purpose;
    mSample sample;
    RofoController rofoController;
    SessionController sessionController;
    private ProgressDialog pDialog;
    public final static String sessionUser = "sessionLogin";
    private mSession session;
    String SampleId = "", RequestDate = "", Purpose = "", StatusSample = "Request";
    TabFragmentSampleItemPagerAdapter mAdapter;
    int type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample_item);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Sample Item List");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        pager = (ViewPager) findViewById(R.id.pager);
        tabs = (TabLayout) findViewById(R.id.tabs);
        this.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        //determinePaneLayout();

        rofoController = RofoController.getInstance(this);
        sessionController = SessionController.getInstance(this);
        sampleid = (TextView) findViewById(R.id.sampleid);
        requestdate = (TextView) findViewById(R.id.requestdate);
        purpose = (TextView) findViewById(R.id.purpose);


        if (getIntent().getSerializableExtra(sessionUser) != null) {
            session = (mSession) getIntent().getSerializableExtra(sessionUser);
        }
        if (getIntent().getSerializableExtra(sessionSample) != null) {
            sample = (mSample) getIntent().getSerializableExtra(sessionSample);
            SampleId = sample.getSampleId();
            RequestDate = sample.getSampleDate();
            Purpose = sample.getSampleFor();
            StatusSample = sample.getSampleStatus();
            sampleid.setText(SampleId);
            purpose.setText(Purpose);
            requestdate.setText(RequestDate);
        }
        if (getIntent().getSerializableExtra(sessionType) != null) {
            type = (int) getIntent().getSerializableExtra(sessionType);
        }
       /* if (getIntent().getSerializableExtra(sessionStatus) != null) {
            StatusSample = (String) getIntent().getSerializableExtra(sessionStatus);
        }*/
        /*if (getIntent().getSerializableExtra(sessionRequestDate) != null) {
            RequestDate = (String) getIntent().getSerializableExtra(sessionRequestDate);
            requestdate.setText(RequestDate);
        }*/
        /*if (getIntent().getSerializableExtra(sessionPurpose) != null) {
            Purpose = (String) getIntent().getSerializableExtra(sessionPurpose);
            purpose.setText(Purpose);
        }*/
        setupTabIcons();

    }

    @Override
    protected void onResume() {
        super.onResume();
        // setupTabIcons();

    }


    private void setupTabIcons() {
        ArrayList<mSample.mProductSample> itemRequest = sample.getProductOfRequest();
        ArrayList<mSample.mProductSample> itemRealisasi = sample.getProductOfRealisasi();
        //if(itemRequest!=null)
        //Log.e("total sample", StatusSample + " cxx");

        mAdapter = new TabFragmentSampleItemPagerAdapter(getSupportFragmentManager(), SampleId,sample, session, StatusSample, itemRequest, itemRealisasi);
        pager.setAdapter(mAdapter);
        tabs.setupWithViewPager(pager);
        tabs.setTabGravity(TabLayout.GRAVITY_FILL);


        TextView tabOne = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabOne.setText("Item Request");
        tabOne.setTextColor(Color.BLACK);
        tabs.getTabAt(0).setCustomView(tabOne);

        TextView tabTwo = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabTwo.setText("Item Realisasi");
        tabTwo.setTextColor(Color.BLACK);
        tabs.getTabAt(1).setCustomView(tabTwo);
        if(type==0) {
            pager.setCurrentItem(0);
        }else{
            pager.setCurrentItem(1);
        }

    }

    //crete menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_customer, menu);
        MenuItem item = menu.findItem(R.id.menuAddCst);
        item.setTitle("OK");
        item.setIcon(android.R.drawable.ic_menu_save);
        MenuItem items = menu.findItem(R.id.menuRefresh);
        items.setTitle("Cancel");
        items.setIcon(android.R.drawable.ic_menu_close_clear_cancel);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            //getFragmentManager().popBackStack();
            finish();
        } else if (item.getItemId() == R.id.menuAddCst) {
            //return data
            int adasample = 0;
            if (type==0) {
                adasample = mAdapter.getItemRequest().size();
            } else if (type==1) {
                adasample = mAdapter.getItemRealisasi().size();
            }
            //Log.e("isi sample","tlt"+adasample);
            if (adasample > 0) {
                sample.setProductOfRequest(mAdapter.getItemRequest());
                sample.setProductOfRealisasi(mAdapter.getItemRealisasi());
                Intent returnIntent = new Intent();
                returnIntent.putExtra(SampleAddActivity.sessionSample, (Serializable) sample);
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
            } else {
                Toast.makeText(this,"Input Item dan Qty",Toast.LENGTH_SHORT).show();
            }
            return true;
        } else if (item.getItemId() == R.id.menuRefresh) {
            //send cancel
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
