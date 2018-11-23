package com.gmk.geisa.activities.Promo1;

import android.os.AsyncTask;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import android.widget.Toast;

import com.gmk.geisa.R;
import com.gmk.geisa.adapter.PromoListAdapter;

import com.gmk.geisa.controller.PromoController;
import com.gmk.geisa.model.mPromo;
import com.gmk.geisa.model.mSession;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class Promo1Activity extends AppCompatActivity {

    int SalesId;
    //private List<Promo> promoList = new ArrayList<>();
    private RecyclerView recyclerView;
    //private PromoAdapter pAdapter;

    private CustomAdapter adapter;
    private List<MyData> data_list;
    private mSession session;

    private RecyclerView.LayoutManager mLayoutManager;

    public final static String sessionUser = "sessionUser";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Promo GEISA");
        setContentView(R.layout.activity_promo1);



        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        data_list = new ArrayList<>();
        load_data_from_server(SalesId);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        adapter = new CustomAdapter(this,data_list);
        recyclerView.setAdapter(adapter);

        //pAdapter = new PromoAdapter(promoList);
        //RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        //recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        //recyclerView.setAdapter(pAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        session = (mSession) getIntent().getSerializableExtra(sessionUser);
        SalesId = session.getId();

        // set the adapter
        //recyclerView.setAdapter(pAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                MyData myData = data_list.get(position);
                Toast.makeText(getApplicationContext(), myData.getPromoName() + " is selected!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        //setupRecyclerView();

        //preparePromoData();
    }

    private void load_data_from_server(final int id) {

        AsyncTask<Integer,Void,Void> task = new AsyncTask<Integer, Void, Void>() {
            @Override
            protected Void doInBackground(Integer... integers) {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url("http://api.geisaforce.com/testAPI/api/Master/GetPromo?salesId="+id)
                        .build();
                try {
                    Response response = client.newCall(request).execute();

                    JSONArray array = new JSONArray(response.body().string());
                    for(int i=0;i<array.length();i++){

                        JSONObject object =array.getJSONObject(i);

                        MyData data = new MyData(object.getInt("PromoId"),object.getString("PromoName"),object.getString("EndDate"));

                        data_list.add(data);

                    }


                } catch (IOException e){
                    e.printStackTrace();
                } catch (JSONException e) {
                    System.out.println("End of Promo");
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                adapter.notifyDataSetChanged();
            }
        };
        task.execute(id);
    }



    /*private void preparePromoData() {
            Promo promo = new Promo("Buy 1 Get 1", "7", "2019-01-01");
            promoList.add(promo);

            promo = new Promo("Buy 1 Get 2", "9", "2019-01-01");
            promoList.add(promo);

            promo = new Promo("ayo di beli jangan lupa", "10", "2019-10-23");
            promoList.add(promo);

            promo = new Promo("Buy1Get1000", "11", "2019-01-01");
            promoList.add(promo);

            promo = new Promo("testpromo", "12", "2019-01-01");
            promoList.add(promo);

            /*promo = new Promo("Mission: Impossible Rogue Nation", "Action", "2015");
            promoList.add(promo);

            promo = new Promo("Up", "Animation", "2009");
            promoList.add(promo);

            promo = new Promo("Star Trek", "Science Fiction", "2009");
            promoList.add(promo);

            promo = new Promo("The LEGO Movie", "Animation", "2014");
            promoList.add(promo);

            promo = new Promo("Iron Man", "Action & Adventure", "2008");
            promoList.add(promo);

            promo = new Promo("Aliens", "Science Fiction", "1986");
            promoList.add(promo);

            promo = new Promo("Chicken Run", "Animation", "2000");
            promoList.add(promo);

            promo = new Promo("Back to the Future", "Science Fiction", "1985");
            promoList.add(promo);

            promo = new Promo("Raiders of the Lost Ark", "Action & Adventure", "1981");
            promoList.add(promo);

            promo = new Promo("Goldfinger", "Action & Adventure", "1965");
            promoList.add(promo);

            promo = new Promo("Guardians of the Galaxy", "Science Fiction & Fantasy", "2014");
            promoList.add(promo);*/

            //pAdapter.notifyDataSetChanged();
        //}

    /*private void setupRecyclerView() {

        ArrayList<mPromo> promo=promoController.getAllPromoFromServer();
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        adapter1 = new PromoListAdapter(promo, this,session);
        recyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this)
                .colorResId(R.color.colorPrimaryDark)
                .size(2)
                .build());
        recyclerView.setAdapter(adapter1);
        this.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        }*/

    }






