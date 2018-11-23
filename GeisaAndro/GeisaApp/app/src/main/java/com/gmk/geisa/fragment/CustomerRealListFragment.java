package com.gmk.geisa.fragment;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import com.gmk.geisa.R;
import com.gmk.geisa.adapter.CustomerAdapter;
import com.gmk.geisa.controller.CustomerController;
import com.gmk.geisa.controller.SessionController;
import com.gmk.geisa.helper.Constants;
import com.gmk.geisa.helper.PermissionUtils;
import com.gmk.geisa.model.mCustomer;
import com.gmk.geisa.model.mSession;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class CustomerRealListFragment extends Fragment {
    private CustomerController customerController;
    private CustomerAdapter customerAdapter;
    private SessionController sessionController;
    private RecyclerView lvItems;
    private LinearLayoutManager linearLayoutManager;
    private SearchView findTXTEdit;
    private ProgressDialog pDialog;

    private int PAGE_SIZE = 12;
    private boolean isLastPage = false;
    private boolean isLoading = false;
    ArrayList<mCustomer> mCustomer;
    private mSession session;

    //comunicate between Frame and Activity

    public CustomerRealListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        customerController = CustomerController.getInstance(getActivity());
        customerController.setCallBackGetUpdateCustomer(callbackUpdateCustomer);
        sessionController = SessionController.getInstance(getActivity());
        session = sessionController.getSession("login", 1);

    }
    @Override
    public void onResume() {
        super.onResume();
        getDataFromLocal();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_customer_list,
                container, false);

        lvItems = (RecyclerView) view.findViewById(R.id.lvItems);
        findTXTEdit = (SearchView) view.findViewById(R.id.findTXTEdit);

        //SEARCH
        findTXTEdit.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                //FILTER AS YOU TYPE
                if (customerAdapter != null) {
                    customerAdapter.filter(query, mCustomer);
                }
                return false;
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        linearLayoutManager = new LinearLayoutManager(getActivity());


        customerAdapter = new CustomerAdapter(this.getActivity());
        //getting data
        LoadData();

        // initListener();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }


    CustomerController.CallBackGetUpdateCustomer callbackUpdateCustomer = new CustomerController.CallBackGetUpdateCustomer() {
        @Override
        public void resultReady(ArrayList<mCustomer> customers, boolean hasil) {
            if (hasil || (null != customers && customers.size() > 0)) {
                getDataFromLocal();
            } else {
                Toast.makeText(getActivity(), "Failed To Get Data From Server ... Try Again Later", Toast.LENGTH_SHORT).show();
                getDataFromLocal();
            }
            if (pDialog != null)
                pDialog.dismiss();

        }

    };

    private boolean checkUrl() {
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            InetAddress.getByName(Constants.APP_URL_SERVER).isReachable(20000); //'2018-7-25' ubah timeout 2 detik menjadi 20 detik
            return true;
        } catch (Exception e) {
            Log.e("isi Error", e.toString());
            return false;
        }
    }

    //nanti dipindah di customer activity
    private void LoadData() {
        if (getNetworkAvailability()) {
            if (pDialog == null)
                pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Getting Data From Server ...\n Please Wait...");
            pDialog.show();
            if (checkUrl()) {
                customerController.checkUpdateAllCustomerFromServer(String.valueOf(session.getId()));
            } else {
                Toast.makeText(getActivity(), "Failed To Connect to Server ... Try Again Later", Toast.LENGTH_SHORT).show();
                getDataFromLocal();
                if (pDialog != null)
                    pDialog.dismiss();
            }
        } else {
            getDataFromLocal();
        }
    }

    private void getDataFromLocal() {
        mCustomer = customerController.getAllCustomer(session.getId(), "1");
        if (null != mCustomer && mCustomer.size() > 0) {
           // Log.e("ada data customer","ada customer"+mCustomer.size());
            lvItems.setLayoutManager(linearLayoutManager);
            lvItems.setAdapter(customerAdapter);
            lvItems.addOnScrollListener(recyclerViewOnScrollListener);

            List<mCustomer> memberList = new ArrayList<>();
            int end = PAGE_SIZE;
            if (end >= mCustomer.size()) end = mCustomer.size() - 1;
             //Log.e("isi data init",PAGE_SIZE+","+end+","+mCustomer.size());

            if (end <= mCustomer.size() - 1) {
               /* for (int i = 0; i <= end; i++) {
                    memberList.add(mCustomer.get(i));
                }
                customerAdapter.addAll(memberList);*/
                customerAdapter.swap(mCustomer);
                if (end >= mCustomer.size() - 1) {
                    customerAdapter.setLoading(false);
                }
            } else {
                customerAdapter.setLoading(false);
            }
        } else {
           // Toast.makeText(getActivity(), "You Didn't Have Any Customer, Please Contact Your Supervisor", Toast.LENGTH_LONG).show();
            customerAdapter.setLoading(false);
        }

    }

    public boolean getNetworkAvailability() {
        return PermissionUtils.isNetworkAvalilable(getActivity().getApplicationContext());
    }

    private void loadData() {
        isLoading = false;
        List<mCustomer> memberList = new ArrayList<>();
        int index = customerAdapter.getItemCount() - 1;
        int end = index + PAGE_SIZE;
        if (end >= mCustomer.size()) end = mCustomer.size() - 1;
       // Log.e("isi data load", index + "," + end + "," + mCustomer.size());
        if (end <= mCustomer.size() - 1) {

            /*for (int i = index; i <= end; i++) {

                memberList.add(mCustomer.get(i));

            }
            customerAdapter.addAll(memberList);*/
            customerAdapter.swap(mCustomer);
            if (end >= mCustomer.size() - 1) {
                customerAdapter.setLoading(false);
            }
        } else {
            customerAdapter.setLoading(false);
        }
    }

    private RecyclerView.OnScrollListener recyclerViewOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            int visibleItemCount = linearLayoutManager.getChildCount();
            int totalItemCount = linearLayoutManager.getItemCount();
            int firstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition();
            //Log.e("isi data",visibleItemCount+","+totalItemCount+","+firstVisibleItemPosition+","+isLoading+","+isLastPage);
            if (!isLoading && !isLastPage) {
                if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                        && firstVisibleItemPosition >= 0
                        && totalItemCount >= PAGE_SIZE) {
                    //Log.e("isi data lagi",visibleItemCount+","+totalItemCount+","+firstVisibleItemPosition);
                    isLoading = true;
                    loadData();

                }
            }
        }
    };


}
