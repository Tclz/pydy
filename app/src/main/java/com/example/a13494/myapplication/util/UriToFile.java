package com.example.a13494.myapplication.util;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import java.io.File;
import static android.content.ContentValues.TAG;
public class UriToFile {

    public static File getFileFromUri(Uri uri)
    {
        String path = null;
        if ("file".equals(uri.getScheme())) {
            path = uri.getEncodedPath();
            if (path != null) {
                path = Uri.decode(path);
                ContentResolver cr = MyApplication.getContext().getContentResolver();
                StringBuffer buffer = new StringBuffer();
                buffer.append("(").append( MediaStore.Images.ImageColumns.DATA).append("=").append("'" + path + "'").append(")");
                Cursor cur = cr.query( MediaStore.Images.Media.EXTERNAL_CONTENT_URI, new String[] {  MediaStore.Images.ImageColumns._ID, MediaStore.Images.ImageColumns.DATA }, buffer.toString(), null, null);
                int index = 0;
                int dataIdx ;
                for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {
                    index = cur.getColumnIndex( MediaStore.Images.ImageColumns._ID);
                    index = cur.getInt(index);
                    dataIdx = cur.getColumnIndex( MediaStore.Images.ImageColumns.DATA);
                    path = cur.getString(dataIdx);
                }
                cur.close();
                if (index != 0) {
                    Uri u = Uri.parse("content://media/external/images/media/" + index);
                    System.out.println("temp uri is :" + u);
                }
            }
            if (path != null) {
                return new File(path);
            }
        } else if ("content".equals(uri.getScheme())) {
            // test4.test2.2以后
            String[] proj = { MediaStore.Images.Media.DATA };
            Cursor cursor = MyApplication.getContext().getContentResolver().query(uri, proj, null, null, null);
            if (cursor.moveToFirst()) {
                int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                path = cursor.getString(columnIndex);
            }
            cursor.close();

            return new File(path);
        } else if(uri.getScheme().equals("")){
            return new File(uri.getPath());

        }
        return null;
    }
}
