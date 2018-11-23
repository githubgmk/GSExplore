package com.gmk.geisa.helper;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.gmk.geisa.Class.SyncDataClass;
import com.gmk.geisa.R;
import com.gmk.geisa.activities.main.MainActivity;
import com.gmk.geisa.databases.mDB;

import java.util.Calendar;

/**
 * Created by kenjin on 9/23/2016.
 */
public class ForegroundService extends Service {
    private static final String LOG_TAG = "ForegroundService";
    Context ctx;
    private static final long milMinute = 60000L;
    private SyncDataClass syncdata;
    private mDB data;
    Enkrip enkrip;
    @Override
    public void onCreate() {
        super.onCreate();
       // Calendar calendar = Calendar.getInstance();
        //Toast.makeText(getApplicationContext(), "Service dicreate pertama"+ calendar.getTime(), Toast.LENGTH_LONG).show();
        ctx=this;
        syncdata = new SyncDataClass(this);
        data = mDB.getInstance(this);
        Calendar calSet = Calendar.getInstance();
        Intent intent = new Intent(this, ForegroundService.class);
        intent.setAction(Constants.ACTION.STARTFOREGROUND_ACTION_MESSAGE);
        PendingIntent pintent = PendingIntent.getService(
                getApplicationContext(), 0, intent, 0);
        AlarmManager alarm = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        calSet.setTimeInMillis(System.currentTimeMillis());
        alarm.setRepeating(AlarmManager.RTC_WAKEUP, calSet.getTimeInMillis(),
                15 * milMinute, pintent);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        ctx=this;
        //Toast.makeText(getApplicationContext(), "Service Task started ", Toast.LENGTH_LONG).show();
        if(intent!=null && (intent.getAction()!=null)){
            if (intent.getAction().equals(Constants.ACTION.STARTFOREGROUND_ACTION)) {
               // Log.e(LOG_TAG, "Received Start Foreground Intent ");
                Intent notificationIntent = new Intent(this, MainActivity.class);
                //notificationIntent.setAction(Constants.ACTION.MAIN_ACTION);
                notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                        | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                        notificationIntent, 0);

                Bitmap icon = BitmapFactory.decodeResource(getResources(),
                        R.mipmap.ic_launcher);
                Resources appR = this.getResources();
                CharSequence txt = appR.getText(appR.getIdentifier("app_name",
                        "string", this.getPackageName()));
                NotificationCompat.Builder notification = new NotificationCompat.Builder(this)
                        .setContentTitle(txt)
                        .setTicker("Foreground Service")
                        .setContentText(txt)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setLargeIcon(Bitmap.createScaledBitmap(icon, 128, 128, false))
                        .setContentIntent(pendingIntent)
                        .setOngoing(true);

                startForeground(Constants.ACTION.FOREGROUND_SERVICE, notification.build());

            }else if(intent.getAction().equals( Constants.ACTION.STARTFOREGROUND_ACTION_MESSAGE)){
                new AsyncGetData().execute();
               /* Calendar tgl=Calendar.getInstance();
                SimpanLog log=new SimpanLog("1010");
                log.simpan("\nThis is a test message from stick broadcast !"+tgl.getTime());
                Toast.makeText(getApplicationContext(), "Service Task evry 5 Minutes", Toast.LENGTH_LONG).show();*/
            }
        }else{
            new AsyncGetData().execute();
           /* Calendar tgl=Calendar.getInstance();
            SimpanLog log=new SimpanLog("1010");
            log.simpan("\nThis is a test message from stick broadcast !"+tgl.getTime());
            Toast.makeText(getApplicationContext(), "Service Task evry 5 Minutes "+tgl.getTime(), Toast.LENGTH_LONG).show();*/
        }

        return Service.START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
       //  Toast.makeText(getApplicationContext(), "Service Task destroyet", Toast.LENGTH_LONG).show();
        Log.e(LOG_TAG, "In onDestroy");
    }

    @Override
    public IBinder onBind(Intent intent) {
        // Used only in case of bound services.
        return null;
    }
    @Override
    public void onTaskRemoved(Intent rootIntent){

       // Toast.makeText(getApplicationContext(), "Service Task removed truiton", Toast.LENGTH_LONG).show();
        super.onTaskRemoved(rootIntent);

        Intent dialogIntent = new Intent(this, MainActivity.class);
        dialogIntent.putExtra(MainActivity.sessiuser, true);
        //dialogIntent.setAction(Constants.ACTION.MAIN_ACTION);
        //dialogIntent.setAction(Constants.ACTION.STARTFOREGROUND_ACTION);
        dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        ctx.startActivity(dialogIntent);
/*
        Intent myIntent = new Intent(getApplicationContext(), ForegroundService.class);
        myIntent.setAction(Constants.ACTION.STARTFOREGROUND_ACTION);
        PendingIntent pendingIntent = PendingIntent.getService(getApplicationContext(), 0, myIntent, 0);

        AlarmManager alarmManager1 = (AlarmManager) getSystemService(ALARM_SERVICE);

        Calendar calendar = Calendar.getInstance();

        calendar.setTimeInMillis(System.currentTimeMillis());

        calendar.add(Calendar.SECOND, 10);

        alarmManager1.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);*/


    }
    public class AsyncGetData extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub

            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
        }

        @Override
        protected Void doInBackground(Void... params) {
            // TODO Auto-generated method stub
            ///mas_area/get_by_vars?id_bbws=1
            syncdata.cePendingData();
            return null;
        }
    }
}
