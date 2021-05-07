package com.example.laspost10h.SupportClass;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class MySingletonUserUploadImage
{
    private static com.example.laspost10h.SupportClass.MySingletonUserUploadImage mySingletonUserUploadImage;
    private RequestQueue queue;
    private static Context context;

    private MySingletonUserUploadImage(Context temp_context)
    {
        context = temp_context;

        queue = getRequestQueue();
    }

    private RequestQueue getRequestQueue()
    {
        if(queue == null)
        {
            queue = Volley.newRequestQueue(context.getApplicationContext());
        }

        return queue;
    }

    public static synchronized com.example.laspost10h.SupportClass.MySingletonUserUploadImage getInstance(Context context)
    {
        if(mySingletonUserUploadImage == null)
        {
            mySingletonUserUploadImage = new com.example.laspost10h.SupportClass.MySingletonUserUploadImage(context);
        }

        return mySingletonUserUploadImage;
    }

    public<T> void addToRequestQueue(Request<T> request)
    {
        getRequestQueue().add(request);
    }
}