package com.gmk.geisa.controller;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.gmk.geisa.databases.mDB;
import com.gmk.geisa.helper.Constants;
import com.gmk.geisa.helper.Enkrip;
import com.gmk.geisa.model.PostResult;
import com.gmk.geisa.model.mTracking;
import com.gmk.geisa.model.mTrackingPicture;
import com.gmk.geisa.service.APIService;
import com.gmk.geisa.service.RetroClient;

import java.io.File;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by kenjin on 1/25/2017.
 */

public class TrackingController {
    //awal harus ada
    private static mDB data;
    private APIService service;
    private static TrackingController instance = null;
    static Enkrip enkrip;
    static Context ctx;
    static boolean primariON = true;

    public static TrackingController getInstance(Context context) {
        if (instance == null) {
            instance = new TrackingController();
        }
        // Log.e("primary on before",primariON +"xx");
        data = mDB.getInstance(context);
        ctx = context;
        enkrip = new Enkrip();
        MyTask task = new MyTask();
        task.execute();
        return instance;
    }

    private CallBackGetDataTrackingPicture callBackGetDataTrackingPicture;

    public void setCallBackGetDataTrackingPicture(CallBackGetDataTrackingPicture callBackGetDataTrackingPicture) {
        this.callBackGetDataTrackingPicture = callBackGetDataTrackingPicture;
    }

    public interface CallBackGetDataTrackingPicture {
        public void resultReady(boolean hasil);
    }

    private CallBackGetDataTracking callBackGetDataTracking;

    public void setCallBackGetDataTracking(CallBackGetDataTracking callBackGetDataTracking) {
        this.callBackGetDataTracking = callBackGetDataTracking;
    }

    public interface CallBackGetDataTracking {
        public void resultReady(boolean hasil);
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
    public void cekinternet() {
        MyTask task = new MyTask();
        task.execute();
    }

    public boolean insertTracking(mTracking tracking) {
        boolean hasil = false;
        if (data.addTracking(tracking)) {
            hasil = true;
            ArrayList<mTracking> tracking1 = data.getTrackingByStatus(1);
            Log.e("cek isi tracking","total"+tracking1.size());
            if (tracking1 != null) {
                insertTrackingServer(tracking1);
            }
        } else {
            hasil = false;
        }
        return hasil;
    }

