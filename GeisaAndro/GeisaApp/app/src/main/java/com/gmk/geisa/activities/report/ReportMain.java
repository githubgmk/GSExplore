package com.gmk.geisa.activities.report;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.gmk.geisa.R;

import com.gmk.geisa.controller.SessionController;
import com.gmk.geisa.model.mCustomer;
import com.gmk.geisa.model.mSession;

public class ReportMain extends AppCompatActivity{
    // Remove the below line after defining your own ad unit ID.
    public final static String sessionUser = "sessionUser";
    public final static String sessionCust = "sessionCust";
    SessionController sessionController;
    String idCallPlan, CustomerName="";
    Button btnReportCallPlan, btnReportPo, btnReportComplain, btnReportBi, btnReportSample, btnReportDemo;
    TextView custName;
    private mSession session;
    private mCustomer customer;
    int SalesId,CustId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_report);
        setTitle("All Report");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        btnReportCallPlan = (Button) findViewById(R.id.btnReportCallPlan);
        btnReportPo = (Button) findViewById(R.id.btnReportPo);
        btnReportComplain = (Button) findViewById(R.id.btnReportComplain);
        btnReportBi = (Button) findViewById(R.id.btnReportBi);
        btnReportSample = (Button) findViewById(R.id.btnReportSample);
        btnReportDemo = (Button) findViewById(R.id.btnReportDemo);
        custName=(TextView) findViewById(R.id.custName);
        sessionController = SessionController.getInstance(this);

        if (getIntent().getSerializableExtra(sessionUser) != null) {
            session = (mSession) getIntent().getSerializableExtra(sessionUser);
            SalesId = session.getId();
        }
        if (getIntent().getSerializableExtra(sessionCust) != null) {
            customer = (mCustomer) getIntent().getSerializableExtra(sessionCust);
            if(customer!=null){
                CustomerName=customer.getCustName();
                custName.setText(CustomerName);
                CustId=customer.getCustId();
            }
        }else{
            CustId=0;
        }

        btnReportCallPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inten = new Intent(ReportMain.this, ReportCallPlanActivity.class);
                inten.putExtra(ReportCallPlanActivity.sessionUser, session);
                inten.putExtra(ReportCallPlanActivity.sessionCustId, CustId);
                inten.putExtra(ReportCallPlanActivity.sessionCustName, CustomerName);
                startActivityForResult(inten, 1);
            }
        });
        btnReportPo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inten = new Intent(ReportMain.this, ReportPOActivity.class);
                inten.putExtra(ReportPOActivity.sessionUser, session);
                inten.putExtra(ReportPOActivity.sessionCustId, CustId);
                inten.putExtra(ReportPOActivity.sessionCustName, CustomerName);
                startActivityForResult(inten, 2);
            }
        });
        btnReportComplain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inten = new Intent(ReportMain.this, ReportComplainActivity.class);//berpindah ke activity yang lain dgn data
                inten.putExtra(ReportComplainActivity.sessionUser, session);
                inten.putExtra(ReportComplainActivity.sessionCustId, CustId);
                inten.putExtra(ReportComplainActivity.sessionCustName, CustomerName);
                startActivityForResult(inten, 3);
            }
        });
        btnReportBi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inten = new Intent(ReportMain.this, ReportBIActivity.class);//berpindah ke activity yang lain dgn data
                inten.putExtra(ReportBIActivity.sessionUser, session);
                inten.putExtra(ReportBIActivity.sessionCustId, CustId);
                inten.putExtra(ReportBIActivity.sessionCustName, CustomerName);
                startActivityForResult(inten, 4);
            }
        });
        btnReportSample.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inten = new Intent(ReportMain.this, ReportSampleActivity.class);
                inten.putExtra(ReportSampleActivity.sessionUser, session);
                inten.putExtra(ReportSampleActivity.sessionCustId, CustId);
                inten.putExtra(ReportSampleActivity.sessionCustName, CustomerName);
                startActivity(inten);
            }
        });
        btnReportDemo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inten = new Intent(ReportMain.this, ReportDemoActivity.class);
                inten.putExtra(ReportDemoActivity.sessionUser, session);
                inten.putExtra(ReportDemoActivity.sessionCustId, CustId);
                inten.putExtra(ReportDemoActivity.sessionCustName, CustomerName);
                startActivity(inten);
            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
       // getMenuInflater().inflate(R.menu.menu_refresh, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == android.R.id.home) {
            //getFragmentManager().popBackStack();
            finish();
        }else if (id == R.id.menuRefresh) {

            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
