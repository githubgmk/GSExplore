package com.gmk.geisa.controller;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.gmk.geisa.databases.mDB;
import com.gmk.geisa.helper.Constants;
import com.gmk.geisa.helper.Enkrip;
import com.gmk.geisa.model.PostResult;
import com.gmk.geisa.model.mSession;
import com.gmk.geisa.service.APIService;
import com.gmk.geisa.service.RetroClient;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by kenjin on 1/16/2017.
 */

public class SessionController {
    //harus ada
    private static mDB data;
    private APIService service;
    private static SessionController instance = null;
    static Enkrip enkrip;
    static Context ctx;
    static boolean primariON = true;

    public static SessionController getInstance(Context context) {
        if (instance == null) {
            instance = new SessionController();
        }
        // Log.e("primary on before",primariON +"xx");
        data = mDB.getInstance(context);
        ctx = context;
        enkrip = new Enkrip();
        MyTask task = new MyTask();
        task.execute();
        return instance;
    }

    private static class MyTask extends AsyncTask<String, Void, Boolean> {

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected Boolean doInBackground(String... params) {
            boolean hasil = false;
            try {
                SocketAddress sockaddr = new InetSocketAddress(Constants.APP_URL_SERVER, 80);
                // Create an unbound socket
                Socket sock = new Socket();
                int timeoutMs = 3000;   // 3 seconds
                sock.connect(sockaddr, timeoutMs);
                hasil = true;
            } catch (Exception e) {
                try {
                    SocketAddress sockaddr = new InetSocketAddress(Constants.APP_IP_SERVER, 80);
                    // Create an unbound socket
                    Socket sock = new Socket();
                    int timeoutMs = 2000;   // 3 seconds
                    sock.connect(sockaddr, timeoutMs);
                    hasil = true;
                } catch (Exception ex) {
                    hasil = false;
                }
            }
            return hasil;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            boolean bResponse = result;
            primariON = bResponse;
            // Log.e("primary on ater",primariON +"xx"+bResponse);

        }
    }
    //end harus ada

    public int getDraftPO() {
        return data.getDraftPO();
    }
    public boolean cekSession(String typesesion, int status) {
        return data.getSessionByStatus(typesesion, status);
    }

    public boolean updateSession(String typesesion, int status) {
        return data.updateSessionStatus(typesesion, status);
    }
    public boolean insertUpdateSessionWork(mSession session) {
        return data.insertUpdateSessionByNama(session);
    }
    public mSession getSession(String typesesion, int status) {
        return data.getSessionByVal(typesesion, status);
    }

    public  mSession getPersonById(int id){
        return data.getLogin(id);
    }

    public boolean UpdateUserLogin(mSession person){
        boolean hasil=false;
        if(data.updateLogin(person)){
            Toast.makeText(ctx.getApplicationContext(), "Data Berhasil Dirubah", Toast.LENGTH_SHORT).show();
            hasil=true;
            UpdateUserLoginServer(person);
       }else{
            hasil=false;
        }
        return  hasil;
    }

    public void UpdateUserLoginServer(final mSession login) {
        String cypertext;
        try {
            cypertext = enkrip.bytesToHex(enkrip.encrypt(login.getUserName() + ":" + login.getUserPass()));
        } catch (Exception e) {
            e.printStackTrace();
            cypertext = "";
        }
        if (primariON) {
            service = RetroClient.getClient(cypertext).create(APIService.class);
        } else {
            service = RetroClient.getClientBackup(cypertext).create(APIService.class);
        }

        Map<String, String> params = new HashMap<String, String>();
        params.put("SalesmanId", String.valueOf(login.getId()));
        params.put("UserName", login.getUserName());
        params.put("UserPass", login.getUserPass());
        params.put("Fcmid", login.getFcmid());
        params.put("Imei", login.getImei());
        params.put("OldPass", login.getOldPass());
        Call<PostResult> result = service.updateUserLoginServer(params);
        result.enqueue(new Callback<PostResult>() {
            @Override
            public void onResponse(Call<PostResult> call, Response<PostResult> response) {
                if (response.isSuccessful()) {
                    // Log.e("isi respon tracking", response.body().toString());
                    PostResult result = response.body();
                    if (result != null) {
                        if (result.isErrNot()) {
                           // Toast.makeText(ctx.getApplicationContext(), "Data Berhasil Terkirim Ke Server", Toast.LENGTH_SHORT).show();
                            login.setStatusChange(0);
                            data.updateLogin(login);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<PostResult> call, Throwable t) {
                Log.e("err update","err update pass "+t.toString());
               // Toast.makeText(ctx.getApplicationContext(), "Data Tidak Dapat Terkirim ke SERVER\n Silahkan ulangi beberapa saat lagi", Toast.LENGTH_SHORT).show();
            }


        });
    }

}
