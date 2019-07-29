package com.rkritchat.model;

import com.rkritchat.anotation.Required;

public class NoteBookModel {
    @Required
    private String name;
    @Required
    private int price;
    @Required
    private String[] test;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String[] getTest() {
        return test;
    }

    public void setTest(String[] test) {
        this.test = test;
    }
}
