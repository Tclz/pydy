package com.example.a13494.myapplication.activity;

import android.os.Handler;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import com.example.a13494.myapplication.adapter.CardStackAdapter;
import com.example.a13494.myapplication.entity.ClothGoods;
import com.example.a13494.myapplication.R;
import com.example.a13494.myapplication.util.ResponseData;
import com.loopeer.cardstack.AllMoveDownAnimatorAdapter;
import com.loopeer.cardstack.CardStackView;
import com.loopeer.cardstack.UpDownAnimatorAdapter;
import com.loopeer.cardstack.UpDownStackAnimatorAdapter;
//import static com.example.a13494.myapplication.util.ImageUtil.bitmapRecycle;

import java.util.ArrayList;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CardStackActivity extends BaseActivity implements CardStackView.ItemExpendListener{



    private ArrayList<ClothGoods> goodslist = ResponseData.getInstance().getClothList();
    @BindView(R.id.stackview_main)
    public CardStackView mStackView;
    private CardStackAdapter mTestStackAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_stack);
        ButterKnife.bind(this);
        //initGoods();
        mStackView = (CardStackView) findViewById(R.id.stackview_main);
        mStackView.setItemExpendListener(this);
        mTestStackAdapter = new CardStackAdapter(this);
        mStackView.setAdapter(mTestStackAdapter);


        new Handler().postDelayed(
                new Runnable() {
                    @Override
                    public void run() {
                        mTestStackAdapter.updateData(goodslist);
                    }
                }
                , 200
        );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_all_down:
                mStackView.setAnimatorAdapter(new AllMoveDownAnimatorAdapter(mStackView));
                break;
            case R.id.menu_up_down:
                mStackView.setAnimatorAdapter(new UpDownAnimatorAdapter(mStackView));
                break;
            case R.id.menu_up_down_stack:
                mStackView.setAnimatorAdapter(new UpDownStackAnimatorAdapter(mStackView));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /*public void onPreClick(View view) {
        mStackView.pre();
    }

    public void onNextClick(View view) {
        mStackView.next();
    }*/
    @Override
    public void onItemExpend(boolean expend) {
        //mActionButtonContainer.setVisibility(expend ? View.VISIBLE : View.GONE);
    }

    @Override
    protected void onStop()
    {
       //bitmapRecycle();
        super.onStop();
    }
    @Override
    protected void onResume()
    {
        mTestStackAdapter.updateData(goodslist);
        super.onResume();
    }
}
