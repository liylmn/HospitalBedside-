package com.zbiti.hospitalbedside.entity;

import com.zbiti.hospitalbedside.entity.RoomInfo;

import java.util.List;

/**
 * Created by Digsol on 2016/12/16 8:35.
 */

public class GetRoomAndBedGetData {
    private String resultCode;//000成功；001失败
    private List<RoomInfo> roomInfos;//床位信息列表
    private String route;

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public List<RoomInfo> getRoomInfos() {
        return roomInfos;
    }

    public void setRoomInfos(List<RoomInfo> roomInfos) {
        this.roomInfos = roomInfos;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }
}
