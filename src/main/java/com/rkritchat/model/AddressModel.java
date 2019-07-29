package com.rkritchat.model;

import com.rkritchat.anotation.Required;

public class AddressModel {
    @Required
    private String tel;
    private String state;
    private String road;

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getRoad() {
        return road;
    }

    public void setRoad(String road) {
        this.road = road;
    }
}
