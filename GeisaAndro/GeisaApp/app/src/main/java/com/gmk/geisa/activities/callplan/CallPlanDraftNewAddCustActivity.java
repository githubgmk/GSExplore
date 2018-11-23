package com.gmk.geisa.activities.callplan;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import android.widget.SearchView;
import android.widget.TextView;

import com.gmk.geisa.R;

import com.gmk.geisa.adapter.CustomerCheckedListAdapter;
import com.gmk.geisa.controller.CustomerController;
import com.gmk.geisa.model.mCustomer;
import com.gmk.geisa.model.mSession;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CallPlanDraftNewAddCustActivity extends AppCompatActivity {
    Button addSelect, addCancel;
    public final static String sessionUser = "sessionLogin";
    public final static String sessionCustomer = "sessionProduct";
    private mSession session;
    private ArrayList<mCustomer> customer = new ArrayList<>();
    private int SalesId;
    private CustomerController customerController;


    ProgressDialog pdialog;
    TextView totalCust;
    SearchView findTXTEdit;
    RecyclerView recyclerView;
    private CustomerCheckedListAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_plan_add_customer);
        setTitle("");
        this.setFinishOnTouchOutside(false);
        this.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        addSelect = (Button) findViewById(R.id.addSelect);
        addCancel = (Button) findViewById(R.id.addCancel);
        totalCust = (TextView) findViewById(R.id.totalCust);
        findTXTEdit = (SearchView) findViewById(R.id.findTXTEdit);
        if (getIntent().getSerializableExtra(sessionUser) != null) {
            session = (mSession) getIntent().getSerializableExtra(sessionUser);
            SalesId = session.getId();

        }
        if (getIntent().getSerializableExtra(sessionCustomer) != null) {
            customer= (ArrayList<mCustomer>) getIntent().getSerializableExtra(sessionCustomer);
        }
        customerController = CustomerController.getInstance(this);
        setupRecyclerView();

        findTXTEdit.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mAdapter.setFilter().filter(newText);
                return false;
            }
        });

        addSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //return data customer with selection
                List<mCustomer> cust=mAdapter.getAllItem();
                Intent returnIntent = new Intent();
                returnIntent.putExtra(CallPlanDraftNewActivity.sessionCustomer, (Serializable) cust);
                setResult(Activity.RESULT_OK,returnIntent);
                finish();
            }
        });
        addCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    private void setupRecyclerView() {
        recyclerView = (RecyclerView) findViewById(R.id.lvItems);
        recyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);

        if (null==customer || customer.size()<=0){
           // Log.e("cek",customer.size()+"xy");
            customer = customerController.getAllCustomer(SalesId, "1","3");
        }

        mAdapter = new CustomerCheckedListAdapter(customer, this, totalCust);
        recyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this)
                .colorResId(R.color.colorPrimaryDark)
                .size(2)
                .build());
        recyclerView.setAdapter(mAdapter);
    }


}
