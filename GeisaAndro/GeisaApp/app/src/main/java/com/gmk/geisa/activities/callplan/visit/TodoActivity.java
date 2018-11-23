package com.gmk.geisa.activities.callplan.visit;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.SearchView;

import com.gmk.geisa.R;
import com.gmk.geisa.adapter.PromoListAdapter;
import com.gmk.geisa.adapter.ToDoListAdapter;
import com.gmk.geisa.controller.CallPlanController;
import com.gmk.geisa.controller.ProductController;
import com.gmk.geisa.model.mPromo;
import com.gmk.geisa.model.mSession;
import com.gmk.geisa.model.mTodoList;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;

public class TodoActivity extends AppCompatActivity {
    public final static String sessionPlan = "sesionPlan";
    public final static String sessionUser = "sessionUser";
    public final static String sessionCustomer = "sessionCustomer";

    private SearchView findTXTEdit;
    private mSession session;
    CallPlanController callPlanController;

    private ProgressDialog pDialog;
    private RecyclerView.LayoutManager mLayoutManager;

    private ToDoListAdapter adapter1;
    RecyclerView recyclerView;


    String CallPlanId;
    int SalesId,CustomerId;
    String thn;
    boolean isPP = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("All Things To Do");


        callPlanController = CallPlanController.getInstance(this);
        callPlanController.setCallBackGetDataToDo(callbackTodo);

        findTXTEdit = (SearchView) findViewById(R.id.findTXTEdit);
        recyclerView = (RecyclerView) findViewById(R.id.lvItems);
        if (getIntent().getSerializableExtra(sessionUser) != null) {
            session = (mSession) getIntent().getSerializableExtra(sessionUser);
            SalesId = session.getId();
            // Log.e("isi user", " x" + session.getId());
        }
        if (getIntent().getSerializableExtra(sessionPlan) != null) {
            CallPlanId = (String) getIntent().getSerializableExtra(sessionPlan);
        }
        if (getIntent().getSerializableExtra(sessionCustomer) != null) {
            CustomerId = Integer.parseInt((String) getIntent().getSerializableExtra(sessionCustomer));
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
        setupRecyclerView();
        getdatafromserver();

    }

    private void getdatafromserver(){
        if (pDialog == null)
            pDialog = new ProgressDialog(this);
        pDialog.setMessage("Getting Data From Server ...\n Please Wait...");
        pDialog.show();

        if (null != session) {
            callPlanController.getAllTodoListFromServer(String.valueOf(session.getId()), String.valueOf(CustomerId));
        } else {
            pDialog.dismiss();
        }
    }

    CallPlanController.CallBackGetDataToDo callbackTodo=new CallPlanController.CallBackGetDataToDo() {
        @Override
        public void resultReady(ArrayList<mTodoList> customers, boolean hasil) {
            if(hasil){
                setupRecyclerView();
            }
            if(pDialog!=null)
                pDialog.dismiss();
        }
    };


    private void setupRecyclerView() {

        ArrayList<mTodoList> todo=callPlanController.getAllToDoByCustId(String.valueOf(CustomerId));
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        adapter1 = new ToDoListAdapter(todo, this,session);
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
        setupRecyclerView();
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
                callPlanController.getAllTodoListFromServer(String.valueOf(session.getId()), String.valueOf(CustomerId));
            } else {
                pDialog.dismiss();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
