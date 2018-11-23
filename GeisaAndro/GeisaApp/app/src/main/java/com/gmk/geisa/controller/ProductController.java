package com.gmk.geisa.controller;

import android.content.Context;
import android.util.Log;

import com.gmk.geisa.databases.mDB;
import com.gmk.geisa.helper.Enkrip;
import com.gmk.geisa.model.mChannel;
import com.gmk.geisa.model.mProduct;
import com.gmk.geisa.model.mProductPriceDiskon;
import com.gmk.geisa.model.mPromo;
import com.gmk.geisa.model.mStock;
import com.gmk.geisa.model.mStockBranch;
import com.gmk.geisa.model.mUnit;
import com.gmk.geisa.service.APIService;
import com.gmk.geisa.service.RetroClient;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by kenjinsan on 5/10/17.
 */

public class ProductController {
//belum dibenerin
    private static mDB data;
    private APIService service;
    private static ProductController instance = null;
    static Enkrip enkrip;
    static Context ctx;
    static boolean primariON = true;
    private ArrayList<mProduct> list = null;
    private ArrayList<mProductPriceDiskon> list1 = null;
    private  ArrayList<mPromo> listPromo=null;

    public static ProductController getInstance(Context context) {
        if (instance == null) {
            instance = new ProductController();
        }
        data = mDB.getInstance(context);
        ctx = context;
        enkrip = new Enkrip();
        return instance;
    }

    //calback get update customer
    private CallBackGetData callBackGetData;

    public void setCallBackGetData(CallBackGetData callBackGetData) {
        this.callBackGetData = callBackGetData;
    }

    public  interface CallBackGetData {
        public  void resultReady(ArrayList<mProduct> customers, boolean hasil);
    }

    private CallBackGetStockProduk callBackGetStockProduk;

    public void setCallBackGetStockProduk(CallBackGetStockProduk callBackGetStockProduk) {
        this.callBackGetStockProduk = callBackGetStockProduk;
    }

    public  interface CallBackGetStockProduk {
        public  void resultReady(ArrayList<mStock> stock, boolean hasil);
    }
    private CallBackGetStock callBackGetStock;

    public void setCallBackGetStock(CallBackGetStock callBackGetStock) {
        this.callBackGetStock = callBackGetStock;
    }

    public  interface CallBackGetStock {
        public  void resultReady(ArrayList<mStockBranch> stock, boolean hasil);
    }

    private CallBackGetDataPromo callBackGetDataPromo;

    public void setCallBackGetDataPromo(CallBackGetDataPromo callBackGetDataPromo) {
        this.callBackGetDataPromo = callBackGetDataPromo;
    }

    public  interface CallBackGetDataPromo {
        public  void resultReady(ArrayList<mPromo> customers, boolean hasil);
    }

    private CallBackGetDataPrice callBackGetDataPrice;

    public void setCallBackGetDataPrice(CallBackGetDataPrice callBackGetDataPrice) {
        this.callBackGetDataPrice = callBackGetDataPrice;
    }

    public  interface CallBackGetDataPrice {
        public  void resultReady(ArrayList<mProductPriceDiskon> customers, boolean hasil);
    }


    /*end callback*/



