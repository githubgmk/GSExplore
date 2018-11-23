package com.gmk.geisa.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gmk.geisa.R;
import com.gmk.geisa.controller.CallPlanController;
import com.gmk.geisa.helper.ItemTouchHelperViewHolder;
import com.gmk.geisa.model.mCallPlanNote;
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
public class CallPlanNoteAdapter extends RecyclerView.Adapter<CallPlanNoteAdapter.ItemViewHolder> {

    private List<mCallPlanNote> mPO = new ArrayList<>();

    private final List<mCallPlanNote> filteredUserList;
    private UserFilter userFilter;
    private Context mContext;
    private mSession sessionUser;
    NumberFormat nf = NumberFormat.getInstance(Locale.GERMAN);
    private CallPlanController callPlanController;

    public CallPlanNoteAdapter(List<mCallPlanNote> po, Context context, mSession session) {
        //mPO = po;
        Log.e("ada note","po"+po.size());
        mContext = context;
        callPlanController = CallPlanController.getInstance(context);
        filteredUserList = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        for (mCallPlanNote cu : po) {
            Log.e("ada note","note"+cu.getNotes1());
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

        final mCallPlanNote selectedCustomer = filteredUserList.get(position);
        holder.idsample.setText(selectedCustomer.getCallPlanNoteId());
        holder.namacustomer.setText(selectedCustomer.getCustName().concat("/").concat(selectedCustomer.getCustAlias()));
        holder.tujuansample.setText(selectedCustomer.getCustAddress());
        holder.requestdate.setText(selectedCustomer.getCreatedDate());
        holder.itemrequest.setText(selectedCustomer.getNotes1());
        holder.lastupdate.setText(selectedCustomer.getModifiedDate());
        holder.status.setText(selectedCustomer.getBiTypeName());
        holder.statusname.setText(selectedCustomer.getBiCsTypeName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               /* holder.itemView.setBackgroundColor(Color.GREEN);
                Intent inten = new Intent(mContext, SampleAddActivity.class);
                inten.putExtra(SampleAddActivity.callPlanId, selectedCustomer.getCallPlanId());
                inten.putExtra(SampleAddActivity.customerId, String.valueOf(selectedCustomer.getCustId()));
                inten.putExtra(SampleAddActivity.sessionUser, sessionUser);
                inten.putExtra(SampleAddActivity.sessionSample, selectedCustomer);
                inten.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.getApplicationContext().startActivity(inten);*/

            }
        });


    }


    @Override
    public int getItemCount() {
        return filteredUserList.size();
    }

    public mCallPlanNote getItem(int position) {
        return filteredUserList.get(position);
    }

    public List<mCallPlanNote> getAllItem() {
        return mPO;
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder implements
            ItemTouchHelperViewHolder {
        public final TextView status, lastupdate, itemrequest, requestdate, tujuansample, namacustomer, idsample,statusname;
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
            statusname=(TextView) itemView.findViewById(R.id.statusname);
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

        private final CallPlanNoteAdapter adapter;

        private final List<mCallPlanNote> originalList;

        private final List<mCallPlanNote> filteredList;

        private UserFilter(CallPlanNoteAdapter adapter, List<mCallPlanNote> originalList) {
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

                for (final mCallPlanNote user : originalList) {

                    if (user.getNotes1().toLowerCase().contains(filterPattern) || user.getNotes3().toLowerCase().contains(filterPattern) || user.getBiTypeName().toLowerCase().contains(filterPattern)) {
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
            adapter.filteredUserList.addAll((ArrayList<mCallPlanNote>) results.values);
            adapter.notifyDataSetChanged();
        }
    }


}
