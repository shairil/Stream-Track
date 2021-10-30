package com.example.myapplication1.Modals;

import java.util.List;

public class Group {
    private String ott, startDate, endDate, grpId;
    private boolean isPaid;
    private Double plan;
    private List<String> members;

    public Group(String ott, String startDate, String endDate,
                 String grpId, boolean isPaid, Double plan, List<String> members) {
        this.ott = ott;
        this.startDate = startDate;
        this.endDate = endDate;
        this.grpId = grpId;
        this.isPaid = isPaid;
        this.plan = plan;
        this.members = members;
    }

    public Group() {
    }

    public String getOtt() {
        return ott;
    }

    public void setOtt(String ott) {
        this.ott = ott;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getGrpId() {
        return grpId;
    }

    public void setGrpId(String grpId) {
        this.grpId = grpId;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public void setPaid(boolean paid) {
        isPaid = paid;
    }

    public Double getPlan() {
        return plan;
    }

    public void setPlan(Double plan) {
        this.plan = plan;
    }

    public List<String> getMembers() {
        return members;
    }

    public void setMembers(List<String> members) {
        this.members = members;
    }
}
