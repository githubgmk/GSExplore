package com.gmk.geisa.controller;

import android.content.Context;
import android.util.Log;

import com.gmk.geisa.databases.mDB;
import com.gmk.geisa.helper.Enkrip;
import com.gmk.geisa.model.Post.rCallPlan;
import com.gmk.geisa.model.Post.rCallPlanComplain;
import com.gmk.geisa.model.Post.rCallPlanDemo;
import com.gmk.geisa.model.Post.rCallPlanNote;
import com.gmk.geisa.model.Post.rCallPlanPost;
import com.gmk.geisa.model.Post.rCallPlanSample;
import com.gmk.geisa.model.Post.rStringList;
import com.gmk.geisa.model.PostResult;
import com.gmk.geisa.model.mCallPlan;
import com.gmk.geisa.model.mCallPlanNote;
import com.gmk.geisa.model.mComplain;
import com.gmk.geisa.model.mDemo;
import com.gmk.geisa.model.mSample;
import com.gmk.geisa.model.mTodoList;
import com.gmk.geisa.model.mTrackingPicture;
import com.gmk.geisa.service.APIService;
import com.gmk.geisa.service.RetroClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by kenjinsan on 5/10/17.
 */

public class CallPlanController {

    private static mDB data;
    private APIService service;
    private static CallPlanController instance = null;
    static Enkrip enkrip;
    static Context ctx;
    static boolean primariON = true;
    private ArrayList<mCallPlan> list = new ArrayList<>();
    private ArrayList<mTodoList> listToDo = new ArrayList<>();
    private static TrackingController trackingController;

    public static CallPlanController getInstance(Context context) {
        if (instance == null) {
            instance = new CallPlanController();
        }
        data = mDB.getInstance(context);
        ctx = context;
        trackingController = TrackingController.getInstance(context);
        enkrip = new Enkrip();
        return instance;
    }

    private CallBackGetDataBIN callBackGetDataBIN;

    public void setCallBackGetDataBIN(CallBackGetDataBIN callBackGetDataBIN) {
        this.callBackGetDataBIN = callBackGetDataBIN;
    }

    public interface CallBackGetDataBIN {
        public void resultReady(boolean hasil);
    }

    private CallBackGetDataSampleRefresh callBackGetDataSampleRefresh;

    public void setCallBackGetDataSampleRefresh(CallBackGetDataSampleRefresh callBackGetDataSampleRefresh) {
        this.callBackGetDataSampleRefresh = callBackGetDataSampleRefresh;
    }

    public interface CallBackGetDataSampleRefresh {
        public void resultReady(boolean hasil);
    }

    private CallBackGetDataSample callBackGetDataSample;

    public void setCallBackGetDataSample(CallBackGetDataSample callBackGetDataSample) {
        this.callBackGetDataSample = callBackGetDataSample;
    }

    public interface CallBackGetDataSample {
        public void resultReady(boolean hasil);
    }

    private CallBackGetDataDemo callBackGetDataDemo;

    public void setCallBackGetDataDemo(CallBackGetDataDemo callBackGetDataDemo) {
        this.callBackGetDataDemo = callBackGetDataDemo;
    }

    public interface CallBackGetDataDemo {
        public void resultReady(boolean hasil);
    }

    private CallBackDemo callBackDemo;

    public void setCallBackDemo(CallBackDemo callBackDemo) {
        this.callBackDemo = callBackDemo;
    }

    public interface CallBackDemo {
        public void resultReady(ArrayList<mDemo> demo, boolean hasil);
    }

    private CallBackGetDataComplain callBackGetDataComplain;

    public void setCallBackGetDataComplain(CallBackGetDataComplain callBackGetDataComplain) {
        this.callBackGetDataComplain = callBackGetDataComplain;
    }

    public interface CallBackGetDataComplain {
        public void resultReady(boolean hasil);
    }


    private CallBackGetDataAllCallNoteUpdate callBackGetDataAllCallNoteUpdate;

    public void setCallBackGetDataAllCallNoteUpdate(CallBackGetDataAllCallNoteUpdate callBackGetDataAllCallNoteUpdate) {
        this.callBackGetDataAllCallNoteUpdate = callBackGetDataAllCallNoteUpdate;
    }

    public interface CallBackGetDataAllCallNoteUpdate {
        public void resultReady(ArrayList<mCallPlanNote> callPlan, boolean hasil);
    }

    private CallBackComplain callBackComplain;

    public void setCallBackComplain(CallBackComplain callBackComplain) {
        this.callBackComplain = callBackComplain;
    }

    public interface CallBackComplain {
        public void resultReady(ArrayList<mComplain> complain, boolean hasil);
    }

    private CallBackSample callBackSample;

    public void setCallBackSample(CallBackSample callBackSample) {
        this.callBackSample = callBackSample;
    }

    public interface CallBackSample {
        public void resultReady(ArrayList<mSample> complain, boolean hasil);
    }

    private CallBackGetDataAllCallUpdate callBackGetDataAllCallUpdate;

    public void setCallBackGetDataAllCallUpdate(CallBackGetDataAllCallUpdate callBackGetDataAllCallUpdate) {
        this.callBackGetDataAllCallUpdate = callBackGetDataAllCallUpdate;
    }

    public interface CallBackGetDataAllCallUpdate {
        public void resultReady(ArrayList<mCallPlan> callPlan, boolean hasil);
    }

    //calback get update customer
    private CallBackGetData callBackGetData;

    public void setCallBackGetData(CallBackGetData callBackGetData) {
        this.callBackGetData = callBackGetData;
    }

    public interface CallBackGetData {
        public void resultReady(ArrayList<mCallPlan> callPlan, boolean hasil);
    }

    private CallBackUpdateCallPlan callBackGetUpdateCallPlan;

    public void setCallBackUpdateCallPlan(CallBackUpdateCallPlan callBackUpdateCallPlan) {
        this.callBackGetUpdateCallPlan = callBackUpdateCallPlan;
    }

    public interface CallBackUpdateCallPlan {
        public void resultReady(ArrayList<mCallPlan> callPlan, boolean hasil);
    }

    private CallBackGetDataDraft callBackGetDataDraft;

    public void setCallBackGetDataDraft(CallBackGetDataDraft callBackGetDataDraft) {
        this.callBackGetDataDraft = callBackGetDataDraft;
    }

    public interface CallBackGetDataDraft {
        public void resultReady(ArrayList<mCallPlan> callPlan, boolean hasil);
    }

    private CallBackGetDataToDo callBackGetDataToDo;

    public void setCallBackGetDataToDo(CallBackGetDataToDo callBackGetDataToDo) {
        this.callBackGetDataToDo = callBackGetDataToDo;
    }

    public interface CallBackGetDataToDo {
        public void resultReady(ArrayList<mTodoList> todo, boolean hasil);
    }

