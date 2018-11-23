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
import android.widget.TextView;

import com.gmk.geisa.R;
import com.gmk.geisa.activities.rofo.RofoMonthActivity;
import com.gmk.geisa.helper.ItemTouchHelperViewHolder;
import com.gmk.geisa.model.mRofoAktualisasi;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Kenjin on 07/23/2017.
 */
public class RofoSummaryLineListAdapter extends RecyclerView.Adapter<RofoSummaryLineListAdapter.ItemViewHolder> {

    private List<mRofoAktualisasi> mProductPrice;
    //private List<mCustomer> mCustomersReal;
    private final List<mRofoAktualisasi> filteredUserList;

    private Context mContext;

    private TextView mTextView, mTextViewppn, mTextViewttl;
    private int totalselected, totalBefore;
    private boolean isPP = false;

    public RofoSummaryLineListAdapter(List<mRofoAktualisasi> customers, Context context, TextView tv, TextView tvppn, TextView tvttl) {
        mProductPrice = customers;
        // mCustomersReal=customers;
        mContext = context;
        mTextView = tv;
        mTextViewppn = tvppn;
        mTextViewttl = tvttl;
        filteredUserList = new ArrayList<>();
        for (mRofoAktualisasi cu : customers) {
            filteredUserList.add(cu);
        }

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
        View rowView = LayoutInflater.from(parent.getContext()).inflate(R.layout.rofo_aktual_list, parent, false);
        ItemViewHolder viewHolder = new ItemViewHolder(rowView);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(final ItemViewHolder holder, int position) {
        NumberFormat nf = NumberFormat.getInstance(Locale.GERMAN);
        final mRofoAktualisasi selectedCustomer = filteredUserList.get(position);
        DecimalFormat df = new DecimalFormat("#0.00");
        //holder.rofoMonth.setText(String.valueOf(selectedCustomer.getYear()));
        holder.rofoMonth.setText(selectedCustomer.getMonth() + ":" + selectedCustomer.getMonthName());
        String nilai = nf.format(Math.round(selectedCustomer.getValueTarget()));
        holder.rofoTarget.setText(nilai);
        String nilai1 = nf.format(Math.round(selectedCustomer.getValueRofo()));
        //Log.e("isi actual",selectedCustomer.getMonthName()+"rofo"+selectedCustomer.getValueRofo()+","+nilai1);
        holder.rofoRofo.setText(nilai1);
        String nilai10 = nf.format(Math.round(selectedCustomer.getValueRofoDraft()));
        holder.rofoRofoDraft.setText(nilai10);
        String nilai2 = nf.format(Math.round(selectedCustomer.getValueSales()));
        //Log.e("isi actual",selectedCustomer.getMonthName()+"actual"+selectedCustomer.getValueSales()+","+nilai2);
        holder.rofoActual.setText(nilai2);
        double hsl = selectedCustomer.getValueRofo() / selectedCustomer.getValueTarget() *100;
        double nilairofo=(Double.isNaN(hsl)|| Double.isInfinite(hsl)?0:hsl);
        String angleFormated = df.format(nilairofo);
        holder.rofoRofoTarget.setText(angleFormated);
        double hsl1 = selectedCustomer.getValueSales() / selectedCustomer.getValueRofo() *100;
        double nilairofo1=(Double.isNaN(hsl1)|| Double.isInfinite(hsl1)?0:hsl1);
        String angleFormated1 = df.format(nilairofo1);
        holder.rofoRofoActual.setText(angleFormated1);
        double hsl2 = selectedCustomer.getValueSales() / selectedCustomer.getValueTarget() *100;
        double nilairofo2=(Double.isNaN(hsl2)|| Double.isInfinite(hsl2)?0:hsl2);
        String angleFormated2 = df.format(nilairofo2);
        holder.rofoActualTarget.setText(angleFormated2);
        holder.itemView.setBackgroundColor(Color.TRANSPARENT);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //v.setBackgroundColor(Color.GREEN);
                Intent inten = new Intent(mContext, RofoMonthActivity.class);
               // Log.e(" isi mau cek",selectedCustomer.getMonth()+","+selectedCustomer.getMonthName()+","+selectedCustomer.getYear());
                inten.putExtra(RofoMonthActivity.sessionMonth, selectedCustomer.getMonth());
                inten.putExtra(RofoMonthActivity.sessionMonthName, selectedCustomer.getMonthName());
                inten.putExtra(RofoMonthActivity.sessionYear, selectedCustomer.getYear());
                inten.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.getApplicationContext().startActivity(inten);
                //v.setBackgroundColor(Color.TRANSPARENT);

            }
        });


    }

    public void hitungHarga(ArrayList<mRofoAktualisasi> po) {
        double jumlahtotal = 0, ppn = 0, total = 0;
        for (mRofoAktualisasi cu : po) {

        }
        NumberFormat nf = NumberFormat.getInstance(Locale.GERMAN);
        String nilai = nf.format(Math.round(jumlahtotal));
        ppn = jumlahtotal * 0.1;
        String ppnnilai = nf.format(Math.round(ppn));
        total = jumlahtotal + ppn;
        String ttlnilai = nf.format(Math.round(total));
        mTextView.setText(nilai);
        mTextViewppn.setText(ppnnilai);
        mTextViewttl.setText(ttlnilai);
    }


    @Override
    public int getItemCount() {
        return filteredUserList.size();
    }

    public mRofoAktualisasi getItem(int position) {
        return filteredUserList.get(position);
    }

    public List<mRofoAktualisasi> getAllItem() {
        return mProductPrice;
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder implements
            ItemTouchHelperViewHolder {
        public final TextView rofoMonth, rofoTarget, rofoRofo,rofoRofoDraft, rofoActual, rofoRofoTarget, rofoRofoActual,rofoActualTarget;//, satuan, qty, unit;
        // public final ImageView handleView;

        public ItemViewHolder(View itemView) {
            super(itemView);
            rofoMonth = (TextView) itemView.findViewById(R.id.rofoMonth);
            rofoTarget = (TextView) itemView.findViewById(R.id.rofoTarget);
            rofoRofo = (TextView) itemView.findViewById(R.id.rofoRofo);
            rofoRofoDraft = (TextView) itemView.findViewById(R.id.rofoRofoDraft);
            rofoActual = (TextView) itemView.findViewById(R.id.rofoActual);
            rofoRofoTarget = (TextView) itemView.findViewById(R.id.rofoRofoTarget);
            rofoRofoActual = (TextView) itemView.findViewById(R.id.rofoRofoActual);
            rofoActualTarget=(TextView) itemView.findViewById(R.id.rofoActualTarget);
            /*satuan = (TextView) itemView.findViewById(R.id.satuan);
            qty = (TextView) itemView.findViewById(R.id.qty);
            unit = (TextView) itemView.findViewById(R.id.unit);
            handleView = (ImageView) itemView.findViewById(R.id.handle);*/


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


}
