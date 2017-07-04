package vn.asiantech.internship.annotations;

/**
 * Author AsianTech Inc.
 * Created by sony on 04/07/2017.
 */
public class Contact {
    private String name;
    private String mail;
    private String phone;

    public Contact() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
