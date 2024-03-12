package engeto.projekt.ja.OrderManager;

import engeto.projekt.ja.CookBook.Dish;

import java.time.LocalDateTime;

public class OrderManager {

    private int tableNumber;
    private Dish dish;
    private int quantity;
    private LocalDateTime orderedTime;
    private LocalDateTime fullfilmentTime;
    private boolean paid;

    public OrderManager(int tableNumber, Dish dish, int quantity, LocalDateTime orderedTime,
                        LocalDateTime fullfilmentTime, boolean paid) {
        this.tableNumber = tableNumber;
        this.dish = dish;
        this.quantity = quantity;
        this.orderedTime = orderedTime;
        this.fullfilmentTime = fullfilmentTime;
        this.paid = paid;
    }

    public int getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(int tableNumber) {
        this.tableNumber = tableNumber;
    }

    public Dish getDish() {
        return dish;
    }

    public void setDish(Dish dish) {
        this.dish = dish;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public LocalDateTime getOrderedTime() {
        return orderedTime;
    }

    public void setOrderedTime(LocalDateTime orderedTime) {
        this.orderedTime = orderedTime;
    }

    public LocalDateTime getFullfilmentTime() {
        return fullfilmentTime;
    }

    public void setFullfilmentTime(LocalDateTime fullfilmentTime) {
        this.fullfilmentTime = fullfilmentTime;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }
    public void markAsFullfilled(){
        fullfilmentTime = LocalDateTime.now();
    }
    public  void  markAsPaid(){
        paid = true;
    }
    public boolean isPaid(){
        return paid;
    }
}
