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
import java.util.Locale;

/**
 * Created by kenjinsan on 4/24/17.
 */

public class CustomerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;

    private boolean loading = true;
    int selectedPosition=-1;
    private List<mCustomer> listCustomer;

    private OnItemClickListener onItemClickListener;
    private Context context;

    //for search
    private List<mCustomer> worldpopulationlist = null;
    private ArrayList<mCustomer> arraylist;

    public CustomerAdapter(Context ctx,List<mCustomer> worldpopulationlist) {
        this.context=ctx;
        listCustomer = new ArrayList<>();
        this.arraylist = new ArrayList<>();
        this.arraylist.addAll(worldpopulationlist);
        this.worldpopulationlist = worldpopulationlist;
    }
    public CustomerAdapter(Context ctx) {
        this.context=ctx;
        listCustomer = new ArrayList<>();
        worldpopulationlist = new ArrayList<>();
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


    public void swap(ArrayList<mCustomer> datas){
        listCustomer.clear();
        listCustomer.addAll(datas);
        notifyDataSetChanged();
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
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_customer, parent, false);
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

            // memberViewHolder.memberThumb.setImageResource(member.getThumb());
            memberViewHolder.memberName.setText(member.getCustName());
            memberViewHolder.memberTeam.setText(member.getAliasName());
            memberViewHolder.memberAddress.setText(member.getAddress());
            memberViewHolder.memberStatus.setText(member.getCustStatusName());

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
                //Intent inten = new Intent(context, CustomerDetailActivity.class);
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
        TextView memberStatus;
        TextView memberAddress;

        OnItemClickListener onItemClickListener;

        public MemberViewHolder(View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);

            //memberThumb = (ImageView) itemView.findViewById(R.id.thumb);
            memberName = (TextView) itemView.findViewById(R.id.name);
            memberTeam = (TextView) itemView.findViewById(R.id.team);
            memberStatus=(TextView) itemView.findViewById(R.id.Status);
            memberAddress=(TextView) itemView.findViewById(R.id.address);

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


    //filter
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        worldpopulationlist.clear();
        listCustomer.clear();
        if (charText.length() == 0) {
            worldpopulationlist.addAll(arraylist);
        } else {
            for (mCustomer wp : arraylist) {
                if (wp.getCustName().toLowerCase(Locale.getDefault()).contains(charText) || wp.getAliasName().toLowerCase(Locale.getDefault()).contains(charText)) {
                    worldpopulationlist.add(wp);
                }
            }
        }
        addAll(worldpopulationlist);

        notifyDataSetChanged();
    }

    public void filter(String charText,ArrayList<mCustomer> customers) {
        if(null!=customers) {
            charText = charText.toLowerCase(Locale.getDefault());
            worldpopulationlist.clear();
            if (charText.length() == 0) {
                worldpopulationlist.addAll(customers);
                this.loading = true;
            } else {
                for (mCustomer wp : customers) {
                    if (wp.getCustName().toLowerCase(Locale.getDefault()).contains(charText) || wp.getAliasName().toLowerCase(Locale.getDefault()).contains(charText)) {
                        worldpopulationlist.add(wp);
                    }
                }
                this.loading = false;
            }

            listCustomer = worldpopulationlist;
            notifyDataSetChanged();
        }
    }
}
