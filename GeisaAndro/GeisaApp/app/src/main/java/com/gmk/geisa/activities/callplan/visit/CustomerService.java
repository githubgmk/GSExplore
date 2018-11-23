package com.gmk.geisa.activities.callplan.visit;

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
import com.gmk.geisa.model.mSession;

public class CustomerService extends AppCompatActivity  {
    public final static String sessionCustName="sessionCustName";
    // Remove the below line after defining your own ad unit ID.
    TextView custName;
    SessionController sessionController;
    String idCallPlan,SalesId,idCustomer="0";
    Button btnCsComplain, btnCsDemo, btnCsSosialisasi,btnVisitBi,btnVisitEx,btnVisitHi;
    private mSession sessionUser;
    private String CustomerNama;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visit_customer_service);
        setTitle("Visit>>Customer Service");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        custName=(TextView) findViewById(R.id.custName); 
        btnCsComplain =(Button) findViewById(R.id.btnCsComplain);
        btnCsDemo =(Button) findViewById(R.id.btnCsDemo);
        btnCsSosialisasi =(Button) findViewById(R.id.btnCsSosialisasi);
        btnVisitBi=(Button) findViewById(R.id.btnVisitBi);//belum butuh tp jgn dihapus
        btnVisitEx=(Button) findViewById(R.id.btnVisitEx);//belum butuh tp jgn dihapus
        btnVisitHi=(Button) findViewById(R.id.btnVisitHi);//belum butuh tp jgn dihapus
        sessionController = SessionController.getInstance(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sessionUser = sessionController.getSession("login", 1);
        if(sessionUser!=null){
            SalesId=String.valueOf(sessionUser.getId());
        }
        mSession session = sessionController.getSession("CallPlan", 1); //nilai4 ambil id callplan;
        if (session != null) {
            idCallPlan = session.getNilai4();
            idCustomer= session.getNilai5();
            //Log.e("idi callpan","id"+idCallPlan);
        }
        if (getIntent().getSerializableExtra(sessionCustName) != null) {
            CustomerNama = (String) getIntent().getSerializableExtra(sessionCustName);
            custName.setText(CustomerNama);
        }
        btnCsComplain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inten = new Intent(CustomerService.this, ComplainActivity.class);//berpindah ke activity yang lain dgn data
                inten.putExtra(ComplainActivity.callPlanId,idCallPlan);
                inten.putExtra(ComplainActivity.customerId,Integer.parseInt(idCustomer));
                inten.putExtra(ComplainActivity.sessionUser,sessionUser);
                startActivityForResult(inten, 1);
            }
        });
        btnCsDemo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inten = new Intent(CustomerService.this, DemoActivity.class);
                inten.putExtra(DemoActivity.callPlanId,idCallPlan);
                inten.putExtra(DemoActivity.customerId,idCustomer);
                inten.putExtra(DemoActivity.sessionUser,sessionUser);
                startActivityForResult(inten, 3);
            }
        });
        btnCsSosialisasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        btnVisitBi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        btnVisitEx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        btnVisitHi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
       // getMenuInflater().inflate(R.menu.menu_submit, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        if (item.getItemId() == android.R.id.home) {

            finish();
        }
        return super.onOptionsItemSelected(item);
    }



}
