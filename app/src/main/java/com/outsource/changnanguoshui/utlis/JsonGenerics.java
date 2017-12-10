package com.outsource.changnanguoshui.utlis;

import com.google.gson.Gson;

/**
 * Created by JimGong on 2016/6/23.
 */
public class JsonGenerics 
{

    
    public static  <T> T transform(String response, Class<T> classOfT)
    {
        return new Gson().fromJson(response, classOfT);
    }
}
