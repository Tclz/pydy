package com.example.a13494.myapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a13494.myapplication.R;
import com.example.a13494.myapplication.View.PopupItem;

import java.util.List;

public class PopupItemAdapter extends ArrayAdapter<PopupItem>{

    private int itemViewResourceId;

    public PopupItemAdapter(Context context, int itemViewId, List<PopupItem>popupItemList)
    {
        super(context,itemViewId,popupItemList);
        itemViewResourceId = itemViewId;

    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        PopupItem item = getItem(position);
        View view  ;
        ViewHolder viewHolder;
        if(convertView==null)
        {
            view = LayoutInflater.from(getContext()).inflate(itemViewResourceId,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.imageView = (ImageView)view.findViewById(R.id.pop_item_image);
            viewHolder.textView = (TextView)view.findViewById(R.id.pop_item_text);
            view.setTag(viewHolder);

        }else{
            view = convertView;
            viewHolder = (ViewHolder)view.getTag();
        }

        viewHolder.imageView.setImageResource(item.getImageId());
        viewHolder.textView.setText(item.getText());
        return view;

    }
    class ViewHolder {
        ImageView imageView;
        TextView textView;
    }

}
