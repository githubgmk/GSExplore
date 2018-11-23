package com.gmk.geisa.controller;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.gmk.geisa.databases.mDB;
import com.gmk.geisa.helper.Constants;
import com.gmk.geisa.helper.Enkrip;
import com.gmk.geisa.model.Post.rUserLogin;
import com.gmk.geisa.model.PostResult;
import com.gmk.geisa.model.mSales;
import com.gmk.geisa.model.mSession;
import com.gmk.geisa.service.APIService;
import com.gmk.geisa.service.RetroClient;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by kenjin on 1/10/2017.
 */

public class RegisterController {
    /*default setting*/
    private static mDB data;
    private APIService service;
    private static RegisterController instance = null;
    static Enkrip enkrip;
    static Context ctx;
    static boolean primariON = true;

    public static RegisterController getInstance(Context context) {
        if (instance == null) {
            instance = new RegisterController();
        }
        // Log.e("primary on before",primariON +"xx");
        data = mDB.getInstance(context);
        ctx = context;
        enkrip = new Enkrip();
        MyTask task = new MyTask();
        task.execute();
        return instance;
    }

    public void cekinternet() {
        MyTask task = new MyTask();
        task.execute();
    }

    //callback get user
    private CallBackGetUsers callBackGetUsers;

    public void setCallBackGetUsers(CallBackGetUsers callback) {
        this.callBackGetUsers = callback;
    }

    public interface CallBackGetUsers {
        public void resultReady(ArrayList<mSession> users, PostResult respon);
    }

    private CallBackGetForgetPassword callBackGetForgetPassword;

    public void setCallBackGetForgetPassword(CallBackGetForgetPassword callback) {
        this.callBackGetForgetPassword = callback;
    }

    public interface CallBackGetForgetPassword {
        public void resultReady(PostResult respon);
    }


    //callback ceklogin
    private CallBackGetLogin callBackgetLogin;

    public void setCallBackgetLogin(CallBackGetLogin callback) {
        this.callBackgetLogin = callback;
    }

    public interface CallBackGetLogin {
        public void resultReady(PostResult result);
    }

    /*end default setting*/
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

