package com.android.androidlibrary.router;

import android.app.Activity;


public interface RouterCallback {

    void onBefore(Activity from, Class<?> to);

    void OnNext(Activity from, Class<?> to);

    void onError(Activity from, Class<?> to, Throwable throwable);

}
