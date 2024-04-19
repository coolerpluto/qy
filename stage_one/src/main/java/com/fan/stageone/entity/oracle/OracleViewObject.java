package com.fan.stageone.entity.oracle;

public class OracleViewObject {
    private String viewName;
    private String viewDesc;

    public OracleViewObject() {
    }

    public OracleViewObject(String viewName, String viewDesc) {
        this.viewName = viewName;
        this.viewDesc = viewDesc;
    }

    public String getViewName() {
        return viewName;
    }

    public void setViewName(String viewName) {
        this.viewName = viewName;
    }

    public String getViewDesc() {
        return viewDesc;
    }

    public void setViewDesc(String viewDesc) {
        this.viewDesc = viewDesc;
    }

    @Override
    public String toString() {
        return "OracleViewObject{" +
                "viewName='" + viewName + '\'' +
                ", viewDesc='" + viewDesc + '\'' +
                '}';
    }
}
