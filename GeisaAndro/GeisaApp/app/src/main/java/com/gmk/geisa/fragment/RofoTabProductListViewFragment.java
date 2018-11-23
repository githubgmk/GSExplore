package com.gmk.geisa.fragment;


import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;

import com.gmk.geisa.R;
import com.gmk.geisa.activities.po.PoNewActivity;
import com.gmk.geisa.activities.po.PoNewAddProductActivity;
import com.gmk.geisa.adapter.POLineListAdapter;
import com.gmk.geisa.adapter.ProductPriceListAdapter;
import com.gmk.geisa.adapter.RofoListAdapter;
import com.gmk.geisa.controller.CustomerController;
import com.gmk.geisa.controller.ProductController;
import com.gmk.geisa.model.mCustomer;
import com.gmk.geisa.model.mCustomerAndDistBranch;
import com.gmk.geisa.model.mPO;
import com.gmk.geisa.model.mPoLine;
import com.gmk.geisa.model.mProductPriceDiskon;
import com.gmk.geisa.model.mRofo;
import com.gmk.geisa.model.mSession;
import com.kenmeidearu.searchablespinnerlibrary.SearchableSpinner;
import com.kenmeidearu.searchablespinnerlibrary.mListString;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class RofoTabProductListViewFragment extends Fragment {

    ArrayList<mRofo> plan = new ArrayList<>();

    static final ArrayList<mRofo> products = new ArrayList<>();
    static final ArrayList<mRofo> selectedProducts = new ArrayList<>();

    RecyclerView recyclerView;
    private SearchView findTXTEdit;
    TextView ttlItem,ttlRofo;

    CustomerController customerController;
    ProductController productController;

    private RofoListAdapter adapter1;
    private RecyclerView.LayoutManager mLayoutManager;
    View view;

    public static RofoTabProductListViewFragment newInstant(ArrayList<mRofo> products) {
        RofoTabProductListViewFragment customerDetailFragment = new RofoTabProductListViewFragment();
        // Log.e("isi call prod"," isinya"+callPlen.size());
        Bundle args = new Bundle();
        args.putSerializable("sesiPlan", products);
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
                plan = (ArrayList<mRofo>) arguments.getSerializable("sesiPlan");
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_rofo_list, container, false);
        findTXTEdit = (SearchView) view.findViewById(R.id.findTXTEdit);
        ttlRofo=(TextView) view.findViewById(R.id.ttlRofo);
        ttlItem=(TextView) view.findViewById(R.id.ttlItem);
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


    public void hitungHarga(ArrayList<mRofo> po) {
        double jumlahtotal = 0;
        int total = 0;
        for (mRofo cu : po) {
            if (!cu.isSelected()) {
                double harga = cu.getValue();
                jumlahtotal += cu.getQty() * harga;
                total+=cu.getQty();
            }
        }
        NumberFormat nf = NumberFormat.getInstance(Locale.GERMAN);
        String nilai = nf.format(Math.round(jumlahtotal));
        ttlRofo.setText(nilai);
        ttlItem.setText(String.valueOf(total));
    }

    private void setupRecyclerView(View view) {
       // Log.e("isi line",poLines.size()+",");
        recyclerView = (RecyclerView) view.findViewById(R.id.lvItems);
        recyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        adapter1 = new RofoListAdapter(plan, getActivity(),false, ttlRofo, ttlItem);
        recyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(getActivity())
                .colorResId(R.color.colorPrimaryDark)
                .size(2)
                .build());
        recyclerView.setAdapter(adapter1);
        hitungHarga(plan);
    }




}
