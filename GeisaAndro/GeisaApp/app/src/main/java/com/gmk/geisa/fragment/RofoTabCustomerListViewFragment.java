package com.gmk.geisa.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;

import com.gmk.geisa.R;
import com.gmk.geisa.adapter.CustomerAdapter;
import com.gmk.geisa.adapter.CustomerListAdapter;
import com.gmk.geisa.adapter.RofoListAdapter;
import com.gmk.geisa.controller.CustomerController;
import com.gmk.geisa.controller.ProductController;
import com.gmk.geisa.model.mCustomer;
import com.gmk.geisa.model.mRofo;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 */
public class RofoTabCustomerListViewFragment extends Fragment {

    ArrayList<mCustomer> plan = new ArrayList<>();

    RecyclerView recyclerView;
    private SearchView findTXTEdit;
    TextView ttlCust;

    CustomerController customerController;
    ProductController productController;

    private CustomerListAdapter adapter1;
    private RecyclerView.LayoutManager mLayoutManager;
    View view;

    public static RofoTabCustomerListViewFragment newInstant(ArrayList<mCustomer> customer) {
        RofoTabCustomerListViewFragment customerDetailFragment = new RofoTabCustomerListViewFragment();
        // Log.e("isi call prod"," isinya"+callPlen.size());
        Bundle args = new Bundle();
        args.putSerializable("sesiPlan", customer);
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
            if (null != arguments.getSerializable("sesiPlan"))
                plan = (ArrayList<mCustomer>) arguments.getSerializable("sesiPlan");
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_rofo_customer_list, container, false);
        findTXTEdit = (SearchView) view.findViewById(R.id.findTXTEdit);
        ttlCust=(TextView) view.findViewById(R.id.ttlCust);
        hitungCust(plan);
        setupRecyclerView(view);
        //SEARCH
        findTXTEdit.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                //FILTER AS YOU TYPE
                adapter1.setFilter().filter(query);
                return false;
            }
        });

        return view;
    }


    public void hitungCust(ArrayList<mCustomer> po) {
        ttlCust.setText(String.valueOf(po.size()));
    }

    private void setupRecyclerView(View view) {
       // Log.e("isi line",poLines.size()+",");
        recyclerView = (RecyclerView) view.findViewById(R.id.lvItems);
        recyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        adapter1 = new CustomerListAdapter(plan,getActivity());
        recyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(getActivity())
                .colorResId(R.color.colorPrimaryDark)
                .size(2)
                .build());
        recyclerView.setAdapter(adapter1);

    }




}
