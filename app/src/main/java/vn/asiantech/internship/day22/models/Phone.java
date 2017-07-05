package vn.asiantech.internship.day22.models;

/**
 * Created by at-dinhvo on 03/07/2017.
 */
public class Phone {

    private String mobile;
    private String home;
    private String office;

    public Phone() {
    }

    public Phone(String mobile, String home, String office) {
        this.mobile = mobile;
        this.home = home;
        this.office = office;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getHome() {
        return home;
    }

    public void setHome(String home) {
        this.home = home;
    }

    public String getOffice() {
        return office;
    }

    public void setOffice(String office) {
        this.office = office;
    }

    public String getMobile() {
        return mobile;
    }
}
