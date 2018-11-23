package com.gmk.geisa.activities.personal;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatCheckBox;
import android.text.InputType;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.gmk.geisa.R;
import com.gmk.geisa.controller.SessionController;
import com.gmk.geisa.helper.Enkrip;
import com.gmk.geisa.model.PostResult;
import com.gmk.geisa.model.mSession;


public class PasswordActivity extends AppCompatActivity {
    EditText password,oldpassword,repassword;
    public final static String sessiuser = "sesiuser";
    private final  static  String sessionForgotPassword="sessionForgotPass";
    //private PostResult forgotpassword;
    private  boolean forgotPassword=false;
    private SessionController sessionController;
    private mSession session,dataperson;
    private ProgressDialog pDialog;
    AppCompatCheckBox showPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);
        sessionController=SessionController.getInstance(this);
        this.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        password=(EditText) findViewById(R.id.password);
        password.setRawInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        oldpassword=(EditText) findViewById(R.id.oldpassword);
        repassword=(EditText) findViewById(R.id.repassword);
        showPassword=(AppCompatCheckBox) findViewById(R.id.showPassword);
        if (getIntent().getSerializableExtra(sessiuser) != null) {
            session = (mSession) getIntent().getSerializableExtra(sessiuser);
            if(session!=null) {
                dataperson = sessionController.getPersonById(session.getId());
            }
        }

        if (getIntent().getSerializableExtra(sessionForgotPassword) != null) {
            forgotPassword = (boolean) getIntent().getSerializableExtra(sessionForgotPassword);
            oldpassword.setHint("Input Code");
        }

        showPassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    password.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    repassword.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);

                }else{
                    password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    repassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
            }
        });


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

            getMenuInflater().inflate(R.menu.menu_submit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            //getFragmentManager().popBackStack();
            finish();
        } else if (item.getItemId() == R.id.menuSubmit) {
            password.setError(null);
            repassword.setError(null);
            oldpassword.setError(null);

            boolean cancel = false;
            View focusView = null;


            String voldpassword = oldpassword.getText().toString();
            String vpassword = password.getText().toString();
            String vrepassword = repassword.getText().toString();
            Enkrip enkrip = new Enkrip();
            String enjrpan = enkrip.MD5_Hash(voldpassword);
            String oldpass="";
            //String newpass = enkrip.MD5_Hash(vpassword);
            String renewpass = enkrip.MD5_Hash(vrepassword);
            if(dataperson!=null){
                oldpass=dataperson.getUserPass();
            }

            if (TextUtils.isEmpty(voldpassword)) {
                oldpassword.setError("Must Fill");
                focusView = oldpassword;
                cancel = true;
            } else if (!enjrpan.equalsIgnoreCase(oldpass) && !forgotPassword) {
                oldpassword.setError("Wrong Old Password");
                focusView = oldpassword;
                cancel = true;
            } else if (TextUtils.isEmpty(vpassword)) {
                password.setError("Must Fill");
                focusView = password;
                cancel = true;
            } else if (TextUtils.isEmpty(vrepassword)) {
                repassword.setError("Must Fill");
                focusView = repassword;
                cancel = true;
            } else if (!vpassword.equalsIgnoreCase(vrepassword)) {
                repassword.setError("Password Not Match");
                focusView = repassword;
                cancel = true;
            }

            if (cancel) {
                // There was an error; don't attempt login and focus the first
                // form field with an error.
                focusView.requestFocus();
            } else {
                this.getWindow().setSoftInputMode(
                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                View view = this.getCurrentFocus();
                if (view != null) {
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
                /*pDialog = new ProgressDialog(this);
                pDialog.setMessage("Submit Data Ke Server ...");
                pDialog.setCancelable(false);
                pDialog.show();*/
                dataperson.setStatusChange(1);
                dataperson.setUserPass(renewpass);
                dataperson.setOldPass(oldpass);

                sessionController.UpdateUserLogin(dataperson);//sesuaikan untuk reset password
                finish();
              //  matikan = new AsyncSubmit().execute();
                //startActivityForResult(inten, 11);*/
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
