package com.zbiti.hospitalbedside.entity;

import com.zbiti.hospitalbedside.common.Constant;

public class TestSendData {
    private String imei;
    private String route;

    public String getImei() {

        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public TestSendData() {
        this.route = Constant.TestRoute;
    }

    public TestSendData(String imei) {
        this.imei = imei;
        this.route = Constant.TestRoute;
    }
}
