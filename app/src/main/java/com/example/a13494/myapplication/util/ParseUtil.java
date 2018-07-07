package com.example.a13494.myapplication.util;

import android.util.Log;

import com.example.a13494.myapplication.entity.ClothGoods;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.ArrayList;


public class ParseUtil {

    public static void parseJSONwithGSON(String jsonData)
    {
        Gson gson = new Gson();
        ArrayList<ClothGoods>clothList = gson.fromJson(jsonData,new TypeToken<ArrayList<ClothGoods>>(){}.getType());
        ResponseData data = ResponseData.getInstance();
        data.clearCloth();
        data.clearBitmap();
        data.setClothList(clothList);
        ArrayList<ClothGoods>clothes =data.getClothList();
        for(int i = 0;i<clothes.size();i++)
        {
            clothes.get(i).setId(i);
        }
        for(ClothGoods cloth :clothes)
        {
            Log.d("MainActivity",String.valueOf(cloth.getId()));
            Log.d("MainActivity",cloth.getImg());
            Log.d("MainActivity",cloth.getLoc());
        }
    }
}
