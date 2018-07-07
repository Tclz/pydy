package com.example.a13494.myapplication.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.a13494.myapplication.api.WechatImagePicker;
import com.example.a13494.myapplication.R;
import com.qingmei2.rximagepicker.core.RxImagePicker;
import com.qingmei2.rximagepicker_extension.MimeType;
import com.qingmei2.rximagepicker_extension_wechat.WechatConfigrationBuilder;
import com.qingmei2.rximagepicker_extension_wechat.ui.WechatImagePickerActivity;

import io.reactivex.functions.Consumer;

public class WechatThemeActivity extends BaseActivity {

    private ImageView ivPickedImage;
    private WechatImagePicker rxImagePicker;
    private static final int REQUEST_WRITE_EXTERNAL_STORAGE_PERMISSION_CAMERA = 99;
    private static final int REQUEST_WRITE_EXTERNAL_STORAGE_PERMISSION_GALLERY = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wechat);

        //textView = (TextView)findViewById(R.id.http_response);
        //sendRequest=(Button)findViewById(R.id.send_request);
        ivPickedImage = (ImageView) findViewById(R.id.iv_picked_image);
        FloatingActionButton fabCamera = (FloatingActionButton) findViewById(R.id.fab_pick_camera);
        FloatingActionButton fabGallery = (FloatingActionButton) findViewById(R.id.fab_pick_gallery);

        initRxImagePicker();

        fabCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermissionAndRequest(REQUEST_WRITE_EXTERNAL_STORAGE_PERMISSION_CAMERA);
            }
        });
        fabGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermissionAndRequest(REQUEST_WRITE_EXTERNAL_STORAGE_PERMISSION_GALLERY);
            }
        });
    }

    private void checkPermissionAndRequest(int requestCode) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    requestCode);
        } else {
            onPermissionGrant(requestCode);
        }
    }

    private void initRxImagePicker() {
        rxImagePicker = new RxImagePicker.Builder()
                .with(this)
                .addCustomGallery(
                        WechatImagePicker.KEY_WECHAT_PICKER_ACTIVITY,
                        WechatImagePickerActivity.class,
                        new WechatConfigrationBuilder(MimeType.ofAll(), false)
                                .maxSelectable(9)
                                .countable(true)
                                .spanCount(4)
                                .countable(false)
                                .theme(R.style.Wechat)
                                .build()
                )
                .build()
                .create(WechatImagePicker.class);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            onPermissionGrant(requestCode);
        } else {
            Toast.makeText(this, "Please allow the Permission test1.", Toast.LENGTH_SHORT).show();
        }
    }

    private void onPermissionGrant(int requestCode) {
        if (requestCode == REQUEST_WRITE_EXTERNAL_STORAGE_PERMISSION_CAMERA) {
            openCamera();
        } else if (requestCode == REQUEST_WRITE_EXTERNAL_STORAGE_PERMISSION_GALLERY) {
            openGallery();
        }
    }

    private void openCamera() {
        rxImagePicker.openCamera()
                .subscribe(onNext(), onError());
    }

    private void openGallery() {
        rxImagePicker.openGallery()
                .subscribe(onNext(), onError());
    }

    private Consumer<Uri> onNext() {
        return new Consumer<Uri>() {
            @Override
            public void accept(Uri uri) {
                Glide.with(WechatThemeActivity.this)
                        .load(uri)
                        .into(ivPickedImage);
            }
        };
    }

    private Consumer<Throwable> onError() {
        return new Consumer<Throwable>() {
            @Override
            public void accept(Throwable e) {
                e.printStackTrace();
                Toast.makeText(WechatThemeActivity.this, "取消拍照", Toast.LENGTH_SHORT).show();
            }
        };
    }



}

