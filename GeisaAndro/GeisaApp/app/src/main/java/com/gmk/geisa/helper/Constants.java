package com.gmk.geisa.helper;

import android.location.Location;
import android.text.TextUtils;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.gmk.geisa.model.mCustomer;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Belal on 5/29/2016.
 */
public class Constants {
    public static final String SHARED_PREF_NAME = "simplifiedcodingchat";
    public static final String USER_ID = "userid";
    public static final String USER_NAME = "username";
    public static final String USER_EMAIL= "useremail";
    public static final String IS_LOGGED_IN= "is_logged_in";
    public static final String PUSH_NOTIFICATION = "pushnotification";
    public static final int NOTIFICATION_ID = 235;
    /*public static final String APP_URL = "http://api.geisaforce.com/gsgmk313com/";*/
    public static final String APP_URL = "http://api.geisaforce.com/testAPI/";//untuk testing
    /*public static final String APP_URL = "http://api.geisaforce.com/GeisaWebAndroid/";*/
      public static final String APP_URL_SERVER = "api.geisaforce.com";
      public static final String APP_IP_SERVER = "api.geisaforce.com";
    /* public static final String APP_URL = "http://10.10.10.163/GeisaWebAndroid/";
     public static final String APP_URL_SERVER = "10.10.10.163";
     public static final String APP_IP_SERVER = "10.10.10.163";
   /* public static final String APP_URL = "http://192.168.43.254/GeisaWebAndroid/";
     public static final String APP_URL_SERVER = "192.168.43.254";
     public static final String APP_IP_SERVER = "192.168.43.254";
   /* public static final String APP_URL = "http://10.211.55.3/GeisaWebAndroid/";
    public static final String APP_URL_SERVER = "10.211.55.3";
    public static final String APP_IP_SERVER = "10.211.55.3";*/
    /*public static final String BACKUP_URL = "http://backup.geisaforce.com/gsgmk313com/";*/
    public static final String BACKUP_URL = "http://api.geisaforce.com/testAPI/";//sementara
    /*public static final String BACKUP_URL = "http://backup.geisaforce.com/GeisaWebAndroid/";*/
    public static final String REG_URL = "http://api.geisaforce.com/serial/";

    public static double CalculateBetweenPoint(LatLng myPosition, LatLng customer) {
        Location mylocation = new Location("");
        Location dest_location = new Location("");
        mylocation.setLatitude(myPosition.latitude);
        mylocation.setLongitude(myPosition.longitude);
        dest_location.setLatitude(customer.latitude);
        dest_location.setLongitude(customer.longitude);
        return mylocation.distanceTo(dest_location);
    }

    public interface ACTION {
        public static final String STARTFOREGROUND_ACTION = " com.gmk.geisa.action.startforeground";
        public static final String STARTFOREGROUND_ACTION_MESSAGE = " com.gmk.geisa.action.startforegroundmessage";
        public static final int FOREGROUND_SERVICE = 1021;
    }

    public static final boolean cekTanggalHariCallPlan(String tanggal){
        Calendar tgldpt = Calendar.getInstance();
        Calendar now=Calendar.getInstance();
        boolean boleh=false;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        if (!TextUtils.isEmpty(tanggal) && !tanggal.equalsIgnoreCase("null")) {
            try {
                Date dates = format.parse(tanggal);
                // Log.e("isi start", date.toString());
                tgldpt.setTime(dates);
                if(!tgldpt.after(now)){
                    boleh=true;
                }
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                boleh = false;
                e.printStackTrace();
            }
        } else {
            boleh = false;
        }
        return  boleh;
    }

    public static  String getBulanString(int month){
        String bulan="Januari";
        switch (month){
            case 1:
                bulan="Januari";
                break;
            case 2:
                bulan="Pebruari";
                break;
            case 3:
                bulan="Maret";
                break;
            case 4:
                bulan="April";
                break;
            case 5:
                bulan="Mei";
                break;
            case 6:
                bulan="Juni";
                break;
            case 7:
                bulan="Juli";
                break;
            case 8:
                bulan="Agustus";
                break;
            case 9:
                bulan="September";
                break;
            case 10:
                bulan="Oktober";
                break;
            case 11:
                bulan="November";
                break;
            case 12:
                bulan="Desember";
                break;
        }
        return bulan;
    }

    public static double CalculationByDistance(LatLng StartP, LatLng EndP) {
        int Radius = 6371;// radius of earth in Km
        double lat1 = StartP.latitude;
        double lat2 = EndP.latitude;
        double lon1 = StartP.longitude;
        double lon2 = EndP.longitude;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1))
                * Math.cos(Math.toRadians(lat2)) * Math.sin(dLon / 2)
                * Math.sin(dLon / 2);
        double c = 2 * Math.asin(Math.sqrt(a));
        double valueResult = Radius * c;
        double km = valueResult / 1;
        DecimalFormat newFormat = new DecimalFormat("####");
        DecimalFormat newFormat1 = new DecimalFormat("#.###");
        int kmInDec = Integer.valueOf(newFormat.format(km));
        double meter = km / 1000;
        int meterInDec = Integer.valueOf(newFormat.format(meter));
        Log.e("Radius Value", "value=" + valueResult + "   KM=" + kmInDec
                + " Meter=" + meterInDec);
        //return Double.valueOf(newFormat1.format(valueResult));
        return Double.valueOf(valueResult);
    }

}