    public void getAllProductFromServer(final String salesId) {
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
        //params.put("pass", pass);

        Call<ArrayList<mProduct>> userlist = service.getDataProduct(params);
        userlist.enqueue(new Callback<ArrayList<mProduct>>() {
            @Override
            public void onResponse(Call<ArrayList<mProduct>> call, Response<ArrayList<mProduct>> response) {
                //Log.e("isi respion cek update", response.code()+" isi code");
                if (response.isSuccessful()) {
                    //Log.e("isi respion", response.body().toString());
                    ArrayList<mProduct> result = response.body();
                    if (result != null) {
                        // Log.e("ada data", result.size()+" ada");
                        list =result;
                        data.InsertUpateAllProduct(result);

                    }
                    callBackGetData.resultReady(list,true);
                }else{
                    Log.e("error",response.message()+","+response.code());
                    callBackGetData.resultReady(list,false);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<mProduct>> call, Throwable t) {
                Log.e("REST error ", t.getMessage() +"xx"+t.toString());
                callBackGetData.resultReady(list,false);
            }


        });
    }

    public void getAllProductPriceDiskonFromServer(final String salesId) {
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
        //params.put("pass", pass);

        Call<ArrayList<mProductPriceDiskon>> userlist = service.getDataProductPriceDiskon(params);
        userlist.enqueue(new Callback<ArrayList<mProductPriceDiskon>>() {
            @Override
            public void onResponse(Call<ArrayList<mProductPriceDiskon>> call, Response<ArrayList<mProductPriceDiskon>> response) {
                //Log.e("isi respion cek update", response.code()+" isi code");
                if (response.isSuccessful()) {
                    //Log.e("isi respion", response.body().toString());
                    ArrayList<mProductPriceDiskon> result = response.body();
                    if (result != null) {
                        // Log.e("ada data", result.size()+" ada");
                        list1 =result;
                        data.InsertUpateAllProductPriceDiskon(result);

                    }
                    callBackGetDataPrice.resultReady(list1,true);
                }else{
                    Log.e("error",response.message()+","+response.code());
                    callBackGetDataPrice.resultReady(list1,false);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<mProductPriceDiskon>> call, Throwable t) {
                Log.e("REST error ", t.getMessage() +"xx"+t.toString());
                callBackGetDataPrice.resultReady(list1,false);
            }


        });
    }

    ArrayList<mStockBranch> liststock=new ArrayList<>();
    public void getAllProductStockFromServer(final String branchId) {
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
        Calendar cal=Calendar.getInstance();

        Map<String, String> params = new HashMap<String, String>();
        params.put("BranchId", branchId);
        params.put("Bulan",String.valueOf(cal.get(Calendar.MONTH)+1));
        params.put("Tahun", String.valueOf(cal.get(Calendar.YEAR)));

        Call<ArrayList<mStockBranch>> userlist = service.getDataStock(params);
        userlist.enqueue(new Callback<ArrayList<mStockBranch>>() {
            @Override
            public void onResponse(Call<ArrayList<mStockBranch>> call, Response<ArrayList<mStockBranch>> response) {
                //Log.e("isi respion cek update", response.code()+" isi code");
                if (response.isSuccessful()) {
                    //Log.e("isi respion", response.body().toString());
                    ArrayList<mStockBranch> result = response.body();
                    if (result != null) {
                        // Log.e("ada data", result.size()+" ada");
                        liststock =result;
                    }
                    callBackGetStock.resultReady(liststock,true);
                }else{
                    Log.e("error",response.message()+","+response.code());
                    callBackGetStock.resultReady(liststock,false);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<mStockBranch>> call, Throwable t) {
                Log.e("REST error ", t.getMessage() +"xx"+t.toString());
                callBackGetStock.resultReady(liststock,false);
            }


        });
    }

    ArrayList<mStock> liststockProduk=new ArrayList<>();
    public void getAllProductStockProductFromServer(final String branchId,final String produkId) {
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
        Calendar cal=Calendar.getInstance();

        Map<String, String> params = new HashMap<String, String>();
        params.put("BranchId", branchId);
        params.put("ProdukId", produkId);
        params.put("Bulan",String.valueOf(cal.get(Calendar.MONTH)+1));
        params.put("Tahun", String.valueOf(cal.get(Calendar.YEAR)));

        Call<ArrayList<mStock>> userlist = service.getDataStockProduct(params);
        userlist.enqueue(new Callback<ArrayList<mStock>>() {
            @Override
            public void onResponse(Call<ArrayList<mStock>> call, Response<ArrayList<mStock>> response) {
                //Log.e("isi respion cek update", response.code()+" isi code");
                if (response.isSuccessful()) {
                    //Log.e("isi respion", response.body().toString());
                    ArrayList<mStock> result = response.body();
                    if (result != null) {
                        // Log.e("ada data", result.size()+" ada");
                        liststockProduk =result;
                    }
                    callBackGetStockProduk.resultReady(liststockProduk,true);
                }else{
                    Log.e("error",response.message()+","+response.code());
                    callBackGetStockProduk.resultReady(liststockProduk,false);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<mStock>> call, Throwable t) {
                Log.e("REST error ", t.getMessage() +"xx"+t.toString());
                callBackGetStockProduk.resultReady(liststockProduk,false);
            }


        });
    }

    public  ArrayList<mProduct> getAllProduct(String statusId){
        return  data.getAllProduct(statusId);
    }

    public ArrayList<mProductPriceDiskon> getAllProductPriceDiskon(String distGroupId){
        return  data.getProdukPriceDiskonById(distGroupId);
    }

    public ArrayList<mProductPriceDiskon> getAllProductPriceDiskon(String distGroupId,String productId){
        return  data.getProdukPriceDiskonById(distGroupId,productId);
    }

    //promo
    public void getAllPromoFromServer(final String salesId) {
        String cypertext;
        try {
            cypertext = enkrip.bytesToHex(enkrip.encrypt("has password" + ":" + "has password"));
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
        Map<String, String> params = new HashMap<String, String>();
        params.put("salesId", salesId);
        //params.put("pass", pass);

        Call<ArrayList<mPromo>> userlist = service.getDataPromo(params);
        userlist.enqueue(new Callback<ArrayList<mPromo>>() {
            @Override
            public void onResponse(Call<ArrayList<mPromo>> call, Response<ArrayList<mPromo>> response) {
                Log.e("isi respion cek update", response.code()+" isi code");
                if (response.isSuccessful()) {
                    Log.e("isi respion", response.body().toString());
                    ArrayList<mPromo> result = response.body();
                    if (result != null) {
                        Log.e("ada data", result.size()+" ada");
                        listPromo =result;
                        data.InsertUpateAllPromo(result);

                    }
                    callBackGetDataPromo.resultReady(listPromo,true);
                }else{
                    Log.e("error",response.message()+","+response.code());
                    callBackGetDataPromo.resultReady(listPromo,false);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<mPromo>> call, Throwable t) {
                Log.e("REST error ", t.getMessage() +"xx"+t.toString());
                callBackGetDataPromo.resultReady(listPromo,false);
            }


        });
    }

    public  ArrayList<mPromo> getAllPromo(){
        return  data.getAllPromo();
    }

}
