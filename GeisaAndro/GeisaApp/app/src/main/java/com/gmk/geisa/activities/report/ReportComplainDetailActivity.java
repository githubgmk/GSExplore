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
import com.gmk.geisa.model.mComplain;
import com.gmk.geisa.model.mCustomer;
import com.gmk.geisa.model.mSession;

public class ReportComplainDetailActivity extends AppCompatActivity {
    public final static String sessionComplain = "sessionComplain";
    private mComplain complain;
    private mCustomer customer;
    private mSession session;
    private String SalesId;

    Button btnCstCekin, btnCstMap;
    TextView cstNama, cstNamaAlias, cstIdGeisa, cstLevel, cstChannel, cstArea, cstZone, cstStatusMember,
            cstLocation, cstPIC, cstPICJabatan, cstEmail, cstTelp, cstAlamat;
    CheckBox qualityapplication, qualityfood, safetyfood, quantityall, packaginall;
    TextView csPic, csComplainId, csPicJabatan, csPicContact, csProduk, csProdukDetail, cstTglSample, csComplainDetail,
            csPriority, csResponseDate, csResponse;


    private CustomerController customerController;
    private SessionController sessionController;
    private Konversi konversi = new Konversi();
    private String Latitude, Longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_complain_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Complain Detail");
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
        safetyfood = (CheckBox) findViewById(R.id.safetyfood);
        qualityfood = (CheckBox) findViewById(R.id.qualityfood);
        qualityapplication = (CheckBox) findViewById(R.id.qualityapplication);
        quantityall = (CheckBox) findViewById(R.id.quantityall);
        packaginall = (CheckBox) findViewById(R.id.packaginall);
        csProduk = (TextView) findViewById(R.id.csProduk);
        csProdukDetail = (TextView) findViewById(R.id.csProdukDetail);
        cstTglSample = (TextView) findViewById(R.id.cstTglSample);
        csComplainDetail = (TextView) findViewById(R.id.csComplainDetail);
        //response
        csPriority = (TextView) findViewById(R.id.csPriority);
        csResponseDate = (TextView) findViewById(R.id.csResponseDate);
        csResponse = (TextView) findViewById(R.id.csResponse);

        session = sessionController.getSession("login", 1);
        if (session != null)
            SalesId = String.valueOf(session.getId());

        if (getIntent().getSerializableExtra(sessionComplain) != null) {
            complain = (mComplain) getIntent().getSerializableExtra(sessionComplain);
            if (complain != null)
                customer = customerController.getCustomerByCustIdAndSalesId(complain.getCustId(), SalesId);
            addData(complain, customer);
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

    private void addData(mComplain complain, mCustomer customer) {

        cstNama.setText(customer.getCustName());
        cstNamaAlias.setText(customer.getAliasName());
        cstIdGeisa.setText(String.valueOf(customer.getCustId()));
        cstLevel.setText(customer.getCustLevelId() + " - " + customer.getCustLevelName());
        cstChannel.setText(customer.getChannelName());
        cstArea.setText(customer.getAreaName());
        cstZone.setText(customer.getCustZoneId() + " - " + customer.getCustZoneName());
        cstStatusMember.setText(customer.getCustStatusName());


        Latitude = String.valueOf(customer.getLat());
        Longitude = String.valueOf(customer.getLng());
        cstPIC.setText(customer.getPic());
        cstPICJabatan.setText(customer.getPicJabatan());
        cstEmail.setText(customer.getEmail());
        cstTelp.setText(customer.getTelp().concat("/").concat(customer.getHp()));
        cstAlamat.setText(customer.getAddress());

        //complain detail
        csComplainId.setText(complain.getComplainId());
        csPic.setText(complain.getCustPic());
        csPicJabatan.setText(complain.getCustPicJabatan());
        csPicContact.setText(complain.getCustPicHp());
        csProduk.setText(complain.getProductName());
        csProdukDetail.setText(complain.getProductNameProduk());
        cstTglSample.setText(complain.getSampleSendDate());
        csComplainDetail.setText(complain.getComplainNote());
        qualityapplication.setChecked(complain.isQualityApplication());
        qualityfood.setChecked(complain.isQualityFood());
        safetyfood.setChecked(complain.isSafetyFood());
        quantityall.setChecked(complain.isQuantityAll());
        packaginall.setChecked(complain.isPackagingAll());
        csPriority.setText(complain.getComplainPriority());
        csResponseDate.setText(complain.getComplainResponseDate().concat(" By ").concat(complain.getComplainResponseBy()));
        csResponse.setText(complain.getComplainResponse());
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
