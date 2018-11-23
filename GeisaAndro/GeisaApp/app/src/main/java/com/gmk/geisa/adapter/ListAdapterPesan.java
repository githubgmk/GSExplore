package com.gmk.geisa.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gmk.geisa.R;
import com.gmk.geisa.databases.mDB;
import com.gmk.geisa.model.mPesan;
import com.gmk.geisa.model.mSession;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by kenjin on 8/10/2016.
 */
public class ListAdapterPesan extends BaseAdapter {
    Context mContext;
    LayoutInflater inflater;
    private List<mPesan> worldpopulationlist = null;
    private ArrayList<mPesan> arraylist;
    private mDB datas;
    Calendar cal = Calendar.getInstance();
    SimpleDateFormat smp = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss");
    SimpleDateFormat smp1 = new SimpleDateFormat(
            "yyyy-MM-dd");
    SimpleDateFormat smp2 = new SimpleDateFormat(
            "dd MMM yyyy");
    String tanggal;
    Date sekarang;
    mSession session;

    public ListAdapterPesan(Context context, List<mPesan> worldpopulationlist) {
        mContext = context;
        this.worldpopulationlist = worldpopulationlist;
        inflater = LayoutInflater.from(mContext);
        this.arraylist = new ArrayList<>();
        this.arraylist.addAll(worldpopulationlist);
        //datas=mDB.getInstance(context);
        tanggal = smp1.format(cal.getTime());
    }


    @Override
    public int getCount() {
        return worldpopulationlist.size();
    }

    @Override
    public mPesan getItem(int position) {
        return worldpopulationlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @SuppressLint("DefaultLocale")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //final ViewHolder holder;
        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(mContext);
            v = vi.inflate(R.layout.pesan_list_status, null);
        }

        mPesan p = getItem(position);

        if (p != null) {
            TextView nama = (TextView) v.findViewById(R.id.pesanpengirim);
            TextView pesanjudul = (TextView) v.findViewById(R.id.pesanjudul);
            TextView pesanisi = (TextView) v.findViewById(R.id.pesanisi);
            TextView tgl = (TextView) v.findViewById(R.id.pesantanggal);
            ImageView imgv = (ImageView) v.findViewById(R.id.pesanImage);


            int warnastatus = Color.BLACK;
            int imagestatus = R.drawable.ic_avatar;
            float nilai;

            if (nama != null) {
                nama.setText(p.getPengirim().toUpperCase());
            }
            if (pesanjudul != null) {
                pesanjudul.setText(p.getJudul());
            }
            if (pesanisi != null) {
                pesanisi.setText(p.getIsiPesan());
            }
            if (tgl != null) {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                try {
                    Date date = format.parse(p.getDateSend());
                    System.out.println(date);
                    tgl.setText(smp2.format(date));
                } catch (ParseException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            if (imgv != null) {
                imgv.setImageResource(imagestatus);
            }
            //Log.e("isi statuspean",p.getId()+":"+ p.getStatusPesan() + "," + p.getIsiPesan()+",");
            if (p.getStatusPesan().toLowerCase().equalsIgnoreCase("new")) {
                nama.setTextColor(Color.RED);
            } else if(p.isNewMessage()) {
                nama.setTextColor(Color.RED);
            }else{
                nama.setTextColor(Color.BLACK);
            }

        }

        return v;
    }

    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        worldpopulationlist.clear();
        if (charText.length() == 0) {
            worldpopulationlist.addAll(arraylist);
        } else {
            if(arraylist!=null && ( arraylist.size()>0)) {
                for (mPesan wp : arraylist) {
                    if (wp.getPengirim().toLowerCase(Locale.getDefault()).contains(charText) || wp.getJudul().toLowerCase(Locale.getDefault()).contains(charText)) {
                        worldpopulationlist.add(wp);
                    }
                }
            }
        }
        notifyDataSetChanged();
    }
}
