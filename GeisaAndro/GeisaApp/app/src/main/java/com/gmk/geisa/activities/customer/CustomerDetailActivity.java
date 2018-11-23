package com.gmk.geisa.activities.customer;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.gmk.geisa.Class.Konversi;
import com.gmk.geisa.R;
import com.gmk.geisa.activities.report.ReportMain;
import com.gmk.geisa.adapter.MapDialogFragmentNewMap;
import com.gmk.geisa.controller.CustomerController;
import com.gmk.geisa.controller.SessionController;
import com.gmk.geisa.fragment.CustomerDistributorFragment;
import com.gmk.geisa.helper.Constants;
import com.gmk.geisa.model.mCallPlan;
import com.gmk.geisa.model.mCustomer;
import com.gmk.geisa.model.mCustomerAndDistBranch;
import com.gmk.geisa.model.mSession;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CustomerDetailActivity extends AppCompatActivity implements MapDialogFragmentNewMap.EditDialogListener {
    public final static String sessioncustomer = "sesicustomer";
    //public final static String sessionOutletId = "sessionOutletId";
    public final static String sessionCallPlan = "sessionCallPlan";
    private boolean isCallPlan = false;
    private mCustomer customer = new mCustomer();
    private mSession session;
    private mCallPlan callplan;
    private SessionController sessionController;
    CustomerController customerController;
    private String idOutlet = "", SalesId = "";
    private Konversi konversi = new Konversi();
    private String Latitude, Longitude;
    private ProgressDialog pDialog;
    Button btnCstCekin, btnCstMap;
    TextView cstNama, cstNamaAlias, cstIdGeisa, cstLevel, cstChannel, cstArea, cstZone, cstStatusMember,
            cstLocation, cstPIC, cstPICJabatan, cstEmail, cstTelp, cstHP, cstAlamat, cstWebsite,
            cstJoinDate, cstStatusPKP, cstLimit, cstTOP, cstNPWP, cstGroupPrice, cstGroupDiskon,
            cstDistributorName, cstDistributorCustomerCode, cstDistributorArea, cstDistributorPic, cstDistributorEmail,
            cstDistributorTelp, cstDistributorAddress;
    FloatingActionButton fab;

    //add page
    TabLayout mTabLayout;
    ViewPager mViewPager;
    FragmentAdapter mPagerAdapter;
    List<String> tabItems = new ArrayList<>();
    ArrayList<mCustomerAndDistBranch> customerAndDistBranches = new ArrayList<>();
    boolean cancelStartStop = false;
    int countwork;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//lock screen orientation
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mTabLayout = (TabLayout) findViewById(R.id.tabs);


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
        cstHP = (TextView) findViewById(R.id.cstHP);
        cstAlamat = (TextView) findViewById(R.id.cstAlamat);
        cstWebsite = (TextView) findViewById(R.id.cstWebsite);
        cstJoinDate = (TextView) findViewById(R.id.cstJoinDate);
        cstStatusPKP = (TextView) findViewById(R.id.cstStatusPKP);
        cstLimit = (TextView) findViewById(R.id.cstLimit);
        cstTOP = (TextView) findViewById(R.id.cstTOP);
        cstNPWP = (TextView) findViewById(R.id.cstNPWP);
        //distributor and price
        cstGroupPrice = (TextView) findViewById(R.id.cstGroupPrice);
        cstGroupDiskon = (TextView) findViewById(R.id.cstGroupDiskon);
        cstDistributorName = (TextView) findViewById(R.id.cstDistributorName);
        cstDistributorCustomerCode = (TextView) findViewById(R.id.cstDistributorCustomerCode);
        cstDistributorArea = (TextView) findViewById(R.id.cstDistributorArea);
        cstDistributorPic = (TextView) findViewById(R.id.cstDistributorPic);
        cstDistributorEmail = (TextView) findViewById(R.id.cstDistributorEmail);
        cstDistributorTelp = (TextView) findViewById(R.id.cstDistributorTelp);
        cstDistributorAddress = (TextView) findViewById(R.id.cstDistributorAddress);
        fab=(FloatingActionButton) findViewById(R.id.fab);

        customerController = CustomerController.getInstance(this);
        customerController.setCallBackSyncCustomer(callBackSync);
        sessionController = SessionController.getInstance(this);
        session = sessionController.getSession("login", 1);
        if (session != null)
            SalesId = String.valueOf(session.getId());
        if (getIntent().getSerializableExtra(sessioncustomer) != null) {
            customer = (mCustomer) getIntent().getSerializableExtra(sessioncustomer);
            idOutlet = String.valueOf(customer.getCustId());
            // Log.e("isi id customer",callPlanId);
        }
        if (getIntent().getSerializableExtra(sessionCallPlan) != null) {
            callplan = (mCallPlan) getIntent().getSerializableExtra(sessionCallPlan);
            idOutlet = String.valueOf(callplan.getCustId());
            isCallPlan = true;
            // Log.e("isi id callplan",callPlanId+","+isCallPlan);
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
                if(countwork<1) {
                    showEditDialog("checkIn");
                }
                countwork++;
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inten = new Intent(CustomerDetailActivity.this, ReportMain.class);//berpindah ke activity yang lain dgn data
                inten.putExtra(ReportMain.sessionCust, customer);
                inten.putExtra(ReportMain.sessionUser, session);
                startActivity(inten);
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        //Calendar cal = Calendar.getInstance();
        //Log.e(" jam ", " jam" + cal.getTime());
        addData();
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
        return true;
    }

    private void addData() {
        customer = customerController.getCustomerByCustIdAndSalesId(idOutlet, SalesId);
        Log.e("refresh data", "gt " + customer.getCustId() + "," + idOutlet + "," + session.getId());
        //set value transaksi
        idOutlet = String.valueOf(customer.getCustId());
        cstNama.setText(customer.getCustName());
        cstNamaAlias.setText(customer.getAliasName());
        cstIdGeisa.setText(String.valueOf(customer.getCustId()));
        cstLevel.setText(customer.getCustLevelId() + " - " + customer.getCustLevelName());
        cstChannel.setText(customer.getChannelName());
        cstArea.setText(customer.getAreaName());
        cstZone.setText(customer.getCustZoneId() + " - " + customer.getCustZoneName());
        cstStatusMember.setText(customer.getCustStatusName());


        //set value detail
        Latitude = String.valueOf(customer.getLat());
        Longitude = String.valueOf(customer.getLng());
        cstLocation.setText(konversi.DataToString(Latitude) + "," + konversi.DataToString(Longitude) + "(" + customer.getRadius() + ")");
        cstPIC.setText(customer.getPic());
        cstPICJabatan.setText(customer.getPicJabatan());
        cstEmail.setText(customer.getEmail());
        cstTelp.setText(customer.getTelp());
        cstHP.setText(customer.getHp());
        cstAlamat.setText(customer.getAddress());
        cstWebsite.setText(customer.getWebsite());

        cstJoinDate.setText(customer.getJoinDate());
        cstStatusPKP.setText(customer.getStsPkpId() + " - " + customer.getStsPkpName());
        cstLimit.setText(customer.getCreditLimit());
        cstTOP.setText(customer.getTop());
        cstNPWP.setText(customer.getNpwp());

        adddistfragement();
    }

    private void adddistfragement() {
        customerAndDistBranches.clear();
        tabItems.clear();
        mTabLayout.removeAllTabs();
        mViewPager.removeAllViews();
        mPagerAdapter = new FragmentAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mPagerAdapter);
        mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        if (null != customer.getCustomerAndBranch() && customer.getCustomerAndBranch().size() > 0) {

            for (int i = 0; i < customer.getCustomerAndBranch().size(); i++) {
                mCustomerAndDistBranch cs = customer.getCustomerAndBranch().get(i);
                customerAndDistBranches.add(cs);
                addTab("Distributor " + (i + 1));
                mPagerAdapter.notifyDataSetChanged();
                //Log.e("isi fragment","gt "+i);
            }


        } else {

            mCustomerAndDistBranch br = new mCustomerAndDistBranch();
            br.setDistBranchName("Need To Add");
            br.setDiscGroupId("Need To Add");
            br.setDiscGroupName("");
            br.setPriceGroupId("Need To Add");
            br.setPriceGroupName("");
            customerAndDistBranches.add(br);
            addTab("Empty Distributor List");
        }

        mTabLayout.setupWithViewPager(mViewPager);
    }


    //untuk memantau tombol back
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            //getFragmentManager().popBackStack();
            finish();
        } else if (item.getItemId() == R.id.menuAddCst) {//save customer
            Toast.makeText(getApplicationContext(), "Add New Outlet", Toast.LENGTH_SHORT).show();
            Intent inten = new Intent(getApplicationContext(), CustomerAddActivity.class);//berpindah ke activity yang lain dgn data
            inten.putExtra(CustomerAddActivity.newOutlet, true);
            inten.putExtra(CustomerAddActivity.idOutlet, idOutlet);
            inten.putExtra(CustomerAddActivity.sessionCust, customer);
            inten.putExtra(CustomerAddActivity.sessionUser, session);
            startActivityForResult(inten, 11);
            return true;
        } else if (item.getItemId() == R.id.menuAddDist) {
            Toast.makeText(getApplicationContext(), "Add Cust Dist", Toast.LENGTH_SHORT).show();
            Intent inten = new Intent(getApplicationContext(), CustomerAddDistributorActivity.class);//berpindah ke activity yang lain dgn data
            inten.putExtra(CustomerAddDistributorActivity.sessionUser, session);
            inten.putExtra(CustomerAddActivity.idOutlet, idOutlet);
            inten.putExtra(CustomerAddActivity.sessionCust, customer);
            startActivityForResult(inten, 12);
            return true;
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
                } else {
                    Toast.makeText(getApplicationContext(), "ERROR ON REFRESH CUSTOMER...", Toast.LENGTH_SHORT).show();
                }


            }
            addData();
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

    //fragmnet tab
    private void addTab(String title) {
        mTabLayout.addTab(mTabLayout.newTab().setText(title));
        addTabPage(title);
    }

    public void addTabPage(String title) {
        tabItems.add(title);
        mPagerAdapter.notifyDataSetChanged();
    }



    class FragmentAdapter extends FragmentStatePagerAdapter {


        public FragmentAdapter(FragmentManager fm) {
            super(fm);

        }

        @Override
        public Fragment getItem(int position) {
            // Log.e("isi gfrag",customerAndDistBranches.get(position).getDistBranchName()+","+position);
            return CustomerDistributorFragment.newInstance(customerAndDistBranches.get(position));
        }


        @Override
        public int getCount() {
            return tabItems.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabItems.get(position);
        }

        @Override
        public Parcelable saveState() {
            return null;
        }

    }

    private void showEditDialog(String jenis) {
        // SupportMapFragment
        FragmentManager fm = getSupportFragmentManager();
        MapDialogFragmentNewMap df = new MapDialogFragmentNewMap(CustomerDetailActivity.this, jenis, customer, callplan);
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
        countwork=0;
        btnCstCekin.setEnabled(true);
    }

}
