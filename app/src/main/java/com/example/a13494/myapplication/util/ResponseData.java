package com.example.a13494.myapplication.util;
import android.graphics.Bitmap;

import com.example.a13494.myapplication.entity.ClothGoods;
import java.util.ArrayList;

public class ResponseData {
    private static ArrayList<ClothGoods>clothList;
    private static ArrayList<Bitmap>bitmaps;
    private static ResponseData responseData;
    private ResponseData()
    {
        clothList = new ArrayList<>();
        bitmaps = new ArrayList<>();
    }
    public static ResponseData getInstance()
    {
        if(responseData!=null)
            return responseData;
        else {
            responseData = new ResponseData();
            return responseData;
        }
    }
    public ArrayList<ClothGoods> getClothList()
    {
        return clothList;
    }
    public ArrayList<Bitmap> getBitmaps()
    {
        return bitmaps;
    }
    public void addCloth(ClothGoods clothGoods)
    {
        clothList.add(clothGoods);
    }
    public void addBitmap(Bitmap bitmap){ bitmaps.add(bitmap);}
    public void clearBitmap()
    {
        bitmaps.clear();
    }
    public void clearCloth()
    {
        clothList.clear();
    }
    public void setClothList(ArrayList<ClothGoods>list)
    {
        clothList = list;
    }
    //public static String response = []
}
