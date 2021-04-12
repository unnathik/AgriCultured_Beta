package com.agricultured.agricultured.v1.beta;

public class ExpertUserModel {
    private String uid, name, phone, organization;
    private boolean active;

    public ExpertUserModel() {
    }

    public ExpertUserModel(String uid, String name, String phone, String organization, boolean active) {
        this.uid = uid;
        this.name = name;
        this.phone = phone;
        this.organization = organization;
        this.active = active;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}