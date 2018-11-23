package com.gmk.geisa.adapter;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.gmk.geisa.R;
import com.gmk.geisa.activities.customer.CustomerDetailActivity;
import com.gmk.geisa.activities.customer.CustomerDetailAllActivity;
import com.gmk.geisa.model.mCustomer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kenjinsan on 4/24/17.
 */

public class CustomerOutletAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;

    private boolean loading = true;
    int selectedPosition=-1;
    private List<mCustomer> listCustomer;
    private OnItemClickListener onItemClickListener;
    private Context context;

    public CustomerOutletAdapter(Context ctx) {
        this.context=ctx;
        listCustomer = new ArrayList<>();
    }

    private void add(mCustomer item) {
        listCustomer.add(item);
        notifyItemInserted(listCustomer.size());
    }

    public void addAll(List<mCustomer> memberList) {
        for (mCustomer member : memberList) {
            add(member);
        }
        selectedPosition=-1;
    }

    public void remove(mCustomer item) {
        int position = listCustomer.indexOf(item);
        if (position > -1) {
            listCustomer.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clear() {
        while (getItemCount() > 0) {
            remove(getItem(0));
        }
    }

    public mCustomer getItem(int position){
        return listCustomer.get(position);
    }

    @Override
    public int getItemViewType (int position) {
        if(isPositionFooter (position)) {
            return VIEW_TYPE_LOADING;
        }
        return VIEW_TYPE_ITEM;
    }

    private boolean isPositionFooter (int position) {
        return position == listCustomer.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_customer_outlet, parent, false);
            return new MemberViewHolder(view, onItemClickListener);
        } else if (viewType == VIEW_TYPE_LOADING) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.footer_loading, parent, false);
            return new LoadingViewHolder(view);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        if (holder instanceof MemberViewHolder) {
            MemberViewHolder memberViewHolder = (MemberViewHolder) holder;

            final mCustomer member = listCustomer.get(position);

            memberViewHolder.memberName.setText(member.getCustName());
            memberViewHolder.memberTeam.setText(member.getAliasName());
            memberViewHolder.memberOther.setText(member.getChannelName());

        } else if (holder instanceof LoadingViewHolder) {
            LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
            loadingViewHolder.progressBar.setIndeterminate(true);
            loadingViewHolder.progressBar.setVisibility(loading ? View.VISIBLE : View.GONE);
        }
        if(selectedPosition==position)
            holder.itemView.setBackgroundColor(Color.parseColor("#FF4081"));
        else
            holder.itemView.setBackgroundColor(Color.parseColor("#ffffff"));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedPosition=position;
                notifyDataSetChanged();
                Intent inten = new Intent(context, CustomerDetailAllActivity.class);
                inten.putExtra(CustomerDetailAllActivity.sessioncustomer, listCustomer.get(position));
                inten.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.getApplicationContext().startActivity(inten);
            }
        });

    }

    public void setLoading(boolean loading){
        this.loading = loading;
    }

    @Override
    public int getItemCount() {
        return listCustomer == null ? 0 : listCustomer.size() + 1;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    static class MemberViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView memberName;
        TextView memberTeam;
        TextView memberOther;

        OnItemClickListener onItemClickListener;

        public MemberViewHolder(View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);

            memberName = (TextView) itemView.findViewById(R.id.name);
            memberTeam = (TextView) itemView.findViewById(R.id.team);
            memberOther=(TextView)  itemView.findViewById(R.id.other);

            itemView.setOnClickListener(this);
            this.onItemClickListener = onItemClickListener;
        }

        @Override
        public void onClick(View v) {
            onItemClickListener.onItemClick(v, getAdapterPosition());
        }
    }

    static class LoadingViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public LoadingViewHolder(View itemView) {
            super(itemView);

            progressBar = (ProgressBar) itemView.findViewById(R.id.loading);
        }
    }


    public interface OnItemClickListener {

        void onItemClick(View view, int position);
    }
}
