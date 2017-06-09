package recyclerview;

/**
 * as a model class,get and set data to User object
 *
 * @author at-HangTran
 * @version 1.0
 * @since 2017-6-9
 */

class User {
    private String name;
    private String description;
    private boolean state;

    public User(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

}
