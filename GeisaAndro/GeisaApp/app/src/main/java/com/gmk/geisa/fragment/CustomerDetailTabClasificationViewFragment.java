package com.gmk.geisa.fragment;



import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gmk.geisa.R;
import com.gmk.geisa.model.mCustomerClasification;



/**
 * A simple {@link Fragment} subclass.
 */
public class CustomerDetailTabClasificationViewFragment extends Fragment {

    private mCustomerClasification clasification=new mCustomerClasification();

    TextView ChannelCode, ChannelWayName,Description,PeriodeStart, ChannelName, ChannelGradeCode, ChannelGradeName, ChannelGradeDescription,
            ChannelAppCode, ChannelAppName, ChannelStagingCode, ChannelStagingName, ChannelStagingDescription, ChannelStagingShareWalet,cstUpdateDate;


    public CustomerDetailTabClasificationViewFragment() {
        // Required empty public constructor
    }

    public static CustomerDetailTabClasificationViewFragment newInstance(mCustomerClasification clasification) {
        CustomerDetailTabClasificationViewFragment customerDetailTabClasificationViewFragment = new CustomerDetailTabClasificationViewFragment();
        Bundle args = new Bundle();
        args.putSerializable("clasif", clasification);
        customerDetailTabClasificationViewFragment.setArguments(args);
        return customerDetailTabClasificationViewFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            if (null != arguments.getSerializable("clasif"))
                clasification = (mCustomerClasification) arguments.getSerializable("clasif");
        }

    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_customer_segmentasi,
                container, false);

        ChannelCode = (TextView) view.findViewById(R.id.cstCode);
        ChannelWayName = (TextView) view.findViewById(R.id.cstLevel);
        ChannelName = (TextView) view.findViewById(R.id.cstChannel);
        PeriodeStart = (TextView) view.findViewById(R.id.cstArea);
        Description = (TextView) view.findViewById(R.id.cstZone);

        ChannelGradeCode = (TextView) view.findViewById(R.id.cstLocation);
        ChannelGradeName = (TextView) view.findViewById(R.id.cstPIC);
        ChannelGradeDescription = (TextView) view.findViewById(R.id.cstPICJabatan);

        ChannelAppCode = (TextView) view.findViewById(R.id.cstEmail);
        ChannelAppName = (TextView) view.findViewById(R.id.cstTelp);
        ChannelStagingCode = (TextView) view.findViewById(R.id.cstHP);

        ChannelStagingName = (TextView) view.findViewById(R.id.cstAlamat);
        ChannelStagingDescription = (TextView) view.findViewById(R.id.cstWebsite);
        ChannelStagingShareWalet = (TextView) view.findViewById(R.id.cstJoinDate);

        cstUpdateDate=(TextView) view.findViewById(R.id.cstUpdateDate);
        //Log.e("isi clasification",clasification.getChannelWayName()+"xx");
        //set value transaksi
        ChannelCode.setText(clasification.getChannelCode());
        ChannelWayName.setText(clasification.getChannelWayName());
        ChannelName.setText(clasification.getChannelName());
        PeriodeStart.setText(clasification.getPeriodeStart());
        Description.setText(clasification.getDescription());

        ChannelGradeCode.setText(clasification.getChannelGradeCode() );
        ChannelGradeName.setText(clasification.getChannelGradeName());

        ChannelGradeDescription.setText(clasification.getChannelGradeDescription());
        ChannelAppCode.setText(clasification.getChannelAppCode());
        ChannelAppName.setText(clasification.getChannelAppName());
        ChannelStagingCode.setText(clasification.getChannelStagingCode());
        ChannelStagingName.setText(clasification.getChannelStagingName());
        ChannelStagingDescription.setText(clasification.getChannelStagingDescription());
        ChannelStagingShareWalet.setText(clasification.getChannelStagingShareWalet());
        cstUpdateDate.setText(clasification.getCreatedDate());

        return view;
    }


}
