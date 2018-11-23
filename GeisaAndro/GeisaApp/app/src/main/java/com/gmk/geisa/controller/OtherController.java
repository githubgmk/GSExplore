package com.gmk.geisa.controller;

import android.content.Context;
import android.util.Log;

import com.androidadvance.androidsurvey.Answers;
import com.androidadvance.androidsurvey.models.Survey;
import com.gmk.geisa.databases.mDB;
import com.gmk.geisa.helper.Enkrip;
import com.gmk.geisa.model.mPO;
import com.gmk.geisa.model.mPesan;
import com.gmk.geisa.service.APIService;
import com.gmk.geisa.service.RetroClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by kenjinsan on 6/21/17.
 */

public class OtherController {
    private static mDB data;
    private APIService service;
    private static OtherController instance = null;
    static Enkrip enkrip;
    static Context ctx;
    static boolean primariON = true;
    private ArrayList<mPO> list = new ArrayList<>();

    public static OtherController getInstance(Context context) {
        if (instance == null) {
            instance = new OtherController();
        }
        data = mDB.getInstance(context);
        ctx = context;
        enkrip = new Enkrip();
        return instance;
    }

    //callbak sync
    private CallBackGetDataSurvey callBackGetDataSurvey;

    public void setCallBackGetDataSurvey(CallBackGetDataSurvey callBackGetDataSurvey) {
        this.callBackGetDataSurvey = callBackGetDataSurvey;
    }

    public int getPesanNew(int id) {
        return data.getPesanByUnread("new", String.valueOf(id));
    }

    public interface CallBackGetDataSurvey {
        public void resultReady(ArrayList<Survey> customers, boolean hasil);
    }

    private CallBackSendDataSurvey callBackSendDataSurvey;

    public void setCallBackSendDataSurvey(CallBackSendDataSurvey callBackSendDataSurvey) {
        this.callBackSendDataSurvey = callBackSendDataSurvey;
    }


    public interface CallBackSendDataSurvey {
        public void resultReady(Answers.AllValue answer, boolean hasil);
    }

    //pesan
    private CallBackGetDataPesan callBackGetDataPesan;

    public void setCallBackGetDataPesan(CallBackGetDataPesan callBackGetDataPesan) {
        this.callBackGetDataPesan = callBackGetDataPesan;
    }

    public interface CallBackGetDataPesan {
        public void resultReady(ArrayList<mPesan> pesan, boolean hasil);
    }

    private CallBackSendDataPesan callBackSendDataPesan;

    public void setCallBackSendDataPesan(CallBackSendDataPesan callBackSendDataPesan) {
        this.callBackSendDataPesan = callBackSendDataPesan;
    }


    public interface CallBackSendDataPesan {
        public void resultReady(mPesan pesan, boolean hasil);
    }
    /*end callback*/


