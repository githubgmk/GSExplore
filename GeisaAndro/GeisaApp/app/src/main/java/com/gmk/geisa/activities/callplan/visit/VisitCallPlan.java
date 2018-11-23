package com.gmk.geisa.activities.callplan.visit;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionMenu;
import com.gmk.geisa.R;
import com.gmk.geisa.activities.po.PoConfirmActivity;
import com.gmk.geisa.activities.po.PoNewActivity;
import com.gmk.geisa.activities.product.ProductActivity;
import com.gmk.geisa.activities.report.ReportMain;
import com.gmk.geisa.activities.support.ResendActivity;
import com.gmk.geisa.activities.support.SyncActivity;
import com.gmk.geisa.adapter.MapDialogFragment;
import com.gmk.geisa.adapter.MapDialogFragmentNewMap;
import com.gmk.geisa.controller.CallPlanController;
import com.gmk.geisa.controller.CustomerController;
import com.gmk.geisa.controller.SessionController;
import com.gmk.geisa.model.mCallPlan;
import com.gmk.geisa.model.mCustomer;
import com.gmk.geisa.model.mSession;

public class VisitCallPlan extends AppCompatActivity implements MapDialogFragmentNewMap.EditDialogListener {
    // Remove the below line after defining your own ad unit ID.
    TextView custName;
    SessionController sessionController;
    CustomerController customerController;
    CallPlanController callPlanController;
    String idCallPlan, idCustomer = "0", SalesId, CustomerNama = "";
    Button btnVisitTd, btnVisitPo, btnVisitCs, btnVisitBi, btnVisitEx, btnVisitReport;
    com.github.clans.fab.FloatingActionButton fabProduct, fabSalesConfirm, fabresend, fabsyncdata, fabpolice, fabtol;
    FloatingActionMenu fab;
    private mSession sessionUser;
    private mCustomer customer;
    boolean CheckoutTask = false;
    mCallPlan callPlan;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_visit);
        setTitle("Customer Visit");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = (FloatingActionMenu) findViewById(R.id.fab);
        fabProduct = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.fabProduct);
        fabSalesConfirm = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.fabSalesConfirm);
        fabresend = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.fabresend);
        custName = (TextView) findViewById(R.id.custName);
        fabsyncdata = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.fabsyncdata);
        /*fabpolice = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.fabpolice);
        fabtol = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.fabtol);*/

        btnVisitTd = (Button) findViewById(R.id.btnVisitTd);
        btnVisitPo = (Button) findViewById(R.id.btnVisitPo);
        btnVisitCs = (Button) findViewById(R.id.btnVisitCs);
        btnVisitBi = (Button) findViewById(R.id.btnVisitBi);
        btnVisitEx = (Button) findViewById(R.id.btnVisitEx);
        btnVisitReport = (Button) findViewById(R.id.btnVisitReport);
        sessionController = SessionController.getInstance(this);
        customerController = CustomerController.getInstance(this);
        callPlanController = CallPlanController.getInstance(this);
        sessionUser = sessionController.getSession("login", 1);
        if (sessionUser != null) {
            SalesId = String.valueOf(sessionUser.getId());
        }
        mSession session = sessionController.getSession("CallPlan", 1); //nilai4 ambil id callplan;
        if (session != null) {
            idCallPlan = session.getNilai4();
            idCustomer = session.getNilai5();
            customer = customerController.getCustomerByCustIdAndSalesId(idCustomer, SalesId);
            if (customer != null) {
                CustomerNama = customer.getCustName();
                custName.setText(CustomerNama);
            }
            callPlan = callPlanController.getCallPlanById(idCallPlan);
            //Log.e("idi callpan","id"+idCallPlan);
        }
        btnVisitTd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inten = new Intent(VisitCallPlan.this, TodoActivity.class);//berpindah ke activity yang lain dgn data
                inten.putExtra(TodoActivity.sessionPlan, idCallPlan);
                inten.putExtra(TodoActivity.sessionCustomer, idCustomer);
                inten.putExtra(TodoActivity.sessionUser, sessionUser);
                startActivityForResult(inten, 2);
            }
        });
        btnVisitPo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(),"test",Toast.LENGTH_SHORT).show();
                Intent inten = new Intent(VisitCallPlan.this, PoNewActivity.class);//berpindah ke activity yang lain dgn data
                inten.putExtra(PoNewActivity.sessionPlan, idCallPlan);
                inten.putExtra(PoNewActivity.sessionCustomer, idCustomer);
                inten.putExtra(PoNewActivity.sessionUser, sessionUser);
                startActivityForResult(inten, 2);
            }
        });
        btnVisitCs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inten = new Intent(VisitCallPlan.this, CustomerService.class);//berpindah ke activity yang lain dgn data
                inten.putExtra(CustomerService.sessionCustName, CustomerNama);
                startActivity(inten);
            }
        });
        btnVisitBi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inten = new Intent(VisitCallPlan.this, BiActivity.class);//berpindah ke activity yang lain dgn data
                inten.putExtra(BiActivity.callPlanId, idCallPlan);
                inten.putExtra(BiActivity.sessionCust, idCustomer);
                inten.putExtra(BiActivity.sessionUser, sessionUser);
                startActivityForResult(inten, 4);
            }
        });
        btnVisitEx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inten = new Intent(VisitCallPlan.this, Expansi.class);//berpindah ke activity yang lain dgn data
                startActivity(inten);
            }
        });
        btnVisitReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inten = new Intent(VisitCallPlan.this, ReportMain.class);//berpindah ke activity yang lain dgn data
                inten.putExtra(ReportMain.sessionCust, customer);
                inten.putExtra(ReportMain.sessionUser, sessionUser);
                startActivity(inten);
            }
        });

        fabProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inten = new Intent(VisitCallPlan.this, ProductActivity.class);
                inten.putExtra(SyncActivity.sessionUser, sessionUser);
                startActivityForResult(inten, 7);
            }
        });

        fabSalesConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inten = new Intent(VisitCallPlan.this, PoConfirmActivity.class);
                // inten.putExtra(PoNewActivity.sessionPlan,idCallPlan);
                // inten.putExtra(PoNewActivity.sessionCustomer,idCustomer);
                inten.putExtra(PoConfirmActivity.sessionUser, sessionUser);
                // inten.putExtra(PoNewActivity.sessionSalesConfirm,true);
                startActivityForResult(inten, 8);
            }
        });
        fabresend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inten = new Intent(VisitCallPlan.this, ResendActivity.class);
                inten.putExtra(ResendActivity.sessionUser, sessionUser);
                startActivityForResult(inten, 9);
            }
        });

        fabsyncdata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inten = new Intent(VisitCallPlan.this, SyncActivity.class);
                inten.putExtra(SyncActivity.sessionUser, sessionUser);
                startActivityForResult(inten, 9);
            }
        });
        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_submit, menu);
        MenuItem item = menu.findItem(R.id.menuSubmit);
        item.setTitle("CheckOut");
        item.setIcon(R.drawable.logout);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.menuSubmit) {
            if (!CheckoutTask)
                confirmCekout();
            // showEditDialog();
            return true;
        }else{
            if (!CheckoutTask)
                confirmCekout();
        }

        return super.onOptionsItemSelected(item);
    }

    private void showEditDialog() {
        // SupportMapFragment
        CheckoutTask = true;
        FragmentManager fm = getSupportFragmentManager();
        MapDialogFragmentNewMap df = new MapDialogFragmentNewMap(VisitCallPlan.this, "checkOut");
        df.show(fm, "fragment tab");

    }

    private void confirmCekout() {
        //imgEdit = true;
        final Context context = VisitCallPlan.this;
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.preview_checkout, null);
        final TextInputEditText infovisit = (TextInputEditText) dialogView.findViewById(R.id.infovisit);
        //final Button btnDel = (Button) dialogView.findViewById(R.id.btnDel);
        final Button btnCheckout = (Button) dialogView.findViewById(R.id.btnCheckout);

        AlertDialog.Builder ad = new AlertDialog.Builder(context);
        //ad.setTitle(title);
        //ad.setMessage(message);
        ad.setView(dialogView);
        final AlertDialog dialog = ad.create();
        if (callPlan != null) {
            if (callPlan.getNotes() != null)
                infovisit.setText(callPlan.getNotes());
        }

        /*btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });*/
        btnCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean cancel = false;
                View focusView = null;
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                // mSession sessions = sessionController.getSession("CallPlan", 1);
                //Log.e("cek out","cek out");
                // if (sessions != null) {
                String isivisit = infovisit.getEditableText().toString();
                if (null != callPlan) {
                    callPlan.setNotes(isivisit);
                    if (callPlanController.InsertUpateAllCallPlan(callPlan)) { //error call back ged data
                        dialog.dismiss();
                        showEditDialog();
                    } else {
                        Toast.makeText(context, "Error on update Notes", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        showEditDialog();
                    }
                    // Log.e("isi cek out","cek masuk");
                } else {
                    Toast.makeText(context, "No CallPlan Record...", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    showEditDialog();
                }
                //}

            }
        });

        //
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        // dialog.setCanceledOnTouchOutside(false);
        dialog.show();

    }


    @Override
    public void updateResult(String inputText) {
        if (!TextUtils.isEmpty(inputText)) {
            if (inputText.equalsIgnoreCase("true")) {
                // Toast.makeText(getApplicationContext(),"keluar juga",Toast.LENGTH_SHORT).show();
                mSession session = sessionController.getSession("CallPlan", 1); //nilai4 ambil id callplan;
                session.setNilai4("0");
                session.setNilai5("0");
                session.setStatus(0);
                sessionController.insertUpdateSessionWork(session);
                finish();
            } else {
                CheckoutTask = false;
                Toast.makeText(getApplicationContext(), "Cancel Check Out", Toast.LENGTH_SHORT).show();
            }
        } else {
            CheckoutTask = false;
            Toast.makeText(getApplicationContext(), "Cancel Check Out", Toast.LENGTH_SHORT).show();
        }
    }


}
