package com.android.androidlibrary.imageloader;


public class ImageFactory {

    private static ILoader loader;


    public static ILoader getLoader() {
        if (loader == null) {
            synchronized (ImageFactory.class) {
                if (loader == null) {
                    loader = new GlideLoader();
                }
            }
        }
        return loader;
    }

}
