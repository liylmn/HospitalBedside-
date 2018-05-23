package com.zbiti.hospitalbedside.entity;

import com.zbiti.hospitalbedside.common.Constant;

/**
 * Created by Digsol on 2016/12/16 8:42.
 */

public class GetBedInfoSendData {
    private String imei;//PAD设备唯一标识
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

    public GetBedInfoSendData() {
        this.route = Constant.GetBedInfoRoute;
    }

    public GetBedInfoSendData(String imei) {
        this.imei = imei;
        this.route = Constant.GetBedInfoRoute;
    }
}
