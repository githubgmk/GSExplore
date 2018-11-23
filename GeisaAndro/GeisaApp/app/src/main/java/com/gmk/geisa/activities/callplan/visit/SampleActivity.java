package com.gmk.geisa.activities.callplan.visit;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.SearchView;

import com.gmk.geisa.R;
import com.gmk.geisa.adapter.SampleListAdapter;
import com.gmk.geisa.controller.CallPlanController;
import com.gmk.geisa.model.mSample;
import com.gmk.geisa.model.mSession;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;

public class SampleActivity extends AppCompatActivity {
    public static String sessionUser = "sessionUser";
    public static String callPlanId = "callPlanId";
    public static String customerId = "customerId";

    private SearchView findTXTEdit;
    private mSession session;
    CallPlanController callPlanController;

    private ProgressDialog pDialog;
    private RecyclerView.LayoutManager mLayoutManager;

    private SampleListAdapter adapter1;
    RecyclerView recyclerView;


    private String idCallPlan, idCustomer;
    int SalesId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("All Pending Sample");


        callPlanController = CallPlanController.getInstance(this);
        callPlanController.setCallBackGetDataSample(callbackSample);
        callPlanController.setCallBackGetDataSampleRefresh(callBackGetDataSampleRefresh);

        findTXTEdit = (SearchView) findViewById(R.id.findTXTEdit);
        recyclerView = (RecyclerView) findViewById(R.id.lvItems);
        if (getIntent().getSerializableExtra(sessionUser) != null) {
            session = (mSession) getIntent().getSerializableExtra(sessionUser);
            SalesId = session.getId();
            // Log.e("isi user", " x" + session.getId());
        }
        if (getIntent().getSerializableExtra(callPlanId) != null) {
            idCallPlan = (String) getIntent().getSerializableExtra(callPlanId);
        }
        if (getIntent().getSerializableExtra(customerId) != null) {
            idCustomer = (String) getIntent().getSerializableExtra(customerId);
        }
        //Log.e("isi sessi a",session.getNama()+","+session.getId());
        this.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);


        findTXTEdit.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter1.setFilter().filter(newText);
                return false;
            }
        });
        setupRecyclerView();
        // getdatafromserver();

    }

    private void getdatafromserver() {
        if (pDialog == null)
            pDialog = new ProgressDialog(this);
        pDialog.setMessage("Getting Data From Server ...\n Please Wait...");
        pDialog.show();
        if (null != session) {
            callPlanController.getAllCallPlanSamplePendingCustomerFromServer(String.valueOf(session.getId()), idCustomer);
        } else {
            pDialog.dismiss();
        }
    }


    private void setupRecyclerView() {

        ArrayList<mSample> todo = callPlanController.getTransCallPlanSampleNotFinish(idCustomer);
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        adapter1 = new SampleListAdapter(todo, this, session,false);
        recyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this)
                .colorResId(R.color.colorPrimaryDark)
                .size(2)
                .build());
        recyclerView.setAdapter(adapter1);
        this.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    private void hiddenKeyboard(View v) {
        InputMethodManager keyboard = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        keyboard.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
        super.onDestroy();

    }

    @Override
    protected void onResume() {
        super.onResume();
        setupRecyclerView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_customer, menu);
        MenuItem item = menu.findItem(R.id.menuAddCst);
        item.setTitle("Add New Sample");
        item.setIcon(android.R.drawable.ic_menu_add);
        return true;
    }

    //untuk memantau tombol back
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            //getFragmentManager().popBackStack();
            finish();
        } else if (item.getItemId() == R.id.menuAddCst) {
            Intent inten = new Intent(SampleActivity.this, SampleAddActivity.class);//berpindah ke activity yang lain dgn data
            inten.putExtra(SampleAddActivity.callPlanId, idCallPlan);
            inten.putExtra(SampleAddActivity.customerId, idCustomer);
            inten.putExtra(SampleAddActivity.sessionUser, session);
            startActivityForResult(inten, 1);
        } else if (item.getItemId() == R.id.menuRefresh) {
            if (pDialog == null)
                pDialog = new ProgressDialog(this);
            pDialog.setMessage("Check Pending Data...\n Please Wait...");
            pDialog.show();

            if (null != session) {
                updateSample();
            } else {
                pDialog.dismiss();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void updateSample() {
        ArrayList<mSample> samples = callPlanController.getAllCallPlanSamplePending();
        callPlanController.reSendCallPlanSampleToServer(String.valueOf(SalesId), samples);
    }

    CallPlanController.CallBackGetDataSample callbackSample = new CallPlanController.CallBackGetDataSample() {
        @Override
        public void resultReady(boolean hasil) {
            if (hasil) {
                getdatafromserver();
            } else {
                pDialog.dismiss();
            }

        }
    };
    CallPlanController.CallBackGetDataSampleRefresh callBackGetDataSampleRefresh = new CallPlanController.CallBackGetDataSampleRefresh() {
        @Override
        public void resultReady(boolean hasil) {
            if (hasil) {
                setupRecyclerView();
            }
            pDialog.dismiss();
        }
    };

}
