package com.example.a13494.myapplication.view;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.example.a13494.myapplication.activity.MainActivity;
import com.example.a13494.myapplication.R;
import com.example.a13494.myapplication.adapter.PopupItemAdapter;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.example.a13494.myapplication.activity.MainActivity.imageName;
import static com.example.a13494.myapplication.activity.MainActivity.tempFile;
import static com.example.a13494.myapplication.activity.MainActivity.imageUri;

public class SelectPicPopupWindow extends PopupWindow {

    private PopupItemAdapter popupItemAdapter;
    private ListView popupListView;
    private List<PopupItem> popupItemList = new ArrayList<>();
    private View menuview;
    private Context mContex;
    //private Uri imageUri;
    //private static File tempFile;
    public ImageView picture;
    //private String imageName;
    private static final int PHOTO_REQUEST_CAREMA = 1;// 拍照
    private static final int CHOOSE_PHOTO = 3; //相册
    private static final int CROP_PHOTO = 2; //裁剪
    private MainActivity activity;
    //private WechatImagePicker rxImagePicker;
    //private static final int REQUEST_WRITE_EXTERNAL_STORAGE_PERMISSION_CAMERA = 99;
    //private static final int REQUEST_WRITE_EXTERNAL_STORAGE_PERMISSION_GALLERY = 100;

    public SelectPicPopupWindow(Context context) {
        super(context);
        mContex = context;
        activity = (MainActivity) mContex;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        menuview = inflater.inflate(R.layout.pop_menu_view, null);
        popupListView = (ListView) menuview.findViewById(R.id.list_view);

        //初始化弹窗信息
        initPopupItems();
        //初始化图片选择器
        //initRxImagePicker();
        popupItemAdapter = new PopupItemAdapter(context, R.layout.popup__menu_item, popupItemList);
        popupListView.setAdapter(popupItemAdapter);
        popupListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                switch (position) {
                    case 0:
                        //拍照
                        //checkPermissionAndRequest(REQUEST_WRITE_EXTERNAL_STORAGE_PERMISSION_CAMERA);
                        //Toast.makeText(mContex, "take photo", Toast.LENGTH_SHORT).show();
                        //获取系统版本
                        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
                        // 激活相机
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        // 判断存储卡是否可以用，可用进行存储
                        if (hasSdcard()) {
                            SimpleDateFormat timeStampFormat = new SimpleDateFormat(
                                    "yyyy_MM_dd_HH_mm_ss");
                            imageName = timeStampFormat.format(new Date());
                            Log.d("MainActivity",imageName);
                            tempFile = new File(Environment.getExternalStorageDirectory(),
                                    imageName + ".jpg");
                            Log.d("MainActivity",tempFile.getPath());
                            if (currentapiVersion < 24) {
                                // 从文件中创建uri
                                imageUri = Uri.fromFile(tempFile);
                                intent.putExtra(MediaStore.EXTRA_OUTPUT, MainActivity.imageUri);
                            } else {
                                //兼容android7.0 使用共享文件的形式
                                ContentValues contentValues = new ContentValues(1);
                                contentValues.put(MediaStore.Images.Media.DATA, tempFile.getAbsolutePath());
                                //检查是否有存储权限，以免崩溃
                                if (ContextCompat.checkSelfPermission(mContex, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                        != PackageManager.PERMISSION_GRANTED) {
                                    Toast.makeText(mContex, "请开启存储权限", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                imageUri = mContex.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
                                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                            }
                        }
                        // 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_CAREMA
                        activity.startActivityForResult(intent, PHOTO_REQUEST_CAREMA);
                        dismiss();
                        break;
                    case 1:
                        //Toast.makeText(mContex, "choose photo", Toast.LENGTH_SHORT).show();
                        // checkPermissionAndRequest(REQUEST_WRITE_EXTERNAL_STORAGE_PERMISSION_GALLERY);
                        //相册选择
                        openGallery();
                        dismiss();
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
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        dismiss();
                    }
                }
                return true;
            }
        });
    }


   /* @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case PHOTO_REQUEST_CAREMA:
                if (resultCode == RESULT_OK) {
                    Intent intent = new Intent("com.android.camera.action.CROP");
                    intent.setDataAndType(imageUri, "image/*");
                    intent.putExtra("scale", true);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                    activity.startActivityForResult(intent, CROP_PHOTO); // 启动裁剪程序
                }
                break;
            case CROP_PHOTO:
                if (resultCode == RESULT_OK) {
                    try {
                        Bitmap bitmap = BitmapFactory.decodeStream(mContex.getContentResolver().openInputStream(imageUri));
                        picture.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case CHOOSE_PHOTO:
                if (resultCode == RESULT_OK) {
                    try {
                        if (data != null) {
                            Uri uri = data.getData();
                            imageUri = uri;
                        }
                        Bitmap bitmap = BitmapFactory.decodeStream(mContex.getContentResolver()
                                .openInputStream(imageUri));
                        picture.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
            default:
                activity.getParent().onActivityResult(requestCode, resultCode, data);
                break;
        }
    }*/

        public boolean hasSdcard () {
            return Environment.getExternalStorageState().equals(
                    Environment.MEDIA_MOUNTED);
        }

        public void openGallery () {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            activity.startActivityForResult(intent, CHOOSE_PHOTO);
        }


        public void initPopupItems ()
        {

            PopupItem item1 = new PopupItem(R.drawable.camera, "拍照");
            popupItemList.add(item1);

            PopupItem item2 = new PopupItem(R.drawable.photo, "从相册中选择");
            popupItemList.add(item2);

            PopupItem item3 = new PopupItem(R.drawable.cancel, "取消");
            popupItemList.add(item3);

        }

    }

