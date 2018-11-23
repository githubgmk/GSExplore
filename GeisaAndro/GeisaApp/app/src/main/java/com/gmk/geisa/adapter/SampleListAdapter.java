package com.gmk.geisa.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gmk.geisa.R;
import com.gmk.geisa.activities.callplan.visit.SampleAddActivity;
import com.gmk.geisa.activities.report.ReportSampleActivity;
import com.gmk.geisa.activities.report.ReportSampleDetailActivity;
import com.gmk.geisa.controller.CallPlanController;
import com.gmk.geisa.helper.ItemTouchHelperViewHolder;
import com.gmk.geisa.model.mSample;
import com.gmk.geisa.model.mSession;
import com.gmk.geisa.model.mTodoList;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Kenjin on 07/23/2017.
 */
public class SampleListAdapter extends RecyclerView.Adapter<SampleListAdapter.ItemViewHolder> {

    private List<mSample> mPO = new ArrayList<>();

    private final List<mSample> filteredUserList;
    private UserFilter userFilter;
    private Context mContext;
    private mSession sessionUser;
    NumberFormat nf = NumberFormat.getInstance(Locale.GERMAN);
    private CallPlanController callPlanController;
    boolean isReport=false;

    public SampleListAdapter(List<mSample> po, Context context, mSession session,boolean report) {
        //mPO = po;
        mContext = context;
        callPlanController = CallPlanController.getInstance(context);
        filteredUserList = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        isReport=report;
        for (mSample cu : po) {
            mPO.add(cu);
            filteredUserList.add(cu);
        }
        sessionUser = session;
        userFilter = new UserFilter(this, po);
    }

    private Calendar getTanggal(String dtStart) {
        Calendar tgldpt = Calendar.getInstance();
        boolean tglkosong = true;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        if (!TextUtils.isEmpty(dtStart) && !dtStart.equalsIgnoreCase("null")) {
            try {
                Date dates = format.parse(dtStart);
                // Log.e("isi start", date.toString());
                tglkosong = false;
                tgldpt.setTime(dates);
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                tglkosong = true;
                e.printStackTrace();
            }
        } else {
            tglkosong = true;
        }
        return tgldpt;
    }


    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rowView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_sample, parent, false);
        ItemViewHolder viewHolder = new ItemViewHolder(rowView);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(final ItemViewHolder holder, int position) {

        final mSample selectedCustomer = filteredUserList.get(position);
        holder.idsample.setText(selectedCustomer.getSampleId());
        if (selectedCustomer.getCustomer() != null)
            holder.namacustomer.setText(selectedCustomer.getCustomer().getCustName());
        holder.tujuansample.setText(selectedCustomer.getSampleFor());
        holder.requestdate.setText(selectedCustomer.getSampleDate());
        String itemrequest="";
        if(selectedCustomer.getProductOfRequest()!=null){
            for(mSample.mProductSample cu: selectedCustomer.getProductOfRequest()){
                itemrequest+=cu.getProductName().concat("[ ").concat(String.valueOf(cu.getQty())).concat(" ").concat(cu.getKemasan()).concat(" ]\n");
            }
        }
        holder.itemrequest.setText(itemrequest.replaceAll("([\\n\\r]+\\s*)*$", ""));
        holder.lastupdate.setText(selectedCustomer.getModifiedDate());
        holder.status.setText(selectedCustomer.getSampleStatus());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                holder.itemView.setBackgroundColor(Color.GREEN);
                if(isReport){
                    Intent inten = new Intent(mContext, ReportSampleDetailActivity.class);
                    inten.putExtra(ReportSampleDetailActivity.sessionSample, selectedCustomer);
                    inten.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.getApplicationContext().startActivity(inten);
                }else {
                    Intent inten = new Intent(mContext, SampleAddActivity.class);
                    inten.putExtra(SampleAddActivity.callPlanId, selectedCustomer.getCallPlanId());
                    inten.putExtra(SampleAddActivity.customerId, String.valueOf(selectedCustomer.getCustId()));
                    inten.putExtra(SampleAddActivity.sessionUser, sessionUser);
                    inten.putExtra(SampleAddActivity.sessionSample, selectedCustomer);
                    inten.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.getApplicationContext().startActivity(inten);
                }

            }
        });


    }


    @Override
    public int getItemCount() {
        return filteredUserList.size();
    }

    public mSample getItem(int position) {
        return filteredUserList.get(position);
    }

    public List<mSample> getAllItem() {
        return mPO;
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder implements
            ItemTouchHelperViewHolder {
        public final TextView status, lastupdate, itemrequest, requestdate, tujuansample, namacustomer, idsample;
        public final ImageView handle;

        public ItemViewHolder(View itemView) {
            super(itemView);
            idsample = (TextView) itemView.findViewById(R.id.idsample);
            namacustomer = (TextView) itemView.findViewById(R.id.namacustomer);
            tujuansample = (TextView) itemView.findViewById(R.id.tujuansample);
            requestdate = (TextView) itemView.findViewById(R.id.requestdate);
            itemrequest = (TextView) itemView.findViewById(R.id.itemrequest);
            lastupdate = (TextView) itemView.findViewById(R.id.lastupdate);
            status = (TextView) itemView.findViewById(R.id.status);
            handle = (ImageView) itemView.findViewById(R.id.handle);


        }

        @Override
        public void onItemSelected() {
            itemView.setBackgroundColor(Color.LTGRAY);
        }

        @Override
        public void onItemClear() {
            itemView.setBackgroundColor(0);
        }


    }


    public Filter setFilter() {
        return userFilter;
    }

    public static class UserFilter extends Filter {

        private final SampleListAdapter adapter;

        private final List<mSample> originalList;

        private final List<mSample> filteredList;

        private UserFilter(SampleListAdapter adapter, List<mSample> originalList) {
            super();
            this.adapter = adapter;
            this.originalList = new LinkedList<>(originalList);
            this.filteredList = new ArrayList<>();
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            this.filteredList.clear();
            final FilterResults results = new FilterResults();

            if (constraint.length() == 0) {
                this.filteredList.addAll(originalList);
            } else {
                final String filterPattern = constraint.toString().toLowerCase().trim();

                for (final mSample user : originalList) {
                    if (user.getSampleFor() != null) {
                        if (user.getSampleFor().toLowerCase().contains(filterPattern) || user.getSampleId().toLowerCase().contains(filterPattern) || user.getSampleDate().toLowerCase().contains(filterPattern)) {
                            this.filteredList.add(user);
                        }
                    }
                }
            }
            results.values = this.filteredList;
            results.count = this.filteredList.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            adapter.filteredUserList.clear();
            adapter.filteredUserList.addAll((ArrayList<mSample>) results.values);
            adapter.notifyDataSetChanged();
        }
    }


}