    public void insertTrackingServer(final mTracking tracking) {
        String cypertext;
        try {
            cypertext = enkrip.bytesToHex(enkrip.encrypt(tracking.getSalesmanId() + ":" + tracking.getSalesmanId()));
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

        /*Map<String, String> params = new HashMap<String, String>();
        params.put("trackingId", tracking.getTrackingId());
        params.put("SalesmanId", tracking.getSalesmanId());
        params.put("trackingType", tracking.getTrackingType());
        params.put("trackingDate", tracking.getTrackingDate());
        params.put("trackingTime", tracking.getTrackingTime());
        params.put("trackingLat", tracking.getTrackingLat());
        params.put("trackingLot", tracking.getTrackingLot());
        params.put("trackingRef", tracking.getTrackingRef());
        params.put("trackingStatus", tracking.getTrackingStatus());
        params.put("createDate", tracking.getCreateDate());*/
        Call<PostResult> result = service.insertTrackingServer(tracking);
        result.enqueue(new Callback<PostResult>() {
            @Override
            public void onResponse(Call<PostResult> call, Response<PostResult> response) {
                if (response.isSuccessful()) {
                    // Log.e("isi respon tracking", response.body().toString());
                    PostResult result = response.body();
                    if (result != null) {
                        if (result.isErrNot()) {
                            data.updateTrackingStatus(result.getMsgA(),result.getMsgB(),0);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<PostResult> call, Throwable t) {
                // Log.e("REST", t.getMessage());

            }


        });
    }


    public void insertTrackingServer(final ArrayList<mTracking> trackingArrayList) {
        if (trackingArrayList != null) {

            for (final mTracking tracking : trackingArrayList) {
                String cypertext;
                try {
                    cypertext = enkrip.bytesToHex(enkrip.encrypt(tracking.getSalesmanId() + ":" + tracking.getSalesmanId()));
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
                /*Map<String, String> params = new HashMap<>();
                params.put("TrackingId", tracking.getTrackingId());
                params.put("SalesmanId", tracking.getSalesmanId());
                params.put("TrackingType", tracking.getTrackingType());
                params.put("TrackingDate", tracking.getTrackingDate());
                params.put("TrackingTime", tracking.getTrackingTime());
                params.put("TrackingLat", tracking.getTrackingLat());
                params.put("TrackingLot", tracking.getTrackingLot());
                params.put("TrackingRef", tracking.getTrackingRef());
                params.put("TrackingStatus", tracking.getTrackingStatus());
                params.put("CreateDate", tracking.getCreateDate());
                params.put("DeviceInfo", tracking.getInfoDevice());*/
                Call<PostResult> result = service.insertTrackingServer(tracking);
                result.enqueue(new Callback<PostResult>() {
                    @Override
                    public void onResponse(Call<PostResult> call, Response<PostResult> response) {
                        if (response.isSuccessful()) {
                            PostResult result = response.body();
                            if (result != null) {
                               // Log.e("isi respon tracking ke ", response.body().toString());
                                if (result.isErrNot()) {
                                  //  Log.e("masuk untuk delete","masuk update"+result.getMsgA());
                                    data.updateTrackingStatus(result.getMsgA(),result.getMsgB(),0);
                                }
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<PostResult> call, Throwable t) {
                         Log.e("err tracking","x"+ t.toString());

                    }


                });
            }
        }

    }

    //resend tracking

    public void reSendTrackingServer(final ArrayList<mTracking> trackingArrayList) {
        if (trackingArrayList != null && trackingArrayList.size()>0) {

            for (final mTracking tracking : trackingArrayList) {
                String cypertext;
                try {
                    cypertext = enkrip.bytesToHex(enkrip.encrypt(tracking.getSalesmanId() + ":" + tracking.getSalesmanId()));
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
                /*Map<String, String> params = new HashMap<>();
                params.put("TrackingId", tracking.getTrackingId());
                params.put("SalesmanId", tracking.getSalesmanId());
                params.put("TrackingType", tracking.getTrackingType());
                params.put("TrackingDate", tracking.getTrackingDate());
                params.put("TrackingTime", tracking.getTrackingTime());
                params.put("TrackingLat", tracking.getTrackingLat());
                params.put("TrackingLot", tracking.getTrackingLot());
                params.put("TrackingRef", tracking.getTrackingRef());
                params.put("TrackingStatus", tracking.getTrackingStatus());
                params.put("CreateDate", tracking.getCreateDate());
                params.put("DeviceInfo", tracking.getInfoDevice());*/
                Call<PostResult> result = service.insertTrackingServer(tracking);
                result.enqueue(new Callback<PostResult>() {
                    @Override
                    public void onResponse(Call<PostResult> call, Response<PostResult> response) {
                        if (response.isSuccessful()) {
                            PostResult result = response.body();
                            if (result != null) {
                                if (result.isErrNot()) {
                                    //  Log.e("masuk untuk delete","masuk update"+result.getMsgA());
                                    if(data.updateTrackingStatus(result.getMsgA(),result.getMsgB(),0)){
                                        callBackGetDataTracking.resultReady(true);
                                    }else{
                                        callBackGetDataTracking.resultReady(false);
                                    }
                                }else {
                                    callBackGetDataTracking.resultReady(false);
                                }
                            }else {
                                callBackGetDataTracking.resultReady(false);
                            }
                        }else{
                            callBackGetDataTracking.resultReady(false);
                        }
                    }

                    @Override
                    public void onFailure(Call<PostResult> call, Throwable t) {
                        Log.e("err tracking","x"+ t.toString());
                        callBackGetDataTracking.resultReady(false);

                    }


                });
            }
        }else {
            callBackGetDataTracking.resultReady(true);
        }

    }

    //tracking picture

    public void insertTrackingPictureServer(mTrackingPicture trackingPicture) {
        //simpan to local
        if (InsertUpateAllTrackingPicture(trackingPicture)) {
            //send file to server for picture & send  database
            insertTrackingPictureToServer(trackingPicture);

        }
    }
    public  ArrayList<mTracking> getAllTrackingPending(){
        return data.getTrackingByStatus(1);
    }

    public  ArrayList<mTrackingPicture> getAllTrackingPicturePending(){
        return data.getTrackingPictureAll(1);
    }

    private boolean InsertUpateAllTrackingPicture(mTrackingPicture trackingPicture) {
        return data.InsertUpateAllTrackingPicture(trackingPicture);
    }

    private boolean updateTrackingPictureStatus(String trackingId, int status) {
        return data.updateTrackingPictureStatus(trackingId, status);
    }

    public void insertTrackingPictureToServer(final mTrackingPicture trackingPicture) {
        if (trackingPicture != null) {


            String cypertext;
            try {
                cypertext = enkrip.bytesToHex(enkrip.encrypt(trackingPicture.getCreatedBy() + ":" + trackingPicture.getPictureRef()));
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
            File file = new File(trackingPicture.getPicture());
            RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), file);
            MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", file.getName(), requestFile);
            RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), file.getName());
            RequestBody TrackingPictureId = RequestBody.create(MediaType.parse("text/plain"), trackingPicture.getTrackingPictureId());
            RequestBody Picture = RequestBody.create(MediaType.parse("text/plain"), trackingPicture.getPicture());
            RequestBody PictureRef = RequestBody.create(MediaType.parse("text/plain"), trackingPicture.getPictureRef());
            RequestBody StatusBattery = RequestBody.create(MediaType.parse("text/plain"), trackingPicture.getStatusBattery());
            RequestBody Note = RequestBody.create(MediaType.parse("text/plain"), trackingPicture.getNote());
            RequestBody CreatedDate = RequestBody.create(MediaType.parse("text/plain"), trackingPicture.getCreatedDate());
            RequestBody CreatedBy = RequestBody.create(MediaType.parse("text/plain"), trackingPicture.getCreatedBy());

            Call<PostResult> result = service.uploadFiles(fileToUpload, filename, TrackingPictureId, Picture, PictureRef, StatusBattery, Note, CreatedDate, CreatedBy);
            //Call<PostResult> result = service.uploadFiles(fileToUpload, trackingPicture);
            result.enqueue(new Callback<PostResult>() {
                @Override
                public void onResponse(Call<PostResult> call, Response<PostResult> response) {
                    if (response.isSuccessful()) {
                        //update trackingpicture status send
                        PostResult hsl = response.body();
                        if (hsl != null) {
                            if (hsl.isErrNot()) {
                                updateTrackingPictureStatus(hsl.getMsgA(), 0);
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<PostResult> call, Throwable t) {
                    Log.e("REST track pic", "err" + t.getMessage() + "," + t.toString());

                }


            });

        }

    }

    //resend traking picture
    public void reSendTrackingPictureToServer(final ArrayList<mTrackingPicture> tp) {
        final boolean[] hasil = {false};
        if(tp!=null && tp.size()>0){
            for (mTrackingPicture trackingPicture:tp) {
                if (trackingPicture != null) {

                    String cypertext;
                    try {
                        cypertext = enkrip.bytesToHex(enkrip.encrypt(trackingPicture.getCreatedBy() + ":" + trackingPicture.getPictureRef()));
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
                    File file = new File(trackingPicture.getPicture());
                    RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), file);
                    MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", file.getName(), requestFile);
                    RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), file.getName());
                    RequestBody TrackingPictureId = RequestBody.create(MediaType.parse("text/plain"), trackingPicture.getTrackingPictureId());
                    RequestBody Picture = RequestBody.create(MediaType.parse("text/plain"), trackingPicture.getPicture());
                    RequestBody PictureRef = RequestBody.create(MediaType.parse("text/plain"), trackingPicture.getPictureRef());
                    RequestBody StatusBattery = RequestBody.create(MediaType.parse("text/plain"), trackingPicture.getStatusBattery());
                    RequestBody Note = RequestBody.create(MediaType.parse("text/plain"), trackingPicture.getNote());
                    RequestBody CreatedDate = RequestBody.create(MediaType.parse("text/plain"), trackingPicture.getCreatedDate());
                    RequestBody CreatedBy = RequestBody.create(MediaType.parse("text/plain"), trackingPicture.getCreatedBy());

                   Call<PostResult> result = service.uploadFiles(fileToUpload, filename, TrackingPictureId, Picture, PictureRef, StatusBattery, Note, CreatedDate, CreatedBy);
                   // Call<PostResult> result=service.uploadFiles(fileToUpload,trackingPicture);
                    result.enqueue(new Callback<PostResult>() {
                        @Override
                        public void onResponse(Call<PostResult> call, Response<PostResult> response) {
                            if (response.isSuccessful()) {
                                //update trackingpicture status send
                                PostResult hsl = response.body();
                                if (hsl != null) {
                                    if (hsl.isErrNot()) {
                                       if(updateTrackingPictureStatus(hsl.getMsgA(), 0)){
                                           callBackGetDataTrackingPicture.resultReady(true);
                                       }else {
                                           callBackGetDataTrackingPicture.resultReady(false);
                                       }
                                    }else {
                                        callBackGetDataTrackingPicture.resultReady(false);
                                    }
                                }else {
                                    callBackGetDataTrackingPicture.resultReady(false);
                                }
                            }else {
                                callBackGetDataTrackingPicture.resultReady(false);
                            }
                        }

                        @Override
                        public void onFailure(Call<PostResult> call, Throwable t) {
                            Log.e("REST track pic", "err" + t.getMessage() + "," + t.toString());
                            callBackGetDataTrackingPicture.resultReady(false);

                        }


                    });

                }else{
                    callBackGetDataTrackingPicture.resultReady(true);
                }
            }

        }else {
            callBackGetDataTrackingPicture.resultReady(true);
        }


    }

}
