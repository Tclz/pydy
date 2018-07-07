package com.example.a13494.myapplication.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewDebug;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a13494.myapplication.adapter.ViewPagerAdapter;
import com.example.a13494.myapplication.R;
import com.example.a13494.myapplication.entity.ClothGoods;
import com.example.a13494.myapplication.util.ActivityCollector;
import com.example.a13494.myapplication.util.HttpUtil;
import com.example.a13494.myapplication.util.ResponseData;
import com.example.a13494.myapplication.util.UriToFile;
import com.example.a13494.myapplication.view.SelectPicPopupWindow;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.litepal.LitePal;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.annotations.NonNull;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import rx.Observable;

import static com.example.a13494.myapplication.util.ParseUtil.parseJSONwithGSON;
import static com.example.a13494.myapplication.util.ResponseData.getInstance;
import static com.example.a13494.myapplication.util.UriToFile.getFileFromUri;


public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    public static final int PHOTO_REQUEST_CAREMA = 1;// 拍照
    public static final int CHOOSE_PHOTO = 3; //相册
    public static final int CROP_PHOTO = 2; //裁剪
    public static final int LOAD_IMAGE = 4;
    //public static final int TIME_DELAY = 100;
    public static Uri imageUri;
    private List<ImageView> imagesList = new ArrayList<>();
    //private ArrayList<ClothGoods>goodslist = new ArrayList<>();

    @BindView(R.id.picture)
    public ImageView picture;
    @OnClick(R.id.picture)
    public void onClick()
    {
        selectImg();
    }
    @BindView(R.id.vp)
    public ViewPager mViewPaper;
    private List<View> dots;
    private int currentItem;
    public static File tempFile;
    public static String imageName;

    @BindView(R.id.click_to_upload)
    public Button upLoad;
    @OnClick(R.id.click_to_upload)
    public void upLoadFile()
    {
        uploadImage();
    }
    private SelectPicPopupWindow menuWindow;
    @BindView(R.id.toolbar)
    public Toolbar toolbar;
    @BindView(R.id.drawer_layout)
    public DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;
    @BindView(R.id.nav_view)
    public NavigationView navigationView;

    private int oldPosition = 0;
    private ProgressDialog pd;
    //private String testImageUrl = "http://g-search1.alicdn.com/img/bao/uploaded/i4/imgextra/i2/131063360/TB2bf9paHSYBuNjSspiXXXNzpXa_!!0-saturn_solar.jpg";
    private int[] imageIds = new int[]{
            R.drawable.vp_img1,
            R.drawable.vp_img2,
            R.drawable.vp_img3,
            R.drawable.vp_img4,
            R.drawable.vp_img5
    };
    private ViewPagerAdapter adapter;
    private ScheduledExecutorService scheduledExecutorService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
        initViewPager();
    }

    private  Handler handler=new Handler(){

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case LOAD_IMAGE: {
                    //ProgressDialog p = new ProgressDialog(MainActivity.this);
                    //Log.d("MainActivity_Load_Image","12222222222222222");
                    //p.setTitle("加载图片");
                    //p.setMessage("加载中,请稍后...");
                    //p.setCancelable(false);
                    //p.show();
                    for(int i=0;i<20;i++)
                    {
                        requestImgFromUrl(i);
                    }
                    try {
                        Thread.sleep(5000);
                    }catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                    pd.dismiss();
                    startStackActivity();
                    break;
                }

                default:
                    break;
            }
        }

    };

    public void initView()
    {
        pd=new ProgressDialog(MainActivity.this);
        picture.setImageResource(R.drawable.upload);
        setSupportActionBar(toolbar);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }


    public void startStackActivity()
    {
        startActivity(new Intent(MainActivity.this,CardStackActivity.class));
    }
    public void initViewPager()
    {
        for (int i = 0; i < imageIds.length; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setBackgroundResource(imageIds[i]);
            imagesList.add(imageView);
        }
        dots = new ArrayList<>();
        dots.add(findViewById(R.id.dot_0));
        dots.add(findViewById(R.id.dot_1));
        dots.add(findViewById(R.id.dot_2));
        dots.add(findViewById(R.id.dot_3));
        dots.add(findViewById(R.id.dot_4));
        adapter = new ViewPagerAdapter(imagesList);
        mViewPaper.setAdapter(adapter);
        mViewPaper.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                dots.get(position).setBackgroundResource(R.drawable.dot);
                dots.get(oldPosition).setBackgroundResource(R.drawable.dot0);
                oldPosition = position;
                currentItem = position;
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }
            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
        });
    }

    public void requestImgFromUrl(int index)
    {
        String url = ResponseData.getInstance().getClothList().get(index).getImg();
        Log.d("MAINACTIVITY",url);
        HttpUtil.getInstance().requestImgFromUrl(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                InputStream is = response.body().byteStream();
                Bitmap bitmap = BitmapFactory.decodeStream(is);
                Log.d("MainActivity",bitmap.toString());
                ResponseData data  = ResponseData.getInstance();
                data.addBitmap(bitmap);
                Log.d("MainActivity", String.valueOf(data.getBitmaps().size()));
            }
        });
    }
    public void selectImg()
    {
        menuWindow = new SelectPicPopupWindow(MainActivity.this);
        backgroundAlpha(0.7f);
        menuWindow.setOnDismissListener(new poponDismissListener());
        View rootView = LayoutInflater.from(MainActivity.this).inflate(R.layout.activity_main,null);
        menuWindow.showAtLocation(rootView, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }
    //设置添加屏幕的背景透明度
    public void backgroundAlpha(float bgAlpha)
    {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getWindow().setAttributes(lp);
    }

    class poponDismissListener implements PopupWindow.OnDismissListener{

        @Override
        public void onDismiss() {
            backgroundAlpha(1f);
        }

    }



    public void uploadImage() {
        if(tempFile!=null)
        {

            pd.setTitle("图片上传");
            pd.setMessage("检索中，请稍候...");
            pd.setCancelable(false);
            pd.show();

            HttpUtil.getInstance().uploadImage(tempFile, new Callback() {

                @Override
                public void onFailure(Call call, IOException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            pd.dismiss();
                            Toast.makeText(MainActivity.this, "查找失败",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            //pd.dismiss();
                            pd.setTitle("加载图片");
                            pd.setMessage("图片加载中...");

                        }
                    });
                    String jsonString = response.body().string();
                    parseJSONwithGSON(jsonString);
                    handler.sendEmptyMessage(LOAD_IMAGE);
                }
            });
        }else{
            Toast.makeText(MainActivity.this,"请先选择图片",Toast.LENGTH_SHORT).show();
            return;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case PHOTO_REQUEST_CAREMA:
                if (resultCode == RESULT_OK) {
                    Intent intent = new Intent("com.android.camera.action.CROP");
                    intent.setDataAndType(imageUri, "image/*");
                    intent.putExtra("scale", true);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                    startActivityForResult(intent, CROP_PHOTO); // 启动裁剪程序
                }
                break;
            case CROP_PHOTO:
                if (resultCode == RESULT_OK) {
                    try {
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
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
                            imageUri = data.getData();
                            tempFile = new File(imageUri.getPath().substring(4));

                            //tempFile=getFileFromUri(uri);
                            //Log.d("MainActivity",tempFile.toString());
                        }
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver()
                                .openInputStream(imageUri));
                        picture.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
            default:
                super.onActivityResult(requestCode, resultCode, data);
                break;
        }
    }


    /**
     * 利用线程池定时执行动画轮播
     */
    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleWithFixedDelay(
                new ViewPageTask(),
                2,
                2,
                TimeUnit.SECONDS);
    }


    /**
     * 图片轮播任务
     */
    private class ViewPageTask implements Runnable {

        @Override
        public void run() {
            currentItem = (currentItem + 1) % imageIds.length;
            mHandler.sendEmptyMessage(0);
        }
    }

    /**
     * 接收子线程传递过来的数据
     */
    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            mViewPaper.setCurrentItem(currentItem);
        }
    };

    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        super.onStop();
        //imageName = "";
        if (scheduledExecutorService != null) {
            scheduledExecutorService.shutdown();
            scheduledExecutorService = null;
        }
    }

    /**
     * 图片轮播结束
     */


    /**
     * 侧滑菜单返回键监听
     */
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    /**
     * 右上角菜单
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * 对左边菜单项的点击操作保持不响应
     *
     * @param item
     * @return
     */
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        switch (item.getItemId()){
            case R.id.quit:
                final AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                dialog.setTitle("退出");
                dialog.setMessage("确定要退出登录吗?");
                dialog.setCancelable(false);
                dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        SharedPreferences.Editor editor = getSharedPreferences("data_up",0).edit();
                        editor.putString("phone","");
                        editor.putString("password","");
                        editor.apply();
                        ActivityCollector.finishAll();
                        //startActivity(new Intent(MainActivity.this,LoginActivity.class));
                        //finish();
                    }
                });
                dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                dialog.show();
                break;
            case R.id.nav_camera:
                startStackActivity();
                break;
                default:
                    break;
        }
        //DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
