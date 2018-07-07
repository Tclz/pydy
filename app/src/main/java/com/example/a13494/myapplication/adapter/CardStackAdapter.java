package com.example.a13494.myapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a13494.myapplication.activity.DetailActivity;
import com.example.a13494.myapplication.entity.ClothGoods;
import com.example.a13494.myapplication.R;
import com.example.a13494.myapplication.util.ResponseData;
import com.loopeer.cardstack.CardStackView;
import com.loopeer.cardstack.StackAdapter;

import java.io.IOException;
import java.util.ArrayList;

import static com.example.a13494.myapplication.util.ImageUtil.getBitmapForImgResourse;


public class CardStackAdapter extends StackAdapter<ClothGoods> {
    //public ArrayList<Bitmap>bitmaps = ResponseData.getInstance().getBitmaps();
    public static Integer [] COLOR_LIST = new Integer[]{
            R.color.color_1,
            R.color.color_2,
            R.color.color_3,
            R.color.color_4,
            R.color.color_5,
            R.color.color_6,
            R.color.color_9,
            R.color.color_10,
            R.color.color_12,
            R.color.color_13,
            R.color.color_14,
            R.color.color_15,
            R.color.color_16,
            R.color.color_17,
            R.color.color_18,
            R.color.color_19,
            R.color.color_20,
            R.color.color_21,
            R.color.color_22,
            R.color.color_24,
    };

    public CardStackAdapter(Context context) {
        super(context);
    }

    @Override
    public void bindView(final ClothGoods item, int position, CardStackView.ViewHolder holder) {
        if (holder instanceof ColorItemLargeHeaderViewHolder) {
            ColorItemLargeHeaderViewHolder h = (ColorItemLargeHeaderViewHolder) holder;
            h.onBind(item, position);
        }
        if (holder instanceof ColorItemWithNoHeaderViewHolder) {
            ColorItemWithNoHeaderViewHolder h = (ColorItemWithNoHeaderViewHolder) holder;
            h.onBind(item, position);
        }
        if (holder instanceof ColorItemViewHolder) {
            ColorItemViewHolder h = (ColorItemViewHolder) holder;
            h.onBind(item, position);
        }
    }

