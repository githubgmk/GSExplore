package com.gmk.geisa.activities.support;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.gmk.geisa.R;
import com.gmk.geisa.activities.main.MainActivity;

import java.util.Locale;

public class SupportActivity extends AppCompatActivity {
    ImageView sphoneimage,semailimage,salamatimage,swebimage,stoeimage;
    TextView sversi;
    Button scekupdate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support);
        setTitle("Support");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        sphoneimage=(ImageView) findViewById(R.id.sphoneimage);
        semailimage=(ImageView) findViewById(R.id.semailimage);
        salamatimage=(ImageView) findViewById(R.id.salamatimage);
        swebimage=(ImageView) findViewById(R.id.swebimage);
        stoeimage=(ImageView) findViewById(R.id.stoeimage);
        scekupdate=(Button) findViewById(R.id.scekupdate);
        sversi=(TextView) findViewById(R.id.sversi);

        //String versi=BuildConfig.VERSION_NAME;
        //PackageInfo pinfo = getApplicationInfo(). getPackageManager().getPackageInfo(getPackageName(), 0);
        //String sVersionCode = pinfo.versionCode; // 1
        //String sVersionName = pinfo.versionName; // 1.0
        PackageManager manager = getApplicationContext().getPackageManager();
        PackageInfo info = null;
        String version="";
        try {
            info = manager.getPackageInfo(
                    getApplicationContext().getPackageName(), 0);
            version=info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        sversi.setText(getString(R.string.app_name) +" v."+ version);

        sphoneimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:+628111208959"));
                startActivity(intent);
            }
        });
        semailimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent testIntent = new Intent(Intent.ACTION_VIEW);
                Uri data = Uri.parse("mailto:?to=" + "fiqri.putra@gandummas.co.id");
                testIntent.setData(data);
                startActivity(testIntent);
            }
        });
        salamatimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float latitude=0,longitude=0;
                String uri = String.format(Locale.ENGLISH, "geo:%f,%f", latitude, longitude);
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                startActivity(intent);
            }
        });
        swebimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://www.gandummas.co.id";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
        stoeimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://www.gandummas.co.id";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
        scekupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "market://details?id="+getApplication().getApplicationContext().getPackageName();
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            //getFragmentManager().popBackStack();
            //finish();
            Intent inten = new Intent(this, MainActivity.class);//berpindah ke activity yang lain dgn data
            startActivityForResult(inten, 1);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
