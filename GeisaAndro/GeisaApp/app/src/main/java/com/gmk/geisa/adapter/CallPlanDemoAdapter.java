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
import com.gmk.geisa.activities.report.ReportComplainDetailActivity;
import com.gmk.geisa.controller.CallPlanController;
import com.gmk.geisa.helper.ItemTouchHelperViewHolder;
import com.gmk.geisa.model.mComplain;
import com.gmk.geisa.model.mDemo;
import com.gmk.geisa.model.mSession;

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
public class CallPlanDemoAdapter extends RecyclerView.Adapter<CallPlanDemoAdapter.ItemViewHolder> {

    private List<mDemo> mPO = new ArrayList<>();

    private final List<mDemo> filteredUserList;
    private UserFilter userFilter;
    private Context mContext;
    private mSession sessionUser;
    NumberFormat nf = NumberFormat.getInstance(Locale.GERMAN);
    private CallPlanController callPlanController;

    public CallPlanDemoAdapter(List<mDemo> po, Context context, mSession session) {
        //mPO = po;
        mContext = context;
        callPlanController = CallPlanController.getInstance(context);
        filteredUserList = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        for (mDemo cu : po) {
            mPO.add(cu);
            filteredUserList.add(cu);
        }
        sessionUser = session;
        userFilter = new UserFilter(this, po);
    }



    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rowView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_demo, parent, false);
        ItemViewHolder viewHolder = new ItemViewHolder(rowView);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(final ItemViewHolder holder, int position) {

        final mDemo selectedCustomer = filteredUserList.get(position);
        holder.idsample.setText(selectedCustomer.getDemoId());
        holder.namacustomer.setText(selectedCustomer.getCustName().concat("/").concat(selectedCustomer.getCustAlias()));
        holder.alamatcustomer.setText(selectedCustomer.getCustAddress());
        holder.complain.setText(selectedCustomer.getDemoTitle());
        holder.requestdate.setText(selectedCustomer.getCreatedDate());
        String detail="";
        detail+="Peserta: "+selectedCustomer.getDemoPeserta()+"\n";
        detail+=selectedCustomer.getDemoDescription();
        holder.itemrequest.setText(detail);
        holder.lastupdate.setText(selectedCustomer.getModifiedDate());
        holder.status.setText(selectedCustomer.getDemoStatusName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               /* holder.itemView.setBackgroundColor(Color.GREEN);
                Intent inten = new Intent(mContext, ReportComplainDetailActivity.class);
                inten.putExtra(ReportComplainDetailActivity.sessionComplain, selectedCustomer);
                inten.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.getApplicationContext().startActivity(inten);*/

            }
        });


    }


    @Override
    public int getItemCount() {
        return filteredUserList.size();
    }

    public mDemo getItem(int position) {
        return filteredUserList.get(position);
    }

    public List<mDemo> getAllItem() {
        return mPO;
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder implements
            ItemTouchHelperViewHolder {
        public final TextView status, lastupdate, itemrequest, requestdate, complain, namacustomer,alamatcustomer, idsample;
        public final ImageView handle;

        public ItemViewHolder(View itemView) {
            super(itemView);
            idsample = (TextView) itemView.findViewById(R.id.idsample);
            namacustomer = (TextView) itemView.findViewById(R.id.namacustomer);
            complain = (TextView) itemView.findViewById(R.id.complain);
            requestdate = (TextView) itemView.findViewById(R.id.requestdate);
            itemrequest = (TextView) itemView.findViewById(R.id.itemrequest);
            lastupdate = (TextView) itemView.findViewById(R.id.lastupdate);
            alamatcustomer=(TextView) itemView.findViewById(R.id.alamatcustomer);
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

        private final CallPlanDemoAdapter adapter;

        private final List<mDemo> originalList;

        private final List<mDemo> filteredList;

        private UserFilter(CallPlanDemoAdapter adapter, List<mDemo> originalList) {
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

                for (final mDemo user : originalList) {

                    if (user.getDemoTitle().toLowerCase().contains(filterPattern) || user.getDemoDescription().toLowerCase().contains(filterPattern) ) {
                        this.filteredList.add(user);
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
            adapter.filteredUserList.addAll((ArrayList<mDemo>) results.values);
            adapter.notifyDataSetChanged();
        }
    }


}
