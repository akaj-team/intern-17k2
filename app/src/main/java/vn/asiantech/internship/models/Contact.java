package vn.asiantech.internship.models;

/**
 * @author at-cuongcao
 * @version 1.0
 * @since 07/03/2017
 */
public class Contact {
    private String name;
    private String email;
    private String phone;

    public Contact() {
    }

    public Contact(String name, String email, String phone) {
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
