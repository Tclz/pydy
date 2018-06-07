package com.example.a13494.myapplication.Activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import com.example.a13494.myapplication.R;

import static android.Manifest.permission.READ_CONTACTS;
import static com.example.a13494.myapplication.Util.JudgeFormat.isPasswordValid;
import static com.example.a13494.myapplication.Util.JudgeFormat.isPhoneNumberValid;

public class LoginActivity extends AppCompatActivity {

    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private UserLoginTask mAuthTask = null;

    // UI references.

    private AutoCompleteTextView mPhoneView;
    private EditText mPasswordView;
    private TextView mCreateAccountView;
    private TextView mForgetPasswordView;
    private CheckBox mCheckBox;
    private View mProgressView;
    private View mLoginFormView;
    private Button SignInButton;
    private Drawable dr_phone;
    private Drawable dr_password;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        mPhoneView = (AutoCompleteTextView) findViewById(R.id.telephone);
        dr_phone = getResources().getDrawable(R.drawable.phone);
        dr_phone.setBounds(0,0,50,50);
        mPhoneView.setCompoundDrawables(dr_phone,null,null,null);

        mPasswordView = (EditText) findViewById(R.id.password);
        dr_password = getResources().getDrawable(R.drawable.password);
        dr_password.setBounds(0,0,50,50);
        mPasswordView.setCompoundDrawables(dr_password,null,null,null);

        mCheckBox =(CheckBox)findViewById(R.id.check_box);
        boolean isRemember = sharedPreferences.getBoolean("remember_password",false);
        if(isRemember){
            String account = sharedPreferences.getString("account","");
            String password = sharedPreferences.getString("password","");
            mPhoneView.setText(account);
            mPasswordView.setText(password);
            mCheckBox.setChecked(true);
        }

        mForgetPasswordView = (TextView)findViewById(R.id.click_to_modify_pwd);
        mForgetPasswordView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,ForgotPasswordActivity.class));
            }
        });

        mCreateAccountView = (TextView)findViewById(R.id.click_to_sign_up);
        mCreateAccountView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
            }
        });

        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        SignInButton = (Button) findViewById(R.id.sign_in_button);
        SignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });
        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
    }


    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mPhoneView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String telephone = mPhoneView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;


        if(!isPhoneNumberValid(telephone))
        {
            mPhoneView.setError(getString(R.string.error_field_required));
            focusView = mPhoneView;
            cancel = true;
        }
        if (!isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the test1
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            mAuthTask = new UserLoginTask(telephone, password);
            mAuthTask.execute((Void) null);
        }
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mTelephone;
        private final String mPassword;

        UserLoginTask(String telephone, String password) {
            mTelephone = telephone;
            mPassword = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {

            //判断网络状况



            editor = sharedPreferences.edit();
            if(mCheckBox.isChecked())
            {
                editor.putBoolean("remember_password",true);
                editor.putString("account",mTelephone);
                editor.putString("password",mPassword);
            }else{
                editor.clear();
            }
            editor.apply();

            //提交账号密码


          return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);

            if (success) {

                // 跳转到主页
                startActivity(new Intent(LoginActivity.this,MainActivity.class));
                finish();

            } else {
                mPasswordView.setError(getString(R.string.error_incorrect_password));
                mPasswordView.requestFocus();
            }
        }
        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }
}

