package com.gmk.geisa.activities.callplan.visit;

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
import android.widget.TextView;

import com.gmk.geisa.R;
import com.gmk.geisa.activities.po.PoNewActivity;
import com.gmk.geisa.adapter.ProductListAdapter;
import com.gmk.geisa.adapter.ProductPriceListAdapter;
import com.gmk.geisa.controller.CustomerController;
import com.gmk.geisa.controller.ProductController;
import com.gmk.geisa.model.mProduct;
import com.gmk.geisa.model.mProductPriceDiskon;
import com.gmk.geisa.model.mSession;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SampleItemAddProductActivity extends AppCompatActivity {
    public final static String sessionSelectedProduct="sessionSelectedProduct";
    public final static String sessionUser = "sessionLogin";
    public final static String sessionProduct = "sessionProduct";
    private mSession session;
    ArrayList<mProduct> sessionproduk=new ArrayList<>();
    ArrayList<mProduct> plan = new ArrayList<>();
    private int SalesId;
    private boolean isSalesCofirm=false;
    ProductController productController;

    ProgressDialog pdialog;
    TextView totalCust;
    SearchView findTXTEdit;
    RecyclerView recyclerView;
    Button addSelect, addCancel;
    private ProductListAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_po_new_add_product);
        setTitle("");
        this.setFinishOnTouchOutside(false);
        this.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        addSelect = (Button) findViewById(R.id.addSelect);
        addCancel = (Button) findViewById(R.id.addCancel);
        totalCust = (TextView) findViewById(R.id.totalCust);
        findTXTEdit = (SearchView) findViewById(R.id.findTXTEdit);
        productController = ProductController.getInstance(this);
        if (getIntent().getSerializableExtra(sessionUser) != null) {
            session = (mSession) getIntent().getSerializableExtra(sessionUser);
            SalesId = session.getId();
           // Log.e("isi cus","xx"+SalesId);
        }
        if (getIntent().getSerializableExtra(sessionSelectedProduct) != null) {
            sessionproduk= (ArrayList<mProduct>) getIntent().getSerializableExtra(sessionSelectedProduct);
        }

        if (getIntent().getSerializableExtra(sessionProduct) != null) {
            sessionproduk= (ArrayList<mProduct>) getIntent().getSerializableExtra(sessionProduct);
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
                List<mProduct> cust=mAdapter.getAllItem();
                Intent returnIntent = new Intent();
                returnIntent.putExtra(SampleItemAddProductActivity.sessionProduct, (Serializable) cust);
                setResult(Activity.RESULT_OK,returnIntent);
                finish();
            }
        });
        addCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    private void setupRecyclerView() {
        plan = productController.getAllProduct("1");
          //Log.e("isi plan",sessionproduk.size()+" ttl");
        if (sessionproduk.size() > 0) {
            for (mProduct cu : sessionproduk) {
               // Log.e("isi prod",cu.getProductName()+","+cu.isSelected()+","+cu.isDraft());
                if ((!cu.isSelected() && !isSalesCofirm) || isSalesCofirm) {
                 //   Log.e("isi prod",cu.getProductName()+","+cu.isSelected());
                    for(mProduct pd: plan){
                        if(pd.getProductId()==cu.getProductId()){
                            //pd.setDraft(cu.isDraft());
                            pd.setSelected(true);
                            break;
                        }

                    }
                }
            }
        }
        mAdapter = new ProductListAdapter(plan, this, totalCust);

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


}
