package engeto.projekt.ja.RestaurantManager;

import engeto.projekt.ja.CookBook.Dish;
import engeto.projekt.ja.OrderManager.OrderManager;

import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class RestaurantManager {

    private final List<OrderManager> orders;

    public RestaurantManager(List<OrderManager> orders){
        this.orders = orders;
    }

    public int getNumberOfInProgressOrders(){
        int count = 0;
        for (OrderManager order : orders){
            if (order.getFullfilmentTime() == null){
                count ++;
            }
        }
        return count;
    }
    public List<OrderManager> getOrdersSortByTime(){
        return orders.stream()
                .sorted(Comparator.comparing(OrderManager::getOrderedTime))
                .collect(Collectors.toList());
    }
    public double getAverageProcessingTimeInMinutes(){
        long totalProcessingTime = 0;
        int processedOrders = 0;
        for (OrderManager order : orders){
            if (order.getFullfilmentTime() != null){
                long processingTime = Duration.between(order.getOrderedTime()
                , order.getFullfilmentTime()).toMinutes();
                totalProcessingTime += processingTime;
                processedOrders++;
            }
        }
        if (processedOrders == 0){
            return 0;
        }
        return (double) totalProcessingTime / processedOrders;
    }
    public List<Dish> getOrderedDishesForToday(){
        List<Dish> orderedDishes = new ArrayList<>();
        LocalDate today = LocalDate.now();
        for (OrderManager order : orders){
            if (order.getOrderedTime().toLocalDate().equals(today)){
                orderedDishes.add(order.getDish());
            }
        }
        return orderedDishes;
    }
    public String exportOrdersForTable(int tableNumber) {
        StringBuilder output = new StringBuilder();
        output.append("** Objednávky pro stůl č. ");
        output.append(String.format("%02d", tableNumber)); // %02d - formát čísla tak, aby mělo alespoň 2 číslice + pokud je číslo měnší než 10 bude doplněno 0 na začátku.
        output.append(" **\n");
        output.append("****\n");

        int itemNumber = 1;
        for (OrderManager order : orders) {
            if (order.getTableNumber() == tableNumber) {
                output.append(itemNumber);
                output.append(". ");
                output.append(order.getDish().getTitle());
                output.append(" ");
                output.append(order.getQuantity());
                output.append("x (");
                output.append(order.getDish().getPrice());
                output.append(" Kč): ");
                output.append(order.getOrderedTime().toLocalTime());
                output.append("-");
                if (order.getFullfilmentTime() != null) {
                    output.append(order.getFullfilmentTime().toLocalTime());
                }
                output.append(order.isPaid() ? " zaplaceno\n" : "\n");
                itemNumber++;
            }
        }

        output.append("******");
        return output.toString();
    }
}
