package com.android.androidlibrary.imageloader;

import android.content.Context;
import android.widget.ImageView;

import com.android.androidlibrary.MyConf;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.io.File;

import static android.R.attr.angle;


public interface ILoader {


    //    DiskCacheStrategy.NONE :不缓存图片
//    DiskCacheStrategy.SOURCE :缓存图片源文件
//    DiskCacheStrategy.RESULT:缓存修改过的图片
//    DiskCacheStrategy.ALL:缓存所有的图片，默认
    void init(Context context);

    /**
     * 网络加载
     *
     * @param target
     * @param url
     * @param options
     */
    void loadNet(ImageView target, String url, Options options);

    void loadNet(Context context, String url, Options options, LoadCallback callback);

    void loadResource(ImageView target, int resId, Options options);

    void loadAssets(ImageView target, String assetName, Options options);

    void loadFile(ImageView target, File file, Options options);

    void clearMemoryCache(Context context);

    void clearDiskCache(Context context);

    void resume(Context context);

    void pause(Context context);

    class Options {
        public int loadingResId = RES_NONE;        //加载中的资源id
        public int loadErrorResId = RES_NONE;      //加载失败的资源id
        public static final int RES_NONE = -1;
        public static final int CIRCLE = 1;
        public static final int ROUND = 2;
        public int circleType = 0; // 圆角圆形状态  circleType==1 圆形 circleType==2 圆角
        public int angle = 0;   //角度
        public DiskCacheStrategy cacheType = DiskCacheStrategy.SOURCE;

        public static Options defaultOptions() {
            return new Options(MyConf.IL_LOADING_RES, MyConf.IL_ERROR_RES);
        }

        public Options() {
        }
        public Options(int loadingResId, int loadErrorResId) {
            this.loadingResId = loadingResId;
            this.loadErrorResId = loadErrorResId;
        }

        public Options(int loadingResId, int loadErrorResId, DiskCacheStrategy cacheType,int circleType) {
            this.loadingResId = loadingResId;
            this.loadErrorResId = loadErrorResId;
            this.cacheType = cacheType;
            this.circleType=circleType;
        }

        /**
         * 圆形
         *
         * @param loadingResId   展位图
         * @param loadErrorResId 加载失败图片
         * @param circleType     图片需要显示类型
         */
        public Options(int loadingResId, int loadErrorResId, int circleType) {
            this.loadingResId = loadingResId;
            this.loadErrorResId = loadErrorResId;
            this.circleType = circleType;
        }

        /**
         * @param loadingResId
         * @param loadErrorResId
         * @param circleType     图片需要显示类型
         * @param angle          角度
         */
        public Options(int loadingResId, int loadErrorResId, int circleType, int angle) {
            this.loadingResId = loadingResId;
            this.loadErrorResId = loadErrorResId;
            this.circleType = circleType;
            this.angle = angle;
        }

    }

}
