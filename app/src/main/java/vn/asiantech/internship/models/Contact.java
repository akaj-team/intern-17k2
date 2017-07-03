package vn.asiantech.internship.models;

/**
 * Created by quanghai on 03/07/2017.
 */

public class Contact {
    private String name;
    private String email;
    private Phone phone;

    public Contact() {
    }

    public Contact(String name, String email, Phone phone) {
        this.name = name;
        this.email = email;
        this.phone = phone;
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

    public Phone getPhone() {
        return phone;
    }

    public void setPhone(Phone phone) {
        this.phone = phone;
    }
}
