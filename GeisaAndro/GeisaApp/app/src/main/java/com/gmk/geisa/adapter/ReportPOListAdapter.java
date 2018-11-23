package com.gmk.geisa.adapter;

import android.content.Context;
import android.content.Intent;
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
import com.gmk.geisa.activities.po.PoNewActivity;
import com.gmk.geisa.helper.ItemTouchHelperViewHolder;
import com.gmk.geisa.model.mPO;
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
public class ReportPOListAdapter extends RecyclerView.Adapter<ReportPOListAdapter.ItemViewHolder> {

    private List<mPO> mPO;

    private final List<mPO> filteredUserList;
    private UserFilter userFilter;
    private Context mContext;
    private mSession sessionUser;
    NumberFormat nf = NumberFormat.getInstance(Locale.GERMAN);
    private boolean IsConfirm,IsCPP;

    public ReportPOListAdapter(List<mPO> po, Context context, mSession session, boolean isConfirm, boolean isCPP) {
        mPO = po;
        mContext = context;
        filteredUserList = new ArrayList<>();
        for (mPO cu : po) {
            filteredUserList.add(cu);
        }
        sessionUser = session;
        userFilter = new UserFilter(this, po);
        IsConfirm=isConfirm;
        IsCPP=isCPP;
    }


    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rowView = LayoutInflater.from(parent.getContext()).inflate(R.layout.report_po_list, parent, false);
        ItemViewHolder viewHolder = new ItemViewHolder(rowView);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(final ItemViewHolder holder, int position) {
        NumberFormat nf = NumberFormat.getInstance(Locale.GERMAN);
        SimpleDateFormat format = new SimpleDateFormat("dd MMMM yyyy hh:mm:ss");
        final mPO selectedCustomer = filteredUserList.get(position);
        final int posisi = position;
        //Log.e("cek PP",selectedCustomer.isPP()+",");
        if (selectedCustomer.isPP()) {
            holder.custid.setText(selectedCustomer.getPoId() + " : PP ");
        } else {
            holder.custid.setText(selectedCustomer.getPoId() + " : Regular");
        }
        holder.custname.setText(String.valueOf(selectedCustomer.getCustId()).concat("-").concat(selectedCustomer.getCustomer().getCustName()));
        holder.custAddress.setText(selectedCustomer.getShipAddress());
        if (selectedCustomer.getDistBranch() != null)
            holder.distributor.setText(selectedCustomer.getDistBranch().getDistBranchName());
        if (selectedCustomer.getDistBranch() != null)
            holder.distributorAddress.setText(selectedCustomer.getDistBranch().getDistBranchAddress());
        if (selectedCustomer.getDistBranch() != null)
            holder.custcode.setText(selectedCustomer.getDistBranch().getCustCode());
        if (selectedCustomer.getDistBranch() != null)
            holder.pricecode.setText(selectedCustomer.getDistBranch().getPriceGroupName());
        String tgl = format.format(getTanggal(selectedCustomer.getCreatedDate()).getTime());
        holder.tglcreate.setText(tgl);
        if(selectedCustomer.isSellOut()){
            holder.status.setText(selectedCustomer.getPoStatusName().concat(" SellOut"));
        }else{
            holder.status.setText(selectedCustomer.getPoStatusName());
        }

        holder.keterangan.setText(selectedCustomer.getKeteranganDetail());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setBackgroundColor(Color.GREEN);
                Intent inten = new Intent(mContext, PoNewActivity.class);
                inten.putExtra(PoNewActivity.sessionUser, sessionUser);
                inten.putExtra(PoNewActivity.sessionPO, selectedCustomer);
                inten.putExtra(PoNewActivity.sessionSalesConfirm, IsConfirm);
                inten.putExtra(PoNewActivity.sessionIsCopyPP, IsCPP);
                inten.putExtra(PoNewActivity.sessionReportView, true);
                inten.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.getApplicationContext().startActivity(inten);
            }
        });


    }

    private Calendar getTanggal(String dtStart) {
        Calendar tgldpt = Calendar.getInstance();
        boolean tglkosong = true;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        if (!TextUtils.isEmpty(dtStart) && !dtStart.equalsIgnoreCase("null")) {
            try {
                Date dates = format.parse(dtStart);
                // Log.e("isi start", date.toString());
                tglkosong = false;
                tgldpt.setTime(dates);
                //  Log.e("tgl",tgldpt.getTime()+",");
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                tglkosong = true;
                e.printStackTrace();
                Log.e("tgl", e.toString() + "");
            }
        } else {
            tglkosong = true;
        }
        return tgldpt;
    }


    @Override
    public int getItemCount() {
        return filteredUserList.size();
    }

    public mPO getItem(int position) {
        return filteredUserList.get(position);
    }

    public List<mPO> getAllItem() {
        return mPO;
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder implements
            ItemTouchHelperViewHolder {
        public final TextView custid, custname, custAddress, distributor, distributorAddress, custcode, pricecode, tglcreate, status,keterangan;
        public final ImageView handleView;

        public ItemViewHolder(View itemView) {
            super(itemView);
            custid = (TextView) itemView.findViewById(R.id.custid);
            custname = (TextView) itemView.findViewById(R.id.custname);
            custAddress = (TextView) itemView.findViewById(R.id.custAddress);
            distributor = (TextView) itemView.findViewById(R.id.distributor);
            distributorAddress = (TextView) itemView.findViewById(R.id.distributorAddress);
            custcode = (TextView) itemView.findViewById(R.id.custcode);
            pricecode = (TextView) itemView.findViewById(R.id.pricecode);
            tglcreate = (TextView) itemView.findViewById(R.id.tglcreate);
            status = (TextView) itemView.findViewById(R.id.status);
            keterangan=(TextView) itemView.findViewById(R.id.keterangan);
            handleView = (ImageView) itemView.findViewById(R.id.handle);


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

        private final ReportPOListAdapter adapter;

        private final List<mPO> originalList;

        private final List<mPO> filteredList;

        private UserFilter(ReportPOListAdapter adapter, List<mPO> originalList) {
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

                for (final mPO user : originalList) {
                    if (user.getCustomer() != null) {
                        if (String.valueOf(user.getCustId()).toLowerCase().contains(filterPattern) || user.getCustomer().getCustName().toLowerCase().contains(filterPattern)) {
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
            adapter.filteredUserList.addAll((ArrayList<mPO>) results.values);
            adapter.notifyDataSetChanged();
        }
    }


}
