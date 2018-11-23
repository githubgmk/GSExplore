package com.gmk.geisa.activities.po;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.TabLayout;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.gcacace.signaturepad.views.SignaturePad;
import com.google.gson.Gson;
import com.gmk.geisa.Class.Konversi;
import com.gmk.geisa.R;
import com.gmk.geisa.controller.CustomerController;
import com.gmk.geisa.controller.PoController;
import com.gmk.geisa.controller.ProductController;
import com.gmk.geisa.controller.TrackingController;
import com.gmk.geisa.fragment.tab.TabFragmentPoPagerAdapter;
import com.gmk.geisa.model.mCustomer;
import com.gmk.geisa.model.mCustomerAndDistBranch;
import com.gmk.geisa.model.mPO;
import com.gmk.geisa.model.mPOCustInfo;
import com.gmk.geisa.model.mPoLine;
import com.gmk.geisa.model.mPoLineOther;
import com.gmk.geisa.model.mSession;
import com.gmk.geisa.model.mTrackingPicture;
import com.kenmeidearu.searchablespinnerlibrary.SearchableSpinner;
import com.kenmeidearu.searchablespinnerlibrary.mListString;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class PoNewActivity extends AppCompatActivity implements com.kenmeidearu.materialdatetimepicker.date.DatePickerDialog.OnDateSetListener {
    public final static String sessionPlan = "sesionPlan";
    public final static String sessionUser = "sessionUser";
    public final static String sessionCustomer = "sessionCustomer";
    public final static String sessionProduct = "sessionProduct";
    public final static String sessionIsCopyPP = "sessionIsCopyPP";
    public final static String sessionPO = "sessionPO";
    public final static String sessionSalesConfirm = "sessionSalesConfirm";
    public final static String sessionReportView = "sessionReportView";
    public final static String sessionSellOut = "sessionSellOut";


    private mSession session;
    private int typePlan;
    CustomerController customerController;
    ProductController productController;
    TrackingController trackingController;
    PoController poController;
    mPOCustInfo poCustinfo;
    mPO sPO, sPOPPCopy;
    ArrayList<mPoLine> poCustItem = new ArrayList<>();
    ArrayList<mPoLineOther> poCustOther = new ArrayList<>();
    private ProgressDialog pDialog;

    RelativeLayout poinfo;
    Button changecust, btnCopyPP;
    TextInputEditText ponumber, pocustnumber, tglFaktur;
    TextInputLayout layoutTglFaktur, layoutpocustnumber;
    TextView custname, custaddress, distname, distaddress, typepotext;
    RadioButton typeporeguler, typepopp;
    RadioGroup typepo;
    String tglPO, PoId, CallPlanId = "0", PicDistributor = "", PicCustomer = "";
    Date podate;

    int CustAndBranchId, BranchId, CustomerId, SalesId, custLevelId;
    boolean isPP = false;
    boolean salesConfirm = false;
    boolean isCopyPP = false;
    boolean reportView = false;
    boolean isSellOut = false;

    private ViewPager pager;
    private TabLayout tabs;
    private TabFragmentPoPagerAdapter mAdapter;
    Bitmap signatureBitmap = null;
    File photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_po_new);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("New PO");
        poinfo = (RelativeLayout) findViewById(R.id.poinfo);
        custname = (TextView) findViewById(R.id.custname);
        distname = (TextView) findViewById(R.id.distname);
        changecust = (Button) findViewById(R.id.changecust);
        btnCopyPP = (Button) findViewById(R.id.btnCopyPP);
        distaddress = (TextView) findViewById(R.id.month);
        custaddress = (TextView) findViewById(R.id.year);
        typeporeguler = (RadioButton) findViewById(R.id.typeporeguler);
        typepopp = (RadioButton) findViewById(R.id.typepopp);
        typepo = (RadioGroup) findViewById(R.id.typepo);
        typepotext = (TextView) findViewById(R.id.typepotext);
        layoutTglFaktur = findViewById(R.id.layoutTglFaktur);
        layoutpocustnumber = findViewById(R.id.layoutpocustnumber);
        tglFaktur = findViewById(R.id.tglFaktur);

        customerController = CustomerController.getInstance(this);
        productController = ProductController.getInstance(this);
        trackingController = TrackingController.getInstance(this);
        poController = PoController.getInstance(this);
        poController.setCallBackGetData(callbackpo);

        pager = (ViewPager) findViewById(R.id.pager);
        tabs = (TabLayout) findViewById(R.id.tabs);
        ponumber = (TextInputEditText) findViewById(R.id.ponumber);
        pocustnumber = (TextInputEditText) findViewById(R.id.pocustnumber);

        Calendar calss = Calendar.getInstance();
        SimpleDateFormat smp2 = new SimpleDateFormat("yyyy/MM/dd");
        SimpleDateFormat smp3 = new SimpleDateFormat("hhmmss");
        tglPO = smp2.format(calss.getTime());
        if (getIntent().getSerializableExtra(sessionUser) != null) {
            session = (mSession) getIntent().getSerializableExtra(sessionUser);
            SalesId = session.getId();
            // Log.e("isi user", " x" + session.getId());
            PoId = tglPO.concat("-").concat(String.valueOf(session.getId())).concat(smp3.format(calss.getTime()));
            setupTabIcons();
        }
        if (getIntent().getSerializableExtra(sessionPlan) != null) {
            CallPlanId = (String) getIntent().getSerializableExtra(sessionPlan);
        }
        if (getIntent().getSerializableExtra(sessionCustomer) != null) {
            CustomerId = Integer.parseInt((String) getIntent().getSerializableExtra(sessionCustomer));
            mCustomer cust = customerController.getCustomerByCustIdAndSalesId(String.valueOf(CustomerId), String.valueOf(SalesId));
            custLevelId = cust.getCustLevelId();
            //Log.e("isi level","l"+custLevelId);
        }

        if (getIntent().getSerializableExtra(sessionSalesConfirm) != null) {
            salesConfirm = (boolean) getIntent().getSerializableExtra(sessionSalesConfirm);
            if (salesConfirm) {
                btnCopyPP.setEnabled(false);
                setTitle("Sales Confirm PO");
                if (getIntent().getSerializableExtra(sessionPO) != null) {
                    poinfo.setVisibility(View.VISIBLE);
                    sPO = (mPO) getIntent().getSerializableExtra(sessionPO);
                    //Log.e("ada di","ada di "+ sPO.getPoLines().size());
                    PoId = sPO.getPoId();
                    isPP = sPO.isPP();
                    isSellOut= sPO.isSellOut();
                    typepopp.setEnabled(false);
                    typeporeguler.setEnabled(false);
                    if (sPO.isPP()) {
                        typepopp.setChecked(true);
                        typepopp.setTextColor(Color.BLUE);
                    } else {
                        typeporeguler.setChecked(true);
                        typeporeguler.setTextColor(Color.BLUE);
                    }
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    Calendar tgldpts= Calendar.getInstance();
                    Date date = null;
                    try {
                        date = format.parse(sPO.getPoDate());
                        tgldpts.setTime(date);
                        tglFaktur.setText(format.format(tgldpts.getTime()));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    pocustnumber.setText(sPO.getPoCustNumberRef());
                    CustomerId = sPO.getCustId();
                    if (sPO.getDistBranch() != null)
                        CustAndBranchId = sPO.getDistBranch().getCustomerDistBranchId();
                    BranchId = sPO.getDistBranchId();
                    String cname = sPO.getCustomer().getCustName();
                    int cid = CustomerId;
                    String caddr = sPO.getShipAddress();
                    String dname = "", dgp = "", daddr = "", ccode = "";
                    if (sPO.getDistBranch() != null) {
                        if (sPO.getDistBranch().getDistBranchName() != null)
                            dname = sPO.getDistBranch().getDistBranchName();
                        if (sPO.getDistBranch().getPriceGroupName() != null)
                            dgp = sPO.getDistBranch().getPriceGroupName();
                        if (sPO.getDistBranch().getDistBranchAddress() != null)
                            daddr = sPO.getDistBranch().getDistBranchAddress();
                        if (sPO.getDistBranch().getCustCode() != null)
                            ccode = sPO.getDistBranch().getCustCode();
                    }
                    custname.setText(cname.concat("-" + cid).concat(":(" + ccode + ")"));
                    custaddress.setText(caddr);
                    distname.setText(dname.concat(" (" + dgp + ")"));
                    distaddress.setText(daddr);
                    if (TextUtils.isEmpty(dgp)) {
                        distname.setTextColor(Color.RED);
                    } else {
                        distname.setTextColor(Color.BLACK);
                    }
                    CustomerId = cid;
                    changecust.setEnabled(false);

                    if (mAdapter != null)
                        mAdapter.clearProduct();
                    if (mAdapter != null)
                        mAdapter.setIsPP(isPP);
                    if (mAdapter != null) {
                        mAdapter.setItemCust(sPO);
                        mAdapter.setItemProduct(sPO, custLevelId);
                        mAdapter.setItemProductOther(sPO);
                    }

                }
            }
        }
        //untuk sell out
        if (getIntent().getSerializableExtra(sessionSellOut) != null) {
            isSellOut = (boolean) getIntent().getSerializableExtra(sessionSellOut);
        }
        if (isSellOut) {
            if(salesConfirm){
                setTitle("Sales Confirm PO");
            }else {
                setTitle("New SellOut");
            }
            typepopp.setEnabled(false);
            typeporeguler.setEnabled(false);
            btnCopyPP.setVisibility(View.GONE);
            btnCopyPP.setEnabled(false);
            //pocustnumber.setHint("No Faktur");
            layoutpocustnumber.setHint("No Faktur");
            layoutTglFaktur.setVisibility(View.VISIBLE);
        } else {
            btnCopyPP.setVisibility(View.VISIBLE);
            btnCopyPP.setEnabled(true);
            layoutTglFaktur.setVisibility(View.GONE);
        }

        //change tanggal
        tglFaktur.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                if (hasFocus) {
                    changeTanggal(tglFaktur.getText().toString());
                }
            }
        });
        tglFaktur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                changeTanggal(tglFaktur.getText().toString());
            }
        });
        //untuk report view
        if (getIntent().getSerializableExtra(sessionReportView) != null) {
            reportView = (boolean) getIntent().getSerializableExtra(sessionReportView);
        }
        if (reportView) {
            btnCopyPP.setVisibility(View.INVISIBLE);
            changecust.setVisibility(View.INVISIBLE);
            setTitle("Report View PO");
            if (getIntent().getSerializableExtra(sessionPO) != null) {
                poinfo.setVisibility(View.VISIBLE);
                sPO = (mPO) getIntent().getSerializableExtra(sessionPO);
                PoId = sPO.getPoId();
                isPP = sPO.isPP();
                typepopp.setEnabled(false);
                typeporeguler.setEnabled(false);
                if (sPO.isPP()) {
                    typepopp.setChecked(true);
                    typepopp.setTextColor(Color.BLUE);
                } else {
                    typeporeguler.setChecked(true);
                    typeporeguler.setTextColor(Color.BLUE);
                }
                pocustnumber.setText(sPO.getPoCustNumberRef());
                CustomerId = sPO.getCustId();
                if (sPO.getDistBranch() != null)
                    CustAndBranchId = sPO.getDistBranch().getCustomerDistBranchId();
                BranchId = sPO.getDistBranchId();
                String cname = sPO.getCustomer().getCustName();
                int cid = CustomerId;
                String caddr = sPO.getShipAddress();
                String dname = "", dgp = "", daddr = "", ccode = "";
                if (sPO.getDistBranch() != null) {
                    if (sPO.getDistBranch().getDistBranchName() != null)
                        dname = sPO.getDistBranch().getDistBranchName();
                    if (sPO.getDistBranch().getPriceGroupName() != null)
                        dgp = sPO.getDistBranch().getPriceGroupName();
                    if (sPO.getDistBranch().getDistBranchAddress() != null)
                        daddr = sPO.getDistBranch().getDistBranchAddress();
                    if (sPO.getDistBranch().getCustCode() != null)
                        ccode = sPO.getDistBranch().getCustCode();
                }
                custname.setText(cname.concat("-" + cid).concat(":(" + ccode + ")"));
                custaddress.setText(caddr);
                distname.setText(dname.concat(" (" + dgp + ")"));
                distaddress.setText(daddr);
                if (TextUtils.isEmpty(dgp)) {
                    distname.setTextColor(Color.RED);
                } else {
                    distname.setTextColor(Color.BLACK);
                }
                CustomerId = cid;
                changecust.setEnabled(false);

                if (mAdapter != null)
                    mAdapter.clearProduct();
                if (mAdapter != null)
                    mAdapter.setCustLevel(custLevelId);
                if (mAdapter != null)
                    mAdapter.setIsPP(isPP);
                if (mAdapter != null) {
                    mAdapter.setItemCust(sPO);
                    mAdapter.setItemProduct(sPO, custLevelId);
                    mAdapter.setItemProductOther(sPO);
                }

            }
        }

        //copy pp
        ponumber.setText(PoId);
        if (getIntent().getSerializableExtra(sessionIsCopyPP) != null) {
            isCopyPP = (boolean) getIntent().getSerializableExtra(sessionIsCopyPP);
            //  Log.e("isi iscopy", isCopyPP+" ," + salesConfirm);
            if (isCopyPP && !salesConfirm) {
                if (getIntent().getSerializableExtra(sessionPO) != null) {
                    poinfo.setVisibility(View.VISIBLE);
                    sPO = (mPO) getIntent().getSerializableExtra(sessionPO);
                    String poref = "";
                    if (sPO != null) {
                        sPOPPCopy = sPO;
                        poref = sPO.getPoId();
                        sPO.setPoCustNumberRef(sPO.getPoId());
                        pocustnumber.setText(sPO.getPoId());
                        sPO.setPoId(PoId);
                        // Log.e("isi iscopy", isCopyPP+" ," + sPO.getPoId());
                        isPP = false;
                        typepopp.setEnabled(false);
                        typeporeguler.setEnabled(false);
                        if (isPP) {
                            typepopp.setChecked(true);
                            typepopp.setTextColor(Color.BLUE);
                        } else {
                            typeporeguler.setChecked(true);
                            typeporeguler.setTextColor(Color.BLUE);
                        }
                        CustomerId = sPO.getCustId();
                        if (sPO.getDistBranch() != null)
                            CustAndBranchId = sPO.getDistBranch().getCustomerDistBranchId();
                        BranchId = sPO.getDistBranchId();
                        String cname = sPO.getCustomer().getCustName();
                        int cid = CustomerId;
                        String caddr = sPO.getShipAddress();
                        String dname = "", dgp = "", daddr = "", ccode = "";
                        if (sPO.getDistBranch() != null) {
                            dname = sPO.getDistBranch().getDistBranchName();
                            dgp = sPO.getDistBranch().getPriceGroupName();
                            daddr = sPO.getDistBranch().getDistBranchAddress();
                            ccode = sPO.getDistBranch().getCustCode();
                        }
                        custname.setText(cname.concat("-" + cid).concat(":(" + ccode + ")"));
                        custaddress.setText(caddr);
                        distname.setText(dname.concat(" (" + dgp + ")"));
                        distaddress.setText(daddr);
                        if (TextUtils.isEmpty(dgp)) {
                            distname.setTextColor(Color.RED);
                        } else {
                            distname.setTextColor(Color.BLACK);
                        }
                        CustomerId = cid;
                        changecust.setEnabled(false);
                        //Calendar calss = Calendar.getInstance();
                        //SimpleDateFormat smp2 = new SimpleDateFormat("yyyy/MM/dd");
                        //SimpleDateFormat smp3 = new SimpleDateFormat("yyyyMMddhhmmss");

                        int ids = 1;
                        if (sPO.getPoLines() != null && sPO.getPoLines().size() > 0) {
                            for (mPoLine cu : sPO.getPoLines()) {
                                String idpo = String.valueOf(session.getId()).concat(smp3.format(calss.getTime())).concat(String.valueOf(ids));
                                cu.setPoId(sPO.getPoId());
                                cu.setRecIdTab(idpo);
                                ids++;
                            }
                        }

                        if (mAdapter != null)
                            mAdapter.clearProduct();
                        if (mAdapter != null)
                            mAdapter.setisCopyPP(isCopyPP);
                        if (mAdapter != null)
                            mAdapter.setCustLevel(custLevelId);
                        if (mAdapter != null)
                            mAdapter.setIsPP(isPP);
                        if (mAdapter != null) {
                            mAdapter.changeDiscFaktur2(false);
                            mAdapter.setPoPPRefId(poref);
                            mAdapter.setItemCust(sPO);
                            mAdapter.setItemProduct(sPO, custLevelId);
                        }
                    }
                }
            }
        }


        this.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        changecust.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmCustomer();
            }
        });

        btnCopyPP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                copyFromPP();
            }
        });

        typepopp.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    btnCopyPP.setVisibility(View.INVISIBLE);
                    typePlan = 1;
                    //endperiodegroup.setVisibility(View.VISIBLE);
                    isPP = true;
                    if (mAdapter != null)
                        mAdapter.setIsPP(isPP);

                } else {
                    typePlan = 0;
                    btnCopyPP.setVisibility(View.VISIBLE);
                    //endperiodegroup.setVisibility(View.GONE);
                    isPP = false;
                    if (mAdapter != null)
                        mAdapter.setIsPP(isPP);
                }
            }
        });

        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                // Log.e("selec","tab selected");
                View focus = getCurrentFocus();
                if (focus != null) {
                    hiddenKeyboard(focus);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                View focus = getCurrentFocus();
                if (focus != null) {
                    hiddenKeyboard(focus);
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                View focus = getCurrentFocus();
                if (focus != null) {
                    hiddenKeyboard(focus);
                }
            }
        });

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

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_customer, menu);
        MenuItem items = menu.findItem(R.id.menuAddCst);
        items.setIcon(android.R.drawable.ic_menu_delete);
        MenuItem item = menu.findItem(R.id.menuRefresh);
        item.setIcon(android.R.drawable.ic_menu_save);
        if (salesConfirm) {
            items.setTitle("Delete");
            items.setVisible(true);
            item.setTitle("Confirm");
        } else {
            item.setTitle("Save");
            items.setVisible(false);
        }
        if (reportView) {
            items.setTitle("Cancel PO");
            items.setVisible(true);
            item.setVisible(false);
        }


        return true;
    }


    private void setupTabIcons() {
        Bundle bundle = new Bundle();

        mAdapter = new TabFragmentPoPagerAdapter(getSupportFragmentManager(), bundle, getPoId(), session, poCustinfo, null);
        pager.setAdapter(mAdapter);
        tabs.setTabGravity(View.FOCUS_LEFT);
        tabs.setupWithViewPager(pager);

    }

    //untuk memantau tombol back
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            //getFragmentManager().popBackStack();
            finish();
        } else if (item.getItemId() == R.id.menuRefresh) {//save customer
            //simpan ke database
            //Toast.makeText(this,"tes",Toast.LENGTH_LONG).show();
            saveToDatabase();
            return true;
        } else if (item.getItemId() == R.id.menuAddCst) {
            cancelPO();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    PoController.CallBackGetData callbackpo = new PoController.CallBackGetData() {
        @Override
        public void resultReady(ArrayList<mPO> customers, boolean hasil) {
            if (pDialog != null)
                pDialog.dismiss();
            if (salesConfirm) {
                if (hasil) {
                    Toast.makeText(getApplicationContext(), "PO Confirm Submitted", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "PO Confirm Error On Submitted\n Please Resend Again..", Toast.LENGTH_LONG).show();
                    finish();
                }
            } else {
                if (hasil) {
                    Toast.makeText(getApplicationContext(), "PO Draft Submitted", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "PO Draft Error On Submitted", Toast.LENGTH_LONG).show();
                    finish();
                }
            }

        }
    };
    //callPlanAddTgl

    private void saveToDatabase() {
        View focusView = null;
        boolean cancel = false;
        typepotext.setError(null);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        if (!typeporeguler.isChecked() && !typepopp.isChecked()) {
            Toast.makeText(this, "Type PO Is Required", Toast.LENGTH_SHORT).show();
            typepotext.setError(getString(R.string.error_field_required));
            focusView = typepo;
            focusView.requestFocus();
        } else if (isSellOut && TextUtils.isEmpty(pocustnumber.getEditableText())) {
            Toast.makeText(this, "No Faktur Is Required", Toast.LENGTH_SHORT).show();
            pocustnumber.setError(getString(R.string.error_field_required));
            focusView = pocustnumber;
            focusView.requestFocus();
        } else if (isSellOut && TextUtils.isEmpty(tglFaktur.getEditableText())) {
            Toast.makeText(this, "Tgl Faktur Is Required", Toast.LENGTH_SHORT).show();
            tglFaktur.setError(getString(R.string.error_field_required));
            focusView = tglFaktur;
            focusView.requestFocus();
        } else {

            if (isSellOut && sPO != null) {
                sPO.setSoDate(tglFaktur.getEditableText().toString());
                sPO.setPoDate(tglFaktur.getEditableText().toString());
                sPO.setPoCustNumberRef(pocustnumber.getEditableText().toString());
                sPO.setSellOut(isSellOut);
            }
            if (poinfo.getVisibility() == View.VISIBLE) {
                if (pDialog == null)
                    pDialog = new ProgressDialog(this);
                pDialog.setMessage("Send PO Data To Server ...\n Please Wait...");
                pDialog.show();
                poCustinfo = mAdapter.getItemCust();
                // Log.e("isi ada","ada"+ mAdapter.getDistName());
                if (poCustinfo != null && !poCustinfo.isError()) {
                    // Toast.makeText(this,"distname "+ mAdapter.getDistName(),Toast.LENGTH_SHORT).show();
                    //listnamaerror.setVisibility(View.INVISIBLE);
                    if (BranchId == 0) {
                        // listnamaerror.setVisibility(View.VISIBLE);
                        changecust.setError(getString(R.string.error_field_required));
                        focusView = changecust;
                        cancel = true;
                    }
                    poCustItem = mAdapter.getItemProduct();


                    poCustOther = mAdapter.getItemProductOther();
                    if (poCustItem == null || poCustItem.size() <= 0) {
                        pDialog.dismiss();
                        Toast.makeText(this, "Product Item On List Of Product Is Required", Toast.LENGTH_SHORT).show();
                    } else {
                        if (cancel) {
                            pDialog.dismiss();
                            focusView.requestFocus();
                        } else {
                            if (salesConfirm) {
                                confirmPO();
                            } else {
                                Calendar calss = Calendar.getInstance();
                                SimpleDateFormat smp2 = new SimpleDateFormat("yyyy/MM/dd");
                                Konversi konversi = new Konversi();

                                Date POTanggal = null;
                                POTanggal = konversi.StringToDateTime(tglPO);
                                assert POTanggal != null;
                                if (tglPO.equalsIgnoreCase(smp2.format(calss.getTime()))) {
                                    confirmSign();
                                } else {
                                    pDialog.dismiss();
                                    Toast.makeText(this, "PO Number Not For This Date\nDon't Cheat Me!!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    }
                } else {
                    pDialog.dismiss();
                }
            } else {
                Toast.makeText(this, "Please Set Customer First", Toast.LENGTH_SHORT).show();
            }
        }

    }

    //change tanggal
//change tanggal
    com.kenmeidearu.materialdatetimepicker.date.DatePickerDialog dpd;
    boolean tglkosong;
    Calendar tgldpt = Calendar.getInstance();

    void changeTanggal(String tanggal) {
        String dtStart = tanggal;
        //Log.e("isi start", dtStart);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        if (!TextUtils.isEmpty(dtStart) && !dtStart.equalsIgnoreCase("null")) {
            try {
                Date date = format.parse(dtStart);
                // Log.e("isi start", date.toString());
                tglkosong = false;
                tgldpt.setTime(date);
                tglFaktur.setText(format.format(tgldpt.getTime()));
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                tglkosong = true;
                e.printStackTrace();
            }
        } else {
            tglkosong = true;
        }
        Calendar nows = Calendar.getInstance();
        Calendar now = tgldpt;
        if (tglkosong) {
            now = nows;
        }

        // Calendar.getInstance();
        dpd = com.kenmeidearu.materialdatetimepicker.date.DatePickerDialog.newInstance(
                this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH),
                nows.get(Calendar.HOUR_OF_DAY),
                nows.get(Calendar.MINUTE),
                false
        );
        dpd.dateOnly(true);
        dpd.setMaxDate(nows);
        Calendar mindate = Calendar.getInstance();
        //mindate.add(Calendar.DATE, 1);
        //dpd.setMinDate(mindate);
        dpd.show(getFragmentManager(), "Datepickerdialog");
    }

    @Override
    public void onDateSet(com.kenmeidearu.materialdatetimepicker.date.DatePickerDialog view, int year, int monthOfYear, int dayOfMonth, int hourOfDay, int minute, int second) {
        setTanggal(year, monthOfYear, dayOfMonth, hourOfDay, minute, second);
    }

    void setTanggal(int year, int monthOfYear, int dayOfMonth, int hourOfDay, int minute, int second) {
        String hourString = hourOfDay < 10 ? "0" + hourOfDay : "" + hourOfDay;
        String minuteString = minute < 10 ? "0" + minute : "" + minute;
        String secondString = second < 10 ? "0" + second : "" + second;
        // String date = dayOfMonth + "/" + (++monthOfYear) + "/" + year + " " + hourString + ":" + minuteString + ":" + secondString;
        String date = year + "-" + (++monthOfYear) + "-" + dayOfMonth;
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            // cal.setTime();
            // Log.e("isi tanggal","xx"+(-monthOfYear)+","+monthOfYear+","+(+monthOfYear));
            cal.set(year, monthOfYear - 1, dayOfMonth, hourOfDay, minute, second);
        } catch (Exception ex) {

        }
        tgldpt = cal;
        tglFaktur.setText(format.format(tgldpt.getTime()));
    }

    //end change tanggal
    private void copyFromPP() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        Intent inten = new Intent(this, PoNewAddNumberReffActivity.class);
        inten.putExtra(PoNewAddNumberReffActivity.sessionUser, session);
        inten.putExtra(PoNewAddNumberReffActivity.sessionIsCopyPP, true);
        startActivityForResult(inten, 789);
    }

    public void confirmCustomer() {
        //imgEdit = true;

        Context context = PoNewActivity.this;
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.preview_change_customer, null);
        final TextInputEditText custId = (TextInputEditText) dialogView.findViewById(R.id.custid);
        final Button btnDel = (Button) dialogView.findViewById(R.id.btnDel);
        final Button btnChange = (Button) dialogView.findViewById(R.id.btnChange);
        final SearchableSpinner listCustomer = (SearchableSpinner) dialogView.findViewById(R.id.listCustomer);
        final TextInputEditText custDistBranchId = (TextInputEditText) dialogView.findViewById(R.id.custDistBranchId);
        final SearchableSpinner listCustDistributor = (SearchableSpinner) dialogView.findViewById(R.id.listCustBranch);
        final ArrayList<mListString> listStringsCustomer = new ArrayList<>();
        final ArrayList<mListString> listStringsCustomerDistributor = new ArrayList<>();
        listStringsCustomer.add(new mListString(0, "Select Customer", "Cust"));
        final ArrayList<mCustomer> lvl = customerController.getAllCustomer(SalesId, "1");
        for (mCustomer lv : lvl) {
            mListString isi = new mListString(lv.getCustId(), lv.getCustName(), lv.getAddress());
            isi.setNilai5(String.valueOf(lv.getCustLevelId()));
            listStringsCustomer.add(isi);
        }
        listCustomer.setAdapter(listStringsCustomer, 3, 3);
        listStringsCustomerDistributor.add(new mListString(0, "Select Distributor", "Dist", 0));
        listCustDistributor.setAdapter(listStringsCustomerDistributor, 4, 4);


        AlertDialog.Builder ad = new AlertDialog.Builder(context);
        //ad.setTitle(title);
        //ad.setMessage(message);
        ad.setView(dialogView);
        final AlertDialog dialog = ad.create();

        //select default cust
        int i = 0;
        for (mListString t : listStringsCustomer) {
            if (t.get_id() == CustomerId) {
                listCustomer.setSelection(i);
                break;
            }
            i++;
        }

        //change cust
        listCustomer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Log.e("tes posisi", id + " ," + position + "," + listStringsLevel.get(position).get_id());
                custId.setText(String.valueOf(listStringsCustomer.get(position).get_id()));
                if (position > 0) {

                    listStringsCustomerDistributor.clear();
                    listStringsCustomerDistributor.add(new mListString(0, "Select Distributor", "Dist", 0));
                    mCustomer cust = lvl.get(position - 1);
                    int total = 0;
                    for (mCustomerAndDistBranch lv : cust.getCustomerAndBranch()) {
                        total++;
                        mListString isi = new mListString(lv.getDistBranchId(), lv.getDistBranchName() != null ? lv.getDistBranchName() : "", lv.getDistBranchAddress() != null ? lv.getDistBranchAddress() : "",
                                lv.getPriceGroupId(), lv.getCustCode() != null ? lv.getCustCode() : "");
                        isi.setNilai5(lv.getDistBranchPic());
                        isi.setNilai6(String.valueOf(lv.getCustomerDistBranchId()));

                        listStringsCustomerDistributor.add(isi);
                        //Log.e("dist",isi.getNilai5()+","+lv.getDistPic());
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
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
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
                    poinfo.setVisibility(View.VISIBLE);
                    String cname = listStringsCustomer.get(listCustomer.getSelectedItemPosition()).getNilai1();
                    int cid = listStringsCustomer.get(listCustomer.getSelectedItemPosition()).get_id();
                    String caddr = listStringsCustomer.get(listCustomer.getSelectedItemPosition()).getNilai2();
                    String dname = listStringsCustomerDistributor.get(listCustDistributor.getSelectedItemPosition()).getNilai1();
                    String dgp = listStringsCustomerDistributor.get(listCustDistributor.getSelectedItemPosition()).getNilai3();
                    String daddr = listStringsCustomerDistributor.get(listCustDistributor.getSelectedItemPosition()).getNilai2();
                    String ccode = listStringsCustomerDistributor.get(listCustDistributor.getSelectedItemPosition()).getNilai4();
                    int custlevel = 0;
                    if (listStringsCustomer.get(listCustomer.getSelectedItemPosition()).getNilai5() != null)
                        custlevel = Integer.parseInt(listStringsCustomer.get(listCustomer.getSelectedItemPosition()).getNilai5());
                    custname.setText(cname.concat("-" + cid).concat(":(" + ccode + ")"));
                    custaddress.setText(caddr);
                    distname.setText(dname.concat(" (" + dgp + ")"));
                    distaddress.setText(daddr);
                    if (TextUtils.isEmpty(dgp)) {
                        distname.setTextColor(Color.RED);
                    } else {
                        distname.setTextColor(Color.BLACK);
                    }
                    CustomerId = cid;
                    custLevelId = custlevel;
                    CustAndBranchId = Integer.parseInt(listStringsCustomerDistributor.get(listCustDistributor.getSelectedItemPosition()).getNilai6());
                    BranchId = listStringsCustomerDistributor.get(listCustDistributor.getSelectedItemPosition()).get_id();
                    PicDistributor = listStringsCustomerDistributor.get(listCustDistributor.getSelectedItemPosition()).getNilai5();
                    // Log.e("pic dist",PicDistributor);
                    if (mAdapter != null)
                        mAdapter.setDetailInfo(PicDistributor, caddr);
                    //addProduct.setEnabled(true);
                    if (mAdapter != null)
                        mAdapter.setIsSellOut(isSellOut);
                    if (mAdapter != null)
                        mAdapter.setCustLevel(custLevelId);
                    if (mAdapter != null)
                        mAdapter.clearProduct();
                    if (mAdapter != null)
                        mAdapter.setIsPP(isPP);
                    dialog.dismiss();
                }
            }
        });

        //
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    private void cancelPO() {
        final AlertDialog alertDialog = new AlertDialog.Builder(PoNewActivity.this).create();
        alertDialog.setTitle("");
        alertDialog.setMessage("Yakin Ingin Membatalkan PO?\nProses ini tidak dapat dibalikan lagi!");
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Delete",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if (sPO.getPoStatusId() < 3) {
                            Calendar calss = Calendar.getInstance();
                            SimpleDateFormat smp4 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                            SimpleDateFormat smp3 = new SimpleDateFormat("yyyy-MM-dd");
                            String poNumber = ponumber.getEditableText().toString();
                            int postatus = 7;
                            String postatusname = "Cancel";
                            //add cash discount
                            poCustinfo = mAdapter.getItemCust();
                            mPO po = new mPO(poNumber, pocustnumber.getEditableText().toString(), sPO.getPoDate(),
                                    BranchId, getCustomerId(), sPO.getSalesmanId(), sPO.getCallPlanId(), sPO.getPoById(), sPO.getPoViaId(), poCustinfo.getShipmentdate(),
                                    poCustinfo.getEndperiode(), poCustinfo.getMechanisme(), poCustinfo.getShipmentaddress(), mAdapter.getDisFaktur1(), mAdapter.getDisFaktur2(), mAdapter.getDisFakturCash(),
                                    sPO.isPP(), poCustinfo.getPicdist(), sPO.getPicCust(), poCustinfo.getNotes(), sPO.getSignature(), postatus, postatusname,
                                    sPO.getCreatedDate(), sPO.getCreatedBy(), 1, sPO.getSoNo(), sPO.getSoDate(), sPO.getDoNo(), sPO.getDoDate(), smp4.format(calss.getTime()),
                                    smp3.format(calss.getTime()), String.valueOf(SalesId));
                            po.setPoLines(poCustItem);
                            po.setPoLineOthers(poCustOther);
                            if (pDialog == null)
                                pDialog = new ProgressDialog(PoNewActivity.this);
                            pDialog.setMessage("Send Cancel PO Data To Server ...\n Please Wait...");
                            pDialog.show();
                            poController.confirmPoToServer(po);
                            dialog.dismiss();
                        } else {
                            dialog.dismiss();
                            final AlertDialog alertDialog2 = new AlertDialog.Builder(PoNewActivity.this).create();
                            alertDialog2.setTitle("");
                            alertDialog2.setMessage("Barang Sudah Disetujui dan Diterima Oleh Customer atau PO Sudah DiCancel, PO Tidak Bisa Dibatalkan Lagi!!");
                            alertDialog2.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    });
                            alertDialog2.show();
                        }

                    }
                });
        alertDialog.show();
    }

    private void confirmPO() {
        AlertDialog alertDialog = new AlertDialog.Builder(PoNewActivity.this).create();
        alertDialog.setTitle("");
        alertDialog.setMessage("Yakin Ingin Pengajukan PO?\nApakah semua data dicek dan valid!");
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Confirm",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Calendar calss = Calendar.getInstance();
                        SimpleDateFormat smp4 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                        SimpleDateFormat smp3 = new SimpleDateFormat("yyyy-MM-dd");
                        String poNumber = ponumber.getEditableText().toString();
                        int postatus = 3;
                        String postatusname = "Approve";
                        if (isPP) {
                            postatus = 1;
                            postatusname = "In Review";
                        }
                        //add cash discount
                        mPO po = new mPO(poNumber, pocustnumber.getEditableText().toString(), sPO.getPoDate(),
                                BranchId, getCustomerId(), SalesId, sPO.getCallPlanId(), sPO.getPoById(), sPO.getPoViaId(), poCustinfo.getShipmentdate(),
                                poCustinfo.getEndperiode(), poCustinfo.getMechanisme(), poCustinfo.getShipmentaddress(), mAdapter.getDisFaktur1(), mAdapter.getDisFaktur2(), mAdapter.getDisFakturCash(),
                                isPP, poCustinfo.getPicdist(), sPO.getPicCust(), poCustinfo.getNotes(), sPO.getSignature(), postatus, postatusname,
                                sPO.getCreatedDate(), sPO.getCreatedBy(), 1, sPO.getSoNo(), sPO.getSoDate(), sPO.getDoNo(), sPO.getDoDate(), smp4.format(calss.getTime()),
                                smp3.format(calss.getTime()), String.valueOf(SalesId));
                        po.setSellOut(isSellOut);
                        po.setPoLines(poCustItem);
                        po.setPoLineOthers(poCustOther);
                        poController.confirmPoToServer(po);
                        dialog.dismiss();

                    }
                });
        alertDialog.show();
    }

    public void confirmSign() {

        Context context = PoNewActivity.this;
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.preview_signature, null);
        final Button btnCancel = (Button) dialogView.findViewById(R.id.btnCancel);
        final Button btnProses = (Button) dialogView.findViewById(R.id.btnProses);
        final Button btnClear = (Button) dialogView.findViewById(R.id.btnClear);
        final SignaturePad mSignaturePad = (SignaturePad) dialogView.findViewById(R.id.signature_pad);
        final TextInputEditText signaturename = (TextInputEditText) dialogView.findViewById(R.id.signaturename);

        AlertDialog.Builder ad = new AlertDialog.Builder(context);
        //ad.setTitle(title);
        //ad.setMessage(message);
        ad.setView(dialogView);
        final AlertDialog dialog = ad.create();

        //change cust
        mSignaturePad.setOnSignedListener(new SignaturePad.OnSignedListener() {
            @Override
            public void onStartSigning() {

            }

            @Override
            public void onSigned() {
                btnProses.setEnabled(true);
                btnProses.setBackgroundResource(android.R.color.holo_green_light);
                btnClear.setEnabled(true);
                btnClear.setBackgroundResource(android.R.color.holo_blue_dark);
                btnCancel.setEnabled(true);

            }

            @Override
            public void onClear() {
                btnProses.setEnabled(false);
                btnProses.setBackground(null);
                btnClear.setEnabled(false);
                btnClear.setBackground(null);
                btnCancel.setEnabled(true);

            }
        });


        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pDialog.dismiss();
                dialog.dismiss();
            }
        });
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSignaturePad.clear();
            }
        });
        btnProses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(signaturename.getEditableText()))
                    PicCustomer = signaturename.getEditableText().toString();
                signatureBitmap = mSignaturePad.getSignatureBitmap();
                Calendar calss = Calendar.getInstance();
                SimpleDateFormat smp2 = new SimpleDateFormat("yyMMddhhmmss");
                SimpleDateFormat smp3 = new SimpleDateFormat("yyyy-MM-dd");
                SimpleDateFormat smp4 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                String poNumberSignature = String.valueOf(session.getId()).concat(String.valueOf(smp2.format(calss.getTime()))).concat(String.valueOf(1));
                String poNumber = ponumber.getEditableText().toString();
                if (addJpgSignatureToGallery(signatureBitmap, poNumberSignature, signaturename.getEditableText().toString(), poNumber)) {
                    mPO po = new mPO(poNumber, pocustnumber.getEditableText().toString(), smp4.format(calss.getTime()),
                            BranchId, getCustomerId(), SalesId, CallPlanId, "Salesman", poCustinfo.getPovia(), poCustinfo.getShipmentdate(),
                            poCustinfo.getEndperiode(), poCustinfo.getMechanisme(), poCustinfo.getShipmentaddress(), mAdapter.getDisFaktur1(), mAdapter.getDisFaktur2(), mAdapter.getDisFakturCash(),
                            isPP, poCustinfo.getPicdist(), PicCustomer, poCustinfo.getNotes(), photo.getName(), 0, poCustinfo.getPostatus(),
                            smp4.format(calss.getTime()), String.valueOf(SalesId), 1, "", "1900-01-01", "", "1900-01-01", "1900-01-01",
                            smp3.format(calss.getTime()), String.valueOf(SalesId));
                    po.setSellOut(isSellOut);
                    po.setPoLines(poCustItem);
                    Gson gson = new Gson();
                    for (mPoLine pos : poCustItem) {
                        Log.e("string item", gson.toJson(pos));
                    }

                    po.setPoLineOthers(poCustOther);
                    poController.updatePoToServer(po);
                } else {
                    Toast.makeText(getApplication(), "Unable to store the signature", Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
            }
        });

        //
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    public File getAlbumStorageDir(String albumName) {
        // Get the directory for the user's public pictures directory.
        // File file = new File(Environment.getExternalStoragePublicDirectory(
        //        Environment.DIRECTORY_PICTURES), albumName);
        File file = new File(Environment.getExternalStorageDirectory().toString() + '/' + albumName);
        if (!file.mkdirs()) {
            Log.e("SignaturePad", "Directory not created");
        }
        return file;
    }

    public boolean addJpgSignatureToGallery(Bitmap signature, String namaTTD, String pengirim, String idpo) {
        boolean result = false;
        try {
            photo = new File(getAlbumStorageDir("Geisa"), namaTTD + ".jpg");// String.format("Signature_%d.jpg", System.currentTimeMillis()));
            if (photo.exists()) {
                photo.delete();
            }
            saveBitmapToJPG(signature, photo, pengirim);
            scanMediaFile(photo);
            //upload picture to server
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat smp2 = new SimpleDateFormat("yyMMddhhmmss");
            SimpleDateFormat smp4 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Intent batteryIntent = getApplicationContext().registerReceiver(
                    null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
            int levelBattrey = batteryIntent.getIntExtra("level", -1);
            String TrackingPictureId = String.valueOf(SalesId).concat(String.valueOf(smp2.format(cal.getTime()))).concat(String.valueOf(1));
            mTrackingPicture trackingPicture = new mTrackingPicture(TrackingPictureId, idpo, photo.getPath(),
                    String.valueOf(levelBattrey), "PO", smp4.format(cal.getTime()), String.valueOf(SalesId), 1);
            trackingController.insertTrackingPictureServer(trackingPicture);
            result = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }


    public void saveBitmapToJPG(Bitmap bitmap, File photo, String pengirim) throws IOException {
        Bitmap newBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(newBitmap);
        // Bitmap newBitmap = Bitmap.createBitmap(bitmap.getWidth(),bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        // Canvas canvas = new Canvas(Bitmap.createScaledBitmap(newBitmap,(int)(newBitmap.getWidth()*0.7), (int)(newBitmap.getHeight()*0.7), true));
        //tambahan ipang
        Paint paint = new Paint();
        paint.setColor(Color.BLUE);
        // paint.setAlpha(alpha);
        paint.setTextSize(22);
        paint.setAntiAlias(true);
        //paint.setUnderlineText(underline);
        //end tambahan
        canvas.drawColor(Color.TRANSPARENT);
        canvas.drawBitmap(bitmap, 0, 0, null);
        canvas.drawText(pengirim, bitmap.getWidth() / 2, bitmap.getHeight() - 15, paint);
        OutputStream stream = new FileOutputStream(photo);
        newBitmap.compress(Bitmap.CompressFormat.JPEG, 80, stream);
        stream.close();
    }

    private void scanMediaFile(File photo) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri contentUri = Uri.fromFile(photo);
        mediaScanIntent.setData(contentUri);
        getApplicationContext().sendBroadcast(mediaScanIntent);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == 789) {
            if (resultCode == RESULT_OK) {
                // Log.e("masuk sini result",requestCode+","+resultCode+","+isCopyPP+","+salesConfirm);
                Calendar calss = Calendar.getInstance();
                SimpleDateFormat smp3 = new SimpleDateFormat("hhmmss");
                if (data.getSerializableExtra(sessionSalesConfirm) != null) {
                    salesConfirm = (boolean) data.getSerializableExtra(sessionSalesConfirm);
                }
                if (data.getSerializableExtra(sessionIsCopyPP) != null) {
                    isCopyPP = (boolean) data.getSerializableExtra(sessionIsCopyPP);
                    Log.e("masuk sini hasil ", isCopyPP + "," + salesConfirm);
                    //  Log.e("isi iscopy", isCopyPP+" ," + salesConfirm);
                    if (isCopyPP && !salesConfirm) {
                        if (data.getSerializableExtra(sessionPO) != null) {
                            poinfo.setVisibility(View.VISIBLE);
                            sPO = (mPO) data.getSerializableExtra(sessionPO);
                            String poref = "";
                            if (sPO != null) {
                                sPOPPCopy = sPO;
                                poref = sPO.getPoId();
                                sPO.setPoCustNumberRef(sPO.getPoId());
                                pocustnumber.setText(sPO.getPoId());
                                sPO.setPoId(PoId);
                                // Log.e("isi iscopy", isCopyPP+" ," + sPO.getPoId());
                                isPP = false;
                                typepopp.setEnabled(false);
                                typeporeguler.setEnabled(false);
                                if (isPP) {
                                    typepopp.setChecked(true);
                                    typepopp.setTextColor(Color.BLUE);
                                } else {
                                    typeporeguler.setChecked(true);
                                    typeporeguler.setTextColor(Color.BLUE);
                                }
                                CustomerId = sPO.getCustId();
                                if (sPO.getDistBranch() != null)
                                    CustAndBranchId = sPO.getDistBranch().getCustomerDistBranchId();
                                BranchId = sPO.getDistBranchId();
                                String cname = sPO.getCustomer().getCustName();
                                int cid = CustomerId;
                                String caddr = sPO.getShipAddress();
                                String dname = "", dgp = "", daddr = "", ccode = "";
                                if (sPO.getDistBranch() != null) {
                                    dname = sPO.getDistBranch().getDistBranchName();
                                    dgp = sPO.getDistBranch().getPriceGroupName();
                                    daddr = sPO.getDistBranch().getDistBranchAddress();
                                    ccode = sPO.getDistBranch().getCustCode();
                                }
                                custname.setText(cname.concat("-" + cid).concat(":(" + ccode + ")"));
                                custaddress.setText(caddr);
                                distname.setText(dname.concat(" (" + dgp + ")"));
                                distaddress.setText(daddr);
                                if (TextUtils.isEmpty(dgp)) {
                                    distname.setTextColor(Color.RED);
                                } else {
                                    distname.setTextColor(Color.BLACK);
                                }
                                CustomerId = cid;
                                changecust.setEnabled(false);
                                //Calendar calss = Calendar.getInstance();
                                //SimpleDateFormat smp2 = new SimpleDateFormat("yyyy/MM/dd");
                                //SimpleDateFormat smp3 = new SimpleDateFormat("yyyyMMddhhmmss");

                                int ids = 1;
                                if (sPO.getPoLines() != null && sPO.getPoLines().size() > 0) {
                                    for (mPoLine cu : sPO.getPoLines()) {
                                        String idpo = String.valueOf(session.getId()).concat(smp3.format(calss.getTime())).concat(String.valueOf(ids));
                                        cu.setPoId(sPO.getPoId());
                                        cu.setRecIdTab(idpo);
                                        ids++;
                                    }
                                }

                                if (mAdapter != null)
                                    mAdapter.clearProduct();
                                if (mAdapter != null)
                                    mAdapter.setisCopyPP(isCopyPP);
                                if (mAdapter != null)
                                    mAdapter.setIsPP(isPP);
                                if (mAdapter != null) {
                                    mAdapter.changeDiscFaktur2(false);
                                    mAdapter.setPoPPRefId(poref);
                                    mAdapter.setItemCust(sPO);
                                    mAdapter.setItemProduct(sPO, custLevelId);
                                }
                            }
                        }
                    }
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    public int getCustAndBranchId() {
        return CustAndBranchId;
    }

    public String getCallPlanId() {
        return CallPlanId;
    }

    public int getCustomerId() {
        return CustomerId;
    }

    public mSession getSession() {
        return session;
    }

    public String getPoId() {
        return PoId;
    }

    public mPO GetPORef() {
        return sPOPPCopy;
    }


}
