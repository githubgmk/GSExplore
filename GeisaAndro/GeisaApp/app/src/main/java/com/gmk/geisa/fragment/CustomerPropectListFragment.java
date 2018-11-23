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
public class CustomerPropectListFragment extends Fragment {
    private CustomerController customerControllers;
    private CustomerAdapter customerAdapters;
    private SessionController sessionController;
    private RecyclerView lvItemsP;
    private LinearLayoutManager linearLayoutManager;
    private SearchView findTXTEditP;
    private ProgressDialog pDialog;

    private int PAGE_SIZEP = 12;
    private boolean isLastPageP = false;
    private boolean isLoadingP = false;
    ArrayList<mCustomer> mCustomers;
    private mSession session;

    //comunicate between Frame and Activity
    public interface OnListItemSelectedListener {
        public void onItemSelected(mCustomer mCustomer);
    }

    public CustomerPropectListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        customerControllers = CustomerController.getInstance(getActivity());
        sessionController = SessionController.getInstance(getActivity());
        session = sessionController.getSession("login", 1);

    }

    @Override
    public void onResume() {
        super.onResume();
        // lvItemsP.setAdapter(null);
        // lvItemsP.setLayoutManager(null);
        //lvItemsP.setAdapter(customerAdapters);
        //lvItemsP.setLayoutManager(linearLayoutManager);
        // customerAdapters.clear();
        //customerAdapters.notifyDataSetChanged();
        getDataFromLocalP();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_customer_list,
                container, false);

        lvItemsP = (RecyclerView) view.findViewById(R.id.lvItems);
        findTXTEditP = (SearchView) view.findViewById(R.id.findTXTEdit);

        //SEARCH
        findTXTEditP.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                //FILTER AS YOU TYPE
                if (customerAdapters != null) {
                    customerAdapters.filter(query, mCustomers);
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


        customerAdapters = new CustomerAdapter(this.getActivity());
        //getting data
        // getDataFromLocalP();

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
                getDataFromLocalP();
            } else {
                // Toast.makeText(getActivity(), "Failed To Get Data From Server ... Try Again Later", Toast.LENGTH_SHORT).show();
                getDataFromLocalP();
            }
            if (pDialog != null)
                pDialog.dismiss();

        }

    };

    private boolean checkUrl() {
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            InetAddress.getByName(Constants.APP_URL_SERVER).isReachable(2000); //Replace with your name
            return true;
        } catch (Exception e) {
            Log.e("isi Error", e.toString());
            return false;
        }
    }

    private void LoadDataP() {
        getDataFromLocalP();
        if (pDialog != null)
            pDialog.dismiss();
        /*if (getNetworkAvailability()) {
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
        }*/
    }

    private void getDataFromLocalP() {
        mCustomers = customerControllers.getAllCustomerExcept(session.getId(), "1");
        // customerAdapters=new CustomerAdapter(this.getActivity());
        //Log.e("masuk cek data","cek data 5");
        //customerAdapters.clear();
        if (null != mCustomers && mCustomers.size() > 0) {
            //  Log.e("ada data cust prospect",mCustomers.size()+"total");

            lvItemsP.setLayoutManager(linearLayoutManager);
            lvItemsP.setAdapter(customerAdapters);
            lvItemsP.addOnScrollListener(recyclerViewOnScrollListenerP);

            List<mCustomer> memberList = new ArrayList<>();
            int end = PAGE_SIZEP;
            if (end >= mCustomers.size()) end = mCustomers.size() - 1;
            // Log.e("isi data init",PAGE_SIZE+","+end+","+mCustomer.size());

            if (end <= mCustomers.size() - 1) {

                /*for (int i = 0; i <= end; i++) {
                    memberList.add(mCustomers.get(i));
                }
                 customerAdapters.addAll(memberList);
                */
                customerAdapters.swap(mCustomers);

                if (end >= mCustomers.size() - 1) {
                    customerAdapters.setLoading(false);
                }
            } else {
                customerAdapters.setLoading(false);
            }
        } else {
            //Toast.makeText(getActivity(), "You Didn't Have Any Customer, Please Contact Your Supervisor", Toast.LENGTH_LONG).show();
            customerAdapters.setLoading(false);
        }
    }

    public boolean getNetworkAvailability() {
        return PermissionUtils.isNetworkAvalilable(getActivity().getApplicationContext());
    }

    private void loadDataP() {
        isLoadingP = false;
        List<mCustomer> memberList = new ArrayList<>();
        int index = customerAdapters.getItemCount() - 1;
        int end = index + PAGE_SIZEP;
        if (end >= mCustomers.size()) end = mCustomers.size() - 1;
        Log.e("isi data load", index + "," + end + "," + mCustomers.size());
        if (end <= mCustomers.size() - 1) {

            /*for (int i = index; i <= end; i++) {

                memberList.add(mCustomers.get(i));

            }
            customerAdapters.addAll(memberList);*/
            customerAdapters.swap(mCustomers);
            if (end >= mCustomers.size() - 1) {
                customerAdapters.setLoading(false);
            }
        } else {
            customerAdapters.setLoading(false);
        }
    }

    private RecyclerView.OnScrollListener recyclerViewOnScrollListenerP = new RecyclerView.OnScrollListener() {
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
            if (!isLoadingP && !isLastPageP) {
                if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                        && firstVisibleItemPosition >= 0
                        && totalItemCount >= PAGE_SIZEP) {
                    //Log.e("isi data lagi",visibleItemCount+","+totalItemCount+","+firstVisibleItemPosition);
                    isLoadingP = true;
                    loadDataP();

                }
            }
        }
    };


}
