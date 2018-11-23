package com.gmk.geisa.activities.callplan;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.astuetz.PagerSlidingTabStrip;
import com.gmk.geisa.R;
import com.gmk.geisa.fragment.tab.DynamicTabDraftPageAdapter;
import com.gmk.geisa.adapter.ViewPagerTab;
import com.gmk.geisa.controller.CallPlanController;
import com.gmk.geisa.fragment.CallPlanDraftTabViewFragment;
import com.gmk.geisa.model.mCallPlan;
import com.gmk.geisa.model.mSession;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class CallPlanDraftActivity extends AppCompatActivity {
    public final static String sessionUser="sessionuser";
    private mSession session;
    CallPlanController callPlanController;
    private ProgressDialog pDialog;
    int posisi;
    TextView updateNilai;
    final ArrayList<ArrayList<mCallPlan>> plan=new ArrayList<>();
    ArrayList<mCallPlan> callPlen;
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    Calendar nows = Calendar.getInstance();
    //add page
    private ViewPager pager;
    private PagerSlidingTabStrip tabs;
    private DynamicTabDraftPageAdapter adapters;
    Menu menu;
    int stateMenu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_plan_draft);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Draft Call Plan");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        pager = (ViewPager) findViewById(R.id.pager);
        tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        updateNilai=(TextView) findViewById(R.id.updateNilai);
        callPlanController=CallPlanController.getInstance(this);
        callPlanController.setCallBackGetDataDraft(callBackSync);


        if (getIntent().getSerializableExtra(sessionUser) != null) {
            session = (mSession) getIntent().getSerializableExtra(sessionUser);
        }

        tabs.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                changeMenuText(position);
            }

            @Override
            public void onPageSelected(int position) {
                posisi=position;
                updateNilai.setText(String.valueOf(plan.get(posisi).size()));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });




    }
    @Override
    protected   void onResume(){
        super.onResume();
        CreateTabs();
        updateNilai.setText(String.valueOf(plan.get(0).size()));
    }
    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        if(pDialog!=null){pDialog.dismiss(); pDialog=null;}
        callPlanController=null;
        super.onDestroy();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_customer, menu);
        this.menu=menu;
        MenuItem item = menu.findItem(R.id.menuAddCst);
        item.setTitle("Add New Plan");
        item.setIcon(android.R.drawable.ic_menu_add);
        MenuItem item1 = menu.findItem(R.id.menuRefresh);
        item1.setTitle("Submit");
        item1.setIcon(R.drawable.ic_submit);
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
            Intent inten = new Intent(getApplicationContext(), CallPlanDraftNewActivity.class);//berpindah ke activity yang lain dgn data
            inten.putExtra(CallPlanDraftNewActivity.sessionPlan, session);
            inten.putExtra(CallPlanDraftNewActivity.sessionTypePlan, stateMenu);
            inten.putExtra(CallPlanDraftNewActivity.sessionUser, session);
            startActivityForResult(inten, 11);
            return true;
        } else if (item.getItemId() == R.id.menuRefresh) {
            Toast.makeText(getApplicationContext(), "Submit Data", Toast.LENGTH_SHORT).show();
            updateDraftCallPlan();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    CallPlanController.CallBackGetDataDraft callBackSync = new CallPlanController.CallBackGetDataDraft() {
        @Override
        public void resultReady(ArrayList<mCallPlan> callPlan, boolean hasil) {
           // Log.e("hasil","hasilnya"+hasil);
            if (pDialog != null)
                pDialog.dismiss();
            if (hasil) {
                if(callPlanController.deleteCallPlanDraft(callPlen)) {
                    Toast.makeText(getApplicationContext(), "DONE Submit", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(), "ERROR ON Submit CallPlan...", Toast.LENGTH_SHORT).show();
                }
                CreateTabs();
            }else{
                CreateTabs();
                Toast.makeText(getApplicationContext(), "ERROR ON Submit CallPlan...", Toast.LENGTH_SHORT).show();
            }

        }
    };

    private void changeMenuText(int posisi){
        if(null!=menu) {
            MenuItem item = menu.findItem(R.id.menuAddCst);
            if (posisi == 0) {
                stateMenu=0;
                item.setTitle("Add New Plan");
            } else {
                stateMenu=1;
                item.setTitle("Add New Template");
            }
        }
    }

    private void updateDraftCallPlan() {
        if (pDialog == null)
            pDialog = new ProgressDialog(this);
        pDialog.setMessage("Send Call Plan Data To Server ...\n Please Wait...");
        pDialog.show();
        if(session!=null) {
            if(null!=adapters) {
              // TextView tes =(TextView) tabs.getChildAt(posisi).findViewById(R.id.badge);
             //   Log.e("isi tab", String.valueOf(tes.getText()));
                ArrayList<mCallPlan> cp=adapters.getAllItem(posisi);
                callPlen=new ArrayList<>();
                ArrayList<mCallPlan> callPlen1=new ArrayList<>();
                for(mCallPlan c:cp){
                    if(!c.isSelected()){
                       // Log.e("ss",c.getCallPlanDate()+","+c.getCustomerName()+","+c.isSelected()+","+c.getModifiedDate()+","+c.getModifiedBy());
                        c.setModifiedBy(String.valueOf(session.getId()));
                        c.setModifiedDate(format.format(nows.getTime()));
                        c.setCallPlanStatusId(1);
                        callPlen.add(c);
                    }else{
                       // Log.e("xx",c.getCallPlanDate()+","+c.getCustomerName()+","+c.isSelected()+","+c.getModifiedDate()+","+c.getModifiedBy());
                        callPlen1.add(c);
                    }
                }
                if(cp.size()>0) {
                    //Log.e(" callplen","cp"+cp.size());
                    callPlanController.updateCallPlanToServerWithDeleteDraft(String.valueOf(session.getId()), callPlen, callPlen1);
                }else{
                    pDialog.dismiss();
                }
               // callPlanController.updateCallPlanToServer(String.valueOf(session.getId()),callPlen);
            }else{
                pDialog.dismiss();
            }
        }else{
            pDialog.dismiss();
        }
    }

    private void CreateTabs(){
        plan.clear();
        ArrayList<CallPlanDraftTabViewFragment> tabFragment=new ArrayList<>();
        ArrayList<mCallPlan> planDraft=callPlanController.getCallPlanDraft(String.valueOf(session.getId()),"0",1);//callplan
        plan.add(planDraft);
        tabFragment.add(CallPlanDraftTabViewFragment.newInstant(planDraft,0));
        ArrayList<mCallPlan> planTemplate=callPlanController.getCallPlanDraft(String.valueOf(session.getId()),"1",1);//template
        plan.add(planTemplate);
        tabFragment.add(CallPlanDraftTabViewFragment.newInstant(planTemplate,1));
        ArrayList<ViewPagerTab> tabsList = new ArrayList<>();
        tabsList.add(new ViewPagerTab("List Draft", planDraft.size()));
        tabsList.add(new ViewPagerTab("List Template", planTemplate.size()));
        adapters = new DynamicTabDraftPageAdapter(getSupportFragmentManager(), tabsList,tabFragment);
        pager.setAdapter(adapters);
        tabs.setViewPager(pager);

    }

}
