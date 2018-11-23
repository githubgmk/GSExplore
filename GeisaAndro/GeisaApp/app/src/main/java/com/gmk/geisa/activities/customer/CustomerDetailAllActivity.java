package com.gmk.geisa.activities.customer;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.astuetz.PagerSlidingTabStrip;
import com.gmk.geisa.R;
import com.gmk.geisa.activities.callplan.CallPlanDraftNewActivity;
import com.gmk.geisa.activities.report.ReportMain;
import com.gmk.geisa.adapter.MapDialogFragment;
import com.gmk.geisa.adapter.MapDialogFragmentNewMap;
import com.gmk.geisa.adapter.ViewPagerTab;
import com.gmk.geisa.adapter.ViewPagerTabSingle;
import com.gmk.geisa.controller.CallPlanController;
import com.gmk.geisa.controller.CustomerController;
import com.gmk.geisa.controller.SessionController;
import com.gmk.geisa.fragment.CallPlanDraftTabViewFragment;
import com.gmk.geisa.fragment.tab.CustomerDetailTabPageAdapter;
import com.gmk.geisa.fragment.tab.DynamicTabDraftPageAdapter;
import com.gmk.geisa.helper.Constants;
import com.gmk.geisa.model.mCallPlan;
import com.gmk.geisa.model.mCustomer;
import com.gmk.geisa.model.mCustomerClasification;
import com.gmk.geisa.model.mSession;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class CustomerDetailAllActivity extends AppCompatActivity implements MapDialogFragmentNewMap.EditDialogListener {
    public final static String sessionUser = "sessionuser";
    public final static String sessioncustomer = "sesicustomer";
    public final static String sessionCallPlan = "sessionCallPlan";
    private mSession session;
    private mCustomer customer = new mCustomer();
    private mCustomerClasification clasification = new mCustomerClasification();
    private CustomerController customerController;
    private mCallPlan callplan;
    private String idOutlet = "", SalesId = "";
    private SessionController sessionController;
    CallPlanController callPlanController;
    private ProgressDialog pDialog;
    int posisi;
    TextView cstNama, cstNamaAlias;
    Button btnCstCekin;
    final ArrayList<ArrayList<mCallPlan>> plan = new ArrayList<>();
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    Calendar nows = Calendar.getInstance();
    //add page
    private ViewPager pager;
    private PagerSlidingTabStrip tabs;
    private CustomerDetailTabPageAdapter adapters;
    Menu menu;
    int stateMenu;
    int countwork;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_detail_all);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Customer Detail");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        pager = (ViewPager) findViewById(R.id.pager);
        tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        btnCstCekin = (Button) findViewById(R.id.btnCstCekIn);
        cstNama = (TextView) findViewById(R.id.cstNama);
        cstNamaAlias = (TextView) findViewById(R.id.cstNamaAlias);
        sessionController = SessionController.getInstance(this);
        fab = (FloatingActionButton) findViewById(R.id.fab);

        customerController = CustomerController.getInstance(this);
        customerController.setCallBackSyncCustomer(callBackSync);
        customerController.setCallBackSyncCustomerClasification(callBackSyncClasification);

        if (getIntent().getSerializableExtra(sessioncustomer) != null) {
            customer = (mCustomer) getIntent().getSerializableExtra(sessioncustomer);
            idOutlet = String.valueOf(customer.getCustId());
            cstNama.setText(customer.getCustName());
            cstNamaAlias.setText(customer.get_Id() + " - " + customer.getAliasName());
            clasification.setCustId(customer.getCustId());
            // Log.e("isi id customer",callPlanId);
        }

        session = sessionController.getSession("login", 1);
        if (session != null)
            SalesId = String.valueOf(session.getId());
        if (getIntent().getSerializableExtra(sessionCallPlan) != null) {

            callplan = (mCallPlan) getIntent().getSerializableExtra(sessionCallPlan);
            if (callplan != null) {
                idOutlet = String.valueOf(callplan.getCustId());
                //customer=customerController.getCustomerByCustIdAndSalesId(idOutlet,SalesId);
                customer = customerController.getCustomerByCustIdAndSalesId(idOutlet, SalesId);
                cstNama.setText(customer.getCustName());
                cstNamaAlias.setText(customer.get_Id() + " - " + customer.getAliasName());
                clasification.setCustId(customer.getCustId());
            }

             //Log.e("isi id callplan",idOutlet+","+callplan.getCallPlanTypeName()+","+callplan.getCustId());
            if (Constants.cekTanggalHariCallPlan(callplan.getCallPlanDate())) {
                btnCstCekin.setVisibility(View.VISIBLE);
            } else {
                btnCstCekin.setVisibility(View.INVISIBLE);
            }
            setTitle("CallPlan Detail");
        } else {
            setTitle("Customer Detail");
        }
        btnCstCekin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnCstCekin.setEnabled(false);
                if (countwork < 1) {
                    showEditDialog("checkIn");
                }
                countwork++;
            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inten = new Intent(CustomerDetailAllActivity.this, ReportMain.class);
                inten.putExtra(ReportMain.sessionCust, customer);
                inten.putExtra(ReportMain.sessionUser, session);
                startActivity(inten);
            }
        });
        updateCustomerClasification();
    }

    @Override
    protected void onResume() {
        super.onResume();
        CreateTabs();

    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
        if (pager != null) {
            pager = null;
        }
        //callPlanController=null;
        super.onDestroy();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == 12) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                if (null != data.getSerializableExtra(sessioncustomer)) {
                    customer = (mCustomer) data.getSerializableExtra(sessioncustomer);
                    idOutlet = String.valueOf(customer.getCustId());
                    SalesId = String.valueOf(customer.getSalesmanId());
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_customer_detail, menu);
        this.menu = menu;
        return true;
    }


    //untuk memantau tombol back
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            //getFragmentManager().popBackStack();
            finish();
        } else if (item.getItemId() == R.id.menuAddCst) {//save customer
            Toast.makeText(getApplicationContext(), "Add New", Toast.LENGTH_SHORT).show();
            Intent inten = new Intent(getApplicationContext(), CustomerAddActivity.class);//berpindah ke activity yang lain dgn data
            inten.putExtra(CustomerAddActivity.newOutlet, true);
            inten.putExtra(CustomerAddActivity.idOutlet, idOutlet);
            inten.putExtra(CustomerAddActivity.sessionCust, customer);
            inten.putExtra(CustomerAddActivity.sessionUser, session);
            startActivityForResult(inten, 11);
            return true;
        }else if(item.getItemId()==R.id.menuAddDist){
            Toast.makeText(getApplicationContext(), "Add Cust Dist", Toast.LENGTH_SHORT).show();
            Intent inten = new Intent(getApplicationContext(), CustomerAddDistributorActivity.class);//berpindah ke activity yang lain dgn data
            inten.putExtra(CustomerAddDistributorActivity.sessionUser, session);
            inten.putExtra(CustomerAddActivity.idOutlet, idOutlet);
            inten.putExtra(CustomerAddActivity.sessionCust, customer);
            startActivityForResult(inten, 12);
        } else if (item.getItemId() == R.id.menuRefresh) {
            Toast.makeText(getApplicationContext(), "Refresh", Toast.LENGTH_SHORT).show();
            updateCustomerDetail();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    CustomerController.CallBackSyncCustomer callBackSync = new CustomerController.CallBackSyncCustomer() {
        @Override
        public void resultReady(mCustomer customers, mCustomer oldcust) {
            if (pDialog != null)
                pDialog.dismiss();
            if (null != customers) {
                if (customerController.addCustomerToLocal(customers, oldcust)) {
                    //finish();
                    Toast.makeText(getApplicationContext(), "DONE REFRESH", Toast.LENGTH_SHORT).show();
                    idOutlet = String.valueOf(customers.getCustId());
                    customer = customerController.getCustomerByCustIdAndSalesId(idOutlet, SalesId);
                    CreateTabs();
                } else {
                    Toast.makeText(getApplicationContext(), "ERROR ON REFRESH CUSTOMER...", Toast.LENGTH_SHORT).show();
                }


            }
        }
    };

    private void updateCustomerDetail() {
        if (pDialog == null)
            pDialog = new ProgressDialog(this);
        pDialog.setMessage("Sync Customer Data To Server ...\n Please Wait...");
        pDialog.show();
        if (session != null) {
            if (null != customer) {
                customerController.syncCustomer(customer, String.valueOf(session.getId()));
            } else {
                pDialog.dismiss();
            }
        } else {
            pDialog.dismiss();
        }
    }

    private void updateCustomerClasification() {
        if (pDialog == null)
            pDialog = new ProgressDialog(this);
        pDialog.setMessage("Get Customer Clasification ...\n Please Wait...");
        pDialog.show();
        if (session != null) {
            if (null != customer) {
                customerController.syncCustomerClasification(clasification);
            } else {
                pDialog.dismiss();
            }
        } else {
            pDialog.dismiss();
        }
    }

    CustomerController.CallBackSyncCustomerClasification callBackSyncClasification = new CustomerController.CallBackSyncCustomerClasification() {
        @Override
        public void resultReady(mCustomerClasification clasificationnew, mCustomerClasification clasificationold) {
            if (pDialog != null)
                pDialog.dismiss();
            if (null != clasificationnew) {
                clasification = clasificationnew;
                CreateTabs();
            }
        }
    };


    private void changeMenuText(int posisi) {
        if (null != menu) {
            MenuItem item = menu.findItem(R.id.menuAddCst);
            if (posisi == 0) {
                stateMenu = 0;
                item.setTitle("Add New Plan");
            } else {
                stateMenu = 1;
                item.setTitle("Add New Template");
            }
        }
    }


    private void CreateTabs() {
        adapters = new CustomerDetailTabPageAdapter(getSupportFragmentManager(), customer, clasification);
        pager.setAdapter(adapters);
        tabs.setAllCaps(false);
        tabs.setViewPager(pager);

    }

    private void showEditDialog(String jenis) {
        // SupportMapFragment
        FragmentManager fm = getSupportFragmentManager();
        MapDialogFragmentNewMap df = new MapDialogFragmentNewMap(CustomerDetailAllActivity.this, jenis, customer, callplan);
        df.show(fm, "fragment tab");

    }

    @Override
    public void updateResult(String inputText) {
        if (!TextUtils.isEmpty(inputText)) {
            if (inputText.equalsIgnoreCase("true")) {
                // Toast.makeText(getApplicationContext(),"keluar juga",Toast.LENGTH_SHORT).show();

                //nanti tambahak kirim data keserver
                // perintah.work();
                // perintah.laporan1("1");
            } else {
                // Toast.makeText(getApplicationContext(),"keluar juga tapi salah",Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getApplicationContext(), "Error ", Toast.LENGTH_SHORT).show();
        }
        countwork = 0;
        btnCstCekin.setEnabled(true);
    }

}
