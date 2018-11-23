package com.gmk.geisa.Class;

import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by kenjin on 11/6/2015.
 */
public class Konversi {

    public  String DateToString(Date data){
        String reportDate="";
        DateFormat df = new SimpleDateFormat("dd MMMM yyyy");
        if(data!=null){
            reportDate = df.format(data);
        }
        return reportDate;
    }
    public String DataToString(String data){
        String hasil="";
        if(data!=null){
            hasil=String.valueOf(data);
        }
        return hasil;
    }
    public  String DataToString(Integer data){
        String hasil="";
        if(data!=null){
            hasil=String.valueOf(data);
        }
        return hasil;
    }

    public  Date StringToDateTime(String data){
        Date d=null;

        SimpleDateFormat smp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat smp2 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat smp4 = new SimpleDateFormat("yyyy/MM/dd");
        SimpleDateFormat smp1 = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        SimpleDateFormat smp3 = new SimpleDateFormat("dd-MM-yyyy");
        try {
             d = smp.parse(data);
        } catch (ParseException ex) {
            try {
                d = smp1.parse(data);
            } catch (ParseException e) {
                try {
                    d = smp2.parse(data);
                } catch (ParseException er) {
                    try {
                        d = smp3.parse(data);
                    } catch (ParseException ers) {
                        try {
                            d = smp4.parse(data);
                        } catch (ParseException erss) {
                            Log.e("err konversi","err"+erss);
                        }
                    }
                }
            }
        }
        return  d;
    }
}
