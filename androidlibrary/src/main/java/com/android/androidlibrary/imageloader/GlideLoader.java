package com.android.androidlibrary.imageloader;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.GlideBitmapDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.signature.StringSignature;

import java.io.File;

import static android.R.attr.tag;


public class GlideLoader implements ILoader {

    private Context mContext;

    @Override
    public void init(Context context) {
        mContext = context;
    }

    /**
     * 网络加载
     *
     * @param target
     * @param url
     * @param options
     */
    @Override
    public void loadNet(ImageView target, String url, Options options) {
        load(getRequestManager(target.getContext()).load(url), target, options);
    }


    @Override
    public void loadNet(Context context, String url, Options options, final LoadCallback callback) {
        DrawableTypeRequest request = getRequestManager(context).load(url);
        if (options == null) options = Options.defaultOptions();

        if (options.loadingResId != Options.RES_NONE) {
            request.placeholder(options.loadingResId);
        }
        if (options.loadErrorResId != Options.RES_NONE) {
            request.error(options.loadErrorResId);
        }

        request.diskCacheStrategy(options.cacheType)
                .crossFade()
                .into(new SimpleTarget<GlideBitmapDrawable>() {

                    @Override
                    public void onLoadFailed(Exception e, Drawable errorDrawable) {
                        super.onLoadFailed(e, errorDrawable);
                        if (callback != null) {
                            callback.onLoadFailed(e);
                        }
                    }

                    @Override
                    public void onResourceReady(GlideBitmapDrawable resource, GlideAnimation<? super GlideBitmapDrawable> glideAnimation) {
                        if (resource != null && resource.getBitmap() != null) {
                            if (callback != null) {
                                callback.onLoadReady(resource.getBitmap());
                            }
                        }
                    }

                });
    }

    /**
     * 从资源id
     *
     * @param target
     * @param resId
     * @param options
     */
    @Override
    public void loadResource(ImageView target, int resId, Options options) {
        load(getRequestManager(target.getContext()).load(resId), target, options);
    }

    /**
     * 从 Assets
     *
     * @param target
     * @param assetName
     * @param options
     */
    @Override
    public void loadAssets(ImageView target, String assetName, Options options) {
        load(getRequestManager(target.getContext()).load("file:///android_asset/" + assetName), target, options);
    }

    /**
     * 从文件记载
     *
     * @param target
     * @param file
     * @param options
     */

    @Override
    public void loadFile(ImageView target, File file, Options options) {
        load(getRequestManager(target.getContext()).load(file), target, options);
    }


    /**
     * 清理内存缓存  可以在UI主线程中进行
     *
     * @param context
     */
    @Override
    public void clearMemoryCache(Context context) {
        Glide.get(context).clearMemory();
    }

    /**
     * 清理磁盘缓存 需要在子线程中执行
     *
     * @param context
     */
    @Override
    public void clearDiskCache(Context context) {
        Glide.get(context).clearDiskCache();
    }


    /**
     * 恢复请求
     *
     * @param context
     */
    @Override
    public void resume(Context context) {
        getRequestManager(context).resumeRequests();
    }

    /**
     * 暂停请求
     *
     * @param context
     */
    @Override
    public void pause(Context context) {
        getRequestManager(context).pauseRequests();
    }


    /**
     * 判断context类型
     *
     * @param context
     * @return
     */
    private RequestManager getRequestManager(Context context) {
        if (context instanceof Activity) {
            return Glide.with((Activity) context);
        }
        return Glide.with(context);
    }

    // circleType==1 圆形 circleType==2 圆角 angle角度
    private void load(DrawableTypeRequest request, ImageView target, Options options) {
        if (options == null) options = Options.defaultOptions();

        if (options.loadingResId != Options.RES_NONE) {
            request.placeholder(options.loadingResId);
        }
        if (options.loadErrorResId != Options.RES_NONE) {
            request.error(options.loadErrorResId);
        }
        if (options != null) {
            if(options.cacheType==DiskCacheStrategy.NONE){
                request.skipMemoryCache(true);
            }
            request.diskCacheStrategy(options.cacheType);

        }
        if (Options.CIRCLE == options.circleType) {
            request.transform(new GlideCircleTransform(mContext));
        } else if (Options.ROUND == options.circleType) {
            request.transform(new GlideRoundTransform(mContext, options.angle));
        }
        request.into(target);
    }

}
