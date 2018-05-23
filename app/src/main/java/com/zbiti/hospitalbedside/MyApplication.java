package com.zbiti.hospitalbedside;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.android.androidlibrary.util.SPUtils;
import com.github.moduth.blockcanary.BlockCanary;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import com.tencent.bugly.crashreport.CrashReport;
import com.zbiti.hospitalbedside.common.Constant;
import com.zbiti.hospitalbedside.common.Global;
import com.zbiti.hospitalbedside.push.MyUtil;
import com.zhy.autolayout.config.AutoLayoutConifg;

import cn.jpush.android.api.JPushInterface;

/**
 * For developer startup JPush SDK
 * <p>
 * 一般建议在自定义 Application 类里初始化。也可以在主 Activity 里。
 */
public class MyApplication extends Application {
    private static final String TAG = "JPush";
    private static Context mContext;
    private RefWatcher refWatcher;
    @Override
    public void onCreate() {
        Log.d(TAG, "[MyApplication] onCreate");
        super.onCreate();
        mContext = getApplicationContext();



        CrashReport.initCrashReport(getApplicationContext(), "10fe940eba", true);
        BlockCanary.install(this, new AppBlockCanaryContext()).start();
        AutoLayoutConifg.getInstance().useDeviceSize();
        JPushInterface.setDebugMode(true);    // 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);            // 初始化 JPush
        refWatcher= setupLeakCanary();
        Global.IMEI = MyUtil.getImei(getApplicationContext(), "");
        String ip=SPUtils.getValue(mContext, "msg", "ip", "");
        String proj=SPUtils.getValue(mContext, "msg", "proj", "");
        if (!ip.equals("")){
            Constant.IP = ip;
            Constant.PROJ = proj;
            Constant.setIP();
        }




    }

    private RefWatcher setupLeakCanary() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return RefWatcher.DISABLED;
        }
        return LeakCanary.install(this);
    }

    public static RefWatcher getRefWatcher(Context context) {
        MyApplication leakApplication = (MyApplication) context.getApplicationContext();
        return leakApplication.refWatcher;
    }

    public static Context getContext() {
        return mContext;
    }


}
