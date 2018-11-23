package com.gmk.geisa.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gmk.geisa.R;
import com.gmk.geisa.helper.ItemTouchHelperViewHolder;
import com.gmk.geisa.model.mCustomerClasification;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Kenjin on 07/23/2017.
 */
public class CustSegmentasiToDoListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<mCustomerClasification.ChannelStagingApproach> mCustomers;
    private final List<mCustomerClasification.ChannelStagingApproach> filteredUserList;
    public static final int CITY_TYPE = 0;
    public static final int EVENT_TYPE = 1;
    List<A> data = new ArrayList<>();

    public CustSegmentasiToDoListAdapter(List<mCustomerClasification.ChannelStagingApproach> todo, Context context) {
        mCustomers = todo;

        filteredUserList = new ArrayList<>();
        filteredUserList.addAll(todo);
        for (int a = 0; a < todo.size(); a++) {
            data.add(new A(a, 0, false));
            int b = 0;
            if (todo.get(a).getItem() != null && todo.get(a).getItem().size() > 0)
                for (mCustomerClasification.ChannelStagingApproach.Item it : todo.get(a).getItem()) {
                    data.add(new A(a, b, true));
                    b++;
                }
        }

    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case CITY_TYPE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_city, parent, false);
                return new CityViewHolder(view);
            case EVENT_TYPE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_event, parent, false);
                return new EventViewHolder(view);
        }
        return null;

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final A dt = data.get(position);
        int pos = (dt.isDetail()) ? 1 : 0;
        mCustomerClasification.ChannelStagingApproach selectedCustomer = null;
        if (dt.getHeader() < filteredUserList.size())
            selectedCustomer = filteredUserList.get(dt.getHeader());
        if (selectedCustomer != null) {
            switch (pos) {
                case CITY_TYPE:
                    ((CityViewHolder) holder).mTitle.setText(selectedCustomer.getChannelStagingFor() + ":" + selectedCustomer.getChannelStagingName());
                    ((CityViewHolder) holder).mTitle1.setText("detail : " + selectedCustomer.getChannelStagingDescription());
                    ((CityViewHolder) holder).mTitle2.setText("Share walet: " + selectedCustomer.getChannelStagingShareWalet());
                    break;
                case EVENT_TYPE:
                    //diadd untuk detail item ya
                    mCustomerClasification.ChannelStagingApproach.Item detail = null;
                    if (selectedCustomer.getItem() != null)
                        detail = selectedCustomer.getItem().get(dt.getNodetail());
                    assert detail != null;
                    ((EventViewHolder) holder).mTitle.setText(detail.getChannelStagingItemName());
                    ((EventViewHolder) holder).mDescription.setText(detail.getChannelStagingItemDesc());
                    break;
            }
        }
    }


    @Override
    public int getItemCount() {
        return data.size();
    }


    @Override
    public int getItemViewType(int position) {
        if (data != null) {
            A object = data.get(position);
            if (object != null) {
                if (object.isDetail()) {
                    return 1;
                } else {
                    return 0;
                }
            }
        }
        return 0;
    }

    public static class CityViewHolder extends RecyclerView.ViewHolder {
        private TextView mTitle, mTitle1, mTitle2;

        public CityViewHolder(View itemView) {
            super(itemView);
            mTitle = (TextView) itemView.findViewById(R.id.titleTextView);
            mTitle1 = (TextView) itemView.findViewById(R.id.titleTextView1);
            mTitle2 = (TextView) itemView.findViewById(R.id.titleTextView2);
        }
    }

    public static class EventViewHolder extends RecyclerView.ViewHolder {
        private TextView mTitle;
        private TextView mDescription;

        public EventViewHolder(View itemView) {
            super(itemView);
            mTitle = (TextView) itemView.findViewById(R.id.titleTextView);
            mDescription = (TextView) itemView.findViewById(R.id.descriptionTextView);
        }
    }


    public static class ItemViewHolder extends RecyclerView.ViewHolder implements
            ItemTouchHelperViewHolder {
        public final TextView productId, productName, productDesc;
        public final ImageView handleView, productImage;


        public ItemViewHolder(View itemView) {
            super(itemView);
            productId = (TextView) itemView.findViewById(R.id.productId);
            productName = (TextView) itemView.findViewById(R.id.productName);
            productDesc = (TextView) itemView.findViewById(R.id.productDesc);
            handleView = (ImageView) itemView.findViewById(R.id.handle);
            productImage = (ImageView) itemView.findViewById(R.id.productImage);


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


    class A {
        private int header;
        private boolean detail;
        private int nodetail;

        public A(int header, int nodetail, boolean detail) {
            this.header = header;
            this.nodetail = nodetail;
            this.detail = detail;
        }

        public int getHeader() {
            return header;
        }

        public void setHeader(int header) {
            this.header = header;
        }

        public boolean isDetail() {
            return detail;
        }

        public int getNodetail() {
            return nodetail;
        }

        public void setNodetail(int nodetail) {
            this.nodetail = nodetail;
        }

    }
}
