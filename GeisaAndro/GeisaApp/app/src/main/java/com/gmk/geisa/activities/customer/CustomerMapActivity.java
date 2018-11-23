package com.gmk.geisa.activities.customer;

import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.gmk.geisa.R;

public class CustomerMapActivity extends AppCompatActivity {// implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,
        //GoogleApiClient.OnConnectionFailedListener, LocationListener {

    public final static String CUST_NAMA = "cstDetailNama";
    public final static String CUST_ALAMAT = "cstDetailAlamat";
    public final static String CUST_LAT = "cstDetailLat";
    public final static String CUST_LOT = "cstDetailLot";


   // private final LatLng HAMBURG = new LatLng(-6.17172746, 106.63929653);
    private Boolean ISTHERECUSTOMER = false;
   // private GoogleMap map;
   // private LatLng myPosition;
    private String Latitued, Longitude;

    //for maps
    // LogCat tag
    private static final String TAG = CustomerMapActivity.class.getSimpleName();

    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 1000;

    private Location mLastLocation;

    // Google client to interact with Google API
    private GoogleApiClient mGoogleApiClient;

    // boolean flag to toggle periodic location updates
    private boolean mRequestingLocationUpdates = false;

    private LocationRequest mLocationRequest;

    // Location updates intervals in sec
    private static int UPDATE_INTERVAL = 10000; // 10 sec
    private static int FATEST_INTERVAL = 2000; // 2 sec
    private static int DISPLACEMENT = 10; // 10 meters

