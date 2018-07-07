package com.example.a13494.myapplication.util;

import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FormatTools {


    public static boolean isPhoneNumberValid(String phoneNumber)
    {

        //校验是否为空
        if(TextUtils.isEmpty(phoneNumber)){
            return false;
        }
        //正则校验
        /*String regex = "^((13[0-9])|(14[5,7])|(15[0-3,5-9])|(17[0,3,5-8])|(18[0-9])|166|198|199|(147))\\d{8}$";
        Pattern p = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(phoneNumber);
        return m.matches();*/
        return true;

    }

    public static boolean isPasswordValid(String password)
    {
        return password.length()>4;
    }


    public static void requestVerifyCode(){

        //请求验证码
    }



}