    /*end callback*/
    //download all callplan from server >= now
    public void checkUpdateAllCallPlanFromServer(String salesId) {
        String cypertext;
        try {
            cypertext = enkrip.bytesToHex(enkrip.encrypt("has password" + ":" + "has password"));

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
        params.put("salesId", salesId);
        Call<ArrayList<mCallPlan>> userlist = service.getCallPlan(params);
        userlist.enqueue(new Callback<ArrayList<mCallPlan>>() {
            @Override
            public void onResponse(Call<ArrayList<mCallPlan>> call, Response<ArrayList<mCallPlan>> response) {
                //Log.e("isi respion cek update", response.code()+" isi code");
                if (response.isSuccessful()) {
                    //Log.e("isi respion", response.body().toString());
                    ArrayList<mCallPlan> result = response.body();
                    if (result != null) {
                        // Log.e("ada data", result.size()+" ada");
                        list = result;
                        data.InsertUpateAllCallPlanWhere(result);
                    }

                    callBackGetData.resultReady(list, true);
                } else {
                    Log.e("error", response.message() + "," + response.code());
                    callBackGetData.resultReady(list, false);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<mCallPlan>> call, Throwable t) {
                Log.e("REST error ", t.getMessage() + "xx" + t.toString());
                callBackGetData.resultReady(list, false);
            }


        });
    }

    // create call plan
    public void updateCallPlanToServerWithDeleteDraftNoCallBack(String salesId, final ArrayList<mCallPlan> callPlans, final ArrayList<mCallPlan> callPlanDel) {

        if (null != callPlanDel)
            deleteCallPlanDraft(callPlanDel);
        if (callPlans != null && callPlans.size() > 0) {
            // list=callPlan; nanti dulu
            //untuk yang del didelete dari draftcallplan;
            //Log.e("ttl callplan del", "ttl"+callPlanDel.size());
            String cypertext;
            try {
                cypertext = enkrip.bytesToHex(enkrip.encrypt("has password" + ":" + "has password"));

            } catch (Exception e) {
                e.printStackTrace();
                cypertext = "";
            }
            if (primariON) {
                service = RetroClient.getClient(cypertext).create(APIService.class);
            } else {
                service = RetroClient.getClientBackup(cypertext).create(APIService.class);
            }

            rCallPlan callplan = new rCallPlan(salesId, callPlans);
            Call<ArrayList<mCallPlan>> userlist = service.updateCallPlan(callplan);
            userlist.enqueue(new Callback<ArrayList<mCallPlan>>() {
                @Override
                public void onResponse(Call<ArrayList<mCallPlan>> call, Response<ArrayList<mCallPlan>> response) {
                    //Log.e("isi respion cek update", response.code()+" isi code");
                    if (response.isSuccessful()) {
                        //Log.e("isi respion", response.body().toString());
                        ArrayList<mCallPlan> result = response.body();
                        if (result != null) {
                            //Log.e("ada data callplan", result.size()+" ada");
                            list = result;
                            if(data.InsertUpateAllCallPlanWhere(result)){
                                //dafd
                                if(deleteCallPlanDraft(result)){
                                    //tambahkan untuk delete callplans
                                    //callBackGetDataDraft.resultReady(list, true);
                                }else{
                                   // callBackGetDataDraft.resultReady(list, false);
                                }
                            }else{
                               // callBackGetDataDraft.resultReady(list, false);
                            }
                        }else{
                           // callBackGetDataDraft.resultReady(list, false);
                        }

                    } else {
                        //Log.e("error", response.message() + "," + response.code());
                       // callBackGetDataDraft.resultReady(list, false);
                    }
                }

                @Override
                public void onFailure(Call<ArrayList<mCallPlan>> call, Throwable t) {
                    Log.e("REST error ", t.getMessage() + "xx" + t.toString());
                    //callBackGetDataDraft.resultReady(list, false);
                }
            });
        } else {
           // callBackGetDataDraft.resultReady(list, true);
        }
    }

    public void updateCallPlanToServerWithDeleteDraft(String salesId, final ArrayList<mCallPlan> callPlans, final ArrayList<mCallPlan> callPlanDel) {

        if (null != callPlanDel)
            deleteCallPlanDraft(callPlanDel);
        if (callPlans != null && callPlans.size() > 0) {
           // list=callPlan; nanti dulu
            //untuk yang del didelete dari draftcallplan;
           //Log.e("ttl callplan del", "ttl"+callPlans.size());
            String cypertext;
            try {
                cypertext = enkrip.bytesToHex(enkrip.encrypt("has password" + ":" + "has password"));

            } catch (Exception e) {
                e.printStackTrace();
                cypertext = "";
            }
            if (primariON) {
                service = RetroClient.getClient(cypertext).create(APIService.class);
            } else {
                service = RetroClient.getClientBackup(cypertext).create(APIService.class);
            }

            rCallPlan callplan = new rCallPlan(salesId, callPlans);
            Call<ArrayList<mCallPlan>> userlist = service.updateCallPlan(callplan);
            userlist.enqueue(new Callback<ArrayList<mCallPlan>>() {
                @Override
                public void onResponse(Call<ArrayList<mCallPlan>> call, Response<ArrayList<mCallPlan>> response) {
                    //Log.e("isi respion cek update", response.code()+" isi code "+callPlans.size());
                    if (response.isSuccessful()) {
                        //Log.e("isi respion", response.body().toString());

                        ArrayList<mCallPlan> result = response.body();
                        if (result != null && result.size()>0) {
                            //Log.e("ada data callplan", result.size()+" ada");
                            list = result;
                            if(data.InsertUpateAllCallPlanWhere(result)){
                                //dafd
                                  if(deleteCallPlanDraft(result)){
                                      //Log.e("delete sukes","delete sukses");
                                        callBackGetDataDraft.resultReady(list,true);
                                  }else{
                                     // Log.e("delete sukes","delete gagal");
                                      callBackGetDataDraft.resultReady(list, false);
                                  }
                            }else{
                               // Log.e("delete sukes","gagal update call plan");
                                callBackGetDataDraft.resultReady(list, false);
                            }
                        }else{
                            callBackGetDataDraft.resultReady(list, true);
                        }

                    } else {
                        //Log.e("error", response.message() + "," + response.code());
                        callBackGetDataDraft.resultReady(list, false);
                    }
                }

                @Override
                public void onFailure(Call<ArrayList<mCallPlan>> call, Throwable t) {
                    //Log.e("REST error ", t.getMessage() + "xx" + t.toString());
                    callBackGetDataDraft.resultReady(list, false);
                }
            });
        } else {
            callBackGetDataDraft.resultReady(list, true);
        }
    }

    public void updateCallPlanToServerStatusNonCallBack(String salesId, ArrayList<mCallPlan> callPlan) {

        String cypertext;
        try {
            cypertext = enkrip.bytesToHex(enkrip.encrypt("has password" + ":" + "has password"));

        } catch (Exception e) {
            e.printStackTrace();
            cypertext = "";
        }
        if (primariON) {
            service = RetroClient.getClient(cypertext).create(APIService.class);
        } else {
            service = RetroClient.getClientBackup(cypertext).create(APIService.class);
        }

        rCallPlan callplan = new rCallPlan(salesId, callPlan);
        Call<ArrayList<mCallPlan>> userlist = service.updateCallPlanRealisasi(callplan);
        userlist.enqueue(new Callback<ArrayList<mCallPlan>>() {
            @Override
            public void onResponse(Call<ArrayList<mCallPlan>> call, Response<ArrayList<mCallPlan>> response) {
                //Log.e("isi respion cek update", response.code()+" isi code");
                if (response.isSuccessful()) {
                    //Log.e("isi respion", response.body().toString());
                    ArrayList<mCallPlan> result = response.body();
                    if (result != null) {
                        Log.e("ada data callplan", result.size() + " ada");
                        data.InsertUpateAllCallPlan(result);

                    }
                } else {
                    Log.e("error", response.message() + "," + response.code());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<mCallPlan>> call, Throwable t) {
                Log.e("REST error ", t.getMessage() + "xx" + t.toString());
            }


        });
    }

    ArrayList<mCallPlanNote> caalnewnote=new ArrayList<>();
    public void checkNewUpdateBetweenAllCallPlanNoteFromServer(final String salesId,String dateFrom,String dateTo,int custId) {
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
        Map<String, String> params = new HashMap<String, String>();
        params.put("salesId", salesId);
        params.put("dateFrom", dateFrom);
        params.put("dateTo", dateTo);
        params.put("custId", String.valueOf(custId));

        Call<ArrayList<mCallPlanNote>> userlist = service.getUpdateAllCallPlanNoteBetween(params);
        userlist.enqueue(new Callback<ArrayList<mCallPlanNote>>() {
            @Override
            public void onResponse(Call<ArrayList<mCallPlanNote>> call, Response<ArrayList<mCallPlanNote>> response) {
                // Log.e("isi respion cek update", response.code() + " isi code");

                if (response.isSuccessful()) {
                    //Log.e("isi respion", response.body().toString());
                    ArrayList<mCallPlanNote> result = response.body();

                    if (result != null && result.size()>0) {
                        // Log.e("ada data", result.size()+" ada");
                        caalnewnote.addAll(result);
                        if (data.InsertUpateAllTransCallPlanNoteWhere(result)) {
                            callBackGetDataAllCallNoteUpdate.resultReady(caalnewnote, true);
                        }else{
                            callBackGetDataAllCallNoteUpdate.resultReady(caalnewnote, false);
                        }
                    }else{
                        callBackGetDataAllCallNoteUpdate.resultReady(caalnewnote, false);
                    }

                } else {
                    Log.e("error", response.message() + "," + response.code());
                    callBackGetDataAllCallNoteUpdate.resultReady(caalnewnote, false);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<mCallPlanNote>> call, Throwable t) {
                Log.e("REST error ", t.getMessage() + "xx" + t.toString());
                callBackGetDataAllCallNoteUpdate.resultReady(caalnewnote, false);
            }


        });
    }

    ArrayList<mCallPlan> caalnew=new ArrayList<>();
    public void checkNewUpdateBetweenAllCallPlanFromServer(final String salesId,String dateFrom,String dateTo,int custId) {
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
        Map<String, String> params = new HashMap<String, String>();
        params.put("salesId", salesId);
        params.put("dateFrom", dateFrom);
        params.put("dateTo", dateTo);
        params.put("custId", String.valueOf(custId));

        Call<ArrayList<mCallPlan>> userlist = service.getUpdateAllCallPlanBetween(params);
        userlist.enqueue(new Callback<ArrayList<mCallPlan>>() {
            @Override
            public void onResponse(Call<ArrayList<mCallPlan>> call, Response<ArrayList<mCallPlan>> response) {
                // Log.e("isi respion cek update", response.code() + " isi code");

                if (response.isSuccessful()) {
                    //Log.e("isi respion", response.body().toString());
                    ArrayList<mCallPlan> result = response.body();

                    if (result != null) {
                        // Log.e("ada data", result.size()+" ada");
                        caalnew.addAll(result);
                        if (data.InsertUpateAllCallPlanWhere(result)) {
                            callBackGetDataAllCallUpdate.resultReady(caalnew, true);
                        }else{
                            callBackGetDataAllCallUpdate.resultReady(caalnew, false);
                        }
                    }else{
                        callBackGetDataAllCallUpdate.resultReady(caalnew, false);
                    }

                } else {
                    Log.e("error", response.message() + "," + response.code());
                    callBackGetDataAllCallUpdate.resultReady(caalnew, false);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<mCallPlan>> call, Throwable t) {
                Log.e("REST error ", t.getMessage() + "xx" + t.toString());
                callBackGetDataAllCallUpdate.resultReady(caalnew, false);
            }


        });
    }

    public void checkNewUpdateAllCallPlanFromServer(final String salesId) {
        final ArrayList<mCallPlan> userList = new ArrayList<>();
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
        Map<String, String> params = new HashMap<String, String>();
        params.put("salesId", salesId);

        Call<ArrayList<mCallPlan>> userlist = service.getUpdateAllCallPlan(params);
        userlist.enqueue(new Callback<ArrayList<mCallPlan>>() {
            @Override
            public void onResponse(Call<ArrayList<mCallPlan>> call, Response<ArrayList<mCallPlan>> response) {
                // Log.e("isi respion cek update", response.code() + " isi code");

                if (response.isSuccessful()) {
                    //Log.e("isi respion", response.body().toString());
                    ArrayList<mCallPlan> result = response.body();

                    if (result != null) {
                        // Log.e("ada data", result.size()+" ada");
                        userList.addAll(result);
                        if (data.InsertUpateAllCallPlanWhere(result)) {
                            // Log.e("updat Server data", result.size()+" ada");
                            UpdateAllCallPlanStatusInServer(salesId, result);
                        }
                    }
                    callBackGetUpdateCallPlan.resultReady(userList, true);
                } else {
                    Log.e("error", response.message() + "," + response.code());
                    callBackGetUpdateCallPlan.resultReady(userList, false);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<mCallPlan>> call, Throwable t) {
                Log.e("REST error ", t.getMessage() + "xx" + t.toString());
                callBackGetUpdateCallPlan.resultReady(userList, false);
            }


        });
    }

    public void UpdateAllCallPlanStatusInServer(String salesId, ArrayList<mCallPlan> callplan) {
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


        List<rStringList> listcust = new ArrayList<>();
        for (mCallPlan cust : callplan) {
            rStringList c = new rStringList();
            c.setId(cust.getCallPlanId());
            listcust.add(c);
        }

        rCallPlanPost parameters = new rCallPlanPost();
        parameters.setSalesId(salesId);
        parameters.setCallPlanlist(listcust);


        Call<ArrayList<PostResult>> userlist = service.setUpdateAllCallPlanStatus(parameters);
        userlist.enqueue(new Callback<ArrayList<PostResult>>() {
            @Override
            public void onResponse(Call<ArrayList<PostResult>> call, Response<ArrayList<PostResult>> response) {
                // Log.e("isi respion cek update", response.code()+" isi code");
                if (response.isSuccessful()) {
                    //Log.e("isi respion", response.body().toString());
                    ArrayList<PostResult> result = response.body();

                    if (result != null) {
                        Log.e("ada data", result.size() + " ada");
                        // Toast.makeText(ctx, "Done.. ", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<PostResult>> call, Throwable t) {
                Log.e("REST", "err " + t.toString());

            }


        });
    }



    public boolean deleteCallPlanDraft(ArrayList<mCallPlan> callPlen) {
        boolean hsl = false;
        if(callPlen.size()>0) {
            for (mCallPlan cp : callPlen) {
                hsl = data.DeleteCallPlanDraft(cp.getCallPlanId());
                // Log.e("hasil delete","del"+hsl+cp.getCallPlanId());
            }
        }else{
            hsl=true;
        }
        return hsl;
    }

    ArrayList<mCallPlan> listCall=new ArrayList<>();
    public void updateCallPlanToServerStatusWithCallBack(String salesId, ArrayList<mCallPlan> callPlan) {
        if(callPlan!=null && callPlan.size()>0) {
            listCall = callPlan;
            String cypertext;
            try {
                cypertext = enkrip.bytesToHex(enkrip.encrypt("has password" + ":" + "has password"));

            } catch (Exception e) {
                e.printStackTrace();
                cypertext = "";
            }
            if (primariON) {
                service = RetroClient.getClient(cypertext).create(APIService.class);
            } else {
                service = RetroClient.getClientBackup(cypertext).create(APIService.class);
            }

            rCallPlan callplan = new rCallPlan(salesId, callPlan);
            Call<ArrayList<mCallPlan>> userlist = service.updateCallPlanRealisasi(callplan);
            userlist.enqueue(new Callback<ArrayList<mCallPlan>>() {
                @Override
                public void onResponse(Call<ArrayList<mCallPlan>> call, Response<ArrayList<mCallPlan>> response) {
                    //Log.e("isi respion cek update", response.code()+" isi code");
                    if (response.isSuccessful()) {
                        //Log.e("isi respion", response.body().toString());
                        ArrayList<mCallPlan> result = response.body();
                        if (result != null && result.size()>0) {
                            // Log.e("ada data", result.size()+" ada");
                            listCall = result;
                            data.InsertUpateAllCallPlan(result);
                            callBackGetData.resultReady(listCall, true);
                        }else{
                            callBackGetData.resultReady(listCall, true);
                        }

                    } else {
                        Log.e("error", response.message() + "," + response.code());
                        callBackGetData.resultReady(listCall, false);
                    }
                }

                @Override
                public void onFailure(Call<ArrayList<mCallPlan>> call, Throwable t) {
                    Log.e("REST error ", t.getMessage() + "xx" + t.toString());
                    callBackGetData.resultReady(listCall, false);
                }


            });
        }else{
            callBackGetData.resultReady(listCall, true);
        }
    }

    public  boolean InsertUpateAllCallPlan(mCallPlan callplan){
      return  data.InsertUpateAllCallPlan(callplan);
    }


    public boolean InsertUpateAllCallPlanWhere(ArrayList<mCallPlan> callPlen) {
        return data.InsertUpateAllCallPlanWhere(callPlen);
    }

    public boolean InsertUpateAllCallPlanDraft(ArrayList<mCallPlan> callPlen) {
        return data.InsertUpateAllCallPlanDraft(callPlen);
    }

    public ArrayList<mCallPlan> getAllCallPlanDraftPending(String salesId) {
        return getCallPlanDraft(salesId, "0", 1);
    }

    public ArrayList<mComplain> getAllCallPlanComplainPending() {
        return data.getTransCallPlanComplainByStatus("1");
    }

    public ArrayList<mComplain> getAllCallPlanComplainBetween(String salesid, String dateFrom,String dateTo,int custId) {
        return data.getTransCallPlanComplainBetween(salesid, dateFrom, dateTo,custId);
    }
    public ArrayList<mDemo> getAllCallPlanDemoPending() {
        return data.getTransCallPlanDemoByStatus("1");
    }
    public ArrayList<mDemo> getAllCallPlanDemoBetween(String salesid, String dateFrom,String dateTo,int custId) {
        return data.getTransCallPlanDemoBetween(salesid, dateFrom, dateTo,custId);
    }

    public ArrayList<mSample> getAllCallPlanSamplePending() {
        return data.getTransCallPlanSampleByStatus("1");
    }

    public ArrayList<mSample> getTransCallPlanSampleNotFinish(String custId) {
        return data.getTransCallPlanSampleNotFinish("3",custId);
    }
    public ArrayList<mSample> getAllCallPlanSampleBetween(String salesid, String dateFrom,String dateTo,int custId) {
        return data.getAllCallPlanSampleBetween(salesid, dateFrom, dateTo,custId);
    }
    public ArrayList<mCallPlanNote> getAllCallPlanNotePending() {
        return data.getTransCallPlanNoteByStatus("1");
    }

    public ArrayList<mCallPlan> getCallPlanDraft(String saleId, String typeDraft, int status) {
        // Log.e("select call",saleId+","+typeDraft+","+status);
        return data.getCallPlanDraftAllJoinBySales(saleId, typeDraft, status);
    }

    public ArrayList<mCallPlan> getCallPlanByDateDraft(String saleId, String byDate, int status) {
        // Log.e("select call",saleId+","+typeDraft+","+status);
        return data.getCallPlanDraftAllJoinByDateBySales(saleId, byDate, status);
    }

    public  ArrayList<mCallPlan> getAllCallPlanBetween(String salesid, String dateFrom,String dateTo,int custId){
        return data.getCallPlanAllJoinByDateBetween(salesid, dateFrom, dateTo,custId);
    }

    public  ArrayList<mCallPlanNote> getAllBIBetween(String salesid, String dateFrom,String dateTo,int custId){
        return data.getTransCallPlanNoteByDateBetween(salesid, dateFrom, dateTo,custId);
    }

    public ArrayList<mCallPlan> getCallPlanByDate(String saleId, String byDate) {
        // Log.e("select call",saleId+","+byDate+","+status);
        return data.getCallPlanAllJoinByDateBySales(saleId, byDate);
    }

    public mCallPlan getCallPlanById(String callplanId) {
        // Log.e("select call",saleId+","+byDate+","+status);
        return data.getCallPlanAllJoinByCallPlanId(callplanId);
    }

    public ArrayList<mCallPlan> getCallPlanByStatus(int statusSend) {
        return data.getCallPlanAllJoinByStatusSend(statusSend);
    }
    //callplannote
    public void updateCallPlanNoteToServer(String salesId, final mCallPlanNote callPlan) {

        //untuk yang del didelete dari draftcallplan;
        String cypertext;
        try {
            cypertext = enkrip.bytesToHex(enkrip.encrypt("has password" + ":" + "has password"));

        } catch (Exception e) {
            e.printStackTrace();
            cypertext = "";
        }
        if (primariON) {
            service = RetroClient.getClient(cypertext).create(APIService.class);
        } else {
            service = RetroClient.getClientBackup(cypertext).create(APIService.class);
        }
        if (callPlan != null) {
            final ArrayList<mCallPlanNote> callPlanNotes = new ArrayList<mCallPlanNote>();
            callPlanNotes.add(callPlan);
            if (data.InsertUpateAllTransCallPlanNoteWhere(callPlanNotes)) {
                ArrayList<mCallPlanNote> callPlanNotes1 = data.getTransCallPlanNoteByStatus("1");
                rCallPlanNote rCPN = new rCallPlanNote();
                rCPN.setSalesId(salesId);
                rCPN.setCallplannotelist(callPlanNotes1);
                Call<ArrayList<mCallPlanNote>> userlist = service.updateTransCallPlanNote(rCPN);
                userlist.enqueue(new Callback<ArrayList<mCallPlanNote>>() {
                    @Override
                    public void onResponse(Call<ArrayList<mCallPlanNote>> call, Response<ArrayList<mCallPlanNote>> response) {
                        //Log.e("isi respion cek update", response.code()+" isi code");

                        if (response.isSuccessful()) {
                            //Log.e("isi respion", response.body().toString());
                            ArrayList<mCallPlanNote> result = response.body();
                            if (result != null && result.size() > 0) {
                                for (mCallPlanNote cu : result) {
                                    data.updateTransCallNoteStatus(cu.getCallPlanNoteId(), cu.getCallPlanId(), 0);
                                }
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ArrayList<mCallPlanNote>> call, Throwable t) {
                        Log.e("REST error ", t.getMessage() + "xx" + t.toString());
                        //callPlanNotes.add(callPlan);
                        // data.InsertUpateAllTransCallPlanNoteWhere(callPlanNotes);
                    }


                });
            } else {

            }

        }
    }
    //callplan note resend

    public void reSendCallPlanNoteToServer(String salesId, final ArrayList<mCallPlanNote> callPlanNotes) {
        if (callPlanNotes != null && callPlanNotes.size() > 0) {

            //untuk yang del didelete dari draftcallplan;
            String cypertext;
            try {
                cypertext = enkrip.bytesToHex(enkrip.encrypt("has password" + ":" + "has password"));

            } catch (Exception e) {
                e.printStackTrace();
                cypertext = "";
            }
            if (primariON) {
                service = RetroClient.getClient(cypertext).create(APIService.class);
            } else {
                service = RetroClient.getClientBackup(cypertext).create(APIService.class);
            }
            if (callPlanNotes != null) {
                rCallPlanNote rCPN = new rCallPlanNote();
                rCPN.setSalesId(salesId);
                rCPN.setCallplannotelist(callPlanNotes);
                Call<ArrayList<mCallPlanNote>> userlist = service.updateTransCallPlanNote(rCPN);
                userlist.enqueue(new Callback<ArrayList<mCallPlanNote>>() {
                    @Override
                    public void onResponse(Call<ArrayList<mCallPlanNote>> call, Response<ArrayList<mCallPlanNote>> response) {
                        //Log.e("isi respion cek update", response.code()+" isi code");

                        if (response.isSuccessful()) {
                            //Log.e("isi respion", response.body().toString());
                            ArrayList<mCallPlanNote> result = response.body();
                            if (result != null && result.size() > 0) {
                                boolean hsl = false;
                                for (mCallPlanNote cu : result) {
                                    hsl = data.updateTransCallNoteStatus(cu.getCallPlanNoteId(), cu.getCallPlanId(), 0);
                                }
                                if (hsl) {
                                    callBackGetDataBIN.resultReady(true);
                                } else {
                                    callBackGetDataBIN.resultReady(false);
                                }
                            } else {
                                callBackGetDataBIN.resultReady(false);
                            }
                        } else {
                            callBackGetDataBIN.resultReady(false);
                        }
                    }

                    @Override
                    public void onFailure(Call<ArrayList<mCallPlanNote>> call, Throwable t) {
                        Log.e("REST error ", t.getMessage() + "xx" + t.toString());
                        callBackGetDataBIN.resultReady(false);
                    }


                });
            }
        } else {
            callBackGetDataBIN.resultReady(true);
        }
    }

    //callplan demo
    public void updateCallPlanDemoToServer(String salesId, final mDemo callPlan, final ArrayList<mTrackingPicture> listGambar) {

        //untuk yang del didelete dari draftcallplan;
        String cypertext;
        try {
            cypertext = enkrip.bytesToHex(enkrip.encrypt("has password" + ":" + "has password"));

        } catch (Exception e) {
            e.printStackTrace();
            cypertext = "";
        }
        if (primariON) {
            service = RetroClient.getClient(cypertext).create(APIService.class);
        } else {
            service = RetroClient.getClientBackup(cypertext).create(APIService.class);
        }
        if (callPlan != null) {
            final ArrayList<mDemo> callPlanDemo = new ArrayList<>();
            callPlanDemo.add(callPlan);
            if (data.InsertUpateAllTransCallPlanDemoWhere(callPlanDemo)) {
                if (listGambar != null && listGambar.size() > 0) {
                    data.InsertUpateAllTrackingPicture(listGambar);
                }
                ArrayList<mDemo> callPlanDemo1 = data.getTransCallPlanDemoByStatus("1");
                rCallPlanDemo rCPd = new rCallPlanDemo();
                rCPd.setSalesId(salesId);
                rCPd.setCallplandemolist(callPlanDemo1);
                Call<ArrayList<mDemo>> userlist = service.updateTransCallPlanDemo(rCPd);
                userlist.enqueue(new Callback<ArrayList<mDemo>>() {
                    @Override
                    public void onResponse(Call<ArrayList<mDemo>> call, Response<ArrayList<mDemo>> response) {
                        //Log.e("isi respion cek update", response.code()+" isi code");

                        if (response.isSuccessful()) {
                            Log.e("isi respion", response.body().toString());
                            ArrayList<mDemo> result = response.body();
                            if (result != null && result.size() > 0) {
                                for (mDemo cu : result) {
                                    if (data.updateTransCallDemoStatus(cu.getDemoId(), cu.getCallPlanId(), 0)) {
                                        ArrayList<mTrackingPicture> tpic = data.getTrackingPictureAllByRef(cu.getDemoId(), 1);
                                        for (mTrackingPicture tp : tpic) {
                                            trackingController.insertTrackingPictureToServer(tp);
                                        }
                                    }
                                }
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ArrayList<mDemo>> call, Throwable t) {
                        Log.e("REST error ", t.getMessage() + "xx" + t.toString());
                        //callPlanNotes.add(callPlan);
                        // data.InsertUpateAllTransCallPlanNoteWhere(callPlanNotes);
                    }


                });
            } else {

            }

        }
    }

    public void reSendCallPlanDemoToServer(String salesId, final ArrayList<mDemo> callPlanDemo) {
        if (callPlanDemo != null && callPlanDemo.size() > 0) {
            //untuk yang del didelete dari draftcallplan;
            String cypertext;
            try {
                cypertext = enkrip.bytesToHex(enkrip.encrypt("has password" + ":" + "has password"));

            } catch (Exception e) {
                e.printStackTrace();
                cypertext = "";
            }
            if (primariON) {
                service = RetroClient.getClient(cypertext).create(APIService.class);
            } else {
                service = RetroClient.getClientBackup(cypertext).create(APIService.class);
            }
            if (callPlanDemo != null) {
                rCallPlanDemo rCPd = new rCallPlanDemo();
                rCPd.setSalesId(salesId);
                rCPd.setCallplandemolist(callPlanDemo);
                Call<ArrayList<mDemo>> userlist = service.updateTransCallPlanDemo(rCPd);
                userlist.enqueue(new Callback<ArrayList<mDemo>>() {
                    @Override
                    public void onResponse(Call<ArrayList<mDemo>> call, Response<ArrayList<mDemo>> response) {
                        //Log.e("isi respion cek update", response.code()+" isi code");

                        if (response.isSuccessful()) {
                            // Log.e("isi respion", response.body().toString());
                            ArrayList<mDemo> result = response.body();
                            if (result != null && result.size() > 0) {
                                boolean hsl = false;
                                for (mDemo cu : result) {
                                    hsl = data.updateTransCallDemoStatus(cu.getDemoId(), cu.getCallPlanId(), 0);

                                }
                                if (hsl) {
                                    callBackGetDataDemo.resultReady(true);
                                } else {
                                    callBackGetDataDemo.resultReady(false);
                                }
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ArrayList<mDemo>> call, Throwable t) {
                        Log.e("REST error ", t.getMessage() + "xx" + t.toString());
                        callBackGetDataDemo.resultReady(false);
                    }
                });

            }
        } else {
            callBackGetDataDemo.resultReady(true);
        }
    }

    ArrayList<mDemo> caalnewdemo=new ArrayList<>();
    public void checkNewUpdateBetweenAllDemoFromServer(final String salesId,String dateFrom,String dateTo,int custId) {
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
        Map<String, String> params = new HashMap<String, String>();
        params.put("salesId", salesId);
        params.put("dateFrom", dateFrom);
        params.put("dateTo", dateTo);
        params.put("custId", String.valueOf(custId));

        Call<ArrayList<mDemo>> userlist = service.getUpdateAllDemoBetween(params);
        userlist.enqueue(new Callback<ArrayList<mDemo>>() {
            @Override
            public void onResponse(Call<ArrayList<mDemo>> call, Response<ArrayList<mDemo>> response) {
                // Log.e("isi respion cek update", response.code() + " isi code");

                if (response.isSuccessful()) {
                    //Log.e("isi respion", response.body().toString());
                    ArrayList<mDemo> result = response.body();

                    if (result != null && result.size()>0) {
                        // Log.e("ada data", result.size()+" ada");
                        caalnewdemo.addAll(result);
                        if (data.InsertUpateAllTransCallPlanDemoWhere(result)) {
                            callBackDemo.resultReady(caalnewdemo, true);
                        }else{
                            callBackDemo.resultReady(caalnewdemo, false);
                        }
                    }else{
                        callBackDemo.resultReady(caalnewdemo, false);
                    }

                } else {
                    Log.e("error", response.message() + "," + response.code());
                    callBackDemo.resultReady(caalnewdemo, false);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<mDemo>> call, Throwable t) {
                Log.e("REST error ", t.getMessage() + "xx" + t.toString());
                callBackDemo.resultReady(caalnewdemo, false);
            }


        });
    }

    //callplan complain
    public void updateCallPlanComplainToServer(String salesId, final mComplain callPlan, final ArrayList<mTrackingPicture> listGambar) {

        //untuk yang del didelete dari draftcallplan;
        String cypertext;
        try {
            cypertext = enkrip.bytesToHex(enkrip.encrypt("has password" + ":" + "has password"));

        } catch (Exception e) {
            e.printStackTrace();
            cypertext = "";
        }
        if (primariON) {
            service = RetroClient.getClient(cypertext).create(APIService.class);
        } else {
            service = RetroClient.getClientBackup(cypertext).create(APIService.class);
        }
        if (callPlan != null) {
            final ArrayList<mComplain> callPlanComplain = new ArrayList<>();
            callPlanComplain.add(callPlan);
            if (data.InsertUpateAllTransCallPlanComplainWhere(callPlanComplain)) {
                if (listGambar != null && listGambar.size() > 0) {
                    if(data.InsertUpateAllTrackingPicture(listGambar)){
                        for (mTrackingPicture tp : listGambar) {
                            trackingController.insertTrackingPictureToServer(tp);
                        }
                    }
                }
                ArrayList<mComplain> callPlanComplain1 = data.getTransCallPlanComplainByStatus("1");
                rCallPlanComplain rCPc = new rCallPlanComplain();
                rCPc.setSalesId(salesId);
                rCPc.setCallplancomplainlist(callPlanComplain1);
                Call<ArrayList<mComplain>> userlist = service.updateTransCallPlanComplain(rCPc);
                userlist.enqueue(new Callback<ArrayList<mComplain>>() {
                    @Override
                    public void onResponse(Call<ArrayList<mComplain>> call, Response<ArrayList<mComplain>> response) {
                        //Log.e("isi respion cek update", response.code()+" isi code");

                        if (response.isSuccessful()) {
                            ArrayList<mComplain> result = response.body();
                            if (result != null && result.size() > 0) {
                                for (mComplain cu : result) {
                                    if (data.updateTransCallComplainStatus(cu.getComplainId(), cu.getCallPlanId(), 0)) {
                                        //ArrayList<mTrackingPicture> tpic = data.getTrackingPictureAllByRef(cu.getComplainId(), 1);
                                        //for (mTrackingPicture tp : tpic) {
                                        //    trackingController.insertTrackingPictureToServer(tp);
                                        //}
                                    }
                                }
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ArrayList<mComplain>> call, Throwable t) {
                        Log.e("REST error ", t.getMessage() + "xx" + t.toString());
                        //callPlanNotes.add(callPlan);
                        // data.InsertUpateAllTransCallPlanNoteWhere(callPlanNotes);
                    }


                });
            } else {

            }

        }
    }

    //resend complain
    public void reSendCallPlanComplainToServer(String salesId, final ArrayList<mComplain> callPlanComplain) {
        if (callPlanComplain != null && callPlanComplain.size() > 0) {
            //untuk yang del didelete dari draftcallplan;
            String cypertext;
            try {
                cypertext = enkrip.bytesToHex(enkrip.encrypt("has password" + ":" + "has password"));

            } catch (Exception e) {
                e.printStackTrace();
                cypertext = "";
            }
            if (primariON) {
                service = RetroClient.getClient(cypertext).create(APIService.class);
            } else {
                service = RetroClient.getClientBackup(cypertext).create(APIService.class);
            }
            if (callPlanComplain != null) {

                rCallPlanComplain rCPc = new rCallPlanComplain();
                rCPc.setSalesId(salesId);
                rCPc.setCallplancomplainlist(callPlanComplain);
                Call<ArrayList<mComplain>> userlist = service.updateTransCallPlanComplain(rCPc);
                userlist.enqueue(new Callback<ArrayList<mComplain>>() {
                    @Override
                    public void onResponse(Call<ArrayList<mComplain>> call, Response<ArrayList<mComplain>> response) {
                        if (response.isSuccessful()) {
                            ArrayList<mComplain> result = response.body();
                            if (result != null && result.size() > 0) {
                                boolean hsl = false;
                                for (mComplain cu : result) {
                                    hsl = data.updateTransCallComplainStatus(cu.getComplainId(), cu.getCallPlanId(), 0);

                                }
                                if (hsl) {
                                    callBackGetDataComplain.resultReady(true);
                                } else {
                                    callBackGetDataComplain.resultReady(false);
                                }

                            } else {
                                callBackGetDataComplain.resultReady(false);
                            }
                        } else {
                            callBackGetDataComplain.resultReady(false);
                        }
                    }

                    @Override
                    public void onFailure(Call<ArrayList<mComplain>> call, Throwable t) {
                        Log.e("REST error ", t.getMessage() + "xx" + t.toString());
                        callBackGetDataComplain.resultReady(false);
                    }


                });

            } else {
                callBackGetDataComplain.resultReady(true);
            }
        } else {
            callBackGetDataComplain.resultReady(true);
        }
    }
    //cek complain from server
    ArrayList<mComplain> caalnewncomplain=new ArrayList<>();
    public void checkNewUpdateBetweenAllComplainFromServer(final String salesId,String dateFrom,String dateTo,int custId) {
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
        Map<String, String> params = new HashMap<String, String>();
        params.put("salesId", salesId);
        params.put("dateFrom", dateFrom);
        params.put("dateTo", dateTo);
        params.put("custId", String.valueOf(custId));

        Call<ArrayList<mComplain>> userlist = service.getUpdateAllComplainBetween(params);
        userlist.enqueue(new Callback<ArrayList<mComplain>>() {
            @Override
            public void onResponse(Call<ArrayList<mComplain>> call, Response<ArrayList<mComplain>> response) {
                // Log.e("isi respion cek update", response.code() + " isi code");

                if (response.isSuccessful()) {
                    //Log.e("isi respion", response.body().toString());
                    ArrayList<mComplain> result = response.body();

                    if (result != null && result.size()>0) {
                        // Log.e("ada data", result.size()+" ada");
                        caalnewncomplain.addAll(result);
                        if (data.InsertUpateAllTransCallPlanComplainWhere(result)) {
                            callBackComplain.resultReady(caalnewncomplain, true);
                        }else{
                            callBackComplain.resultReady(caalnewncomplain, false);
                        }
                    }else{
                        callBackComplain.resultReady(caalnewncomplain, false);
                    }

                } else {
                    Log.e("error", response.message() + "," + response.code());
                    callBackComplain.resultReady(caalnewncomplain, false);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<mComplain>> call, Throwable t) {
                Log.e("REST error ", t.getMessage() + "xx" + t.toString());
                callBackComplain.resultReady(caalnewncomplain, false);
            }


        });
    }
    //callplan sample

    public void getAllCallPlanSamplePendingCustomerFromServer(String salesId, String customerId) {

        //untuk yang del didelete dari draftcallplan;
        String cypertext;
        try {
            cypertext = enkrip.bytesToHex(enkrip.encrypt("has password" + ":" + "has password"));

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
        params.put("SalesId", salesId);
        params.put("CustId", customerId);
        Call<ArrayList<mSample>> userlist = service.getTransCallPlanSampleCustomer(params);
        userlist.enqueue(new Callback<ArrayList<mSample>>() {
            @Override
            public void onResponse(Call<ArrayList<mSample>> call, Response<ArrayList<mSample>> response) {
                //Log.e("isi respion cek update", response.code()+" isi code");

                if (response.isSuccessful()) {
                    ArrayList<mSample> result = response.body();
                    if (result != null && result.size() > 0) {
                       /* for (mSample cu : result) {
                            if (data.updateTransCallSampleStatus(cu.getSampleId(), cu.getCallPlanId(), 0)) {
                                callBackGetDataSampleRefresh.resultReady(true);
                            }
                        }*/
                       if(data.InsertUpateAllTransCallPlanSampleWhere(result)) {
                           callBackGetDataSampleRefresh.resultReady(true);
                       }else{
                           callBackGetDataSampleRefresh.resultReady(false);
                       }
                    }else{
                        callBackGetDataSampleRefresh.resultReady(false);
                    }
                }else{
                    callBackGetDataSampleRefresh.resultReady(false);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<mSample>> call, Throwable t) {
                Log.e("REST error ", t.getMessage() + "xx" + t.toString());
                //callPlanNotes.add(callPlan);
                // data.InsertUpateAllTransCallPlanNoteWhere(callPlanNotes);
                callBackGetDataSampleRefresh.resultReady(false);
            }


        });

    }

    public void updateCallPlanSampleToServer(String salesId, final mSample callPlan, final ArrayList<mTrackingPicture> listGambar) {

        //untuk yang del didelete dari draftcallplan;
        String cypertext;
        try {
            cypertext = enkrip.bytesToHex(enkrip.encrypt("has password" + ":" + "has password"));

        } catch (Exception e) {
            e.printStackTrace();
            cypertext = "";
        }
        if (primariON) {
            service = RetroClient.getClient(cypertext).create(APIService.class);
        } else {
            service = RetroClient.getClientBackup(cypertext).create(APIService.class);
        }
        if (callPlan != null) {
            final ArrayList<mSample> callPlanSample = new ArrayList<>();
            callPlanSample.add(callPlan);
            if (data.InsertUpateAllTransCallPlanSampleWhere(callPlanSample)) {

                if (listGambar != null && listGambar.size() > 0) {
                    data.InsertUpateAllTrackingPicture(listGambar);
                }
                ArrayList<mSample> callPlanSample1 = data.getTransCallPlanSampleByStatus("1");
                rCallPlanSample rCPs = new rCallPlanSample();
                rCPs.setSalesId(salesId);
                rCPs.setCallplansamplelist(callPlanSample1);
                Call<ArrayList<mSample>> userlist = service.updateTransCallPlanSample(rCPs);
                userlist.enqueue(new Callback<ArrayList<mSample>>() {
                    @Override
                    public void onResponse(Call<ArrayList<mSample>> call, Response<ArrayList<mSample>> response) {
                        //Log.e("isi respion cek update", response.code()+" isi code");

                        if (response.isSuccessful()) {
                            ArrayList<mSample> result = response.body();
                            if (result != null && result.size() > 0) {
                                for (mSample cu : result) {
                                    if (data.updateTransCallSampleStatus(cu.getSampleId(), cu.getCallPlanId(), 0)) {
                                        ArrayList<mTrackingPicture> tpic = data.getTrackingPictureAllByRef(cu.getSampleId(), 1);
                                        for (mTrackingPicture tp : tpic) {
                                            trackingController.insertTrackingPictureToServer(tp);
                                        }
                                    }
                                }
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ArrayList<mSample>> call, Throwable t) {
                        Log.e("REST error ", t.getMessage() + "xx" + t.toString());
                        //callPlanNotes.add(callPlan);
                        // data.InsertUpateAllTransCallPlanNoteWhere(callPlanNotes);
                    }


                });
            } else {

            }

        }
    }

    ArrayList<mSample> caalnewnsample=new ArrayList<>();
    public void checkNewUpdateBetweenAllSampleFromServer(final String salesId,String dateFrom,String dateTo,int custId) {
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
        Map<String, String> params = new HashMap<String, String>();
        params.put("salesId", salesId);
        params.put("dateFrom", dateFrom);
        params.put("dateTo", dateTo);
        params.put("custId", String.valueOf(custId));

        Call<ArrayList<mSample>> userlist = service.getUpdateAllSampleBetween(params);
        userlist.enqueue(new Callback<ArrayList<mSample>>() {
            @Override
            public void onResponse(Call<ArrayList<mSample>> call, Response<ArrayList<mSample>> response) {
                // Log.e("isi respion cek update", response.code() + " isi code");

                if (response.isSuccessful()) {
                    //Log.e("isi respion", response.body().toString());
                    ArrayList<mSample> result = response.body();

                    if (result != null && result.size()>0) {
                        // Log.e("ada data", result.size()+" ada");
                        caalnewnsample.addAll(result);
                        if (data.InsertUpateAllTransCallPlanSampleFromServer(result)) {
                            callBackSample.resultReady(caalnewnsample, true);
                        }else{
                            callBackSample.resultReady(caalnewnsample, false);
                        }
                    }else{
                        callBackSample.resultReady(caalnewnsample, false);
                    }

                } else {
                    Log.e("error", response.message() + "," + response.code());
                    callBackSample.resultReady(caalnewnsample, false);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<mSample>> call, Throwable t) {
                Log.e("REST error ", t.getMessage() + "xx" + t.toString());
                callBackSample.resultReady(caalnewnsample, false);
            }


        });
    }

    //sample resend
    public void reSendCallPlanSampleToServer(String salesId, final ArrayList<mSample> callPlanSample) {
        if (callPlanSample != null && callPlanSample.size() > 0) {
            String cypertext;
            try {
                cypertext = enkrip.bytesToHex(enkrip.encrypt("has password" + ":" + "has password"));

            } catch (Exception e) {
                e.printStackTrace();
                cypertext = "";
            }
            if (primariON) {
                service = RetroClient.getClient(cypertext).create(APIService.class);
            } else {
                service = RetroClient.getClientBackup(cypertext).create(APIService.class);
            }
            if (callPlanSample != null) {
                rCallPlanSample rCPs = new rCallPlanSample();
                rCPs.setSalesId(salesId);
                rCPs.setCallplansamplelist(callPlanSample);
                Call<ArrayList<mSample>> userlist = service.updateTransCallPlanSample(rCPs);
                userlist.enqueue(new Callback<ArrayList<mSample>>() {
                    @Override
                    public void onResponse(Call<ArrayList<mSample>> call, Response<ArrayList<mSample>> response) {
                        //Log.e("isi respion cek update", response.code()+" isi code");

                        if (response.isSuccessful()) {
                            ArrayList<mSample> result = response.body();
                            if (result != null && result.size() > 0) {
                                boolean hsl = false;
                                for (mSample cu : result) {
                                    if (data.updateTransCallSampleStatus(cu.getSampleId(), cu.getCallPlanId(), 0)) {
                                        hsl =true;
                                        ArrayList<mTrackingPicture> tpic = data.getTrackingPictureAllByRef(cu.getSampleId(), 1);
                                        for (mTrackingPicture tp : tpic) {
                                            trackingController.insertTrackingPictureToServer(tp);
                                        }
                                    }
                                }
                                if (hsl) {
                                    callBackGetDataSample.resultReady(true);
                                } else {
                                    callBackGetDataSample.resultReady(false);
                                }
                            }else {
                                callBackGetDataSample.resultReady(false);
                            }
                        }else{
                            callBackGetDataSample.resultReady(false);
                        }
                    }

                    @Override
                    public void onFailure(Call<ArrayList<mSample>> call, Throwable t) {
                        Log.e("REST error ", t.getMessage() + "xx" + t.toString());
                        callBackGetDataSample.resultReady(false);
                    }

                });
            }
        } else {
            callBackGetDataSample.resultReady(true);
        }
    }

//todolist

    public void getAllTodoListFromServer(final String salesId, final String custId) {
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
        Map<String, String> params = new HashMap<String, String>();
        params.put("SalesId", salesId);
        params.put("CustId", custId);

        Call<ArrayList<mTodoList>> userlist = service.getDataTodoList(params);
        userlist.enqueue(new Callback<ArrayList<mTodoList>>() {
            @Override
            public void onResponse(Call<ArrayList<mTodoList>> call, Response<ArrayList<mTodoList>> response) {
                //Log.e("isi respion cek update", response.code()+" isi code");
                if (response.isSuccessful()) {
                    //Log.e("isi respion", response.body().toString());
                    ArrayList<mTodoList> result = response.body();
                    if (result != null && result.size() > 0) {
                        // Log.e("ada data", result.size()+" ada");
                        listToDo = result;
                       if(data.InsertUpateAllTodoList(result)){
                           setUpdateAllTodoListStatusNewInServer(salesId,result);
                       }

                    }
                    callBackGetDataToDo.resultReady(listToDo, true);
                } else {
                    Log.e("error", response.message() + "," + response.code());
                    callBackGetDataToDo.resultReady(listToDo, false);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<mTodoList>> call, Throwable t) {
                Log.e("REST error ", t.getMessage() + "xx" + t.toString());
                callBackGetDataToDo.resultReady(listToDo, false);
            }


        });
    }

    public void setUpdateAllTodoListStatusNewInServer(String salesId, ArrayList<mTodoList> todo) {
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


        List<rStringList> listcust = new ArrayList<>();
        for (mTodoList cust : todo) {
            rStringList c = new rStringList();
            c.setId(String.valueOf(cust.getRecId()));
            listcust.add(c);
        }

        rCallPlanPost parameters = new rCallPlanPost();
        parameters.setSalesId(salesId);
        parameters.setCallPlanlist(listcust);


        Call<ArrayList<PostResult>> userlist = service.setUpdateAllTodoListStatusNew(parameters);
        userlist.enqueue(new Callback<ArrayList<PostResult>>() {
            @Override
            public void onResponse(Call<ArrayList<PostResult>> call, Response<ArrayList<PostResult>> response) {
                // Log.e("isi respion cek update", response.code()+" isi code");
                if (response.isSuccessful()) {
                    //Log.e("isi respion", response.body().toString());
                    ArrayList<PostResult> result = response.body();

                    if (result != null) {
                        Log.e("ada data", result.size() + " ada");
                        // Toast.makeText(ctx, "Done.. ", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<PostResult>> call, Throwable t) {
                Log.e("REST", "err " + t.toString());

            }


        });
    }

    public void updateTodoListToServer(mTodoList todo) {
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

        Call<mTodoList> userlist = service.updateTodoList(todo);
        userlist.enqueue(new Callback<mTodoList>() {
            @Override
            public void onResponse(Call<mTodoList> call, Response<mTodoList> response) {
                //Log.e("isi respion cek update", response.code()+" isi code");
                if (response.isSuccessful()) {
                    //Log.e("isi respion", response.body().toString());
                    mTodoList result = response.body();
                    if (result != null) {
                        // Log.e("ada data", result.isStatusRead()+" ada");
                        if (result.isStatusRead()) {
                            ArrayList<mTodoList> tod = new ArrayList<>();
                            tod.add(result);
                            data.InsertUpateAllTodoList(tod);
                        }

                    }
                }
            }

            @Override
            public void onFailure(Call<mTodoList> call, Throwable t) {
                Log.e("REST error ", t.getMessage() + "xx" + t.toString());
            }


        });
    }

    public ArrayList<mTodoList> getAllToDoByCustId(String custId) {
        return data.getTodoListByCustId(custId);
    }

    public mTodoList getToDoById(String Id) {
        return data.getTodoListById(Id);
    }

}
