package com.zbiti.hospitalbedside.common;

import android.os.Environment;

/**
 * Created by Song on 2016/8/15.
 */
public class Constant {
    public static final String SETTINGS_PASSWORD = "123";
    public static final String ACTION_TIMING_CHANGE = "ACTION_TIMING_CHANGE";

    //    public static String IP = "http://192.168.1.236:8080";
    public static String IP = "http://114.242.18.143:8082";
    //    public static String PROJ = "/icu/";
    public static String PROJ = "/eoms/";

    public static String PATH_DOWNLOAD = Environment.getExternalStorageDirectory() + "/"
            + "download";

    public final static String Common = "httpServiceClient/icuAppServices.do";
    public static String Test = IP + PROJ + "httpServiceClient/icuAppServices.do";
    public static String GetRoomAndBed = IP + PROJ + "httpServiceClient/icuAppServices.do";
    public static String BandBed = IP + PROJ + "httpServiceClient/icuAppServices.do";
    public static String GetBedInfo = IP + PROJ + "httpServiceClient/getAllBedInfo.do";
    public static String VersionCheck = IP + PROJ + "httpServiceClient/icuAppServices.do";
    public static String Download = IP + PROJ + "httpServiceClient/downLoadFile.do";
    public static String SENDMESSAGE = IP + PROJ + "/main/sendMessage.do";

    public static String TestRoute = "test";
    public static String GetRoomAndBedRoute = "getAllBedInfo";
    public static String BandBedRoute = "blindPadToBed";
    public static String GetBedInfoRoute = "getBedInfo";
    public static String VersionCheckRoute = "bedVersion";

    public static void setIP() {
        Test = IP + PROJ + "httpServiceClient/icuAppServices.do";
        GetRoomAndBed = IP + PROJ + "httpServiceClient/icuAppServices.do";
        BandBed = IP + PROJ + "httpServiceClient/icuAppServices.do";
        GetBedInfo = IP + PROJ + "httpServiceClient/getAllBedInfo.do";
        VersionCheck = IP + PROJ + "httpServiceClient/icuAppServices.do";
        Download = IP + PROJ + "httpServiceClient/downLoadFile.do";
        SENDMESSAGE = IP + PROJ + "/main/sendMessage.do";
    }
}
