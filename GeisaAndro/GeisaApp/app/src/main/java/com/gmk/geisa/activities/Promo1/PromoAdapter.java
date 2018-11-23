package com.gmk.geisa.activities.Promo1;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gmk.geisa.R;

import java.util.List;

public class PromoAdapter extends RecyclerView.Adapter<PromoAdapter.MyViewHolder> {

    private List<Promo> promoList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView promoName, promoDetail, promoGroup;

        public MyViewHolder(View view) {
            super(view);
            promoName = (TextView) view.findViewById(R.id.list_row_promoName);
            promoDetail = (TextView) view.findViewById(R.id.list_row_promoDetail);
            promoGroup = (TextView) view.findViewById(R.id.list_row_promoGroup);
        }
    }


    public PromoAdapter(List<Promo> promoList) {
        this.promoList = promoList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.promo_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Promo promo = promoList.get(position);
        holder.promoName.setText(promo.getPromoName());
        holder.promoDetail.setText(promo.getPromoDetail());
        holder.promoGroup.setText(promo.getPromoGroup());
    }

    @Override
    public int getItemCount() {
        return promoList.size();
    }
}