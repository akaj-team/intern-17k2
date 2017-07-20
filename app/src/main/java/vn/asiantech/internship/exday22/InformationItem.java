package vn.asiantech.internship.exday22;

/**
 * Copyright © 2016 AsianTech inc.
 * Created by datbu on 03-07-2017.
 */
class InformationItem {
    private String name;
    private String email;
    private String mobile;

    InformationItem(String name, String email, String mobile) {
        this.name = name;
        this.email = email;
        this.mobile = mobile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    String getEmail() {
        return email;
    }

    String getMobile() {
        return mobile;
    }

}
