package com.gmk.geisa.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;

import com.gmk.geisa.R;
import com.gmk.geisa.adapter.CustSegmentasiToDoListAdapter;
import com.gmk.geisa.adapter.ProductListAdapter;
import com.gmk.geisa.model.mCustomerClasification;
import com.gmk.geisa.model.mProduct;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class CustomerToDoTabViewFragment extends Fragment {

    ArrayList<mCustomerClasification.ChannelStagingApproach> plan=new ArrayList<>();
    RecyclerView recyclerView;
    private CustSegmentasiToDoListAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    public  static CustomerToDoTabViewFragment newInstant(ArrayList<mCustomerClasification.ChannelStagingApproach> aproach){
        CustomerToDoTabViewFragment customerDetailFragment = new CustomerToDoTabViewFragment();
       // Log.e("isi call prod"," isinya"+callPlen.size());
        Bundle args = new Bundle();
        args.putSerializable("sessiapproach", aproach);
        //args.putSerializable("idtab", idtab);
        customerDetailFragment.setArguments(args);
        return customerDetailFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            if (null != arguments.getSerializable("sessiapproach"))
                plan = (ArrayList<mCustomerClasification.ChannelStagingApproach>) arguments.getSerializable("sessiapproach");
           // if (null != arguments.getSerializable("idtab"))
             //   idtab = (int) arguments.getSerializable("idtab");
        }
        //Log.e("isi call prod"," isinya"+plan.size());

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_recyler_nosearch_list, container, false);
        //Log.e("isi cust"," sitab"+plan.size());
        setupRecyclerView(view);

        return view;
    }

    private void setupRecyclerView(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.lvItems);
        recyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new CustSegmentasiToDoListAdapter(plan, getActivity());
        recyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(getActivity())
                .colorResId(R.color.colorPrimaryDark)
                .size(2)
                .build());
        recyclerView.setAdapter(mAdapter);
    }

    public  ArrayList<mCustomerClasification.ChannelStagingApproach> getItem(){
        return  plan;
    }


}