    @Override
    protected CardStackView.ViewHolder onCreateView(ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case R.layout.list_card_item_larger_header:
                view = getLayoutInflater().inflate(R.layout.list_card_item_larger_header, parent, false);
                return new ColorItemLargeHeaderViewHolder(view);
            case R.layout.list_card_item_with_no_header:
                view = getLayoutInflater().inflate(R.layout.list_card_item_with_no_header, parent, false);
                return new ColorItemWithNoHeaderViewHolder(view);
            default:
                view = getLayoutInflater().inflate(R.layout.list_card_item, parent, false);
                return new ColorItemViewHolder(view);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 6) {//TODO TEST LARGER ITEM
            return R.layout.list_card_item_larger_header;
        } else if (position == 10) {
            return R.layout.list_card_item_with_no_header;
        } else {
            return R.layout.list_card_item;
        }
    }

    static class ColorItemViewHolder extends CardStackView.ViewHolder {
        View mLayout;
        View mContainerContent;
        TextView mTextTitle;
        TextView tv_goods_detail;
        ImageView iv_cloth;

        public ColorItemViewHolder(View view) {
            super(view);
            mLayout = view.findViewById(R.id.frame_list_card_item);
            mContainerContent = view.findViewById(R.id.container_list_content);
            mTextTitle = (TextView) view.findViewById(R.id.text_list_card_title);
            tv_goods_detail = (TextView)view.findViewById(R.id.goods_detail);
            iv_cloth = (ImageView)view.findViewById(R.id.goods_image);
        }

        @Override
        public void onItemExpand(boolean b) {

            if(!b)
            {
                releaseImageViewResouce(iv_cloth);
            }
            mContainerContent.setVisibility(b ? View.VISIBLE : View.GONE);
        }

        public void onBind(final ClothGoods item, int position) {
            Integer clothId = item.getId()%20;
            mLayout.getBackground().setColorFilter(ContextCompat.getColor(getContext(),COLOR_LIST[clothId]), PorterDuff.Mode.SRC_IN);
            mTextTitle.setText(String.valueOf(position));
            String detail = item.getName()+'\n'+item.getLoc();
            tv_goods_detail.setText(detail);
            iv_cloth.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    Intent intent = new Intent(getContext(),DetailActivity.class);
                    intent.putExtra("imgUrl",item.getLink());
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    getContext().startActivity(intent);
                    return true;
                }
            });
            //iv_cloth.setImageResource(item.getImageId());
            try{
                getBitmapForImgResourse(getContext(),ResponseData.getInstance().getBitmaps().get(position),iv_cloth);
            }catch (IOException e)
            {
                e.printStackTrace();
            }
        }

    }

    static class ColorItemWithNoHeaderViewHolder extends CardStackView.ViewHolder {
        View mLayout;
        TextView mTextTitle;
        //TextView tv_goods_detail;
        ImageView iv_cloth;



        public ColorItemWithNoHeaderViewHolder(View view) {
            super(view);
            mLayout = view.findViewById(R.id.frame_list_card_item);
            mTextTitle = (TextView) view.findViewById(R.id.text_list_card_title);
            //tv_goods_detail = (TextView)view.findViewById(R.id.goods_detail);
            iv_cloth = (ImageView)view.findViewById(R.id.goods_image);

        }

        @Override
        public void onItemExpand(boolean b) {
            if(!b)
            {
                releaseImageViewResouce(iv_cloth);
            }
        }

        public void onBind(final ClothGoods item, int position) {
            Integer clothId = item.getId()%24;
            mLayout.getBackground().setColorFilter(ContextCompat.getColor(getContext(),COLOR_LIST[clothId]), PorterDuff.Mode.SRC_IN);
            mTextTitle.setText(String.valueOf(position));
            //String detail = item.getName()+'\n'+item.getLoc();
            //tv_goods_detail.setText(detail);
            iv_cloth.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    Intent intent = new Intent(getContext(),DetailActivity.class);
                    intent.putExtra("imgUrl",item.getLink());
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    getContext().startActivity(intent);
                    return true;
                }
            });

            //iv_cloth.setImageResource(item.getImageId());
            try{
                getBitmapForImgResourse(getContext(),ResponseData.getInstance().getBitmaps().get(position),iv_cloth);
            }catch (IOException e)
            {
                e.printStackTrace();
            }
        }

    }

    static class ColorItemLargeHeaderViewHolder extends CardStackView.ViewHolder {
        View mLayout;
        View mContainerContent;
        TextView mTextTitle;
        TextView tv_goods_detail;
        ImageView iv_cloth;

        public ColorItemLargeHeaderViewHolder(View view) {
            super(view);
            mLayout = view.findViewById(R.id.frame_list_card_item);
            mContainerContent = view.findViewById(R.id.container_list_content);
            mTextTitle = (TextView) view.findViewById(R.id.text_list_card_title);
            tv_goods_detail = (TextView)view.findViewById(R.id.goods_detail);
            iv_cloth = (ImageView)view.findViewById(R.id.goods_image);
        }

        @Override
        public void onItemExpand(boolean b){

            if(!b)
            {
                releaseImageViewResouce(iv_cloth);
            }
            mContainerContent.setVisibility(b ? View.VISIBLE : View.GONE);
        }


        @Override
        protected void onAnimationStateChange(int state, boolean willBeSelect) {
            super.onAnimationStateChange(state, willBeSelect);
            if (state == CardStackView.ANIMATION_STATE_START && willBeSelect) {
                onItemExpand(true);
            }
            if (state == CardStackView.ANIMATION_STATE_END && !willBeSelect) {
                onItemExpand(false);
            }
        }

        public void onBind(final ClothGoods item, int position){
            Integer clothId = item.getId()%24;
            mLayout.getBackground().setColorFilter(ContextCompat.getColor(getContext(),COLOR_LIST[clothId]), PorterDuff.Mode.SRC_IN);
            mTextTitle.setText(String.valueOf(position));
            String detail = item.getName()+'\n'+item.getLoc();
            tv_goods_detail.setText(detail);
            iv_cloth.setOnLongClickListener(new View.OnLongClickListener() {
                                                  @Override
                                                  public boolean onLongClick(View view) {
                                                      Intent intent = new Intent(getContext(),DetailActivity.class);
                                                      intent.putExtra("imgUrl",item.getLink());
                                                      intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                                      getContext().startActivity(intent);
                                                      return true;
                                                  }
                                              }

            );
            //iv_cloth.setImageResource(item.getImageId());
            try{
                getBitmapForImgResourse(getContext(),ResponseData.getInstance().getBitmaps().get(position),iv_cloth);
            }catch (IOException e)
            {
                e.printStackTrace();
            }

            itemView.findViewById(R.id.goods_detail).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((CardStackView) itemView.getParent()).performItemClick(ColorItemLargeHeaderViewHolder.this);
                }
            });
        }

    }

    public static void releaseImageViewResouce(ImageView imageView) {
        if (imageView == null) return;
        Drawable drawable = imageView.getDrawable();
        if (drawable != null && drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            Bitmap bitmap = bitmapDrawable.getBitmap();
            if (bitmap != null && !bitmap.isRecycled()) {
                bitmap.recycle();
            }
        }
    }
}