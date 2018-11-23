package com.gmk.geisa.activities.report;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.gmk.geisa.Class.Konversi;
import com.gmk.geisa.R;
import com.gmk.geisa.controller.CustomerController;
import com.gmk.geisa.controller.SessionController;
import com.gmk.geisa.model.mCustomer;
import com.gmk.geisa.model.mSample;
import com.gmk.geisa.model.mSession;

public class ReportSampleDetailActivity extends AppCompatActivity {
    public static String sessionSample = "sessionSample";
    private mSample sample;
    private mCustomer customer;
    private mSession session;
    private String SalesId;

    Button btnCstCekin, btnCstMap;
    TextView cstNama, cstNamaAlias, cstIdGeisa, cstLevel, cstChannel, cstArea, cstZone, cstStatusMember,
            cstLocation, cstPIC, cstPICJabatan, cstEmail, cstTelp, cstAlamat;
    CheckBox qualityapplication, qualityfood, safetyfood, quantityall, packaginall;
    TextView csPic, csComplainId, csPicJabatan, csPicContact,
            csSampleRequestDate, csSampleFor, csItemRequest, csRealisasiInfo, csItemRealisasi, cstTglRealisasi, csResponseDate, csDetailResponse;


    private CustomerController customerController;
    private SessionController sessionController;
    private Konversi konversi = new Konversi();
    private String Latitude, Longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_sample_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Sample Detail");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//lock screen orientation
        customerController = CustomerController.getInstance(this);
        sessionController = SessionController.getInstance(this);
        btnCstCekin = (Button) findViewById(R.id.btnCstCekIn);
        btnCstMap = (Button) findViewById(R.id.btnCstLocation);

        cstNama = (TextView) findViewById(R.id.cstNama);
        cstNamaAlias = (TextView) findViewById(R.id.cstNamaAlias);
        cstIdGeisa = (TextView) findViewById(R.id.cstCode);//id geisa
        cstLevel = (TextView) findViewById(R.id.cstLevel);
        cstChannel = (TextView) findViewById(R.id.cstChannel);
        cstArea = (TextView) findViewById(R.id.cstArea);
        cstZone = (TextView) findViewById(R.id.cstZone);
        cstStatusMember = (TextView) findViewById(R.id.cstStatusMember);
        cstLocation = (TextView) findViewById(R.id.cstLocation);
        cstPIC = (TextView) findViewById(R.id.cstPIC);
        cstPICJabatan = (TextView) findViewById(R.id.cstPICJabatan);
        cstEmail = (TextView) findViewById(R.id.cstEmail);
        cstTelp = (TextView) findViewById(R.id.cstTelp);
        cstAlamat = (TextView) findViewById(R.id.cstAlamat);
        //complain detail

        csComplainId = (TextView) findViewById(R.id.csComplainId);
        csPic = (TextView) findViewById(R.id.csPic);
        csPicJabatan = (TextView) findViewById(R.id.csPicJabatan);
        csPicContact = (TextView) findViewById(R.id.csPicContact);
        csSampleRequestDate = (TextView) findViewById(R.id.csSampleRequestDate);
        csSampleFor = (TextView) findViewById(R.id.csSampleFor);
        csItemRequest = (TextView) findViewById(R.id.csItemRequest);
        csRealisasiInfo = (TextView) findViewById(R.id.csRealisasiInfo);
        csItemRealisasi = (TextView) findViewById(R.id.csItemRealisasi);
        cstTglRealisasi = (TextView) findViewById(R.id.cstTglRealisasi);
        csDetailResponse = (TextView) findViewById(R.id.csDetailResponse);
        csResponseDate = (TextView) findViewById(R.id.csResponseDate);
        //response


        session = sessionController.getSession("login", 1);
        if (session != null)
            SalesId = String.valueOf(session.getId());

        if (getIntent().getSerializableExtra(sessionSample) != null) {
            sample = (mSample) getIntent().getSerializableExtra(sessionSample);
            if (sample != null)
                customer = customerController.getCustomerByCustIdAndSalesId(String.valueOf(sample.getCustId()), SalesId);
            addData(sample);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    private void addData(mSample sample) {
        if (customer != null) {
            cstNama.setText(customer.getCustName());
            cstNamaAlias.setText(customer.getAliasName());
            cstIdGeisa.setText(String.valueOf(customer.getCustId()));
            cstLevel.setText(customer.getCustLevelId() + " - " + customer.getCustLevelName());
            cstChannel.setText(customer.getChannelName());
            cstArea.setText(customer.getAreaName());
            cstZone.setText(customer.getCustZoneId() + " - " + customer.getCustZoneName());
            cstStatusMember.setText(customer.getCustStatusName());
            cstPIC.setText(customer.getPic());
            cstPICJabatan.setText(customer.getPicJabatan());
            cstEmail.setText(customer.getEmail());
            cstTelp.setText(customer.getTelp().concat("/").concat(customer.getHp()));
            cstAlamat.setText(customer.getAddress());
        }

        //sample detail
        csComplainId.setText(sample.getSampleId());
        csSampleRequestDate.setText(sample.getSampleDate());
        csSampleFor.setText(sample.getSampleFor());
        String itemrequest = "";
        if (sample.getProductOfRequest() != null) {
            for (mSample.mProductSample cu : sample.getProductOfRequest()) {
                itemrequest += cu.getProductName().concat("[ ").concat(String.valueOf(cu.getQty())).concat(" ").concat(cu.getKemasan()).concat(" ]\n");
            }
        }
        csItemRequest.setText(itemrequest);

        csPic.setText(sample.getCustPic());
        csPicJabatan.setText(sample.getCustPicJabatan());
        csPicContact.setText(sample.getCustPicHp());
        cstTglRealisasi.setText(sample.getSampleReceivedDate());
        csRealisasiInfo.setText(sample.getNote());
        String tglrealisasi="";
        if(!sample.getSampleResponseDate().equalsIgnoreCase("1900-01-01") && !sample.getSampleResponseDate().equalsIgnoreCase("2000-01-01"))
            tglrealisasi=sample.getSampleResponseDate();
        cstTglRealisasi.setText(tglrealisasi);
        String itemresponse = "";
        if (sample.getProductOfRealisasi() != null) {
            for (mSample.mProductSample cu : sample.getProductOfRealisasi()) {
                itemresponse += cu.getProductName().concat("[ ").concat(String.valueOf(cu.getQty())).concat(" ").concat(cu.getKemasan()).concat(" ]\n");
            }
        }
        csItemRealisasi.setText(itemresponse);
        String tglresponse="";
        if(!sample.getSampleResponseDate().equalsIgnoreCase("1900-01-01") && !sample.getSampleResponseDate().equalsIgnoreCase("2000-01-01"))
            tglresponse=sample.getSampleResponseDate();
        csResponseDate.setText(tglresponse);
        csDetailResponse.setText(sample.getSampleResponseNote());

    }

    //untuk memantau tombol back
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            //getFragmentManager().popBackStack();
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


}
