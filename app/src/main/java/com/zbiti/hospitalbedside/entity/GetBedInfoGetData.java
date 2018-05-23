package com.zbiti.hospitalbedside.entity;

/**
 * Created by Digsol on 2016/12/16 8:42.
 */

public class GetBedInfoGetData {
    private String resultCode;// 000成功；001失败
    private PatientBaseInfo baseInfo;// 病人基础信息
    private NursingInfo nursingInfo;// 护理信息
    private String sysName;//系统名称，如”东部战区南京总院”
    private String bedNo;//床位号
    private String route;
    private String sysTime;//系统时间(system.currenttimemillis()的值)

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public PatientBaseInfo getBaseInfo() {
        return baseInfo;
    }

    public void setBaseInfo(PatientBaseInfo baseInfo) {
        this.baseInfo = baseInfo;
    }

    public NursingInfo getNursingInfo() {
        return nursingInfo;
    }

    public void setNursingInfo(NursingInfo nursingInfo) {
        this.nursingInfo = nursingInfo;
    }

    public String getSysName() {
        return sysName;
    }

    public void setSysName(String sysName) {
        this.sysName = sysName;
    }

    public String getBedNo() {
        return bedNo;
    }

    public void setBedNo(String bedNo) {
        this.bedNo = bedNo;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public String getSysTime() {
        return sysTime;
    }

    public void setSysTime(String sysTime) {
        this.sysTime = sysTime;
    }
}
