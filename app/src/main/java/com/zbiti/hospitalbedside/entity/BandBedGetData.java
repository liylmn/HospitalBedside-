package com.zbiti.hospitalbedside.entity;

/**
 * Created by Digsol on 2016/12/16 8:40.
 */

public class BandBedGetData {
    private String resultCode;// 000成功；001失败
    private String route;

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }
}
