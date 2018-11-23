package com.gmk.geisa.activities.main;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.gmk.geisa.databases.mDB;


public class SplashActivity extends AppCompatActivity {
    private UserLoginTask mAuthTask = null;
    private mDB data;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        data = mDB.getInstance(this);
        mAuthTask = new UserLoginTask();
        mAuthTask.execute((Void) null);

    }


    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {



        UserLoginTask() {
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.
            boolean hasil = false;

            if (data.getSessionByStatus("login",1)) {
               // Log.e("isi login",login.getUserName()+","+login.getUserPass());
                hasil=true;
            } else {
                hasil=false;
            }

            // TODO: register the new account here.
            return hasil;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;

            if (success) {
                //add jika dia ada active call plan
                Intent inten = new Intent(SplashActivity.this, MainActivity.class);//berpindah ke activity yang lain dgn data
                //inten.putExtra(MainActivity.session,login);
                //inten.putExtra(CustomerActivity.KEY_DATA_NUMBER,123);
                //startActivity(inte);
                startActivityForResult(inten, 1);
                finish();
            } else {
                Intent inten = new Intent(SplashActivity.this, LoginActivity.class);//berpindah ke activity yang lain dgn data
                //inten.putExtra(MainActivity.session,login);
                //inten.putExtra(CustomerActivity.KEY_DATA_NUMBER,123);
                //startActivity(inte);
                startActivityForResult(inten, 1);
                finish();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
        }
    }
}
