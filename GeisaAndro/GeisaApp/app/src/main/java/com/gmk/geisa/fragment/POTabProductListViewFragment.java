package com.gmk.geisa.fragment;


import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
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
import com.gmk.geisa.activities.po.PoNewActivity;
import com.gmk.geisa.activities.po.PoNewAddProductActivity;
import com.gmk.geisa.adapter.POLineListAdapter;
import com.gmk.geisa.adapter.ProductPriceListAdapter;
import com.gmk.geisa.controller.CustomerController;
import com.gmk.geisa.controller.ProductController;
import com.gmk.geisa.model.mCustomer;
import com.gmk.geisa.model.mCustomerAndDistBranch;
import com.gmk.geisa.model.mPO;
import com.gmk.geisa.model.mPoLine;
import com.gmk.geisa.model.mProductPriceDiskon;
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
public class POTabProductListViewFragment extends Fragment {

    ArrayList<mProductPriceDiskon> plan = new ArrayList<>();
    public final static String sessionProduct = "sessionProduct";
    static final ArrayList<mProductPriceDiskon> products = new ArrayList<>();
    static final ArrayList<mProductPriceDiskon> selectedProducts = new ArrayList<>();
    private ArrayList<mPoLine> poLines = new ArrayList<>();
    mPO PO = null;
    mPO POPP = null;
    private int totalBefore;
    private mSession session;
    ArrayList<mPoLine> po = new ArrayList<>();
    RecyclerView recyclerView;
    TextView totalCust, custname, custaddress, distname, distaddress;
    TextView totalproduct, listnamaerror, listnama, jovalue, ppnvalue, ttlvalue;
    TextInputEditText discountfaktur1, discountfaktur2, discountfakturcash;
    TextInputLayout layoutdiscountfaktur1, layoutdiscountfaktur2, layoutdiscountfakturcash;
    FloatingActionButton addProduct;
    private String POID, PoPPRefId = "";
    CustomerController customerController;
    ProductController productController;

    int CustAndBranchId;
    private ProductPriceListAdapter mAdapter;
    private POLineListAdapter adapter1;
    private RecyclerView.LayoutManager mLayoutManager;
    View view;
    private boolean isPP = false, isCopyPP = false,isSellOut=false;
    private boolean isSalesCofirm = false;
    private boolean disFakturEnable = true;
    private int levelCust = 0;

    public static POTabProductListViewFragment newInstant(String poid, mSession session, ArrayList<mProductPriceDiskon> products) {
        POTabProductListViewFragment customerDetailFragment = new POTabProductListViewFragment();
        // Log.e("isi call prod"," isinya"+callPlen.size());
        Bundle args = new Bundle();
        args.putSerializable("sesiPlan", products);
        args.putSerializable("sesiUser", session);
        args.putSerializable("sesiPO", poid);
        //args.putSerializable("sesiPOPPRef", refId);
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
                plan = (ArrayList<mProductPriceDiskon>) arguments.getSerializable("sesiPlan");
            if (null != arguments.getSerializable("sesiUser"))
                session = (mSession) arguments.getSerializable("sesiUser");
            if (null != arguments.getSerializable("sesiPO"))
                POID = (String) arguments.getSerializable("sesiPO");
           /* if (null != arguments.getSerializable("sesiPO"))
                PoPPRefId = (String) arguments.getSerializable("sesiPOPPRef");*/
        }
        //  Log.e("isi call prod"," isinya"+plan.size());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_po_new_detail_item, container, false);
        addProduct = (FloatingActionButton) view.findViewById(R.id.addProduct);
        totalproduct = (TextView) view.findViewById(R.id.totalproduct);
        listnamaerror = (TextView) view.findViewById(R.id.listnamaerror);
        listnama = (TextView) view.findViewById(R.id.listnama);
        // Log.e("isi cust"," sitab"+plan.size());
        jovalue = (TextView) view.findViewById(R.id.ttlRofo);
        ppnvalue = (TextView) view.findViewById(R.id.ttlItem);
        ttlvalue = (TextView) view.findViewById(R.id.ttlvalue);
        discountfaktur1 = (TextInputEditText) view.findViewById(R.id.discountfaktur1);
        discountfaktur2 = (TextInputEditText) view.findViewById(R.id.discountfaktur2);
        layoutdiscountfaktur1 = view.findViewById(R.id.layoutdiscountfaktur1);
        layoutdiscountfaktur2 = view.findViewById(R.id.layoutdiscountfaktur2);
        layoutdiscountfakturcash = view.findViewById(R.id.layoutdiscountfakturcash);
        discountfakturcash = (TextInputEditText) view.findViewById(R.id.discountfakturcash);

        addProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                Intent inten = new Intent(getActivity(), PoNewAddProductActivity.class);
                session = ((PoNewActivity) getActivity()).getSession();
                inten.putExtra(PoNewAddProductActivity.sessionUser, session);
                int disandbranch = ((PoNewActivity) getActivity()).getCustAndBranchId();
                ArrayList<mProductPriceDiskon> data = new ArrayList<mProductPriceDiskon>();
                inten.putExtra(PoNewAddProductActivity.sessionCustDistBranch, disandbranch);
                for (mPoLine pi : poLines) {
                    //Log.e("isi poline ness produk"," "+ poLines.size()+","+pi.isSelected());
                    if ((!pi.isSelected() && !isSalesCofirm) || isSalesCofirm) {
                        for (mProductPriceDiskon pu : selectedProducts) {
                            // Log.e("isi rec",pi.getProductName()+","+pi.getPriceId()+","+pu.getRecIdTab()+","+pu.getProductName());
                            if (pi.getPriceId() == pu.getRecId()) {
                                //pu.setSelected(true);
                                pu.setDraft(pi.isDraft());
                                data.add(pu);
                                break;
                            }
                        }
                    }
                }
                inten.putExtra(PoNewAddProductActivity.sessionSelectedProduct, data);
                inten.putExtra(PoNewAddProductActivity.sessionSalesCofirm, isSalesCofirm);
                startActivityForResult(inten, 100);
            }
        });

        setCustLevel(levelCust);
        setIsPP(isPP);
        setisCopyPP(isCopyPP);
        setPoPPrefId(PoPPRefId);
        if (PO != null) {
            POID = PO.getPoId();
            checkProductSelect();
            setViewItem(PO);
        }
        if (discountfaktur2 != null)
            discountfaktur2.setEnabled(disFakturEnable);

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == 100) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                if (null != data.getSerializableExtra(sessionProduct)) {
                    getActivity().findViewById(R.id.typepopp).setEnabled(false);
                    getActivity().findViewById(R.id.typeporeguler).setEnabled(false);
                    ArrayList<mPoLine> line = new ArrayList<>();
                    line.addAll(poLines);
                    poLines.clear();
                    products.clear();
                    products.addAll((ArrayList<mProductPriceDiskon>) data.getSerializableExtra(sessionProduct));
                    selectedProducts.clear();
                    ArrayList<mProductPriceDiskon> customer = new ArrayList<>();
                    customer.addAll(products);
                    int ids = 1;
                    for (mProductPriceDiskon cu : customer) {
                        final mProductPriceDiskon cust = cu;
                        if (cust.isSelected()) {
                            cust.setSelected(false);
                            mPoLine poLine = null;
                            boolean ada = false;
                            selectedProducts.add(cust);
                            for (mPoLine ln : line) {
                                if (ln.getPriceId() == cu.getRecId()) {
                                    poLine = ln;
                                    ada = true;
                                    break;
                                }
                            }
                            //cust.setSelected(true);
                            //cek apa ada perubahan di produk yg sudah diterima
                            if (!ada) {
                                //Log.e("id po", POID);
                                //ids kedua dan sebelumya nanti harus dirubah jika sudah punya promo
                                Calendar calss = Calendar.getInstance();
                                SimpleDateFormat smp2 = new SimpleDateFormat("yyyy/MM/dd");
                                SimpleDateFormat smp3 = new SimpleDateFormat("yyyyMMddhhmmss");
                                String idpo = String.valueOf(session.getId()).concat(smp3.format(calss.getTime())).concat(String.valueOf(ids));
                                poLine = new mPoLine(idpo,
                                        POID, cust.getProductId(), cust.getProductName(), cust.getProductCode(), 1, cust.getPrice(), cust.getUnitId(),
                                        cust.getRecId(), cust.getPrice(), 0, cust.getDisc1(), cust.getDisc2(),cust.getDisc3(),
                                        cust.getDiscRp(), 0, idpo, 0, false, smp2.format(calss.getTime()), smp2.format(calss.getTime()), 1);
                            }
                            poLines.add(poLine);
                            ids++;
                        }

                    }
                }
                setupRecyclerView(view);
                totalBefore = selectedProducts.size();
                totalproduct.setText(String.valueOf(totalBefore));
            }
        }
    }

    private void checkProductSelect() {
        ArrayList<mProductPriceDiskon> produk = productController.getAllProductPriceDiskon(String.valueOf(((PoNewActivity) getActivity()).getCustAndBranchId()));
        for (mProductPriceDiskon cu : produk) {
            boolean ada = false;
            for (mPoLine ln : poLines) {
                //Log.e("isi poline",ln.getProductCode()+","+poLines.size());
                if (ln.getPriceId() == cu.getRecId()) {
                    ada = true;
                    break;
                }
            }
            if (ada) {
                selectedProducts.add(cu);
            }
        }
    }

    public void hitungHarga(ArrayList<mPoLine> po) {
        double jumlahtotal = 0, ppn = 0, total = 0;
        for (mPoLine cu : po) {
            if (!cu.isSelected()) {
                double harga = 0;
                if (cu.isIncludePPN()) {
                    harga = (cu.getUnitPrice() / 1.1) * 1;
                } else {
                    harga = cu.getUnitPrice();
                }
                double diskontotal = (1 - (1 - cu.getDisc1() / 100) * (1 - cu.getDisc2() / 100));
                jumlahtotal += cu.getQty() * (harga - (harga * diskontotal) - cu.getDiscRp());
                // Log.e("diskontotal","ttl"+diskontotal+","+jumlahtotal) ;
            }
        }
        NumberFormat nf = NumberFormat.getInstance(Locale.GERMAN);
        String nilai = nf.format(Math.round(jumlahtotal));
        ppn = jumlahtotal * 0.1;
        // Log.e("jmlttl",ppn+","+jumlahtotal) ;
        String ppnnilai = nf.format(Math.round(ppn));
        total = jumlahtotal + ppn;
        String ttlnilai = nf.format(Math.round(total));
        jovalue.setText(nilai);
        ppnvalue.setText(ppnnilai);
        ttlvalue.setText(ttlnilai);
    }

    private void setupRecyclerView(View view) {
        // Log.e("isi line",poLines.size()+","+levelCust);
        recyclerView = (RecyclerView) view.findViewById(R.id.lvItems);
        recyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);

        adapter1 = new POLineListAdapter(levelCust, poLines, getActivity(), isPP, isCopyPP, PoPPRefId, ((PoNewActivity) getActivity()).getCustAndBranchId(), jovalue, ppnvalue, ttlvalue);
        recyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(getActivity())
                .colorResId(R.color.colorPrimaryDark)
                .size(2)
                .build());
        recyclerView.setAdapter(adapter1);
        hitungHarga(poLines);
    }

    public void setRecyclerView() {
        poLines.clear();
        if (adapter1 != null) {
            if (view != null)
                setupRecyclerView(view);
            adapter1.notifyDataSetChanged();
            if (jovalue != null)
                jovalue.setText("0");

        }
    }

    private void setViewItem(mPO po) {
        if (po != null) {
            poLines.clear();
            poLines.addAll(po.getPoLines());
            //Log.e("isi poline ness"," "+ poLines.size());
            discountfaktur1.setText(String.valueOf(po.getDisc1()));
            discountfaktur2.setText(String.valueOf(po.getDisc2()));
            discountfakturcash.setText(String.valueOf(po.getCashDisc()));
            setupRecyclerView(view);
        }
    }

    public void setItem(mPO po,int custlevel) {
        PO = po;
        POPP = po;
        levelCust=custlevel;
        //Log.e("mulai dari sini", "mulai lagi "+levelCust);
        if (po != null) {
            isSalesCofirm = true;
            poLines.clear();
            poLines.addAll(po.getPoLines());
        }
        //setViewItem(po);
       /* for (mPoLine ln:po.getPoLines()) {
            if(ln.getPoLineBonus()!=null)
                Log.e("mulai dari","lagi "+ ln.getPoLineBonus().getProductId()+","+ln.getPoLineBonus().getQty()+"/"+ln.getProductId()+","+ln.getQty());
        }*/

    }

    public ArrayList<mPoLine> getItem() {
        listnamaerror.setVisibility(View.INVISIBLE);
        if (poLines == null || poLines.size() <= 0)
            listnamaerror.setVisibility(View.VISIBLE);
        //Log.e("isi poitem", poLines.size() + ",");
        return poLines;
    }

    public double getDiscFaktur1() {
        double diskon = 0;
        if (!TextUtils.isEmpty(discountfaktur1.getEditableText())) {
            diskon = Double.valueOf(discountfaktur1.getEditableText().toString());
        }
        return diskon;
    }

    public void changeDiscFaktur2(boolean status) {
        disFakturEnable = status;

    }

    public double getDiscFaktur2() {
        double diskon = 0;
        if (!TextUtils.isEmpty(discountfaktur2.getEditableText())) {
            diskon = Double.valueOf(discountfaktur2.getEditableText().toString());
        }
        return diskon;
    }

    public double getDiscFakturCash() {
        double diskon = 0;
        if (!TextUtils.isEmpty(discountfakturcash.getEditableText())) {
            diskon = Double.valueOf(discountfakturcash.getEditableText().toString());
        }
        return diskon;
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
        final ArrayList<mCustomer> lvl = customerController.getAllCustomer(70, "1");
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
                    plan = productController.getAllProductPriceDiskon(String.valueOf(CustAndBranchId));
                    //  Log.e("isi plan",plan.size()+" ttl");
                    mAdapter = new ProductPriceListAdapter(plan, getActivity(), totalCust);
                    mAdapter.notifyDataSetChanged();
                    recyclerView.setAdapter(mAdapter);
                    recyclerView.invalidate();
                    dialog.dismiss();
                }
            }
        });

        //
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        dialog.show();
    }

    public void setIsPP(boolean isPP) {
        this.isPP = isPP;
         Log.e("is pp", isPP + "xy"+levelCust);
        if (isPP) {
            if (discountfaktur1 != null)
                discountfaktur1.setEnabled(true);
            if (levelCust == 1) {
                if (layoutdiscountfakturcash != null)
                    layoutdiscountfakturcash.setEnabled(false);//untuk diskon faktur cash selalu 0
            }else{
                if (layoutdiscountfakturcash != null)
                    layoutdiscountfakturcash.setEnabled(true);
            }
        } else {
            if (discountfaktur1 != null)
                discountfaktur1.setEnabled(false);
            if (levelCust == 1) {
                if (layoutdiscountfakturcash != null)
                    layoutdiscountfakturcash.setEnabled(false);
            } else {
                if (layoutdiscountfakturcash != null)
                    layoutdiscountfakturcash.setEnabled(true);
            }
        }


    }

    public void setisCopyPP(boolean isCopyPP) {
        this.isCopyPP = isCopyPP;
    }

    public void setPoPPrefId(String refId) {
        this.PoPPRefId = refId;
    }

    public void setCustLevel(int level) {
        this.levelCust = level;
        if (levelCust == 1) {
            if (layoutdiscountfaktur1 != null)
                layoutdiscountfaktur1.setHint(getResources().getString(R.string.discfaktur1ho));
            if (layoutdiscountfaktur2 != null)
                layoutdiscountfaktur2.setHint(getResources().getString(R.string.discfaktur2ho));
            if (layoutdiscountfakturcash != null)
                layoutdiscountfakturcash.setHint(getResources().getString(R.string.discfakturcashho));
        } else {
            if (layoutdiscountfaktur1 != null)
                layoutdiscountfaktur1.setHint(getResources().getString(R.string.discfaktur1));
            if (layoutdiscountfaktur2 != null)
                layoutdiscountfaktur2.setHint(getResources().getString(R.string.discfaktur2));
            if (layoutdiscountfakturcash != null)
                layoutdiscountfakturcash.setHint(getResources().getString(R.string.discfakturcash));
        }
    }

    public void setIsSellOut(boolean isSellOut) {
        this.isSellOut=isSellOut;
    }
}
