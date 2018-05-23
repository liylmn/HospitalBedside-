package com.zbiti.hospitalbedside.net;


import com.zbiti.hospitalbedside.common.Constant;
import com.zbiti.hospitalbedside.entity.GetRoomAndBedGetData;
import com.zbiti.hospitalbedside.entity.MainDataEntity;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;



public class NetApi {


    /**
     * 首页展示接口
     *
     * @param jsonCallback
     */
    public static void getMainData(HashMap<String, String> params, JsonCallback<MainDataEntity> jsonCallback) {
        invokeGet(Constant.GetBedInfo, params, jsonCallback);
    }
    /**
     * 病床展示接口
     *
     * @param jsonCallback
     */
    public static void getsetData(HashMap<String, String> params, JsonCallback<GetRoomAndBedGetData> jsonCallback) {
        invokeGet(Constant.GetRoomAndBed, params, jsonCallback);
    }
    /**
     * 绑定病床接口
     *
     * @param jsonCallback
     */
    public static void getBanBedData(HashMap<String, String> params, JsonCallback<GetRoomAndBedGetData> jsonCallback) {
        invokeGet(Constant.BandBed, params, jsonCallback);
    }
    /**
     * 发送蓝牙消息
     *
     * @param jsonCallback
     */
    public static void sendblue(HashMap<String, String> params, JsonCallback<GetRoomAndBedGetData> jsonCallback) {
        invokeGet(Constant.SENDMESSAGE, params, jsonCallback);
    }

    private static void invokeGet( String url,HashMap params, Callback callback) {
        OkHttpUtils.get().url(url)
                .params(params == null ? new HashMap<String, String>() : params)
                .build()
                .execute(callback);
    }

    private static void invokePost(String url, HashMap params, Callback callback) {
        OkHttpUtils.post().url(url)
                .params(params == null ? new HashMap<String, String>() : params)
                .build()
                .execute(callback);
    }

//    private static void invokePostTag(String url, HashMap params, Callback callback, int tag) {
//        OkHttpUtils.post().url(url)
//                .params(params == null ? new HashMap<String, String>() : params)
//                .addHeader("terminal", TERMINAL)
//                .tag(tag)
//                .build()
//                .execute(callback);
//    }


//    private static void invokePostFile(String url, HashMap params, ArrayList<ImageItem> pathList, Callback basemodelCallback) {
//
//        Map<String, File> files = new HashMap<>();
//        if (pathList != null) {
//            for (int i = 0; i < pathList.size(); i++) {
//                String newPath = BitmapUtils.compressImageUpload(pathList.get(i).path);
//                files.put(newPath.split("/")[newPath.split("/").length - 1], new File(newPath));
//
//            }
//        }
//        OkHttpUtils
//                .post()
//                .url(url)
//                .params(params)
//                .files("files", files)
//                .build()
//                .execute(basemodelCallback);
//    }
//
//    private static void invokeFile(String url, HashMap params, Map<String, File> files, Callback basemodelCallback) {
//        OkHttpUtils
//                .post()
//                .url(url)
//                .params(params)
//                .files("files", files)
//                .build()
//                .execute(basemodelCallback);
//    }
}
