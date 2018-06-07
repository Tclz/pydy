package com.example.a13494.myapplication.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import com.example.a13494.myapplication.Adapter.GuidePagersAdapter;
import com.example.a13494.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class WelcomeActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener{

    private ViewPager viewPager;
    private GuidePagersAdapter guidePagersAdapter;
    private List<View> viewList;
    private ImageView imageViews[] = new ImageView[3];
    private int[] ids = {R.id.image_view1, R.id.image_view2, R.id.image_view3};
    private Button button;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_guide_pagers);
        initAllViews();
    }
    @SuppressLint("InflateParams")
    private void initAllViews()
    {    // load view
        // final LayoutInflater inflater = LayoutInflater.from(GuidePagersActivity.this);


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
        button=(Button)view3.findViewById(R.id.button_to_main);
        viewPager.addOnPageChangeListener(this);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent =new Intent(WelcomeActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
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
