package com.gmk.geisa.activities.rofo;

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
import com.gmk.geisa.activities.callplan.CallPlanDraftActivity;
import com.gmk.geisa.adapter.RofoSummaryLineListAdapter;
import com.gmk.geisa.controller.CustomerController;
import com.gmk.geisa.controller.RofoController;
import com.gmk.geisa.helper.Constants;
import com.gmk.geisa.model.mRofoAktualisasi;
import com.gmk.geisa.model.mSession;
import com.kenmeidearu.searchablespinnerlibrary.SearchableSpinner;
import com.kenmeidearu.searchablespinnerlibrary.mListString;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class RofoActivity extends AppCompatActivity {
    //public final static String sessionPlan = "sesionPlan";
    public final static String sessionUser = "sessionUser";
    //public final static String sessionCustomer="sessionCustomer";
    //public final static String sessionProduct = "sessionProduct";
    //public final static String sessionTypePlan = "sessionTypePlan";
    static final int SELECT_CUSTOMER = 111;
    //private mCustomer customer=new mCustomer();
    static final ArrayList<mRofoAktualisasi> aktualisasi = new ArrayList<>();
    private mSession session;
    private int typePlan, totalBefore;
    CustomerController customerController;
    RofoController rofoController;

    private ProgressDialog pDialog;
    private RecyclerView.LayoutManager mLayoutManager;
    View view;
    TextView targetval,rofoval,salesval;
    TextInputEditText tahun;
    SearchableSpinner listTahun;
    private RofoSummaryLineListAdapter adapter1;
    RecyclerView recyclerView;
    private ArrayList<mRofoAktualisasi> aktualisasilist = new ArrayList<>();
    ArrayList<mListString> listStringTahun = new ArrayList<>();


    int SalesId;
    String thn;
    boolean isPP = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rofo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("All Rofo");


        customerController = CustomerController.getInstance(this);
        rofoController = RofoController.getInstance(this);
        rofoController.setCallBackGetDataRofoAktualisasi(callbackaktualisasi);

        tahun = (TextInputEditText) findViewById(R.id.tahun);
        listTahun = (SearchableSpinner) findViewById(R.id.listTahun);
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
        // ArrayList<mLevel> lvl = levelController.getAllLevel();
        thn=String.valueOf(calss.get(calss.YEAR));
        listStringTahun.add(new mListString(0, String.valueOf(calss.get(calss.YEAR))));
        listStringTahun.add(new mListString(0, String.valueOf(calss.get(calss.YEAR) - 1)));
        listStringTahun.add(new mListString(0, String.valueOf(calss.get(calss.YEAR) - 2)));
        listStringTahun.add(new mListString(0, String.valueOf(calss.get(calss.YEAR) - 3)));
        listStringTahun.add(new mListString(0, String.valueOf(calss.get(calss.YEAR) + 1)));
        listTahun.setAdapter(listStringTahun, 1, 1);
        listTahun.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                thn=listStringTahun.get(position).getNilai1();
                tahun.setText(thn);
                setupRecyclerView(thn);
                getDataFromServer();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        setupRecyclerView(thn);


    }

    private void getDataFromServer(){
        Calendar cals=Calendar.getInstance();
        if (pDialog == null)
            pDialog = new ProgressDialog(this);
        pDialog.setMessage("Get Data From Server ...\n Please Wait...");
        pDialog.show();
        rofoController.getAllRofoAktualisasiFromServer(String.valueOf(SalesId),thn);
        //setupRecyclerView(thn);
    }


    private void setupRecyclerView(String tahun) {
        ArrayList<mRofoAktualisasi> act=rofoController.getRofoAktualBySalesIdYear(String.valueOf(SalesId),Integer.parseInt(tahun));
        aktualisasilist.clear();
        for(int i=1;i<=12;i++){
            boolean ketemu=false;
            for (mRofoAktualisasi cu: act) {
                if(cu.getMonth()==i){
                    ketemu=true;
                  //  Log.e("isi a","a "+cu.getValueSales()+","+cu.getMonth()+","+cu.getMonthName()+","+cu.getValueRofo());
                    aktualisasilist.add(cu);
                    break;
                }
            }
            if(!ketemu){

                Calendar cal=Calendar.getInstance();
                SimpleDateFormat smp3 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                mRofoAktualisasi ac=new mRofoAktualisasi(String.valueOf(i).concat(tahun),Integer.parseInt(tahun),i, Constants.getBulanString(i),0,0,0,0,0,smp3.format(cal.getTime()));
                //Log.e("isi a","ac"+ac.getValueSales()+","+ac.getMonth()+","+ac.getMonthName()+","+ac.getValueRofo());
                aktualisasilist.add(ac);
            }

        }
        //recyclerView = (RecyclerView) view.findViewById(R.id.lvItems);
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        adapter1 = new RofoSummaryLineListAdapter(aktualisasilist, this,targetval, salesval, rofoval);
        recyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this)
                .colorResId(R.color.colorPrimaryDark)
                .size(2)
                .build());
        adapter1.notifyDataSetChanged();
        recyclerView.setAdapter(adapter1);
        recyclerView.invalidate();
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
        MenuItem item = menu.findItem(R.id.menuRefresh);
        item.setTitle("Add Rofo");
        item.setIcon(android.R.drawable.ic_menu_add);
        return true;
    }


    //untuk memantau tombol back
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            //getFragmentManager().popBackStack();
            finish();
        } else if (item.getItemId() == R.id.menuRefresh) {
            Intent inten = new Intent(getApplicationContext(), RofoNewActivity.class);//berpindah ke activity yang lain dgn data
            inten.putExtra(RofoNewActivity.sessionUser, session);
            startActivityForResult(inten, 1212);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    RofoController.CallBackGetDataRofoAktualisasi callbackaktualisasi = new RofoController.CallBackGetDataRofoAktualisasi() {
        @Override
        public void resultReady(ArrayList<mRofoAktualisasi> customers, boolean hasil) {
            if(pDialog!=null)
                pDialog.dismiss();
            if (hasil) {
                Toast.makeText(getApplicationContext(), "Sync Rofo Actual Done", Toast.LENGTH_SHORT).show();
                setupRecyclerView(thn);
            } else {
                setupRecyclerView(thn);
                Toast.makeText(getApplicationContext(), "Error On Sycn Rofo", Toast.LENGTH_SHORT).show();
            }

        }
    };
    //callPlanAddTgl
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        Log.e("keini","kesini"+requestCode);
        if (requestCode == 1212) {
            Log.e("keini","kesini");
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {

                setupRecyclerView(thn);
                getDataFromServer();
            }
        }else{
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
