package com.zbiti.hospitalbedside.util;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;

import java.io.File;

/**
 * Created by Digsol on 2016/5/6 16:01.
 */
public class AppUtil {
    /**
     * 检测应用当前版本
     *
     * @param context
     * @return
     */
    public static int checkVersion(Context context) {
        PackageManager packageManager = context.getPackageManager();
        PackageInfo packageInfo;
        int version = -1;
        try {
            packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            version = packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return version;
    }

    /**
     * 安装apk
     *
     * @param context
     * @param path
     * @param name
     */
    public static void installApk(Context context, String path, String name) {
        File file = new File(path, name);
        if (!file.exists()) {
            return;
        }
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.parse("file://" + file.toString()),
                "application/vnd.android.package-archive");
        context.startActivity(intent);
    }

    /**
     * 获取VersionName
     *
     * @param context
     * @return
     */
    public static String getVersionName(Context context) {
        PackageManager packageManager = context.getPackageManager();
        PackageInfo packageInfo;
        try {
            packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }
}
