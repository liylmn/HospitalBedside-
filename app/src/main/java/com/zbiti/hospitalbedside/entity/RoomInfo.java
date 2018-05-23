package com.zbiti.hospitalbedside.entity;

import java.util.List;

/**
 * Created by Digsol on 2016/12/15 19:05.
 */

public class RoomInfo {
    private String roomId;//病房唯一标识
    private String roomNo;//病房号
    private String imei;
    private List<BedInfo> bedInfos;//病床信息列表

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(String roomNo) {
        this.roomNo = roomNo;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public List<BedInfo> getBedInfos() {
        return bedInfos;
    }

    public void setBedInfos(List<BedInfo> bedInfos) {
        this.bedInfos = bedInfos;
    }
}
