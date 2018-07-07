package com.example.a13494.myapplication.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import com.example.a13494.myapplication.R;
import com.example.a13494.myapplication.util.HttpUtil;

import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnEditorAction;
import okhttp3.Call;
import okhttp3.Response;
import rx.internal.operators.BackpressureUtils;

import static com.example.a13494.myapplication.util.FormatTools.isPasswordValid;
import static com.example.a13494.myapplication.util.FormatTools.isPhoneNumberValid;
import static com.example.a13494.myapplication.util.NetWorkUtil.isNetworkAvaliable;

public class LoginActivity extends BaseActivity {


    @BindView(R.id.telephone)
    public AutoCompleteTextView mPhoneView;
    @BindView(R.id.password)
    public EditText mPasswordView;
    @BindView(R.id.click_to_sign_up)
    public TextView mCreateAccountView;
    @OnClick(R.id.click_to_sign_up)
    public void signUp()
    { startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
    }
    @BindView(R.id.click_to_modify_pwd)
    public TextView mForgetPasswordView;
    @OnClick(R.id.click_to_modify_pwd)
    public void forgetPwd()
    {
        startActivity(new Intent(LoginActivity.this, ForgotPasswordActivity.class));
    }
    @BindView(R.id.login_progress)
    public View mProgressView;
    @BindView(R.id.login_form)
    public View mLoginFormView;

    @BindView(R.id.sign_in_button)
    public Button SignInButton;
    @OnClick(R.id.sign_in_button)
    public void login()
    {
        attemptLogin();
    }

    @BindDrawable(R.drawable.phone)
    public Drawable dr_phone;
    @BindDrawable(R.drawable.password)
    public Drawable dr_password;
    public Boolean fileStatus = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        // Set up the login form.

        //mPhoneView = (AutoCompleteTextView) findViewById(R.id.telephone);
        ///dr_phone = getResources().getDrawable(R.drawable.phone);
        dr_phone.setBounds(0, 0, 50, 50);
        mPhoneView.setCompoundDrawables(dr_phone, null, null, null);

        //mPasswordView = (EditText) findViewById(R.id.password);
        //dr_password = getResources().getDrawable(R.drawable.password);
        dr_password.setBounds(0, 0, 50, 50);
        mPasswordView.setCompoundDrawables(dr_password, null, null, null);

        //mCheckBox = (CheckBox) findViewById(R.id.check_box);

        //mForgetPasswordView = (TextView) findViewById(R.id.click_to_modify_pwd);
        /*mForgetPasswordView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, ForgotPasswordActivity.class));
            }
        });*/

        /*mCreateAccountView = (TextView) findViewById(R.id.click_to_sign_up);
        mCreateAccountView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });*/

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

       // SignInButton = (Button) findViewById(R.id.sign_in_button);
       /* SignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });*/
        //mLoginFormView = findViewById(R.id.login_form);
        //mProgressView = findViewById(R.id.login_progress);
    }

    @Override
    protected void onResume()
    {
        getUPFromFile();
        super.onResume();
    }


    private void attemptLogin() {

        // Reset errors.
        mPhoneView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        final String telephone = mPhoneView.getText().toString();
        final String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;


        if (!isPhoneNumberValid(telephone)) {
            mPhoneView.setError(getString(R.string.error_invalid_phone));
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
            //判断网络状况
            boolean res  = isNetworkAvaliable(LoginActivity.this);
            if(!res)
            {
                Toast.makeText(LoginActivity.this, "网络异常，请检查网络设置",
                        Toast.LENGTH_SHORT).show();
                showProgress(false);
                return;
            }

            //提交账号密码
            HttpUtil.getInstance().sendLoginMsg(telephone, password,new okhttp3.Callback() {
                @Override
                public void onResponse(Call call, Response response) throws IOException {

                    String responseData = response.body().string();
                    System.out.println(response.code());
                    Log.d("LoginActivity", responseData);
                    Log.d("LoginActivity", responseData);
                    Log.d("LoginActivity", responseData);
                    Log.d("LoginActivity", responseData);
                    Log.d("LoginActivity", responseData);
                    Log.d("LoginActivity", responseData);
                    Log.d("LoginActivity", responseData);

                    if (responseData.equals("\"right\"")) {
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish();
                        saveUPInFile(telephone,password);
                        fileStatus = true;
                    } else {
                       runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                showProgress(false);
                                Toast.makeText(LoginActivity.this, "用户名/密码错误",
                                        Toast.LENGTH_SHORT).show();

                            }
                        });
                        fileStatus=false;
                        //startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        //finish();
                    }
                }
                @Override
                public void onFailure(Call call, IOException e) {
                   runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                showProgress(false);
                                Toast.makeText(LoginActivity.this, "登录异常",
                                        Toast.LENGTH_SHORT).show();
                                fileStatus=false;
                                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                finish();

                            }
                        });

                }
            });
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

    //用户名密码写入文件
    private void saveUPInFile(String phone,String password)
    {
        SharedPreferences.Editor editor = getSharedPreferences("data_up",0).edit();
        editor.putString("phone",phone);
        editor.putString("password",password);
        editor.apply();
    }

    //从文件中读取用户名密码
    private void getUPFromFile()
    {
        SharedPreferences pref=getSharedPreferences("data_up",0);
        SharedPreferences.Editor editor=pref.edit();
        String phone=pref.getString("phone","");
        String password=pref.getString("password","");
        if(isPhoneNumberValid(phone)&&isPasswordValid(password))
        {
            mPhoneView.setText(phone);
            mPasswordView.setText(password);
            attemptLogin();
            if(!fileStatus){
                editor.clear();
                editor.apply();
            }
        }
    }
}
