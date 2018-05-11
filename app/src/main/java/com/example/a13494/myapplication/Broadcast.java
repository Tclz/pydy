package com.example.a13494.myapplication;

import android.os.Environment;


public class Broadcast {

    public static boolean hasSdcard() {
        return Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
    }

}
