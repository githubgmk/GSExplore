package com.gmk.geisa.activities.po;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
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
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.gmk.geisa.R;
import com.gmk.geisa.activities.rofo.RofoNewActivity;
import com.gmk.geisa.adapter.POListAdapter;
import com.gmk.geisa.adapter.RofoSummaryLineListAdapter;
import com.gmk.geisa.controller.CustomerController;
import com.gmk.geisa.controller.PoController;
import com.gmk.geisa.controller.RofoController;
import com.gmk.geisa.helper.Constants;
import com.gmk.geisa.model.mPO;
import com.gmk.geisa.model.mPoLine;
import com.gmk.geisa.model.mRofoAktualisasi;
import com.gmk.geisa.model.mSession;
import com.kenmeidearu.searchablespinnerlibrary.SearchableSpinner;
import com.kenmeidearu.searchablespinnerlibrary.mListString;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class PoConfirmActivity extends AppCompatActivity {
    //public final static String sessionPlan = "sesionPlan";
    public final static String sessionUser = "sessionUser";
    static final int SELECT_CUSTOMER = 111;
    //private mCustomer customer=new mCustomer();
    private mSession session;
    CustomerController customerController;
    PoController poController;

    private ProgressDialog pDialog;
    private RecyclerView.LayoutManager mLayoutManager;

    TextView targetval, rofoval, salesval;
    private POListAdapter adapter1;
    RecyclerView recyclerView;
    private ArrayList<mRofoAktualisasi> aktualisasilist = new ArrayList<>();
    ArrayList<mListString> listStringTahun = new ArrayList<>();


    int SalesId;
    String thn;
    boolean isPP = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_po_confirm);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("All Pending PO");


        customerController = CustomerController.getInstance(this);
        poController = PoController.getInstance(this);


        recyclerView = (RecyclerView) findViewById(R.id.lvItems);

        Calendar calss = Calendar.getInstance();
        SimpleDateFormat smp2 = new SimpleDateFormat("yyyy/MM/dd");
        SimpleDateFormat smp3 = new SimpleDateFormat("hhmmss");

        if (getIntent().getSerializableExtra(sessionUser) != null) {
            session = (mSession) getIntent().getSerializableExtra(sessionUser);
            SalesId = session.getId();
            // Log.e("isi user", " x" + session.getId());

            // setupTabIcons();
        }
        //Log.e("isi sessi a",session.getNama()+","+session.getId());
        this.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);


    }


    private void setupRecyclerView() {
        ArrayList<mPO> po = poController.getAllPODraft();
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        adapter1 = new POListAdapter(po, this, session, true, false);
        recyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this)
                .colorResId(R.color.colorPrimaryDark)
                .size(2)
                .build());
        recyclerView.setAdapter(adapter1);
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
        setupRecyclerView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       // getMenuInflater().inflate(R.menu.menu_submit, menu);
        //MenuItem item = menu.findItem(R.id.menuSubmit);
        //item.setTitle("Input Sell Out");
        //item.setIcon(android.R.drawable.ic_menu_set_as);
        return true;
    }

    //untuk memantau tombol back
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            //getFragmentManager().popBackStack();
            finish();
        } /*else if (item.getItemId() == R.id.menuSubmit) {//save customer
            Intent intent =new Intent(this, PoNewActivity.class);
            intent.putExtra(PoNewActivity.sessionUser, session);
            intent.putExtra(PoNewActivity.sessionSellOut,true);
            startActivity(intent);
            return true;
        }*/
        return super.onOptionsItemSelected(item);
    }

}
