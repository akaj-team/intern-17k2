package vn.asiantech.internship.ninepath;

/**
 * Used as a massage project
 *
 * @author at-HangTran
 * @version 1.0
 * @since 2017-6-21
 */
class Message {
    private String sms;
    private boolean state;

    public Message(String sms, boolean state) {
        this.sms = sms;
        this.state = state;
    }

    public String getSms() {
        return sms;
    }

    public void setSms(String sms) {
        this.sms = sms;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }
}
