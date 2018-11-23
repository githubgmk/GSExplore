package com.gmk.geisa.Class;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import com.gmk.geisa.databases.mDB;
import com.gmk.geisa.helper.CallHttp;
import com.gmk.geisa.helper.Constants;
import com.gmk.geisa.helper.Enkrip;
import com.gmk.geisa.model.mPesan;
import com.gmk.geisa.model.mSession;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by kenjin on 7/22/2016.
 */
public class SyncDataClass {
    private final String BACKUP_URL;
    private final String REGISTER_URL;
    private final String APP_URL;
   // private ObjectMapper objectMapper = null;
   // private JsonFactory jsonFactory = null;
  //  private JsonParser jp = null;
    private CallHttp http = new CallHttp();
    String TAG = getClass().toString();
    private mDB data;
    private mSession session;
    private ProgressDialog pDialog;
    private String userid;
    private String bbwsid;
    Context context;
    Enkrip enkrip;

    public SyncDataClass(Context context) {
        this.context = context;
        data = mDB.getInstance(this.context);
     //   objectMapper = new ObjectMapper();
    //    jsonFactory = new JsonFactory();
        session = new mSession();
        enkrip = new Enkrip();
        APP_URL = Constants.APP_URL;
        BACKUP_URL = Constants.BACKUP_URL;
        REGISTER_URL  = Constants.REG_URL;
        getLoginValue();
    }

    //ceklogin & user
    private void getLoginValue() {
        mSession session = data.getSessionByVal("ceklogin", 1);
        if (session != null) {
            userid = session.getNilai1();
            bbwsid = session.getNilai7();
        }

    }
    public String registerToServer(final String name,final String email, final String emei, final String serialnumber) {
        // Log.e("isi register","sampe register");
        // REGISTER_URL = context.getString(R.string.REGISTER_URL);
        Map<String, String> params = new HashMap<String, String>();
        String fcm_id = FirebaseInstanceId.getInstance().getToken();
        params.put("tag", "register");
        params.put("name", name);
        params.put("imei", emei);
        params.put("serialnumber", serialnumber);
        params.put("fcmid", fcm_id);
        params.put("user_email",email);
        String hsl = "";
        // Log.e("fcm",fcm_id +" ");
        String cypertext;
        try {
            cypertext = enkrip.bytesToHex(enkrip.encrypt(serialnumber + ":" + emei));
            Log.e("sn", cypertext);

        } catch (Exception e) {
            e.printStackTrace();
            cypertext = "";
        }
        if (fcm_id != null) {
            hsl = registerApp(params, cypertext);

            if (hsl.equalsIgnoreCase("")) {
                Log.e("isi register", "sukses register");
                return "";
            } else {
                Log.e("isi register nya", hsl);
                return hsl;
            }
        } else {
            Log.e("isi fcm null nya", fcm_id + " ");
            return "Network";
        }
    }
    public String registerToServer(final String name, final String emei, final String serialnumber) {
        // Log.e("isi register","sampe register");
        // REGISTER_URL = context.getString(R.string.REGISTER_URL);
        Map<String, String> params = new HashMap<String, String>();
        String fcm_id = FirebaseInstanceId.getInstance().getToken();
        params.put("tag", "register");
        params.put("name", name);
        params.put("imei", emei);
        params.put("serialnumber", serialnumber);
        params.put("fcmid", fcm_id);
        String hsl = "";
        // Log.e("fcm",fcm_id +" ");
        String cypertext;
        try {
            cypertext = enkrip.bytesToHex(enkrip.encrypt(serialnumber + ":" + emei));
            Log.e("sn", cypertext);

        } catch (Exception e) {
            e.printStackTrace();
            cypertext = "";
        }
        if (fcm_id != null) {
            hsl = registerApp(params, cypertext);

            if (hsl.equalsIgnoreCase("")) {
                Log.e("isi register", "sukses register");
                return "";
            } else {
                Log.e("isi register nya", hsl);
                return hsl;
            }
        } else {
            Log.e("isi fcm null nya", fcm_id + " ");
            return "Network";
        }
    }

