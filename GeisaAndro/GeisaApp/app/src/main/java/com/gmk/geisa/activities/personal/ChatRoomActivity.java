package com.gmk.geisa.activities.personal;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.gmk.geisa.R;
import com.gmk.geisa.controller.OtherController;
import com.gmk.geisa.databases.mDB;
import com.gmk.geisa.helper.Constants;
import com.gmk.geisa.helper.ThreadAdapter;
import com.gmk.geisa.model.Message;
import com.gmk.geisa.model.mPesan;
import com.gmk.geisa.model.mSession;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ChatRoomActivity extends AppCompatActivity implements View.OnClickListener {

    //Broadcast receiver to receive broadcasts
    private BroadcastReceiver mRegistrationBroadcastReceiver;

    //Progress dialog
    private ProgressDialog dialog;
    OtherController otherController;

    //Recyclerview objects
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;

    //ArrayList of messages to store the thread messages
    private ArrayList<Message> messages;

    //Button to send new message on the thread
    private Button buttonSend;

    //EditText to send new message on the thread
    private EditText editTextMessage;
    public final static String pesannya = "mpesan";
    public final static String login = "ceklogin";
    mPesan psn;
    mSession slogin;
    private mDB data;
    String APP_URL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Detail Coaching");
        this.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        data = mDB.getInstance(this);
        otherController = OtherController.getInstance(this);
        otherController.setCallBackSendDataPesan(callbackPesan);

        //Bundle bundle = new Bundle();
        if (getIntent().getSerializableExtra(pesannya) != null) {
            //kirim data locations ke tabfragement1;
            psn = (mPesan) getIntent().getSerializableExtra(pesannya);
            // Log.e("isi lokasi detail","masuk:"+locations.getNama());
            // bundle.putSerializable(LokasiDetail1Fragment.datalokasi,psn);
            setTitle("Coaching : " + psn.getJudul());
        }
        if (getIntent().getSerializableExtra(login) != null) {
            slogin = (mSession) getIntent().getSerializableExtra(login);
        }
        APP_URL = Constants.APP_URL;

        //Initializing recyclerview
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        //Initializing message arraylist
        messages = new ArrayList<>();

        //Calling function to fetch the existing messages on the thread
        fetchMessages1();

        //initializing button and edittext
        buttonSend = (Button) findViewById(R.id.buttonSend);
        editTextMessage = (EditText) findViewById(R.id.editTextMessage);

        //Adding listener to button
        buttonSend.setOnClickListener(this);
    }

    OtherController.CallBackSendDataPesan callbackPesan = new OtherController.CallBackSendDataPesan() {
        @Override
        public void resultReady(mPesan pesan, boolean hasil) {
            if (hasil) {

                for(Message p: messages){
                    if(p.getIdmessage()==pesan.getId()) {
                        p.setSend(hasil);
                        Log.e("pesan","update");
                        break;
                    }
                }
                adapter.notifyDataSetChanged();
            }
            if(pesan!=null) {
                if (data.insertUpdatePesan(pesan)) {
                    Log.e("hasil update", "sukses");
                }
            }
        }
    };

    private void fetchMessages1() {
        if (psn != null) {
            //Log.e("ada isi", "isi pesan");
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat smp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Log.e("ada isi", psn.getDateRead() + "," + psn.getStatusPesan() + "," + psn.getId());
            psn.setDateRead(smp.format(cal.getTime()));
            if (psn.getStatusPesan().equalsIgnoreCase("new")) {
                Log.e("ada isi ya", psn.getDateRead() + "," + psn.getStatusPesan() + "," + psn.getId());
                psn.setStatusPesan("read");
                psn.setStatusSend(true);
                otherController.sendPesanToServer(psn,true);

            }
            long userId = psn.getId();
            int idpengirim = psn.getIdPengirim();
            String message = psn.getIsiPesan();
            String name = psn.getPengirim();
            String sentAt = psn.getDateSend();
            Message messagObject = new Message(userId, idpengirim, message, sentAt, name,true);
            messages.add(messagObject);
            adapter = new ThreadAdapter(ChatRoomActivity.this, messages, slogin.getId());
            recyclerView.setAdapter(adapter);
            scrollToBottom();
           // Log.e("cari balasan", "cari balas " + userId);
            ArrayList<mPesan> pesanbalasan = data.getPesanByRef(String.valueOf(userId));
            for (mPesan psnbls : pesanbalasan) {
                if (psnbls != null) {
                    //Log.e("ada isi balas", psnbls.getIdPengirim() + "," + psnbls.getStatusPesan() + "," + psnbls.getId()+","+psnbls.getIsiPesan());
                    if (psnbls.getStatusPesan().equalsIgnoreCase("new") && psnbls.getIdPengirim()!=slogin.getId()) {
                    //    Log.e("ada isi balas", psnbls.getDateRead() + "," + psnbls.getStatusPesan() + "," + psnbls.getId());
                        psnbls.setStatusPesan("read");
                        psnbls.setStatusSend(true);
                        psnbls.setDateRead(smp.format(cal.getTime()));
                        otherController.sendPesanToServer(psnbls,true);

                    }
                   // Log.e("ada balasan", "ada balasan " + psnbls.getId());
                    Message messagObjectn = new Message(psnbls.getId(), psnbls.getIdPengirim(), psnbls.getIsiPesan(), psnbls.getDateSend(), psnbls.getPengirim(),psnbls.getStatusSend());
                    messages.add(messagObjectn);
                    adapter = new ThreadAdapter(ChatRoomActivity.this, messages, slogin.getId());
                    recyclerView.setAdapter(adapter);
                    scrollToBottom();
                }
            }

        }


    }


    //Processing message to add on the thread
    private void processMessage(String name, String message, String idpengirim, String id) {
        Message m = new Message(Long.parseLong(id), Integer.parseInt(idpengirim), message, getTimeStamp(), name);
        messages.add(m);
        scrollToBottom();
    }

    //This method will send the new message to the thread
    private void sendMessage() {
        final String message = editTextMessage.getText().toString().trim();
        if (message.equalsIgnoreCase(""))
            return;
        final int userId = slogin.getId();// AppController.getInstance().getSalesmanId();
        final String name = slogin.getNilai1();// AppController.getInstance().getUserName();
        final String sentAt = getTimeStamp();
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat smp = new SimpleDateFormat("yyMMddHHmmss");

        final String idnya = smp.format(cal.getTime());


        Message m = new Message(Long.valueOf(idnya), userId, message, sentAt, name);
        mPesan pesan = new mPesan(m.getIdmessage(), psn.getIdPenerima(), psn.getPenerima(), psn.getIdPengirim(), psn.getPengirim(),
                psn.getJudul(), m.getMessage(), "NON", psn.getTypePesan(), m.getSentAt(), "1900-01-01", "new", String.valueOf(psn.getId()), false);
        editTextMessage.setText("");

        otherController.sendPesanToServer(pesan,true);
        messages.add(m);
        adapter.notifyDataSetChanged();

        scrollToBottom();


        /*StringRequest stringRequest = new StringRequest(Request.Method.POST, APP_URL + "mas_area/sendMessage",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Log.e("hasil post",response);
                        try {
                            JSONObject jObj = new JSONObject(response);
                            boolean error = jObj.getBoolean("error");
                            int statuskirim=1;
                            if(!error) {
                                Toast.makeText(getApplicationContext(), "Sucess Send Message", Toast.LENGTH_SHORT).show();
                                statuskirim=0;
                            }
                            mPesan pesannan=new mPesan(Long.valueOf(idnya),SalesmanId,name,0,"DataCenter","Replay:" + psn.getJudul(),message,"",
                                    "fromDevice",sentAt,"","new",String.valueOf(psn.getId()),statuskirim);
                            data.insertUpdatePesan(pesannan);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id", String.valueOf(SalesmanId)+idnya);
                params.put("fcm_id", "");
                params.put("idpengirim", String.valueOf(SalesmanId));
                params.put("pengirim", name);
                params.put("idpenerima", "0");
                params.put("penerima", "DataCenter");
                params.put("judul", "Replay:" + psn.getJudul());
                params.put("isipesan", message);
                params.put("typepesan", "fromDevice");
                params.put("datesend", sentAt);
                params.put("dateread", "");
                params.put("statuspesan", "new");
                params.put("refid", String.valueOf(psn.getId()));
                params.put("statussend", String.valueOf("0"));
                return params;
            }
        };

        //Disabling retry to prevent duplicate messages
        int socketTimeout = 0;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);

        stringRequest.setRetryPolicy(policy);
        AppController.getInstance().addToRequestQueue(stringRequest);*/
    }

    //method to scroll the recyclerview to bottom
    private void scrollToBottom() {
        adapter.notifyDataSetChanged();
        if (adapter.getItemCount() > 1)
            recyclerView.getLayoutManager().smoothScrollToPosition(recyclerView, null, adapter.getItemCount() - 1);
    }

    //This method will return current timestamp
    public static String getTimeStamp() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(new Date());
    }

    //Registering broadcast receivers
    @Override
    protected void onResume() {
        super.onResume();
        Log.w("MainActivity", "onResume");
       /* LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(GCMRegistrationIntentService.REGISTRATION_SUCCESS));*/
      /*  LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(GCMRegistrationIntentService.REGISTRATION_ERROR));
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(GCMRegistrationIntentService.REGISTRATION_TOKEN_SENT));*/
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Constants.PUSH_NOTIFICATION));
    }


    //Unregistering receivers
    @Override
    protected void onPause() {
        super.onPause();
        Log.w("MainActivity", "onPause");
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
    }


    //Sending message onclick
    @Override
    public void onClick(View v) {
        if (v == buttonSend)
            sendMessage();
    }

    //Creating option menu to add logout feature
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Adding our menu to toolbar
        getMenuInflater().inflate(R.menu.activity_main_drawer, menu);
        return true;
    }

    //Adding logout option here
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            //AppController.getInstance().logout();
            finish();
            //startActivity(new Intent(this, MainActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }
}