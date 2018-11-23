package com.gmk.geisa.activities.customer;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gmk.geisa.R;
import com.gmk.geisa.controller.CustomerController;
import com.gmk.geisa.controller.DistBranchController;
import com.gmk.geisa.model.mCustomer;
import com.gmk.geisa.model.mCustomerAndDistBranch;
import com.gmk.geisa.model.mDistributorBranch;
import com.gmk.geisa.model.mSession;
import com.kenmeidearu.searchablespinnerlibrary.SearchableSpinner;
import com.kenmeidearu.searchablespinnerlibrary.mListString;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class CustomerAddDistributorActivity extends AppCompatActivity {
    public static String sessionUser = "sessionUser";
    public static String sessionCust = "sessionCust";
    private mSession session;
    private mCustomer customer;
    private String idoutlet;
    private int SalesId;
    private ProgressDialog pDialog;
    DistBranchController distBranchController;
    CustomerController customerController;


    ArrayList<mListString> listStringsDistributor = new ArrayList<>();
    //private String inBedMenuTitle = "Set to 'In bed'";
    SearchableSpinner listDistributor;
    TextInputEditText CstAddCustomerCode, CstAddCustomerDistributor;
    TextView CstAddCustomerName, cstDistributorName, cstDistributorArea, cstDistributorPic, cstDistributorEmail,
            cstDistributorTelp, cstDistributorAddress,CstCustName;
    Button distSave, distCancel;
    RelativeLayout infoDistributor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_add_distributor);
        this.setFinishOnTouchOutside(false);
        infoDistributor = (RelativeLayout) findViewById(R.id.infoDistributor);


        CstAddCustomerDistributor = (TextInputEditText) findViewById(R.id.CstAddCustomerDistributor);

        CstAddCustomerCode = (TextInputEditText) findViewById(R.id.CstAddCustomerCode);
        listDistributor = (SearchableSpinner) findViewById(R.id.listDistributor);
        CstCustName=(TextView) findViewById(R.id.CstCustName);
        CstAddCustomerName = (TextView) findViewById(R.id.CstAddCustomerName);
        cstDistributorName = (TextView) findViewById(R.id.cstDistributorName);
        cstDistributorArea = (TextView) findViewById(R.id.cstDistributorArea);
        cstDistributorPic = (TextView) findViewById(R.id.cstDistributorPic);
        cstDistributorEmail = (TextView) findViewById(R.id.cstDistributorEmail);
        cstDistributorTelp = (TextView) findViewById(R.id.cstDistributorTelp);
        cstDistributorAddress = (TextView) findViewById(R.id.cstDistributorAddress);
        distSave = (Button) findViewById(R.id.distSave);
        distCancel = (Button) findViewById(R.id.distCancel);

        //add controller
        distBranchController = DistBranchController.getInstance(this);
        customerController = CustomerController.getInstance(this);
        customerController.setCallBackAddCustomer(callBackAddCustomer);

        setTitle("");

        if (getIntent().getSerializableExtra(sessionUser) != null) {
            session = (mSession) getIntent().getSerializableExtra(sessionUser);
            SalesId = session.getId();
        }

        if (getIntent().getSerializableExtra(sessionCust) != null) {
            customer = (mCustomer) getIntent().getSerializableExtra(sessionCust);
            idoutlet = String.valueOf(customer.getCustId());
            CstCustName.setText(customer.getCustName());
            //CstAddCustomerName.setText(customer.getCustName());
            Log.e("isi cust", customer.getCustLevelId()+","+idoutlet + "," + customer.getChannelId());
            distSave.setEnabled(true);
        }

        //distributor
        ArrayList<mDistributorBranch> dbh = distBranchController.getAllDistBranch();
        listStringsDistributor.add(new mListString(0, "Select Distributor"));
        for (mDistributorBranch lv : dbh) {
            mListString isi = new mListString(lv.getDistBranchId(), lv.getDistBranchName(), lv.getAddress(), lv.getAreaCode(), lv.getAreaName());
            isi.setNilai5(lv.getPic());
            isi.setNilai6(lv.getEmail());
            isi.setNilai7(lv.getTelp());
            listStringsDistributor.add(isi);
        }
        listDistributor.setAdapter(listStringsDistributor, 4, 4);

        listDistributor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                CstAddCustomerDistributor.setText(String.valueOf(listStringsDistributor.get(position).get_id()));
                cstDistributorName.setText(listStringsDistributor.get(position).getNilai1());
                cstDistributorAddress.setText(listStringsDistributor.get(position).getNilai2());
                cstDistributorArea.setText(listStringsDistributor.get(position).getNilai3() + "-" + listStringsDistributor.get(position).getNilai4());
                cstDistributorPic.setText(listStringsDistributor.get(position).getNilai5());
                cstDistributorEmail.setText(listStringsDistributor.get(position).getNilai6());
                cstDistributorTelp.setText(listStringsDistributor.get(position).getNilai7());

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        CstAddCustomerDistributor.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().equals("0")) {
                    infoDistributor.setVisibility(View.VISIBLE);
                } else {
                    infoDistributor.setVisibility(View.GONE);
                }
            }
        });
        distSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savedToDatabase();
            }
        });
        distCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }


    private void savedToDatabase() {
        boolean cancel = false;
        View focusView = null;
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        CstAddCustomerName.setError(null);
        CstAddCustomerDistributor.setError(null);
        CstAddCustomerCode.setError(null);

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(CstAddCustomerDistributor.getEditableText())) {
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
            //add cust and distributor
            mCustomerAndDistBranch distBranch = new mCustomerAndDistBranch();
            distBranch.setCustomerDistBranchId(idCust);
            distBranch.setDistBranchId(Integer.parseInt(CstAddCustomerDistributor.getEditableText().toString()));
            distBranch.setCustId(Integer.parseInt(idoutlet));
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
            pDialog.setMessage("Send Distributor Customer To Server ...\n Please Wait...");
            pDialog.show();
            customerController.addCustomerDistributor(distBranch, String.valueOf(SalesId), idoutlet);
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
                    Toast.makeText(getApplicationContext(), "DATA DISTRIBUTOR CREATEED...", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "ERROR ON CREATE CUSTOMER...", Toast.LENGTH_SHORT).show();
                }
                customer = customerController.getCustomerByCustIdAndSalesId(idoutlet, String.valueOf(SalesId));
                Intent returnIntent = new Intent();
                returnIntent.putExtra(CustomerDetailActivity.sessioncustomer, customer);
                setResult(Activity.RESULT_OK,returnIntent);
                finish();

            }


        }
    };


}
