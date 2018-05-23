package com.zbiti.hospitalbedside.entity;

import com.zbiti.hospitalbedside.common.Constant;

/**
 * Created by Digsol on 2017/1/6 17:09.
 */

public class VersionCheckSendData {
    private String route;//接口唯一标识 bedVersion

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public VersionCheckSendData() {
        this.route = Constant.VersionCheckRoute;
    }
}
