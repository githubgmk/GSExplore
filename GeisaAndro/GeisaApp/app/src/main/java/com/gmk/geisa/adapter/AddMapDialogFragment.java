package com.gmk.geisa.adapter;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.gmk.geisa.R;
import com.gmk.geisa.controller.SessionController;
import com.gmk.geisa.controller.TrackingController;
import com.gmk.geisa.databases.mDB;
import com.gmk.geisa.fragment.tab.TransparentMapFragment;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by kenjin on 12/2/2016.
 */
public class AddMapDialogFragment extends DialogFragment {
    private LatLng myPosition;
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 1000;
    //end maps
    private View view;

    private GoogleMap mMap;
    boolean firstlocation;
    mDB data;
    SessionController sessionController;
    TrackingController trackingController;
    Button mapDone;
    TextInputEditText popupMapLatitude, popupMapLongitude;
    CheckBox notInCustomer;
    double latitude = 0, longitude = 0;
    private EditDialogListener mListener;
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

    @SuppressLint("ValidFragment")
    public AddMapDialogFragment(Context context) {
        this.context = context;
        data = mDB.getInstance(getActivity());
        sessionController = SessionController.getInstance(context);
        trackingController = TrackingController.getInstance(context);
    }

    public AddMapDialogFragment() {
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Dialog dialog = new Dialog(getActivity(),
                R.style.Theme_CustomDialog);
        LayoutInflater inflater = getActivity().getLayoutInflater();

        if (view == null) {
            view = inflater.inflate(R.layout.popup_add_map, null);
        }
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(view);
        // Creating Full Screen
        dialog.getWindow().setLayout(ViewPager.LayoutParams.MATCH_PARENT,
                ViewPager.LayoutParams.MATCH_PARENT);
        mapDone = (Button) view.findViewById(R.id.mapDone);
        notInCustomer = (CheckBox) view.findViewById(R.id.notInCustomer);
        popupMapLatitude = (TextInputEditText) view.findViewById(R.id.popupMapLatitude);
        popupMapLongitude = (TextInputEditText) view.findViewById(R.id.popupMapLongitude);

        StatusGPSCheck();
        if (checkPlayServices()) {
            //  Log.e("in masuk","ini masuk");
            mapFragment = ((TransparentMapFragment) getActivity().getFragmentManager()
                    .findFragmentById(R.id.map));
            // mapFragment.getMapAsync(this);
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


    @Override
    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        notInCustomer.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    popupMapLatitude.setText("0");
                    popupMapLongitude.setText("0");
                } else {
                    popupMapLatitude.setText(String.valueOf(latitude));
                    popupMapLongitude.setText(String.valueOf(longitude));
                }
            }
        });

        mapDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditDialogListener activity = (EditDialogListener) getActivity();
                String hasil = popupMapLatitude.getEditableText() + "," + popupMapLongitude.getEditableText();
                activity.updateResult(hasil);
                dismiss();
            }
        });
        // initializeViews();

    }


    private void setUpMapIfNeededNew() {

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
                        myPosition = new LatLng(arg0.getLatitude(), arg0.getLongitude());
                        zoomToLocation(arg0);

                    }

                });

                mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(LatLng latLng) {
                        setLatLot(latLng);
                        addMarker(latLng);
                       /* Marker mMarker = mMap.addMarker(new MarkerOptions().position(latLng));
                        if (mMap != null) {
                            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16.0f));
                        }*/
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

    private void setLatLot(LatLng latLng) {
        latitude = latLng.latitude;
        longitude = latLng.longitude;
        if (!notInCustomer.isChecked()) {
            popupMapLatitude.setText(String.valueOf(latitude));
            popupMapLongitude.setText(String.valueOf(longitude));
        }
    }
    private  void zoomToLocation(Location location){
        if (location != null && !firstlocation) {
            firstlocation=true;
            LatLng loc = new LatLng(location.getLatitude(), location.getLongitude());
            setLatLot(loc);
            Marker davao = mMap.addMarker(new MarkerOptions()
                    .position(loc)
                    .title("Your Are Here")
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.addcust))
            );
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, 18.0f));
        }
    }
    private void addMarker(LatLng location) {
        if (mMap != null) {
            mMap.clear();
        }
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        Marker davao = mMap.addMarker(new MarkerOptions()
                .position(location)
                .title("Add Customer")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.addcust))
                );
        builder.include(davao.getPosition());//include to builder
        if(null!=myPosition) {
            Marker currentLocation = mMap.addMarker(new MarkerOptions()
                    .position(myPosition).title("Your Here!")
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.youarehere)));
            builder.include(currentLocation.getPosition());//include to builder
        }
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 18.0f));

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
                    android.app.Fragment fragment = (getActivity().getFragmentManager()
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
                android.app.Fragment fragment = (getActivity().getFragmentManager()
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
            //Intent intent = new Intent("android.location.GPS_ENABLED_CHANGE");
            //intent.putExtra("enabled", true);
            //this.sendBroadcast(intent);

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


}
