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
import com.gmk.geisa.model.mCustomer;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Kenjin on 07/23/2017.
 */
public class CustomerCheckedListAdapter extends RecyclerView.Adapter<CustomerCheckedListAdapter.ItemViewHolder> {

    private List<mCustomer> mCustomers;
    //private List<mCustomer> mCustomersReal;
    private final List<mCustomer> filteredUserList;
    private UserFilter userFilter;
    private Context mContext;

    private TextView mTextView;
    private int totalselected;

    public CustomerCheckedListAdapter(List<mCustomer> customers, Context context, TextView tv) {
        mCustomers = customers;
        // mCustomersReal=customers;
        mContext = context;
        mTextView = tv;
        filteredUserList = new ArrayList<>();
        for (mCustomer cu : customers) {
            filteredUserList.add(cu);
        }
        userFilter = new UserFilter(this, customers);


    }


    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rowView = LayoutInflater.from(parent.getContext()).inflate(R.layout.checked_customer_list, parent, false);
        ItemViewHolder viewHolder = new ItemViewHolder(rowView);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(final ItemViewHolder holder, int position) {


        final mCustomer selectedCustomer = filteredUserList.get(position);
        final int posisi = position;

        holder.customerName.setText(selectedCustomer.getCustName());
        holder.customerAlias.setText(selectedCustomer.getAliasName());
        holder.customerAddress.setText(selectedCustomer.getAddress());
        if (selectedCustomer.isSelected()) {
            holder.handleView.setImageResource(android.R.drawable.checkbox_on_background);
            holder.itemView.setBackgroundColor(Color.GREEN);
            totalselected += 1;
            mTextView.setText(String.valueOf(totalselected));
        } else {
            holder.itemView.setBackgroundColor(Color.TRANSPARENT);
            holder.handleView.setImageResource(R.drawable.ic_reorder_grey);
        }
        //change image from internet
        /*Picasso.with(mContext)
                .load(selectedCustomer.getImagePath())
                .placeholder(R.drawable.addcustomer)
                .into(holder.profileImage);*/
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedCustomer.isSelected()) {
                    filteredUserList.get(posisi).setSelected(false);
                    selectedCustomer.setSelected(false);
                    int idx = mCustomers.indexOf(filteredUserList.get(posisi));
                    mCustomers.get(idx).setSelected(false);
                    holder.handleView.setImageResource(R.drawable.ic_reorder_grey);
                    v.setBackgroundColor(Color.TRANSPARENT);
                    totalselected -= 1;

                } else {
                    filteredUserList.get(posisi).setSelected(true);
                    selectedCustomer.setSelected(true);
                    int idx = mCustomers.indexOf(filteredUserList.get(posisi));
                    mCustomers.get(idx).setSelected(true);
                    holder.handleView.setImageResource(android.R.drawable.checkbox_on_background);
                    v.setBackgroundColor(Color.GREEN);
                    totalselected += 1;

                }
                mTextView.setText(String.valueOf(totalselected));
            }
        });


    }


    @Override
    public int getItemCount() {
        return filteredUserList.size();
    }

    public mCustomer getItem(int position) {
        return filteredUserList.get(position);
    }

    public List<mCustomer> getAllItem() {
        return mCustomers;
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder implements
            ItemTouchHelperViewHolder {
        public final TextView customerName, customerAlias, customerAddress;
        public final ImageView handleView, profileImage;


        public ItemViewHolder(View itemView) {
            super(itemView);
            customerName = (TextView) itemView.findViewById(R.id.text_view_customer_name);
            customerAlias = (TextView) itemView.findViewById(R.id.text_view_customer_alias);
            customerAddress = (TextView) itemView.findViewById(R.id.text_view_customer_address);
            handleView = (ImageView) itemView.findViewById(R.id.handle);
            profileImage = (ImageView) itemView.findViewById(R.id.image_view_customer_head_shot);


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

        private final CustomerCheckedListAdapter adapter;

        private final List<mCustomer> originalList;

        private final List<mCustomer> filteredList;

        private UserFilter(CustomerCheckedListAdapter adapter, List<mCustomer> originalList) {
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

                for (final mCustomer user : originalList) {
                    if (user.getCustName().toLowerCase().contains(filterPattern) || user.getAliasName().toLowerCase().contains(filterPattern)) {
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
            adapter.filteredUserList.addAll((ArrayList<mCustomer>) results.values);
            adapter.notifyDataSetChanged();
        }
    }


}
