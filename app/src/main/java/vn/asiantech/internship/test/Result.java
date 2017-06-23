package vn.asiantech.internship.test;

/**
 * As a result project when complete test.
 *
 * @author at-HangTran
 * @version 1.0
 * @since 2017-6-23
 */
class Result {
    private String question;
    private boolean isTrue;

    Result(String question, boolean isTrue) {
        this.question = question;
        this.isTrue = isTrue;
    }

    String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public boolean isTrue() {
        return isTrue;
    }

    public void setTrue(boolean aTrue) {
        isTrue = aTrue;
    }
}
