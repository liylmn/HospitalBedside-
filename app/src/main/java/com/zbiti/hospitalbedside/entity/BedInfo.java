package com.zbiti.hospitalbedside.entity;

/**
 * Created by Digsol on 2016/12/15 19:05.
 */

public class BedInfo {
    private String bedId;//病床唯一标识
    private String bedNo;//病床号
    private String roomNo;//所在病房号
    private String imei;

    public String getBedId() {
        return bedId;
    }

    public void setBedId(String bedId) {
        this.bedId = bedId;
    }

    public String getBedNo() {
        return bedNo;
    }

    public void setBedNo(String bedNo) {
        this.bedNo = bedNo;
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
}
