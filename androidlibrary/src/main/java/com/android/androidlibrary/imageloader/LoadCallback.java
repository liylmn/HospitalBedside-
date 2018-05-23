package com.android.androidlibrary.imageloader;

import android.graphics.Bitmap;


public abstract class LoadCallback {
   public void onLoadFailed(Throwable e) {
    }

    public abstract void onLoadReady(Bitmap bitmap);

    void onLoadCanceled() {
    }
}
