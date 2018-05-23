package com.zbiti.hospitalbedside.entity;

/**
 * Created by Digsol on 2017/1/6 17:09.
 */

public class VersionCheckGetData {
    private String code;//当前病床端最新版本code
    private String content;//该版本更新内容
    private String route;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }
}
