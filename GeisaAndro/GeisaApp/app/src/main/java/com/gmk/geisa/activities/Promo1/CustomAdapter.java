package com.gmk.geisa.activities.Promo1;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gmk.geisa.R;

import org.w3c.dom.Text;

import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    private Context context;
    private List<MyData> my_data;

    public CustomAdapter(Context context, List<MyData> my_data) {
        this.context = context;
        this.my_data = my_data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.promo_list_row,parent,false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        //holder.promoid.setText(my_data.get(position).getPromoId());
        holder.promoname.setText(my_data.get(position).getPromoName());
        holder.enddate.setText(my_data.get(position).getEndDate());


    }

    @Override
    public int getItemCount() {
        return my_data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder  {

        public TextView promoid;
        public TextView promoname;
        public TextView enddate;

        public ViewHolder(View itemView) {
            super(itemView);
            //promoid = itemView.findViewById(R.id.list_row_promoGroup);
            promoname = itemView.findViewById(R.id.list_row_promoName);
            enddate = itemView.findViewById(R.id.list_row_promoDetail);

        }


    }
}
