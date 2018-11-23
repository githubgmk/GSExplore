package com.gmk.geisa.activities.rofo;

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

import com.gmk.geisa.R;
import com.gmk.geisa.controller.RofoController;
import com.gmk.geisa.controller.SessionController;
import com.gmk.geisa.fragment.tab.TabFragmentRofoPagerAdapter;
import com.gmk.geisa.model.mCustomer;
import com.gmk.geisa.model.mRofo;
import com.gmk.geisa.model.mSession;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class RofoMonthActivity extends AppCompatActivity {
    public final static String sessionMonth = "sessionSampleId";
    public final static String sessionMonthName = "sessionRequestDate";
    public final static String sessionYear = "sessionPurpose";
    private TabLayout tabs;
    private ViewPager pager;
    private TextView bulan, tahun;
    // CustomerController customerController;
    RofoController rofoController;
    SessionController sessionController;
    private ProgressDialog pDialog;
    public final static String sessionUser = "sessionLogin";
    private mSession session;
    private int bln, thn;
    String blnName = "";
    TabFragmentRofoPagerAdapter mAdapter;
    boolean adRofo = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rofo_month);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Rofo Month Detail");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        pager = (ViewPager) findViewById(R.id.pager);
        tabs = (TabLayout) findViewById(R.id.tabs);
        this.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        //determinePaneLayout();

        rofoController = RofoController.getInstance(this);
        rofoController.setCallBackGetDataRofo(callback);
        sessionController = SessionController.getInstance(this);
        bulan = (TextView) findViewById(R.id.month);
        tahun = (TextView) findViewById(R.id.year);

        if (sessionController.cekSession("login", 1)) {
            session = sessionController.getSession("login", 1);
        } else {
            session = null;
        }

        if (getIntent().getSerializableExtra(sessionUser) != null) {
            session = (mSession) getIntent().getSerializableExtra(sessionUser);
        }
        if (getIntent().getSerializableExtra(sessionMonth) != null) {
            bln = (int) getIntent().getSerializableExtra(sessionMonth);
            // Log.e("isi bulan","bln "+ bln);
            bulan.setText(String.valueOf(bln + ":" + blnName));
        }
        if (getIntent().getSerializableExtra(sessionMonthName) != null) {
            blnName = (String) getIntent().getSerializableExtra(sessionMonthName);
            // Log.e("isi bulan","bln "+ bln);
            bulan.setText(String.valueOf(bln + ":" + blnName));
        }
        if (getIntent().getSerializableExtra(sessionYear) != null) {
            thn = (int) getIntent().getSerializableExtra(sessionYear);
            // Log.e("isi bulan","bln "+ thn);
            tahun.setText(String.valueOf(thn));
        }


        //setupTabIcons();


    }

    @Override
    protected void onResume() {
        super.onResume();
        setupTabIcons();

    }


    RofoController.CallBackGetDataRofo callback = new RofoController.CallBackGetDataRofo() {
        @Override
        public void resultReady(ArrayList<mRofo> customers, boolean hasil) {
            if (pDialog != null)
                pDialog.dismiss();
            if (hasil)
                updateTabFragment();
        }
    };


    private void setupTabIcons() {

        Bundle bundle = new Bundle();
        ArrayList<mRofo> listrofoApprove = rofoController.getRofoBySalesIdYearMonthApprove(String.valueOf(session.getId()), thn, bln);
        ArrayList<mRofo> listrofoNotApprove = rofoController.getRofoBySalesIdYearMonthNotApprove(String.valueOf(session.getId()), thn, bln);
        Log.e("total rofo","ttl "+listrofoNotApprove.size());
        ArrayList<mCustomer> listcustomer = rofoController.getCustomerBySalesIdYearMonth(String.valueOf(session.getId()), thn, bln);
        mAdapter = new TabFragmentRofoPagerAdapter(getSupportFragmentManager(), bundle, listcustomer, listrofoApprove, listrofoNotApprove);
        //mAdapter.notifyDataSetChanged();
        pager.setAdapter(mAdapter);
        //mAdapter.notifyDataSetChanged();
        tabs.setupWithViewPager(pager);
        tabs.setTabGravity(TabLayout.GRAVITY_FILL);

        TextView tabOne = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabOne.setText("List Customer");
        tabOne.setTextColor(Color.BLACK);
        //tabOne.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_cust, 0, 0);
        tabs.getTabAt(0).setCustomView(tabOne);

        TextView tabTwo = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabTwo.setText("List Approved");
        tabTwo.setTextColor(Color.BLACK);
        //tabTwo.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_cust_prospek, 0, 0);
        tabs.getTabAt(1).setCustomView(tabTwo);

        TextView tabThree = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabThree.setText("List Not Approved");
        tabThree.setTextColor(Color.BLACK);
        // tabThree.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_cust_prospek, 0, 0);
        tabs.getTabAt(2).setCustomView(tabThree);

    }

    //crete menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_customer, menu);
        MenuItem item = menu.findItem(R.id.menuAddCst);
        item.setTitle("Add Rofo");
        item.setIcon(android.R.drawable.ic_menu_add);
        Calendar call = Calendar.getInstance();
        if (thn <= call.get(call.YEAR)) {
            SimpleDateFormat smpbln = new SimpleDateFormat("M");
            if (bln < Integer.valueOf(smpbln.format(call.getTime())) - 1)
                item.setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            //getFragmentManager().popBackStack();
            Log.e("ada r"," a" +adRofo);
            Intent returnIntent = new Intent();
            if (adRofo)
                setResult(Activity.RESULT_OK, returnIntent);
            finish();
        } else if (item.getItemId() == R.id.menuAddCst) {
            Intent inten = new Intent(getApplicationContext(), RofoNewActivity.class);
            inten.putExtra(RofoNewActivity.sessionUser, session);
            inten.putExtra(RofoNewActivity.sessionBulan, bln);
            inten.putExtra(RofoNewActivity.sessionBulanName, blnName);
            inten.putExtra(RofoNewActivity.sessionTahun, thn);
            startActivityForResult(inten, 27);
            return true;
        } else if (item.getItemId() == R.id.menuRefresh) {
            if (pDialog == null)
                pDialog = new ProgressDialog(this);
            pDialog.setMessage("Getting Data From Server ...\n Please Wait...");
            pDialog.show();
            if (null != session) {
                rofoController.getAllRofoFromServer(String.valueOf(session.getId()), String.valueOf(thn), String.valueOf(bln));
            } else {
                pDialog.dismiss();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void updateTabFragment() {
        // pager.clearOnPageChangeListeners();
        // tabs.setupWithViewPager(pager);
        //  tabs.setTabGravity(TabLayout.GRAVITY_FILL);

        setupTabIcons();

    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        if (pager != null) {
            pager = null;
        }
        super.onDestroy();

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == 27) {
            // Make sure the request was successful
           // Log.e("add rofo"," ad"+adRofo);
            if (resultCode == RESULT_OK) {
               // Log.e("add rofo"," ad"+adRofo);
                adRofo = true;
            }
        }else{
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

}
