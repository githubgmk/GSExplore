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
import android.widget.Toast;

import com.gmk.geisa.R;
import com.gmk.geisa.activities.customer.CustomerDetailActivity;
import com.gmk.geisa.activities.customer.CustomerDetailAllActivity;
import com.gmk.geisa.helper.ItemTouchHelperViewHolder;
import com.gmk.geisa.model.mCallPlan;
import com.gmk.geisa.model.mSession;

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
public class ReportCallPlanListAdapter extends RecyclerView.Adapter<ReportCallPlanListAdapter.ItemViewHolder> {

    private List<mCallPlan> mCustomers;
    //private List<mCustomer> mCustomersReal;
    private final List<mCallPlan> filteredUserList = new ArrayList<>();
    private UserFilter userFilter;
    private Context mContext;
    private mSession sessionUser;

    public ReportCallPlanListAdapter(List<mCallPlan> customers, Context context, mSession session) {
        mCustomers = customers;
        // mCustomersReal=customers;
        mContext = context;
        sessionUser = session;
        for (mCallPlan cu : customers) {
            filteredUserList.add(cu);
        }
        userFilter = new UserFilter(this, customers);
    }


    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rowView = LayoutInflater.from(parent.getContext()).inflate(R.layout.checked_call_plan_list, parent, false);
        ItemViewHolder viewHolder = new ItemViewHolder(rowView);
        return viewHolder;
    }

    private String getBulan(String tgl) {
        Calendar tgldtp = getTanggal(tgl);
        SimpleDateFormat dateFormat = new SimpleDateFormat("LLL", Locale.getDefault());
        dateFormat.format(tgldtp.getTime());
        return String.valueOf(dateFormat.format(tgldtp.getTime()));
    }

    private String getTahun(String tgl) {
        Calendar tgldtp = getTanggal(tgl);
        return String.valueOf(tgldtp.get(Calendar.YEAR));
    }

    private String getHari(String tgl) {
        Calendar tgldtp = getTanggal(tgl);
        return String.valueOf(tgldtp.get(Calendar.DAY_OF_MONTH));
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

    private boolean cekTanggalHariCallPlan(String tanggal) {
        Calendar tgldpt = Calendar.getInstance();
        Calendar now = Calendar.getInstance();
        boolean boleh = false;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        if (!TextUtils.isEmpty(tanggal) && !tanggal.equalsIgnoreCase("null")) {
            try {
                Date dates = format.parse(tanggal);
                // Log.e("isi start", date.toString());
                tgldpt.setTime(dates);
                if (tgldpt.before(now) || tgldpt.equals(now)) {
                    boleh = true;
                }
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                boleh = false;
                e.printStackTrace();
            }
        } else {
            boleh = false;
        }
        return boleh;
    }

    @Override
    public void onBindViewHolder(final ItemViewHolder holder, int position) {


        final mCallPlan selectedCustomer = filteredUserList.get(position);
        final int posisi = position;
        // Log.e("isi tgl",selectedCustomer.getCallPlanDate()+","+selectedCustomer.getCustomerAddress()+","+totalselected);
        holder.tglcall.setText(getHari(selectedCustomer.getCallPlanDate()));
        holder.blncall.setText(getBulan(selectedCustomer.getCallPlanDate()));
        holder.tahuncall.setText(getTahun(selectedCustomer.getCallPlanDate()));
        holder.customerName.setText(selectedCustomer.getCustomerName());
        holder.customerAlias.setText(selectedCustomer.getCustomerAlias());
        holder.customerAddress.setText(selectedCustomer.getCustomerAddress());
        holder.callplanstatus.setText(selectedCustomer.getCallPlanStatusName());
        holder.callplantype.setText(selectedCustomer.getCallPlanTypeName());
        if (selectedCustomer.getCallPlanStatusId() >= 3) {
            holder.callplanstatus.setTextColor(Color.GREEN);
        } else if (selectedCustomer.getCallPlanStatusId() >= 2) {
            holder.callplanstatus.setTextColor(Color.BLUE);
        } else {
            holder.callplanstatus.setTextColor(Color.RED);
        }

        //holder.itemView.setBackgroundColor(Color.TRANSPARENT);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setBackgroundColor(Color.GREEN);
                if (cekTanggalHariCallPlan(selectedCustomer.getCallPlanDate())) {
                    if (selectedCustomer.getCallPlanStatusId() == 2) {
                        Intent inten = new Intent(mContext, CustomerDetailAllActivity.class);
                        inten.putExtra(CustomerDetailAllActivity.sessionCallPlan, true);
                        inten.putExtra(CustomerDetailAllActivity.sessionCallPlan, selectedCustomer);
                        mContext.startActivity(inten);
                    } else {
                        //masuk call plan untuk update data saja tidak cek in dan tidak cek out
                        v.setBackgroundColor(Color.TRANSPARENT);
                    }
                } else {
                    Toast.makeText(mContext, "It's not The Day", Toast.LENGTH_SHORT).show();
                    v.setBackgroundColor(Color.TRANSPARENT);

                }



                /*if (selectedCustomer.isSelected()) {
                    filteredUserList.get(posisi).setSelected(false);
                    selectedCustomer.setSelected(false);
                    int idx = mCustomers.indexOf(filteredUserList.get(posisi));
                    mCustomers.get(idx).setSelected(false);
                   // holder.handleView.setImageResource(R.drawable.ic_reorder_grey);
                    v.setBackgroundColor(Color.TRANSPARENT);
                    totalselected += 1;
                   // mTextView.setText(String.valueOf(totalselected));
                } else {
                    filteredUserList.get(posisi).setSelected(true);
                    selectedCustomer.setSelected(true);
                    int idx = mCustomers.indexOf(filteredUserList.get(posisi));
                    mCustomers.get(idx).setSelected(true);
                   // holder.handleView.setImageResource(android.R.drawable.ic_menu_delete);
                    v.setBackgroundColor(Color.RED);
                    totalselected -= 1;
                   // mTextView.setText(String.valueOf(totalselected));

                }*/

            }
        });


    }


    @Override
    public int getItemCount() {
        return filteredUserList.size();
    }

    public mCallPlan getItem(int position) {
        return filteredUserList.get(position);
    }

    public List<mCallPlan> getAllItem() {
        return mCustomers;
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder implements
            ItemTouchHelperViewHolder {
        public final TextView customerName, customerAlias, customerAddress,
                tglcall, blncall, tahuncall, callplanstatus, callplantype;
        public final ImageView profileImage, callplanstatusimage;


        public ItemViewHolder(View itemView) {
            super(itemView);
            tglcall = (TextView) itemView.findViewById(R.id.callplantgl);
            blncall = (TextView) itemView.findViewById(R.id.callplanbln);
            tahuncall = (TextView) itemView.findViewById(R.id.callplantahun);
            customerName = (TextView) itemView.findViewById(R.id.text_view_customer_name);
            customerAlias = (TextView) itemView.findViewById(R.id.text_view_customer_alias);
            customerAddress = (TextView) itemView.findViewById(R.id.text_view_customer_address);
            profileImage = (ImageView) itemView.findViewById(R.id.image_view_customer_head_shot);
            callplanstatus = (TextView) itemView.findViewById(R.id.callplanstatus);
            callplanstatusimage = (ImageView) itemView.findViewById(R.id.callplanstatusimage);
            callplantype = (TextView) itemView.findViewById(R.id.callplantype);


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

        private final ReportCallPlanListAdapter adapter;

        private final List<mCallPlan> originalList;

        private final List<mCallPlan> filteredList;

        private UserFilter(ReportCallPlanListAdapter adapter, List<mCallPlan> originalList) {
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

                for (final mCallPlan user : originalList) {
                    if (user.getCustomerName().toLowerCase().contains(filterPattern) || user.getCustomerAlias().toLowerCase().contains(filterPattern)) {
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
            if (adapter != null) {
                adapter.filteredUserList.clear();
                if (results.values != null)
                    adapter.filteredUserList.addAll((ArrayList<mCallPlan>) results.values);
            }
            adapter.notifyDataSetChanged();
        }
    }


}
