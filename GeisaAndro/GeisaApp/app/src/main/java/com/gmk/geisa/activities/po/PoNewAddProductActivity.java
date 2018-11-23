package com.gmk.geisa.activities.po;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;

import com.gmk.geisa.R;
import com.gmk.geisa.adapter.ProductPriceListAdapter;
import com.gmk.geisa.controller.CustomerController;
import com.gmk.geisa.controller.ProductController;
import com.gmk.geisa.model.mProductPriceDiskon;
import com.gmk.geisa.model.mSession;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PoNewAddProductActivity extends AppCompatActivity {
    public final static String sessionSelectedProduct="sessionSelectedProduct";
    public final static String sessionUser = "sessionLogin";
    public final static String sessionProduct = "sessionProduct";
    public  final static String sessionCustDistBranch="sessionCustDistBranch";
    public  final static String sessionSalesCofirm="sessionSalesCofirm";
    public final static String sessionIsCopyPP = "sessionIsCopyPP";
    private mSession session;
    ArrayList<mProductPriceDiskon> sessionproduk=new ArrayList<>();
    ArrayList<mProductPriceDiskon> plan = new ArrayList<>();
    private int SalesId;
    private int CustAndBranchId;
    private boolean IsConfirm,IsCPP;
    private CustomerController customerController;
    ProductController productController;

    ProgressDialog pdialog;
    TextView totalCust;
    SearchView findTXTEdit;
    RecyclerView recyclerView;
    Button addSelect, addCancel;
    private ProductPriceListAdapter mAdapter;
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
            sessionproduk= (ArrayList<mProductPriceDiskon>) getIntent().getSerializableExtra(sessionSelectedProduct);
        }

        if (getIntent().getSerializableExtra(sessionProduct) != null) {
            sessionproduk= (ArrayList<mProductPriceDiskon>) getIntent().getSerializableExtra(sessionProduct);
        }

        if (getIntent().getSerializableExtra(sessionCustDistBranch) != null) {
            CustAndBranchId= (int) getIntent().getSerializableExtra(sessionCustDistBranch);
         //   Log.e("isi b","xx"+CustAndBranchId);
        }

        if (getIntent().getSerializableExtra(sessionSalesCofirm) != null) {
            IsConfirm = (boolean) getIntent().getSerializableExtra(sessionSalesCofirm);
        }
        if (getIntent().getSerializableExtra(sessionIsCopyPP) != null) {
            IsCPP = (boolean) getIntent().getSerializableExtra(sessionIsCopyPP);
        }
        customerController = CustomerController.getInstance(this);
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
                List<mProductPriceDiskon> cust=mAdapter.getAllItem();
                Intent returnIntent = new Intent();
                returnIntent.putExtra(PoNewActivity.sessionProduct, (Serializable) cust);
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

        //Log.e("isia",CustAndBranchId+"xx");
        plan = productController.getAllProductPriceDiskon(String.valueOf(CustAndBranchId));
          //Log.e("isi plan",sessionproduk.size()+" ttl");
        if (sessionproduk.size() > 0) {
            for (mProductPriceDiskon cu : sessionproduk) {
               // Log.e("isi prod",cu.getProductName()+","+cu.isSelected()+","+cu.isDraft());
                if ((!cu.isSelected() && !IsConfirm) || IsConfirm) {
                 //   Log.e("isi prod",cu.getProductName()+","+cu.isSelected());
                    for(mProductPriceDiskon pd: plan){
                        if(pd.getRecId()==cu.getRecId()){
                            pd.setDraft(cu.isDraft());
                            pd.setSelected(true);
                            break;
                        }

                    }
                }
            }
        }
        mAdapter = new ProductPriceListAdapter(plan, this, totalCust);

        recyclerView = (RecyclerView) findViewById(R.id.lvItems);
        recyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);

        recyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this)
                .colorResId(R.color.colorPrimaryDark)
                .size(1)
                .build());
        recyclerView.setAdapter(mAdapter);
    }


}
