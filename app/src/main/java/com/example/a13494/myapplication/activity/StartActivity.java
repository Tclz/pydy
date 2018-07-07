package com.example.a13494.myapplication.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.os.Bundle;
import android.view.WindowManager;

import com.example.a13494.myapplication.R;

public class StartActivity extends BaseActivity {

    private static final int DELAY = 2000;
    private static final int GO_GUIDE = 0;
    private static final int GO_HOME = 1;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    boolean welcome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getSupportActionBar()!=null)
        {
            getSupportActionBar().hide();
        }
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_start);
        initLayout();
    }
    private void initLayout() {
        pref = PreferenceManager.getDefaultSharedPreferences(this);
        welcome = pref.getBoolean("welcome",false);
        if (welcome) {
            handler.sendEmptyMessageDelayed(GO_HOME, DELAY);

        } else {
            handler.sendEmptyMessageDelayed(GO_GUIDE, DELAY);
            editor = pref.edit();
            editor.putBoolean("welcome",true);
            editor.apply();
        }
    }
    private  Handler handler =new Handler(){

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case GO_GUIDE: {
                    goGuide();
                    break;
                }
                case GO_HOME: {
                    goHome();
                    break;
                }
                default:
                    break;
            }
        }

    };
    private void goGuide()
    {
        Intent intent=new Intent(this,WelcomeActivity.class);
        startActivity(intent);
        finish();
    }
    private void goHome()
    {
        Intent intent=new Intent(this,LoginActivity.class);
        startActivity(intent);
        finish();
    }

}
