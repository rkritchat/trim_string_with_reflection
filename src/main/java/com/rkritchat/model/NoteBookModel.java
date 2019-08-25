package com.rkritchat.model;

import com.rkritchat.anotation.Required;

import java.util.List;

public class NoteBookModel {
    @Required
    private String name;
    @Required
    private int price;
    @Required
    private String[] people;
    private double amount;
    private List<String> address;

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

    public String[] getPeople() {
        return people;
    }

    public void setPeople(String[] people) {
        this.people = people;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public List<String> getAddress() {
        return address;
    }

    public void setAddress(List<String> address) {
        this.address = address;
    }
}