    public boolean updateUserServer(final mSession login) {
        boolean error = true;
        if (login != null) {
            try {
                String cypertext;
                try {
                    cypertext = enkrip.bytesToHex(enkrip.encrypt(login.getUserName() + ":" + login.getImei()));
                    Log.e("sn", cypertext);

                } catch (Exception e) {
                    e.printStackTrace();
                    cypertext = "";
                }
                Map<String, String> params = new HashMap<String, String>();
                params.put("user_id", String.valueOf(login.getId()));
                params.put("fcmid", login.getFcmid());
                params.put("imei", login.getImei());
                params.put("user_email", login.getUserEmail());
                String json = http.postHttp(APP_URL + "mas_user/update", params, cypertext);
                JSONObject jObj = new JSONObject(json);
                error = jObj.getBoolean("error");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (!error) {
            return true;
        } else {
            return false;
        }
    }

    private String registerApp(Map<String, String> params, String key) {
        String hasil = "";
        try {

            String json = http.postHttp(REGISTER_URL, params, key);
            JSONObject jObj = new JSONObject(json);
            boolean error = jObj.getBoolean("error");
            String error_msg = jObj.getString("error_msg");
            //Log.e("isi json", json);
            Log.e("isi param", params.get("serialnumber") + "," + params.get("imei"));
            // Check for error node in json
            if (!error) {
                session.setNama(jObj.getString("tag"));
                session.setStatus(1);
                session.setNilai1(jObj.getString("f"));
                session.setNilai2(jObj.getString("t"));
                session.setNilai3(params.get("serialnumber"));
                session.setNilai4(params.get("imei"));

                if (session != null) {
                    Log.e("isi ceklogin", session.getNama() + "," + session.getNilai2() + "," + session.getNilai3() + "," + session.getNilai4());
                    data.insertUpdateSessionByNama(session);
                    hasil = "";
                }

            } else {
                Log.e("isi ceklogin", String.valueOf(error) + "," + error_msg);
                //Toast.makeText(context.getApplicationContext(), error_msg + " ", Toast.LENGTH_LONG).show();
                hasil = error_msg;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            hasil = "Network";
        }
        return hasil;
    }

    public ArrayList<mSession> getAllUser() {
        ArrayList<mSession> userList = null;
        try {
            String cypertext;
            try {
                cypertext = enkrip.bytesToHex(enkrip.encrypt("sirep" + ":" + "new"));
                // Log.e("sn", cypertext);

            } catch (Exception e) {
                e.printStackTrace();
                cypertext = "";
            }
            Map<String, String> params = new HashMap<String, String>();
            String fcm_id = FirebaseInstanceId.getInstance().getToken();
            params.put("aktif", "aktif");
            String json = http.postHttp(APP_URL + "mas_user/getuser", params, cypertext);
            Type listType = new TypeToken<ArrayList<mSession>>() {
            }.getType();
            userList = new GsonBuilder().create().fromJson(json, listType);
           /* postList=new StringBuffer();
            for(mSession post: sessionList){
                postList.append("\n title: "+post.getUserName()+"\n auther: "+post.getUserAlias()+"\n date: "+post.getId()+"\n description: "+post.getUserEmail()+"\n\n");
            }*/
        } catch (JsonParseException e) {
            // Do your Error handling
        }
        return userList;
    }

    /*resubmit All*/
    public boolean reSubmitAll(String cipertex){
        //cek pesan belum terkirim
        //cekPendingMessage(cipertex);
        //cek gambar belum terkirim
        //cekPendingImageData(cipertex);
        //cek piket belum terkirim
        //cekPendingPiket(cipertex);
        //cek temuan belum terkirim
        //cekPendingTemuan(cipertex);
        return  true;
    }
    /*get pesan*/
    public boolean uploadPesan(mPesan pesan, String cypertext) {
        boolean hasil = false;
        try {

            Map<String, String> params = new HashMap<>();
            params.put("id", String.valueOf( pesan.getId()));
            params.put("fcm_id", pesan.getFcmid());
            params.put("idpengirim", String.valueOf(pesan.getIdPenerima()));
            params.put("pengirim", pesan.getPengirim());
            params.put("idpenerima",String.valueOf( pesan.getIdPenerima()));
            params.put("penerima", pesan.getPenerima());
            params.put("judul", "Replay:" + pesan.getJudul());
            params.put("isipesan", pesan.getIsiPesan());
            params.put("typepesan", pesan.getTypePesan());
            params.put("datesend", pesan.getDateSend());
            params.put("dateread", pesan.getDateRead());
            params.put("statuspesan", pesan.getStatusPesan());
            params.put("refid", String.valueOf(pesan.getRefId()));
            params.put("statussend", String.valueOf("0"));
            String json = http.postHttp(APP_URL + "mas_area/sendMessage", params, cypertext);
            JSONObject jObj = new JSONObject(json);
            boolean error = jObj.getBoolean("error");
            Log.e("isi piket", json);
            //Log.e("isi param", params.get("serialnumber") + "," + params.get("imei"));
            if (!error) {
                Log.e("isi pesan hasil", String.valueOf(error));
                pesan.setStatusSend(false);
                data.insertUpdatePesan(pesan);
                hasil = true;
            }
        } catch (JsonParseException e) {
            // Do your Error handling
            Log.e("error pesan", e.getMessage());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return hasil;
    }
    public boolean refreshAllPesan(String idUser) {
        ArrayList<mPesan> pesanList = null;
        boolean hasil = false;
        pesanList = getAllPesan(idUser);
        if (pesanList != null) {
            for (mPesan psn : pesanList) {
                // delete todo
                if (psn != null) {
                    /*Log.e("isi session",
                            msession.getId() + "," + msession.getId() + "," + msession.getNama() + "," + msession.getKodeBalai());*/
                    data.insertUpdatePesanFromServer(psn);
                }
            }
            hasil = true;
        } else {
            hasil = false;
        }
        return hasil;
    }

    public ArrayList<mPesan> getAllPesan(String idUser) {
        ArrayList<mPesan> pesanList = null;
        try {
            String cypertext;
            try {
                cypertext = enkrip.bytesToHex(enkrip.encrypt("sirep" + ":" + "new"));
                // Log.e("sn", cypertext);

            } catch (Exception e) {
                e.printStackTrace();
                cypertext = "";
            }
            Map<String, String> params = new HashMap<String, String>();
            //String fcm_id = FirebaseInstanceId.getInstance().getToken();
            params.put("idPenerima", idUser);
            //params.put("idPengirim", idUser);
            String json = http.postHttp(APP_URL + "mas_area/getMessage", params, cypertext);
            Type listType = new TypeToken<ArrayList<mPesan>>() {
            }.getType();
            pesanList = new GsonBuilder().create().fromJson(json, listType);
        } catch (JsonParseException e) {
            // Do your Error handling
            Log.e("error lokasi", e.getMessage());
        }
        return pesanList;
    }

    public void cePendingData() {
        mSession ss = data.getSessionByVal("ceklogin", 1);
        String cypertext = "";
        String iduser="";
        try {
            if (ss != null) {
                cypertext = enkrip.bytesToHex(enkrip.encrypt(ss.getNilai1() + ":" + ss.getNilai5()));//username & macadress
                iduser=String.valueOf(ss.getId());
                //Log.e("sn", cypertext);
            }

        } catch (Exception e) {
            Log.e("error cyper", e.getMessage());
            e.printStackTrace();
            cypertext = "";
        }
        reSubmitAll(cypertext);
        refreshAllPesan(userid);

    }


}
