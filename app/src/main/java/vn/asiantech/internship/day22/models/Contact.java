package vn.asiantech.internship.day22.models;

/**
 * Created by at-dinhvo on 03/07/2017.
 */
public class Contact {

    private String id;
    private String name;
    private String email;
    private String gender;
    private String address;
    private Phone phone;

    public Contact(String id, String name, String email, String gender, String address, Phone phone) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.gender = gender;
        this.address = address;
        this.phone = phone;
    }

    public String getGender() {
        return gender;
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

    public Phone getPhone() {
        return phone;
    }
}