    public void getSurvey(int salesId, int custId) {
        String cypertext;
        try {
            cypertext = Enkrip.bytesToHex(enkrip.encrypt("has password" + ":" + "has password"));
            //  Log.e("sn", cypertext);

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
        params.put("salesId", String.valueOf(salesId));
        params.put("custId", String.valueOf(custId));

        Call<ArrayList<Survey>> userlist = service.GetSurvey(params);
        userlist.enqueue(new Callback<ArrayList<Survey>>() {
            @Override
            public void onResponse(Call<ArrayList<Survey>> call, Response<ArrayList<Survey>> response) {
                //Log.e("isi respion cek update", response.code()+" isi code");
                if (response.isSuccessful()) {
                    ArrayList<Survey> pos = response.body();
                    if (pos != null) {
                        /*for (mPO po : pos) {
                            data.InsertUpateAllPO(po);
                        }*/
                        callBackGetDataSurvey.resultReady(pos, true);
                    } else {
                        callBackGetDataSurvey.resultReady(pos, false);
                    }

                } else {
                    callBackGetDataSurvey.resultReady(null, false);
                    Log.e("error", response.message() + "," + response.code());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Survey>> call, Throwable t) {
                Log.e("REST error ", t.getMessage() + "xx" + t.toString());
                callBackGetDataSurvey.resultReady(null, false);
            }


        });
    }


    public void sendSurveyToServer(Answers.AllValue answer) {
        String cypertext;
        try {
            cypertext = enkrip.bytesToHex(enkrip.encrypt("has password" + ":" + "has password"));
            //  Log.e("sn", cypertext);

        } catch (Exception e) {
            e.printStackTrace();
            cypertext = "";
        }
        if (primariON) {
            service = RetroClient.getClient(cypertext).create(APIService.class);
        } else {
            service = RetroClient.getClientBackup(cypertext).create(APIService.class);
        }


        Call<Answers.AllValue> userlist = service.sendSurvey(answer);
        userlist.enqueue(new Callback<Answers.AllValue>() {
            @Override
            public void onResponse(Call<Answers.AllValue> call, Response<Answers.AllValue> response) {
                // Log.e("isi respion cek update", response.code()+" isi code");
                if (response.isSuccessful()) {
                    //Log.e("isi respion", response.body().toString());
                    Answers.AllValue result = response.body();

                    if (result != null) {
                        callBackSendDataSurvey.resultReady(result, result.isStatusSend());
                    } else {
                        callBackSendDataSurvey.resultReady(null, false);
                    }
                } else {
                    //simpan ke database untuk diupdate
                    callBackSendDataSurvey.resultReady(null, false);
                }
            }

            @Override
            public void onFailure(Call<Answers.AllValue> call, Throwable t) {
                Log.e("REST", "err " + t.toString());
                callBackSendDataSurvey.resultReady(null, false);
            }


        });
    }

    //get pesan
    public boolean getPesan(int salesId, final boolean needCallBack) {
        String cypertext;
        final boolean[] hsl = {false};
        try {
            cypertext = enkrip.bytesToHex(enkrip.encrypt("has password" + ":" + "has password"));
            //  Log.e("sn", cypertext);

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
        params.put("salesId", String.valueOf(salesId));

        Call<ArrayList<mPesan>> userlist = service.GetPesan(params);
        userlist.enqueue(new Callback<ArrayList<mPesan>>() {
            @Override
            public void onResponse(Call<ArrayList<mPesan>> call, Response<ArrayList<mPesan>> response) {
                //Log.e("isi respion cek update", response.code()+" isi code");
                if (response.isSuccessful()) {
                    ArrayList<mPesan> pos = response.body();
                    if (pos != null) {
                        for (mPesan psn : pos) {
                            if (psn != null) {
                                /*Log.e("isi session",
                                        msession.getId() + "," + msession.getId() + "," + msession.getNama() + "," + msession.getKodeBalai());*/
                                data.insertUpdatePesanFromServer(psn);
                            }
                        }
                        if (needCallBack)
                            callBackGetDataPesan.resultReady(pos, true);
                        hsl[0] = true;
                    } else {
                        if (needCallBack)
                            callBackGetDataPesan.resultReady(pos, false);
                        hsl[0] = false;
                    }

                } else {
                    if (needCallBack)
                        callBackGetDataPesan.resultReady(null, false);
                    Log.e("error", response.message() + "," + response.code());
                    hsl[0] = false;
                }
            }

            @Override
            public void onFailure(Call<ArrayList<mPesan>> call, Throwable t) {
                Log.e("REST error ", t.getMessage() + "xx" + t.toString());
                if (needCallBack)
                    callBackGetDataPesan.resultReady(null, false);
                hsl[0] = false;
            }


        });

        return hsl[0];
    }

    public void sendPesanToServer(final mPesan pesan, final boolean needCallBack) {
        String cypertext;
        try {
            cypertext = enkrip.bytesToHex(enkrip.encrypt("has password" + ":" + "has password"));
            //  Log.e("sn", cypertext);

        } catch (Exception e) {
            e.printStackTrace();
            cypertext = "";
        }
        if (primariON) {
            service = RetroClient.getClient(cypertext).create(APIService.class);
        } else {
            service = RetroClient.getClientBackup(cypertext).create(APIService.class);
        }


        Call<mPesan> userlist = service.sendPesan(pesan);
        userlist.enqueue(new Callback<mPesan>() {
            @Override
            public void onResponse(Call<mPesan> call, Response<mPesan> response) {
                // Log.e("isi respion cek update", response.code()+" isi code");
                if (response.isSuccessful()) {
                    //Log.e("isi respion", response.body().toString());
                    mPesan result = response.body();

                    if (result != null) {
                        if (needCallBack)
                            callBackSendDataPesan.resultReady(result, result.getStatusSend());
                    } else {
                        if (needCallBack)
                            callBackSendDataPesan.resultReady(pesan, false);
                    }
                } else {
                    //simpan ke database untuk diupdate
                    if (needCallBack)
                        callBackSendDataPesan.resultReady(pesan, false);
                }
            }

            @Override
            public void onFailure(Call<mPesan> call, Throwable t) {
                Log.e("REST", "err " + t.toString());
                if (needCallBack)
                    callBackSendDataPesan.resultReady(pesan, false);
            }


        });
    }

}
