package com.gmk.geisa.activities.callplan.visit;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.SearchView;

import com.androidadvance.androidsurvey.models.Survey;
import com.gmk.geisa.R;
import com.gmk.geisa.adapter.PromoListAdapter;
import com.gmk.geisa.adapter.SurveyListAdapter;
import com.gmk.geisa.controller.OtherController;
import com.gmk.geisa.controller.ProductController;
import com.gmk.geisa.model.mPromo;
import com.gmk.geisa.model.mSession;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;

public class SurveyActivity extends AppCompatActivity {
    //public final static String sessionPlan = "sesionPlan";
    public final static String sessionUser = "sessionUser";
    static final int SELECT_CUSTOMER = 111;
    private SearchView findTXTEdit;
    private mSession session;
    OtherController otherController;

    private ProgressDialog pDialog;
    private RecyclerView.LayoutManager mLayoutManager;

    private SurveyListAdapter adapter1;
    RecyclerView recyclerView;



    int SalesId;
    String thn;
    boolean isPP = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("All Available Survey");

        otherController = OtherController.getInstance(this);
        otherController.setCallBackGetDataSurvey(callbackSurvey);

        findTXTEdit = (SearchView) findViewById(R.id.findTXTEdit);
        recyclerView = (RecyclerView) findViewById(R.id.lvItems);
        if (getIntent().getSerializableExtra(sessionUser) != null) {
            session = (mSession) getIntent().getSerializableExtra(sessionUser);
            SalesId = session.getId();
            otherController.getSurvey(session.getId(),0);
        }
        //Log.e("isi sessi a",session.getNama()+","+session.getId());
        this.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);


        findTXTEdit.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter1.setFilter().filter(newText);
                return false;
            }
        });

    }

    OtherController.CallBackGetDataSurvey callbackSurvey=new OtherController.CallBackGetDataSurvey() {
        @Override
        public void resultReady(ArrayList<Survey> survey, boolean hasil) {
            if(hasil){
                setupRecyclerView(survey);
            }
            if(pDialog!=null)
                pDialog.dismiss();
        }
    };


    private void setupRecyclerView(ArrayList<Survey> surveys) {

        //ArrayList<mPromo> promo= otherController.getAllPromo();
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        adapter1 = new SurveyListAdapter(surveys, this,session);
        recyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this)
                .colorResId(R.color.colorPrimaryDark)
                .size(2)
                .build());
        recyclerView.setAdapter(adapter1);
        this.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    private void hiddenKeyboard(View v) {
        InputMethodManager keyboard = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        keyboard.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
        super.onDestroy();

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_refresh, menu);
        return true;
    }

    //untuk memantau tombol back
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            //getFragmentManager().popBackStack();
            finish();
        }else if(item.getItemId() == R.id.menuRefresh){
            if (pDialog == null)
                pDialog = new ProgressDialog(this);
            pDialog.setMessage("Getting Data From Server ...\n Please Wait...");
            pDialog.show();

            if (null != session) {
                otherController.getSurvey(session.getId(),0);
            } else {
                pDialog.dismiss();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        adapter1.onActivityResult(requestCode, resultCode, data);
        /*if (requestCode == 69) {
            if (resultCode == RESULT_OK) {

                String answers_json = data.getExtras().getString("answers");
                Log.e("****", "****************** WE HAVE ANSWERS ******************");
                Log.e("ANSWERS JSON 1", answers_json);
                Log.e("****", "*****************************************************");

                //do whatever you want with them...
            }
        }*/
    }

}
