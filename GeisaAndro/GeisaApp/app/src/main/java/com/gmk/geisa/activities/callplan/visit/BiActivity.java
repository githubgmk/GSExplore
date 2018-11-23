package com.gmk.geisa.activities.callplan.visit;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Toast;

import com.gmk.geisa.R;
import com.gmk.geisa.controller.BiCsController;
import com.gmk.geisa.controller.CallPlanController;
import com.gmk.geisa.controller.ProductController;
import com.gmk.geisa.model.mBiCsType;
import com.gmk.geisa.model.mCallPlanNote;
import com.gmk.geisa.model.mCustomer;
import com.gmk.geisa.model.mProduct;
import com.gmk.geisa.model.mSession;
import com.kenmeidearu.searchablespinnerlibrary.SearchableSpinner;
import com.kenmeidearu.searchablespinnerlibrary.mListString;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class BiActivity extends AppCompatActivity {
    public static String sessionUser = "sessionUser";
    public static String callPlanId = "callPlanId";
    public static String sessionCust = "sessionCust";
    private mSession session;
    private String idCallPlan;
    private int SalesId;
    private ProgressDialog pDialog;
    CallPlanController callPlanController;
    ProductController productController;
    BiCsController biCsController;
    ArrayList<mListString> listStringsType = new ArrayList<>();
    ArrayList<mListString> listStringsProduct = new ArrayList<>();
    //private String inBedMenuTitle = "Set to 'In bed'";
    Button CstAddCustomerMap;
    SearchableSpinner listType,listProduct;
    TextInputEditText biNoteId, biNoteVisitId, biNoteTypeId, biNote1, biNote2, biNote3;
    private String idBiNote,CustId,biNoteTypeName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bi);

        biNoteId = (TextInputEditText) findViewById(R.id.biNoteId);
        biNoteVisitId = (TextInputEditText) findViewById(R.id.biNoteVisitId);
        biNoteTypeId = (TextInputEditText) findViewById(R.id.biNoteTypeId);
        listType = (SearchableSpinner) findViewById(R.id.listType);
        listProduct= (SearchableSpinner) findViewById(R.id.listProduct);
        biNote1 = (TextInputEditText) findViewById(R.id.biNote1);
        biNote2 = (TextInputEditText) findViewById(R.id.biNote2);
        biNote3 = (TextInputEditText) findViewById(R.id.biNote3);
        this.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        //add controller
        callPlanController = CallPlanController.getInstance(this);
        productController=ProductController.getInstance(this);
        biCsController = BiCsController.getInstance(this);
        setTitle("Add New BIN");


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Calendar calss = Calendar.getInstance();
        SimpleDateFormat smp2 = new SimpleDateFormat("yyMMddhhmmss");
        if (getIntent().getSerializableExtra(sessionUser) != null) {
            session = (mSession) getIntent().getSerializableExtra(sessionUser);
            SalesId = session.getId();
            idBiNote = String.valueOf(SalesId).concat(String.valueOf(smp2.format(calss.getTime())));
            biNoteId.setText(idBiNote);
        }
        if (getIntent().getSerializableExtra(callPlanId) != null) {
            idCallPlan = (String) getIntent().getSerializableExtra(callPlanId);
            biNoteVisitId.setText(idCallPlan);
        }
        if (getIntent().getSerializableExtra(sessionCust) != null) {
            CustId = (String) getIntent().getSerializableExtra(sessionCust);
        }

        //level
        ArrayList<mBiCsType> lvl = biCsController.getAllBiCsType("BIN");
        listStringsType.add(new mListString(0, "Select Jenis BIN"));
        for (mBiCsType lv : lvl) {
            mListString isi = new mListString(lv.getBiCsTypeId(), lv.getBiCsTypeName());
            listStringsType.add(isi);
        }
        listType.setAdapter(listStringsType, 2, 2);
        listType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    biNoteTypeId.setText(String.valueOf(listStringsType.get(position).get_id()));
                    biNoteTypeName=String.valueOf(listStringsType.get(position).getNilai1());

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
//product
        ArrayList<mProduct> plan = productController.getAllProduct("1");
        listStringsProduct.add(new mListString(0, "Select Product Item","0","-"));
        for (mProduct lv : plan) {
            mListString isi = new mListString(lv.getProductId(), lv.getProductName(),lv.getProductSimpleDescription(),lv.getProductCode());
            listStringsProduct.add(isi);
        }
        listProduct.setAdapter(listStringsProduct, 3, 3);
        listProduct.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                biNote3.setText(String.valueOf(listStringsProduct.get(position).get_id()));

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    //crete menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_customer, menu);

        MenuItem item = menu.findItem(R.id.menuAddCst);
        item.setTitle("Save");
        item.setIcon(android.R.drawable.ic_menu_save);
        MenuItem item1 = menu.findItem(R.id.menuRefresh);
        item1.setTitle("Cancel");
        item1.setIcon(android.R.drawable.ic_menu_close_clear_cancel);
        return true;
    }

    private void cancelAdd() {
        //biNoteId.setText(null);
        //biNoteVisitId.setText(null);
        biNoteId.setError(null);
        biNoteVisitId.setError(null);
        biNoteTypeId.setError(null);
        biNote1.setError(null);
        biNote2.setError(null);
        biNote3.setError(null);
        biNote1.setError(null);
        biNote1.setText(null);
        biNote2.setText(null);
        biNote3.setText(null);
        listType.setSelection(0);

    }

    private void savedToDatabase() {
        boolean cancel = false;
        View focusView = null;
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        biNoteId.setError(null);
        biNoteVisitId.setError(null);
        biNoteTypeId.setError(null);
        biNote1.setError(null);
        biNote2.setError(null);
        biNote3.setError(null);

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(biNoteId.getEditableText())) {
            biNoteId.setError(getString(R.string.error_field_required));
            focusView = biNoteId;
            cancel = true;
        } else if (TextUtils.isEmpty(biNoteVisitId.getEditableText())) {
            biNoteVisitId.setError(getString(R.string.error_field_required));
            focusView = biNoteVisitId;
            cancel = true;
        } else if (TextUtils.isEmpty(biNoteTypeId.getEditableText()) || biNoteTypeId.getEditableText().toString().equals("0")) {
            biNoteTypeId.setError(getString(R.string.error_field_required));
            focusView = biNoteTypeId;
            cancel = true;
        } else if (TextUtils.isEmpty(biNote1.getEditableText())) {
            biNote1.setError(getString(R.string.error_field_required));
            focusView = biNote1;
            cancel = true;
        } /*else if (TextUtils.isEmpty(biNote2.getEditableText())) {
            biNote2.setError(getString(R.string.error_field_required));
            focusView = biNote2;
            cancel = true;
        } else if (TextUtils.isEmpty(biNote3.getEditableText())) {
            biNote3.setError(getString(R.string.error_field_required));
            focusView = biNote3;
            cancel = true;
        } */

        if (cancel) {
            focusView.requestFocus();
        } else {
            Calendar calss = Calendar.getInstance();
            SimpleDateFormat smp2 = new SimpleDateFormat("yyy-MM-dd hh:mm:ss");
            mCallPlanNote callPlanNote = new mCallPlanNote(biNoteId.getEditableText().toString(), Integer.valueOf(biNoteTypeId.getEditableText().toString()),biNoteTypeName,
                    biNoteVisitId.getEditableText().toString(),Integer.parseInt(CustId), biNote1.getEditableText().toString(), biNote2.getEditableText().toString(),
                    biNote3.getEditableText().toString(), smp2.format(calss.getTime()), String.valueOf(SalesId), 1);
            if (pDialog == null)
                pDialog = new ProgressDialog(this);
            pDialog.setMessage("Send Customer To Server ...\n Please Wait...");
            pDialog.show();
            callPlanController.updateCallPlanNoteToServer(String.valueOf(SalesId), callPlanNote);
            pDialog.dismiss();
            Toast.makeText(getApplicationContext(), "Save To Database Success"+ callPlanNote.getCallPlanNoteId(), Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            //getFragmentManager().popBackStack();
            finish();
        } else if (item.getItemId() == R.id.menuAddCst) {//save customer
            savedToDatabase();
            return true;
        } else if (item.getItemId() == R.id.menuRefresh) {
            Toast.makeText(getApplicationContext(), "Cancel", Toast.LENGTH_SHORT).show();
            cancelAdd();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
