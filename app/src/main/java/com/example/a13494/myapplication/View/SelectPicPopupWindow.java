package com.example.a13494.myapplication.View;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Switch;
import android.widget.Toast;

import com.example.a13494.myapplication.Activity.MainActivity;
import com.example.a13494.myapplication.R;
import com.example.a13494.myapplication.Adapter.PopupItemAdapter;

import java.util.ArrayList;
import java.util.List;

public class SelectPicPopupWindow extends PopupWindow {

    //private Button item_popupwindows_camera;
    //private Button item_popupwindows_photo;
    //private Button item_popupwindows_cancel;
    private PopupItemAdapter popupItemAdapter;
    private ListView popupListView;
    private List<PopupItem> popupItemList = new ArrayList<>();
    private View menuview;
    private Context mContex;

    public SelectPicPopupWindow(Context context)
    {
        super(context);
        mContex = context;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        menuview = inflater.inflate(R.layout.pop_menu_view,null);
        popupListView = (ListView) menuview.findViewById(R.id.list_view);
        //初始化弹窗信息
        initPopupItems();
        popupItemAdapter = new PopupItemAdapter(context,R.layout.popup__menu_item,popupItemList);
        popupListView.setAdapter(popupItemAdapter);
        popupListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
               switch(position){
                   case 0:
                       //拍照
                       Toast.makeText(mContex, "take photo", Toast.LENGTH_SHORT).show();
                       break;
                   case 1:
                       Toast.makeText(mContex,"choose photo",Toast.LENGTH_SHORT).show();
                       //相册选择
                       break;
                   case 2:
                       //取消
                       dismiss();
                       break;
               }
           }
       });

        this.setContentView(menuview);
        this.setWidth(ViewGroup.LayoutParams.FILL_PARENT);
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setFocusable(true);
        //ColorDrawable dw = new ColorDrawable(0xb0000000);
        //this.setBackgroundDrawable(dw);

        menuview.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int height = menuview.findViewById(R.id.ll_pop_view).getTop();
                int y = (int) motionEvent.getY();
                if (motionEvent.getAction() == MotionEvent.ACTION_UP){
                    if (y<height){
                        dismiss();
                    }
                }
                return true;
            }
        });
    }


    public void initPopupItems()
    {

            PopupItem item1 = new PopupItem(R.drawable.camera,"拍照");
            popupItemList.add(item1);

            PopupItem item2 = new PopupItem(R.drawable.photo,"从相册中选择");
            popupItemList.add(item2);

            PopupItem item3 = new PopupItem(R.drawable.cancel,"取消");
            popupItemList.add(item3);

    }

}
