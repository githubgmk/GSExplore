package com.gmk.geisa.controller;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.gmk.geisa.databases.mDB;
import com.gmk.geisa.helper.Constants;
import com.gmk.geisa.helper.Enkrip;
import com.gmk.geisa.model.Post.rCustomerAdd;
import com.gmk.geisa.model.Post.rCustomerPost;
import com.gmk.geisa.model.Post.rCustomerlist;
import com.gmk.geisa.model.PostResult;
import com.gmk.geisa.model.mCustomer;
import com.gmk.geisa.model.mCustomerAndDistBranch;
import com.gmk.geisa.model.mCustomerClasification;
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
 * Created by kenjin on 2/8/2016.
 */
public class CustomerController {
    private static final String DL_URL = Constants.APP_URL + "mas_customer/";
    private static mDB data;
    private APIService service;
    private static CustomerController instance = null;
    static Enkrip enkrip;
    static Context ctx;
    static boolean primariON = true;
    private ArrayList<mCustomer> userList = new ArrayList<>();
    private ArrayList<mCustomer> userList1 = new ArrayList<>();


    public static CustomerController getInstance(Context context) {
        if (instance == null) {
            instance = new CustomerController();
        }
        // Log.e("primary on before",primariON +"xx");
        data = mDB.getInstance(context);
        ctx = context;
        enkrip = new Enkrip();
        //MyTask task = new MyTask();
        //task.execute();
        return instance;
    }

    /*start callback*/
    //callback customer
    private CallBackGetCustomer callBackGetCustomer;

    public void setCallBackGetCustomer(CallBackGetCustomer callback) {
        this.callBackGetCustomer = callback;
    }


    public interface CallBackGetCustomer {
        public void resultReady(ArrayList<mCustomer> customers, boolean hasil);
    }

    //callback Sync customer
    private CallBackSyncCustomer callBackSyncCustomer;

    public void setCallBackSyncCustomer(CallBackSyncCustomer callBackSyncCustomer) {
        this.callBackSyncCustomer = callBackSyncCustomer;
    }

    public interface CallBackSyncCustomer {
        public void resultReady(mCustomer customers, mCustomer oldcust);
    }

    //callback Sync customer clasification
    private CallBackSyncCustomerClasification callBackSyncCustomerClasification;

    public void setCallBackSyncCustomerClasification(CallBackSyncCustomerClasification callBackSyncCustomerClasification) {
        this.callBackSyncCustomerClasification = callBackSyncCustomerClasification;
    }

    public interface CallBackSyncCustomerClasification {
        public void resultReady(mCustomerClasification clasificationNew, mCustomerClasification clasificationOld);
    }
    //callback add customer Sync
    private CallBackAddCustomerSync callBackAddCustomerSync;

    public void setCallBackAddCustomerSync(CallBackAddCustomerSync callBackAddCustomerSync) {
        this.callBackAddCustomerSync = callBackAddCustomerSync;
    }

    public interface CallBackAddCustomerSync {
        public void resultReady(boolean result);
    }

    //callback add customer
    private CallBackAddCustomer callBackAddCustomer;

    public void setCallBackAddCustomer(CallBackAddCustomer callBackAddCustomer) {
        this.callBackAddCustomer = callBackAddCustomer;
    }

    public interface CallBackAddCustomer {
        public void resultReady(mCustomer customers, mCustomer oldcustomer);
    }

    //calback get update customer
    private CallBackGetUpdateCustomer callBackGetUpdateCustomer;

    public void setCallBackGetUpdateCustomer(CallBackGetUpdateCustomer callBackGetUpdateCustomer) {
        this.callBackGetUpdateCustomer = callBackGetUpdateCustomer;
    }

    public interface CallBackGetUpdateCustomer {
        public void resultReady(ArrayList<mCustomer> customers, boolean hasil);
    }

    //main
    private CallBackGetUpdateCustomerMain callBackGetUpdateCustomerMain;

    public void setCallBackGetUpdateCustomerMain(CallBackGetUpdateCustomerMain callBackGetUpdateCustomerMain) {
        this.callBackGetUpdateCustomerMain = callBackGetUpdateCustomerMain;
    }

    public interface CallBackGetUpdateCustomerMain {
        public void resultReady(ArrayList<mCustomer> customers, boolean hasil);
    }

