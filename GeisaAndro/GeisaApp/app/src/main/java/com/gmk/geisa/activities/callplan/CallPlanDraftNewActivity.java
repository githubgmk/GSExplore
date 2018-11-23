package com.gmk.geisa.activities.callplan;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.gmk.geisa.R;
import com.gmk.geisa.adapter.CustomerDeleteListAdapter;
import com.gmk.geisa.controller.CallPlanController;
import com.gmk.geisa.model.mCallPlan;
import com.gmk.geisa.model.mCustomer;
import com.gmk.geisa.model.mSession;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class CallPlanDraftNewActivity extends AppCompatActivity implements com.kenmeidearu.materialdatetimepicker.date.DatePickerDialog.OnDateSetListener {
    public final static String sessionPlan = "sesionPlan";
    public final static String sessionUser = "sessionUser";
    public final static String sessionCustomer = "sessionProduct";
    public final static String sessionTypePlan = "sessionTypePlan";
    static final int SELECT_CUSTOMER = 111;
    //private mCustomer customer=new mCustomer();
    static final ArrayList<mCustomer> customers = new ArrayList<>();
    static final ArrayList<mCustomer> selectedCustomers = new ArrayList<>();
    private mSession session;
    private int typePlan, totalBefore;
    CallPlanController callPlanController;
    private ProgressDialog pDialog;
    Button addCust;
    TextInputEditText callPlanAddTgl;
    TextView totalCust,listnamaerror,listnama;
    RecyclerView recyclerView;
    private CustomerDeleteListAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draft_new);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("New Plan");
        addCust = (Button) findViewById(R.id.addCust);
        callPlanAddTgl = (TextInputEditText) findViewById(R.id.callPlanAddTgl);
        totalCust = (TextView) findViewById(R.id.totalCust);
        listnamaerror=(TextView)  findViewById(R.id.listnamaerror);
        listnama=(TextView) findViewById(R.id.listnama);
        callPlanController=CallPlanController.getInstance(this);

        this.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//lock screen orientation
        if (getIntent().getSerializableExtra(sessionUser) != null) {
            session = (mSession) getIntent().getSerializableExtra(sessionUser);
            // Log.e("isi user", " x" + session.getId());
        }
        if (getIntent().getSerializableExtra(sessionTypePlan) != null) {
            typePlan = (int) getIntent().getSerializableExtra(sessionTypePlan);
            if (typePlan != 0) {
                setTitle("New Template");
                callPlanAddTgl.setHint(getString(R.string.templateName));
            }
        }
        //nanti dibuatkan untuk list plan
        /*if (getIntent().getSerializableExtra(sessionPlan) != null) {
            typePlan = (mPlan) getIntent().getSerializableExtra(sessionPlan);
        }*/

        addCust.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                Intent inten = new Intent(getApplicationContext(), CallPlanDraftNewAddCustActivity.class);
                inten.putExtra(CallPlanDraftNewAddCustActivity.sessionUser, session);
                if (selectedCustomers.size() > 0) {
                    for (mCustomer cu : selectedCustomers) {
                        if (!cu.isSelected()) {
                            int idx = customers.indexOf(cu);
                            customers.get(idx).setSelected(true);
                        } else {
                            int idx = customers.indexOf(cu);
                            customers.get(idx).setSelected(false);
                        }
                    }
                }
                inten.putExtra(CallPlanDraftNewAddCustActivity.sessionCustomer, customers);
                startActivityForResult(inten, SELECT_CUSTOMER);
            }
        });

        callPlanAddTgl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (typePlan == 0)
                    changeTanggal(callPlanAddTgl.getText().toString());
            }
        });
        callPlanAddTgl.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    if (typePlan == 0)
                        changeTanggal(callPlanAddTgl.getText().toString());
                }
            }
        });

        setupRecyclerView();


    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_submit, menu);
        MenuItem item = menu.findItem(R.id.menuSubmit);
        item.setIcon(android.R.drawable.ic_menu_save);
        item.setTitle("Save");
        return true;
    }

    //untuk memantau tombol back
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            //getFragmentManager().popBackStack();
            finish();
        } else if (item.getItemId() == R.id.menuSubmit) {//save customer
            Toast.makeText(getApplicationContext(), "Submit Data", Toast.LENGTH_SHORT).show();
            //simpan ke database
            saveToDatabase();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == SELECT_CUSTOMER) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                if (null != data.getSerializableExtra(sessionCustomer)) {
                    customers.clear();
                    customers.addAll((ArrayList<mCustomer>) data.getSerializableExtra(sessionCustomer));
                    selectedCustomers.clear();
                    ArrayList<mCustomer> customer = new ArrayList<>();
                    customer.addAll(customers);
                    for (mCustomer cu : customer) {
                        final mCustomer cust = cu;
                        if (cust.isSelected()) {
                            cust.setSelected(false);
                            selectedCustomers.add(cust);
                            //cust.setSelected(true);
                        }
                    }
                }
                totalBefore = selectedCustomers.size();
                totalCust.setText(String.valueOf(totalBefore));
                setupRecyclerView();
            }
        }
    }

    private void setupRecyclerView() {
        recyclerView = (RecyclerView) findViewById(R.id.lvItems);
        recyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new CustomerDeleteListAdapter(selectedCustomers, this, totalCust);
        recyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this)
                .colorResId(R.color.colorPrimaryDark)
                .size(2)
                .build());
        recyclerView.setAdapter(mAdapter);
    }

    //change tanggal
    com.kenmeidearu.materialdatetimepicker.date.DatePickerDialog dpd;
    boolean tglkosong;
    Calendar tgldpt = Calendar.getInstance();

    void changeTanggal(String tanggal) {
        String dtStart = tanggal;
        //Log.e("isi start", dtStart);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        if (!TextUtils.isEmpty(dtStart) && !dtStart.equalsIgnoreCase("null")) {
            try {
                Date date = format.parse(dtStart);
                // Log.e("isi start", date.toString());
                tglkosong = false;
                tgldpt.setTime(date);
                callPlanAddTgl.setText(format.format(tgldpt.getTime()));
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                tglkosong = true;
                e.printStackTrace();
            }
        } else {
            tglkosong = true;
        }
        Calendar nows = Calendar.getInstance();
        Calendar now = tgldpt;
        if (tglkosong) {
            now = nows;
        }

        // Calendar.getInstance();
        dpd = com.kenmeidearu.materialdatetimepicker.date.DatePickerDialog.newInstance(
                this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH),
                nows.get(Calendar.HOUR_OF_DAY),
                nows.get(Calendar.MINUTE),
                false
        );
        dpd.dateOnly(true);
        //dpd.setMaxDate(nows);
        Calendar mindate = Calendar.getInstance();
        mindate.add(Calendar.DATE, 3);
        dpd.setMinDate(mindate);
        dpd.show(getFragmentManager(), "Datepickerdialog");
    }

    @Override
    public void onDateSet(com.kenmeidearu.materialdatetimepicker.date.DatePickerDialog view, int year, int monthOfYear, int dayOfMonth, int hourOfDay, int minute, int second) {
        setTanggal(year, monthOfYear, dayOfMonth, hourOfDay, minute, second);
    }

    void setTanggal(int year, int monthOfYear, int dayOfMonth, int hourOfDay, int minute, int second) {
        String hourString = hourOfDay < 10 ? "0" + hourOfDay : "" + hourOfDay;
        String minuteString = minute < 10 ? "0" + minute : "" + minute;
        String secondString = second < 10 ? "0" + second : "" + second;
        // String date = dayOfMonth + "/" + (++monthOfYear) + "/" + year + " " + hourString + ":" + minuteString + ":" + secondString;
        String date = year + "-" + (++monthOfYear) + "-" + dayOfMonth;
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            // cal.setTime();
            // Log.e("isi tanggal","xx"+(-monthOfYear)+","+monthOfYear+","+(+monthOfYear));
            cal.set(year, monthOfYear - 1, dayOfMonth, hourOfDay, minute, second);
        } catch (Exception ex) {

        }
        tgldpt = cal;
        callPlanAddTgl.setText(format.format(tgldpt.getTime()));
    }
    //callPlanAddTgl

    private  void saveToDatabase(){
        View focusView = null;
        boolean cancel=false;
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        callPlanAddTgl.setError(null);
        listnama.setError(null);
        listnamaerror.setVisibility(View.INVISIBLE);
        if(TextUtils.isEmpty(callPlanAddTgl.getEditableText())){
            callPlanAddTgl.setError(getString(R.string.error_field_required));
            focusView = callPlanAddTgl;
            cancel = true;
        }else if(totalBefore<=0 || selectedCustomers.size()<=0){
            listnama.setError(getString(R.string.error_field_required));
            listnamaerror.setVisibility(View.VISIBLE);
            focusView =addCust;
            cancel=false;
        }
        if(cancel){
            focusView.requestFocus();
        }else{
            String plan=callPlanAddTgl.getEditableText().toString();
            String tglcallplan;

            Calendar calss = Calendar.getInstance();
            SimpleDateFormat smp2 = new SimpleDateFormat("yyMMddhhmmss");
            SimpleDateFormat smp3 = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat smp4 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            tglcallplan=smp4.format(calss.getTime());
            ArrayList<mCallPlan> newCallplan=new ArrayList<>();
            int naik=1;
            for(mCustomer cu: selectedCustomers){
                String callplanId = String.valueOf(session.getId()).concat(String.valueOf(smp2.format(calss.getTime()))).concat(String.valueOf(naik));
                //Log.e("isi call plan",callplanId+","+plan+","+"Plan"+","+session.getId()+","+cu.getCustId()+","+ typePlan+","+tglcallplan);
                newCallplan.add(new mCallPlan(callplanId,plan,"Plan",session.getId(),cu.getCustId(), typePlan,"",tglcallplan,1));
                naik++;
            }
            if(callPlanController.InsertUpateAllCallPlanDraft(newCallplan)){
                selectedCustomers.clear();
                customers.clear();
                finish();
            }
        }

    }

}
