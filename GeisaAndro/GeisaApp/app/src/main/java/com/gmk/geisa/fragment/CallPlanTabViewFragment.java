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
import com.gmk.geisa.model.mCallPlan;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class CallPlanTabViewFragment extends Fragment {

    ArrayList<mCallPlan> plan = new ArrayList<>();
    RecyclerView recyclerView;
    TextView totalall, ttlplan, ttlunplan;
    int idtab;
    private CallPlanDeleteListAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    public static CallPlanTabViewFragment newInstant(ArrayList<mCallPlan> callPlen, int idtab) {
        CallPlanTabViewFragment customerDetailFragment = new CallPlanTabViewFragment();
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
        View view = inflater.inflate(R.layout.fragment_callplan_list, container, false);

        totalall = (TextView) view.findViewById(R.id.updateNilai);
        ttlplan = ((TextView) view.findViewById(R.id.updateNilai1));
        ttlunplan = (TextView) view.findViewById(R.id.updateNilai2);

        setupRecyclerView(view);

        return view;
    }

    private void setupRecyclerView(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.lvItems);
        recyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new CallPlanDeleteListAdapter(plan, getActivity(), totalall, ttlplan, ttlunplan);
        recyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(getActivity())
                .colorResId(R.color.colorPrimaryDark)
                .size(2)
                .build());
        recyclerView.setAdapter(mAdapter);
    }

    public ArrayList<mCallPlan> getItem() {
        return plan;
    }


}
