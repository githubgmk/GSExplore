package com.gmk.geisa.activities.callplan;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.astuetz.PagerSlidingTabStrip;
import com.gmk.geisa.R;
import com.gmk.geisa.activities.callplan.visit.VisitCallPlan;
import com.gmk.geisa.controller.SessionController;
import com.gmk.geisa.fragment.tab.DynamicTabPageAdapter;
import com.gmk.geisa.adapter.ViewPagerTab;
import com.gmk.geisa.controller.CallPlanController;
import com.gmk.geisa.fragment.CallPlanTabViewFragment;
import com.gmk.geisa.model.mCallPlan;
import com.gmk.geisa.model.mSession;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class CallPlanActivity extends AppCompatActivity {
    //private TabLayout tabs;
    private ViewPager pager;
    private PagerSlidingTabStrip tabs;
    private DynamicTabPageAdapter adapters;
    CallPlanController callPlanController;
    SessionController sessionController;
    final ArrayList<ArrayList<mCallPlan>> plan=new ArrayList<>();
    private ProgressDialog pDialog;
    public final static String sessionUser = "sessionLogin";
    private mSession session;
    int posisiTab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_plan);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Call Plan");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        //determinePaneLayout();
        pager = (ViewPager) findViewById(R.id.pager);
        tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        sessionController = SessionController.getInstance(this);
        callPlanController = CallPlanController.getInstance(this);
        callPlanController.setCallBackUpdateCallPlan(callBackSync);
        if (getIntent().getSerializableExtra(sessionUser) != null) {
            session = (mSession) getIntent().getSerializableExtra(sessionUser);
        }
        cekUpdateFromServer();


    }
    CallPlanController.CallBackUpdateCallPlan callBackSync = new CallPlanController.CallBackUpdateCallPlan() {
        @Override
        public void resultReady(ArrayList<mCallPlan> callPlan, boolean hasil) {
            if (pDialog != null)
                pDialog.dismiss();
            if (hasil) {
                Toast.makeText(getApplicationContext(), "DONE SYNC", Toast.LENGTH_SHORT).show();
                CreateTabs();
            }else{
                CreateTabs();
                Toast.makeText(getApplicationContext(), "ERROR ON SYNC CallPlan...", Toast.LENGTH_SHORT).show();
            }

        }
    };



    //crete menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_customer, menu);
        MenuItem item = menu.findItem(R.id.menuAddCst);
        item.setTitle("Draft CallPlan");
        item.setIcon(android.R.drawable.ic_menu_add);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            //getFragmentManager().popBackStack();
            finish();
        } else if (item.getItemId() == R.id.menuAddCst) {
            Intent inten = new Intent(getApplicationContext(), CallPlanDraftActivity.class);//berpindah ke activity yang lain dgn data
            inten.putExtra(CallPlanDraftActivity.sessionUser, session);
            startActivityForResult(inten, 16);
            return true;
        } else if (item.getItemId() == R.id.menuRefresh) {
            if (pDialog == null)
                pDialog = new ProgressDialog(this);
            pDialog.setMessage("Getting Data From Server ...\n Please Wait...");
            pDialog.show();

            if (null != session) {

                callPlanController.checkNewUpdateAllCallPlanFromServer(String.valueOf(session.getId()));
            } else {
                pDialog.dismiss();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    //add tabs
    @Override
    protected void onResume() {
        super.onResume();
        if(CekCallPlan()){
            //Log.e("ada callplan","ada call plan");
            Intent inten = new Intent(CallPlanActivity.this, VisitCallPlan.class);
            startActivityForResult(inten, 7);
            //finish();
        }else{
            CreateTabs();
        }
    }
    private boolean CekCallPlan(){
        mSession session = sessionController.getSession("CallPlan", 1); //nilai4 ambil id callplan;
        if (null != session) {
            return  true;
        }else{
            return false;
        }
    }

    private void  cekUpdateFromServer(){
        if (pDialog == null)
            pDialog = new ProgressDialog(this);
        pDialog.setMessage("Getting Data From Server ...\n Please Wait...");
        pDialog.show();
        pDialog.setCancelable(true);
        if (null != session) {
            callPlanController.checkNewUpdateAllCallPlanFromServer(String.valueOf(session.getId()));
        } else {
            pDialog.dismiss();
        }
       // CreateTabs();

    }

    private void CreateTabs() {
        ArrayList<ViewPagerTab> tabsList = new ArrayList<>();
        //if you want dynamic
        ArrayList<CallPlanTabViewFragment> tabFragment = new ArrayList<>();
        //ArrayList<mCallPlan> planDraft=new ArrayList<>();
        plan.clear();
        for (int i = 0; i < 5; i++) {
            Calendar tgl = Calendar.getInstance();
            tgl.add(Calendar.DAY_OF_MONTH, i);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

            //nanti diganti lagi dengan yang bener
            ArrayList<mCallPlan> cp=callPlanController.getCallPlanByDate(String.valueOf(session.getId()), format.format(tgl.getTime()));
            plan.add(cp);//callplan
            //Log.e("isi call",cp.size()+" ada,"+format.format(tgl.getTime())+","+session.getId());
            tabFragment.add(CallPlanTabViewFragment.newInstant(cp,i));//add from database for data
            tabsList.add(new ViewPagerTab(format.format(tgl.getTime()), plan.get(i).size()));
        }


        adapters = new DynamicTabPageAdapter(getSupportFragmentManager(), tabsList, tabFragment);
        if(pager!=null) {
            pager.setAdapter(adapters);
            if (tabs != null)
                tabs.setViewPager(pager);
        }
        tabs.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
               // Log.e("get posisi",position+"x");
                posisiTab=position;

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        if (pager != null) {
            pager=null;
        }
        super.onDestroy();

    }


}
