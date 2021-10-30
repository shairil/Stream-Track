package com.example.myapplication1.Modals;

public class OTT {
    private String platform, userName, email;
    private String plan, groupId;
    private String startDate;

    public OTT(String platform, String userName, String email, String plan, String startDate) {
        this.platform = platform;
        this.userName = userName;
        this.email = email;
        this.plan = plan;
        this.startDate = startDate;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public OTT(String platform){
        this.platform = platform;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public OTT() {
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }
}
