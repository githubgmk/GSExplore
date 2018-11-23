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
import com.gmk.geisa.adapter.CallPlanDraftDeleteListAdapter;
import com.gmk.geisa.adapter.ProductListAdapter;
import com.gmk.geisa.model.mCallPlan;
import com.gmk.geisa.model.mProduct;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProductTabViewFragment extends Fragment {

    ArrayList<mProduct> plan=new ArrayList<>();
    RecyclerView recyclerView;
    TextView totalCust;
    private SearchView findTXTEdit;
    int idtab;
    private ProductListAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    public  static ProductTabViewFragment newInstant(ArrayList<mProduct> products){
        ProductTabViewFragment customerDetailFragment = new ProductTabViewFragment();
       // Log.e("isi call prod"," isinya"+callPlen.size());
        Bundle args = new Bundle();
        args.putSerializable("sesiPlan", products);
        //args.putSerializable("idtab", idtab);
        customerDetailFragment.setArguments(args);
        return customerDetailFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            if (null != arguments.getSerializable("sesiPlan"))
                plan = (ArrayList<mProduct>) arguments.getSerializable("sesiPlan");
           // if (null != arguments.getSerializable("idtab"))
             //   idtab = (int) arguments.getSerializable("idtab");
        }
        Log.e("isi call prod"," isinya"+plan.size());

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_customer_list, container, false);
        Log.e("isi cust"," sitab"+plan.size());
        findTXTEdit = (SearchView) view.findViewById(R.id.findTXTEdit);
        setupRecyclerView(view);
        findTXTEdit.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mAdapter.setFilter().filter(newText);
                return false;
            }
        });
       /* TextView textView=(TextView) view.findViewById(R.id.text);
        if(null!=tes)
            textView.setText(tes);*/

        return view;
    }

    private void setupRecyclerView(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.lvItems);
        recyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new ProductListAdapter(plan, getActivity(), totalCust);
        recyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(getActivity())
                .colorResId(R.color.colorPrimaryDark)
                .size(2)
                .build());
        recyclerView.setAdapter(mAdapter);
    }

    public  ArrayList<mProduct> getItem(){
        return  plan;
    }


}
