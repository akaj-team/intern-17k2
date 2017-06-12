package drawerlayout;

/**
 * Created by sony on 12/06/2017.
 */

public class Function {
    private String name;
    private int image;

    public Function(String name, int image) {
        this.name = name;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
