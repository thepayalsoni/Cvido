package com.cvido.application;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Helper class that is used to provide references to initialized RequestQueue(s) and ImageLoader(s)
 */
public class MyVolley {

    private static RequestQueue mRequestQueue;

    private MyVolley() {
        // no instances
    }

    static void init(Context context) {

        mRequestQueue = createVolleyRequestQue(context);

    }

    public static RequestQueue createVolleyRequestQue(Context context) {

        return Volley.newRequestQueue(context);

    }

    public static RequestQueue getRequestQueue() {
        if (mRequestQueue != null) {
            return mRequestQueue;
        } else {
            throw new IllegalStateException("RequestQueue not initialized");
        }
    }

}
