package com.gmk.geisa.activities.product;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.gmk.geisa.R;
import com.gmk.geisa.activities.callplan.CallPlanDraftActivity;
import com.gmk.geisa.fragment.tab.TabFragmentProdukPagerAdapter;
import com.gmk.geisa.controller.ProductController;
import com.gmk.geisa.model.mProduct;
import com.gmk.geisa.model.mProductPriceDiskon;
import com.gmk.geisa.model.mSession;

import java.util.ArrayList;

public class ProductActivity extends AppCompatActivity {
    //private TabLayout tabs;
    private ViewPager pager;
    private TabLayout tabs;
    ProductController productController;
    final ArrayList<ArrayList<mProduct>> prod =new ArrayList<>();
    private ProgressDialog pDialog;
    public final static String sessionUser = "sessionLogin";
    private mSession session;
    TextView updateNilai;
    int posisiTab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Product List");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        //determinePaneLayout();
        pager = (ViewPager) findViewById(R.id.pager);
        tabs = (TabLayout) findViewById(R.id.tabs);
        updateNilai=(TextView) findViewById(R.id.updateNilai);
        productController = ProductController.getInstance(this);
        productController.setCallBackGetDataPrice(callBackPrice);
        productController.setCallBackGetData(callBackProduct);
        if (getIntent().getSerializableExtra(sessionUser) != null) {
            session = (mSession) getIntent().getSerializableExtra(sessionUser);
        }
        setupTabIcons();
        //cekUpdateFromServer();


    }
    ProductController.CallBackGetDataPrice callBackPrice = new ProductController.CallBackGetDataPrice() {
        @Override
        public void resultReady(ArrayList<mProductPriceDiskon> productPrice, boolean hasil) {
            if (pDialog != null)
                pDialog.dismiss();
            if (hasil) {
                Toast.makeText(getApplicationContext(), "DONE SYNC", Toast.LENGTH_SHORT).show();
                setupTabIcons();
            }else{
                setupTabIcons();
                Toast.makeText(getApplicationContext(), "ERROR ON SYNC Product Price...", Toast.LENGTH_SHORT).show();
            }

        }
    };
    ProductController.CallBackGetData callBackProduct = new ProductController.CallBackGetData() {
        @Override
        public void resultReady(ArrayList<mProduct> productPrice, boolean hasil) {
            if (pDialog != null)
                pDialog.dismiss();
            if (hasil) {
                Toast.makeText(getApplicationContext(), "DONE SYNC", Toast.LENGTH_SHORT).show();
                productController.getAllProductPriceDiskonFromServer(String.valueOf(session.getId()));
                setupTabIcons();
            }else{
                setupTabIcons();
                Toast.makeText(getApplicationContext(), "ERROR ON SYNC Product...", Toast.LENGTH_SHORT).show();
            }

        }
    };



    //crete menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_refresh, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            //getFragmentManager().popBackStack();
            finish();
        } else if (item.getItemId() == R.id.menuRefresh) {
            if (pDialog == null)
                pDialog = new ProgressDialog(this);
            pDialog.setMessage("Getting Data From Server ...\n Please Wait...");
            pDialog.show();

            if (null != session) {
                productController.getAllProductFromServer(String.valueOf(session.getId()));
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
       // CreateTabs();
       // Log.e("posisi tab",posisiTab+","+prod.get(posisiTab).size());
//        updateNilai.setText(String.valueOf(prod.get(0).size()));
    }


    private void  cekUpdateFromServer(){
        if (pDialog == null)
            pDialog = new ProgressDialog(this);
        pDialog.setMessage("Getting Data From Server ...\n Please Wait...");
        pDialog.show();
        if (null != session) {
            productController.getAllProductFromServer(String.valueOf(session.getId()));
        } else {
            pDialog.dismiss();
        }
       // CreateTabs();

    }
    private void setupTabIcons() {
        Bundle bundle = new Bundle();
        ArrayList<mProduct> products=productController.getAllProduct("1");//callplan\
        ArrayList<mProductPriceDiskon> productPrice=productController.getAllProductPriceDiskon("0");//callplan\
        //ArrayList<mStockBranch> produkStok=productController.getAllProductPriceDiskon("0");//callplan\
       // Log.e("isi proudc"," xx"+products.size()+","+productPrice.size());
        pager.setAdapter(new TabFragmentProdukPagerAdapter(getSupportFragmentManager(),bundle,products,productPrice,null));
        //pager.setAdapter(new TabFragmentProdukPagerAdapter(getSupportFragmentManager(),bundle));
        tabs.setupWithViewPager(pager);
        tabs.setTabGravity(TabLayout.GRAVITY_FILL);
        TextView tabOne = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabOne.setText("List Product");
        tabOne.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_cust, 0, 0);
        tabs.getTabAt(0).setCustomView(tabOne);

        TextView tabTwo = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabTwo.setText("Price List");
        tabTwo.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_cust_prospek, 0, 0);
        tabs.getTabAt(1).setCustomView(tabTwo);

        TextView tabThree = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabThree.setText("Stock");
        tabThree.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_cust, 0, 0);
        tabs.getTabAt(2).setCustomView(tabThree);

    }




}
