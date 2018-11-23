package com.gmk.geisa.activities.customer;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.gmk.geisa.R;
import com.gmk.geisa.adapter.AddMapDialogFragment;
import com.gmk.geisa.controller.AreaController;
import com.gmk.geisa.controller.ChannelController;
import com.gmk.geisa.controller.CustStatusController;
import com.gmk.geisa.controller.CustomerController;
import com.gmk.geisa.controller.DistBranchController;
import com.gmk.geisa.controller.LevelController;
import com.gmk.geisa.controller.ZoneController;
import com.gmk.geisa.model.mArea;
import com.gmk.geisa.model.mChannel;
import com.gmk.geisa.model.mCustomer;
import com.gmk.geisa.model.mCustomerAndDistBranch;
import com.gmk.geisa.model.mDistributorBranch;
import com.gmk.geisa.model.mLevel;
import com.gmk.geisa.model.mSession;
import com.gmk.geisa.model.mZone;
import com.kenmeidearu.searchablespinnerlibrary.SearchableSpinner;
import com.kenmeidearu.searchablespinnerlibrary.mListString;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;

public class CustomerAddActivity extends AppCompatActivity implements com.kenmeidearu.materialdatetimepicker.date.DatePickerDialog.OnDateSetListener,
        AddMapDialogFragment.EditDialogListener {
    public static String sessionUser = "sessionUser";
    public static String newOutlet = "newOutlet";
    public static String idOutlet = "callPlanId";
    public static String sessionCust = "sessionCust";
    private mSession session;
    private mCustomer customer = null;
    private boolean newoutlet;
    private String idoutlet;
    private int SalesId;
    private ProgressDialog pDialog;
    LevelController levelController;
    ChannelController channelController;
    AreaController areaController;
    ZoneController zoneController;
    CustStatusController custStatusController;
    DistBranchController distBranchController;
    CustomerController customerController;

    ArrayList<mListString> listStringsLevel = new ArrayList<>();
    ArrayList<mListString> listStringsChannel = new ArrayList<>();
    ArrayList<mListString> listStringsArea = new ArrayList<>();
    ArrayList<mListString> listStringsZone = new ArrayList<>();
    ArrayList<mListString> listStringsStatus = new ArrayList<>();
    ArrayList<mListString> listStringsPKP = new ArrayList<>();
    ArrayList<mListString> listStringsDistributor = new ArrayList<>();
    //private String inBedMenuTitle = "Set to 'In bed'";
    Button CstAddCustomerMap;
    SearchableSpinner listLevel, listChannel, listArea, listZone, listStatus, listPKP, listDistributor;
    TextInputEditText CstAddCustomerName, CstAddCustomerNameAlias, CstAddCustomerLevel, CstAddCustomerChannel,
            CstAddCustomerArea, CstAddCustomerZone, CstAddCustomerLocation, CstAddCustomerPic,
            CstAddCustomerPicJabatan, CstAddCustomerEmail, CstAddCustomerTelp, CstAddCustomerHp,
            CstAddCustomerAlamat, CstAddCustomerWebsite, CstAddCustomerJoinDate, CstAddCustomerStatusPKP, CstAddCustomerKreditLimit,
            CstAddCustomerTOP, CstAddCustomerNPWP, CstAddCustomerDistributor, CstAddCustomerCode;
    RadioButton CstAddCustomerTypeRegular, CstAddCustomerTypeProspect;
    TextView CstAddCustomerTypeGroupLabel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_add);
        CstAddCustomerTypeGroupLabel = (TextView) findViewById(R.id.CstAddCustomerTypeGroupLabel);
        CstAddCustomerTypeRegular = (RadioButton) findViewById(R.id.CstAddCustomerTypeRegular);
        CstAddCustomerTypeProspect = (RadioButton) findViewById(R.id.CstAddCustomerTypeProspect);
        CstAddCustomerName = (TextInputEditText) findViewById(R.id.CstAddCustomerName);
        CstAddCustomerNameAlias = (TextInputEditText) findViewById(R.id.CstAddCustomerNameAlias);
        CstAddCustomerLevel = (TextInputEditText) findViewById(R.id.CstAddCustomerLevel);
        listLevel = (SearchableSpinner) findViewById(R.id.listLevel);
        CstAddCustomerChannel = (TextInputEditText) findViewById(R.id.CstAddCustomerChannel);
        listChannel = (SearchableSpinner) findViewById(R.id.listChannel);
        CstAddCustomerArea = (TextInputEditText) findViewById(R.id.CstAddCustomerArea);
        listArea = (SearchableSpinner) findViewById(R.id.listArea);
        CstAddCustomerZone = (TextInputEditText) findViewById(R.id.CstAddCustomerZone);
        listZone = (SearchableSpinner) findViewById(R.id.listZone);
        CstAddCustomerLocation = (TextInputEditText) findViewById(R.id.CstAddCustomerLocation);
        CstAddCustomerPic = (TextInputEditText) findViewById(R.id.CstAddCustomerPic);
        CstAddCustomerPicJabatan = (TextInputEditText) findViewById(R.id.CstAddCustomerPicJabatan);
        CstAddCustomerEmail = (TextInputEditText) findViewById(R.id.CstAddCustomerEmail);
        CstAddCustomerTelp = (TextInputEditText) findViewById(R.id.CstAddCustomerTelp);
        CstAddCustomerHp = (TextInputEditText) findViewById(R.id.CstAddCustomerHp);
        CstAddCustomerAlamat = (TextInputEditText) findViewById(R.id.CstAddCustomerAlamat);
        CstAddCustomerWebsite = (TextInputEditText) findViewById(R.id.CstAddCustomerWebsite);
        CstAddCustomerJoinDate = (TextInputEditText) findViewById(R.id.CstAddCustomerJoinDate);
        CstAddCustomerStatusPKP = (TextInputEditText) findViewById(R.id.CstAddCustomerStatusPKP);
        listPKP = (SearchableSpinner) findViewById(R.id.listPKP);
        CstAddCustomerKreditLimit = (TextInputEditText) findViewById(R.id.CstAddCustomerKreditLimit);
        CstAddCustomerTOP = (TextInputEditText) findViewById(R.id.CstAddCustomerTOP);
        CstAddCustomerNPWP = (TextInputEditText) findViewById(R.id.CstAddCustomerNPWP);
        CstAddCustomerDistributor = (TextInputEditText) findViewById(R.id.CstAddCustomerDistributor);
        CstAddCustomerCode = (TextInputEditText) findViewById(R.id.CstAddCustomerCode);
        listDistributor = (SearchableSpinner) findViewById(R.id.listDistributor);
        CstAddCustomerMap = (Button) findViewById(R.id.CstAddCustomerMap);
        //add controller
        levelController = LevelController.getInstance(this);
        areaController = AreaController.getInstance(this);
        channelController = ChannelController.getInstance(this);
        zoneController = ZoneController.getInstance(this);
        custStatusController = CustStatusController.getInstance(this);
        distBranchController = DistBranchController.getInstance(this);
        customerController = CustomerController.getInstance(this);
        customerController.setCallBackAddCustomer(callBackAddCustomer);

        setTitle("Add New Customer Outlet");


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (getIntent().getSerializableExtra(sessionUser) != null) {
            session = (mSession) getIntent().getSerializableExtra(sessionUser);
            SalesId = session.getId();
        }
        if (getIntent().getSerializableExtra(newOutlet) != null) {
            newoutlet = (boolean) getIntent().getSerializableExtra(newOutlet);
        }
        if (getIntent().getSerializableExtra(idOutlet) != null) {
            idoutlet = (String) getIntent().getSerializableExtra(idOutlet);
        }


        //level
        ArrayList<mLevel> lvl = levelController.getAllLevel();
        listStringsLevel.add(new mListString(0, "Select Level"));
        for (mLevel lv : lvl) {
            mListString isi = new mListString(lv.getCustLevelId(), lv.getCustLevelName());
            listStringsLevel.add(isi);
        }
        listLevel.setAdapter(listStringsLevel, 2, 2);

        listLevel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Log.e("tes posisi", id + " ," + position + "," + listStringsLevel.get(position).get_id());
                if (null != customer) {
                    CstAddCustomerLevel.setText(String.valueOf(customer.getCustLevelId()));
                    CstAddCustomerLevel.setEnabled(false);
                } else {
                    CstAddCustomerLevel.setText(String.valueOf(listStringsLevel.get(position).get_id()));
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //channel
        ArrayList<mChannel> cnl = channelController.getAllChannel();
        listStringsChannel.add(new mListString(0, "Select Channel"));
        for (mChannel lv : cnl) {
            mListString isi = new mListString(lv.getChannelId(), lv.getChannelName());
            listStringsChannel.add(isi);
        }
        listChannel.setAdapter(listStringsChannel, 2, 2);

        listChannel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                CstAddCustomerChannel.setText(String.valueOf(listStringsChannel.get(position).get_id()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //area
        ArrayList<mArea> area = areaController.getAllArea();
        listStringsArea.add(new mListString(0, "Select Area"));
        for (mArea lv : area) {
            mListString isi = new mListString(lv.getAreaId(), lv.getAreaName());
            listStringsArea.add(isi);
        }
        listArea.setAdapter(listStringsArea, 2, 2);

        listArea.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                CstAddCustomerArea.setText(String.valueOf(listStringsArea.get(parent.getSelectedItemPosition()).get_id()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //zone
        ArrayList<mZone> zn = zoneController.getAllZone();
        listStringsZone.add(new mListString(0, "Select Zone"));
        for (mZone lv : zn) {
            mListString isi = new mListString(lv.getCustZoneId(), lv.getCustZoneName());
            listStringsZone.add(isi);
        }
        listZone.setAdapter(listStringsZone, 2, 2);

        listZone.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                CstAddCustomerZone.setText(String.valueOf(listStringsZone.get(parent.getSelectedItemPosition()).get_id()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        //status PKP
        listStringsPKP.add(new mListString(0, "Select PKP"));
        listStringsPKP.add(new mListString(1, "PKP", "PKP"));
        listStringsPKP.add(new mListString(2, "NON PKP", "NONPKP"));
        listPKP.setAdapter(listStringsPKP, 2, 2);

        listPKP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (id > 0) {
                    CstAddCustomerStatusPKP.setText(listStringsPKP.get(parent.getSelectedItemPosition()).getNilai2());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //distributor
        ArrayList<mDistributorBranch> dbh = distBranchController.getAllDistBranch();
        listStringsDistributor.add(new mListString(0, "Select Distributor"));
        for (mDistributorBranch lv : dbh) {
            mListString isi = new mListString(lv.getDistBranchId(), lv.getDistBranchName(), lv.getAddress(), lv.getAreaCode(), lv.getAreaName());
            listStringsDistributor.add(isi);
        }
        listDistributor.setAdapter(listStringsDistributor, 4, 4);

        listDistributor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                CstAddCustomerDistributor.setText(String.valueOf(listStringsDistributor.get(position).get_id()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //joindate
        CstAddCustomerJoinDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeTanggal(CstAddCustomerJoinDate.getText().toString());
            }
        });
        CstAddCustomerJoinDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    changeTanggal(CstAddCustomerJoinDate.getText().toString());
            }
        });

        //btn location
        CstAddCustomerMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = getCurrentFocus();
                if (view != null) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
                showEditDialog();
            }
        });
        if (getIntent().getSerializableExtra(sessionCust) != null) {
            customer = (mCustomer) getIntent().getSerializableExtra(sessionCust);
            if (null != customer) {
                // Log.e("isi customer",customer.getCustName());
                CstAddCustomerName.setText(customer.getCustName());
                CstAddCustomerName.setEnabled(false);
                //Log.e("isi cust",customer.getCustLevelId()+","+customer.getChannelId());
                if (customer.getCustStatusId() == 5) {
                    CstAddCustomerTypeProspect.setChecked(true);
                } else {
                    CstAddCustomerTypeRegular.setChecked(true);
                }


                //CstAddCustomerLevel.setText(String.valueOf(customer.getCustLevelId()));
                for (int i = 0; i < listStringsLevel.size(); i++) {
                    if (listStringsLevel.get(i).get_id() == customer.getCustLevelId()) {
                        listLevel.setSelection(i);
                        break;
                    }
                }

                // CstAddCustomerChannel.setText(String.valueOf(customer.getChannelId()));
                for (int i = 0; i < listStringsChannel.size(); i++) {
                    if (listStringsChannel.get(i).get_id() == customer.getChannelId()) {
                        listChannel.setSelection(i);
                        break;
                    }
                }
                if (customer.getJoinDate() != null)
                    CstAddCustomerJoinDate.setText(customer.getJoinDate().replace("/", "-"));
                for (int i = 0; i < listStringsPKP.size(); i++) {
                    if (listStringsPKP.get(i).getNilai2() == customer.getStsPkpId()) {
                        listPKP.setSelection(i);
                        break;
                    }
                }
                CstAddCustomerKreditLimit.setText(customer.getCreditLimit());
                CstAddCustomerTOP.setText(customer.getTop());
                CstAddCustomerNPWP.setText(customer.getNpwp());
            }
            CstAddCustomerTypeProspect.setChecked(true);
            CstAddCustomerNameAlias.requestFocus();
        }

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
        CstAddCustomerTypeRegular.setChecked(false);
        //CstAddCustomerTypeProspect.setChecked(false);
        CstAddCustomerName.setText(null);
        CstAddCustomerNameAlias.setText(null);
        CstAddCustomerLevel.setText(null);
        listLevel.setSelection(0);
        CstAddCustomerChannel.setText(null);
        listChannel.setSelection(0);
        CstAddCustomerArea.setText(null);
        listArea.setSelection(0);
        CstAddCustomerZone.setText(null);
        listZone.setSelection(0);
        CstAddCustomerLocation.setText(null);
        CstAddCustomerPic.setText(null);
        CstAddCustomerPicJabatan.setText(null);
        CstAddCustomerEmail.setText(null);
        CstAddCustomerTelp.setText(null);
        CstAddCustomerHp.setText(null);
        CstAddCustomerAlamat.setText(null);
        CstAddCustomerWebsite.setText(null);
        CstAddCustomerJoinDate.setText(null);
        CstAddCustomerStatusPKP.setText(null);
        listPKP.setSelection(0);
        CstAddCustomerKreditLimit.setText(null);
        CstAddCustomerTOP.setText(null);
        CstAddCustomerNPWP.setText(null);
        CstAddCustomerDistributor.setText(null);
        CstAddCustomerCode.setText(null);
        CstAddCustomerName.setError(null);
        CstAddCustomerLevel.setError(null);
        CstAddCustomerChannel.setError(null);
        CstAddCustomerArea.setError(null);
        CstAddCustomerLocation.setError(null);
        CstAddCustomerPic.setError(null);
        CstAddCustomerTelp.setError(null);
        CstAddCustomerAlamat.setError(null);
        CstAddCustomerEmail.setError(null);
        CstAddCustomerJoinDate.setError(null);
        CstAddCustomerDistributor.setError(null);
        listDistributor.setSelection(0);
        CstAddCustomerCode.setError(null);
        CstAddCustomerName.requestFocus();
    }

    private void savedToDatabase() {
        boolean cancel = false;
        View focusView = null;
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        CstAddCustomerTypeGroupLabel.setError(null);
        CstAddCustomerName.setError(null);
        CstAddCustomerLevel.setError(null);
        CstAddCustomerChannel.setError(null);
        CstAddCustomerArea.setError(null);
        CstAddCustomerLocation.setError(null);
        CstAddCustomerPic.setError(null);
        CstAddCustomerEmail.setError(null);
        CstAddCustomerTelp.setError(null);
        CstAddCustomerAlamat.setError(null);
        CstAddCustomerJoinDate.setError(null);
        CstAddCustomerStatusPKP.setError(null);
        CstAddCustomerDistributor.setError(null);
        CstAddCustomerCode.setError(null);

        // Check for a valid password, if the user entered one.
        if (!CstAddCustomerTypeRegular.isChecked() && !CstAddCustomerTypeProspect.isChecked()) {
            CstAddCustomerTypeGroupLabel.setError(getString(R.string.error_field_required));
            focusView = CstAddCustomerTypeGroupLabel;
            cancel = true;
        } else if (TextUtils.isEmpty(CstAddCustomerName.getEditableText())) {
            CstAddCustomerName.setError(getString(R.string.error_field_required));
            focusView = CstAddCustomerName;
            cancel = true;
        } else if (TextUtils.isEmpty(CstAddCustomerNameAlias.getEditableText())) {
            CstAddCustomerNameAlias.setError(getString(R.string.error_field_required));
            focusView = CstAddCustomerNameAlias;
            cancel = true;
        } else if (TextUtils.isEmpty(CstAddCustomerLevel.getEditableText()) || CstAddCustomerLevel.getEditableText().toString().equals("0")) {
            CstAddCustomerLevel.setError(getString(R.string.error_field_required));
            focusView = CstAddCustomerLevel;
            cancel = true;
        } else if (TextUtils.isEmpty(CstAddCustomerChannel.getEditableText()) || CstAddCustomerChannel.getEditableText().toString().equals("0")) {
            CstAddCustomerChannel.setError(getString(R.string.error_field_required));
            focusView = CstAddCustomerChannel;
            cancel = true;
        } else if (TextUtils.isEmpty(CstAddCustomerArea.getEditableText()) || CstAddCustomerArea.getEditableText().toString().equals("0")) {
            CstAddCustomerArea.setError(getString(R.string.error_field_required));
            focusView = CstAddCustomerArea;
            cancel = true;
        } else if (TextUtils.isEmpty(CstAddCustomerLocation.getEditableText())) {
            CstAddCustomerLocation.setError(getString(R.string.error_field_required));
            focusView = CstAddCustomerLocation;
            cancel = true;
        } else if (TextUtils.isEmpty(CstAddCustomerPic.getEditableText())) {
            CstAddCustomerPic.setError(getString(R.string.error_field_required));
            focusView = CstAddCustomerPic;
            cancel = true;
        }
        else if (!TextUtils.isEmpty(CstAddCustomerEmail.getEditableText())) {
            if(!isEmailValid(CstAddCustomerEmail.getEditableText().toString())) {
                CstAddCustomerEmail.setError(getString(R.string.error_invalid_email));
                focusView = CstAddCustomerEmail;
                cancel = true;
            }
        }
        else if (TextUtils.isEmpty(CstAddCustomerTelp.getEditableText())) {
            CstAddCustomerTelp.setError(getString(R.string.error_field_required));
            focusView = CstAddCustomerTelp;
            cancel = true;
        } else if (TextUtils.isEmpty(CstAddCustomerAlamat.getEditableText())) {
            CstAddCustomerAlamat.setError(getString(R.string.error_field_required));
            focusView = CstAddCustomerAlamat;
            cancel = true;
        }  else if (TextUtils.isEmpty(CstAddCustomerJoinDate.getEditableText())) {
            CstAddCustomerJoinDate.setError(getString(R.string.error_field_required));
            focusView = CstAddCustomerJoinDate;
            cancel = true;
        } else if (TextUtils.isEmpty(CstAddCustomerStatusPKP.getEditableText())) {
            CstAddCustomerStatusPKP.setError(getString(R.string.error_field_required));
            focusView = CstAddCustomerStatusPKP;
            cancel = true;
        } else if (TextUtils.isEmpty(CstAddCustomerDistributor.getEditableText())) {
            CstAddCustomerDistributor.setError(getString(R.string.error_field_required));
            focusView = CstAddCustomerDistributor;
            cancel = true;
        } else if (TextUtils.isEmpty(CstAddCustomerCode.getEditableText())) {
            CstAddCustomerCode.setError(getString(R.string.error_field_required));
            focusView = CstAddCustomerCode;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            Calendar calss = Calendar.getInstance();
            SimpleDateFormat smp2 = new SimpleDateFormat("yyMMddhhmmss");
            int idCust = (int) (new Date().getTime() / 1000);
            String custIdAndro = String.valueOf(SalesId).concat(String.valueOf(smp2.format(calss.getTime())));
            mCustomer customer = new mCustomer();
            customer.setSalesmanId(SalesId);
            customer.set_Id(idCust);
            customer.setCustId(idCust);
            if (newoutlet) {
                customer.setCustGroupId(idoutlet);
            } else {
                customer.setCustGroupId(String.valueOf(idCust));
            }
            customer.setCustName(CstAddCustomerName.getEditableText().toString());
            customer.setAliasName(CstAddCustomerNameAlias.getEditableText().toString());
            customer.setCustLevelId(Integer.parseInt(CstAddCustomerLevel.getEditableText().toString()));
            customer.setChannelId(Integer.parseInt(CstAddCustomerChannel.getEditableText().toString()));
            customer.setAreaId(Integer.parseInt(CstAddCustomerArea.getEditableText().toString()));
            customer.setCustZoneId(Integer.parseInt(CstAddCustomerZone.getEditableText().toString()));
            String[] parts = CstAddCustomerLocation.getEditableText().toString().split(","); // escape .
            String part1 = parts[0];
            String part2 = parts[1];
            customer.setLat(!part1.equalsIgnoreCase("") ? Double.parseDouble(part1) : 0);
            customer.setLng(!part2.equalsIgnoreCase("") ? Double.parseDouble(part2) : 0);
            if (CstAddCustomerTypeProspect.isChecked()) {
                customer.setCustStatusId(3);//5-prospect
            } else {
                customer.setCustStatusId(3);//in review akan dirubah draft jika tidak terkirim
            }
            customer.setRadius(250);
            customer.setPic(CstAddCustomerPic.getEditableText().toString());
            customer.setPicJabatan(CstAddCustomerPicJabatan.getEditableText().toString());
            customer.setEmail(CstAddCustomerEmail.getEditableText().toString());
            customer.setTelp(CstAddCustomerTelp.getEditableText().toString());
            customer.setHp(CstAddCustomerHp.getEditableText().toString());
            customer.setAddress(CstAddCustomerAlamat.getEditableText().toString());
            customer.setWebsite(CstAddCustomerWebsite.getEditableText().toString());
            customer.setJoinDate(CstAddCustomerJoinDate.getEditableText().toString());
            customer.setStsPkpId(CstAddCustomerStatusPKP.getEditableText().toString());
            customer.setStsPkpName("");
            customer.setCreditLimit(CstAddCustomerKreditLimit.getEditableText().toString());
            customer.setCustById("Sales");//cek lagi
            customer.setCustByName("Sales");
            customer.setFreqTypeId(0);
            customer.setFreqTypeName("");
            customer.setTop(CstAddCustomerTOP.getEditableText().toString());
            customer.setNpwp(CstAddCustomerNPWP.getEditableText().toString());
            customer.setCustIdAndro(custIdAndro);
            customer.setStatusSend(1);
            //add cust and distributor
            mCustomerAndDistBranch distBranch = new mCustomerAndDistBranch();
            distBranch.setCustomerDistBranchId(idCust);
            distBranch.setDistBranchId(Integer.parseInt(CstAddCustomerDistributor.getEditableText().toString()));
            distBranch.setCustId(idCust);
            distBranch.setCustCode(CstAddCustomerCode.getEditableText().toString());
            distBranch.setDiscGroupId("");
            distBranch.setDiscGroupName("");
            distBranch.setPriceGroupId("");
            distBranch.setPriceGroupName("");
            distBranch.setCustIdAndro(custIdAndro);
            distBranch.setStatusSend(1);
            ArrayList<mCustomerAndDistBranch> distBranches = new ArrayList<>();
            distBranches.add(distBranch);
            customer.setCustomerAndBranch(distBranches);
            if (pDialog == null)
                pDialog = new ProgressDialog(this);
            pDialog.setMessage("Send Customer To Server ...\n Please Wait...");
            pDialog.show();
            int group = 0;
            if (newoutlet)
                group = 1;
            customer.setNewOutlet(group);
            customerController.addCustomer(customer, String.valueOf(SalesId), group);
            //Toast.makeText(getApplicationContext(), "Save To Database Success"+ customer.getCustId(), Toast.LENGTH_SHORT).show();

        }
    }

    CustomerController.CallBackAddCustomer callBackAddCustomer = new CustomerController.CallBackAddCustomer() {
        @Override
        public void resultReady(mCustomer customers, mCustomer oldcustomer) {
            if (pDialog != null)
                pDialog.dismiss();
            if (null != customers) {
                if (customerController.addCustomerToLocal(customers, oldcustomer)) {
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "ERROR ON CREATE CUSTOMER...", Toast.LENGTH_SHORT).show();
                }

            }


        }
    };

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            //getFragmentManager().popBackStack();
            finish();
        } else if (item.getItemId() == R.id.menuAddCst) {//save customer
            savedToDatabase();
            return true;
        } else if (item.getItemId() == R.id.menuRefresh) {
            Toast.makeText(getApplicationContext(), "Cancel", Toast.LENGTH_SHORT).show();
            cancelAdd();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //edit maps
    private void showEditDialog() {
        // SupportMapFragment
        FragmentManager fm = getSupportFragmentManager();
        AddMapDialogFragment df = new AddMapDialogFragment(this);
        df.show(fm, "fragment tab");

    }

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
                CstAddCustomerJoinDate.setText(format.format(tgldpt.getTime()));
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
        mindate.set(Calendar.DAY_OF_MONTH, 1);
        //mindate.add(Calendar.YEAR, -1);
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
        try {
            // cal.setTime();
            // Log.e("isi tanggal","xx"+(-monthOfYear)+","+monthOfYear+","+(+monthOfYear));
            cal.set(year, monthOfYear - 1, dayOfMonth, hourOfDay, minute, second);
        } catch (Exception ex) {

        }
        tgldpt = cal;
        CstAddCustomerJoinDate.setText(format.format(tgldpt.getTime()));
    }


//implement maps

    @Override
    public void updateResult(String inputText) {
        CstAddCustomerLocation.setText(inputText);
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        // return email.contains("@");
        Matcher matcher = Patterns.EMAIL_ADDRESS.matcher(email);
        return matcher.matches();
    }
}
