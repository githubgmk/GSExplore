package com.gmk.geisa.activities.main;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gmk.geisa.R;
import com.gmk.geisa.activities.Promo1.Promo1Activity;
import com.gmk.geisa.activities.callplan.CallPlanActivity;
import com.gmk.geisa.activities.callplan.visit.ComplainActivity;
import com.gmk.geisa.activities.callplan.visit.SurveyActivity;
import com.gmk.geisa.activities.callplan.visit.VisitCallPlan;
import com.gmk.geisa.activities.customer.CustomerActivity;
import com.gmk.geisa.activities.personal.PasswordActivity;
import com.gmk.geisa.activities.personal.PesanActivity;
import com.gmk.geisa.activities.po.PoConfirmActivity;
import com.gmk.geisa.activities.po.PoNewActivity;
import com.gmk.geisa.activities.product.ProductActivity;
import com.gmk.geisa.activities.report.ReportMain;
import com.gmk.geisa.activities.rofo.RofoActivity;
import com.gmk.geisa.activities.support.PetaActivity;
import com.gmk.geisa.activities.support.ResendActivity;
import com.gmk.geisa.activities.support.SupportActivity;
import com.gmk.geisa.activities.support.SyncActivity;
import com.gmk.geisa.adapter.MapDialogFragment;
import com.gmk.geisa.adapter.MapDialogFragmentNewMap;
import com.gmk.geisa.controller.OtherController;
import com.gmk.geisa.controller.SessionController;
import com.gmk.geisa.jsoup.AppVersion;
import com.gmk.geisa.jsoup.PlayStoreApi;
import com.gmk.geisa.jsoup.converter.JsoupConverterFactory;
import com.gmk.geisa.model.mCustomer;
import com.gmk.geisa.model.mPesan;
import com.gmk.geisa.model.mSession;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;
import okio.Utf8;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, MapDialogFragmentNewMap.EditDialogListener {
    private Button vBtnMainCst, vBtnMainPo, vBtnMainCall, vBtnMainTarget, vBtnMainPromo, vBtnMainReport, pbtnMainPromo;
    //Button btnStartStopWork;
    private FloatingActionButton fab;
    private ImageView imgLogoGMK;
    de.hdodenhof.circleimageview.CircleImageView idProfileImage;
    com.kyleduo.switchbutton.SwitchButton btnStartStopWork;
    private GridLayout gridLayout;
    private TextView idProfile, idProfileLevel;
    mSession session;
    boolean adaSession = false;
    SessionController sessionController;
    public final static String sessiuser = "sesiuser";
    public static String messagingfromserver;
    NavigationView navigationView;
    View headerLayout;
    boolean isWork = false, cancelStartStop = false;
    int countwork;
    OtherController otherController;

    //check permision

    @SuppressLint("InlinedApi")
    private static final String[] INITIAL_PERMS = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.CAMERA,
            Manifest.permission.CONTROL_LOCATION_UPDATES,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.SET_ALARM,
            Manifest.permission.PACKAGE_USAGE_STATS,
            Manifest.permission.KILL_BACKGROUND_PROCESSES

    };
    private static final String[] CAMERA_PERMS = {
            Manifest.permission.CAMERA
    };
    private static final String[] CONTACTS_PERMS = {
            Manifest.permission.READ_CONTACTS
    };
    private static final String[] LOCATION_PERMS = {
            Manifest.permission.ACCESS_FINE_LOCATION
    };
    private static final int INITIAL_REQUEST = 1337;
    private static final int CAMERA_REQUEST = INITIAL_REQUEST + 1;
    private static final int CONTACTS_REQUEST = INITIAL_REQUEST + 2;
    private static final int LOCATION_REQUEST = INITIAL_REQUEST + 3;

    //
    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imgLogoGMK =  findViewById(R.id.logoGMK);
        vBtnMainCst =  findViewById(R.id.btnMainCst);
        vBtnMainPo =  findViewById(R.id.btnMainPo);
        vBtnMainCall =  findViewById(R.id.btnMainCallPlan);
        vBtnMainTarget =  findViewById(R.id.btnMainTarget);
        pbtnMainPromo = findViewById(R.id.btnMainPromo);
        //vBtnMainPromo = (Button) findViewById(R.id.btnMainPromo);
        vBtnMainReport =  findViewById(R.id.btnMainReport);
        //vBtnMainCst.setBackgroundResource(R.drawable.btnMainCst);
        fab = (FloatingActionButton) findViewById(R.id.fab);

        gridLayout = (GridLayout) findViewById(R.id.mainmenu);
        sessionController = SessionController.getInstance(this);
        otherController = OtherController.getInstance(this);
        otherController.setCallBackGetDataPesan(callbackPesan);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        headerLayout = navigationView.getHeaderView(0);
        idProfile = (TextView) headerLayout.findViewById(R.id.idProfile);
        idProfileLevel = (TextView) headerLayout.findViewById(R.id.idProfileLevel);
        idProfileImage = (CircleImageView) headerLayout.findViewById(R.id.idProfileImage);


        cekVersion();
        cekSession();
        //requestPermissions(INITIAL_PERMS, INITIAL_REQUEST);
        if (!canAccessLocation() || !canAccessCamera() || !canKillBacground()) {
            requestPermissions(INITIAL_PERMS, INITIAL_REQUEST);
        }
        if (!canAccessCamera()) {
            requestPermissions(CAMERA_PERMS, CAMERA_REQUEST);
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

       /**/
        vBtnMainCst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inten = new Intent(MainActivity.this, CustomerActivity.class);//berpindah ke activity yang lain dgn data
                inten.putExtra(CustomerActivity.sessionUser, session);
                startActivityForResult(inten, 1);
            }
        });
        //btn main po
        vBtnMainPo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inten = new Intent(MainActivity.this, PoNewActivity.class);//berpindah ke activity yang lain dgn data
                inten.putExtra(PoNewActivity.sessionUser, session);
                startActivityForResult(inten, 2);
                //Toast.makeText(MainActivity.this, "Button PO Clicked", Toast.LENGTH_SHORT).show();
            }
        });
        //btn Call Plan
        vBtnMainCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inten = new Intent(MainActivity.this, CallPlanActivity.class);//berpindah ke activity yang lain dgn data
                inten.putExtra(CallPlanActivity.sessionUser, session);
                startActivityForResult(inten, 3);
                //Toast.makeText(MainActivity.this, "Button Call Plan Clicked", Toast.LENGTH_SHORT).show();
            }
        });
        //btn Target
        vBtnMainTarget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inten = new Intent(MainActivity.this, RofoActivity.class);
                inten.putExtra(RofoActivity.sessionUser, session);
                startActivityForResult(inten, 4);

            }
        });
        //btn Promo
        /*vBtnMainPromo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inten = new Intent(MainActivity.this, PromoActivity.class);
                inten.putExtra(PromoActivity.sessionUser, session);
                startActivityForResult(inten, 5);
            }
        });*/
        //btn Promo1
        pbtnMainPromo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inten = new Intent(MainActivity.this, Promo1Activity.class);
                inten.putExtra(Promo1Activity.sessionUser, session);
                startActivityForResult(inten, 5);
            }
        });
        //btn report
        vBtnMainReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inten = new Intent(MainActivity.this, ReportMain.class);
                inten.putExtra(ReportMain.sessionUser, session);
                mCustomer customer=null;
                inten.putExtra(ReportMain.sessionCust,customer);
                startActivityForResult(inten, 6);
            }
        });
        //end main menu button

        //fab
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(MainActivity.this,"tes",Toast.LENGTH_SHORT).show();
                Intent inten = new Intent(MainActivity.this, PesanActivity.class);
                inten.putExtra(PesanActivity.sessionUser, session);
                startActivity(inten);
            }
        });


    }

    OtherController.CallBackGetDataPesan callbackPesan = new OtherController.CallBackGetDataPesan() {
        @Override
        public void resultReady(ArrayList<mPesan> pesan, boolean hasil) {
            newMessage();
        }
    };

    private void newMessage() {
        int totalpesan = otherController.getPesanNew(session.getId());
        if (totalpesan > 0) {
            fab.setBackgroundTintList(ContextCompat.getColorStateList(MainActivity.this, R.color.colorAccent));
            fab.setImageResource(R.drawable.ic_inbox_new);
        } else {
            fab.setBackgroundTintList(ContextCompat.getColorStateList(MainActivity.this, R.color.aluminum));
            fab.setImageResource(R.drawable.ic_inbox);
        }
    }


    private void showEditDialog(String jenis) {
        // SupportMapFragment
        FragmentManager fm = getSupportFragmentManager();
        MapDialogFragmentNewMap df = new MapDialogFragmentNewMap(MainActivity.this, jenis);
       /*
        if (fm == null)
            fm = getSupportFragmentManager();
        if (df == null)
            df = new MapDialogFragment(MainActivity.this, jenis);*/
        df.show(fm, "fragment tab");


    }

    @Override
    protected void onResume() {
        super.onResume();
        //check untuk tanggal
        /*Calendar cal = Calendar.getInstance();
        SimpleDateFormat smp = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat smp1 = new SimpleDateFormat(
                "yyyy-MM-dd");
        SimpleDateFormat smp2 = new SimpleDateFormat(
                "yyyyMMddHHmmssSSS");
        String tanggal = smp.format(cal.getTime());
        String tanggalnya = smp1.format(cal.getTime());
        String idtgl =  smp2.format(cal.getTime());
        Log.e("error tgl","dtet:"+cal.getTime()+",tgl:"+tanggal+",tglnya:"+tanggalnya+",id:"+idtgl);*/

        cekSession();
        if (CekCallPlan()) {
            //Log.e("ada callplan","ada call plan");
            Intent inten = new Intent(MainActivity.this, VisitCallPlan.class);//berpindah ke activity yang lain dgn data
            startActivityForResult(inten, 7);
            //finish();
        } else {
            // Log.e("cek pesan","cek pesan");
            newMessage();
            otherController.getPesan(session.getId(), true);
        }
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        // getMenuInflater().inflate(R.menu.main, menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);

        MenuItem item = menu.findItem(R.id.show_secure);
        btnStartStopWork = (com.kyleduo.switchbutton.SwitchButton) item.getActionView();
        //btnStartStopWork =(Button) item.getActionView();
        cekWork();
        enableDrawer(isWork);
        /*btnStartStopWork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(getApplication(),"saya clik xx",Toast.LENGTH_SHORT).show();

                Log.e(" klik co"," c"+ countwork);
               *//* if (isWork) {
                    showEditDialog("stopWork");
                } else {
                    showEditDialog("startWork");
                }*//*
            }
        });*/
        //cekWork();

        btnStartStopWork.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Toast.makeText(getApplication(),isChecked+" xx",Toast.LENGTH_SHORT).show();
                // Log.e("isi ",isChecked+" isinya");
                //  cekLogin(isChecked);
                btnStartStopWork.setEnabled(false);
                Log.e(" klik com", " co " + countwork + ",ischecked:" + isChecked + ", cancel:" + cancelStartStop);
                if (countwork < 1) {
                    if (isChecked) {
                        // if (!cancelStartStop)
                        showEditDialog("startWork");
                    } else {
                        // if (!cancelStartStop)
                        showEditDialog("stopWork");
                    }
                }
                countwork++;
                cancelStartStop = false;
                // enableDrawer(isChecked);

            }

        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.menuRefresh) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        //Toast.makeText(this, id, Toast.LENGTH_SHORT).show();
        if (id == R.id.nav_visit) {
            Intent inten = new Intent(MainActivity.this, PoConfirmActivity.class);
            inten.putExtra(PoConfirmActivity.sessionUser, session);
            startActivityForResult(inten, 8);
        }else  if (id == R.id.nav_sell_out) {
            Intent intent =new Intent(this, PoNewActivity.class);
            intent.putExtra(PoNewActivity.sessionUser, session);
            intent.putExtra(PoNewActivity.sessionSellOut,true);
                startActivityForResult(intent, 13);
        } else if (id == R.id.nav_maps) {
            Intent inten = new Intent(this, PetaActivity.class);
            inten.putExtra(PetaActivity.sessionUser, session);
            startActivityForResult(inten, 9);
        } else if (id == R.id.nav_report) {
            //product
            Intent inten = new Intent(this, ProductActivity.class);
            inten.putExtra(ProductActivity.sessionUser, session);
            startActivityForResult(inten, 10);
        } else if (id == R.id.nav_complain) {
            Intent inten = new Intent(this, ComplainActivity.class);
            inten.putExtra(ComplainActivity.sessionUser, session);
            startActivityForResult(inten, 11);
        } else if (id == R.id.nav_survey) {
            Intent inten = new Intent(this, SurveyActivity.class);
            inten.putExtra(SurveyActivity.sessionUser, session);
            startActivityForResult(inten, 12);
        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.show_secure) {

        } else if (id == R.id.nav_message) {
            Intent inten = new Intent(this, PesanActivity.class);
            inten.putExtra(PesanActivity.sessionUser, session);
            startActivity(inten);
        } else if (id == R.id.nav_pass) {
            Intent inten = new Intent(this, PasswordActivity.class);
            inten.putExtra(PasswordActivity.sessiuser, session);
            startActivityForResult(inten, 4);
        } else if (id == R.id.nav_sync) {
            // SyncServer();
            Intent inten = new Intent(this, SyncActivity.class);
            inten.putExtra(SyncActivity.sessionUser, session);
            startActivityForResult(inten, 5);
            //Toast.makeText(this, "galery selected", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_send) {
            Intent inten = new Intent(this, ResendActivity.class);
            inten.putExtra(ResendActivity.sessionUser, session);
            startActivityForResult(inten, 6);
        } else if (id == R.id.nav_about) {
            Intent inten = new Intent(this, SupportActivity.class);
            startActivityForResult(inten, 7);
        } else if (id == R.id.nav_login) {
            if (item.getTitle().equals("LogOut")) {
                sessionController.updateSession("login", 0);
                cekSession();
            } else {
                Intent inten = new Intent(this, LoginActivity.class);//berpindah ke activity yang lain dgn data
                startActivityForResult(inten, 1);
                finish();
            }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private boolean CekCallPlan() {
        mSession session = sessionController.getSession("CallPlan", 1); //nilai4 ambil id callplan;
        if (null != session) {
            return true;
        } else {
            return false;
        }
    }

    void SyncServer() {

        //Context context = detailDraftPO.this;
        String title = "Please wait until download finish";
        String message = "silahkan pilih alamat pengiriman dibawah ini :";
        String button1String = "Batal";
        String button2String = "OK";

        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.view_download, null);
        // final EditText alamattujuan = (EditText) dialogView.findViewById(R.id.alamat_kirim_lain_tujuan);
        // final RadioButton alamat2 = (RadioButton) dialogView.findViewById(R.id.alamat_kirim_lain);
        // final RadioButton alamat1 = (RadioButton) dialogView.findViewById(R.id.alamat_kirim_default);

        AlertDialog.Builder ad = new AlertDialog.Builder(this);
        ad.setTitle(title);
        //ad.setMessage(message);
        ad.setView(dialogView);

        ad.show();

    }

    void cekWork() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat smp1 = new SimpleDateFormat(
                "yyyy-MM-dd");
        String tanggal = smp1.format(cal.getTime());
       /* Menu menu = navigationView.getMenu();
        MenuItem nav_visit = menu.findItem(R.id.nav_visit);
        MenuItem nav_maps = menu.findItem(R.id.nav_maps);
        MenuItem nav_report = menu.findItem(R.id.nav_report);*/
        // MenuItem nav_visit = menu.findItem(R.id.nav_visit);
        mSession session = sessionController.getSession("work", 1);
        if (null != session && session.getNilai3().equalsIgnoreCase(tanggal)) {
            // Log.e("isi work",session.getNama()+","+session.getNilai3());
            isWork = true;
            vBtnMainCst.setEnabled(true);
            vBtnMainCall.setEnabled(true);
            vBtnMainPo.setEnabled(true);
            pbtnMainPromo.setEnabled(true);
            vBtnMainTarget.setEnabled(true);

            btnStartStopWork.setChecked(true);

            //  enableDrawer(true);
        } else {
            //  Log.e("isi work","no session");
            isWork = false;
            vBtnMainCst.setEnabled(false);
            vBtnMainCall.setEnabled(false);
            vBtnMainPo.setEnabled(false);
            pbtnMainPromo.setEnabled(false);
            vBtnMainTarget.setEnabled(false);

            btnStartStopWork.setChecked(false);

            //  enableDrawer(false);
        }

    }

    void enableDrawer(boolean enabled) {
        Menu menu = navigationView.getMenu();
        MenuItem nav_visit = menu.findItem(R.id.nav_visit);
        MenuItem nav_maps = menu.findItem(R.id.nav_maps);
        MenuItem nav_report = menu.findItem(R.id.nav_report);
        MenuItem nav_complain = menu.findItem(R.id.nav_complain);
        MenuItem nav_survey = menu.findItem(R.id.nav_survey);
        MenuItem nav_sell_out=menu.findItem(R.id.nav_sell_out);
        nav_visit.setVisible(enabled);
        nav_maps.setVisible(enabled);
        nav_report.setVisible(enabled);
        nav_complain.setVisible(enabled);
        nav_survey.setVisible(enabled);
        nav_sell_out.setVisible(enabled);



        invalidateOptionsMenu();
    }

    private PlayStoreApi playStoreApi;

    void cekVersion() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(PlayStoreApi.BASE_URL)
                .addConverterFactory(JsoupConverterFactory.create())
                .build();
        playStoreApi = retrofit.create(PlayStoreApi.class);
        loadApps();
    }

    public void loadApps() {
        playStoreApi.getApps().enqueue(new Callback<AppVersion>() {
            @Override
            public void onResponse(Call<AppVersion> call, Response<AppVersion> response) {
                //hideLoading();
                // Log.e(" isi respon body","respon:"+response.body());
                AppVersion appVersion = response.body();
                if (appVersion != null) {
                    //  Log.e("versi","v "+ appVersion.detail.version );
                    PackageManager manager = getApplicationContext().getPackageManager();
                    PackageInfo info = null;
                    String version = "";
                    try {
                        info = manager.getPackageInfo(
                                getApplicationContext().getPackageName(), 0);
                        version = info.versionName;
                    } catch (PackageManager.NameNotFoundException e) {
                        e.printStackTrace();
                    }
                    if (appVersion.detail.version != null)
                        if (!version.trim().equalsIgnoreCase(appVersion.detail.version.trim())) {
                            Toast.makeText(MainActivity.this, "Please Update Your APP First From Play Store!", Toast.LENGTH_SHORT).show();
                            Intent inten = new Intent(MainActivity.this, SupportActivity.class);
                            startActivity(inten);
                        }
                }
                // adapter.loadData(response.body());
            }

            @Override
            public void onFailure(Call<AppVersion> call, Throwable t) {
                Log.e(" isi respon body", "respon:" + t.toString() + "\n" + t.getMessage());
                //Toast.makeText(PlayStoreActivity.this, "Error loading movies from Play Store",
                //        Toast.LENGTH_SHORT).show();
            }
        });
    }

    void cekSession() {
        if (sessionController.cekSession("login", 1)) {
            session = sessionController.getSession("login", 1);
            adaSession = true;
        } else {
            session = null;
            adaSession = false;
        }
        Menu menu = navigationView.getMenu();
        MenuItem nav_login = menu.findItem(R.id.nav_login);
        MenuItem nav_podraft = menu.findItem(R.id.nav_visit);
        //MenuItem nav_complain = menu.findItem(R.id.nav_complain);
        MenuItem nav_personal = menu.findItem(R.id.nav_manage);
        MenuItem nav_sync = menu.findItem(R.id.nav_sync);
        MenuItem nav_send = menu.findItem(R.id.nav_send);
        //MenuItem nav_list=menu.findItem(R.id.nav_list);
        MenuItem nav_pass = menu.findItem(R.id.nav_pass);
        if (adaSession) {
            nav_login.setTitle("LogOut");
            // nav_login.setIcon()
            // Log.e("isis session3", session.getNilai3());
            if (session.getNilai3().equalsIgnoreCase("1")) {
                //  nav_addmember.setVisible(true);
            } else {
                //  nav_addmember.setVisible(false);
            }

            //if (enabled) {
            int podraft = sessionController.getDraftPO();
            if (podraft > 0){
                nav_podraft.setTitle(Html.fromHtml("PO Draft <font color='red'>["+podraft+"]</font>"));
            }else{
                nav_podraft.setTitle("PO Draft");
            }
            //nav_list.setVisible(true);
            //nav_complain.setVisible(true);
            nav_personal.setVisible(true);
            nav_sync.setVisible(true);
            nav_send.setVisible(true);
            nav_pass.setVisible(true);
        } else {
            nav_login.setTitle("Login");
            // nav_list.setVisible(false);
            // nav_addmember.setVisible(false);
            //nav_complain.setVisible(false);
            nav_personal.setVisible(false);
            nav_sync.setVisible(false);
            nav_send.setVisible(false);
            nav_pass.setVisible(false);
            idProfile.setText("");
            idProfileLevel.setText("");
            Intent inten = new Intent(this, LoginActivity.class);//berpindah ke activity yang lain dgn data
            startActivityForResult(inten, 1);
            finish();
        }
        if (session != null) {

            idProfile.setText(session.getNilai2());
            idProfileLevel.setText(session.getNilai1() + " / " + session.getNilai6());
            if (session.getNilai9() != null) {
                byte[] picture = new byte[0];
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                    picture = session.getNilai9().getBytes(StandardCharsets.UTF_8);
                } else {
                    picture = session.getNilai9().getBytes(Charset.forName("UTF-8"));
                }
                idProfileImage.setImageBitmap(BitmapFactory.decodeByteArray(picture, 0, picture.length));
            } else {
                idProfileImage.setImageDrawable(getResources().getDrawable(R.drawable.ic_avatar));
            }
        }
    }

    @Override
    public void updateResult(String inputText) {
        if (!TextUtils.isEmpty(inputText)) {
            Log.e("isi ", "isi " + inputText);
            if (inputText.equalsIgnoreCase("true")) {
                // Toast.makeText(getApplicationContext(),"keluar juga",Toast.LENGTH_SHORT).show();
                if (isWork) {
                    isWork = false;
                    btnStartStopWork.setChecked(false);
                    enableDrawer(false);
                } else {
                    isWork = true;
                    btnStartStopWork.setChecked(true);
                    enableDrawer(true);
                }
                //nanti tambahak kirim data keserver
                // perintah.work();
                // perintah.laporan1("1");
            } else {
                isWork = false;
                cancelStartStop = true;
                btnStartStopWork.setChecked(false);
                cekWork();
                enableDrawer(false);
            }
        } else {
            isWork = false;
            cancelStartStop = true;
            btnStartStopWork.setChecked(false);
            cekWork();
            enableDrawer(false);
        }
        countwork = 0;
        btnStartStopWork.setEnabled(true);
        // Log.e(" klik cox"," cx"+ countwork);
        //cekLogin();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        switch (requestCode) {
            case CAMERA_REQUEST:
                if (canAccessCamera()) {
                    // doCameraThing();
                } else {
                    finish();
                }
                break;

            case CONTACTS_REQUEST:
                if (canAccessContacts()) {
                    // doContactsThing();
                } else {
                    finish();
                }
                break;

            case LOCATION_REQUEST:
                if (canAccessLocation()) {
                    // doLocationThing();
                } else {
                    finish();
                }
                break;
        }
    }

    private boolean canAccessLocation() {
        return (hasPermission(Manifest.permission.ACCESS_FINE_LOCATION));

    }

    private boolean canKillBacground() {
        return (hasPermission(Manifest.permission.KILL_BACKGROUND_PROCESSES));

    }

    private boolean canAccessCamera() {
        return (hasPermission(Manifest.permission.CAMERA));
    }

    private boolean canAccessContacts() {
        return (hasPermission(Manifest.permission.READ_CONTACTS));
    }


    @SuppressLint("WrongConstant")
    private boolean hasPermission(String perm) {
        int hasWriteContactsPermission = 0;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {

            hasWriteContactsPermission = checkSelfPermission(perm);
            //Log.e("type os",android.os.Build.VERSION.SDK_INT+" "+android.os.Build.VERSION_CODES.M+"-"+hasWriteContactsPermission);
            if (hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED) {
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }

        //return(PackageManager.PERMISSION_GRANTED==checkSelfPermission(perm));
    }

    /*public void Pindah(View view) {Intent intent = new Intent(MainActivity.this, Promo1Activity.class);
        startActivity(intent);
    }*/
}
