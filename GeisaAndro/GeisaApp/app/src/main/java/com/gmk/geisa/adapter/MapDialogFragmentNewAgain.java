package com.gmk.geisa.adapter;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextClock;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.gmk.geisa.R;
import com.gmk.geisa.activities.callplan.visit.VisitCallPlan;
import com.gmk.geisa.controller.CallPlanController;
import com.gmk.geisa.controller.SessionController;
import com.gmk.geisa.controller.TrackingController;
import com.gmk.geisa.databases.mDB;
import com.gmk.geisa.fragment.tab.TransparentMapFragment;
import com.gmk.geisa.helper.Constants;
import com.gmk.geisa.model.mCallPlan;
import com.gmk.geisa.model.mCustomer;
import com.gmk.geisa.model.mSession;
import com.gmk.geisa.model.mTracking;
import com.gmk.geisa.model.mTrackingPicture;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;

/**
 * Created by kenjin on 12/2/2016.
 */
public class MapDialogFragmentNewAgain extends DialogFragment implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
        AdapterView.OnItemSelectedListener, LocationListener, GoogleMap.OnCameraIdleListener {
    //start work
    Marker currLocationMarker;
    private LatLng myPosition;
    private LatLng CUSTOMER = null;
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 1000;
    //end maps
    private View view = null;

    private GoogleMap mMap;
    mDB data;
    SessionController sessionController;
    CallPlanController callPlanController;
    TrackingController trackingController;
    Button startWork, cancelWork;
    LinearLayout bacgroundcekin;
    ImageView imageView1;
    TextClock digitalClock1;
    private EditDialogListener mListener;
    private String jenispopup = null;
    Context context;
    //private SupportMapFragment mapFragment;
    MapFragment mapFragment;
    private static final String[] INITIAL_PERMS = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };
    private static final String[] LOCATION_PERMS = {
            Manifest.permission.ACCESS_FINE_LOCATION
    };
    private static final int INITIAL_REQUEST = 1337;
    private static final int LOCATION_REQUEST = INITIAL_REQUEST + 3;
    private String deviceInfo = "-";
    private mCustomer customer;
    // private mSession session;
    private mCallPlan callPlan;
    float currentZoom;
    private String salesId, customerId;
    Dialog dialog = null;

    @SuppressLint("ValidFragment")
    public MapDialogFragmentNewAgain(Context context, String jenis) {
        if (!TextUtils.isEmpty(jenis)) {
            jenispopup = jenis;
        } else {
            jenispopup = "startWork";
        }
        this.context = context;
        this.deviceInfo = getPhoneNumber();
        data = mDB.getInstance(getActivity());
        sessionController = SessionController.getInstance(context);
        trackingController = TrackingController.getInstance(context);
        callPlanController = CallPlanController.getInstance(context);
        // session = sessionController.getSession("login", 1);

    }

    @SuppressLint("ValidFragment")
    public MapDialogFragmentNewAgain(Context context, String jenis, mCustomer customer, mCallPlan callPlan) {
        if (!TextUtils.isEmpty(jenis)) {
            jenispopup = jenis;
        } else {
            jenispopup = "startWork";
        }
        this.context = context;
        this.deviceInfo = getPhoneNumber();
        data = mDB.getInstance(getActivity());
        sessionController = SessionController.getInstance(context);
        trackingController = TrackingController.getInstance(context);
        callPlanController = CallPlanController.getInstance(context);
        this.customer = customer;
        this.callPlan = callPlan;
        // session = sessionController.getSession("login", 1);
    }

    public MapDialogFragmentNewAgain() {
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        dialog = new Dialog(getActivity(),
                R.style.Theme_CustomDialog);
        LayoutInflater inflater = getActivity().getLayoutInflater();
        if (view == null) {
            if (jenispopup != null) {
                if (jenispopup.equalsIgnoreCase("startWork")) {
                    view = inflater.inflate(R.layout.popup_start_work, null);
                } else if (jenispopup.equalsIgnoreCase("stopWork")) {
                    view = inflater.inflate(R.layout.popup_stop_work, null);
                } else if (jenispopup.equalsIgnoreCase("checkIn")) {
                    view = inflater.inflate(R.layout.popup_cekin_plan, null);
                    bacgroundcekin = (LinearLayout) view.findViewById(R.id.bacgroundcekin);
                    imageView1 = (ImageView) view.findViewById(R.id.imageView1);
                    digitalClock1 = (TextClock) view.findViewById(R.id.digitalClock1);
                } else if (jenispopup.equalsIgnoreCase("checkOut")) {
                    view = inflater.inflate(R.layout.popup_cekout_plan, null);
                } else { //defaultt
                    view = inflater.inflate(R.layout.popup_stop_work, null);
                }
            } else {
                view = inflater.inflate(R.layout.popup_start_work, null);
            }
        }
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(view);
        // Creating Full Screen
        dialog.getWindow().setLayout(ViewPager.LayoutParams.MATCH_PARENT,
                ViewPager.LayoutParams.MATCH_PARENT);
        startWork = (Button) view.findViewById(R.id.startWork);
        cancelWork = (Button) view.findViewById(R.id.cancelWork);

        StatusGPSCheck();
        buildGoogleApiClient();
        if (checkPlayServices()) {
            SupportMapFragment mapFragment =
                    (SupportMapFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);
        }
        if (checkPlayServices()) {
            //  Log.e("in masuk","ini masuk");
            mapFragment = ((TransparentMapFragment) getActivity().getFragmentManager()
                    .findFragmentById(R.id.map));

            mapFragment.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(GoogleMap googleMap) {
                    if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    // googleMap.setMyLocationEnabled(true);
                    mMap = googleMap;

                    setUpMapIfNeededNew();

                }
            });
        }


        return dialog;
    }

    private GoogleMap googleMap;
    private Location mLastLocations;
    // private LatLng myPosition;
    //private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 1000;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;

    protected synchronized void buildGoogleApiClient() {
        // Toast.makeText(this, "buildGoogleApiClient", Toast.LENGTH_SHORT).show();
        mGoogleApiClient = new GoogleApiClient.Builder(getContext())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    public void onMapReady(GoogleMap gMap) {
        googleMap = gMap;
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            return;
        }
        /*mMyLocationCheckbox.setChecked(true);
        updateMapType();
        updateTraffic();
        updateMyLocation();
        updateBuildings();*/
        googleMap.setMyLocationEnabled(true);
        /*options = new FirebaseOptions.Builder().setApplicationId("geofire").setDatabaseUrl(GEO_FIRE_DB).build();
        if (null == app || !app.getName().equalsIgnoreCase("geofire") || !FirebaseApp.getApps(this).equals(app)) {
            app = FirebaseApp.initializeApp(this, options, "geofire");
        }*/
        Log.e("cek maps ready ", "map ready");

        ///map on click
        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            @Override
            public void onMapClick(LatLng point) {
                // onRoute = false;


            }
        });

        googleMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {
                // Setting up the infoWindow with current's marker info
                // infoTitle.setText(marker.getTitle());
                // infoSnippet.setText(marker.getSnippet());
                // infoButtonListener.setMarker(marker);
                Log.e("isi snipet", "snipet " + marker.getSnippet());
                try {
                    //      infoImage.setImageResource(getIcon(getApplication(), marker.getSnippet()));
                } catch (Exception ex) {
                    //      infoImage.setImageResource(R.drawable.you_are_here);
                    Log.e("error", ex.toString());
                }
                // We must call this to set the current marker and infoWindow references
                // to the MapWrapperLayout
                //  mapWrapperLayout.setMarkerWithInfoWindow(marker, infoWindow);
                // return infoWindow;
                return null;
            }
        });
        //info window

        googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                //  addRoute(marker.getPosition());
                // Toast.makeText(ScrollingActivity.this, marker.getTitle() + "'s button clicked!", Toast.LENGTH_SHORT).show();
            }
        });

        // We want to reuse the info window for all the markers,
        // so let's create only one class member instance
        /*this.infoWindow = (ViewGroup) getLayoutInflater().inflate(R.layout.info_window, null);
        this.infoTitle = (TextView) infoWindow.findViewById(R.id.title);
        this.infoSnippet = (TextView) infoWindow.findViewById(R.id.snippet);
        this.infoImage = (ImageView) infoWindow.findViewById(R.id.image);
        this.infoButton = (Button) infoWindow.findViewById(R.id.button);

        // Setting custom OnTouchListener which deals with the pressed state
        // so it shows up
        this.infoButtonListener = new OnInfoWindowElemTouchListener(infoButton,
                getResources().getDrawable(R.drawable.ic_car),
                getResources().getDrawable(R.drawable.driver_history)) {
            @Override
            protected void onClickConfirmed(View v, Marker marker) {
                // Here we can perform some action triggered after clicking the button
                // Toast.makeText(ScrollingActivity.this, marker.getTitle() + "'s button clicked!", Toast.LENGTH_SHORT).show();
            }
        };
        this.infoButton.setOnTouchListener(infoButtonListener);*/


        //end info window

    }
    //end tes


    @Override
    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);

        startWork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // textnone.setText("buton diklik");
                //this.mListener.MapDialogOnComplete("tes");
                if (myPosition != null) {
                    //Toast.makeText(getActivity(), "Start work", Toast.LENGTH_SHORT).show();
                    //dowork(myPosition);
                    if (dowork(myPosition)) {
                        //Log.e("start work","start work");
                        EditDialogListener activity = (EditDialogListener) getActivity();
                        activity.updateResult("true");
                        dismiss();
                    }
                } else {
                    Toast.makeText(getActivity(), "Tunggu Sampai Lokasi Anda Tampil di Maps...", Toast.LENGTH_SHORT).show();
                }

            }
        });
        cancelWork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditDialogListener activity = (EditDialogListener) getActivity();
                activity.updateResult("false");
                dismiss();
            }
        });
        // initializeViews();
        // mSession sessions = sessionController.getSession("CallPlan", 1);
        // Log.e("isi call",sessions.getNilai1()+","+sessions.getNilai2()+","+sessions.getNilai3()+","+sessions.getNilai4());

    }

    private boolean dowork(LatLng lokasi) {
        boolean hasil = false;
        //Log.e("device info", deviceInfo);
        if (lokasi != null) {

            mSession session = sessionController.getSession("login", 1);
            if (jenispopup.equalsIgnoreCase("startWork")) {
                //  mSession session=sessionController.getSession("login",1);
                if (null != session) {
                    salesId = String.valueOf(session.getId());
                    Calendar cal = Calendar.getInstance();
                    SimpleDateFormat smp = new SimpleDateFormat(
                            "yyyy-MM-dd HH:mm:ss");
                    SimpleDateFormat smp1 = new SimpleDateFormat(
                            "yyyy-MM-dd");
                    SimpleDateFormat smp2 = new SimpleDateFormat(
                            "yyyyMMddHHmmssSSS");
                    String tanggal = smp.format(cal.getTime());
                    String tanggalnya = smp1.format(cal.getTime());
                    String idtgl = String.valueOf(session.getId()) + smp2.format(cal.getTime());
                    mTracking tracking;
                    //cek lagi untuk reff
                    tracking = new mTracking(0, idtgl, String.valueOf(session.getId()), "Work",
                            tanggalnya, tanggal, String.valueOf(lokasi.latitude),
                            String.valueOf(lokasi.longitude), "0", jenispopup, 1, smp.format(cal.getTime()), deviceInfo);
                    // Log.e("device info track",tracking.getInfoDevice());
                    if (trackingController.insertTracking(tracking)) {
                        // Log.e("masuk ke tracking", "masuk ket tracking");
                        mSession session1 = new mSession(0, "work", 1, idtgl, tanggal, tanggalnya, null);
                        sessionController.insertUpdateSessionWork(session1);

                    }
                    Toast.makeText(getActivity(), "Anda Mulai Bekerja di Lokasi : \n" +
                            myPosition.latitude + "," + myPosition.longitude, Toast.LENGTH_SHORT).show();
                    hasil = true;
                } else {
                    Toast.makeText(getActivity().getApplicationContext(), "Gagal Start Work", Toast.LENGTH_SHORT).show();
                    hasil = false;
                }
            } else if (jenispopup.equalsIgnoreCase("stopWork")) {
                if (null != session) {
                    salesId = String.valueOf(session.getId());
                    Calendar cals = Calendar.getInstance(Locale.US);
                    int dayOfWeek = cals.get(Calendar.DAY_OF_WEEK);

                    if (cals.get(Calendar.HOUR_OF_DAY) <= 16 && (dayOfWeek == 1 && dayOfWeek == 7)) {
                        Toast.makeText(getActivity().getApplicationContext(), "Stop Work Hanya bisa dilakukan diatas Jam 16.00", Toast.LENGTH_SHORT).show();
                    } else {

                        Calendar cal = Calendar.getInstance();
                        SimpleDateFormat smp = new SimpleDateFormat(
                                "yyyy-MM-dd HH:mm:ss");
                        SimpleDateFormat smp1 = new SimpleDateFormat(
                                "yyyy-MM-dd");
                        SimpleDateFormat smp2 = new SimpleDateFormat(
                                "yyyyMMddHHmmssSSS");
                        String tanggal = smp.format(cal.getTime());
                        String tanggalnya = smp1.format(cal.getTime());
                        String idtgl = String.valueOf(session.getId()) + smp2.format(cal.getTime());
                        mTracking tracking;

                        tracking = new mTracking(0, idtgl, String.valueOf(session.getId()), "Work",
                                tanggalnya, tanggal, String.valueOf(lokasi.latitude),
                                String.valueOf(lokasi.longitude), "0", jenispopup, 1, smp.format(cal.getTime()), deviceInfo);

                        if (trackingController.insertTracking(tracking)) {
                            mSession session1 = new mSession(0, "work", 0, idtgl, tanggal, tanggalnya, null);
                            sessionController.insertUpdateSessionWork(session1);
                        }
                        Toast.makeText(getActivity(), "Anda Selesai Bekerja di Lokasi : \n" +
                                myPosition.latitude + "," + myPosition.longitude, Toast.LENGTH_SHORT).show();


                        hasil = true;
                    }
                }
            } else if (jenispopup.equalsIgnoreCase("checkIn")) {
                //Log.e(" anda mulai cek in","cek in ya");
                if (customer != null) {
                    if (null != session) {
                        salesId = String.valueOf(session.getId());
                        customerId = String.valueOf(customer.getCustId());
                        double lat = Double.valueOf(customer.getLat());
                        double lot = Double.valueOf(customer.getLng());
                        if (lat != 0 && lot != 0) {
                            if (canCheckIn(myPosition, CUSTOMER)) {
                                takePicture(callPlan, customer, session);
                            } else {
                                cannotLogin();
                            }

                        } else {
                            takePicture(callPlan, customer, session);
                        }
                    }
                }

            } else if (jenispopup.equalsIgnoreCase("checkOut")) {
                mSession sessions = sessionController.getSession("CallPlan", 1);
                //Log.e("cek out","cek out");
                if (sessions != null) {
                    if (null != session) {
                        //Log.e("id callplan cekout",sessions.getNilai4());
                        ArrayList<mCallPlan> newCallplan = new ArrayList<>();
                        mCallPlan call = callPlanController.getCallPlanById(sessions.getNilai4());
                        if (call != null) {
                            // Log.e("id callplan cekout",sessions.getNilai4());
                            call.setCallPlanStatusId(3);
                            call.setCallPlanStatusName("Visited");
                        }
                        newCallplan.add(call);
                        if (callPlanController.InsertUpateAllCallPlanWhere(newCallplan)) { //error call back ged data
                            callPlanController.updateCallPlanToServerStatusNonCallBack(String.valueOf(session.getId()), newCallplan);
                        }
                        // Log.e("isi cek out","cek masuk");
                        salesId = String.valueOf(session.getId());
                        customerId = String.valueOf(sessions.getNilai5());
                        Calendar cal = Calendar.getInstance();
                        SimpleDateFormat smp = new SimpleDateFormat(
                                "yyyy-MM-dd HH:mm:ss");
                        SimpleDateFormat smp1 = new SimpleDateFormat(
                                "yyyy-MM-dd");
                        SimpleDateFormat smp2 = new SimpleDateFormat(
                                "yyyyMMddHHmmssSSS");
                        String tanggal = smp.format(cal.getTime());
                        String tanggalnya = smp1.format(cal.getTime());
                        String idtgl = salesId + smp2.format(cal.getTime());
                        mTracking tracking;

                        tracking = new mTracking(0, idtgl, salesId, "CallPlan",
                                tanggalnya, tanggal, String.valueOf(lokasi.latitude),
                                String.valueOf(lokasi.longitude), sessions.getNilai4(), jenispopup, 1, smp.format(cal.getTime()), deviceInfo);

                        if (trackingController.insertTracking(tracking)) {
                            // Log.e("isi cek out","cek out");
                            Toast.makeText(getActivity(), "Anda Check Out di Lokasi : \n" +
                                    myPosition.latitude + "," + myPosition.longitude, Toast.LENGTH_SHORT).show();
                            hasil = true;
                        }
                    }
                }
            }


        }
        return hasil;
    }

    private void cannotLogin() {
        Toast.makeText(context, "Can't CheckIn, Your Are Not In Range", Toast.LENGTH_SHORT).show();
        bacgroundcekin.setBackgroundResource(R.drawable.warning_buletan);
        imageView1.setImageResource(R.drawable.warning_text);
        digitalClock1.setVisibility(View.GONE);
        startWork.setVisibility(View.GONE);
    }

    private boolean canCheckIn(LatLng myPosition, LatLng CUSTOMER) {
        boolean hsl = false;
        if (customer != null && CUSTOMER != null) {
            double lat = Double.valueOf(customer.getLat());
            double lot = Double.valueOf(customer.getLng());
            double custradius = Double.valueOf(customer.getRadius()) / 1000;
            double jarak;
            if (lat != 0 && lot != 0) {
                CUSTOMER = new LatLng(lat, lot);
                jarak = Constants.CalculationByDistance(myPosition, CUSTOMER);
                //Log.e("radiusnya",jarak+","+customer.getRadius()+","+custradius);
                if (jarak <= custradius) {
                    hsl = true;
                } else {
                    hsl = false;
                }
            } else {
                hsl = true;
            }
        } else if (customer != null) {
            if (customer.getLat() == 0 && customer.getLng() == 0)
                hsl = true;
        }
        return hsl;
    }

    private void resetLogin() {
        if (jenispopup.equalsIgnoreCase("checkIn")) {
            bacgroundcekin.setBackgroundResource(R.drawable.checkout_buletan);
            imageView1.setImageResource(R.drawable.checkin_text);
            digitalClock1.setVisibility(View.VISIBLE);
            startWork.setVisibility(View.VISIBLE);
            if (!canCheckIn(myPosition, CUSTOMER)) {
                cannotLogin();
            }
        }

    }

    private void setUpMapIfNeededNew() {
        // Do a null check to confirm that we have not already instantiated the map.
        // if (mMap == null) {

           /* mMap = ((TransparentMapFragment) getActivity().getFragmentManager()
                    .findFragmentById(R.id.map)).getMap();*/

        if (isGoogleMapsInstalled()) {
            if (mMap != null) {
                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    Log.e("error map", "err map");
                    return;
                }
                mMap.setMyLocationEnabled(true);
                mMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {

                    @Override
                    public void onMyLocationChange(Location arg0) {
                        // TODO Auto-generated method stub
                        // LatLng location=new LatLng(arg0.getLatitude(), arg0.getLongitude());
                        // addMarker(arg0);
                        myPosition = new LatLng(arg0.getLatitude(), arg0.getLongitude());
                        addMarkerWithCustomer(arg0, customer);
                        resetLogin();

                    }
                });

            }
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage("installGoogleMaps");
            builder.setCancelable(false);
            builder.setPositiveButton("install", getGoogleMapsListener());
            AlertDialog dialog = builder.create();
            dialog.show();
        }

        // Check if we were successful in obtaining the map.

    }

    //}

    private void addMarkerWithCustomer(Location location, mCustomer customer) {
        boolean ISTHERECUSTOMER = false;
        LatLng loc = new LatLng(location.getLatitude(), location.getLongitude());
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        if (mMap != null)
            mMap.clear();
        if (customer != null) {
            try {

                double lat = customer.getLat();
                double lot = customer.getLng();
                double radius = customer.getRadius();
                if (lat != 0 && lot != 0) {
                    CUSTOMER = new LatLng(lat, lot);
                    Marker davao = mMap.addMarker(new MarkerOptions().position(CUSTOMER).title(customer.getCustName() + "#alias:" + customer.getAliasName()).snippet(customer.getAddress()).icon(BitmapDescriptorFactory.fromResource(R.drawable.addcust)));
                    builder.include(davao.getPosition());//include to builder
                    Circle circle = mMap.addCircle(new CircleOptions()
                            .center(davao.getPosition())
                            .radius(radius)
                            .strokeColor(Color.RED)
                            .strokeWidth(1)
                            .fillColor(0x5500ff00));
                    circle.setCenter(davao.getPosition());
                    ISTHERECUSTOMER = true;
                } else {
                    ISTHERECUSTOMER = false;
                }
            } catch (Exception x) {
                ISTHERECUSTOMER = false;
            }
        }
        if (location != null) {
            myPosition = new LatLng(location.getLatitude(), location.getLongitude());
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(myPosition);
            markerOptions.title("You Are Here!").snippet(myPosition.latitude + "," + myPosition.longitude)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.you_are_here));
            currLocationMarker = mMap.addMarker(markerOptions);
            builder.include(currLocationMarker.getPosition());//include to builder
        }


        if (mMap != null) {

            double zoom = mMap.getCameraPosition().zoom;
            //Log.e("isi zoom",zoom+"xx");
            if (zoom == currentZoom || zoom < 5) {
                if (ISTHERECUSTOMER) {
                    LatLngBounds bounds = builder.build();
                    int padding = 80; // offset from edges of the map in pixels
                    CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
                    mMap.moveCamera(cu);
                    currentZoom = mMap.getCameraPosition().zoom;
                    //googleMap.animateCamera(cu);

                } else {
                    // zoom in the camera to langlong
                    if (myPosition != null) {
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, 16.0f));
                        currentZoom = mMap.getCameraPosition().zoom;
                        // animate the zoom process
                        //googleMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);
                    }
                }
            }

        }


    }

    private void addMarker(Location location) {
        if (location != null) {
            if (mMap != null) {
                mMap.clear();
            }
            myPosition = new LatLng(location.getLatitude(), location.getLongitude());
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(myPosition);
            markerOptions.title("Current Position\n" + myPosition.latitude + "," + myPosition.longitude)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.you_are_here));
            currLocationMarker = mMap.addMarker(markerOptions);
        }
        // Toast.makeText(this,"Location Changed",Toast.LENGTH_SHORT).show();

        //zoom to current position:
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(myPosition).zoom(19).build();

        mMap.animateCamera(CameraUpdateFactory
                .newCameraPosition(cameraPosition));
    }


    public boolean isGoogleMapsInstalled() {
        try {
            getActivity().getPackageManager().getApplicationInfo(
                    "com.google.android.apps.maps", 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    public DialogInterface.OnClickListener getGoogleMapsListener() {
        return new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("market://details?id=com.google.android.apps.maps"));
                startActivity(intent);

                // Finish the fragment so they can't circumvent the check
                if (getActivity() != null) {
                    Fragment fragment = (getActivity().getFragmentManager()
                            .findFragmentByTag(MapFragment.class.getName()));
                    android.app.FragmentTransaction ft = getActivity().getFragmentManager()
                            .beginTransaction();
                    ft.remove(fragment);
                    ft.commitAllowingStateLoss();
                }
            }

        };
    }

    @Override
    public void onDestroyView() {

        super.onDestroyView();
        if (getActivity() != null) {
            try {
                Fragment fragment = (getActivity().getFragmentManager()
                        .findFragmentById(R.id.map));
                android.app.FragmentTransaction ft = getActivity().getFragmentManager()
                        .beginTransaction();
                ft.remove(fragment);
                ft.commitAllowingStateLoss();
            } catch (Exception e) {

            }
        }

    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            this.mListener = (EditDialogListener) activity;
        } catch (final ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnCompleteListener");
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onCameraIdle() {

    }


    public interface EditDialogListener {
        void updateResult(String inputText);
    }

    //cek maps
    public void StatusGPSCheck() {
        final LocationManager manager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        Log.e("cekgps", "cekgps");
        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            turnGPSOn();
            Log.e("idupin", "hidup");
            // buildAlertMessageNoGps();
        }
        turnOnDataConnection(true, getActivity().getApplicationContext());


    }

    private void turnGPSOn() {

        if (Build.VERSION.SDK_INT > 11) {

            String provider = Settings.Secure.getString(getActivity().getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
            //Log.e("idupin", "mulai cek ");
            if (!provider.contains("gps")) { //if gps is disabled
                final Intent poke = new Intent();
                poke.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
                poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
                poke.setData(Uri.parse("3"));
                getActivity().sendBroadcast(poke);
                Toast.makeText(getActivity().getApplicationContext(), "Aktifkan Location", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                //buildAlertMessageNoGps();
            }
        } else {
            String provider = Settings.Secure.getString(getActivity().getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
            Log.e("idupin", "mulai cek ");
            if (!provider.contains("gps")) { //if gps is disabled
                final Intent poke = new Intent();
                poke.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
                poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
                poke.setData(Uri.parse("3"));
                getActivity().sendBroadcast(poke);
                Toast.makeText(getActivity().getApplicationContext(), "hidup", Toast.LENGTH_SHORT).show();
                //buildAlertMessageNoGps();
            }
        }
    }

    int bv = Build.VERSION.SDK_INT;

    boolean turnOnDataConnection(boolean ON, Context context) {

        try {
            if (bv == Build.VERSION_CODES.FROYO)

            {
                Method dataConnSwitchmethod;
                Class<?> telephonyManagerClass;
                Object ITelephonyStub;
                Class<?> ITelephonyClass;

                TelephonyManager telephonyManager = (TelephonyManager) context
                        .getSystemService(Context.TELEPHONY_SERVICE);

                telephonyManagerClass = Class.forName(telephonyManager.getClass().getName());
                Method getITelephonyMethod = telephonyManagerClass.getDeclaredMethod("getITelephony");
                getITelephonyMethod.setAccessible(true);
                ITelephonyStub = getITelephonyMethod.invoke(telephonyManager);
                ITelephonyClass = Class.forName(ITelephonyStub.getClass().getName());

                if (ON) {
                    dataConnSwitchmethod = ITelephonyClass
                            .getDeclaredMethod("enableDataConnectivity");
                } else {
                    dataConnSwitchmethod = ITelephonyClass
                            .getDeclaredMethod("disableDataConnectivity");
                }
                dataConnSwitchmethod.setAccessible(true);
                dataConnSwitchmethod.invoke(ITelephonyStub);

            } else {
                //log.i("App running on Ginger bread+");
                final ConnectivityManager conman = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                final Class<?> conmanClass = Class.forName(conman.getClass().getName());
                final Field iConnectivityManagerField = conmanClass.getDeclaredField("mService");
                iConnectivityManagerField.setAccessible(true);
                final Object iConnectivityManager = iConnectivityManagerField.get(conman);
                final Class<?> iConnectivityManagerClass = Class.forName(iConnectivityManager.getClass().getName());
                final Method setMobileDataEnabledMethod = iConnectivityManagerClass.getDeclaredMethod("setMobileDataEnabled", Boolean.TYPE);
                setMobileDataEnabledMethod.setAccessible(true);
                setMobileDataEnabledMethod.invoke(iConnectivityManager, ON);
            }


            return true;
        } catch (Exception e) {
            Log.e("ERR DATA", "error turning on/off data");

            return false;
        }

    }

    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil
                .isGooglePlayServicesAvailable(getActivity());
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, getActivity(),
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Toast.makeText(getActivity().getApplicationContext(),
                        "This device is not supported.", Toast.LENGTH_LONG)
                        .show();
                dismiss();
            }
            return false;
        }
        return true;
    }


    @SuppressLint("HardwareIds")
    public String getPhoneNumber() {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String phonenum = "", IMEI, devicename;
        try {
            //Log.e("isi telepon",telephonyManager.getLine1Number());
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                return null;
            }
            if (null != telephonyManager.getLine1Number()) {
                if (!telephonyManager.getLine1Number().equals("")) {
                    phonenum = telephonyManager.getLine1Number();
                } else {
                    phonenum = telephonyManager.getSimSerialNumber() + " " + telephonyManager.getSimOperatorName();
                }
            }
            IMEI = telephonyManager.getDeviceId();
            devicename = Build.MODEL + " " + Build.MANUFACTURER;
        } catch (Exception e) {
            phonenum = "Error!!";
            IMEI = "Error!!";
            devicename = "Error!";
        }
        return IMEI + "/" + devicename + "/" + phonenum;
        // Log.e("device info",phonenum+"/"+IMEI+"/"+devicename);
    }

    private String newCallPlan() {
        mSession session = sessionController.getSession("login", 1);

        String tglcallplan;

        Calendar calss = Calendar.getInstance();
        SimpleDateFormat smp2 = new SimpleDateFormat("yyMMddhhmmss");
        SimpleDateFormat smp3 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat smp4 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        tglcallplan = smp4.format(calss.getTime());
        String plan = smp3.format(calss.getTime());
        ArrayList<mCallPlan> newCallplan = new ArrayList<>();
        int naik = 1;
        String callplanId = String.valueOf(session.getId()).concat(String.valueOf(smp2.format(calss.getTime()))).concat(String.valueOf(naik));
        //Log.e("isi call plan",callplanId+","+plan+","+"Plan"+","+session.getId()+","+cu.getCustId()+","+ typePlan+","+tglcallplan);
        mCallPlan call = new mCallPlan(callplanId, plan, "UnPlan", session.getId(), customer.getCustId(), 0,"", tglcallplan, 1);
        call.setCallPlanStatusName("Visited");
        call.setCallPlanTypeName("UnPlan");
        call.setModifiedBy(String.valueOf(session.getId()));
        call.setModifiedDate(smp3.format(calss.getTime()));
        call.setCallPlanStatusId(3);
        newCallplan.add(call);
        if (callPlanController.InsertUpateAllCallPlanWhere(newCallplan)) { //error call back ged data
            callPlanController.updateCallPlanToServerStatusNonCallBack(String.valueOf(session.getId()), newCallplan);
            return callplanId;
        } else {
            return null;
        }
    }

    //take picture
    private Uri file;
    private int recCode = 100;

    public void takePicture(mCallPlan callPlan, mCustomer customer, mSession session) {
        String callplanId = "";
        if (callPlan != null) {
            callplanId = callPlan.getCallPlanId();
        } else {
            callplanId = "UNP" + customer.getCustById();
        }
        // "G_" + salesCustomer+"_"+ idMasCallPlan + ".jpg";
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        file = Uri.fromFile(getOutputMediaFile(session.getId() + "_" + callplanId));
        //Log.e("isi fileasli",file.toString());
        intent.putExtra(MediaStore.EXTRA_OUTPUT, file);

        //intent.putExtra("output", file);
        startActivityForResult(intent, recCode);
    }

    //getoutputmedia
    private static File getOutputMediaFile(String uniqname) {
        /*File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DCIM), "/Geisa/TrackingPicture");*/
        File mediaStorageDir = new File(Environment.getExternalStorageDirectory().toString() + "/Geisa/TrackingPicture");
        //Log.e("lokasi picture",mediaStorageDir.toString()+","+mediaStorageDir.getName());
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.e("CameraDemo", "failed to create directory");
                return null;
            }
        }

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        return new File(mediaStorageDir.getPath() + File.separator +
                "GF_" + uniqname + "_" + timeStamp + ".jpg");
    }

    //save picture
    public File saveBitmapToFile(File file) {
        try {
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            o.inSampleSize = 6;
            FileInputStream inputStream = new FileInputStream(file);
            BitmapFactory.decodeStream(inputStream, null, o);
            inputStream.close();

            final int REQUIRED_SIZE = 75;
            int scale = 1;
            while (o.outWidth / scale / 2 >= REQUIRED_SIZE && o.outHeight / scale / 2 >= REQUIRED_SIZE) {
                scale *= 2;
            }
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            inputStream = new FileInputStream(file);
            Bitmap selectedBitmap = BitmapFactory.decodeStream(inputStream, null, o2);
            inputStream.close();
            //overwrie file
            file.createNewFile();
            FileOutputStream outputStream = new FileOutputStream(file);
            selectedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            return file;
        } catch (Exception e) {
            return null;
        }
    }

    private boolean createTrackingCallPlan(LatLng lokasi, String jenispopup, String ref, int incall) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat smp = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat smp1 = new SimpleDateFormat(
                "yyyy-MM-dd");
        SimpleDateFormat smp2 = new SimpleDateFormat(
                "yyyyMMddHHmmssSSS");
        String tanggal = smp.format(cal.getTime());
        String tanggalnya = smp1.format(cal.getTime());
        String idtgl = salesId + smp2.format(cal.getTime());
        mTracking tracking;

        tracking = new mTracking(0, idtgl, salesId, "CallPlan",
                tanggalnya, tanggal, String.valueOf(lokasi.latitude),
                String.valueOf(lokasi.longitude), ref, jenispopup, 1, smp.format(cal.getTime()), deviceInfo);

        if (trackingController.insertTracking(tracking)) {
            mSession session1 = new mSession(0, "CallPlan", incall, idtgl, tanggal, tanggalnya, ref);
            session1.setNilai5(customerId);
            sessionController.insertUpdateSessionWork(session1);
            return true;
        }else {
            return false;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent datas) {
        Log.e("hasilnya camera", requestCode + "," + resultCode + "," + RESULT_OK);
        if (requestCode >= 100) {
            if (resultCode == RESULT_OK) {
                try {
                   // Log.e("isi file", file.getPath());
                    File imgFile = new File(file.getPath());
                    File new1 = saveBitmapToFile(imgFile);
                    String callPlanId = null;
                    //get callplan or insert call plan
                    if (null != callPlan && null != callPlan.getCallPlanId()) {
                        callPlanId = callPlan.getCallPlanId();
                        //Log.e("isi callplan hora",callPlanId+" x");
                    } else {
                        callPlanId = newCallPlan();
                    }
                    //Log.e("isi callplan", callPlanId + " x");
                    //simpan image to database then send to server
                    Calendar calss = Calendar.getInstance();
                    SimpleDateFormat smp2 = new SimpleDateFormat("yyMMddhhmmss");
                    SimpleDateFormat smp4 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                    Intent batteryIntent = getActivity().registerReceiver(
                            null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
                    int levelBattrey = batteryIntent.getIntExtra("level", -1);
                    //Log.e("isi batre",levelBattrey+"%");
                    String TrackingPictureId = String.valueOf(salesId).concat(String.valueOf(smp2.format(calss.getTime()))).concat(String.valueOf(1));
                    mTrackingPicture trackingPicture = new mTrackingPicture(TrackingPictureId, callPlanId, file.getPath(), String.valueOf(levelBattrey), "checkIn", smp4.format(calss.getTime()), salesId, 1);

                    trackingController.insertTrackingPictureServer(trackingPicture);
                    //insert Tracking
                   // Log.e("mulai tracking","mulai tracking");
                    if (createTrackingCallPlan(myPosition, "checkIn", callPlanId, 1)) {
                        getActivity().finish();
                        dismiss();
                        startActivity(new Intent(getActivity().getApplicationContext(), VisitCallPlan.class));
                        //Toast.makeText(getActivity(), "Check In", Toast.LENGTH_SHORT).show();
                    } else {
                        //cannot create call plan error
                        cannotLogin();
                        Toast.makeText(getActivity(), "Problem With CallPlan Please Contact Admin", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception ex) {
                    Toast.makeText(getActivity(), "Problem With CallPlan Please Contact Admin", Toast.LENGTH_SHORT).show();
                }
            }
        } else {
            Toast.makeText(getActivity(), "Error On Camera, Try Again Latter", Toast.LENGTH_SHORT).show();
        }
    }
    //end take picture


}
