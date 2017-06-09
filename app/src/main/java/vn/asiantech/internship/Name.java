package vn.asiantech.internship;

class Name {
    private String name;
    private boolean addtt;

    Name(String name, boolean addtt) {
        this.name = name;
        this.addtt = addtt;
    }

    boolean isAddtt() {
        return addtt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    void setAddtt() {
        addtt = !addtt;
    }
}
