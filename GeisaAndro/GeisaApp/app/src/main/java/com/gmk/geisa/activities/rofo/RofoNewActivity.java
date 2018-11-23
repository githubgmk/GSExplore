package com.gmk.geisa.activities.rofo;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gmk.geisa.R;
import com.gmk.geisa.activities.po.PoNewAddProductActivity;
import com.gmk.geisa.adapter.RofoListAdapter;
import com.gmk.geisa.controller.CustomerController;
import com.gmk.geisa.controller.ProductController;
import com.gmk.geisa.controller.RofoController;
import com.gmk.geisa.fragment.tab.TabFragmentPoPagerAdapter;
import com.gmk.geisa.model.mCustomer;
import com.gmk.geisa.model.mCustomerAndDistBranch;
import com.gmk.geisa.model.mProductPriceDiskon;
import com.gmk.geisa.model.mRofo;
import com.gmk.geisa.model.mSession;
import com.kenmeidearu.searchablespinnerlibrary.SearchableSpinner;
import com.kenmeidearu.searchablespinnerlibrary.mListString;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class RofoNewActivity extends AppCompatActivity implements com.kenmeidearu.materialdatetimepicker.date.DatePickerDialog.OnDateSetListener {
    public final static String sessionUser = "sessionUser";
    public final static String sessionProduct = "sessionProduct";
    static final int SELECT_CUSTOMER = 111;
    public  final static String sessionBulan="sessionBulan";
    public  final static String sessionBulanName="sessionBulanName";
    public  final static String sessionTahun="sessionTahun";


    private ArrayList<mRofo> poLines = new ArrayList<>();
    static final ArrayList<mProductPriceDiskon> products = new ArrayList<>();
    static final ArrayList<mProductPriceDiskon> selectedProducts = new ArrayList<>();
    private mSession session;
    private int typePlan, totalBefore;
    CustomerController customerController;
    ProductController productController;
    RofoController rofoController;

    private ProgressDialog pDialog;

    LinearLayout poinfo;
    Button changecust,addProduct;
    TextInputEditText monthName, pocustnumber;
    TextView custname, custaddress, distname, distaddress, typepotext,ttlItem,ttlRofo,totalproduct;
    RecyclerView recyclerView;
    private RofoListAdapter adapter1;

    String BulanSelect="", PicDistributor = "";
    Calendar bulanRofo;
    int CustAndBranchId, BranchId, CustomerId, SalesId,tahun=0,bulan=0;

    private RecyclerView.LayoutManager mLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rofo_new);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Calendar calen=Calendar.getInstance();
        setTitle("New Rofo->"+calen.get(calen.YEAR));
        poinfo = (LinearLayout) findViewById(R.id.poinfo);
        custname = (TextView) findViewById(R.id.custname);
        distname = (TextView) findViewById(R.id.distname);
        changecust = (Button) findViewById(R.id.changecust);
        addProduct=(Button) findViewById(R.id.addProduct);
        distaddress = (TextView) findViewById(R.id.month);
        custaddress = (TextView) findViewById(R.id.year);
        ttlItem=(TextView) findViewById(R.id.ttlItem);
        ttlRofo=(TextView) findViewById(R.id.ttlRofo);
        totalproduct=(TextView) findViewById(R.id.totalproduct);

        customerController = CustomerController.getInstance(this);
        productController = ProductController.getInstance(this);
        rofoController = RofoController.getInstance(this);

        changecust.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmCustomer();
            }
        });
        monthName = (TextInputEditText) findViewById(R.id.monthName);


        if (getIntent().getSerializableExtra(sessionUser) != null) {
            session = (mSession) getIntent().getSerializableExtra(sessionUser);
            SalesId = session.getId();
        }
        if (getIntent().getSerializableExtra(sessionBulan) != null) {
            bulan = (int) getIntent().getSerializableExtra(sessionBulan);
        }
        if (getIntent().getSerializableExtra(sessionTahun) != null) {
            tahun = (int) getIntent().getSerializableExtra(sessionTahun);
            setTitle("New Rofo->"+tahun);
        }
        if (getIntent().getSerializableExtra(sessionBulanName) != null) {
            BulanSelect = (String) getIntent().getSerializableExtra(sessionBulanName);
            monthName.setText(BulanSelect);
            monthName.setEnabled(false);
            if(bulan!=0 && tahun!=0) {
                Calendar cal=Calendar.getInstance();

                //Log.e("isi start", dtStart);
                SimpleDateFormat form = new SimpleDateFormat("dd");
                String dtStart = tahun+"-"+bulan+"-"+ form.format(cal.getTime())+"";
                SimpleDateFormat format = new SimpleDateFormat("yyyy-M-dd");
                SimpleDateFormat smp = new SimpleDateFormat("MMMM");
                if (!TextUtils.isEmpty(dtStart) && !dtStart.equalsIgnoreCase("null")) {
                    try {
                        Date date = format.parse(dtStart);
                         Log.e("isi tgl", date.toString()+","+dtStart);
                        tglkosong = false;
                        tgldpt.setTime(date);
                        BulanSelect = format.format(tgldpt.getTime());
                        monthName.setText(smp.format(tgldpt.getTime()));

                    } catch (ParseException e) {
                        // TODO Auto-generated catch block
                        tglkosong = true;
                        e.printStackTrace();
                    }
                } else {
                    tglkosong = true;
                }
            }
        }

       // Log.e("isi sessi",session.getNama()+","+session.getId());

        this.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        monthName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (typePlan == 0)
                    changeTanggal(BulanSelect);
            }
        });
        monthName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    if (typePlan == 0 && bulan==0)
                        changeTanggal(BulanSelect);
                }
            }
        });
        addProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                Intent inten = new Intent(RofoNewActivity.this, PoNewAddProductActivity.class);
                inten.putExtra(PoNewAddProductActivity.sessionUser, session);
                ArrayList<mProductPriceDiskon> data = new ArrayList<mProductPriceDiskon>();
                inten.putExtra(PoNewAddProductActivity.sessionCustDistBranch, CustAndBranchId);
                for (mRofo pi : poLines) {
                    if (!pi.isSelected()) {
                        for (mProductPriceDiskon pu : selectedProducts) {
                            //dirubah untuk data yang sudah dipilih
                            // Log.e("isi rec",pi.getProductName()+","+pi.getPriceId()+","+pu.getRecIdTab()+","+pu.getProductName());
                            if (pi.getPriceId() == pu.getRecId()) { //ini harus dicek lagi
                           // if (pi.getProductId()    == pu.getProductId()) {
                                //pu.setSelected(true);
                                data.add(pu);
                                break;
                            }
                        }
                    }
                }
                inten.putExtra(PoNewAddProductActivity.sessionSelectedProduct, data);
                startActivityForResult(inten, 100);
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
        getMenuInflater().inflate(R.menu.menu_submit, menu);
        MenuItem item = menu.findItem(R.id.menuSubmit);
        item.setIcon(android.R.drawable.ic_menu_save);
        item.setTitle("Save");
        return true;
    }


    //untuk memantau tombol back
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            //getFragmentManager().popBackStack();
            finish();
        } else if (item.getItemId() == R.id.menuSubmit) {//save customer
            //simpan ke database
            saveToDatabase();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == 100) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                if (null != data.getSerializableExtra(sessionProduct)) {
                    ArrayList<mRofo> line = new ArrayList<>();
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
                            mRofo rofoLine = null;
                            boolean ada = false;
                            selectedProducts.add(cust);
                            for (mRofo ln : line) {
                                if (ln.getPriceId() == cu.getRecId()) {
                                    rofoLine = ln;
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
                                SimpleDateFormat smpbln=new SimpleDateFormat("M");
                               // Log.e("tgl dpt",tgldpt.toString()+","+tgldpt.get(tgldpt.MONTH));
                                rofoLine = new mRofo(String.valueOf(session.getId()).concat(smp3.format(calss.getTime())).concat(String.valueOf(ids)),
                                        tgldpt.get(tgldpt.YEAR), Integer.valueOf(smpbln.format(tgldpt.getTime())),SalesId,CustomerId,BranchId,
                                        cust.getProductId(), cust.getProductName(), cust.getProductCode(), 1, cust.getPrice(), cust.getUnitId(),
                                        cust.getRecId(),0, "Draft", smp2.format(calss.getTime()),String.valueOf(SalesId),smp2.format(calss.getTime()),String.valueOf(SalesId), 1);
                            }
                            poLines.add(rofoLine);
                            ids++;
                        }
                    }
                }
                setupRecyclerView();
                totalBefore = selectedProducts.size();
               // ttlItem.setText(String.valueOf(totalBefore));
                totalproduct.setText(String.valueOf(totalBefore));
            }
        }
    }
    private  void cekListRofo(String custId,String disbranchId,String branchId){
        //besok cek lagi untuk distbranchnya didatabase
        SimpleDateFormat smpbln=new SimpleDateFormat("M");
        //Log.e("bulan rofo",tgldpt.get(tgldpt.MONTH)+"x"+ Integer.valueOf(smpbln.format(tgldpt.getTime())));
        poLines=rofoController.getRofoBySalesIdYearMonthCust(String.valueOf(SalesId),tgldpt.get(tgldpt.YEAR),Integer.valueOf(smpbln.format(tgldpt.getTime())),custId,branchId);
        ArrayList<mProductPriceDiskon> produk =productController.getAllProductPriceDiskon(disbranchId);
        for (mProductPriceDiskon cu:produk) {
            boolean ada=false;
            for (mRofo ln : poLines) {
               // Log.e("isi poline",ln.getProductCode()+","+poLines.size());
                //if (ln.getPriceId() == cu.getRecId()) {
                if (ln.getPriceId() == cu.getRecId()) {
                    ada = true;
                    break;
                }
            }
            if(ada){
                //Log.e("isi product",cu.getProductName()+","+cu.getProductId());
                selectedProducts.add(cu);
            }
        }

        //Log.e("isi line","isi"+poLines.size()+","+SalesId+","+tgldpt.get(tgldpt.YEAR)+","+tgldpt.get(tgldpt.MONTH)+","+custId+","+disbranchId);
        setupRecyclerView();
        totalBefore = selectedProducts.size();
        // ttlItem.setText(String.valueOf(totalBefore));
        totalproduct.setText(String.valueOf(totalBefore));
    }
    private void setupRecyclerView() {
        recyclerView = (RecyclerView) findViewById(R.id.lvItems);
        recyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        adapter1 = new RofoListAdapter(poLines, this,true, ttlRofo, ttlItem);
        recyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this)
                .colorResId(R.color.colorPrimaryDark)
                .size(2)
                .build());
        recyclerView.setAdapter(adapter1);
        hitungHarga(poLines);
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

    private void saveToDatabase() {
        View focusView = null;
        boolean cancel = false;
        monthName.setError(null);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        if (TextUtils.isEmpty(monthName.getEditableText().toString())) {
            monthName.setError(getString(R.string.error_field_required));
            focusView = monthName;
            focusView.requestFocus();
        } else {

            if (poinfo.getVisibility() == View.VISIBLE) {
                if (pDialog == null)
                    pDialog = new ProgressDialog(this);
                pDialog.setMessage("Send Rofo Data To Server ...\n Please Wait...");
                pDialog.show();
                // Log.e("isi ada","ada"+ mAdapter.getDistName());
                if (adapter1 != null &&  adapter1.getItemCount()>0) {
                    // Toast.makeText(this,"distname "+ mAdapter.getDistName(),Toast.LENGTH_SHORT).show();
                    //listnamaerror.setVisibility(View.INVISIBLE);
                    if (BranchId == 0) {
                        // listnamaerror.setVisibility(View.VISIBLE);
                        changecust.setError(getString(R.string.error_field_required));
                        focusView = changecust;
                        cancel = true;
                    }

                    if (cancel) {
                        pDialog.dismiss();
                        focusView.requestFocus();
                    } else {
                        ArrayList<mRofo>rofo=new ArrayList<>();
                        for(mRofo cu:adapter1.getAllItem()){
                            if(!cu.isSelected()) {
                                cu.setStatusSend(1);
                                rofo.add(cu);
                            }
                        }
                        Intent returnIntent = new Intent();
                        setResult(Activity.RESULT_OK, returnIntent);
                        //Log.e("isi rofo",rofo.size()+","+adapter1.getAllItem().size());
                        rofoController.updateRofoToServer(rofo, String.valueOf(SalesId));

                        finish();
                    }

                } else {
                    Toast.makeText(this, "Please Set Item First", Toast.LENGTH_SHORT).show();
                    pDialog.dismiss();
                }
            } else {
                Toast.makeText(this, "Please Set Customer First", Toast.LENGTH_SHORT).show();
            }
        }

    }


    public void confirmCustomer() {
        //imgEdit = true;

        Context context = RofoNewActivity.this;
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
        listStringsCustomer.add(new mListString(0, "Select Customer"));
        final ArrayList<mCustomer> lvl = customerController.getAllCustomer(SalesId, "1");
        for (mCustomer lv : lvl) {
            mListString isi = new mListString(lv.getCustId(), lv.getCustName(), lv.getAddress());
            listStringsCustomer.add(isi);
        }
        listCustomer.setAdapter(listStringsCustomer, 3, 3);
        listStringsCustomerDistributor.add(new mListString(0, "Select Distributor","Dist",0));
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
                    listStringsCustomerDistributor.add(new mListString(0, "Select Distributor","Dist",0));
                    mCustomer cust = lvl.get(position - 1);
                    int total = 0;
                    for (mCustomerAndDistBranch lv : cust.getCustomerAndBranch()) {
                        total++;
                       /* mListString isi = new mListString(lv.getCustomerDistBranchId(), lv.getDistBranchName(), lv.getDistBranchAddress(), lv.getPriceGroupId(), lv.getCustCode());
                        isi.setNilai5(lv.getDistBranchPic());*/
                        mListString isi = new mListString(lv.getDistBranchId(), lv.getDistBranchName()!=null? lv.getDistBranchName():"", lv.getDistBranchAddress()!=null?lv.getDistBranchAddress():"",
                                lv.getPriceGroupId(), lv.getCustCode()!=null?lv.getCustCode():"");
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
                    CustomerId = cid;
                    CustAndBranchId = Integer.parseInt(listStringsCustomerDistributor.get(listCustDistributor.getSelectedItemPosition()).getNilai6());
                    BranchId = listStringsCustomerDistributor.get(listCustDistributor.getSelectedItemPosition()).get_id();
                    PicDistributor = listStringsCustomerDistributor.get(listCustDistributor.getSelectedItemPosition()).getNilai5();
                    //Log.e("dist id dist",CustAndBranchId+" ");
                    dialog.dismiss();
                    cekListRofo(String.valueOf(CustomerId),String.valueOf(CustAndBranchId),String.valueOf(BranchId));
                }
            }
        });

        //
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }
//tgl
//change tanggal
com.kenmeidearu.materialdatetimepicker.date.DatePickerDialog dpd;
    boolean tglkosong;
    Calendar tgldpt = Calendar.getInstance();

    void changeTanggal(String tanggal) {
        String dtStart = tanggal;
        //Log.e("isi start", dtStart);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat smp = new SimpleDateFormat("MMMM");
        if (!TextUtils.isEmpty(dtStart) && !dtStart.equalsIgnoreCase("null")) {
            try {
                Date date = format.parse(dtStart);
                // Log.e("isi start", date.toString());
                tglkosong = false;
                tgldpt.setTime(date);
                BulanSelect=format.format(tgldpt.getTime());
                monthName.setText(smp.format(tgldpt.getTime()));

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
        dpd.showMonthPickerFirst(true);
        dpd.monthYearOnly(true);
        dpd.dateOnly(true);

        //dpd.setMaxDate(nows);
        Calendar mindate = Calendar.getInstance();
        mindate.add(Calendar.MONTH, -1);
        dpd.setMinDate(mindate);
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
        SimpleDateFormat smp = new SimpleDateFormat("MMMM");
        try {
            // cal.setTime();
            // Log.e("isi tanggal","xx"+(-monthOfYear)+","+monthOfYear+","+(+monthOfYear));
            cal.set(year, monthOfYear - 1, dayOfMonth, hourOfDay, minute, second);
        } catch (Exception ex) {

        }
        tgldpt = cal;
        BulanSelect=format.format(tgldpt.getTime());
        monthName.setText(smp.format(tgldpt.getTime()));
    }
}
