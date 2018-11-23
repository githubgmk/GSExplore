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
import com.gmk.geisa.activities.customer.CustomerAddActivity;
import com.gmk.geisa.controller.CustomerController;
import com.gmk.geisa.controller.SessionController;
import com.gmk.geisa.model.mCustomer;
import com.gmk.geisa.model.mSession;

public class Expansi extends AppCompatActivity  {
    // Remove the below line after defining your own ad unit ID.
TextView custName;
    SessionController sessionController;
    CustomerController customerController;
    String idCallPlan,idCustomer="0",SalesId;
    Button btnExNoo, btnExSample, btnCsSosialisasi,btnVisitBi,btnVisitEx,btnVisitHi;
    private mSession sessionUser;
    mCustomer customer=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visit_expansi);
        setTitle("Visit>>Expansi");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        custName=(TextView) findViewById(R.id.custName);
        btnExNoo =(Button) findViewById(R.id.btnExNoo);
        btnExSample =(Button) findViewById(R.id.btnExSample);
        btnCsSosialisasi =(Button) findViewById(R.id.btnCsSosialisasi);//belum butuh tp jgn dihapus
        btnVisitBi=(Button) findViewById(R.id.btnVisitBi);//belum butuh tp jgn dihapus
        btnVisitEx=(Button) findViewById(R.id.btnVisitEx);//belum butuh tp jgn dihapus
        btnVisitHi=(Button) findViewById(R.id.btnVisitHi);//belum butuh tp jgn dihapus
        sessionController = SessionController.getInstance(this);
        customerController=CustomerController.getInstance(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sessionUser = sessionController.getSession("login", 1);
        if(sessionUser!=null){
            SalesId=String.valueOf(sessionUser.getId());

        }
        mSession session = sessionController.getSession("CallPlan", 1); //nilai4 ambil id callplan;
        if (session != null) {
            idCallPlan = session.getNilai4();
            idCustomer=session.getNilai5();
            customer=customerController.getCustomerByCustIdAndSalesId(idCustomer,SalesId);
            custName.setText(customer.getCustName());
            //Log.e("idi cust","id"+customer.getCustName());
        }
        btnExNoo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inten = new Intent(Expansi.this, CustomerAddActivity.class);//berpindah ke activity yang lain dgn data

                inten.putExtra(CustomerAddActivity.newOutlet, true);
                inten.putExtra(CustomerAddActivity.idOutlet, idCustomer);
                inten.putExtra(CustomerAddActivity.sessionCust, customer);
                inten.putExtra(CustomerAddActivity.sessionUser,sessionUser);
                startActivityForResult(inten, 1);
            }
        });
        btnExSample.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inten = new Intent(Expansi.this, SampleActivity.class);//berpindah ke activity yang lain dgn data
                inten.putExtra(SampleActivity.callPlanId,idCallPlan);
                inten.putExtra(SampleActivity.customerId, idCustomer);
                inten.putExtra(SampleActivity.sessionUser,sessionUser);
                startActivityForResult(inten, 5);
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
