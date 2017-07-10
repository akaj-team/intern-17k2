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
    private Phone phone;

    public Contact(String name, String email, String address, String gender, Phone phone) {
        this.name = name;
        this.email = email;
        this.address = address;
        this.gender = gender;
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

    public Phone getPhone() {
        return phone;
    }

    public void setPhone(Phone phone) {
        this.phone = phone;
    }
}
