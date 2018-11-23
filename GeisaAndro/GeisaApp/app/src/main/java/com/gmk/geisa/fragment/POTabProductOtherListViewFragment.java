package com.gmk.geisa.fragment;


import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;

import com.gmk.geisa.R;
import com.gmk.geisa.activities.po.PoNewActivity;
import com.gmk.geisa.adapter.POLineListAdapter;
import com.gmk.geisa.adapter.POLineOtherListAdapter;
import com.gmk.geisa.controller.CustomerController;
import com.gmk.geisa.controller.ProductController;
import com.gmk.geisa.model.mPO;
import com.gmk.geisa.model.mPoLine;
import com.gmk.geisa.model.mPoLineOther;
import com.gmk.geisa.model.mProductPriceDiskon;
import com.gmk.geisa.model.mSession;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class POTabProductOtherListViewFragment extends Fragment {

    ArrayList<mPoLineOther> plan = new ArrayList<>();
    public final static String sessionProduct = "sessionProduct";
    static final ArrayList<mProductPriceDiskon> products = new ArrayList<>();
    static final ArrayList<mProductPriceDiskon> selectedProducts = new ArrayList<>();
    private ArrayList<mPoLine> poLines = new ArrayList<>();
    mPO PO=null;
    private int totalBefore;
    private mSession session;
    ArrayList<mPoLineOther> po = new ArrayList<>();
    RecyclerView recyclerView;
    TextView totalCust, custname, custaddress, distname, distaddress;
    TextView totalproduct, listnamaerror, listnama;
    FloatingActionButton addProduct;
    private String POID;
    CustomerController customerController;
    ProductController productController;
    private SearchView findTXTEdit;
    int CustAndBranchId;
    private POLineOtherListAdapter mAdapter;
    private POLineListAdapter adapter1;
    private RecyclerView.LayoutManager mLayoutManager;
    View view;

    public static POTabProductOtherListViewFragment newInstant(String poid, mSession session, ArrayList<mProductPriceDiskon> products) {
        POTabProductOtherListViewFragment customerDetailFragment = new POTabProductOtherListViewFragment();
        // Log.e("isi call prod"," isinya"+callPlen.size());
        Bundle args = new Bundle();
        args.putSerializable("sesiPlan", products);
        args.putSerializable("sesiUser", session);
        args.putSerializable("sesiPO", poid);
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
            //if (null != arguments.getSerializable("sesiPlan"))
            //    plan = (ArrayList<mProductPriceDiskon>) arguments.getSerializable("sesiPlan");
            if (null != arguments.getSerializable("sesiUser"))
                session = (mSession) arguments.getSerializable("sesiUser");
            if (null != arguments.getSerializable("sesiPO"))
                POID = (String) arguments.getSerializable("sesiPO");
        }
        //  Log.e("isi call prod"," isinya"+plan.size());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_po_new_detail_other, container, false);
        addProduct = (FloatingActionButton) view.findViewById(R.id.addProduct);
        totalproduct = (TextView) view.findViewById(R.id.totalproduct);
        listnamaerror = (TextView) view.findViewById(R.id.listnamaerror);
        listnama = (TextView) view.findViewById(R.id.listnama);
        totalCust = (TextView) getActivity().findViewById(R.id.totalproduct);
        // Log.e("isi cust"," sitab"+plan.size());
        setupRecyclerView(view);
        addProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addOtherProduct();
            }
        });
        if(PO!=null){
            setViewItem(PO);
        }
        return view;
    }


    private void setupRecyclerView(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.lvItems);
        recyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new POLineOtherListAdapter(plan, getActivity(), totalCust);
        recyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(getActivity())
                .colorResId(R.color.colorPrimaryDark)
                .size(2)
                .build());
        recyclerView.setAdapter(mAdapter);
    }

    private void  setViewItem(mPO po){
        if (po != null) {
            plan.clear();
            plan.addAll(po.getPoLineOthers());
            setupRecyclerView(view);
        }
    }

    public void setItem(mPO po) {
        PO=po;
        plan.clear();
        plan.addAll(po.getPoLineOthers());
    }

    public ArrayList<mPoLineOther> getItem() {
        ArrayList<mPoLineOther> others = new ArrayList<>();
        for (mPoLineOther ot : plan) {
            if (!ot.isSelected()) {
                others.add(ot);
            }
        }
        return others;
    }


    public void addOtherProduct() {

        Context context = getActivity();
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.preview_change_product_other, null);
        final Button btnDel = (Button) dialogView.findViewById(R.id.btnDel);
        final Button btnChange = (Button) dialogView.findViewById(R.id.btnChange);
        final TextInputEditText otherName = (TextInputEditText) dialogView.findViewById(R.id.otherName);
        final TextInputEditText otherCode = (TextInputEditText) dialogView.findViewById(R.id.otherCode);
        final TextInputEditText otherQty = (TextInputEditText) dialogView.findViewById(R.id.otherQty);
        final TextInputEditText otherUnit = (TextInputEditText) dialogView.findViewById(R.id.otherUnit);

        AlertDialog.Builder ad = new AlertDialog.Builder(context);
        //ad.setTitle(title);
        //ad.setMessage(message);
        ad.setView(dialogView);
        final AlertDialog dialog = ad.create();


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
                String code = "", name = "", un = "";
                double qt = 0;
                //Log.e("isi qty",otherQty.getEditableText().toString());
                if (!TextUtils.isEmpty(otherCode.getEditableText())) {
                    code = otherCode.getEditableText().toString();
                }
                if (!TextUtils.isEmpty(otherName.getEditableText())) {
                    name = otherName.getEditableText().toString();
                }
                if (!TextUtils.isEmpty(otherQty.getEditableText())) {
                    qt = Double.parseDouble(otherQty.getEditableText().toString());
                }
                if (!TextUtils.isEmpty(otherUnit.getEditableText())) {
                    un = otherUnit.getEditableText().toString();
                }

                if (qt > 0) {

                    Calendar calss = Calendar.getInstance();
                    SimpleDateFormat smp2 = new SimpleDateFormat("yyMMddhhmmss");
                    String ids = session.getId() + smp2.format(calss.getTime());
                    POID=((PoNewActivity) getActivity()).getPoId();
                    mPoLineOther poOther = new mPoLineOther(ids, POID, code, name, qt, un, 1);
                    plan.add(poOther);
                    Log.e("isi adapter", qt + "," + plan.size());

                }
                setupRecyclerView(view);

                mAdapter = new POLineOtherListAdapter(plan, getActivity(), totalCust);
                mAdapter.notifyDataSetChanged();
                recyclerView.setAdapter(mAdapter);
                recyclerView.invalidate();
                dialog.dismiss();
            }
        });

        //
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

}
