package com.example.a13494.myapplication.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.a13494.myapplication.util.ActivityCollector;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public class BaseActivity extends AppCompatActivity {
    //Unbinder binder;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        ActivityCollector.addActivity(this);
        super.onCreate(savedInstanceState);
        //binder=ButterKnife.bind(this);
    }
    @Override
    protected void onDestroy()
    {
        ActivityCollector.removeActivity(this);
        //binder.unbind();
        super.onDestroy();
    }
}
