package com.gmk.geisa.activities.main;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.ParseException;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.gmk.geisa.Class.SyncDataClass;
import com.gmk.geisa.R;
import com.gmk.geisa.activities.personal.ForgotPasswordActivity;
import com.gmk.geisa.controller.RegisterController;
import com.gmk.geisa.databases.mDB;
import com.gmk.geisa.helper.Enkrip;
import com.gmk.geisa.model.PostResult;
import com.gmk.geisa.model.mSales;
import com.gmk.geisa.model.mSession;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;

import static android.Manifest.permission.READ_PHONE_STATE;

/**
 * A ceklogin screen that offers ceklogin via email/password.
 */
public class LoginActivity extends AppCompatActivity { /*implements LoaderCallbacks<Cursor> {*/

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_PHONE = 1;
    private mDB data;
    mSession login = null;
    SyncDataClass syncdata;
    String fcmid = "false";
    TelephonyManager telephony;
    RegisterController register;
    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "root:toor", "kenjin:ipang"
    };


    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView, mUsernameView, mSerialView;
    private View mLoginFormView;
    private TextInputLayout registerView, registerEmailView;
    private TextView mErrorserial;
    private mSession session;
    private String imei, pnumber, pass, email;
    private Context context;
    Button mEmailSignInButton;
    ProgressDialog dialog;
    TextView messageWithSpannableLinkTextView;

    //callback login
    RegisterController.CallBackGetUsers callback = new RegisterController.CallBackGetUsers() {
        @Override
        public void resultReady(ArrayList<mSession> users, PostResult respon) {
            if (respon.isErrNot()) {
                // Log.e("sampai sini", "sampai sini " + respon.getMsgC());
                if (cekloginNames(respon.getMsgC())) {
                    //   Log.e("sampai sini", "masuk sini");
                    if (ceklogin(respon.getMsgC(), respon.getMsgD())) { //cek login local sama tidak username password
                        //     Log.e("sampai sini", "ok login sini");
//cek untuk respon message F
                        login(respon.getMsgC(), respon.getMsgD(), respon.getMsgE(), respon.getMsgF(), respon.getMsgG(), respon.getMsgBol());
                        //update sales email nanti ditambahkan

                    } else {
                        if (dialog != null) {
                            dialog.dismiss();
                        }
                        mErrorserial.setText(R.string.error_incorrect_password);
                        if (respon.getMsgBol()) {
                            registerView.setVisibility(View.VISIBLE);
                            registerEmailView.setVisibility(View.VISIBLE);
                        }

                    }
                } else {
                    if (dialog != null) {
                        dialog.dismiss();
                    }
                    //    Log.e("sampai sini gagal", "sampai sini");
                    mErrorserial.setText(respon.getErrMsg());
                    if (respon.getMsgBol()) {
                        registerView.setVisibility(View.VISIBLE);
                        registerEmailView.setVisibility(View.VISIBLE);
                    }

                }

            } else {
                if (dialog != null) {
                    dialog.dismiss();
                }
                mErrorserial.setText(respon.getErrMsg());
                if (respon.getMsgBol()) {
                    registerView.setVisibility(View.VISIBLE);
                    registerEmailView.setVisibility(View.VISIBLE);
                }

            }
            /*if (dialog != null) {
                dialog.dismiss();
            }*/

        }

    };
    //callback register
    RegisterController.CallBackGetLogin callbacklogin = new RegisterController.CallBackGetLogin() {
        @Override
        public void resultReady(PostResult result) {

            if (null != result) {
                //Log.e("masuk cb register",result.getErrMsg().toString()+","+result.isErrNot());
                if (result.isErrNot()) {
                    // Log.e("hasil register", "bukan register0" + result.isErrNot() + "," + result.getMsgBol());
                    //cek apa new register atau tidak
                    if (result.getMsgBol()) {
                        //cek keserver data user
                        register.setCallBackGetUsers(callback);
                        if (cekserial(true)) {
                            //serial sudah ada dan hasilnya sama
                            if (session != null) {
                                //download user
                                register.GetUserFromServer(result.getMsgC(), result.getMsgD(), result.getMsgE(), result.getMsgF(), result.getMsgG(), result.getMsgBol());
                            } else {
                                if (dialog != null) {
                                    dialog.dismiss();
                                }
                            }
                        } else {
                            //error serial
                            //  Log.e("register","malah kesini");
                            if (dialog != null) {
                                dialog.dismiss();
                            }
                        }
                    } else {
                        Log.e("hasil register2", "bukan register1 " + result.getMsgC() + "," + result.getMsgBol());
                        //cek username sudah terdaftar diandroid
                        if (cekloginNames(result.getMsgC())) {
                            //Log.e("hasil register2","cek login ok"+result.isErrNot()+","+result.getMsgBol());
                            //cek username dan password
                            if (ceklogin(result.getMsgC(), result.getMsgD())) { //dilokal ada dan sesuai
                                login(result.getMsgC(), result.getMsgD(), result.getMsgE(), result.getMsgF(), "", result.getMsgBol());
                            } else {
                                register.setCallBackGetUsers(callback);
                                register.GetUserFromServer(result.getMsgC(), result.getMsgD(), result.getMsgE(), result.getMsgF(), "CEKLOGIN", result.getMsgBol());
                                /*if (dialog != null) {
                                    dialog.dismiss();
                                }
                                mErrorserial.setText(R.string.error_incorrect_password);
                                if (result.getMsgBol()) {
                                    registerView.setVisibility(View.VISIBLE);
                                    //registerEmailView.setVisibility(View.VISIBLE);
                                }*/
                                //registerEmailView.setVisibility(View.VISIBLE);
                            }
                        } else {
                            register.cekinternet();
                            register.setCallBackGetUsers(callback);
                            register.GetUserFromServer(result.getMsgC(), result.getMsgD(), result.getMsgE(), result.getMsgF(), result.getMsgG(), result.getMsgBol());

                        }

                    }

                } else {
                    if (dialog != null) {
                        dialog.dismiss();
                    }
                    if (null != result.getErrTag() && result.getErrTag().equalsIgnoreCase("noserial")) {
                        mErrorserial.setText(getString(R.string.error_incorrect_serial));
                        registerView.setVisibility(View.VISIBLE);
                        registerEmailView.setVisibility(View.VISIBLE);
                    } else if (null != result.getErrTag() && result.getErrTag().equalsIgnoreCase("maxsersial")) {
                        mErrorserial.setText(getString(R.string.error_incorrect_max_serial));
                        registerView.setVisibility(View.VISIBLE);
                        registerEmailView.setVisibility(View.VISIBLE);
                    } else {
                        mErrorserial.setText(result.getErrMsg());
                        registerView.setVisibility(View.VISIBLE);
                        registerEmailView.setVisibility(View.VISIBLE);
                    }
                    if (!result.getMsgBol()) {
                        registerView.setVisibility(View.GONE);
                        registerEmailView.setVisibility(View.GONE);
                    }

                }
            } else {
                mErrorserial.setText("Error On Register please make sure you have internet access");
                registerView.setVisibility(View.VISIBLE);
                registerEmailView.setVisibility(View.VISIBLE);
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
           /* if (dialog != null) {
                dialog.dismiss();
            }*/

        }
    };

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        register = RegisterController.getInstance(this);
        data = mDB.getInstance(this);
        context = this;
        syncdata = new SyncDataClass(this);
        //register.setCallBackGetUsers(callback);
        register.setCallBackgetLogin(callbacklogin);
        // sessionController = new SessionController(this);
        session = new mSession();


        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        if (mayRequestPhone()) {
            getPhoneNumber();
        }
        // populateAutoComplete();
        mUsernameView = (EditText) findViewById(R.id.username);
        mPasswordView = (EditText) findViewById(R.id.password);
        mSerialView = (EditText) findViewById(R.id.serial);
        mErrorserial = (TextView) findViewById(R.id.errorserial);
        messageWithSpannableLinkTextView = (TextView) findViewById(R.id.txtLostpassword);

        //mLoginFormView = findViewById(R.id.login_form);
        // mProgressView = findViewById(R.id.login_progress);
        // progressview = findViewById(R.id.progressview);

        registerView = (TextInputLayout) findViewById(R.id.registerView);
        registerEmailView = (TextInputLayout) findViewById(R.id.registerEmailView);
        cekserial(false);

        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(mPasswordView.getWindowToken(), 0);
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager imm = (InputMethodManager) getSystemService(context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(mEmailSignInButton.getWindowToken(), 0);

                attemptLogin();
            }
        });

        //forgot password
        SpannableString spannableString = new SpannableString(getString(R.string.defaultpassword));
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
                startActivity(new Intent(LoginActivity.this, ForgotPasswordActivity.class));
            }
        };

        spannableString.setSpan(clickableSpan, 0,
                spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        messageWithSpannableLinkTextView.setText(spannableString, TextView.BufferType.SPANNABLE);
        messageWithSpannableLinkTextView.setMovementMethod(LinkMovementMethod.getInstance());


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //  Toast.makeText(getApplicationContext(), "Service Task destroyet", Toast.LENGTH_LONG).show();
        if (dialog != null) {
            dialog.dismiss();
        }
    }

    private boolean mayRequestPhone() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (checkSelfPermission(READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        if (shouldShowRequestPermissionRationale(READ_PHONE_STATE)) {
            Snackbar.make(mEmailView, R.string.permission_rationale, Snackbar.LENGTH_INDEFINITE)
                    .setAction(android.R.string.ok, new View.OnClickListener() {
                        @Override
                        @TargetApi(Build.VERSION_CODES.M)
                        public void onClick(View v) {
                            requestPermissions(new String[]{READ_PHONE_STATE}, REQUEST_READ_PHONE);
                        }
                    });
        } else {
            requestPermissions(new String[]{READ_PHONE_STATE}, REQUEST_READ_PHONE);
        }
        return false;
    }

    private boolean cekserial(boolean regist) {
        boolean hasil = false;
        session = data.getSessionByVal("serialdata");
        if (session != null) {
            Log.e("isi session", session.getNilai1() + " x1x" + session.getNilai2() + " x2x" + session.getNilai3() + " x3x" + session.getNilai4() + " x4x" + session.getNama() + " xnx");

            if (session.getNilai4() != null && session.getNilai4().equalsIgnoreCase(imei)) {
                if (!session.getNilai2().equalsIgnoreCase("null")) {
                    Calendar cal1 = Calendar.getInstance();
                    SimpleDateFormat smp11 = new SimpleDateFormat("yyyy-MM-dd");
                    Date limitDate1 = null;
                    Date sekarang1 = null;
                    try {
                        limitDate1 = smp11.parse(session.getNilai2());
                        sekarang1 = smp11.parse(smp11.format(cal1.getTime()));
                        //cek lagi
                        //Log.e("isi tgl",sekarang1.toString()+","+ limitDate1);
                        if (!limitDate1.after(sekarang1)) {
                            if (!regist) {
                                Toast.makeText(getApplicationContext(), R.string.error_expired_tgl, Toast.LENGTH_SHORT).show();
                                mErrorserial.setText(R.string.error_expired_tgl);
                                registerView.setVisibility(View.VISIBLE);
                                registerEmailView.setVisibility(View.VISIBLE);
                            } else {
                                mErrorserial.setText(R.string.error_expired_tgl);
                            }
                            hasil = false;
                        } else {
                            if (!regist) {
                                registerView.setVisibility(View.GONE);
                                registerEmailView.setVisibility(View.GONE);
                            }
                            hasil = true;
                        }
                        //untuk cek register dulu
                        /*if(!regist) {
                            registerView.setVisibility(View.VISIBLE);
                        }*/
                    } catch (ParseException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                        if (!regist) {
                            registerView.setVisibility(View.VISIBLE);
                            registerEmailView.setVisibility(View.VISIBLE);
                        }
                        hasil = false;
                    } catch (java.text.ParseException e) {
                        e.printStackTrace();
                        if (!regist) {
                            registerView.setVisibility(View.VISIBLE);
                            registerEmailView.setVisibility(View.VISIBLE);
                        }
                        hasil = false;
                    }

                } else {

                    if (!regist) {
                        Toast.makeText(getApplicationContext(), R.string.error_expired_tgl, Toast.LENGTH_SHORT).show();
                        mErrorserial.setText(R.string.error_expired_tgl);
                        registerView.setVisibility(View.VISIBLE);
                        registerEmailView.setVisibility(View.VISIBLE);
                    } else {
                        mErrorserial.setText(R.string.error_expired_tgl);
                    }
                    hasil = false;
                }
            } else {
                Log.e(" imei nul", "imei null");
                if (!regist) {
                    Toast.makeText(getApplicationContext(), R.string.error_incorrect_imei, Toast.LENGTH_SHORT).show();
                    mErrorserial.setText(R.string.error_incorrect_imei);
                    registerView.setVisibility(View.VISIBLE);
                    registerEmailView.setVisibility(View.VISIBLE);
                } else {
                    mErrorserial.setText(R.string.error_incorrect_imei);
                }
                hasil = false;
            }
        } else {
            if (!regist) {
                // Toast.makeText(getApplicationContext(), R.string.error_expired_tgl, Toast.LENGTH_SHORT).show();
                mErrorserial.setText(R.string.error_warning_internet);
                registerView.setVisibility(View.VISIBLE);
                registerEmailView.setVisibility(View.VISIBLE);
            } else {
                mErrorserial.setText(R.string.error_on_register);
                registerView.setVisibility(View.VISIBLE);
                registerEmailView.setVisibility(View.VISIBLE);
            }
            hasil = false;
        }
        mErrorserial.setVisibility(View.VISIBLE);
        return hasil;
    }


    @SuppressLint("HardwareIds")
    public void getPhoneNumber() {
        TelephonyManager telephonyManager = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
        String phonenum, IMEI, devicename;
        try {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            phonenum = telephonyManager.getLine1Number();
            IMEI = telephonyManager.getDeviceId();
            devicename = Build.MODEL;
        } catch (Exception e) {
            phonenum = "Error!!";
            IMEI = "Error!!";
        }

        pnumber = phonenum;
        imei = IMEI;
        if (Build.FINGERPRINT.startsWith("generic") || Build.FINGERPRINT.contains("generic"))
            imei = "emulator";
        Log.e("isi imei", imei + " imenya");

    }


    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {

        if (requestCode == REQUEST_READ_PHONE) {
            // Log.e("setuju","setuju emei"+requestCode+","+grantResults.length+","+grantResults[0]+","+PackageManager.PERMISSION_GRANTED);
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // setEmei();
                getPhoneNumber();

            }

        }
    }

    /**
     * Set up the {@link android.app.ActionBar}, if the API is available.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void setupActionBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            // Show the Up button in the action bar.
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }
    }

    /**
     * Attempts to sign in or register the account specified by the ceklogin form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual ceklogin attempt is made.
     */

    private void attemptLogin() {

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        // Reset errors.
        mUsernameView.setError(null);
        mPasswordView.setError(null);
        mEmailView.setError(null);
        mSerialView.setError(null);

        // Store values at the time of the ceklogin attempt.
        String username = mUsernameView.getText().toString();
        String password = mPasswordView.getText().toString();
        String email = mEmailView.getText().toString();
        String serial = mSerialView.getText().toString();
        boolean newregister = false;
        if (registerView.getVisibility() == View.VISIBLE) {
            newregister = true;
        }

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }
        //cek username
        if (TextUtils.isEmpty(username)) {
            mUsernameView.setError(getString(R.string.error_field_required));
            focusView = mUsernameView;
            cancel = true;
        }

        if (newregister) {
            // Check for a valid email address.
            if (TextUtils.isEmpty(email)) {
                mEmailView.setError(getString(R.string.error_field_required));
                focusView = mEmailView;
                cancel = true;
            } else if (!isEmailValid(email)) {
                mEmailView.setError(getString(R.string.error_invalid_email));
                focusView = mEmailView;
                cancel = true;
            } else if (TextUtils.isEmpty(serial)) {
                mSerialView.setError(getString(R.string.error_field_required));
                focusView = mSerialView;
                cancel = true;
            }
        }

        if (cancel) {
            // There was an error; don't attempt ceklogin and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // perform the user ceklogin attempt.
            if (newregister) {
                ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

                NetworkInfo wifiNetwork = connectivityManager
                        .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
                NetworkInfo mobileNetwork = connectivityManager
                        .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
                boolean connected = false;

                if (wifiNetwork != null)
                    if (wifiNetwork.isConnectedOrConnecting())
                        connected = true;
                    else if (mobileNetwork != null)
                        if (mobileNetwork.isConnectedOrConnecting())
                            connected = true;
                if (connected) {
                    //Log.e("ada ngak si", String.valueOf(((GeisaApp) getApplicationContext()).isFirstCreate()));
                    // if (dialog == null)
                    dialog = new ProgressDialog(LoginActivity.this);
                    dialog.setMessage(getString(R.string.string_getting_json_message));
                    dialog.show();
                    if (newregister) {
                        register.cekinternet();
                    }
                    Enkrip enkrip = new Enkrip();
                    String enjrpan = enkrip.MD5_Hash(password);
                    register.getLogin(username, enjrpan, email, imei, serial, newregister);

                } else {

                    new AlertDialog.Builder(LoginActivity.this)
                            .setTitle("No Internet Connection")
                            .setMessage(
                                    "Activate your mobile data or wifi")
                            .setNeutralButton(
                                    "OK",
                                    new DialogInterface.OnClickListener() {

                                        @Override
                                        public void onClick(
                                                DialogInterface dialog,
                                                int which) {
                                            // TODO Auto-generated
                                            // method stub
                                            dialog.dismiss();
                                        }
                                    }).show();

                }
            } else {
                //if (dialog == null)
                dialog = new ProgressDialog(LoginActivity.this);
                dialog.setMessage(getString(R.string.string_getting_json_message));
                dialog.show();
                register.cekinternet();
                Enkrip enkrip = new Enkrip();
                String enjrpan = enkrip.MD5_Hash(password);
                register.getLogin(username, enjrpan, email, imei, serial, newregister);
            }


        }
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        // return email.contains("@");
        Matcher matcher = Patterns.EMAIL_ADDRESS.matcher(email);
        return matcher.matches();
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 2;
    }

    private boolean cekloginNames(String username) {
        mSession lgn = data.getLogins(username);
        if (lgn != null) {
            Log.e("isi login", lgn.getUserName());
            return true;
        } else {
            return false;
        }
    }

    protected boolean ceklogin(String username, String password) {
        boolean hsl = false;
        //  Log.e("isi login", username + "," + password);
        login = data.getLogin(username, password);
        if (login != null) {
            hsl = true;
        }
        return hsl;

    }

    private void login(String username, String password, String email, String emei, String serial, boolean reg) {
        //cek data keserver untuk allow tidaknya user semisal hp hilang jadi tidak bisa login setelah rubah password diserver
        if (reg) {
            String errMessage = "";
            if (login != null) {
                if (null != login.getImei() && !login.getImei().equalsIgnoreCase("") && !login.getImei().equalsIgnoreCase(emei)) {
                    errMessage = getString(R.string.error_incorrect_imei);
                     Log.e("masuk bed", login.getImei()+","+emei);
                    //mPasswordView.setError(errMessage);
                    //mPasswordView.requestFocus();
                    mErrorserial.setText(errMessage);
                    mErrorserial.setError(errMessage);
                    mErrorserial.setVisibility(View.VISIBLE);
                    if (dialog != null) {
                        dialog.dismiss();
                    }
                } else {
                    String fcm_id = FirebaseInstanceId.getInstance().getToken();
                    mSales sls = new mSales(username, password, login.getId(), fcm_id, emei, 1, login.getUserAlias(), email, Integer.parseInt(login.getUserSPV()), "", Integer.parseInt(login.getUserLevel()), Integer.parseInt(login.getAreaId()), login.getStatus(), Integer.parseInt(login.getRecId()));
                    register.updateUserServer(sls);
                    if (data.insertUpdateSessionByNama(login)) {
                        Intent inten = new Intent(LoginActivity.this, MainActivity.class);//berpindah ke activity yang lain dgn data
                        //inten.putExtra(MainActivity.session,login);
                        //inten.putExtra(CustomerActivity.KEY_DATA_NUMBER,123);
                        //startActivity(inte);
                        startActivityForResult(inten, 1);
                        finish();
                    }
                }


            } else {
                mErrorserial.setText("No Login Field");
                mErrorserial.setError(errMessage);
                mErrorserial.setVisibility(View.VISIBLE);
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        } else {
            if (serial.equalsIgnoreCase("CEKLOGIN")) {
                String errMessage = "";
                if (login != null) {
                    if (null != login.getImei() && !login.getImei().equalsIgnoreCase("") && !login.getImei().equalsIgnoreCase(emei)) {
                        errMessage = getString(R.string.error_incorrect_imei);
                        // Log.e("masuk bed", errMessage);
                        Log.e("masuk bed1", login.getImei()+","+emei);
                        //mPasswordView.setError(errMessage);
                        //mPasswordView.requestFocus();
                        mErrorserial.setText(errMessage);
                        mErrorserial.setError(errMessage);
                        mErrorserial.setVisibility(View.VISIBLE);
                        if (dialog != null) {
                            dialog.dismiss();
                        }
                    } else {
                        if (data.insertUpdateSessionByNama(login)) {
                            Intent inten = new Intent(LoginActivity.this, MainActivity.class);//berpindah ke activity yang lain dgn data
                            //inten.putExtra(MainActivity.session,login);
                            //inten.putExtra(CustomerActivity.KEY_DATA_NUMBER,123);
                            //startActivity(inte);
                            startActivityForResult(inten, 1);
                            finish();
                        }
                    }


                } else {
                    mErrorserial.setText("No Login Field");
                    mErrorserial.setError(errMessage);
                    mErrorserial.setVisibility(View.VISIBLE);
                    if (dialog != null) {
                        dialog.dismiss();
                    }
                }
            } else {
                register.setCallBackGetUsers(callback);
                register.GetUserFromServer(username, password, email, emei, "CEKLOGIN", reg);
            }
        }


    }
    // Log.e("isi err msg", errMessage);


}





