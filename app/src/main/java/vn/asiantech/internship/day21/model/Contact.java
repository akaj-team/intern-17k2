package vn.asiantech.internship.day21.model;

/**
 * Copyright © 2017 AsianTech inc.
 * Created by rimoka on 03/07/2017.
 */

public class Contact {
    private String id;
    private String name;
    private String email;
    private String address;
    private String gender;
    private String phone;

    public Contact() {
    }

    public Contact(String id, String name, String email, String address, String gender, String phone) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.address = address;
        this.gender = gender;
        this.phone = phone;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
