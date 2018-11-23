package com.gmk.geisa.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.TextView;

import com.gmk.geisa.R;
import com.gmk.geisa.controller.ProductController;
import com.gmk.geisa.helper.ItemTouchHelperViewHolder;
import com.gmk.geisa.model.mCustomer;
import com.kenmeidearu.searchablespinnerlibrary.mListString;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Kenjin on 07/23/2017.
 */
public class CustomerListAdapter extends RecyclerView.Adapter<CustomerListAdapter.ItemViewHolder> {

    private final List<mCustomer> filteredUserList;
    ArrayList<mListString> listStringUnit = new ArrayList<>();
    NumberFormat nf = NumberFormat.getInstance(Locale.GERMAN);
    ProductController productController;
    private List<mCustomer> mProductPrice;
    private UserFilter userFilter;
    private Context mContext;


    public CustomerListAdapter(List<mCustomer> customers, Context context) {
        mProductPrice = customers;
        // mCustomersReal=customers;
        mContext = context;
        filteredUserList = new ArrayList<>();
        productController = ProductController.getInstance(context);
        for (mCustomer cu : customers) {
            filteredUserList.add(cu);
        }
        userFilter = new UserFilter(this, customers);
    }


    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rowView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_customer, parent, false);
        ItemViewHolder viewHolder = new ItemViewHolder(rowView);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(final ItemViewHolder holder, int position) {
        NumberFormat nf = NumberFormat.getInstance(Locale.GERMAN);
        final mCustomer selectedCustomer = filteredUserList.get(position);
        if (selectedCustomer != null) {
            holder.memberName.setText(selectedCustomer.getCustName());
            holder.memberTeam.setText(selectedCustomer.getAliasName());
            holder.memberAddress.setText(selectedCustomer.getAddress());
            holder.memberStatus.setText(selectedCustomer.getCustStatusName());
        }
    }


    @Override
    public int getItemCount() {
        return filteredUserList.size();
    }

    public mCustomer getItem(int position) {
        return filteredUserList.get(position);
    }

    public List<mCustomer> getAllItem() {
        return mProductPrice;
    }

    public Filter setFilter() {
        return userFilter;
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder implements
            ItemTouchHelperViewHolder {
        TextView memberName;
        TextView memberTeam;
        TextView memberStatus;
        TextView memberAddress;

        public ItemViewHolder(View itemView) {
            super(itemView);
            memberName = (TextView) itemView.findViewById(R.id.name);
            memberTeam = (TextView) itemView.findViewById(R.id.team);
            memberStatus = (TextView) itemView.findViewById(R.id.Status);
            memberAddress = (TextView) itemView.findViewById(R.id.address);
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

    public static class UserFilter extends Filter {

        private final CustomerListAdapter adapter;

        private final List<mCustomer> originalList;

        private final List<mCustomer> filteredList;

        private UserFilter(CustomerListAdapter adapter, List<mCustomer> originalList) {
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
                    if (user.getCustName().toLowerCase().contains(filterPattern) || user.getAliasName().toLowerCase().equalsIgnoreCase(filterPattern)) {
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
                if (adapter.filteredUserList != null) {
                    adapter.filteredUserList.clear();
                    if (results.values != null)
                        adapter.filteredUserList.addAll((ArrayList<mCustomer>) results.values);
                }
                adapter.notifyDataSetChanged();
            }
        }
    }


}
