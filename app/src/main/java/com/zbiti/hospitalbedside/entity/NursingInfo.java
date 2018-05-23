package com.zbiti.hospitalbedside.entity;

import java.util.List;

/**
 * Created by Digsol on 2016/12/16 8:43.
 */

public class NursingInfo {
    private String nursing;//护理等级
    private String bill;//费用类别
    private List<String> allergy;//过敏情况
    private List<String> diet;//饮食方式
    private String wc;//如厕能力
    private String accompany;//是否陪护
    private String condition;//病人病情
    private List<String> meterages;//计量内容
    private List<String> protects;//防护内容
    private List<String> catheters;//导管留置
    private String operationTime;//手术时间


    public String getNursing() {
        return nursing;
    }

    public void setNursing(String nursing) {
        this.nursing = nursing;
    }

    public String getBill() {
        return bill;
    }

    public void setBill(String bill) {
        this.bill = bill;
    }

    public List<String> getAllergy() {
        return allergy;
    }

    public void setAllergy(List<String> allergy) {
        this.allergy = allergy;
    }

    public List<String> getDiet() {
        return diet;
    }

    public void setDiet(List<String> diet) {
        this.diet = diet;
    }

    public String getWc() {
        return wc;
    }

    public void setWc(String wc) {
        this.wc = wc;
    }

    public String getAccompany() {
        return accompany;
    }

    public void setAccompany(String accompany) {
        this.accompany = accompany;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public List<String> getMeterages() {
        return meterages;
    }

    public void setMeterages(List<String> meterages) {
        this.meterages = meterages;
    }

    public List<String> getProtects() {
        return protects;
    }

    public void setProtects(List<String> protects) {
        this.protects = protects;
    }

    public List<String> getCatheters() {
        return catheters;
    }

    public void setCatheters(List<String> catheters) {
        this.catheters = catheters;
    }

    public String getOperationTime() {
        return operationTime;
    }

    public void setOperationTime(String operationTime) {
        this.operationTime = operationTime;
    }
}
