package vn.asiantech.internship.models;

/**
 * Created by Thanh Thien on 7/3/2017.
 * Contact
 */
public class Contact {
    private String name;
    private String email;
    private String address;
    private String gender;
    private Phone contacts;

    public Contact(String name, String email, String address, String gender, Phone contacts) {
        this.name = name;
        this.email = email;
        this.address = address;
        this.gender = gender;
        this.contacts = contacts;
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

    public Phone getContacts() {
        return contacts;
    }

    public void setContacts(Phone contacts) {
        this.contacts = contacts;
    }
}
