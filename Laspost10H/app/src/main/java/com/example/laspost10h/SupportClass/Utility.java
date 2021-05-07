package com.example.laspost10h.SupportClass;

import android.content.Context;
import android.net.ConnectivityManager;

public class Utility
{
    public static boolean isNetworkAvailable(Context context)
    {
        final ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        final android.net.NetworkInfo Wi_Fi = connectivityManager.getNetworkInfo(connectivityManager.TYPE_WIFI);

        final android.net.NetworkInfo mobile_data = connectivityManager.getNetworkInfo(connectivityManager.TYPE_MOBILE);

        if(Wi_Fi.isConnectedOrConnecting() || mobile_data.isConnectedOrConnecting())
        {
            return true;
        }

        else
        {
            return false;
        }
    }
}