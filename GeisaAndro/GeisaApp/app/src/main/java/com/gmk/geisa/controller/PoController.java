package com.gmk.geisa.controller;

import android.content.Context;
import android.util.Log;

import com.gmk.geisa.databases.mDB;
import com.gmk.geisa.helper.Enkrip;
import com.gmk.geisa.model.Post.rPoPost;
import com.gmk.geisa.model.Post.rStringList;
import com.gmk.geisa.model.PostResult;
import com.gmk.geisa.model.mPO;
import com.gmk.geisa.model.mPoLine;
import com.gmk.geisa.service.APIService;
import com.gmk.geisa.service.RetroClient;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by kenjinsan on 6/21/17.
 */

public class PoController {
    private static mDB data;
    private APIService service;
    private static PoController instance = null;
    static Enkrip enkrip;
    static Context ctx;
    static boolean primariON = true;
    private ArrayList<mPO> list = new ArrayList<>();

    public static PoController getInstance(Context context) {
        if (instance == null) {
            instance = new PoController();
        }
        data = mDB.getInstance(context);
        ctx = context;
        enkrip = new Enkrip();
        return instance;
    }

    //callbak sync
    private CallBackGetData callBackGetData;

    public void setCallBackGetData(CallBackGetData callBackGetData) {
        this.callBackGetData = callBackGetData;
    }

    public interface CallBackGetData {
        public void resultReady(ArrayList<mPO> customers, boolean hasil);
    }

    //calback get update customer
    private CallBackGetDataSync callBackGetDataSync;

    public void setCallBackGetDataSync(CallBackGetDataSync callBackGetDataSync) {
        this.callBackGetDataSync = callBackGetDataSync;
    }

    public interface CallBackGetDataSync {
        public void resultReady(boolean hasil);
    }

    private CallBackGetDataAllPo callBackGetDataAllPo;

    public void setCallBackGetDataAllPo(CallBackGetDataAllPo callBackGetDataAllPo) {
        this.callBackGetDataAllPo = callBackGetDataAllPo;
    }

    public interface CallBackGetDataAllPo {
        public void resultReady(ArrayList<mPO> po,boolean hasil);
    }

    /*end callback*/

    public void confirmPoToServer(final mPO po) {
        if (po != null) {
            data.InsertUpateAllPO(po);
        }
        String cypertext;
        try {
            cypertext = enkrip.bytesToHex(enkrip.encrypt("has password" + ":" + "has password"));
            //  Log.e("sn", cypertext);

        } catch (Exception e) {
            e.printStackTrace();
            cypertext = "";
        }
        if (primariON) {
            service = RetroClient.getClient(cypertext, 30).create(APIService.class);
        } else {
            service = RetroClient.getClientBackup(cypertext, 30).create(APIService.class);
        }
        //Map<String, String> params = new HashMap<String, String>();
        //params.put("salesId", salesId);
        //params.put("pass", pass);

        Call<mPO> userlist = service.confirmPO(po);
        userlist.enqueue(new Callback<mPO>() {
            @Override
            public void onResponse(Call<mPO> call, Response<mPO> response) {
                //Log.e("isi respion cek update", response.code()+" isi code");
                if (response.isSuccessful()) {
                    if (response.code() == 200) {
                        mPO result = response.body();
                        if (result != null) {
                            list.add(result);
                            //Log.e("status",result.getStatusSend()+",");
                            data.InsertUpateAllPO(result);

                        }
                        callBackGetData.resultReady(list, true);
                    } else if (response.code() == 500) {
                        //mPO result = response.body();
                        //if (result != null) {
                            list.add(po);
                          //  data.InsertUpateAllPO(result);

                        //}
                        callBackGetData.resultReady(list, false);
                    } else {
                        callBackGetData.resultReady(list, false);
                    }
                    //Log.e("isi respion", response.body().toString());

                } else {
                    callBackGetData.resultReady(list, false);
                    Log.e("error", response.message() + "," + response.code());
                }
            }

            @Override
            public void onFailure(Call<mPO> call, Throwable t) {
                Log.e("REST error ", t.getMessage() + "xx" + t.toString());
                callBackGetData.resultReady(list, false);
            }


        });
    }

    public void updatePoToServer(final mPO po) {
        if (po != null) {
            data.InsertUpateAllPO(po);
        }
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
        //Map<String, String> params = new HashMap<String, String>();
        //params.put("salesId", salesId);
        //params.put("pass", pass);

        Call<mPO> userlist = service.updatePO(po);
        userlist.enqueue(new Callback<mPO>() {
            @Override
            public void onResponse(Call<mPO> call, Response<mPO> response) {
                //Log.e("isi respion cek update", response.code()+" isi code");
                if (response.isSuccessful()) {
                    if (response.code() == 200) {
                       // Log.e("isi respion ", response.body().toString());
                        mPO result = response.body();
                        if (result != null) {
                            list.add(result);
                            data.InsertUpateAllPO(result);

                        }
                        callBackGetData.resultReady(list, true);
                    } else if (response.code() == 500) {
                       // Log.e("isi respion err ", response.body().toString());
                        //mPO result = response.body();
                        //if (result != null) {
                            list.add(po);
                            //data.InsertUpateAllPO(result);

                        //}
                        callBackGetData.resultReady(list, false);
                    } else {
                        callBackGetData.resultReady(list, false);
                    }
                    //Log.e("isi respion", response.body().toString());

                } else {
                    callBackGetData.resultReady(list, false);
                    //Log.e("error", response.message() + "," + response.code());
                }
            }

            @Override
            public void onFailure(Call<mPO> call, Throwable t) {
                //Log.e("REST error ", t.getMessage() + "xx" + t.toString());
                callBackGetData.resultReady(list, false);
            }


        });
    }

