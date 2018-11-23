package com.gmk.geisa.activities.personal;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.gmk.geisa.R;
import com.gmk.geisa.adapter.ListAdapterPesan;
import com.gmk.geisa.controller.OtherController;
import com.gmk.geisa.databases.mDB;
import com.gmk.geisa.helper.Enkrip;
import com.gmk.geisa.model.mPesan;
import com.gmk.geisa.model.mSession;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class PesanActivity extends AppCompatActivity {

    public final static String sessionUser = "sessionUser";
    private ProgressDialog pDialog;
    private mDB data;
    //tambahan
    ArrayList<mPesan> pesen;
    OtherController otherController;
    private mSession session;
    int SalesId;
    private ListView lvItems;
    private SearchView findTXTEdit;
    ListAdapterPesan adapter;
    Calendar cal = Calendar.getInstance();
    SimpleDateFormat smp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    SimpleDateFormat smp1 = new SimpleDateFormat("yyyy-MM-dd");
    String tanggal;
    Enkrip enkrip;
    public boolean adaDataPesan = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lokasi);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Coaching");
        data = mDB.getInstance(this);
        enkrip = new Enkrip();
        tanggal = smp1.format(cal.getTime());
        this.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        findTXTEdit = (SearchView) findViewById(R.id.findTXTEdit);
        lvItems = (ListView) findViewById(R.id.lvItems);
        otherController = OtherController.getInstance(this);
        otherController.setCallBackGetDataPesan(callbackPesan);

        if (getIntent().getSerializableExtra(sessionUser) != null) {
            session = (mSession) getIntent().getSerializableExtra(sessionUser);
            SalesId = session.getId();
            otherController.getPesan(session.getId(), true);
        }

        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view,
                                    int position, long rowId) {
                // Retrieve item based on position
                // mLocation pesen = adapterItems.getItem(position);
                mPesan loc = pesen.get(position);
                Intent i = new Intent(getApplicationContext(), ChatRoomActivity.class);
                // Embed the serialized item
                i.putExtra(ChatRoomActivity.pesannya, loc);
                i.putExtra(ChatRoomActivity.login, session);
                // Start the activity
                startActivity(i);
                //Toast.makeText(getApplicationContext(),loc.getNamaAlat()+" : "+position,Toast.LENGTH_LONG).show();
            }
        });

        findTXTEdit.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (adapter != null)
                    adapter.filter(newText);
                return false;
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        refreshListView();
    }

    OtherController.CallBackGetDataPesan callbackPesan = new OtherController.CallBackGetDataPesan() {
        @Override
        public void resultReady(ArrayList<mPesan> pesan, boolean hasil) {
            if (hasil) {
                //refreshListView(pesan);
                refreshListView();
            }
            if (pDialog != null)
                pDialog.dismiss();
        }
    };

    public void refreshListView() {
        //mSession ss = data.getSessionByVal("ceklogin", 1);
        // Log.e("isi session",ss.getNilai7());
        pesen = data.getPesanAll(session.getId());
        //Log.e("isi jumlah sebelum",String.valueOf(pesen.size()));
        if (pesen != null && pesen.size() > 0) {
            //  Log.e("isi pesan jumlah", String.valueOf(pesen.size()));
            adaDataPesan = true;
            setAdapter();
        }
    }

    public void setAdapter() {
        adapter = new ListAdapterPesan(getApplicationContext(), pesen);
        adapter.notifyDataSetChanged();
        lvItems.setAdapter(adapter);

    }

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
            /*pDialog = new ProgressDialog(this);
            pDialog.setMessage("Loading Data");
            pDialog.setCancelable(false);
            pDialog.show();
            matikan=new AsyncRefresh().execute();*/

            if (pDialog == null)
                pDialog = new ProgressDialog(this);
            pDialog.setMessage("Getting Message From Server ...\n Please Wait...");
            pDialog.show();

            if (null != session) {
                otherController.getPesan(session.getId(), true);
            } else {
                pDialog.dismiss();
            }
            //startActivityForResult(inten, 11);
            // return true;
//        }else if(item.getProductId()==R.id.action_settings) {
//            Toast.makeText(getApplicationContext(), "Setting", Toast.LENGTH_SHORT).show();
//            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //untuk action done
    class DoneOnEditorActionListener implements OnEditorActionListener {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                return true;
            }
            return false;
        }
    }


}
