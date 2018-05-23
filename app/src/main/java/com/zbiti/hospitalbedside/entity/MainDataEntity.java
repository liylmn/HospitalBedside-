package com.zbiti.hospitalbedside.entity;

import java.util.List;

/**
 * <pre>
 *     author : liy_lmn
 *     time   : 2017/10/11
 *     desc   :
 * </pre>
 */

public class MainDataEntity {

    /**
     * resultCode : 0
     * data : {"information":"重症急性胰腺炎","hosNum":"1008841169","idNum":"119","enghosName":"Nanjing General Hospital","codeImg":"http://114.242.18.143:8082/eoms/qrCode.png","touseNames":["病重","磺胺药过敏","防坠床","防烫伤","防跌倒","防压疮","防拔管","防误吸","防走失"],"clasName":"全费","reminds2":["CVC/PICC导管","导尿管/膀胱穿刺","CRRT导管","负压引流管","胃肠减压管"],"hosName":"南京军区总医院","reminds":["尿液计量","造口液计量","腹腔液计量"],"hosImg":"http://114.242.18.143:8082/eoms/hosImg.png","age":"56","userName":"赵黎霞","gender":"女","hosTime":"2017-05-25 12:17","surgeryData":"2017-06-11 16:31","bedNum":"6","doctorName":"柯路"}
     * resMsg : 成功
     */

    private String resultCode;
    private DataBean data;
    private String resMsg;

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getResMsg() {
        return resMsg;
    }

    public void setResMsg(String resMsg) {
        this.resMsg = resMsg;
    }

    public static class DataBean {
        /**
         * information : 重症急性胰腺炎
         * hosNum : 1008841169
         * idNum : 119
         * enghosName : Nanjing General Hospital
         * codeImg : http://114.242.18.143:8082/eoms/qrCode.png
         * touseNames : ["病重","磺胺药过敏","防坠床","防烫伤","防跌倒","防压疮","防拔管","防误吸","防走失"]
         * clasName : 全费
         * reminds2 : ["CVC/PICC导管","导尿管/膀胱穿刺","CRRT导管","负压引流管","胃肠减压管"]
         * hosName : 南京军区总医院
         * reminds : ["尿液计量","造口液计量","腹腔液计量"]
         * hosImg : http://114.242.18.143:8082/eoms/hosImg.png
         * age : 56
         * userName : 赵黎霞
         * gender : 女
         * hosTime : 2017-05-25 12:17
         * surgeryData : 2017-06-11 16:31
         * bedNum : 6
         * doctorName : 柯路
         */

        private String information;
        private String hosNum;
        private String idNum;
        private String enghosName;
        private String codeImg;
        private String clasName;
        private String hosName;
        private String hosImg;
        private String age;
        private String userName;
        private String gender;
        private String hosTime;
        private String surgeryData;
        private String bedNum;
        private String doctorName;
        private List<String> touseNames;
        private List<String> reminds2;
        private List<String> reminds;

        public String getInformation() {
            return information;
        }

        public void setInformation(String information) {
            this.information = information;
        }

        public String getHosNum() {
            return hosNum;
        }

        public void setHosNum(String hosNum) {
            this.hosNum = hosNum;
        }

        public String getIdNum() {
            return idNum;
        }

        public void setIdNum(String idNum) {
            this.idNum = idNum;
        }

        public String getEnghosName() {
            return enghosName;
        }

        public void setEnghosName(String enghosName) {
            this.enghosName = enghosName;
        }

        public String getCodeImg() {
            return codeImg;
        }

        public void setCodeImg(String codeImg) {
            this.codeImg = codeImg;
        }

        public String getClasName() {
            return clasName;
        }

        public void setClasName(String clasName) {
            this.clasName = clasName;
        }

        public String getHosName() {
            return hosName;
        }

        public void setHosName(String hosName) {
            this.hosName = hosName;
        }

        public String getHosImg() {
            return hosImg;
        }

        public void setHosImg(String hosImg) {
            this.hosImg = hosImg;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getHosTime() {
            return hosTime;
        }

        public void setHosTime(String hosTime) {
            this.hosTime = hosTime;
        }

        public String getSurgeryData() {
            return surgeryData;
        }

        public void setSurgeryData(String surgeryData) {
            this.surgeryData = surgeryData;
        }

        public String getBedNum() {
            return bedNum;
        }

        public void setBedNum(String bedNum) {
            this.bedNum = bedNum;
        }

        public String getDoctorName() {
            return doctorName;
        }

        public void setDoctorName(String doctorName) {
            this.doctorName = doctorName;
        }

        public List<String> getTouseNames() {
            return touseNames;
        }

        public void setTouseNames(List<String> touseNames) {
            this.touseNames = touseNames;
        }

        public List<String> getReminds2() {
            return reminds2;
        }

        public void setReminds2(List<String> reminds2) {
            this.reminds2 = reminds2;
        }

        public List<String> getReminds() {
            return reminds;
        }

        public void setReminds(List<String> reminds) {
            this.reminds = reminds;
        }
    }
}
