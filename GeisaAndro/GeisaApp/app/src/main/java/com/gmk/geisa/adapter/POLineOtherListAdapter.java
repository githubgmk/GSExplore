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
import com.gmk.geisa.helper.ItemTouchHelperViewHolder;
import com.gmk.geisa.model.mPoLineOther;

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
public class POLineOtherListAdapter extends RecyclerView.Adapter<POLineOtherListAdapter.ItemViewHolder> {

    private List<mPoLineOther> mProductPrice;
    //private List<mCustomer> mCustomersReal;
    private final List<mPoLineOther> filteredUserList;
    private UserFilter userFilter;
    private Context mContext;

    private TextView mTextView;
    private int totalselected, totalBefore;

    public POLineOtherListAdapter(List<mPoLineOther> customers, Context context, TextView tv) {
        mProductPrice = customers;
        // mCustomersReal=customers;
        mContext = context;
        mTextView = tv;
        filteredUserList = new ArrayList<>();
        for (mPoLineOther cu : customers) {
            filteredUserList.add(cu);
        }
        userFilter = new UserFilter(this, customers);
    }

    public static int getIcon(Context c, String resName) {
        try {
            String newstr = resName;
            int endIndex = resName.lastIndexOf(".");
            if (endIndex != -1) {
                newstr = resName.substring(0, endIndex); // not forgot to put check if(endIndex != -1)
            }
            return c.getResources().getIdentifier(newstr, "drawable", c.getPackageName());
        } catch (Exception e) {
            Log.e("err", e.toString());
            e.printStackTrace();
            return -1;
        }
    }


    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rowView = LayoutInflater.from(parent.getContext()).inflate(R.layout.checked_po_product_other, parent, false);
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

    @Override
    public void onBindViewHolder(final ItemViewHolder holder, int position) {
        final mPoLineOther selectedCustomer = filteredUserList.get(position);
        final int posisi = position;
        holder.otherCode.setText(selectedCustomer.getProductCode());
        holder.otherName.setText(selectedCustomer.getProductName());
        holder.otherQty.setText(String.valueOf(selectedCustomer.getQty()));
        holder.otherUnit.setText(selectedCustomer.getUnit());

        if(selectedCustomer.isSelected()){
            holder.handle.setImageResource(android.R.drawable.ic_menu_delete);
            holder.itemView.setBackgroundColor(Color.RED);
        }else{
            holder.handle.setImageResource(R.drawable.ic_reorder_grey);
            holder.itemView.setBackgroundColor(Color.TRANSPARENT);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedCustomer.isSelected()) {

                    filteredUserList.get(posisi).setSelected(false);
                    selectedCustomer.setSelected(false);
                    int idx = mProductPrice.indexOf(filteredUserList.get(posisi));
                    mProductPrice.get(idx).setSelected(false);
                    holder.handle.setImageResource(android.R.drawable.ic_menu_delete);
                    v.setBackgroundColor(Color.RED);
                    totalselected -= 1;
                    //
                } else {
                    filteredUserList.get(posisi).setSelected(true);
                    selectedCustomer.setSelected(true);
                    int idx = mProductPrice.indexOf(filteredUserList.get(posisi));
                    mProductPrice.get(idx).setSelected(true);
                    holder.handle.setImageResource(R.drawable.ic_reorder_grey);
                    v.setBackgroundColor(Color.TRANSPARENT);
                    totalselected += 1;
                    //

                }
                if (mTextView != null)
                    mTextView.setText(String.valueOf(totalselected));

            }
        });


    }


    @Override
    public int getItemCount() {
        return filteredUserList.size();
    }

    public mPoLineOther getItem(int position) {
        return filteredUserList.get(position);
    }

    public mPoLineOther getItem(mPoLineOther ot){
        return filteredUserList.get(filteredUserList.indexOf(ot));
    }

    public List<mPoLineOther> getAllItem() {
        return mProductPrice;
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder implements
            ItemTouchHelperViewHolder {
        public final TextView otherCode, otherName, otherQty, otherUnit;
        public final ImageView handle;

        public ItemViewHolder(View itemView) {
            super(itemView);
            otherCode = (TextView) itemView.findViewById(R.id.otherCode);
            otherName = (TextView) itemView.findViewById(R.id.otherName);
            otherQty = (TextView) itemView.findViewById(R.id.otherQty);
            otherUnit = (TextView) itemView.findViewById(R.id.otherUnit);
            handle=(ImageView) itemView.findViewById(R.id.handle);


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

        private final POLineOtherListAdapter adapter;

        private final List<mPoLineOther> originalList;

        private final List<mPoLineOther> filteredList;

        private UserFilter(POLineOtherListAdapter adapter, List<mPoLineOther> originalList) {
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

                for (final mPoLineOther user : originalList) {
                    if (String.valueOf(user.getProductCode()).toLowerCase().contains(filterPattern) || user.getProductName().toLowerCase().contains(filterPattern)) {
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
            adapter.filteredUserList.addAll((ArrayList<mPoLineOther>) results.values);
            adapter.notifyDataSetChanged();
        }
    }


}
