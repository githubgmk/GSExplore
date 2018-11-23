package com.gmk.geisa.fragment;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;

import com.gmk.geisa.R;
import com.gmk.geisa.adapter.ProductStockAdapter;
import com.gmk.geisa.controller.CustomerController;
import com.gmk.geisa.controller.ProductController;
import com.gmk.geisa.controller.SessionController;
import com.gmk.geisa.model.mCustomerAndDistBranch;
import com.gmk.geisa.model.mSession;
import com.gmk.geisa.model.mStockBranch;
import com.kenmeidearu.searchablespinnerlibrary.SearchableSpinner;
import com.kenmeidearu.searchablespinnerlibrary.mListString;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProductTabStockViewFragment extends Fragment {

    ArrayList<mStockBranch> plan = new ArrayList<>();
    RecyclerView recyclerView;
    TextView totalCust, custname, custaddress, distname, distaddress;
    Button changecust;
    mSession session;
    CustomerController customerController;
    ProductController productController;
    SessionController sessionController;
    private SearchView findTXTEdit;
    int CustAndBranchId, SalesId;
    private ProductStockAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ProgressDialog pDialog;

    public static ProductTabStockViewFragment newInstant(ArrayList<mStockBranch> products) {
        ProductTabStockViewFragment customerDetailFragment = new ProductTabStockViewFragment();
        // Log.e("isi call prod"," isinya"+callPlen.size());
        Bundle args = new Bundle();
        args.putSerializable("sesiPlan", products);
        //args.putSerializable("idtab", idtab);
        customerDetailFragment.setArguments(args);
        return customerDetailFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        customerController = CustomerController.getInstance(getActivity());
        productController = ProductController.getInstance(getActivity());
        productController.setCallBackGetStock(callbakstock);
        sessionController = SessionController.getInstance(getActivity());
        Bundle arguments = getArguments();
        if (arguments != null) {
            if (null != arguments.getSerializable("sesiPlan"))
                plan = (ArrayList<mStockBranch>) arguments.getSerializable("sesiPlan");
        }
        if (sessionController.cekSession("login", 1)) {
            session = sessionController.getSession("login", 1);
            if (session != null)
                SalesId = session.getId();
        }
        //  Log.e("isi call prod"," isinya"+plan.size());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_product_stock_list, container, false);
        // Log.e("isi cust"," sitab"+plan.size());
        findTXTEdit = (SearchView) view.findViewById(R.id.findTXTEdit);
        distname = (TextView) view.findViewById(R.id.distname);
        changecust = (Button) view.findViewById(R.id.changecust);
        distaddress = (TextView) view.findViewById(R.id.month);
        setupRecyclerView(view);
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
        changecust.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmLokasi();
            }
        });
       /* TextView textView=(TextView) view.findViewById(R.id.text);
        if(null!=tes)
            textView.setText(tes);*/

        return view;
    }

    private void setupRecyclerView(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.lvItems);
        recyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new ProductStockAdapter(plan, getActivity());
        recyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(getActivity())
                .colorResId(R.color.colorPrimaryDark)
                .size(2)
                .build());
        recyclerView.setAdapter(mAdapter);
    }

    public ArrayList<mStockBranch> getItem() {
        return plan;
    }


    public void confirmLokasi() {
        //imgEdit = true;

        Context context = getActivity();
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.preview_change_distributor, null);
        final Button btnDel = (Button) dialogView.findViewById(R.id.btnDel);
        final Button btnChange = (Button) dialogView.findViewById(R.id.btnChange);
        final TextInputEditText custDistBranchId = (TextInputEditText) dialogView.findViewById(R.id.custDistBranchId);
        final SearchableSpinner listCustDistributor = (SearchableSpinner) dialogView.findViewById(R.id.listCustBranch);
        final ArrayList<mListString> listStringsCustomerDistributor = new ArrayList<>();
        listStringsCustomerDistributor.add(new mListString(0, "Select Distributor"));
        final ArrayList<mCustomerAndDistBranch> lvl = customerController.getAllDistBranchDistinct(SalesId);
        for (mCustomerAndDistBranch lv : lvl) {
            mListString isi = new mListString(lv.getCustomerDistBranchId(), lv.getDistBranchName(), lv.getDistBranchAddress(), lv.getDistBranchAreaName(), lv.getDistBranchAreaCode());
            listStringsCustomerDistributor.add(isi);
        }
        listCustDistributor.setAdapter(listStringsCustomerDistributor, 4, 4);

        AlertDialog.Builder ad = new AlertDialog.Builder(context);
        //ad.setTitle(title);
        //ad.setMessage(message);
        ad.setView(dialogView);
        final AlertDialog dialog = ad.create();


        listCustDistributor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Log.e("tes posisi", id + " ," + position + "," + listStringsLevel.get(position).get_id());
                custDistBranchId.setText(String.valueOf(listStringsCustomerDistributor.get(position).get_id()));

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean cancel = false;
                View focusView = null;
                getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                custDistBranchId.setError(null);
                 if (TextUtils.isEmpty(custDistBranchId.getEditableText())) {
                    custDistBranchId.setError(getString(R.string.error_field_required));
                    focusView = listCustDistributor;
                    cancel = true;
                }
                if (cancel) {
                    focusView.requestFocus();
                } else {

                    String dname = listStringsCustomerDistributor.get(listCustDistributor.getSelectedItemPosition()).getNilai1();
                    String dgp = listStringsCustomerDistributor.get(listCustDistributor.getSelectedItemPosition()).getNilai3();
                    String daddr = listStringsCustomerDistributor.get(listCustDistributor.getSelectedItemPosition()).getNilai2();
                    String ccode = listStringsCustomerDistributor.get(listCustDistributor.getSelectedItemPosition()).getNilai4();
                    distname.setText(dname.concat(" (" + dgp + ")"));
                    distaddress.setText(daddr);
                    if (TextUtils.isEmpty(dgp)) {
                        distname.setTextColor(Color.RED);
                    } else {
                        distname.setTextColor(Color.BLACK);
                    }
                    CustAndBranchId = listStringsCustomerDistributor.get(listCustDistributor.getSelectedItemPosition()).get_id();
                    generateStok(String.valueOf(CustAndBranchId));
                   // plan = productController.getAllProductPriceDiskon(String.valueOf(CustAndBranchId));
                    //  Log.e("isi plan",plan.size()+" ttl");
                    dialog.dismiss();
                }
            }
        });

        //
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        dialog.show();
    }


   private void generateStok(String BranchId){
       if (pDialog == null)
           pDialog = new ProgressDialog(getActivity());
       pDialog.setMessage("Getting Data From Server ...\n Please Wait...");
       pDialog.show();
       if (null != session) {
           productController.getAllProductStockFromServer(BranchId);
       } else {
           pDialog.dismiss();
       }
    }
    ProductController.CallBackGetStock callbakstock=new ProductController.CallBackGetStock() {
        @Override
        public void resultReady(ArrayList<mStockBranch> stock, boolean hasil) {
            plan=stock;
            mAdapter = new ProductStockAdapter(plan, getActivity());
            mAdapter.notifyDataSetChanged();
            recyclerView.setAdapter(mAdapter);
            recyclerView.invalidate();
            pDialog.dismiss();
        }
    };
}