                // This method will block no more than timeoutMs.
                // If the timeout occurs, SocketTimeoutException is thrown.
                int timeoutMs = 3000;   // 3 seconds
                sock.connect(sockaddr, timeoutMs);
                hasil = true;
            } catch (Exception e) {
                try {

                    SocketAddress sockaddr = new InetSocketAddress(Constants.APP_IP_SERVER, 80);
                    // Create an unbound socket
                    Socket sock = new Socket();

                    // This method will block no more than timeoutMs.
                    // If the timeout occurs, SocketTimeoutException is thrown.
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
            Log.e("primary on ater",primariON +"xx"+bResponse);

        }
    }
    //mulai

    public void getLogin(final String username, final String password, final String email, String emei, final String serial, final boolean newregister) {
        Log.e("isi regis", newregister + "xx");
        try {
            if (newregister) {
                String cypertext;
                try {
                    cypertext = enkrip.bytesToHex(enkrip.encrypt(username + ":" + password));
                    Log.e("sn", cypertext);

                } catch (Exception e) {
                    e.printStackTrace();
                    cypertext = "";
                }
                Log.e("primary on", primariON + "xx");
                if (primariON) {
                    service = RetroClient.getClient(cypertext).create(APIService.class);
                } else {
                    service = RetroClient.getClientBackup(cypertext).create(APIService.class);
                }


                Map<String, String> params = new HashMap<String, String>();
                String fcm_id = FirebaseInstanceId.getInstance().getToken();
                params.put("tag", "register");
                params.put("name", username);
                Log.e("cek generic", "G:" + Build.FINGERPRINT);
                if (Build.FINGERPRINT.startsWith("generic") || Build.FINGERPRINT.contains("generic")) {
                    params.put("imei", "emuator");
                    emei="emulator";
                } else {
                    params.put("imei", emei);
                }
                params.put("serialnumber", serial);
                if (null != fcm_id) {
                    params.put("fcmid", fcm_id);
                }
                params.put("user_email", email);
                params.put("soft", ctx.getApplicationContext().getPackageName());
                Call<PostResult> userlist = service.registerUser(params, Constants.REG_URL + "registrasi.php");
                final String finalEmei = emei;
                userlist.enqueue(new Callback<PostResult>() {
                    @Override
                    public void onResponse(Call<PostResult> call, Response<PostResult> response) {
                        if (response.isSuccessful()) {
                            Log.e("isi respon", response.body().toString());
                            PostResult result = response.body();
                            if (result != null) {
                                result.setMsgC(username);
                                result.setMsgD(password);
                                result.setMsgE(email);
                                result.setMsgF(finalEmei);
                                result.setMsgG(serial);
                                result.setMsgBol(newregister);
                                Log.e("isi result", finalEmei);
                                mSession session = new mSession();
                                session.setId(result.getErrId());
                                session.setNama(result.getErrTag());
                                session.setUserEmail(email);
                                session.setStatus(1);
                                session.setNilai1(result.getMsgA());
                                session.setNilai2(result.getMsgB());
                                session.setNilai3(serial);
                                session.setNilai4(finalEmei);
                                Log.e("isi ceklogin masuk", session.getId() + "," + session.getNama() + "," + session.getNilai2() + "," + session.getNilai3() + "," + session.getNilai4());
                                //create dataserial
                                data.insertUpdateSessionByNama(session);
                            }
                            callBackgetLogin.resultReady(result);

                        } else {
                            PostResult result = new PostResult(false, response.code() + "");
                            if (result != null) {
                                result.setMsgC(username);
                                result.setMsgD(password);
                                result.setMsgE(email);
                                result.setMsgF(finalEmei);
                                result.setMsgG(serial);
                                result.setMsgBol(newregister);
                            }
                            callBackgetLogin.resultReady(result);
                        }
                    }

                    @Override
                    public void onFailure(Call<PostResult> call, Throwable t) {
                        Log.e("REST", t.getMessage());

                        PostResult result = new PostResult(false, t.getMessage());
                        if (result != null) {
                            result.setMsgC(username);
                            result.setMsgD(password);
                            result.setMsgE(email);
                            result.setMsgF(finalEmei);
                            result.setMsgG(serial);
                            result.setMsgBol(newregister);
                        }
                        callBackgetLogin.resultReady(result);
                    }

                });
            } else {
                Log.e("bukan register","bukan register"+newregister);
                //cek user login diserver

                PostResult result = new PostResult(true, "OK");
                result.setErrMsg("Login");
                result.setMsgC(username);
                result.setMsgD(password);
                result.setMsgE(email);
                result.setMsgF(emei);
                result.setMsgG(serial);
                result.setMsgBol(newregister);
                callBackgetLogin.resultReady(result);
            }
        } catch (Exception ex) {
            Log.e("bukan err", "bukan register" + ex.toString());
            PostResult result = new PostResult(false, "ERR");
            result.setErrMsg(ex.getMessage());
            result.setMsgC(username);
            result.setMsgD(password);
            result.setMsgE(email);
            result.setMsgF(emei);
            result.setMsgG(serial);
            result.setMsgBol(newregister);
            callBackgetLogin.resultReady(result);
        }

    }

    public void GetUserFromServer(final String username, final String pass, final String email, final String emei, final String serial, final boolean reg) {

        String cypertext;
        try {
            cypertext = enkrip.bytesToHex(enkrip.encrypt(username + ":" + pass));
            Log.e("sn", cypertext);

        } catch (Exception e) {
            e.printStackTrace();
            cypertext = "";
        }
        if (primariON) {
            service = RetroClient.getClient(cypertext).create(APIService.class);
        } else {
            service = RetroClient.getClientBackup(cypertext).create(APIService.class);
        }
        rUserLogin params = new rUserLogin();
        params.setUname(username);
        params.setPass(pass);
        /*Map<String, String> params = new HashMap<String, String>();
        params.put("uname", username);
        params.put("pass", pass);*/

        final Call<ArrayList<mSales>> userlist = service.getUser(params);
        userlist.enqueue(new Callback<ArrayList<mSales>>() {
            @Override
            public void onResponse(Call<ArrayList<mSales>> call, Response<ArrayList<mSales>> response) {
                Log.e("isi respon hasil", response.body().toString() + "," + response.code());
                if (response.isSuccessful()) {
                    PostResult hasil = new PostResult(false, "OK");
                    hasil.setMsgC(username);
                    hasil.setMsgD(pass);
                    hasil.setMsgE(email);
                    hasil.setMsgF(emei);
                    hasil.setMsgG(serial);
                    hasil.setMsgBol(reg);
                    ArrayList<mSession> sessions = new ArrayList<mSession>();
                    ArrayList<mSales> result = response.body();
                    Log.e("isi respin", response.body().toString());
                    if (result != null && result.size() > 0) {
                        Log.e("malah kesini","malah kesini"+result.size());
                        for (mSales usr : result) {
                            mSession msession = null;
                            if (usr != null) {
                                msession = new mSession(usr.getSalesmanId(), usr.getUserName(), usr.getUserPass(), String.valueOf(usr.getStatusId()), String.valueOf(usr.getSpvId()), String.valueOf(usr.getSalesmanLevelId()),
                                        String.valueOf(usr.getAreaId()), usr.getEmail(), usr.getSalesmanName(), usr.getFcmId(), usr.getImei(), String.valueOf(usr.getRecId()));
                            }

                            if (msession != null) {
                                sessions.add(msession);
                                //tambahkan emei,fcm,email jika kosong, dan siap2 update ke server pastikan userlogin ngak boleh sama dengan yang lain
                                if (null == msession.getImei() || TextUtils.isEmpty(msession.getImei()) || msession.getImei().equalsIgnoreCase("")) {
                                    msession.setImei(emei);
                                    msession.setStatusChange(1);
                                    // Log.e("imei kosong",msession.getImei());
                                }
                                if (null == msession.getUserEmail() || TextUtils.isEmpty(msession.getUserEmail())) {
                                    msession.setUserEmail(email);
                                    msession.setStatusChange(1);
                                    //Log.e("email kosong",msession.getUserEmail());
                                }
                                if (null == msession.getFcmid() || TextUtils.isEmpty(msession.getFcmid())) {
                                    String fcmid = FirebaseInstanceId.getInstance().getToken();
                                    msession.setFcmid(fcmid);
                                    msession.setStatusChange(1);
                                    // Log.e("fcm kosong",msession.getFcmid());
                                }
                                Log.e("isi session",
                                        msession.getId() + "," + msession.getUserName() + "," + msession.getUserPass() + "," + msession.getUserStatus() + "," +
                                                msession.getUserSPV() + "," + msession.getUserLevel() + "," + msession.getAreaId() + "," + msession.getUserEmail() + "," +
                                                msession.getUserMacAddress() + "," + msession.getUserAlias());
                                data.insertUpdateLogin(msession);
                               /* if(msession.getStatusChange()==1){
                                    updateUserServer(msession);
                                }*/
                                hasil.setErrNot(true);
                                hasil.setErrMsg("OK");

                            } else {
                                hasil.setErrMsg("Username Not Registered, Please Contact Administrator");
                            }
                        }
                        callBackGetUsers.resultReady(sessions, hasil);
                    } else {
                        Log.e("kesini","kesini");
                        if (serial.equalsIgnoreCase("CEKLOGIN")) {
                            hasil.setErrMsg("You Don't Have Authority To Access This App, Please Contact Administrator");
                        } else {
                            hasil.setErrMsg("Username Not Registered, Please Contact Administrator");
                        }
                        callBackGetUsers.resultReady(null, hasil);
                    }

                } else {
                    PostResult hasil = new PostResult(false, "Username Not Registered, Please Contact Administrator");
                    hasil.setMsgC(username);
                    hasil.setMsgD(pass);
                    hasil.setMsgE(email);
                    hasil.setMsgF(emei);
                    hasil.setMsgG(serial);
                    hasil.setMsgBol(reg);
                    callBackGetUsers.resultReady(null, hasil);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<mSales>> call, Throwable t) {
                Log.e("REST err gloigin", t.getMessage() + "," + t.toString());
                PostResult result = new PostResult(false, t.toString());
                if (result != null) {
                    result.setMsgC(username);
                    result.setMsgD(pass);
                    result.setMsgE(email);
                    result.setMsgF(emei);
                    result.setMsgBol(reg);
                }
                callBackGetUsers.resultReady(null, result);
            }


        });
    }

    public void updateUserServer(final mSales login) {
        String cypertext;
        try {
            cypertext = enkrip.bytesToHex(enkrip.encrypt(login.getUserName() + ":" + login.getUserPass()));
            Log.e("sn", cypertext);

        } catch (Exception e) {
            e.printStackTrace();
            cypertext = "";
        }
        if (primariON) {
            service = RetroClient.getClient(cypertext).create(APIService.class);
        } else {
            service = RetroClient.getClientBackup(cypertext).create(APIService.class);
        }
        //Log.e("update login",login.getId()+","+login.getFcmid()+","+login.getImei()+","+login.getUserEmail());
        Call<mSales> result = service.updateUserServer(login);
        result.enqueue(new Callback<mSales>() {
            @Override
            public void onResponse(Call<mSales> call, Response<mSales> response) {
                if (response.isSuccessful()) {
                    Log.e("isi respon", response.body().toString());
                    mSales usr = response.body();
                    if (usr != null && usr.getEmail() != null) {
                        mSession msession = new mSession(usr.getSalesmanId(), usr.getUserName(), usr.getUserPass(), String.valueOf(usr.getStatusId()), String.valueOf(usr.getSpvId()), String.valueOf(usr.getSalesmanLevelId()),
                                String.valueOf(usr.getAreaId()), usr.getEmail(), usr.getSalesmanName(), usr.getFcmId(), usr.getImei(), String.valueOf(usr.getRecId()));
                        if (msession != null)
                            data.updateLogin(msession);
                    }
                }
            }

            @Override
            public void onFailure(Call<mSales> call, Throwable t) {
                Log.e("REST", t.getMessage());
            }


        });
    }

    public void resetPassword(String username) {
        String cypertext;
        try {
            cypertext = enkrip.bytesToHex(enkrip.encrypt(username + ":" + username));
            // Log.e("sn", cypertext);

        } catch (Exception e) {
            e.printStackTrace();
            cypertext = "";
        }
        if (primariON) {
            service = RetroClient.getClient(cypertext).create(APIService.class);
        } else {
            service = RetroClient.getClientBackup(cypertext).create(APIService.class);
        }
        //Log.e("update login",login.getId()+","+login.getFcmid()+","+login.getImei()+","+login.getUserEmail());
        Map<String, String> params = new HashMap<String, String>();
        params.put("userlogin", username);
        Call<PostResult> result = service.UserForgetPassword(params);
        result.enqueue(new Callback<PostResult>() {
            @Override
            public void onResponse(Call<PostResult> call, Response<PostResult> response) {
                if (response.isSuccessful()) {
                    //  Log.e("isi respon", response.body().toString());
                    PostResult result = response.body();
                    if (result != null) {
                        callBackGetForgetPassword.resultReady(result);
                    } else {
                        PostResult results = new PostResult(false, "0");
                        callBackGetForgetPassword.resultReady(results);
                    }
                } else {
                    PostResult results = new PostResult(false, "0");
                    callBackGetForgetPassword.resultReady(results);
                }
            }

            @Override
            public void onFailure(Call<PostResult> call, Throwable t) {
                // Log.e("REST", t.getMessage());
                PostResult results = new PostResult(false, "0");
                callBackGetForgetPassword.resultReady(results);
            }


        });
    }

    public  boolean checkUpdateApps(){
        boolean hasil=false;

        return  hasil;

    }



}
