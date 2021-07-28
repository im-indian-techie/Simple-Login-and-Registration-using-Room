package com.ashin.testapp.utils;

import android.content.Context;
import android.content.SharedPreferences;
import com.ashin.testapp.model.User;
import com.google.gson.Gson;

public class PreferenceManager {


    private static PreferenceManager instance;
    private static SharedPreferences settings;
    public static PreferenceManager getInstance()
    {

        if(instance==null)
        {
            instance=new PreferenceManager();
        }
        return instance;
    }

    public void setUserDetails(Context context, User user)
    {
        String object="";
        if(user!=null) {
            Gson gson = new Gson();
            object = gson.toJson(user);
        }
        SharedPreferences.Editor editor = context.getSharedPreferences(context.getPackageName(), context.MODE_PRIVATE).edit();
        editor.putString("userdetails",object);
        editor.commit();
    }
    public User getUserDetails(Context context)
    {
        settings = context.getSharedPreferences(context.getPackageName(), context.MODE_PRIVATE);
        User user=null;
        SharedPreferences prefs= context.getSharedPreferences(context.getPackageName(), context.MODE_PRIVATE);
        String object=prefs.getString("userdetails","");
        if(!object.isEmpty())
        {
            Gson gson = new Gson();
            user=gson.fromJson(object,User.class);
        }
        return user;
    }


}