    /*end callback*/


    public void GetAllCustomerFromServer(final String salesId) {
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

        Call<ArrayList<mCustomer>> userlist = service.getAllCustomer(params);
        userlist.enqueue(new Callback<ArrayList<mCustomer>>() {
            @Override
            public void onResponse(Call<ArrayList<mCustomer>> call, Response<ArrayList<mCustomer>> response) {
                //  Log.e("isi respion", response.code() + " isi code");
                if (response.isSuccessful()) {
                    // Log.e("isi respion", response.body().toString());
                    ArrayList<mCustomer> result = response.body();

                    if (result != null) {
                        // Log.e("ada data", result.size()+" ada");
                        userList = result;
                        if (data.delCustBySales(salesId) > -5)
                            data.InsertUpateAllCustomer(result);

                    }
                    callBackGetCustomer.resultReady(userList, true);
                } else {
                    callBackGetCustomer.resultReady(userList, false);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<mCustomer>> call, Throwable t) {
                Log.e("REST", "err" + t.getMessage() + "," + t.toString());
                callBackGetCustomer.resultReady(userList, false);
            }


        });
    }

    public void checkUpdateAllCustomerFromServer(final String salesId) {
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

        Call<ArrayList<mCustomer>> userlist = service.getUpdateAllCustomer(params);
        userlist.enqueue(new Callback<ArrayList<mCustomer>>() {
            @Override
            public void onResponse(Call<ArrayList<mCustomer>> call, Response<ArrayList<mCustomer>> response) {
                // Log.e("isi respion cek update", response.code() + " isi code");
                if (response.isSuccessful()) {
                    // Log.e("isi respion", response.body().toString());
                    ArrayList<mCustomer> result = response.body();

                    if (result != null) {
                        Log.e("ada data cust", result.size() + " ada");
                        userList = result;

                        if (data.InsertUpateAllCustomer(result)) {
                            // Log.e("updat Server data", result.size()+" ada");
                            if (callBackGetUpdateCustomer != null)
                                callBackGetUpdateCustomer.resultReady(userList, true);
                            if (result.size() > 0) {
                                UpdateAllCustomerStatusInServer(salesId, result);
                            }
                        } else {
                            if (callBackGetUpdateCustomer != null)
                                callBackGetUpdateCustomer.resultReady(userList, false);
                        }

                    } else {
                        Log.e("tidak ada", "tidak da data");
                        if (callBackGetUpdateCustomer != null)
                            callBackGetUpdateCustomer.resultReady(userList, true);
                    }

                } else {
                    Log.e("error", response.message() + "," + response.code());
                    if (callBackGetUpdateCustomer != null)
                        callBackGetUpdateCustomer.resultReady(userList, false);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<mCustomer>> call, Throwable t) {
                Log.e("REST error ", t.getMessage() + "xx" + t.toString());
                if (callBackGetUpdateCustomer != null)
                    callBackGetUpdateCustomer.resultReady(userList, false);
            }


        });
    }

    public void checkUpdateAllCustomerFromServerMain(final String salesId) {
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
        // ArrayList<mCustomer> userList = new ArrayList<mCustomer>();
        Call<ArrayList<mCustomer>> userlist = service.getUpdateAllCustomer(params);
        userlist.enqueue(new Callback<ArrayList<mCustomer>>() {
            @Override
            public void onResponse(Call<ArrayList<mCustomer>> call, Response<ArrayList<mCustomer>> response) {
                // Log.e("isi respion cek update", response.code() + " isi code");
                if (response.isSuccessful()) {
                    // Log.e("isi respion", response.body().toString());
                    ArrayList<mCustomer> result = response.body();

                    if (result != null) {
                        Log.e("ada data cust", result.size() + " ada");
                        userList1 = result;

                        if (data.InsertUpateAllCustomer(result)) {
                            // Log.e("updat Server data", result.size()+" ada");

                            callBackGetUpdateCustomerMain.resultReady(userList1, true);

                            if (result.size() > 0) {
                                UpdateAllCustomerStatusInServer(salesId, result);
                            }
                        } else {
                            callBackGetUpdateCustomerMain.resultReady(userList1, false);
                        }

                    } else {
                        Log.e("tidak ada", "tidak da data");
                        callBackGetUpdateCustomerMain.resultReady(userList1, true);
                    }

                } else {
                    Log.e("error", response.message() + "," + response.code());
                    callBackGetUpdateCustomerMain.resultReady(userList1, false);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<mCustomer>> call, Throwable t) {
                Log.e("REST error ", t.getMessage() + "xx" + t.toString());
                callBackGetUpdateCustomerMain.resultReady(userList1, false);
            }


        });
    }

    public void UpdateAllCustomerStatusInServer(String salesId, ArrayList<mCustomer> customers) {
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


        List<rCustomerlist> listcust = new ArrayList<>();
        for (mCustomer cust : customers) {
            rCustomerlist c = new rCustomerlist();
            c.setCustomerId(cust.getCustId());
            listcust.add(c);
        }

/*
        Map<String, String> params = new HashMap<String, String>();
        params.put("salesId", salesId);
        //params.put("customerId", paramsCutomer);*/


        rCustomerPost parameters = new rCustomerPost();
        parameters.setSalesId(salesId);
        parameters.setCustomerlist(listcust);


        Call<ArrayList<PostResult>> userlist = service.setUpdateAllCustomer(parameters);
        userlist.enqueue(new Callback<ArrayList<PostResult>>() {
            @Override
            public void onResponse(Call<ArrayList<PostResult>> call, Response<ArrayList<PostResult>> response) {
                // Log.e("isi respion cek update", response.code()+" isi code");
                if (response.isSuccessful()) {
                    //Log.e("isi respion", response.body().toString());
                    ArrayList<PostResult> result = response.body();

                    if (result != null) {
                        //Log.e("ada data", result.size()+" ada");
                        Toast.makeText(ctx, "Done.. ", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<PostResult>> call, Throwable t) {
                Log.e("REST", "err " + t.toString());

            }


        });
    }

    public void addCustomer(final mCustomer customer, final String salesId, final int GroupCust) {
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

        rCustomerAdd parameters = new rCustomerAdd();
        parameters.setSalesId(salesId);
        parameters.setCustomer(customer);
        parameters.setGroupCust(GroupCust);// add from customer or from add customer

        Call<mCustomer> userlist = service.addNewCustomer(parameters);
        userlist.enqueue(new Callback<mCustomer>() {
            @Override
            public void onResponse(Call<mCustomer> call, Response<mCustomer> response) {
                // Log.e("isi respion cek update", response.code()+" isi code");
                if (response.isSuccessful()) {
                    //Log.e("isi respion", response.body().toString());
                    mCustomer result = response.body();

                    if (result != null) {
                        callBackAddCustomer.resultReady(result, customer);
                    } else {
                        callBackAddCustomer.resultReady(customer, customer);
                    }

                } else {
                    if (customer.getCustStatusId() == 3)
                        customer.setCustStatusId(0);
                    callBackAddCustomer.resultReady(customer, customer);
                }
            }

            @Override
            public void onFailure(Call<mCustomer> call, Throwable t) {
                Log.e("REST", "err " + t.toString());
                Toast.makeText(ctx, "Can't Connect to Server\nCustomer Will Send to Server Soon..", Toast.LENGTH_SHORT).show();
                customer.setStatusSend(1);
                if (customer.getCustStatusId() == 3)
                    customer.setCustStatusId(0);
                callBackAddCustomer.resultReady(customer, customer);
            }


        });
    }

    public void updateCustomerToServer(final mCustomer customer) {
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

        Call<mCustomer> userlist = service.updateCustomer(customer);
        userlist.enqueue(new Callback<mCustomer>() {
            @Override
            public void onResponse(Call<mCustomer> call, Response<mCustomer> response) {
                // Log.e("isi respion cek update", response.code()+" isi code");
                if (response.isSuccessful()) {
                    //Log.e("isi respion", response.body().toString());
                    mCustomer result = response.body();
                    if (result != null) {

                    }

                }
            }

            @Override
            public void onFailure(Call<mCustomer> call, Throwable t) {
                Log.e("REST", "err " + t.toString());
            }


        });
    }

    public void reSendCustomer(final ArrayList<mCustomer> cu, final String salesId) {
        final boolean[] hasil = {false};
        if (cu != null & cu.size() > 0) {
            for (final mCustomer customer : cu) {
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

                rCustomerAdd parameters = new rCustomerAdd();
                parameters.setSalesId(salesId);
                parameters.setCustomer(customer);
                parameters.setGroupCust(customer.getNewOutlet());// add from customer or from add customer

                Call<mCustomer> userlist = service.addNewCustomer(parameters);
                userlist.enqueue(new Callback<mCustomer>() {
                    @Override
                    public void onResponse(Call<mCustomer> call, Response<mCustomer> response) {
                        // Log.e("isi respion cek update", response.code()+" isi code");
                        if (response.isSuccessful()) {
                            //Log.e("isi respion", response.body().toString());
                            mCustomer result = response.body();
                            if (result != null) {
                                hasil[0] = addCustomerToLocal(result, customer);
                            }

                        }
                    }

                    @Override
                    public void onFailure(Call<mCustomer> call, Throwable t) {
                        Log.e("REST", "err " + t.toString());
                        hasil[0] = false;
                    }


                });
            }
            callBackAddCustomerSync.resultReady(hasil[0]);
        } else {
            callBackAddCustomerSync.resultReady(true);
        }


    }

    public void syncCustomer(mCustomer customer, String salesId) {
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

        Call<mCustomer> userlist = null;

        //select from database then update to server
        final mCustomer cust = getCustomerByCustIdAndSalesId(String.valueOf(customer.getCustId()), salesId);

        if (cust.getStatusSend() == 1 || cust.getCustStatusId() == 0) {
            if (cust.getCustStatusId() == 0)
                cust.setCustStatusId(3);
            rCustomerAdd parameters = new rCustomerAdd();
            parameters.setSalesId(salesId);
            parameters.setCustomer(cust);
            /// Log.e("cust cek",cust.getCustGroupId()+","+cust.getCustName());
            // add from customer or from add customer
            int group = 0;
            if (null != cust.getCustGroupId() && !cust.getCustGroupId().equals(String.valueOf(cust.getCustId())))
                group = 1;
            parameters.setGroupCust(group);
            userlist = service.addNewCustomer(parameters);
        } else {
            Map<String, String> parameters = new HashMap<String, String>();
            parameters.put("salesId", salesId);
            parameters.put("CustId", String.valueOf(cust.getCustId()));
            userlist = service.getCustomerById(parameters);
        }
        if (null != userlist) {
            userlist.enqueue(new Callback<mCustomer>() {
                @Override
                public void onResponse(Call<mCustomer> call, Response<mCustomer> response) {
                    // Log.e("isi respion cek update", response.code()+" isi code");
                    if (response.isSuccessful()) {
                        //Log.e("isi respion", response.body().toString());
                        mCustomer result = response.body();

                        if (result != null) {
                            callBackSyncCustomer.resultReady(result, cust);
                        } else {
                            callBackSyncCustomer.resultReady(cust, cust);
                        }

                    } else {
                        if (cust.getCustStatusId() == 3)
                            cust.setCustStatusId(0);
                        callBackSyncCustomer.resultReady(cust, cust);
                    }
                }

                @Override
                public void onFailure(Call<mCustomer> call, Throwable t) {
                    // Log.e("REST", "err " + t.toString());
                    Toast.makeText(ctx, "Can't Connect to Server\nCustomer Will Send to Server Soon..", Toast.LENGTH_SHORT).show();
                    cust.setStatusSend(1);
                    if (cust.getCustStatusId() == 3)
                        cust.setCustStatusId(0);
                    callBackSyncCustomer.resultReady(cust, cust);
                }


            });
        } else {
            callBackSyncCustomer.resultReady(cust, cust);
        }
    }


    public void syncCustomerClasification(final mCustomerClasification cust) {
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

        Map<String, String> parameters = new HashMap<String, String>();
        parameters.put("CustId", String.valueOf(cust.getCustId()));
        Call<mCustomerClasification> userlist = service.getCustomerClasificationById(parameters);
        if (null != userlist) {
            userlist.enqueue(new Callback<mCustomerClasification>() {
                @Override
                public void onResponse(Call<mCustomerClasification> call, Response<mCustomerClasification> response) {
                    // Log.e("isi respion cek update", response.code()+" isi code");
                    if (response.isSuccessful()) {
                        //Log.e("isi respion", response.body().toString());
                        mCustomerClasification result = response.body();

                        if (result != null) {
                            callBackSyncCustomerClasification.resultReady(result, cust);
                        } else {
                            callBackSyncCustomerClasification.resultReady(cust, cust);
                        }

                    } else {
                        callBackSyncCustomerClasification.resultReady(cust, cust);
                    }
                }

                @Override
                public void onFailure(Call<mCustomerClasification> call, Throwable t) {
                    // Log.e("REST", "err " + t.toString());
                    Toast.makeText(ctx, "Can't Connect to Server\n Error Connection..", Toast.LENGTH_SHORT).show();
                    callBackSyncCustomerClasification.resultReady(cust, cust);
                }


            });
        } else {
            callBackSyncCustomerClasification.resultReady(cust, cust);
        }
    }

    public boolean addCustomerToLocal(mCustomer customer, mCustomer oldcustomer) {
        return data.InsertUpateNewCustomer(customer, oldcustomer);
    }

    public ArrayList<mCustomer> getAllCustomer(int salesId, String status) {
        return data.getCustomerAllJoinBySales(String.valueOf(salesId), status);
    }
    public ArrayList<mCustomerAndDistBranch> getAllDistBranchDistinct(int salesId) {
        return data.getCustAndDistBranchDistinctBySalesId(String.valueOf(salesId));
    }
    public ArrayList<mCustomer> getAllCustomer(int salesId, String status, String orstatus) {
        return data.getCustomerAllJoinBySales(String.valueOf(salesId), status, orstatus);
    }

    public ArrayList<mCustomer> getAllCustomerExcept(int salesId, String status) {
        return data.getCustomerAllJoinBySalesExcept(String.valueOf(salesId), status);
    }

    public  ArrayList<mCustomer> getAllCustomerAktif(int salesId){
        return  data.getCustomerAllJoin(String.valueOf(salesId));
    }

    public ArrayList<mCustomer> getAllCustomerByCustGroup(long custId) {
        return data.getCustomerByCustomerGroup(String.valueOf(custId));
    }

    public mCustomer getCustomerByCustIdAndSalesId(String custId, String salesId) {
        return data.getCustomerJoinByCustIdAndSalesId(custId, salesId);
    }


    public void addCustomerDistributor(mCustomerAndDistBranch distBranch, String salesId, String custId) {
        final mCustomer customer;
        boolean hasil = data.addCustomerAndBranch(distBranch);
        if (hasil) {
            customer = getCustomerByCustIdAndSalesId(custId, salesId);


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
            int GroupCust = 0;
            if (null != customer.getCustGroupId() && !customer.getCustGroupId().equals(String.valueOf(customer.getCustId())))
                GroupCust = 1;
            rCustomerAdd parameters = new rCustomerAdd();
            parameters.setSalesId(salesId);
            parameters.setCustomer(customer);
            parameters.setGroupCust(GroupCust);// add from customer or from add customer

            Call<mCustomer> userlist = service.addNewCustomer(parameters);
            userlist.enqueue(new Callback<mCustomer>() {
                @Override
                public void onResponse(Call<mCustomer> call, Response<mCustomer> response) {
                    // Log.e("isi respion cek update", response.code()+" isi code");
                    if (response.isSuccessful()) {
                        //Log.e("isi respion", response.body().toString());
                        mCustomer result = response.body();

                        if (result != null) {
                            callBackAddCustomer.resultReady(result, customer);
                        } else {
                            callBackAddCustomer.resultReady(customer, customer);
                        }

                    } else {
                        if (customer.getCustStatusId() == 3)
                            customer.setCustStatusId(0);
                        callBackAddCustomer.resultReady(customer, customer);
                    }
                }

                @Override
                public void onFailure(Call<mCustomer> call, Throwable t) {
                    Log.e("REST", "err " + t.toString());
                    Toast.makeText(ctx, "Can't Connect to Server\nCustomer Will Send to Server Soon..", Toast.LENGTH_SHORT).show();
                    customer.setStatusSend(1);
                    if (customer.getCustStatusId() == 3)
                        customer.setCustStatusId(0);
                    callBackAddCustomer.resultReady(customer, customer);
                }


            });
        }
    }


    public ArrayList<mCustomer> getAllCustomerPending(String salesId) {
        return data.getCustomerAllJoinBySalesStatusSend(salesId, "1");
    }


    public mCustomer findById(int id) {
        return userList.get(id);
    }
}
