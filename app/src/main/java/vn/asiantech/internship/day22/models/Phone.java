package vn.asiantech.internship.day22.models;

/**
 * Created by at-dinhvo on 03/07/2017.
 */
public class Phone {

    private String mobile;
    private String home;
    private String office;

    public Phone(String mobile, String home, String office) {
        this.mobile = mobile;
        this.home = home;
        this.office = office;
    }

    public String getMobile() {
        return mobile;
    }
}
