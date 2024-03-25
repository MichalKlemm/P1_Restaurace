package engeto.projekt.ja.CookBook;

import java.math.BigDecimal;

public class Dish {

    private int index;
    private String title;
    private BigDecimal price;
    private Integer preparationTime;
    private String image;

    public Dish(String title, BigDecimal price, Integer preparationTime, String image) {
        this.index = index;
        this.title = title;
        this.price = price;
        this.preparationTime = preparationTime > 0 ? preparationTime : 1;
        this.image = image != null && !image.isEmpty() ? image : "blank.png";
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getPreparationTime() {
        return preparationTime;
    }

    public void setPreparationTime(Integer preparationTime) {
        this.preparationTime = preparationTime > 0 ? preparationTime : 1;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image != null && !title.isEmpty() ? image : "blank.png";
    }
    public boolean isValid() {
        return title != null && !title.isEmpty() && price != null && price.compareTo(BigDecimal.ZERO) >= 0
                && preparationTime != null && preparationTime > 0 && image != null && !image.isEmpty();
    }
}
