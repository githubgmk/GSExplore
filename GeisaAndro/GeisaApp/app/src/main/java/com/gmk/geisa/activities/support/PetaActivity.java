package com.gmk.geisa.activities.support;

import android.Manifest;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;
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
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.jaredrummler.android.processes.AndroidProcesses;
import com.gmk.geisa.R;
import com.gmk.geisa.controller.CustomerController;
import com.gmk.geisa.helper.LocationAssistant;
import com.gmk.geisa.helper.PermissionUtils;
import com.gmk.geisa.model.mCustomer;
import com.gmk.geisa.model.mSession;
import com.kenmeidearu.searchablespinnerlibrary.SearchableSpinner;
import com.kenmeidearu.searchablespinnerlibrary.mListString;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.google.android.gms.maps.GoogleMap.MAP_TYPE_HYBRID;
import static com.google.android.gms.maps.GoogleMap.MAP_TYPE_NONE;
import static com.google.android.gms.maps.GoogleMap.MAP_TYPE_NORMAL;
import static com.google.android.gms.maps.GoogleMap.MAP_TYPE_SATELLITE;
import static com.google.android.gms.maps.GoogleMap.MAP_TYPE_TERRAIN;

public class PetaActivity extends AppCompatActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
        AdapterView.OnItemSelectedListener, LocationListener, ActivityCompat.OnRequestPermissionsResultCallback,
        LocationAssistant.Listener {
    public final static String sessionUser = "sessionLogin";
    //maps
    private GoogleMap googleMap;
    private Location mLastLocations;
    // private LatLng myPosition;
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 1000;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    Marker currLocationMarker;
    boolean isLocationConnected;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private boolean mShowPermissionDeniedDialog = false;

    private CheckBox mTrafficCheckbox;
    private CheckBox mMyLocationCheckbox;
    private CheckBox mBuildingsCheckbox;
    private SearchableSpinner petaNamaLokasi;
    private Spinner mSpinner;
    //end peta
    private ProgressDialog pDialog = null;
    private AsyncTask<Void, Void, Void> matikan;
    mSession session = null;
    ArrayList<mCustomer> location;
    ArrayList<mListString> listStringsLokasi = new ArrayList<>();
    mCustomer singleLoc;
    // boolean spesific=false;
    MyAdapter myAdapter;
    String hariini = "";
    CustomerController customerController;
    private Calendar lastUpdateTime;
    boolean updateperiodic = false;
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    private LocationAssistant assistant;
    private TextView tvLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peta);
        customerController = CustomerController.getInstance(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("All Customer Map");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        petaNamaLokasi = (SearchableSpinner) findViewById(R.id.petaNamaLokasi);
        assistant = new LocationAssistant(this, this, LocationAssistant.Accuracy.HIGH, 500, false);
        assistant.setVerbose(true);


        if (getIntent().getSerializableExtra(sessionUser) != null) {
            session = (mSession) getIntent().getSerializableExtra(sessionUser);
        }
        Calendar now = Calendar.getInstance();
        hariini = format.format(now.getTime());


        initialMapsType();
        buildGoogleApiClient();
        if (checkPlayServices()) {
            SupportMapFragment mapFragment =
                    (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);
        }

        petaNamaLokasi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //  Log.e("tes posisi", id + " ," + position + "," + listStringsLokasi.get(position).get_id());
                if (position != 0) {
                    updateMyNewLocation(position);
                } else {
                    addMarkers(false);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        assistant.start();

    }

    @Override
    protected void onPause() {
        assistant.stop();
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (pDialog != null) {
            if (pDialog.isShowing()) {
                pDialog.dismiss();
            }
            pDialog = null;
        }
    }


    public static Date getZeroTimeDate(Date fecha) {
        Date res = fecha;
        Calendar calendar = Calendar.getInstance();

        calendar.setTime(fecha);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        res = calendar.getTime();

        return res;
    }


    //crete menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.menu_refresh, menu);
        return true;
    }

    public void refreshListView() {
        //mSession ss = data.getSessionByVal("login", 1);
        //add pesen for all
        ArrayList<mCustomer> temploc;
        location = new ArrayList<>();
        listStringsLokasi = new ArrayList<>();
        listStringsLokasi.add(new mListString(0, "Customer List", "All Customer"));
        temploc = customerController.getAllCustomerAktif(session.getId());
        if (temploc != null) {
            int i = 1;
            for (mCustomer lv : temploc) {
                mListString isi = new mListString(i, lv.getCustName(), lv.getAliasName(), lv.getAddress(), lv.getCustStatusName());
                listStringsLokasi.add(isi);
                location.add(lv);
                i++;
            }
        }

        petaNamaLokasi.setAdapter(listStringsLokasi, 3, 3);

    }


    public class MyAdapter extends BaseAdapter {

        // ArrayList<mLocation> lokasiList = new ArrayList<>();
        private ArrayList<mCustomer> lokasiList;
        Context mcontext;

        public MyAdapter(Context context, ArrayList<mCustomer> items) {
            this.lokasiList = items;
            mcontext = context;
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return lokasiList.size();
        }

        @Override
        public Object getItem(int arg0) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public long getItemId(int arg0) {
            // TODO Auto-generated method stub
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;
            if (v == null) {
                LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = vi.inflate(R.layout.spinner_item_code, null);
            }
            mCustomer o = lokasiList.get(position);
            if (o != null) {
                TextView tt = (TextView) v.findViewById(R.id.spinnerHead);
                TextView bt = (TextView) v.findViewById(R.id.sub);
                TextView btdet = (TextView) v.findViewById(R.id.subdetail);
                if (tt != null) {
                    tt.setText(lokasiList.get(position).getCustName().toUpperCase());
                }
                if (bt != null) {
                    bt.setText(lokasiList.get(position).getAddress());
                }
                if (btdet != null) {
                    String jam = lokasiList.get(position).getCustStatusName();
                    btdet.setText(jam);
                }
            }
            return v;
        }

    }


    void addMarkers(boolean spesific) {
        boolean ISTHERECUSTOMER = false;
        //petaNamaLokasi = (Spinner) findViewById(R.id.petaNamaLokasi);
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        // Log.e("spesific", " x " + spesific);
        if (spesific) {
            if (singleLoc != null) {
                Log.e("single loc", " x " + singleLoc.getLat() + "," + singleLoc.getLng());
                //get data single
                pDialog = new ProgressDialog(this);
                pDialog.setMessage("Loading Data ...");
                pDialog.setCancelable(true);
                pDialog.show();
                mCustomer lokasisingle = customerController.getCustomerByCustIdAndSalesId(String.valueOf(singleLoc.getCustId()), String.valueOf(session.getId()));
                if (lokasisingle != null) {
                    // Log.e("jumlah lokasi", " x" + lokasisingle.size());
                    double lat = lokasisingle.getLat();
                    double lot = lokasisingle.getLng();
                    // if (lat != 0 && lot != 0) {
                    LatLng CUSTOMER = new LatLng(lat, lot);
                    Marker davao = googleMap.addMarker(new
                            MarkerOptions()
                            .position(CUSTOMER)
                            .title(lokasisingle.getCustName().toUpperCase())
                            .snippet(lokasisingle.getAddress())
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.addcust)));
                    builder.include(davao.getPosition());//include to builder
                    ISTHERECUSTOMER = true;


                    // }
                } else {

                    double lat = singleLoc.getLat();
                    double lot = singleLoc.getLng();
                    // if (lat != 0 && lot != 0) {
                    LatLng CUSTOMER = new LatLng(lat, lot);
                    Marker davao = googleMap.addMarker(new
                            MarkerOptions()
                            .position(CUSTOMER)
                            .title(singleLoc.getCustName().toUpperCase())
                            .snippet(singleLoc.getAddress())
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.addcust)));
                    builder.include(davao.getPosition());//include to builder
                    ISTHERECUSTOMER = true;


                    //  }
                }
                if (pDialog != null) pDialog.dismiss();
            }
        } else {
            if (location != null && location.size() > 0) {
                //   Log.e("ada lokasi", "ada lokasinya" + location.size());
                for (mCustomer loc : location) {
                    if (loc != null) {
                        //            Log.e("ada lokasi", "ada lokasinya" + loc.getEmail() + "," + loc.getLatitude() + "," + loc.getLongitude());
                        double lat = loc.getLat();
                        double lot = loc.getLng();
                        // if (lat != 0 && lot != 0) {

                        LatLng CUSTOMER = new LatLng(lat, lot);
                        Marker davao = googleMap.addMarker(new
                                MarkerOptions()
                                .position(CUSTOMER)
                                .title(loc.getCustName().toUpperCase())
                                .snippet(loc.getAddress())
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.addcust)));
                        builder.include(davao.getPosition());//include to builder
                        ISTHERECUSTOMER = true;

                        // }
                    }

                }
            }

        }


        LatLng locs = null;
        if (isLocationConnected) {
            locs = new LatLng(mLastLocations.getLatitude(), mLastLocations.getLongitude());
            Marker currentLocation = googleMap.addMarker(new MarkerOptions()
                    .position(locs).title("Your Here!")
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.you_are_here)));
            builder.include(currentLocation.getPosition());//include to builder
        }

        if (ISTHERECUSTOMER && spesific) {
            LatLngBounds bounds = builder.build();
            int padding = 80; // offset from edges of the map in pixels
            CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
            googleMap.moveCamera(cu);

        } else if (ISTHERECUSTOMER && !spesific) {
            if (locs != null)
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(locs, 12.0f));
        } else {
            // zoom in the camera to langlong
            if (mLastLocations != null) {
                // Log.e("update periodic","update peridic");
                updateperiodic = false;
                if (isLocationConnected) {
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(locs, 16.0f));
                }
                // animate the zoom process
                //googleMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);
            }
        }

    }

    //gps
    public void StatusGPSCheck() {
        final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        // Log.e("cekgps","cekgps");
        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            turnGPSOn();
            //Log.e("idupin","hidup");
            // buildAlertMessageNoGps();
        }
        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER))
            turnOnDataConnection(true, this.getApplicationContext());


    }

    private void turnGPSOn() {

        if (Build.VERSION.SDK_INT > 11) {
            //Intent intent = new Intent("android.location.GPS_ENABLED_CHANGE");
            //intent.putExtra("enabled", true);
            //this.sendBroadcast(intent);

            String provider = Settings.Secure.getString(getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
            //Log.e("idupin", "mulai cek ");
            if (!provider.contains("gps")) { //if gps is disabled
                final Intent poke = new Intent();
                poke.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
                poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
                poke.setData(Uri.parse("3"));
                sendBroadcast(poke);
                Toast.makeText(getApplicationContext(), "Aktifkan Location", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                //buildAlertMessageNoGps();
            }
        } else {
            String provider = Settings.Secure.getString(getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
            // Log.e("idupin", "mulai cek ");
            if (!provider.contains("gps")) { //if gps is disabled
                final Intent poke = new Intent();
                poke.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
                poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
                poke.setData(Uri.parse("3"));
                sendBroadcast(poke);
                Toast.makeText(getApplicationContext(), "hidup", Toast.LENGTH_SHORT).show();
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


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            //getFragmentManager().popBackStack();
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    //untuk maps
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.layers_spinner:
                updateMapType();
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // Do nothing.
    }

    void initialMapsType() {
        mSpinner = (Spinner) findViewById(R.id.layers_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.layers_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(adapter);
        mSpinner.setOnItemSelectedListener(this);

        mTrafficCheckbox = (CheckBox) findViewById(R.id.traffic);
        mMyLocationCheckbox = (CheckBox) findViewById(R.id.my_location);
        mBuildingsCheckbox = (CheckBox) findViewById(R.id.buildings);
    }

    private void updateMyNewLocation(int position) {

        singleLoc = location.get(position - 1);
        //Log.e("isi lokasi", position + "," + singleLoc.getId() + "," + singleLoc.getIdMember() + "," + singleLoc.getNamaMember());
        if (singleLoc != null) {
            googleMap.clear();
            if (currLocationMarker != null) {
                currLocationMarker.remove();
            }
            // Log.e("isi marker", singleLoc.getNamaMember());
            if (null != singleLoc.getCustName()) {
                addMarkers(true);
            } else {
                addMarkers(false);
            }
            //Toast.makeText(getApplicationContext(), "lokasinya " + singleLoc.getNama() + ":" + singleLoc.getKode(), Toast.LENGTH_SHORT).show();
        }

    }

    private void updateMapType() {
        // No toast because this can also be called by the Android framework in onResume() at which
        // point mMap may not be ready yet.
        if (googleMap == null) {
            return;
        }
        String layerName = ((String) mSpinner.getSelectedItem());
        if (layerName.equals(getString(R.string.normal))) {
            googleMap.setMapType(MAP_TYPE_NORMAL);
        } else if (layerName.equals(getString(R.string.hybrid))) {
            googleMap.setMapType(MAP_TYPE_HYBRID);
        } else if (layerName.equals(getString(R.string.satellite))) {
            googleMap.setMapType(MAP_TYPE_SATELLITE);
        } else if (layerName.equals(getString(R.string.terrain))) {
            googleMap.setMapType(MAP_TYPE_TERRAIN);
        } else if (layerName.equals(getString(R.string.none_map))) {
            googleMap.setMapType(MAP_TYPE_NONE);
        } else {
            Log.i("LDA", "Error setting layer with name " + layerName);
        }
    }

    private boolean checkReady() {
        if (googleMap == null) {
            Toast.makeText(this, R.string.map_not_ready, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }


    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        if (mShowPermissionDeniedDialog) {
            PermissionUtils.PermissionDeniedDialog
                    .newInstance(false).show(getSupportFragmentManager(), "dialog");
            mShowPermissionDeniedDialog = false;
        }
    }

    /**
     * Called when the Traffic checkbox is clicked.
     */
    public void onTrafficToggled(View view) {
        updateTraffic();
    }

    private void updateTraffic() {
        if (!checkReady()) {
            return;
        }
        Log.e("tes trafic", "enable trafic");
        googleMap.setTrafficEnabled(mTrafficCheckbox.isChecked());
    }

    /**
     * Called when the MyLocation checkbox is clicked.
     */
    public void onMyLocationToggled(View view) {
        updateMyLocation();
    }

    private void updateMyLocation() {
        if (!checkReady()) {
            return;
        }

        if (!mMyLocationCheckbox.isChecked()) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            googleMap.setMyLocationEnabled(false);
            return;
        }

        // Enable the pesen layer. Request the pesen permission if needed.
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            googleMap.setMyLocationEnabled(true);
        } else {
            // Uncheck the box until the layer has been enabled and request missing permission.
            mMyLocationCheckbox.setChecked(false);
            PermissionUtils.requestPermission(this, LOCATION_PERMISSION_REQUEST_CODE,
                    Manifest.permission.ACCESS_FINE_LOCATION, false);
        }
    }

    /**
     * Called when the Buildings checkbox is clicked.
     */
    public void onBuildingsToggled(View view) {
        updateBuildings();
    }

    private void updateBuildings() {
        if (!checkReady()) {
            return;
        }
        googleMap.setBuildingsEnabled(mBuildingsCheckbox.isChecked());
    }

    //real  maps
    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mGoogleApiClient.disconnect();
    }

    @Override
    public void onMapReady(GoogleMap gMap) {
        //Toast.makeText(this, "masuk", Toast.LENGTH_SHORT).show();
        googleMap = gMap;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            return;
        }
        googleMap.setMyLocationEnabled(true);
        // mGoogleApiClient.connect();
        mMyLocationCheckbox.setChecked(true);
        refreshListView();
        updateMapType();
        updateTraffic();
        updateMyLocation();
        updateBuildings();
        //getdata();
    }

    protected synchronized void buildGoogleApiClient() {
        // Toast.makeText(this, "buildGoogleApiClient", Toast.LENGTH_SHORT).show();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    public void onConnected(Bundle bundle) {
        //Toast.makeText(this, "onConnected", Toast.LENGTH_SHORT).show();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            return;
        }
        lastUpdateTime = Calendar.getInstance();
        mLocationRequest = new LocationRequest();
        //  mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(30000); //3 seconds
        mLocationRequest.setFastestInterval(10000); //1 seconds
        mLocationRequest.setSmallestDisplacement(10F); //1/10 meter

        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);

        Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        if (mLastLocation != null) {
            //place marker at current position
            //mGoogleMap.clear();
            //myPosition = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
            mLastLocations = mLastLocation;
            LatLng myPosition = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(myPosition);
            markerOptions.title("Current Position\n" + myPosition.latitude + "," + myPosition.longitude)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.you_are_here));
            //markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
            currLocationMarker = googleMap.addMarker(markerOptions);
            isLocationConnected = true;
            googleMap.clear();
            addMarkers(false);

        }


    }

    @Override
    public void onConnectionSuspended(int i) {
        Toast.makeText(this, "onConnectionSuspended", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Toast.makeText(this, "onConnectionFailed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLocationChanged(Location location) {

        if (Build.VERSION.SDK_INT >= 18)
            Log.e("check mock", location.isFromMockProvider() + " x");
        //myPosition = new LatLng(location.getLatitude(), location.getLongitude());
        //
        Calendar now = Calendar.getInstance();
        long difference = now.getTimeInMillis() - lastUpdateTime.getTimeInMillis();
        int minute = (int) (difference / DateUtils.MINUTE_IN_MILLIS);
        // Log.e("lokasi menit",String .valueOf(minute));
        if (location != null && mLastLocations != null) {
            if (location.distanceTo(mLastLocations) > 300 || minute >= 15) {
               /* if (minute > 15){
                    Log.e("lokasi berubah 15 menit",String .valueOf(minute));
                    lastUpdateTime = now;
                    hariini = format.format(now.getTime());
                    getdata();//cek lagi untuk update data pertama buka dan sudah ada data untuk hari itu (lebih enak last update dibuat pas get data)
                }*/
                // String distance = String.valueOf(location.distanceTo(mLastLocations));
                mLastLocations.setLongitude(location.getLongitude());
                mLastLocations.setLatitude(location.getLatitude());
                updateperiodic = true;
                googleMap.clear();
                addMarkers(false);
                /*String Text = "My current location is: " + "Latitude = "
                        + mLastLocations.getLatitude() + "Longitude = " + mLastLocations.getLongitude() + ":" + distance;
                Toast.makeText(getApplicationContext(), Text, Toast.LENGTH_LONG)
                        .show();*/
            }
        }

    }

    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil
                .isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Toast.makeText(getApplicationContext(),
                        "This device is not supported.", Toast.LENGTH_LONG)
                        .show();
                finish();
            }
            return false;
        }
        return true;
    }

    public boolean isMockLocationEnabled() {
        boolean isMock = false;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PackageManager pm = getPackageManager();
            List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);
            List<ActivityManager.RunningAppProcessInfo> processes = AndroidProcesses.getRunningAppProcessInfo(getApplicationContext());
            ArrayList<String> proses = new ArrayList<>();
            for (ActivityManager.RunningAppProcessInfo proc : processes) {
                proses.add(proc.processName);
            }
            for (ApplicationInfo applicationInfo : packages) {
                try {
                    PackageInfo packageInfo = pm.getPackageInfo(applicationInfo.packageName, PackageManager.GET_PERMISSIONS);
                    String[] requestedPermissions = packageInfo.requestedPermissions;
                    if (requestedPermissions != null) {
                        for (int i = 0; i < requestedPermissions.length; i++) {
                            //Log.e("isi packet reguest", applicationInfo.packageName);
                            if (requestedPermissions[i].equals("android.permission.ACCESS_MOCK_LOCATION")
                                    && !applicationInfo.packageName.equals(getPackageName())) {
                                for (String p : proses) {
                                    if (p.equalsIgnoreCase(applicationInfo.packageName)) {
                                        //Log.e("mock ada", "stop mocme");
                                        return true;
                                    }

                                }

                            }
                        }
                    }
                } catch (PackageManager.NameNotFoundException e) {
                    Log.e("err ada moc", e.toString());
                }
            }
        } else {
            isMock = !android.provider.Settings.Secure.getString(this.getContentResolver(), "mock_location").equals("0");
        }
        return isMock;
    }

    public boolean cekFakeGPS() {
        Log.e("lokasi fake", "ad" + Settings.Secure.getString(getContentResolver(), Settings.Secure.ALLOW_MOCK_LOCATION));
        if (Settings.Secure.getString(getContentResolver(), Settings.Secure.ALLOW_MOCK_LOCATION).equals("0"))
            return false;
        else return true;
    }

    //352437071771057
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] results) {
        if (requestCode != LOCATION_PERMISSION_REQUEST_CODE) {
            return;
        }

        if (PermissionUtils.isPermissionGranted(permissions, results,
                Manifest.permission.ACCESS_FINE_LOCATION)) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                return;
            }
            googleMap.setMyLocationEnabled(true);
            mMyLocationCheckbox.setChecked(true);
        } else {
            mShowPermissionDeniedDialog = true;
        }
        if (assistant.onPermissionsUpdated(requestCode, results))
            tvLocation.setOnClickListener(null);
    }
    //cek moke location start

    /*@Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (assistant.onPermissionsUpdated(requestCode, grantResults))
            tvLocation.setOnClickListener(null);
    }*/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        assistant.onActivityResult(requestCode, resultCode);
    }

    @Override
    public void onNeedLocationPermission() {
       // tvLocation.setText("Need\nPermission");
        /*tvLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                assistant.requestLocationPermission();
            }
        });*/
        assistant.requestAndPossiblyExplainLocationPermission();
    }

    @Override
    public void onExplainLocationPermission() {
        new AlertDialog.Builder(this)
                .setMessage(R.string.permissionExplanation)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        assistant.requestLocationPermission();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        //tvLocation.setOnClickListener(new View.OnClickListener() {
                        //    @Override
                        //    public void onClick(View v) {
                                assistant.requestLocationPermission();
                        //    }
                       // });
                    }
                })
                .show();
    }

    @Override
    public void onLocationPermissionPermanentlyDeclined(View.OnClickListener fromView,
                                                        DialogInterface.OnClickListener fromDialog) {
        new AlertDialog.Builder(this)
                .setMessage(R.string.permissionPermanentlyDeclined)
                .setPositiveButton(R.string.ok, fromDialog)
                .show();
    }

    @Override
    public void onNeedLocationSettingsChange() {
        new AlertDialog.Builder(this)
                .setMessage(R.string.switchOnLocationShort)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        assistant.changeLocationSettings();
                    }
                })
                .show();
    }


    @Override
    public void onFallBackToSystemSettings(View.OnClickListener fromView, DialogInterface.OnClickListener fromDialog) {
        new AlertDialog.Builder(this)
                .setMessage(R.string.switchOnLocationLong)
                .setPositiveButton(R.string.ok, fromDialog)
                .show();
    }


    @Override
    public void onNewLocationAvailable(Location location) {
        if (location == null) return;
        mLastLocations = location;
        /*tvLocation.setOnClickListener(null);
        tvLocation.setText(location.getLongitude() + "\n" + location.getLatitude());
        tvLocation.setAlpha(1.0f);
        tvLocation.animate().alpha(0.5f).setDuration(400);*/
    }

    AlertDialog dialog = null,dialogerr=null;

    @Override
    public void onMockLocationsDetected(View.OnClickListener fromView, DialogInterface.OnClickListener fromDialog, String mockApp,boolean isMock) {
        if (isMock) {
            if (dialog == null) {
                dialog = new AlertDialog.Builder(this)
                        .setMessage(getString(R.string.mockLocationMessage).concat("\nPlease Shut Down " + mockApp.toUpperCase() + " !!!!!\nDon't Cheat Me.."))
                        .setPositiveButton(R.string.ok, fromDialog)
                        .show();
            } else {
                dialog.setMessage(getString(R.string.mockLocationMessage).concat("\nPlease Shut Down " + mockApp.toUpperCase() + " !!!!!\nDon't Cheat Me.."));
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();

            }
        } else {
            Log.e("error moc","eror moc");
            dialog=null;
            //assistant.changeLocationSettings();
            if(dialogerr==null) {
                dialogerr = new AlertDialog.Builder(this).create();
            }
            dialogerr.setMessage(getString(R.string.switchOnLocationShort));
            dialogerr.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.ok), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    startActivityForResult(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS),101);
                    assistant.changeLocationSettings();
                    /*
                    String provider = Settings.Secure.getString(getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
                    if(provider.contains("gps")){ //if gps is enabled
                        final Intent poke = new Intent();
                        poke.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
                        poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
                        poke.setData(Uri.parse("3"));
                        sendBroadcast(poke);
                    }*/
                    //assistant.changeLocationSettings();
                }
            });
            dialogerr.setCanceledOnTouchOutside(false);
            dialogerr.show();
            //onNeedLocationSettingsChange();


        }
    }

    @Override
    public void onError(LocationAssistant.ErrorType type, String message) {
        // tvLocation.setText(getString(R.string.error));
    }
    //end moke location end

}
