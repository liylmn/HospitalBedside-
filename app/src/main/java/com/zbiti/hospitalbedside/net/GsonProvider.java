package com.zbiti.hospitalbedside.net;

import com.google.gson.Gson;

/**
 * Created by zzr on 16/12/23.
 */

public class GsonProvider {
    private Gson mGson;
    private static GsonProvider instance;
    private GsonProvider() {
        mGson = new Gson();
    }

    public static GsonProvider getInstance() {
        if (instance == null) {
            synchronized (GsonProvider.class) {
                if (instance == null) {
                    instance = new GsonProvider();
                }
            }
        }
        return instance;
    }

    public Gson getGson() {
        return mGson;
    }
}
