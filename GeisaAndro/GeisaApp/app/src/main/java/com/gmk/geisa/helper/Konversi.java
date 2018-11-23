package com.gmk.geisa.helper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

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
}
