package com.gmk.geisa.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gmk.geisa.R;
import com.gmk.geisa.adapter.CallPlanDeleteListAdapter;
import com.gmk.geisa.adapter.CallPlanDraftDeleteListAdapter;
import com.gmk.geisa.model.mCallPlan;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class CallPlanDraftTabViewFragment extends Fragment {

    ArrayList<mCallPlan> plan=new ArrayList<>();
    RecyclerView recyclerView;
    TextView totalCust;
    int idtab;
    private CallPlanDraftDeleteListAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    public  static CallPlanDraftTabViewFragment newInstant(ArrayList<mCallPlan> callPlen, int idtab){
        CallPlanDraftTabViewFragment customerDetailFragment = new CallPlanDraftTabViewFragment();
       // Log.e("isi call prod"," isinya"+callPlen.size());
        Bundle args = new Bundle();
        args.putSerializable("sesiPlan", callPlen);
        args.putSerializable("idtab", idtab);
        customerDetailFragment.setArguments(args);
        return customerDetailFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            if (null != arguments.getSerializable("sesiPlan"))
                plan = (ArrayList<mCallPlan>) arguments.getSerializable("sesiPlan");
            if (null != arguments.getSerializable("idtab"))
                idtab = (int) arguments.getSerializable("idtab");
        }
        //Log.e("isi call prod"," isinya"+prod.size());

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_callplan_list, container, false);
        //Log.e("isi cust"," sitab"+idtab);
        //PagerSlidingTabStrip tabLayout = (PagerSlidingTabStrip) getActivity().findViewById(R.id.tabs);
       // totalall= (TextView)tabLayout.getChildAt(0).findViewById(R.id.badge);

       // TextView tes =(TextView)  tabLayout.getChildAt(idtab).findViewById(R.id.badge);
        //totalall= ((TextView)getView().getRootView().findViewById(R.id.badge));
        totalCust = (TextView)  getActivity().findViewById(R.id.updateNilai);
        //totalall=(TextView) view.findViewWithTag(R.id.badge);
        //Log.e("isi cust"," si"+prod.size()+","+tes.getText());
        //totalall=tes;
        //
        //totalall.setText(String.valueOf(prod.size()));
        setupRecyclerView(view);

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
        mAdapter = new CallPlanDraftDeleteListAdapter(plan, getActivity(), totalCust);
        recyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(getActivity())
                .colorResId(R.color.colorPrimaryDark)
                .size(2)
                .build());
        recyclerView.setAdapter(mAdapter);
    }

    public  ArrayList<mCallPlan> getItem(){
        return  plan;
    }


}
