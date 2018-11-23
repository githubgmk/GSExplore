package com.gmk.geisa.fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gmk.geisa.R;
import com.gmk.geisa.model.mCustomer;
import com.gmk.geisa.model.mCustomerAndDistBranch;

/**
 * Created by kenjinsan on 5/9/17.
 */

public class CustomerDistributorFragment extends Fragment {
    TextView cstGroupPrice, cstGroupDiskon, cstDistributorName, cstDistributorCustomerCode, cstDistributorArea, cstDistributorPic, cstDistributorEmail,
            cstDistributorTelp, cstDistributorAddress;
    static mCustomer customer;
    static mCustomerAndDistBranch customerAndDistBranch;
    private static final String ARG_SECTION_NUMBER = "section_number";
    private int sectionNumber;

    public CustomerDistributorFragment() {
        setRetainInstance(true);
    }

    public static CustomerDistributorFragment newInstance(mCustomerAndDistBranch distBranch) {
        CustomerDistributorFragment customerDistributorFragment = new CustomerDistributorFragment();
        //customerAndDistBranch = distBranch;
        Bundle args = new Bundle();
        args.putSerializable(ARG_SECTION_NUMBER, distBranch);
        customerDistributorFragment.setArguments(args);

        return customerDistributorFragment;
    }
    public static CustomerDistributorFragment newInstance(int sectionNumber) {
        CustomerDistributorFragment customerDistributorFragment = new CustomerDistributorFragment();
        //customerAndDistBranch = distBranch;
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        customerDistributorFragment.setArguments(args);
        return customerDistributorFragment;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_distributor_info, container, false);
        cstGroupPrice = (TextView) view.findViewById(R.id.cstGroupPrice);
        cstGroupDiskon = (TextView) view.findViewById(R.id.cstGroupDiskon);
        cstDistributorName = (TextView) view.findViewById(R.id.cstDistributorName);
        cstDistributorCustomerCode = (TextView) view.findViewById(R.id.cstDistributorCustomerCode);
        cstDistributorArea = (TextView) view.findViewById(R.id.cstDistributorArea);
        cstDistributorPic = (TextView) view.findViewById(R.id.cstDistributorPic);
        cstDistributorEmail = (TextView) view.findViewById(R.id.cstDistributorEmail);
        cstDistributorTelp = (TextView) view.findViewById(R.id.cstDistributorTelp);
        cstDistributorAddress = (TextView) view.findViewById(R.id.cstDistributorAddress);
        if (null != getArguments().getSerializable(ARG_SECTION_NUMBER))
            customerAndDistBranch = (mCustomerAndDistBranch) getArguments().getSerializable(ARG_SECTION_NUMBER);
       /* sectionNumber = getArguments().getInt(ARG_SECTION_NUMBER);
        cstDistributorName.setText("tab "+sectionNumber);*/
        //set distributor and price
        if (null != customerAndDistBranch) {
           // Log.e("isi branch",customerAndDistBranch.getDistBranchName());
            cstGroupPrice.setText(customerAndDistBranch.getPriceGroupId() + " - " + customerAndDistBranch.getPriceGroupName());
            cstGroupDiskon.setText(customerAndDistBranch.getDiscGroupId() + " - " + customerAndDistBranch.getDiscGroupName());
            cstDistributorName.setText(customerAndDistBranch.getDistBranchName());
            cstDistributorCustomerCode.setText(customerAndDistBranch.getCustCode());
            cstDistributorArea.setText(customerAndDistBranch.getDistBranchAreaCode() + " - " + customerAndDistBranch.getDistBranchAreaName());
            cstDistributorPic.setText(customerAndDistBranch.getDistBranchPic());
            cstDistributorEmail.setText(customerAndDistBranch.getDistBranchEmail());
            cstDistributorTelp.setText(customerAndDistBranch.getDistBranchTelp());
            cstDistributorAddress.setText(customerAndDistBranch.getDistBranchAddress());
        }
        return view;
    }
}
