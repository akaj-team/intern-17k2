package vn.asiantech.internship.models;

/**
 * Created by thanh Thien on 7/3/2017.
 * Phone
 */
public class Phone {
    private String home;
    private String mobile;
    private String office;

    public Phone(String home, String mobile, String office) {
        this.home = home;
        this.mobile = mobile;
        this.office = office;
    }

    public String getHome() {
        return home;
    }

    public void setHome(String home) {
        this.home = home;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getOffice() {
        return office;
    }

    public void setOffice(String office) {
        this.office = office;
    }
}
