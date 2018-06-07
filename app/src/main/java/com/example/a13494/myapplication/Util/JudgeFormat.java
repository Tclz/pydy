package com.example.a13494.myapplication.Util;

import android.text.TextUtils;

public class JudgeFormat {


    public static boolean isPhoneNumberValid(String phoneNumber)
    {

        //校验是否为空
        if(TextUtils.isEmpty(phoneNumber)){
            return false;
        }

        //正则校验

        return true;

    }
    public static boolean isPasswordValid(String password)
    {
        return true;
    }

    public static void requestVerifyCode(){

        //请求验证码
    }



}