    public void updatePoToServer(final ArrayList<mPO> allpo) {
        final boolean[] hasil = {false};
        if (allpo != null && allpo.size() > 0) {
            for (mPO po : allpo) {
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
                //Map<String, String> params = new HashMap<String, String>();
                //params.put("salesId", salesId);
                //params.put("pass", pass);

                Call<mPO> userlist = service.updatePO(po);
                userlist.enqueue(new Callback<mPO>() {
                    @Override
                    public void onResponse(Call<mPO> call, Response<mPO> response) {
                        //Log.e("isi respion cek update", response.code()+" isi code");
                        if (response.isSuccessful()) {
                            if (response.code() == 200) {
                                mPO result = response.body();
                                if (result != null) {
                                    list.add(result);
                                    data.InsertUpateAllPO(result);

                                }
                                hasil[0] = true;
                                callBackGetDataSync.resultReady(true);
                            } else if (response.code() == 500) {
                                mPO result = response.body();
                                if (result != null) {
                                    list.add(result);
                                    data.InsertUpateAllPO(result);

                                }
                                hasil[0] = false;
                                callBackGetDataSync.resultReady(false);
                            } else {
                                hasil[0] = false;
                                callBackGetDataSync.resultReady(false);
                            }
                            //Log.e("isi respion", response.body().toString());

                        } else {
                            callBackGetDataSync.resultReady(false);
                            Log.e("error", response.message() + "," + response.code());
                        }
                    }

                    @Override
                    public void onFailure(Call<mPO> call, Throwable t) {
                        //Log.e("REST error ", t.getMessage() + "xx" + t.toString());
                        hasil[0] = false;
                        callBackGetDataSync.resultReady(false);
                    }


                });
            }

        } else {
            callBackGetDataSync.resultReady(true);
        }


    }

    public void getPoPPActiveFromServer(String salesId) {
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
       // params.put("custId", custId);

        Call<ArrayList<mPO>> userlist = service.GetPPActiveByCust(params);
        userlist.enqueue(new Callback<ArrayList<mPO>>() {
            @Override
            public void onResponse(Call<ArrayList<mPO>> call, Response<ArrayList<mPO>> response) {
                //Log.e("isi respion cek update", response.code()+" isi code");
                if (response.isSuccessful()) {
                    ArrayList<mPO> pos = response.body();
                    if (pos != null) {
                        for (mPO po : pos) {
                            data.InsertUpateAllPO(po);
                        }
                        callBackGetDataSync.resultReady(true);
                    } else {
                        callBackGetDataSync.resultReady(false);
                    }

                } else {
                    callBackGetDataSync.resultReady(false);
                    //Log.e("error", response.message() + "," + response.code());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<mPO>> call, Throwable t) {
                //Log.e("REST error ", t.getMessage() + "xx" + t.toString());
                callBackGetDataSync.resultReady(false);
            }


        });
    }

    ArrayList<mPO> polist=new ArrayList<>();
    public void getAllPOUpdateFromServer(final String salesId, String dateFrom, String dateTo,int custId) {
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
        params.put("custId",String.valueOf(custId));

        Call<ArrayList<mPO>> userlist = service.cekPoUpdate(params);
        userlist.enqueue(new Callback<ArrayList<mPO>>() {
            @Override
            public void onResponse(Call<ArrayList<mPO>> call, Response<ArrayList<mPO>> response) {
                //Log.e("isi respion cek update", response.code()+" isi code");
                if (response.isSuccessful()) {
                    ArrayList<mPO> pos = response.body();
                    polist=pos;
                    if (pos != null) {
                        for (mPO po : pos) {
                            data.InsertUpateAllPO(po);
                        }
                       // sendSurveyToServer(salesId,pos);
                        callBackGetDataAllPo.resultReady(pos,true);
                    } else {
                        callBackGetDataAllPo.resultReady(polist,false);
                    }

                } else {
                    callBackGetDataAllPo.resultReady(polist,false);
                    Log.e("error", response.message() + "," + response.code());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<mPO>> call, Throwable t) {
                Log.e("REST error ", t.getMessage() + "xx" + t.toString());
                callBackGetDataAllPo.resultReady(polist,false);
            }


        });
    }

    public void UpdateAllPOStatusInServer(String salesId, ArrayList<mPO> callplan) {
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
        for (mPO cust : callplan) {
            rStringList c = new rStringList();
            c.setId(cust.getPoId());
            listcust.add(c);
        }

        rPoPost parameters = new rPoPost();
        parameters.setSalesId(salesId);
        parameters.setPolist(listcust);


        Call<ArrayList<PostResult>> userlist = service.setUpdateAllPoStatus(parameters);
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

    public ArrayList<mPO> getAllPODraft() {
        return data.getAllPODraft();
    }

    public  ArrayList<mPoLine> GetAllPOLineByPoId(String PoIdReff){
        return  data.getPOLineByPoId(PoIdReff,false);
    }

    public ArrayList<mPO> getAllPOPending() {
        return data.getAllPOPending();
    }

    public ArrayList<mPO> getAllPOBetween(String from,String To,int custId) {
        //nanti ditambahkan filter untuk periode tampil
        return data.getAllPOBetween(from,To,custId);
    }

    public ArrayList<mPO> getAllPoPPActive(String salesId) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat smp1 = new SimpleDateFormat("yyyy-MM-dd");
        String tgl = smp1.format(calendar.getTime());
        return data.getAllPOPPActive(salesId, tgl);
    }
}
