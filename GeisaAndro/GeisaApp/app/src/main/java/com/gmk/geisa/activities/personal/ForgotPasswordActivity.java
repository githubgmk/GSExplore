package com.gmk.geisa.activities.personal;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.gmk.geisa.R;
import com.gmk.geisa.controller.RegisterController;
import com.gmk.geisa.databases.mDB;
import com.gmk.geisa.model.PostResult;
import com.gmk.geisa.model.mSession;

/**
 * A ceklogin screen that offers ceklogin via email/password.
 */
public class ForgotPasswordActivity extends AppCompatActivity { /*implements LoaderCallbacks<Cursor> {*/

    /**
     * Id to identity READ_CONTACTS permission request.
     */

    private mDB data;
    RegisterController register;
    // UI references.

    private EditText  mUsernameView;
    private Context context;
    Button mEmailSignInButton;
    ProgressDialog dialog;

    //callback login
    RegisterController.CallBackGetForgetPassword callback = new RegisterController.CallBackGetForgetPassword() {
        @Override
        public void resultReady(PostResult respon) {
            if(dialog!=null)
                dialog.dismiss();
            if (respon.isErrNot()) {
                Intent intent=new Intent(ForgotPasswordActivity.this,PasswordActivity.class);
                startActivity(intent);
            }else {
                Toast.makeText(ForgotPasswordActivity.this,"Problem With Internet Connection\n Please Try Again Latter",Toast.LENGTH_SHORT).show();
            }
        }

    };


    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        register = RegisterController.getInstance(this);
        data = mDB.getInstance(this);
        context = this;
        register.setCallBackGetForgetPassword(callback);

        mUsernameView = (EditText) findViewById(R.id.username);

        mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager imm = (InputMethodManager) getSystemService(context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(mEmailSignInButton.getWindowToken(), 0);
                attemptLogin();
            }
        });


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //  Toast.makeText(getApplicationContext(), "Service Task destroyet", Toast.LENGTH_LONG).show();
        if (dialog != null) {
            dialog.dismiss();
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            //getFragmentManager().popBackStack();
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void attemptLogin() {

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        // Reset errors.
        mUsernameView.setError(null);


        // Store values at the time of the ceklogin attempt.
        String username = mUsernameView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(username)) {
            mUsernameView.setError(getString(R.string.error_field_required));
            focusView = mUsernameView;
            cancel = true;
        }else if(!cekloginNames(username)){
            mUsernameView.setError(getString(R.string.error_incorrect_userlogin));
            focusView = mUsernameView;
            cancel = true;
        }


        if (cancel) {
            focusView.requestFocus();
        } else {
                //if (dialog == null)
                dialog = new ProgressDialog(ForgotPasswordActivity.this);
                dialog.setMessage(getString(R.string.string_getting_json_message));
                dialog.show();
                register.resetPassword(username);
        }
    }

    private boolean cekloginNames(String username) {
        mSession lgn = data.getLogins(username);
        if (lgn != null) {
            return true;
        } else {
            return false;
        }
    }


}





