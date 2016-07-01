package com.android.example.forecast;

import android.app.Application;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.Volley;
import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;

/**
 * Created by Mohamed Habib on 6/30/2016.
 */
public class AppClass extends Application {
    private RequestQueue mRequestQueue;
    private static AppClass mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        FlowManager.init(new FlowConfig.Builder(this).build());
        // Instantiate the RequestQueue with the cache and network.
        mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        mInstance = this;

    }

    public static synchronized AppClass getInstance() {
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        return mRequestQueue;
    }

}
