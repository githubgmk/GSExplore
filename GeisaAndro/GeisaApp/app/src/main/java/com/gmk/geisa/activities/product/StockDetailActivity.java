package com.gmk.geisa.activities.product;

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
import com.gmk.geisa.adapter.ProductStockAdapter;
import com.gmk.geisa.adapter.ProductStockDetailAdapter;
import com.gmk.geisa.adapter.PromoListAdapter;
import com.gmk.geisa.controller.ProductController;
import com.gmk.geisa.model.mPromo;
import com.gmk.geisa.model.mSession;
import com.gmk.geisa.model.mStock;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;

public class StockDetailActivity extends AppCompatActivity {
    public final static String sessionProduk = "sessionProduk";
    public final static String sessionBranch = "sessionBranch";
    public final static String sessionUser = "sessionUser";
    private SearchView findTXTEdit;
    ProductController productController;
    private  String BranchId="0",ProductId="0";

    private ProgressDialog pDialog;
    private RecyclerView.LayoutManager mLayoutManager;

    private ProductStockDetailAdapter adapter1;
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
        setTitle("Stock Detail");


        productController = ProductController.getInstance(this);
        productController.setCallBackGetStockProduk(callbackStock);

        findTXTEdit = (SearchView) findViewById(R.id.findTXTEdit);
        recyclerView = (RecyclerView) findViewById(R.id.lvItems);
        if (getIntent().getSerializableExtra(sessionProduk) != null) {
            ProductId = (String) getIntent().getSerializableExtra(sessionProduk);
        }
        if (getIntent().getSerializableExtra(sessionBranch) != null) {
            BranchId = (String) getIntent().getSerializableExtra(sessionBranch);
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

        getStockDetail();

    }

    ProductController.CallBackGetStockProduk callbackStock=new ProductController.CallBackGetStockProduk() {
        @Override
        public void resultReady(ArrayList<mStock> stock, boolean hasil) {
            if(hasil){
                setupRecyclerView(stock);
            }
            if(pDialog!=null)
                pDialog.dismiss();
        }
    };


    private void setupRecyclerView(ArrayList<mStock> stock) {

        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        adapter1 = new ProductStockDetailAdapter(stock, this);
        recyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this)
                .colorResId(R.color.colorPrimaryDark)
                .size(2)
                .build());
        recyclerView.setAdapter(adapter1);
        this.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
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
    private void getStockDetail(){
        if (pDialog == null)
            pDialog = new ProgressDialog(this);
        pDialog.setMessage("Getting Data From Server ...\n Please Wait...");
        pDialog.show();

        if (null != BranchId && null!=ProductId ) {
            productController.getAllProductStockProductFromServer(BranchId,ProductId);
        } else {
            pDialog.dismiss();
        }
    }

    //untuk memantau tombol back
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            //getFragmentManager().popBackStack();
            finish();
        }else if(item.getItemId() == R.id.menuRefresh){
            getStockDetail();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
