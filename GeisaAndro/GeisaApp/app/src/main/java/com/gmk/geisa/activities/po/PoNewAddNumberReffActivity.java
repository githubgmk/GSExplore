package com.gmk.geisa.activities.po;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.SearchView;

import com.gmk.geisa.R;
import com.gmk.geisa.adapter.POListRefAdapter;
import com.gmk.geisa.controller.PoController;
import com.gmk.geisa.model.mPO;
import com.gmk.geisa.model.mSession;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;

public class PoNewAddNumberReffActivity extends AppCompatActivity {
    public final static String sessionUser = "sessionLogin";
    public final static String sessionSalesConfirm = "sessionSalesConfirm";
    public final static String sessionIsCopyPP = "sessionIsCopyPP";
    private mSession session;
    ArrayList<mPO> mPOs=new ArrayList<>();
    private int SalesId;
    private boolean IsConfirm,IsCPP;
    PoController poController;

    ProgressDialog pDialog;
    SearchView findTXTEdit;
    RecyclerView recyclerView;
    Button addSelect, addCancel;
    private POListRefAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    int currentPosition = 0;
    int previousPosition = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_po_new_add_po_ref);
        setTitle("");
        this.setFinishOnTouchOutside(false);
        this.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        addSelect = (Button) findViewById(R.id.addSelect);
        addCancel = (Button) findViewById(R.id.addCancel);
        findTXTEdit = (SearchView) findViewById(R.id.findTXTEdit);
        poController = PoController.getInstance(this);
        poController.setCallBackGetDataSync(callbackPP);
        if (getIntent().getSerializableExtra(sessionUser) != null) {
            session = (mSession) getIntent().getSerializableExtra(sessionUser);
            SalesId = session.getId();
           // Log.e("isi cus","xx"+SalesId);
        }
        if (getIntent().getSerializableExtra(sessionSalesConfirm) != null) {
            IsConfirm = (boolean) getIntent().getSerializableExtra(sessionSalesConfirm);
        }
        if (getIntent().getSerializableExtra(sessionIsCopyPP) != null) {
            IsCPP = (boolean) getIntent().getSerializableExtra(sessionIsCopyPP);
        }
        setupRecyclerView();


        findTXTEdit.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mAdapter.setFilter().filter(newText);
                return false;
            }
        });

        addSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //return data customer with selection
                mPO po=mAdapter.getItemSelected();
                Intent returnIntent = new Intent();
                returnIntent.putExtra(PoNewActivity.sessionPO, po);
                returnIntent.putExtra(PoNewActivity.sessionSalesConfirm, IsConfirm);
                returnIntent.putExtra(PoNewActivity.sessionIsCopyPP, IsCPP);
                setResult(Activity.RESULT_OK,returnIntent);
               // Log.e("select pi","se"+po.getPoId());
                finish();
            }
        });
        addCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getDataFromServer();

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

    private void  getDataFromServer(){
        if (pDialog == null)
            pDialog = new ProgressDialog(this);
        pDialog.setMessage("Refresh Data From Server ...\n Please Wait...");
        pDialog.show();
        pDialog.setCancelable(false);
        poController.getPoPPActiveFromServer(String.valueOf(SalesId));
    }


    private void setupRecyclerView() {

        mPOs = poController.getAllPoPPActive(String.valueOf(SalesId));
        mAdapter = new POListRefAdapter(mPOs, this, session,IsConfirm,IsCPP);

        recyclerView = (RecyclerView) findViewById(R.id.lvItems);
        recyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);

        recyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this)
                .colorResId(R.color.colorPrimaryDark)
                .size(2)
                .build());
        recyclerView.setAdapter(mAdapter);
    }

    PoController.CallBackGetDataSync callbackPP = new PoController.CallBackGetDataSync() {
        @Override
        public void resultReady(boolean hasil) {
            setupRecyclerView();
            pDialog.dismiss();

        }
    };

}
