package com.example.a13494.myapplication.util;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;

public class ImageUtil {
  //private static Bitmap btp;
  public static void getBitmapForImgResourse(Context mContext,Bitmap bitmap, ImageView mImageView) throws IOException {
       // InputStream is = mContext.getResources().openRawResource(imgId);
        //BitmapFactory.Options options = new BitmapFactory.Options();
        //options.inJustDecodeBounds = false;
        //options.inPreferredConfig = Bitmap.Config.RGB_565;
        //options.inPurgeable = true;
        //options.inInputShareable = true;
        //options.inSampleSize = 1;
        //btp = BitmapFactory.decodeStream(is, null, options);
        mImageView.setImageBitmap(bitmap);
        //is.close();
}
  /*public static void bitmapRecycle()
  {  if(btp!=null)
     btp.recycle();
  }*/
}
