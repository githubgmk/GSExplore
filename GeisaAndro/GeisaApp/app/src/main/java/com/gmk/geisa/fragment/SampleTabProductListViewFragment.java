package com.gmk.geisa.fragment;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.TextView;

import com.gmk.geisa.R;
import com.gmk.geisa.activities.callplan.visit.ComplainActivity;
import com.gmk.geisa.activities.callplan.visit.SampleItemAddProductActivity;
import com.gmk.geisa.activities.po.PoNewAddProductActivity;
import com.gmk.geisa.adapter.SampleLineListAdapter;
import com.gmk.geisa.controller.CustomerController;
import com.gmk.geisa.controller.ProductController;
import com.gmk.geisa.model.mCustomer;
import com.gmk.geisa.model.mCustomerAndDistBranch;
import com.gmk.geisa.model.mPO;
import com.gmk.geisa.model.mPoLine;
import com.gmk.geisa.model.mProduct;
import com.gmk.geisa.model.mSample;
import com.gmk.geisa.model.mSession;
import com.kenmeidearu.searchablespinnerlibrary.SearchableSpinner;
import com.kenmeidearu.searchablespinnerlibrary.mListString;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class SampleTabProductListViewFragment extends Fragment {

    ArrayList<mSample.mProductSample> plan = new ArrayList<>();
    public final static String sessionProduct = "sessionProduct";
    static final ArrayList<mProduct> products = new ArrayList<>();
    static final ArrayList<mProduct> selectedProducts = new ArrayList<>();
    private ArrayList<mSample.mProductSample> itemSample = new ArrayList<>();
    mSample sample = null;
    private int totalBefore;
    private mSession session;
    ArrayList<mPoLine> po = new ArrayList<>();
    RecyclerView recyclerView;
    TextView totalCust, custname, custaddress, distname, distaddress;
    TextView totalproduct, listnamaerror, listnama, ttlAllItem;

    Button addProduct, addProductFromOther;
    private String SampleId;
    CustomerController customerController;
    ProductController productController;
    private String StatusSample, TypeSample,SampleName="request";

    int CustAndBranchId;
    private SampleLineListAdapter adapter1;
    private RecyclerView.LayoutManager mLayoutManager;
    View view;

    private boolean isSalesCofirm = false;

    public static SampleTabProductListViewFragment newInstant(String sampleId,mSample sample, mSession session, String typeSample, String statusSample, ArrayList<mSample.mProductSample> products) {
        SampleTabProductListViewFragment customerDetailFragment = new SampleTabProductListViewFragment();
        // Log.e("isi call prod"," isinya"+callPlen.size());
        Bundle args = new Bundle();
        args.putSerializable("sesiPlan", products);
        args.putSerializable("sesiUser", session);
        args.putSerializable("sesiSampleId", sampleId);
        args.putSerializable("sesiStatus", statusSample);
        args.putSerializable("sesitypeSample", typeSample);
        args.putSerializable("sesiSample", sample);
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
                itemSample = (ArrayList<mSample.mProductSample>) arguments.getSerializable("sesiPlan");
            // Log.e("total product",plan.size()+" ukuran");
            if (null != arguments.getSerializable("sesiUser"))
                session = (mSession) arguments.getSerializable("sesiUser");
            if (null != arguments.getSerializable("sesiSampleId"))
                SampleId = (String) arguments.getSerializable("sesiSampleId");
            if (null != arguments.getSerializable("sesiStatus"))
                StatusSample = (String) arguments.getSerializable("sesiStatus");
            if (null != arguments.getSerializable("sesitypeSample")) {
                TypeSample = (String) arguments.getSerializable("sesitypeSample");
                if(TypeSample.equalsIgnoreCase("draft")) {
                    SampleName="request";
                }else{
                    SampleName="realisasi";
                }
            }
            if (null != arguments.getSerializable("sesiSample"))
                sample = (mSample) arguments.getSerializable("sesiSample");
        }
       // Log.e("isi sample prod"," isinya"+SampleId);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_sample_item_detail, container, false);
        addProduct = (Button) view.findViewById(R.id.addProduct);
        addProductFromOther = (Button) view.findViewById(R.id.addProductFromOther);
        ttlAllItem = (TextView) view.findViewById(R.id.ttlAllItem);
        totalproduct = (TextView) view.findViewById(R.id.totalproduct);
        listnamaerror = (TextView) view.findViewById(R.id.listnamaerror);
        listnama = (TextView) view.findViewById(R.id.listnama);

        addProductFromOther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //copy from requested
                copyProduct();
            }
        });

        addProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                Intent inten = new Intent(getActivity(), SampleItemAddProductActivity.class);
                inten.putExtra(SampleItemAddProductActivity.sessionUser, session);
                ArrayList<mProduct> data = new ArrayList<>();
                for (mSample.mProductSample pi : itemSample) {
                   // Log.e("isi poline ness produk"," "+ itemSample.size()+","+pi.isSelected()+","+pi.getProductId());
                    if (!pi.isSelected()) {
                        for (mProduct pu : selectedProducts) {
                            // Log.e("isi rec",pi.getProductName()+","+pi.getPriceId()+","+pu.getRecIdTab()+","+pu.getProductName());
                            if (pi.getProductId() == pu.getProductId()) {
                                //pu.setSelected(true);
                                // pu.setDraft(pi.isDraft());
                                data.add(pu);
                                break;
                            }
                        }
                    }
                }
                inten.putExtra(PoNewAddProductActivity.sessionSelectedProduct, data);
                inten.putExtra(PoNewAddProductActivity.sessionSalesCofirm, isSalesCofirm);
                startActivityForResult(inten, 141);
            }
        });
        // Log.e("isi type",TypeSample+","+ StatusSample);
        if (TypeSample.equalsIgnoreCase(StatusSample)) {
            addProduct.setVisibility(View.VISIBLE);
            if (sample.getProductOfRequest() != null && !TypeSample.equalsIgnoreCase("draft") ) {
                if (sample.getProductOfRequest().size() > 0)
                    addProductFromOther.setVisibility(View.VISIBLE);
            }
        } else {
            addProduct.setVisibility(View.GONE);
        }

        //aktifkan copy from product

        if (itemSample != null) {
            setupRecyclerView(view);
            totalBefore = itemSample.size();
            totalproduct.setText(String.valueOf(totalBefore));
        }

        return view;
    }

    private void copyProduct() {
        AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
        alertDialog.setTitle("");
        alertDialog.setMessage("Copy Data from request?!");
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        //sample.setProductOfRealisasi(sample.getProductOfRequest());
                        addRealisasi();
                    }
                });
        alertDialog.show();
    }
    private void  addRealisasi(){
        ArrayList<mProduct> allProduct = productController.getAllProduct("1");
        int ids = 1;
        for (mSample.mProductSample cu : sample.getProductOfRequest()) {
            Calendar calss = Calendar.getInstance();
            SimpleDateFormat smp2 = new SimpleDateFormat("yyyy/MM/dd");
            SimpleDateFormat smp3 = new SimpleDateFormat("yyyyMMddhhmmss");
            String idpo = String.valueOf(session.getId()).concat(smp3.format(calss.getTime())).concat(String.valueOf(ids));
            cu.setSampleProdukId(idpo);
            cu.setTypeRequest(SampleName);
            //Log.e("kode sample",cu.getSampleId()+","+cu.getProductName()+","+cu.getSampleProdukId());
            itemSample.add(cu);
            ids++;
            for (mProduct pu : allProduct) {
                if (cu.getProductId() == pu.getProductId()) {
                    selectedProducts.add(pu);
                    break;
                }
            }
        }

        setupRecyclerView(view);
        totalBefore = itemSample.size();
        totalproduct.setText(String.valueOf(totalBefore));
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == 141) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                if (null != data.getSerializableExtra(sessionProduct)) {
                    ArrayList<mSample.mProductSample> line = new ArrayList<>();
                    line.addAll(itemSample);
                    itemSample.clear();
                    products.clear();
                    products.addAll((ArrayList<mProduct>) data.getSerializableExtra(sessionProduct));
                    selectedProducts.clear();
                    ArrayList<mProduct> customer = new ArrayList<>();
                    customer.addAll(products);
                    int ids = 1;
                    for (mProduct cu : customer) {
                        final mProduct cust = cu;
                        if (cust.isSelected()) {
                            cust.setSelected(false);
                            mSample.mProductSample poLine = null;
                            boolean ada = false;
                            selectedProducts.add(cust);
                            for (mSample.mProductSample ln : line) {
                                if (ln.getProductId() == cu.getProductId()) {
                                    poLine = ln;
                                    ada = true;
                                    break;
                                }
                            }
                            //cust.setSelected(true);
                            //cek apa ada perubahan di produk yg sudah diterima
                            if (!ada) {

                                //ids kedua dan sebelumya nanti harus dirubah jika sudah punya promo
                                Calendar calss = Calendar.getInstance();
                                SimpleDateFormat smp2 = new SimpleDateFormat("yyyy/MM/dd");
                                SimpleDateFormat smp3 = new SimpleDateFormat("yyyyMMddhhmmss");
                                String idpo = String.valueOf(session.getId()).concat(smp3.format(calss.getTime())).concat(String.valueOf(ids));
                                poLine = new mSample.mProductSample(String.valueOf(idpo), SampleId, cust.getProductId(), cust.getProductName(), cust.getProductCode(), "-", 0, "",
                                        SampleName, smp2.format(calss.getTime()));
                                Log.e("id po",idpo+","+SampleId+","+selectedProducts.size()+","+itemSample.size());
                            }
                            itemSample.add(poLine);

                            ids++;
                        }
                    }
                }
                setupRecyclerView(view);
                totalBefore = itemSample.size();
                totalproduct.setText(String.valueOf(totalBefore));
            }
        }
    }

    private void setupRecyclerView(View view) {
        //Log.e("isi line",itemSample.size()+",");
        recyclerView = (RecyclerView) view.findViewById(R.id.lvItems);
        recyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        adapter1 = new SampleLineListAdapter(itemSample, getActivity());
        recyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(getActivity())
                .colorResId(R.color.colorPrimaryDark)
                .size(2)
                .build());
        //Log.e("total adapter",adapter1.getItemCount()+"x"+adapter1.getItem(0).getProductId());
        recyclerView.setAdapter(adapter1);
    }

    public void setRecyclerView() {
        /*itemSample.clear();
        if (adapter1 != null) {
            if (view != null)
                setupRecyclerView(view);
            adapter1.notifyDataSetChanged();

        }*/
    }

    private void setViewItem(mPO po) {
       /* if(po!=null) {
            //itemSample.clear();
           // itemSample.addAll(po.getPoLines());
            //Log.e("isi poline ness"," "+ itemSample.size());
            setupRecyclerView(view);
        }*/
    }

    public void setItem(mSample po) {
        sample = po;
    }

    public ArrayList<mSample.mProductSample> getItem() {
        listnamaerror.setVisibility(View.INVISIBLE);
        if (itemSample == null || itemSample.size() <= 0)
            listnamaerror.setVisibility(View.VISIBLE);
       // Log.e("isi poitem", itemSample.size() + ",");
        ArrayList<mSample.mProductSample> sample = new ArrayList<>();
        for (mSample.mProductSample sm : itemSample) {
            if (sm.getQty() > 0)
                sample.add(sm);
        }
        if (sample.size() <= 0) {
            listnamaerror.setText("Input Item beserta Qty");
            listnamaerror.setVisibility(View.VISIBLE);
        }
        return sample;
    }


    public void confirmLokasi() {
        //imgEdit = true;

        Context context = getActivity();
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.preview_change_customer, null);
        final TextInputEditText custId = (TextInputEditText) dialogView.findViewById(R.id.custid);
        final Button btnDel = (Button) dialogView.findViewById(R.id.btnDel);
        final Button btnChange = (Button) dialogView.findViewById(R.id.btnChange);
        final SearchableSpinner listCustomer = (SearchableSpinner) dialogView.findViewById(R.id.listCustomer);
        final TextInputEditText custDistBranchId = (TextInputEditText) dialogView.findViewById(R.id.custDistBranchId);
        final SearchableSpinner listCustDistributor = (SearchableSpinner) dialogView.findViewById(R.id.listCustBranch);
        final ArrayList<mListString> listStringsCustomer = new ArrayList<>();
        final ArrayList<mListString> listStringsCustomerDistributor = new ArrayList<>();
        listStringsCustomer.add(new mListString(0, "Select Customer"));
        final ArrayList<mCustomer> lvl = customerController.getAllCustomer(session.getId(), "1");
        for (mCustomer lv : lvl) {
            mListString isi = new mListString(lv.getCustId(), lv.getCustName(), lv.getAddress());
            listStringsCustomer.add(isi);
        }
        listCustomer.setAdapter(listStringsCustomer, 3, 3);
        listStringsCustomerDistributor.add(new mListString(0, "Select Distributor"));
        listCustDistributor.setAdapter(listStringsCustomerDistributor, 4, 4);


        AlertDialog.Builder ad = new AlertDialog.Builder(context);
        //ad.setTitle(title);
        //ad.setMessage(message);
        ad.setView(dialogView);
        final AlertDialog dialog = ad.create();

        //change cust
        listCustomer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                custId.setText(String.valueOf(listStringsCustomer.get(position).get_id()));
                if (position > 0) {
                    listStringsCustomerDistributor.clear();
                    listStringsCustomerDistributor.add(new mListString(0, "Select Distributor"));
                    mCustomer cust = lvl.get(position - 1);
                    int total = 0;
                    for (mCustomerAndDistBranch lv : cust.getCustomerAndBranch()) {
                        total++;
                        mListString isi = new mListString(lv.getCustomerDistBranchId(), lv.getDistBranchName(), lv.getDistBranchAddress(), lv.getPriceGroupId(), lv.getCustCode());
                        listStringsCustomerDistributor.add(isi);
                    }
                    listCustDistributor.setAdapter(listStringsCustomerDistributor, 4, 4);
                    if (total == 1) {
                        listCustDistributor.setSelection(1);
                    }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

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
                custId.setError(null);
                if (TextUtils.isEmpty(custId.getEditableText())) {
                    custId.setError(getString(R.string.error_field_required));
                    focusView = listCustomer;
                    cancel = true;
                } else if (TextUtils.isEmpty(custDistBranchId.getEditableText())) {
                    custDistBranchId.setError(getString(R.string.error_field_required));
                    focusView = listCustDistributor;
                    cancel = true;
                }
                if (cancel) {
                    focusView.requestFocus();
                } else {
                    String cname = listStringsCustomer.get(listCustomer.getSelectedItemPosition()).getNilai1();
                    int cid = listStringsCustomer.get(listCustomer.getSelectedItemPosition()).get_id();
                    String caddr = listStringsCustomer.get(listCustomer.getSelectedItemPosition()).getNilai3();
                    String dname = listStringsCustomerDistributor.get(listCustDistributor.getSelectedItemPosition()).getNilai1();
                    String dgp = listStringsCustomerDistributor.get(listCustDistributor.getSelectedItemPosition()).getNilai3();
                    String daddr = listStringsCustomerDistributor.get(listCustDistributor.getSelectedItemPosition()).getNilai2();
                    String ccode = listStringsCustomerDistributor.get(listCustDistributor.getSelectedItemPosition()).getNilai4();
                    custname.setText(cname.concat("-" + cid).concat(":(" + ccode + ")"));
                    custaddress.setText(caddr);
                    distname.setText(dname.concat(" (" + dgp + ")"));
                    distaddress.setText(daddr);
                    if (TextUtils.isEmpty(dgp)) {
                        distname.setTextColor(Color.RED);
                    } else {
                        distname.setTextColor(Color.BLACK);
                    }
                    CustAndBranchId = listStringsCustomerDistributor.get(listCustDistributor.getSelectedItemPosition()).get_id();
                    //plan = productController.getAllProductPriceDiskon(String.valueOf(CustAndBranchId));
                    //  Log.e("isi plan",plan.size()+" ttl");
                    //  mAdapter = new ProductPriceListAdapter(plan, getActivity(), totalCust);
                    // mAdapter.notifyDataSetChanged();
                    // recyclerView.setAdapter(mAdapter);
                    recyclerView.invalidate();
                    dialog.dismiss();
                }
            }
        });

        //
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        dialog.show();
    }

}
