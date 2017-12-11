package com.outsource.changnanguoshui.utlis;

import com.google.gson.Gson;

/**
 * Created by JimGong on 2016/6/23.
 */
public class JsonGenerics implements IGenericsSerializator
{
    Gson mGson = new Gson();

    @Override
    public <T> T transform(String response, Class<T> classOfT)
    {
        return mGson.fromJson(response, classOfT);
    }
}
