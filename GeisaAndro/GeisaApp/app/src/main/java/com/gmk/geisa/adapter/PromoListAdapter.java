package com.gmk.geisa.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.gmk.geisa.activities.main.PromoActivity;
import com.kenmeidearu.searchablespinnerlibrary.mListString;


import com.gmk.geisa.R;
import com.gmk.geisa.helper.ItemTouchHelperViewHolder;
import com.gmk.geisa.model.mPromo;
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
public class PromoListAdapter extends RecyclerView.Adapter<PromoListAdapter.ItemViewHolder> {


    private final List<mPromo> filteredUserList;
    ArrayList<mListString> listStringsUnit = new ArrayList<>();
    NumberFormat nf = NumberFormat.getInstance(Locale.GERMAN);
    private List<mPromo> mPO;
    private UserFilter userFilter;
    private Context mContext;
    private mSession sessionUser;


    public PromoListAdapter(List<mPromo> po, Context context, mSession session) {
        mPO = po;
        mContext = context;
        filteredUserList = new ArrayList<>();
        for (mPromo cu : po) {
            filteredUserList.add(cu);
        }
        sessionUser = session;
        userFilter = new UserFilter(this, po);
    }


    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rowView = LayoutInflater.from(parent.getContext()).inflate(R.layout.promo_list_row, parent, false); //list_promo
        ItemViewHolder viewHolder = new ItemViewHolder(rowView);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(final ItemViewHolder holder, int position) {

        SimpleDateFormat format = new SimpleDateFormat("dd MMMM yyyy");
        final mPromo selectedCustomer = filteredUserList.get(position);
        if(selectedCustomer != null) {



            holder.promoname.setText(selectedCustomer.getPromoName());
            holder.bonusqty.setText(String.valueOf(selectedCustomer.getQtyBonus()));

            /*holder.promoname.setText(selectedCustomer.getPromoName());
            holder.produk.setText(String.valueOf(selectedCustomer.getProductId()).concat("-").concat(selectedCustomer.getProductName()));
            holder.minQty.setText(String.valueOf(selectedCustomer.getMinQty()).concat(" ").concat(selectedCustomer.getUnitId()));
            holder.qtyKelipatan.setText(String.valueOf(selectedCustomer.getMultiplyQty()).concat(" ").concat(selectedCustomer.getUnitId()));
            holder.bonuspoduk.setText(String.valueOf(selectedCustomer.getProductIdBonus()).concat("-").concat(selectedCustomer.getProductBonusName()));
            holder.bonusqty.setText(String.valueOf(selectedCustomer.getQtyBonus()));
            holder.bonusunit.setText(selectedCustomer.getUnitIdBonus());
            String tglmulai = format.format(getTanggal(selectedCustomer.getStartDate()).getTime());
            String tglselesai = format.format(getTanggal(selectedCustomer.getEndDate()).getTime());
            holder.stardate.setText(tglmulai);
            holder.enddate.setText(tglselesai);
            holder.typepromo.setText(selectedCustomer.getCustName());
            holder.dist.setText("Dist:" + selectedCustomer.getDistName());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                /*v.setBackgroundColor(Color.GREEN);
                Intent inten = new Intent(mContext, PoNewActivity.class);
                inten.putExtra(PoNewActivity.sessionUser, sessionUser);
                inten.putExtra(PoNewActivity.sessionPO, selectedCustomer);
                inten.putExtra(PoNewActivity.sessionSalesConfirm, true);
                inten.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.getApplicationContext().startActivity(inten);
                }
            });*/
        }

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

    public mPromo getItem(int position) {
        return filteredUserList.get(position);
    }

    public List<mPromo> getAllItem() {
        return mPO;
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder implements
            ItemTouchHelperViewHolder {
        TextView promoname;
        TextView bonusqty;
        TextView produk, minQty, qtyKelipatan, bonuspoduk, bonusunit, stardate, enddate, typepromo,dist;

        public ItemViewHolder(View itemView) {
            super(itemView);
            promoname = (TextView) itemView.findViewById(R.id.promoname);
            //produk = (TextView) itemView.findViewById(R.id.produk);
            //minQty = (TextView) itemView.findViewById(R.id.minQty);
            //qtyKelipatan = (TextView) itemView.findViewById(R.id.qtyKelipatan);
            //bonuspoduk = (TextView) itemView.findViewById(R.id.bonuspoduk);
            bonusqty = (TextView) itemView.findViewById(R.id.bonusqty);
            //bonusunit = (TextView) itemView.findViewById(R.id.bonusunit);
            //stardate = (TextView) itemView.findViewById(R.id.stardate);
            //enddate = (TextView) itemView.findViewById(R.id.enddate);
            //typepromo = (TextView) itemView.findViewById(R.id.typepromo);
            //dist=(TextView) itemView.findViewById(R.id.dist);


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

        private final PromoListAdapter adapter;

        private final List<mPromo> originalList;

        private final List<mPromo> filteredList;

        private UserFilter(PromoListAdapter adapter, List<mPromo> originalList) {
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

                for (final mPromo user : originalList) {
                    if (user.getPromoName() != null) {
                        if (user.getPromoName().toLowerCase().contains(filterPattern) || user.getCustName().toLowerCase().contains(filterPattern) || user.getPromoName().toLowerCase().contains(filterPattern)) {
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
            adapter.filteredUserList.addAll((ArrayList<mPromo>) results.values);
            adapter.notifyDataSetChanged();
        }
    }


}
