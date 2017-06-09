package recyclerview;

class User {
    private String name;
    private String description;
    private boolean state;

    User(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    String getDescription() {
        return description;
    }

    boolean isState() {
        return state;
    }

    void setState(boolean state) {
        this.state = state;
    }
}
