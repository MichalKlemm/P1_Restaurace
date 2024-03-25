package engeto.projekt.ja.CookBook;

import java.util.ArrayList;
import java.util.List;

public class CookBook {

    private final List<Dish> dishes;

    public CookBook(){
        this.dishes = new ArrayList<>();
    }
    public void addDish(Dish dish){
        dishes.add(dish);
    }
    public void removeDish(Dish dish){
        dishes.remove(dish);
    }

    public Dish findDishByTitle(String title){
        for (Dish dish : dishes){
            if (dish.getTitle().equals(title)){
                return dish;
            }
        }
        return null;
    }
    public List<Dish> getDishes(){
        return dishes;
    }
}
