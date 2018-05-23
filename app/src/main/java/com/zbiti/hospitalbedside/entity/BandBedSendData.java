package com.zbiti.hospitalbedside.entity;

import com.zbiti.hospitalbedside.common.Constant;

/**
 * Created by Digsol on 2016/12/16 8:38.
 */

public class BandBedSendData {
    private String imei;//PAD设备唯一标识
    private String password;//密钥
    private String bedId;//病床唯一标识
    private String route;
    private String uuid;

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBedId() {
        return bedId;
    }

    public void setBedId(String bedId) {
        this.bedId = bedId;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public BandBedSendData() {
        this.route = Constant.BandBedRoute;
    }

    public BandBedSendData(String imei, String password, String bedId,String uuid) {
        this.imei = imei;
        this.password = password;
        this.bedId = bedId;
        this.route = Constant.BandBedRoute;
        this.uuid = uuid;
    }
}
