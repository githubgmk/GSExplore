package com.gmk.geisa.activities.customer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.gmk.geisa.R;
import com.gmk.geisa.adapter.CustomerOutletAdapter;
import com.gmk.geisa.controller.CustomerController;
import com.gmk.geisa.model.mCustomer;

import java.util.ArrayList;
import java.util.List;

public class CustomerOutletActivity extends AppCompatActivity {
    private TextView customerName;
    private RecyclerView lvItems;
    public final static String sessioncustomer = "sesicustomer";
    private mCustomer customer;
    ArrayList<mCustomer> mCustomer;
    private CustomerOutletAdapter customerOutletAdapter;
    private CustomerController customerController;
    private LinearLayoutManager linearLayoutManager;
    private int PAGE_SIZE = 10;
    private boolean isLastPage = false;
    private boolean isLoading = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_outlet);
        setTitle("Customer Outlet");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        customerName=(TextView) findViewById(R.id.customerName);
        customerController = CustomerController.getInstance(this);
        lvItems=(RecyclerView) findViewById(R.id.lvItems);
        if (getIntent().getSerializableExtra(sessioncustomer) != null){
            //kirim data locations ke tabfragement1;
            customer = (mCustomer) getIntent().getSerializableExtra(sessioncustomer);
            customerName.setText("Customer Name : "+customer.getCustName());
        }

        linearLayoutManager = new LinearLayoutManager(this);
        customerOutletAdapter = new CustomerOutletAdapter(this);
        lvItems.setLayoutManager(linearLayoutManager);
        lvItems.setAdapter(customerOutletAdapter);
        lvItems.addOnScrollListener(recyclerViewOnScrollListener);

        initData();
    }
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
            Toast.makeText(getApplicationContext(), "Add New mCustomer", Toast.LENGTH_SHORT).show();
            Intent inten= new Intent(getApplicationContext(),CustomerAddActivity.class);//berpindah ke activity yang lain dgn data
            //inten.putExtra(CustomerActivity.KEY_DATA,"Training Applikasi Android");
            //inten.putExtra(CustomerActivity.KEY_DATA_NUMBER,123);
            //startActivity(inten);

            startActivityForResult(inten, 11);
            return  true;
        }
        return  super.onOptionsItemSelected(item);
    }

    private RecyclerView.OnScrollListener recyclerViewOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            int visibleItemCount = linearLayoutManager.getChildCount();
            int totalItemCount = linearLayoutManager.getItemCount();
            int firstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition();

            if (!isLoading && !isLastPage) {
                if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                        && firstVisibleItemPosition >= 0
                        && totalItemCount >= PAGE_SIZE) {
                    isLoading = true;
                    loadData();

                }
            }
        }
    };

    private void initData() {
        List<mCustomer> memberList = new ArrayList<>();
        if(null!=customer) {
            mCustomer = customerController.getAllCustomerByCustGroup(customer.getCustId());
            int end =PAGE_SIZE;
            if (end >= mCustomer.size()) end = mCustomer.size() - 1;
            if (end <= mCustomer.size() - 1) {
                for (int i = 0; i <= end; i++) {
                    memberList.add(mCustomer.get(i));
                }
                customerOutletAdapter.addAll(memberList);
                if (end >= mCustomer.size() - 1) {
                    customerOutletAdapter.setLoading(false);
                }
            }else {
                customerOutletAdapter.setLoading(false);
            }
        }


    }


    private void loadData() {
        isLoading = false;
        List<mCustomer> memberList = new ArrayList<>();
        int index = customerOutletAdapter.getItemCount() - 1;
        int end = index + PAGE_SIZE;
        if (end >= mCustomer.size()) end = mCustomer.size() - 1;

        if (end <= mCustomer.size() - 1) {

            for (int i = index; i <= end; i++) {

                memberList.add(mCustomer.get(i));

            }
            customerOutletAdapter.addAll(memberList);
            if (end >= mCustomer.size() - 1) {
                customerOutletAdapter.setLoading(false);
            }
        } else {
            customerOutletAdapter.setLoading(false);
        }

    }

}
