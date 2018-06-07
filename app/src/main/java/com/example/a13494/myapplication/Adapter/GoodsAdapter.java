package com.example.a13494.myapplication.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.a13494.myapplication.Activity.DetailActivity;
import com.example.a13494.myapplication.Entity.ClothGoods;
import com.example.a13494.myapplication.R;
import java.util.ArrayList;

public class GoodsAdapter extends RecyclerView.Adapter<GoodsAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<ClothGoods> goodsList;

    public void updateData(ArrayList<ClothGoods> list) {
        this.goodsList = list;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(mContext==null)
        {
            mContext=parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.goods_item,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        ClothGoods goods = goodsList.get(position);
        holder.cloth_description.setText(goods.getName());
        Glide.with(mContext).load(goods.getImageId()).into(holder.cloth_image);
        holder.cloth_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext,DetailActivity.class);
                int position = holder.getAdapterPosition();
                intent.putExtra("imgUrl",goodsList.get(position).getPurchaseLink());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);

            }
        });
    }
    @Override
    public int getItemCount()
    {
        return goodsList == null ? 0 : goodsList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{

        CardView cardView;
        ImageView cloth_image;
        TextView cloth_description;

        public ViewHolder(View view)
        {
            super(view);
            cardView = (CardView)view;
            cloth_image = (ImageView) view.findViewById(R.id.cloth_image);
            cloth_description = (TextView) view.findViewById(R.id.cloth_description);
        }
    }

    public GoodsAdapter(ArrayList<ClothGoods>goodsListist) {
        this.goodsList=goodsListist;
    }

}
