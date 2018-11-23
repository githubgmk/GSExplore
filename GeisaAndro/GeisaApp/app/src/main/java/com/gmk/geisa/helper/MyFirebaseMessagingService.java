package com.gmk.geisa.helper;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.gmk.geisa.R;
import com.gmk.geisa.activities.main.MainActivity;
import com.gmk.geisa.activities.personal.PesanActivity;
import com.gmk.geisa.databases.mDB;
import com.gmk.geisa.model.mPesan;

/**
 * Created by Snow on 5/31/2016.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private mDB data;
    private mPesan pesan;

    private static final String TAG = "MyFirebaseMsgService";

    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
    // [START receive_message]
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // TODO(developer): Handle FCM messages here.
        // If the application is in the foreground handle both data and notification messages here.
        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
       // Log.e(TAG, "From: " + remoteMessage.getFrom());
       // Log.e(TAG, "Notification Message Body: " + remoteMessage.getNotification().getBody());
       // Log.e(TAG, "Notification Message Tag: " + remoteMessage.getNotification().getTag());
        //String msg = remoteMessage.getNotification().getBody();
        String _id = "", judul = "", penerima = "", pengirim = "", isipesan = "", typepesan = "", idpenerima = "", idpengirim = "", dateread = "", datesend = "", fcmid = "", statuspesan = "",refid="";
        /*Map<String, String> dari = remoteMessage.getData();

        // Iterator myVeryOwnIterator = dari.keySet().iterator();
        for (Map.Entry<String, String> entry : dari.entrySet()) {
            // Log.e("isi map", entry.getKey() + "/" + entry.getValue());
            if (entry.getKey().equalsIgnoreCase("id")) {
                _id = entry.getValue();
            } else if (entry.getKey().equalsIgnoreCase("judul")) {
                judul = entry.getValue();
            } else if (entry.getKey().equalsIgnoreCase("penerima")) {
                penerima = entry.getValue();
            } else if (entry.getKey().equalsIgnoreCase("pengirim")) {
                pengirim = entry.getValue();
            } else if (entry.getKey().equalsIgnoreCase("isipesan")) {
                isipesan = entry.getValue();
            } else if (entry.getKey().equalsIgnoreCase("typepesan")) {
                typepesan = entry.getValue();
            } else if (entry.getKey().equalsIgnoreCase("idpenerima")) {
                idpenerima = entry.getValue();
            } else if (entry.getKey().equalsIgnoreCase("idpengirim")) {
                idpengirim = entry.getValue();
            } else if (entry.getKey().equalsIgnoreCase("dateread")) {
                dateread = entry.getValue();
            } else if (entry.getKey().equalsIgnoreCase("datesend")) {
                datesend = entry.getValue();
            } else if (entry.getKey().equalsIgnoreCase("fcmid")) {
                fcmid = entry.getValue();
            } else if (entry.getKey().equalsIgnoreCase("statuspesan")) {
                statuspesan = entry.getValue();
            } else if (entry.getKey().equalsIgnoreCase("refid")) {
                refid = entry.getValue();
            }
        }*/

        data = mDB.getInstance(this);
        pesan = new mPesan(Long.valueOf(_id), Integer.valueOf(idpengirim), pengirim, Integer.valueOf(idpenerima), penerima, judul, isipesan, fcmid, typepesan, datesend, dateread, statuspesan, refid, false);
        data.insertUpdatePesan(pesan);
        sendNotification(isipesan, judul);
    }
    // [END receive_message]

    /**
     * Create and show a simple notification containing the received FCM message.
     *
     * @param messageBody FCM message body received.
     */
    private void sendNotification(String messageBody, String judul) {
        Intent intent = new Intent(this, PesanActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra(MainActivity.messagingfromserver, messageBody);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 99 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);
        Resources appR = this.getResources();
        CharSequence txt = appR.getText(appR.getIdentifier("app_name",
                "string", this.getPackageName()));
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(txt)
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(99 /* ID of notification */, notificationBuilder.build());
    }
}