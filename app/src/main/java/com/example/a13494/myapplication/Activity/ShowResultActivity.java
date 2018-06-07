package com.example.a13494.myapplication.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.example.a13494.myapplication.Entity.ClothGoods;
import com.example.a13494.myapplication.R;
import com.example.a13494.myapplication.Adapter.GoodsAdapter;

import java.util.ArrayList;
import java.util.Random;
public class ShowResultActivity extends AppCompatActivity {


    private ClothGoods[] clothes = {new ClothGoods("abb",20,null,null,null,R.drawable.test1,"https://click.simba.taobao.com/cc_im?p=%B3%C4%C9%C0&s=1512165146&k=557&e=D9%2F2b5nuBwzPIzl32eqm3DDgdpPDAreHwsyh05dIj%2FzOk6bTvyzNzOjnHmo4ZK21bNIrdYYgyFwYnRa3o0S3rGlSWMi5kR1pmLKIbuIgysIahKDXSB0H6K94mZ65zXXxoKLJUnK1AUTmnh95qU2HL08lwuVVss5IedWRq7BXxE%2BhgXctUZAeeqNFCIuCzeSB2PtZQP8R8LGQmKfWwUQaoKpiyY0L59ZvLhH0U%2BRDmwSmbUp16Zar8SKr1syFmeKtBcPkajRKV0s03tCJkJDhuLX80INSOnTf2POInD5h1J%2FRLkQqnnVGlNFIqmk5jwVlCdyhMH64uKB1w9C6jCwcGkCFjAXoQJsqV%2BomSN4tAspUGeld1MR%2BFfwM%2BsqntI5CmKciQ135DEs%2BiCfKbE4R5pj%2F0RNm5HhzJttO7QrZaLg%2FTHQYQyZJ0Yvhhv4th1xSKt8tD0FNFu6VM%2FxLJdO4O4VgR9wHN8LsoX1xC4N7Nx9X6iZI3i0Cyq%2FhHAzXeL6F2EBuN%2Brn%2FCQDFTAS%2BPzxqIBw2vP84%2FPll%2BkIr7LCwq8%3D"),
            new ClothGoods("abb",20,null,null,null,R.drawable.test2,null),
    new ClothGoods("abb",20,null,null,null,R.drawable.test3,null),
    new ClothGoods("abb",20,null,null,null,R.drawable.test4,null),
    new ClothGoods("abb",20,null,null,null,R.drawable.test5,null),
    new ClothGoods("abb",20,null,null,null,R.drawable.test6,null),
    new ClothGoods("abb",20,null,null,null,R.drawable.test7,null),
    new ClothGoods("abb",20,null,null,null,R.drawable.test8,null),
            new ClothGoods("abb",20,null,null,null,R.drawable.test9,null),
            new ClothGoods("abb",20,null,null,null,R.drawable.test10,null)};

    private ArrayList<ClothGoods>goodsList=new ArrayList<>();
    private GoodsAdapter adapter;
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_result);

        initGoods();

        recyclerView = (RecyclerView)findViewById(R.id.recyclerview);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new GoodsAdapter(goodsList);
        recyclerView.setAdapter(adapter);

    }

    public void initGoods()
    {
       goodsList.clear();
       for(int i =0;i<50;i++)
       {
           Random random = new Random();
           int index = random.nextInt(clothes.length);
           goodsList.add(clothes[index]);
       }


    }
}
