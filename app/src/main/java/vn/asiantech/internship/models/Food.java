package vn.asiantech.internship.models;

/**
 * model food
 * Created by huypham on 15/06/2017.
 */

public class Food {
    private String foodName;
    private int[] image;
    private String content;

    public Food(String foodName, int[] image, String content) {
        this.foodName = foodName;
        this.image = image;
        this.content = content;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public int[] getImage() {
        return image;
    }

    public void setImage(int[] image) {
        this.image = image;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
