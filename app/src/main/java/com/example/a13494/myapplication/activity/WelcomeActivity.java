package com.example.a13494.myapplication.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import com.example.a13494.myapplication.adapter.GuidePagersAdapter;
import com.example.a13494.myapplication.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WelcomeActivity extends BaseActivity implements ViewPager.OnPageChangeListener{

    private ViewPager viewPager;
    private GuidePagersAdapter guidePagersAdapter;
    private List<View> viewList;
    private ImageView imageViews[] = new ImageView[3];
    private int[] ids = {R.id.image_view1, R.id.image_view2, R.id.image_view3};
    @BindView(R.id.start)
    public ImageView start;
    @OnClick(R.id.start)
    public void start()
    {
        Intent intent =new Intent(WelcomeActivity.this,LoginActivity.class);
        startActivity(intent);
        finish();

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_guide_pagers);
        ButterKnife.bind(this);
        initAllViews();
    }
    @SuppressLint("InflateParams")
    private void initAllViews()
    {
        View view1=View.inflate(WelcomeActivity.this,R.layout.guide_view_page1,null);
        View view2=View.inflate(WelcomeActivity.this,R.layout.guide_view_pager2,null);
        View view3=View.inflate(WelcomeActivity.this,R.layout.guide_view_pager3,null);

        viewList = new ArrayList<>();
        viewList.add(view1);
        viewList.add(view2);
        viewList.add(view3);

        //Bind ImageView with Id
        for(int i=0;i<ids.length;i++)
        {
            imageViews[i]=(ImageView)findViewById(ids[i]);
        }

        viewPager=(ViewPager)findViewById(R.id.vp_guide);
        guidePagersAdapter=new GuidePagersAdapter(this,viewList);
        viewPager.setAdapter(guidePagersAdapter);
        //button=(Button)view3.findViewById(R.id.button_to_main);
        start = (ImageView)findViewById(R.id.start);
        viewPager.addOnPageChangeListener(this);

    }
    @Override
    public void onPageSelected(int position) {
        for (int i = 0; i <ids.length; i++) {
            if (i != position) {
                imageViews[i].setImageResource(R.drawable.unselected);
            } else {
                imageViews[i].setImageResource(R.drawable.selected);
            }
        }
    }
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }
    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
