package com.gmk.geisa.activities.report;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;

import com.gmk.geisa.R;
import com.gmk.geisa.adapter.ReportCallPlanListAdapter;
import com.gmk.geisa.controller.CallPlanController;
import com.gmk.geisa.model.mCallPlan;
import com.gmk.geisa.model.mSession;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ReportCallPlanActivity extends AppCompatActivity implements com.kenmeidearu.materialdatetimepicker.date.DatePickerDialog.OnDateSetListener {
    public final static String sessionUser = "sessionUser";
    public final static String sessionCustId = "sessionCustId";
    public final static String sessionCustName = "sessionCustName";
    private String custName;
    TextView custname;
    private  int custId;
    private mSession session;
    CallPlanController callPlanController;

    private ProgressDialog pDialog;
    private RecyclerView.LayoutManager mLayoutManager;

    private ReportCallPlanListAdapter adapter1;
    private SearchView findTXTEdit;
    TextInputEditText dateFrom, dateTo;
    Button btnProses;
    RecyclerView recyclerView;
    String startPeriode, endPeriode;
    Calendar tglawal;
    int SalesId, posisiEdit = 0;
    boolean startreport = true;
    SimpleDateFormat smp1 = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_po);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("All Report Call Plan");

        callPlanController = CallPlanController.getInstance(this);
        callPlanController.setCallBackGetDataAllCallUpdate(callBackCall);

        recyclerView = (RecyclerView) findViewById(R.id.lvItems);
        findTXTEdit = (SearchView) findViewById(R.id.findTXTEdit);
        dateFrom = (TextInputEditText) findViewById(R.id.dateFrom);
        dateTo = (TextInputEditText) findViewById(R.id.dateTo);
        btnProses = (Button) findViewById(R.id.btnProses);
        custname=(TextView) findViewById(R.id.custname);
        if (getIntent().getSerializableExtra(sessionUser) != null) {
            session = (mSession) getIntent().getSerializableExtra(sessionUser);
            SalesId = session.getId();
        }
        if (getIntent().getSerializableExtra(sessionCustId) != null) {
            custId = (int) getIntent().getSerializableExtra(sessionCustId);
        }
        if (getIntent().getSerializableExtra(sessionCustName) != null) {
            custName = (String) getIntent().getSerializableExtra(sessionCustName);
            custname.setText(custName);
        }
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

        tglawal = Calendar.getInstance();
        Calendar calendarfrom = Calendar.getInstance();
        if(custId!=0){
            calendarfrom.add(Calendar.DAY_OF_MONTH, -90);
        }else{
            calendarfrom.add(Calendar.DAY_OF_MONTH, -30);
        }
        endPeriode = smp1.format(tglawal.getTime());
        startPeriode = smp1.format(calendarfrom.getTime());
        dateFrom.setText(startPeriode);
        dateTo.setText(endPeriode);

        dateFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                posisiEdit = 1;
                changeTanggal(dateFrom.getText().toString());
            }
        });
        dateFrom.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus & !startreport) {
                    posisiEdit = 1;
                    changeTanggal(dateFrom.getText().toString());
                }
            }
        });
        dateTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                posisiEdit = 0;
                changeTanggal(dateTo.getText().toString());
            }
        });
        dateTo.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus & !startreport) {
                    posisiEdit = 0;
                    changeTanggal(dateTo.getText().toString());
                }
            }
        });
        btnProses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDataPOBetweenServer(dateFrom.getEditableText().toString(), dateTo.getEditableText().toString());
            }
        });

        getDataPOBetweenServer(dateFrom.getEditableText().toString(), dateTo.getEditableText().toString());

    }

    CallPlanController.CallBackGetDataAllCallUpdate callBackCall = new CallPlanController.CallBackGetDataAllCallUpdate() {
        @Override
        public void resultReady(ArrayList<mCallPlan> call, boolean hasil) {
            if (hasil) {
                if (call != null && call.size() > 0)
                    callPlanController.UpdateAllCallPlanStatusInServer(String.valueOf(SalesId), call);
                getDataPOBetweenLokal(String.valueOf(SalesId),dateFrom.getEditableText().toString(), dateTo.getEditableText().toString());
            }
            pDialog.dismiss();
        }
    };

    private void getDataPOBetweenServer(String from, String to) {
        if (pDialog == null)
            pDialog = new ProgressDialog(this);
        pDialog.setMessage("Getting Data From Server ...\n Please Wait...");
        pDialog.show();

        if (null != session) {
            callPlanController.checkNewUpdateBetweenAllCallPlanFromServer(String.valueOf(SalesId), dateFrom.getEditableText().toString(), dateTo.getEditableText().toString(),custId);
        } else {
            getDataPOBetweenLokal(String.valueOf(SalesId), from, to);
            pDialog.dismiss();
        }


    }

    private void getDataPOBetweenLokal(String salesId, String from, String to) {
        ArrayList<mCallPlan> po = callPlanController.getAllCallPlanBetween(salesId, from, to,custId);
        setupRecyclerView(po);
    }


    private void setupRecyclerView(ArrayList<mCallPlan> po) {

        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        adapter1 = new ReportCallPlanListAdapter(po, this, session);
        recyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this)
                .colorResId(R.color.colorPrimaryDark)
                .size(2)
                .build());
        recyclerView.setAdapter(adapter1);
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
        getDataPOBetweenLokal(String.valueOf(SalesId), dateFrom.getEditableText().toString(), dateTo.getEditableText().toString());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // getMenuInflater().inflate(R.menu.menu_refresh, menu);
        return true;
    }

    //untuk memantau tombol back
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            //getFragmentManager().popBackStack();
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    //change tanggal
    com.kenmeidearu.materialdatetimepicker.date.DatePickerDialog dpd;
    boolean tglkosong;
    Calendar tgldpt = Calendar.getInstance();

    void changeTanggal(String tanggal) {
        Calendar tglapa = Calendar.getInstance();
        tglapa.setTime(tglawal.getTime());
        // Log.e("tgl awal",tgldpt.getTime()+","+tglawal.getTime()+","+posisiEdit);
        startreport = false;
        String dtStart = tanggal;
        //Log.e("isi start", dtStart);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        if (!TextUtils.isEmpty(dtStart) && !dtStart.equalsIgnoreCase("null")) {
            try {
                Date date = format.parse(dtStart);
                // Log.e("isi start", date.toString());
                // Log.e("tgl awal before",tglawal.getTime()+","+posisiEdit+","+tglapa.getTime());
                tglkosong = false;
                tgldpt.setTime(date);
                /*if(posisiEdit==0) {
                   // tglawal.setTime(date);
                    dateFrom.setText(format.format(tgldpt.getTime()));
                }else{
                    dateTo.setText(format.format(tgldpt.getTime()));
                }*/
                // Log.e("tgl awal sbl lagi",tglawal.getTime()+","+posisiEdit+","+tglapa.getTime());
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                tglkosong = true;
                e.printStackTrace();
                Log.e("err", " x" + e.toString());
            }
        } else {
            tglkosong = true;
        }
        Calendar nows = Calendar.getInstance();
        Calendar now = tgldpt;
        if (tglkosong) {
            now = nows;
        }
        // Log.e("tgl awal sebelum",tglawal.getTime()+","+posisiEdit+","+tglapa.getTime());
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
        Calendar mindate = Calendar.getInstance();
        //Log.e("tgl awal lagi",tglawal.getTime()+","+posisiEdit+","+tglapa.getTime());
        if (posisiEdit == 0) {
            mindate.add(Calendar.YEAR, -1);
            dpd.setMaxDate(nows);
            dpd.setMinDate(mindate);
            // Log.e(" ttgl d",tglawal.getTime()+","+mindate.getTime()+",tgl"+dpd.getMinDate().getTime()+","+ dpd.getMaxDate().getTime());
        } else {
            // Log.e("tgl awal xx",","+tglapa.getTime());
            dpd.setMaxDate(tglapa);
            Calendar mindates = Calendar.getInstance();
            mindates.setTime(tglapa.getTime());
            if(custId!=0){
                mindates.add(Calendar.DATE, -90);
            }else{
                mindates.add(Calendar.DATE, -30);
            }

            dpd.setMinDate(mindates);
            // Log.e("tgl awal terus",tgldpt.getTime()+","+tglawal.getTime()+","+posisiEdit+","+dpd.getMinDate().getTime()+","+ mindates.getTime());
            // Log.e(" ttgl a",tglawal.getTime()+","+mindate.getTime()+",tgl"+dpd.getMinDate().getTime()+","+ dpd.getMaxDate().getTime());
        }
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
        if (posisiEdit == 0) {
            tglawal = tgldpt;
            // Log.e("tgl dpt",tgldpt.getTime()+","+tglawal.getTime());
            dateTo.setText(format.format(tgldpt.getTime()));
            dateFrom.setEnabled(true);
        } else {
            dateFrom.setText(format.format(tgldpt.getTime()));
        }
    }

}
