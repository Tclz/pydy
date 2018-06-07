package com.example.a13494.myapplication.Activity;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import com.example.a13494.myapplication.R;
import com.example.a13494.myapplication.View.SelectPicPopupWindow;
import com.example.a13494.myapplication.Adapter.PopupItemAdapter;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static final int PHOTO_REQUEST_CAREMA = 1;// 拍照
    public static final int CHOOSE_PHOTO = 3; //相册
    public static final int CROP_PHOTO = 2; //裁剪
    private ImageView picture;
    private Uri imageUri;
    private ViewPager mViewPaper;
    private List<ImageView> images;
    private List<View> dots;
    private int currentItem;
    public static File tempFile;
    private Button takePhoto;
    private Button testImagePicker;
    private Button chooseFromAlbum;
    private Button popWindow;
    private String imageName;
    private SelectPicPopupWindow menuWindow;

    //记录上一次点的位置
    private int oldPosition = 0;
    //存放图片的id
    private int[] imageIds = new int[]{
            R.drawable.a,
            R.drawable.b,
            R.drawable.c,
            R.drawable.d,
            R.drawable.e
    };
    //存放图片的标题
    private String[] titles = new String[]{"test1", "test2", "test3", "test4", "test5"};
    private TextView title;
    private ViewPagerAdapter adapter;
    private ScheduledExecutorService scheduledExecutorService;

   // private HTextView hTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        takePhoto = (Button) findViewById(R.id.take_photo);
        chooseFromAlbum = (Button) findViewById(R.id.choose_from_album);
        popWindow = (Button)findViewById(R.id.popWindow);
        popWindow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImg();
            }
        });
        picture = (ImageView) findViewById(R.id.picture);
        testImagePicker = (Button) findViewById(R.id.testButton);
        takePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取系统版本
                int currentapiVersion = android.os.Build.VERSION.SDK_INT;
                // 激活相机
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                // 判断存储卡是否可以用，可用进行存储
                if (hasSdcard()) {
                    SimpleDateFormat timeStampFormat = new SimpleDateFormat(
                            "yyyy_MM_dd_HH_mm_ss");
                    imageName = timeStampFormat.format(new Date());
                    tempFile = new File(Environment.getExternalStorageDirectory(),
                            imageName + ".jpg");
                    if (currentapiVersion < 24) {
                        // 从文件中创建uri
                        imageUri = Uri.fromFile(tempFile);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                    } else {
                        //兼容android7.0 使用共享文件的形式
                        ContentValues contentValues = new ContentValues(1);
                        contentValues.put(MediaStore.Images.Media.DATA, tempFile.getAbsolutePath());
                        //检查是否有存储权限，以免崩溃
                        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                != PackageManager.PERMISSION_GRANTED) {
                            Toast.makeText(MainActivity.this,"请开启存储权限",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                    }
                }
                // 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_CAREMA
                startActivityForResult(intent, PHOTO_REQUEST_CAREMA);
            }
        });

        chooseFromAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        testImagePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, WechatThemeActivity.class));

            }
        });
        /*
        hTextView = (HTextView) findViewById(R.id.htext);
        hTextView.setAnimateType(HTextViewType.LINE);
        hTextView.animateText("new simple string");
        */
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               new Thread(new Runnable() {
                   @Override
                   public void run() {
                      uploadImage(imageName);
                   }
               }).start();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        mViewPaper = (ViewPager) findViewById(R.id.vp);
        images = new ArrayList<>();
        for (int i = 0; i < imageIds.length; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setBackgroundResource(imageIds[i]);
            images.add(imageView);
        }
        dots = new ArrayList<>();
        dots.add(findViewById(R.id.dot_0));
        dots.add(findViewById(R.id.dot_1));
        dots.add(findViewById(R.id.dot_2));
        dots.add(findViewById(R.id.dot_3));
        dots.add(findViewById(R.id.dot_4));
        title = (TextView) findViewById(R.id.title);
        title.setText(titles[0]);
        adapter = new ViewPagerAdapter();
        mViewPaper.setAdapter(adapter);
        mViewPaper.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                title.setText(titles[position]);
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

     //onCreate 方法结束.

    public void selectImg()
    {
        menuWindow = new SelectPicPopupWindow(MainActivity.this);
        backgroundAlpha(0.7f);
        menuWindow.setOnDismissListener(new poponDismissListener());
        View rootView = LayoutInflater.from(MainActivity.this).inflate(R.layout.activity_main,null);
        menuWindow.showAtLocation(rootView, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    /**
     * 设置添加屏幕的背景透明度
     * @param bgAlpha
     */
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
   /* private View.OnClickListener itemsOnClick = new View.OnClickListener() {
        public void onClick(View v) {
            menuWindow.dismiss();
            switch (v.getId()) {
                case R.id.pop_menu_camera:
                    break;
                case R.id.pop_menu_photo:
                    break;
                default:
                    break;
            }
        }

    };*/

    public  boolean hasSdcard() {
        return Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
    }

    public void uploadImage(String imageName)
    {
        startActivity(new Intent(MainActivity.this,ShowResultActivity.class));

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
                            Uri uri = data.getData();
                            imageUri = uri;
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

    public void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, CHOOSE_PHOTO);
    }

//
    /**
     * 自定义Adapter
     */
    class ViewPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return images.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public void destroyItem(ViewGroup view, int position, Object object) {
            // TODO Auto-generated method stub
//          super.destroyItem(container, position, object);
//          view.removeView(view.getChildAt(position));
//          view.removeViewAt(position);
            view.removeView(images.get(position));
        }

        @Override
        public Object instantiateItem(ViewGroup view, int position) {
            // TODO Auto-generated method stub
            view.addView(images.get(position));
            return images.get(position);
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
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
