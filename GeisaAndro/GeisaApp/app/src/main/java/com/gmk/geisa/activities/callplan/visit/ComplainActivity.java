package com.gmk.geisa.activities.callplan.visit;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gmk.geisa.R;
import com.gmk.geisa.controller.CallPlanController;
import com.gmk.geisa.controller.CustomerController;
import com.gmk.geisa.controller.ProductController;
import com.gmk.geisa.model.mComplain;
import com.gmk.geisa.model.mCustomer;
import com.gmk.geisa.model.mCustomerAndDistBranch;
import com.gmk.geisa.model.mProduct;
import com.gmk.geisa.model.mSession;
import com.gmk.geisa.model.mTrackingPicture;
import com.kenmeidearu.materialdatetimepicker.date.DatePickerDialog;
import com.kenmeidearu.searchablespinnerlibrary.SearchableSpinner;
import com.kenmeidearu.searchablespinnerlibrary.mListString;
import com.mvc.imagepicker.ImagePicker;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ComplainActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    public static String sessionUser = "sessionUser";
    public static String callPlanId = "callPlanId";
    public static String customerId = "customerId";
    public static String sessionCust = "sessionCust";
    private mSession session;
    private mCustomer customer;
    private String idCallPlan;
    int idCustomer;
    private int SalesId;
    private ProgressDialog pDialog;
    CallPlanController callPlanController;
    CustomerController customerController;
    ProductController productController;
    ArrayList<mListString> listStringsType = new ArrayList<>();
    SearchableSpinner listItem;
    TextInputEditText complainId, complainVisitId, complaincustname, complaincustjab,
            complaincusthp, complainitem, complainitemdetail, complainSampleSendDate, complainnote;
    TextView custname, custaddress, distname, distaddress, typepotext;
    CheckBox safetyfood, qualityfood, qualityapplication, quantityall, packaginall;
    LinearLayout custdetail;
    Button buttonimage;
    Button changecust;
    ImageView imagefl1, imagefl2, imagefl3, imagefl4, imagefl5, imagefl6;
    private String idComplain;
    boolean imgEdit = false, imgChange = false;
    String imgSrc = "";
    Bitmap changeBitmap;
    private Uri file;
    private int recCode = 100;
    private ArrayList<mTrackingPicture> mGambar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complain);

        buttonimage = (Button) findViewById(R.id.buttonimage);
        imagefl1 = (ImageView) findViewById(R.id.imagefl1);
        imagefl2 = (ImageView) findViewById(R.id.imagefl2);
        imagefl3 = (ImageView) findViewById(R.id.imagefl3);
        imagefl4 = (ImageView) findViewById(R.id.imagefl4);
        imagefl5 = (ImageView) findViewById(R.id.imagefl5);
        imagefl6 = (ImageView) findViewById(R.id.imagefl6);
        safetyfood = (CheckBox) findViewById(R.id.safetyfood);
        qualityfood = (CheckBox) findViewById(R.id.qualityfood);
        qualityapplication = (CheckBox) findViewById(R.id.qualityapplication);
        quantityall = (CheckBox) findViewById(R.id.quantityall);
        packaginall = (CheckBox) findViewById(R.id.packaginall);
        complainId = (TextInputEditText) findViewById(R.id.complainId);
        complainVisitId = (TextInputEditText) findViewById(R.id.complainVisitId);
        complaincustname = (TextInputEditText) findViewById(R.id.complaincustname);
        complaincustjab = (TextInputEditText) findViewById(R.id.complaincustjab);
        complaincusthp = (TextInputEditText) findViewById(R.id.complaincusthp);
        complainitem = (TextInputEditText) findViewById(R.id.complainitem);
        listItem = (SearchableSpinner) findViewById(R.id.listItem);
        complainitemdetail = (TextInputEditText) findViewById(R.id.complainitemdetail);
        complainSampleSendDate = (TextInputEditText) findViewById(R.id.complainSendDate);
        complainnote = (TextInputEditText) findViewById(R.id.complainnote);
        custdetail=(LinearLayout) findViewById(R.id.custdetail);
        changecust = (Button) findViewById(R.id.changecust);
        custname = (TextView) findViewById(R.id.custname);
        distname = (TextView) findViewById(R.id.distname);
        changecust = (Button) findViewById(R.id.changecust);
        distaddress = (TextView) findViewById(R.id.month);
        custaddress = (TextView) findViewById(R.id.year);
        typepotext = (TextView) findViewById(R.id.typepotext);

        ImagePicker.setMinQuality(600, 600);

        this.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        //add controller
        callPlanController = CallPlanController.getInstance(this);
        productController = ProductController.getInstance(this);
        customerController = CustomerController.getInstance(this);
        setTitle("Add New Complain");
        mGambar = new ArrayList<>();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Calendar calss = Calendar.getInstance();
        SimpleDateFormat smp2 = new SimpleDateFormat("yyMMddhhmmss");
        if (getIntent().getSerializableExtra(sessionUser) != null) {
            session = (mSession) getIntent().getSerializableExtra(sessionUser);
            SalesId = session.getId();
            idComplain = String.valueOf(SalesId).concat(String.valueOf(smp2.format(calss.getTime())));
            complainId.setText(idComplain);
        }
        if (getIntent().getSerializableExtra(callPlanId) != null) {
            idCallPlan = (String) getIntent().getSerializableExtra(callPlanId);
            complainVisitId.setVisibility(View.VISIBLE);
            custdetail.setVisibility(View.GONE);
        }else{
            idCallPlan="0";
            complainVisitId.setVisibility(View.GONE);
            custdetail.setVisibility(View.VISIBLE);
        }
        complainVisitId.setText(idCallPlan);
        if (getIntent().getSerializableExtra(customerId) != null) {
            idCustomer = (int) getIntent().getSerializableExtra(customerId);
        }
        Log.e("id cust","id cust"+idCustomer);
        complaincustname.requestFocus();
        buttonimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgEdit = false;
                takePicture(v);
            }
        });

        imagefl1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show_dialogConfirm(0);
            }
        });
        imagefl2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show_dialogConfirm(1);
            }
        });
        imagefl3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show_dialogConfirm(2);
            }
        });
        imagefl4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show_dialogConfirm(3);
            }
        });
        imagefl5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show_dialogConfirm(4);
            }
        });
        imagefl6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show_dialogConfirm(5);
            }
        });

        //produk
        ArrayList<mProduct> prod = productController.getAllProduct("1");
        listStringsType.add(new mListString(0, "Select Item Product"));
        for (mProduct lv : prod) {
            mListString isi = new mListString(lv.getProductId(), lv.getProductName(), lv.getProductSimpleDescription());
            listStringsType.add(isi);
        }
        listItem.setAdapter(listStringsType, 2, 2);
        listItem.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                complainitem.setText(String.valueOf(listStringsType.get(position).get_id()));

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        changecust.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmCustomer();
            }
        });
        //tgl
        /*complainSampleSendDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeTanggal(complainSampleSendDate.getText().toString());
            }
        });*/
        complainSampleSendDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    changeTanggal(complainSampleSendDate.getText().toString());
            }
        });
        complaincusthp.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus)
                    getWindow().setSoftInputMode(
                            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
            }
        });


    }

    public void confirmCustomer() {
        //imgEdit = true;

        Context context = ComplainActivity.this;
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
            if (t.get_id() == idCustomer) {
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
                    //poinfo.setVisibility(View.VISIBLE);
                    String cname = listStringsCustomer.get(listCustomer.getSelectedItemPosition()).getNilai1();
                    int cid = listStringsCustomer.get(listCustomer.getSelectedItemPosition()).get_id();
                    String caddr = listStringsCustomer.get(listCustomer.getSelectedItemPosition()).getNilai2();
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
                    idCustomer = cid;
                    dialog.dismiss();
                }
            }
        });

        //
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }
    //crete menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_customer, menu);

        MenuItem item = menu.findItem(R.id.menuAddCst);
        item.setTitle("Save");
        item.setIcon(android.R.drawable.ic_menu_save);
        MenuItem item1 = menu.findItem(R.id.menuRefresh);
        item1.setTitle("Cancel");
        item1.setIcon(android.R.drawable.ic_menu_close_clear_cancel);
        return true;
    }


    private void cancelAdd() {
        //complainId.setText(null);
        //complainVisitId.setText(null);
        mGambar = new ArrayList<>();
        complaincustname.setText(null);
        complaincustjab.setText(null);
        complaincusthp.setText(null);
        complainitem.setText(null);
        complainitemdetail.setText(null);
        complainSampleSendDate.setText(null);
        complainnote.setText(null);

    }

    private void checkdata() {
        boolean cancel = false;
        View focusView = null;
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        complainId.setError(null);
        changecust.setError(null);
        complainVisitId.setError(null);
        complaincustname.setError(null);
        complaincustjab.setError(null);
        complaincusthp.setError(null);
        complainitem.setError(null);
        complainitemdetail.setError(null);
        complainSampleSendDate.setError(null);
        complainnote.setError(null);


        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(complainId.getEditableText())) {
            complainId.setError(getString(R.string.error_field_required));
            focusView = complainId;
            cancel = true;
        } else if (TextUtils.isEmpty(complainVisitId.getEditableText())) {
            complainVisitId.setError(getString(R.string.error_field_required));
            focusView = complainVisitId;
            cancel = true;
        }else if(idCustomer==0){
            changecust.setError(getString(R.string.error_field_required));
            focusView = changecust;
            cancel = true;
        } else if (TextUtils.isEmpty(complaincustname.getEditableText())) {
            complaincustname.setError(getString(R.string.error_field_required));
            focusView = complaincustname;
            cancel = true;
        } else if (TextUtils.isEmpty(complaincustjab.getEditableText())) {
            complaincustjab.setError(getString(R.string.error_field_required));
            focusView = complaincustjab;
            cancel = true;
        } else if (TextUtils.isEmpty(complaincusthp.getEditableText())) {
            complaincusthp.setError(getString(R.string.error_field_required));
            focusView = complaincusthp;
            cancel = true;
        } else if (TextUtils.isEmpty(complainitem.getEditableText()) || complainitem.getEditableText().toString().equalsIgnoreCase("0")) {
            complainitem.setError(getString(R.string.error_field_required));
            focusView = complainitem;
            cancel = true;
        } else if (TextUtils.isEmpty(complainSampleSendDate.getEditableText())) {
            complainSampleSendDate.setError(getString(R.string.error_field_required));
            focusView = complainSampleSendDate;
            cancel = true;
        } else if (TextUtils.isEmpty(complainnote.getEditableText())) {
            complainnote.setError(getString(R.string.error_field_required));
            focusView = complainnote;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            persetujuanComplain();
        }
    }

    private void saveDataToDatabase() {
        if (pDialog == null)
            pDialog = new ProgressDialog(this);
        pDialog.setMessage("Send Complain To Server ...\n Please Wait...");
        pDialog.show();
        mGambar.clear();
        if (bitmapArrayList != null && bitmapArrayList.size() > 0) {
            int i = 1;
            for (Bitmap bt : bitmapArrayList) {
                SaveImageAttachment(bt, i);
                i++;
            }
        }

        Calendar calss = Calendar.getInstance();
        SimpleDateFormat smp2 = new SimpleDateFormat("yyy-MM-dd hh:mm:ss");
        SimpleDateFormat smp3 = new SimpleDateFormat("yyy-MM-dd");

        mComplain callPlanComplain = new mComplain(complainId.getEditableText().toString(), safetyfood.isChecked(), qualityfood.isChecked(),
                qualityapplication.isChecked(), quantityall.isChecked(), packaginall.isChecked(), complainVisitId.getEditableText().toString(),String.valueOf(idCustomer),
                complainitem.getEditableText().toString(), complainitemdetail.getEditableText().toString(), 1, "Open", complainSampleSendDate.getEditableText().toString(),
                complaincustname.getEditableText().toString(), complaincustjab.getEditableText().toString(), complaincusthp.getEditableText().toString(),
                complainnote.getEditableText().toString(), smp2.format(calss.getTime()), String.valueOf(SalesId), 1);
        //Log.e("cek checked",safetyfood.isChecked()+","+callPlanComplain.isSafetyFood());

        callPlanController.updateCallPlanComplainToServer(String.valueOf(SalesId), callPlanComplain, mGambar);
        pDialog.dismiss();
        Toast.makeText(getApplicationContext(), "Save To Database Success" + callPlanComplain.getComplainId(), Toast.LENGTH_SHORT).show();
        finish();
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            //getFragmentManager().popBackStack();
            finish();
        } else if (item.getItemId() == R.id.menuAddCst) {//save customer
            checkdata();
            return true;
        } else if (item.getItemId() == R.id.menuRefresh) {
            Toast.makeText(getApplicationContext(), "Cancel", Toast.LENGTH_SHORT).show();
            cancelAdd();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void persetujuanComplain() {
        AlertDialog alertDialog = new AlertDialog.Builder(ComplainActivity.this).create();
        alertDialog.setTitle("");
        alertDialog.setMessage("Yakin Ingin Mengajukan Complain?\nApakah semua data dicek dan valid!");
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Confirm",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        saveDataToDatabase();
                    }
                });
        alertDialog.show();
    }

    //tanggal
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
                complainSampleSendDate.setText(format.format(tgldpt.getTime()));
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
        Calendar maxdate = Calendar.getInstance();
        maxdate.add(Calendar.DATE, 30);
        dpd.setMaxDate(maxdate);
        Calendar mindate = Calendar.getInstance();
        mindate.add(Calendar.DATE, -30);
        dpd.setMinDate(mindate);
        dpd.show(getFragmentManager(), "Datepickerdialog");
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
        complainSampleSendDate.setText(format.format(tgldpt.getTime()));
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth, int hourOfDay, int minute, int second) {
        setTanggal(year, monthOfYear, dayOfMonth, hourOfDay, minute, second);
    }

    //proses picture
    int indexchange = 0;

    public void show_dialogConfirm(final int index) {
        indexchange = index;
        imgEdit = true;
        Context context = ComplainActivity.this;
        Bitmap bt = null;

        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.preview_image, null);
        final ImageView preview = (ImageView) dialogView.findViewById(R.id.imagePreview);
        //final ImageView cancel = (ImageView) dialogView.findViewById(R.id.imageCancel);
        final Button btnDel = (Button) dialogView.findViewById(R.id.btnDel);
        final Button btnChange = (Button) dialogView.findViewById(R.id.btnChange);

        AlertDialog.Builder ad = new AlertDialog.Builder(context);
        //ad.setTitle(title);
        //ad.setMessage(message);
        ad.setView(dialogView);
        final AlertDialog dialog = ad.create();
        /*if (mGambar != null && mGambar.size() > index) {
            String pathGambar = mGambar.get(index).getPicture();
            File imgFile = new File(pathGambar);
            bt = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            preview.setImageBitmap(bt);
            imgSrc = pathGambar;
            changeBitmap=bt;
        } else {
            imgSrc = "DefaultGambar";
        }*/
        if (bitmapArrayList != null && bitmapArrayList.size() > index) {
           /* String pathGambar = mGambar.get(index).getPicture();
            File imgFile = new File(pathGambar);
            bt = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            imgSrc = pathGambar;*/
            Bitmap bts = bitmapArrayList.get(index);
            preview.setImageBitmap(bts);
            changeBitmap = bts;
            imgSrc = bts.toString();
        } else {
            imgSrc = "DefaultGambar";
        }
        preview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgEdit = false;
                imgChange = false;
                dialog.dismiss();
            }
        });

        final Bitmap finalBt = bt;
        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //mGambar.get(index);
                imgEdit = true;
                imgChange = false;
              /*  if(mGambar!=null && mGambar.size()>index) {
                    int sblm=mGambar.size();
                    mGambar.remove(index);
                    int ssdh=mGambar.size();
                    Log.e("delete gambar","didelete "+index+" dari "+ sblm+" sesudah"+ssdh);
                }*/
                dialog.dismiss();
                buttonimage.setEnabled(true);
                buttonimage.setBackgroundResource(R.drawable.ic_camera_ambil);
                reindexPicture(finalBt, indexchange, true);
                Toast.makeText(getApplicationContext(), "Delete Picture", Toast.LENGTH_SHORT).show();
            }
        });
        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if (mGambar != null) {
                imgEdit = true;
                imgChange = true;
                takePicture(v);
                //}
                dialog.dismiss();
                Toast.makeText(getApplicationContext(), "Change Picture", Toast.LENGTH_SHORT).show();
            }
        });

        //
        dialog.show();
    }

    public static final String IMAGE_TYPE = "image/*";

    public void takePicture(View view) {

       /* Intent intent = new Intent();
        intent.setType(IMAGE_TYPE);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,
                getString(R.string.picdist)), recCode);*/
        ImagePicker.pickImage(this, "Select Image:", recCode, false);
        /*
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        file = Uri.fromFile(getOutputMediaFile(SalesId + "_" + idComplain));
        //Log.e("isi fileasli",file.toString());
        intent.putExtra(MediaStore.EXTRA_OUTPUT, file);

        startActivityForResult(intent, recCode);*/
    }

    private static File getOutputMediaFile(String uniqname) {
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DCIM), "Geisa");

        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d("CameraComplain", "failed to create directory");
                return null;
            }
        }

        // String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        return new File(mediaStorageDir.getPath() + File.separator +
                "DM_" + uniqname + ".jpg");
    }


    ArrayList<Bitmap> bitmapArrayList = new ArrayList<>();

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Log.e("hasilnya", requestCode + "," + resultCode + "," + RESULT_OK);
        Bitmap bt = ImagePicker.getImageFromResult(this, requestCode, resultCode, data);
        if (bt != null) {
            // String filename=ImagePicker.getImagePathFromResult(this, requestCode, resultCode, data);
            //copy file to directory geisa and delete original
            //Log.e("file lokasi","lokasi "+filename);

            if (requestCode >= 100) {
                if (resultCode == RESULT_OK) {
                    if (imgEdit) {
                    /*File imgFile = new File(file.getPath());
                    File new1 = saveBitmapToFile(imgFile);
                    Bitmap bt = BitmapFactory.decodeFile(new1.getAbsolutePath());*/
                        reindexPicture(bt, indexchange, false);
                    } else {
                        bitmapArrayList.add(bt);
                        // Log.e("isi file", file.getPath());
                        //File imgFile = new File(file.getPath());
                        //File new1 = saveBitmapToFile(imgFile);
                        //Bitmap bt = BitmapFactory.decodeFile(new1.getAbsolutePath());
                        switch (requestCode) {
                            case 100:
                                imagefl1.setImageBitmap(bt);
                                break;
                            case 101:
                                imagefl2.setImageBitmap(bt);
                                break;
                            case 102:
                                imagefl3.setImageBitmap(bt);
                                break;
                            case 103:
                                imagefl4.setImageBitmap(bt);
                                break;
                            case 104:
                                imagefl5.setImageBitmap(bt);
                                break;
                            case 105:
                                imagefl6.setImageBitmap(bt);
                                buttonimage.setEnabled(false);
                                buttonimage.setBackgroundResource(R.drawable.ic_camera);
                                break;
                            default:
                                buttonimage.setEnabled(false);
                                buttonimage.setBackgroundResource(R.drawable.ic_camera);
                                break;

                        }
                       /* Calendar cal = Calendar.getInstance();
                        SimpleDateFormat smp2 = new SimpleDateFormat("yyMMddhhmmss");
                        SimpleDateFormat smp4 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                        Intent batteryIntent = getApplicationContext().registerReceiver(
                                null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
                        int levelBattrey = batteryIntent.getIntExtra("level", -1);
                        String TrackingPictureId = String.valueOf(SalesId).concat(String.valueOf(smp2.format(cal.getTime()))).concat(String.valueOf(recCode));
                        mGambar.add(new mTrackingPicture(TrackingPictureId, idComplain, filename,
                                String.valueOf(levelBattrey), "Complain", smp4.format(cal.getTime()), String.valueOf(SalesId), 1));
                        */
                        recCode += 1;
                    }

                }
            }
        }

    }

    private void reindexPicture(Bitmap bts, int index, boolean delete) {
        //int requestCode=100;
        int update = 100;
        boolean deleted;
        ArrayList<Bitmap> tempmgambar = new ArrayList<Bitmap>();
        imagefl1.setImageResource(R.drawable.ic_camera);
        imagefl2.setImageResource(R.drawable.ic_camera);
        imagefl3.setImageResource(R.drawable.ic_camera);
        imagefl4.setImageResource(R.drawable.ic_camera);
        imagefl5.setImageResource(R.drawable.ic_camera);
        imagefl6.setImageResource(R.drawable.ic_camera);
        tempmgambar.addAll(bitmapArrayList);
        if (tempmgambar.size() > 0) {
            bitmapArrayList.clear();
            if (imgChange) {
                if (tempmgambar.size() > index) {
                    tempmgambar.set(index, bts);
                } else {
                    tempmgambar.add(bts);
                }
            } else if (delete) {
                if (tempmgambar.size() > index)
                    tempmgambar.remove(index);
            }
            for (Bitmap bt : tempmgambar) {
                /*File imgFile = null;
                File new1;
                //Bitmap bt = null;
                String nama = "";
                String path = "";
                deleted = false;*/
                //
                /*double hsl= ImgDiffPercent.DiffImages(bt,bts);
                Log.e("reindex gambar",bt.toString()+ " masuk re index"+ bts.toString()+"," + hsl);
                if (ImgDiffPercent.DiffImages(bt,bts)>80) {
                    Log.e("reindex gambar", "masuk re update delete" + imgSrc);
                    if (imgChange) {
                        *//*imgFile = new File(file.getPath());
                        new1 = saveBitmapToFile(imgFile);
                        bt = BitmapFactory.decodeFile(new1.getAbsolutePath());
                        nama = imgFile.getName();
                        path = file.getPath();*//*
                        bt=bts;
                        Log.e("reindex gambar", "masuk re update" + path);
                    } else {
                        deleted = true;
                        Log.e("reindex gambar", "masuk re delete");
                    }
                } else {
                    Log.e("reindex gambar", "hanya update");
                   *//* imgFile = new File(gbr.getPicture());
                    new1 = saveBitmapToFile(imgFile);
                    bt = BitmapFactory.decodeFile(new1.getAbsolutePath());
                    nama = imgFile.getName();
                    path = gbr.getPicture();*//*
                    bt=bts;
                }
                Log.e("reindex gambar", "isi reg" + update);*/
                //if (!deleted) {
                // bitmapArrayList.add(bt);
                switch (update) {
                    case 100:
                        imagefl1.setImageBitmap(bt);
                        break;
                    case 101:
                        imagefl2.setImageBitmap(bt);
                        break;
                    case 102:
                        imagefl3.setImageBitmap(bt);
                        break;
                    case 103:
                        imagefl4.setImageBitmap(bt);
                        break;
                    case 104:
                        imagefl5.setImageBitmap(bt);
                        break;
                    case 105:
                        imagefl6.setImageBitmap(bt);
                        buttonimage.setEnabled(false);
                        buttonimage.setBackgroundResource(R.drawable.ic_camera);
                        break;
                    default:
                        buttonimage.setEnabled(false);
                        buttonimage.setBackgroundResource(R.drawable.ic_camera);
                        break;

                }

                //Calendar cal = Calendar.getInstance();
                //SimpleDateFormat smp2 = new SimpleDateFormat("yyMMddhhmmss");
                // SimpleDateFormat smp4 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                //Intent batteryIntent = getApplicationContext().registerReceiver(
                //        null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
                //int levelBattrey = batteryIntent.getIntExtra("level", -1);
                // String TrackingPictureId = String.valueOf(SalesId).concat(String.valueOf(smp2.format(cal.getTime()))).concat(String.valueOf(update));
                //tempmgambar.add(new mTrackingPicture(TrackingPictureId, idComplain, path,
                //        gbr.getStatusBattery(), "Complain", gbr.getCreatedDate(), String.valueOf(SalesId), 1));
                    /*tempmgambar.add(new mTrackingPicture(String.valueOf(update) + "-" + userlogin + "_" + smp2.format(cal.getTime()),
                            path, nama, kodeLapangan, smp.format(cal.getTime()), 1));*/
                update += 1;
            }

            //}
            //Log.e("reindex gambar", update + "sampe lah" + imgChange);
            /*if (imgSrc.equalsIgnoreCase("DefaultGambar") && imgChange) {
                File imgFile = null;
                File new1;
                Bitmap bt = null;
                String nama = "";
                String path = "";
                deleted = false;

                imgFile = new File(file.getPath());
                new1 = saveBitmapToFile(imgFile);
                bt=bts;
               // bt = BitmapFactory.decodeFile(new1.getAbsolutePath());
                nama = imgFile.getName();
                path = file.getPath();
                // Log.e("reindex gambar", update + "masuk lah update" + path);

                if (!deleted) {
                    bitmapArrayList.add(bt);
                    switch (update) {
                        case 100:
                            imagefl1.setImageBitmap(bt);
                            break;
                        case 101:
                            imagefl2.setImageBitmap(bt);
                            break;
                        case 102:
                            imagefl3.setImageBitmap(bt);
                            break;
                        case 103:
                            imagefl4.setImageBitmap(bt);
                            break;
                        case 104:
                            imagefl5.setImageBitmap(bt);
                            break;
                        case 105:
                            imagefl6.setImageBitmap(bt);
                            buttonimage.setEnabled(false);
                            buttonimage.setBackgroundResource(R.drawable.ic_camera);
                            break;
                        default:
                            buttonimage.setEnabled(false);
                            buttonimage.setBackgroundResource(R.drawable.ic_camera);
                            break;

                    }

                   *//* Calendar cal = Calendar.getInstance();
                    SimpleDateFormat smp2 = new SimpleDateFormat("yyMMddhhmmss");
                    SimpleDateFormat smp4 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                    Intent batteryIntent = getApplicationContext().registerReceiver(
                            null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
                    int levelBattrey = batteryIntent.getIntExtra("level", -1);
                    String TrackingPictureId = String.valueOf(SalesId).concat(String.valueOf(smp2.format(cal.getTime()))).concat(String.valueOf(update));
                    tempmgambar.add(new mTrackingPicture(TrackingPictureId, idComplain, file.getPath(),
                            String.valueOf(levelBattrey), "Complain", smp4.format(cal.getTime()), String.valueOf(SalesId), 1));
                    *//*
                    *//*tempmgambar.add(new mTrackingPicture(String.valueOf(update) + "-" + userlogin + "_" + smp2.format(cal.getTime()),
                            path, nama, kodeLapangan, smp.format(cal.getTime()), 1));*//*
                    update += 1;
                }
            }*/
        } else {
            // File imgFile = new File(file.getPath());
            //File new1 = saveBitmapToFile(imgFile);
            //Bitmap bt = BitmapFactory.decodeFile(new1.getAbsolutePath());
            Bitmap bt = bts;
            tempmgambar.add(bt);
            switch (update) {
                case 100:
                    imagefl1.setImageBitmap(bt);
                    break;
                case 101:
                    imagefl2.setImageBitmap(bt);
                    break;
                case 102:
                    imagefl3.setImageBitmap(bt);
                    break;
                case 103:
                    imagefl4.setImageBitmap(bt);
                    break;
                case 104:
                    imagefl5.setImageBitmap(bt);
                    break;
                case 105:
                    imagefl6.setImageBitmap(bt);
                    buttonimage.setBackgroundResource(R.drawable.ic_camera);
                    buttonimage.setEnabled(false);
                    break;
                default:
                    buttonimage.setEnabled(false);
                    buttonimage.setBackgroundResource(R.drawable.ic_camera);
                    break;

            }
           /* Calendar cal = Calendar.getInstance();
            SimpleDateFormat smp2 = new SimpleDateFormat("yyMMddhhmmss");
            SimpleDateFormat smp4 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Intent batteryIntent = getApplicationContext().registerReceiver(
                    null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
            int levelBattrey = batteryIntent.getIntExtra("level", -1);
            String TrackingPictureId = String.valueOf(SalesId).concat(String.valueOf(smp2.format(cal.getTime()))).concat(String.valueOf(update));
            tempmgambar.add(new mTrackingPicture(TrackingPictureId, idComplain, file.getPath(),
                    String.valueOf(levelBattrey), "Complain", smp4.format(cal.getTime()), String.valueOf(SalesId), 1));*/
            /*tempmgambar.add(new mTrackingPicture(String.valueOf(update) + "-" + userlogin + "_" + smp2.format(cal.getTime()),
                    file.getPath(), imgFile.getName(), kodeLapangan, smp.format(cal.getTime()), 1));*/
            update += 1;
        }

        recCode = update;
        bitmapArrayList.addAll(tempmgambar);
        //mGambar = tempmgambar;
        imgEdit = false;
        imgChange = false;
        imgSrc = "";
    }

    private void SaveImageAttachment(Bitmap bitmap, int urutan) {
        if (bitmap != null) {
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat smp2 = new SimpleDateFormat("yyMMddhhmmss");
            SimpleDateFormat smp4 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            String TrackingPictureId = String.valueOf(SalesId).concat(String.valueOf(smp2.format(cal.getTime()))).concat(String.valueOf(urutan));
            File file = SaveBitmapToFile(bitmap, SalesId + "_" + idComplain + "_" + TrackingPictureId);
            if (file != null) {
                Intent batteryIntent = getApplicationContext().registerReceiver(
                        null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
                int levelBattrey = batteryIntent.getIntExtra("level", -1);

                mGambar.add(new mTrackingPicture(TrackingPictureId, idComplain, file.getPath(),
                        String.valueOf(levelBattrey), "Complain", smp4.format(cal.getTime()), String.valueOf(SalesId), 1));
            }
        }

    }

    public File saveBitmapToFile(File file) {
        try {
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            o.inSampleSize = 6;
            FileInputStream inputStream = new FileInputStream(file);
            BitmapFactory.decodeStream(inputStream, null, o);
            inputStream.close();

            final int REQUIRED_SIZE = 75;
            int scale = 1;
            while (o.outWidth / scale / 2 >= REQUIRED_SIZE && o.outHeight / scale / 2 >= REQUIRED_SIZE) {
                scale *= 2;
            }
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            inputStream = new FileInputStream(file);
            Bitmap selectedBitmap = BitmapFactory.decodeStream(inputStream, null, o2);
            inputStream.close();
            //overwrie file
            file.createNewFile();
            FileOutputStream outputStream = new FileOutputStream(file);
            selectedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            return file;
        } catch (Exception e) {
            return null;
        }
    }

    private File SaveBitmapToFile(Bitmap bitmap, String filename) {
        File pictureFile = getOutputMediaFile(filename);
        if (pictureFile == null) {
            Log.e("SaveBitmapToFile", "Error creating media file, check storage permissions: ");// e.getMessage());
            return pictureFile;
        }
        try {
            FileOutputStream fos = new FileOutputStream(pictureFile);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.close();
        } catch (FileNotFoundException e) {
            Log.d("SaveBitmapToFile", "File not found: " + e.getMessage());
        } catch (IOException e) {
            Log.d("SaveBitmapToFile", "Error accessing file: " + e.getMessage());
        }
        return pictureFile;
    }

}