    // UI elements
    private TextView cstNama, cstAlamat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_map);
        cstNama = (TextView) findViewById(R.id.cstMapsNama);
        cstAlamat = (TextView) findViewById(R.id.cstMapsAlamat);

        cstNama.setText(getIntent().getStringExtra(CUST_NAMA));
        cstAlamat.setText(getIntent().getStringExtra(CUST_ALAMAT));
        Latitued = getIntent().getStringExtra(CUST_LAT);
        Longitude = getIntent().getStringExtra(CUST_LOT);


        setTitle("Add Customer Maps");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (checkPlayServices()) {
            // Building the GoogleApi client
            buildGoogleApiClient();
            createLocationRequest();
        }
    }

    //untuk memantau tombol back
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            //getFragmentManager().popBackStack();
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

   /* @Override
    public void onMapReady(GoogleMap map) {
    }*/

    private void setUpMapIfNeeded() {
        // First we need to check availability of play services

       /* if (map == null) {
            map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
            LatLngBounds.Builder builder = new LatLngBounds.Builder();
            *//**//*
            Marker davao = map.addMarker(new MarkerOptions().position(HAMBURG).title("Hamburg City").snippet("Ateneo de Hamburg University").icon(BitmapDescriptorFactory.fromResource(R.drawable.cst_marker_location)));
            builder.include(davao.getPosition());//include to builder


            try {
                if (!Latitued.isEmpty() && !Longitude.isEmpty()) {
                    Double langi = Double.valueOf(Latitued);
                    Double longi = Double.valueOf(Longitude);
                    LatLng CustLocation = new LatLng(langi, longi);
                    Marker kiel = map.addMarker(new MarkerOptions()
                                    .position(CustLocation)
                                    .title(cstNama.getText().toString())
                                    .snippet(cstAlamat.getText().toString())
                                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.cst_marker_location))
                    );
                    builder.include(kiel.getPosition());//include to builder
                    ISTHERECUSTOMER = true;
                }
            } catch (Exception x) {

            }


            //get current location
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            map.setMyLocationEnabled(true);

            if (mLastLocation != null) {

                // Getting latitude of the current location
                double latitude = mLastLocation.getLatitude();

                // Getting longitude of the current location
                double longitude = mLastLocation.getLongitude();

                // Creating a LatLng object for the current location
                LatLng latLng = new LatLng(latitude, longitude);

                myPosition = new LatLng(latitude, longitude);
                Marker currentLocation = map.addMarker(new MarkerOptions()
                        .position(myPosition).title("Your Here!")
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.you_are_here)));
                builder.include(currentLocation.getPosition());//include to builder
                //end curent location

            }

            //handle zoom

            //for (Marker marker : markers) {
            //builder.include(davao.getPosition());
            //}
            if (ISTHERECUSTOMER) {
                LatLngBounds bounds = builder.build();
                int padding = 80; // offset from edges of the map in pixels
                CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
                map.moveCamera(cu);
                map.animateCamera(cu);

            } else {
                // zoom in the camera to langlong
                if (myPosition != null) {
                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(myPosition, 16.0f));

                    // animate the zoom process
                    map.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);
                }
            }


        }*/
    }


    //new latlang
    @Override
    protected void onStart() {
        super.onStart();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        checkPlayServices();

        // Resuming the periodic location updates
        if (mGoogleApiClient != null)
        if (mGoogleApiClient.isConnected() && mRequestingLocationUpdates) {
            startLocationUpdates();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient != null)
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopLocationUpdates();
    }

    /**
     * Method to display the location on UI
     */
    private void displayLocation() {

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mLastLocation = LocationServices.FusedLocationApi
                .getLastLocation(mGoogleApiClient);

        /*if (mLastLocation != null) {
            latitude = mLastLocation.getLatitude();
            longitude = mLastLocation.getLongitude();

            //lblLocation.setText(latitude + ", " + longitude);

        } else {

           // lblLocation
           //         .setText("(Couldn't get the location. Make sure location is enabled on the device)");
        }*/
        setUpMapIfNeeded();
    }

    /**
     * Method to toggle periodic location updates
     */
    private void togglePeriodicLocationUpdates() {
        if (!mRequestingLocationUpdates) {
            // Changing the button text
         /*   btnStartLocationUpdates
                    .setText(getString(R.string.btn_stop_location_updates));*/

            mRequestingLocationUpdates = true;

            // Starting the location updates
            startLocationUpdates();

            Log.d(TAG, "Periodic location updates started!");

        } else {
            // Changing the button text
        /*    btnStartLocationUpdates
                    .setText(getString(R.string.btn_start_location_updates));*/
            mRequestingLocationUpdates = false;

            // Stopping the location updates
            stopLocationUpdates();

            Log.d(TAG, "Periodic location updates stopped!");
        }
    }

    /**
     * Creating google api client object
     */
    protected synchronized void buildGoogleApiClient() {
      /*  mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();*/
    }

    /**
     * Creating location request object
     */
    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(UPDATE_INTERVAL);
        mLocationRequest.setFastestInterval(FATEST_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setSmallestDisplacement(DISPLACEMENT);
    }

    /**
     * Method to verify google play services on the device
     */
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

    /**
     * Starting the location updates
     */
    protected void startLocationUpdates() {

       /* if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient, mLocationRequest, this);*/

    }

    /**
     * Stopping location updates
     */
    protected void stopLocationUpdates() {
     /*   LocationServices.FusedLocationApi.removeLocationUpdates(
                mGoogleApiClient, this);*/
    }

    /**
     * Google api callback methods
     */
   /* @Override
    public void onConnectionFailed(ConnectionResult result) {
        Log.i(TAG, "Connection failed: ConnectionResult.getErrorCode() = "
                + result.getErrorCode());
    }

    @Override
    public void onConnected(Bundle arg0) {

        // Once connected with google api, get the location
        displayLocation();

        if (mRequestingLocationUpdates) {
            startLocationUpdates();
        }
    }*/

  /*  @Override
    public void onConnectionSuspended(int arg0) {
        mGoogleApiClient.connect();
    }

    @Override
    public void onLocationChanged(Location location) {
        // Assign the new location
        mLastLocation = location;

        // Toast.makeText(getApplicationContext(), "Location changed!",
        //         Toast.LENGTH_SHORT).show();

        // Displaying the new location on UI
        displayLocation();
    }*/

}
