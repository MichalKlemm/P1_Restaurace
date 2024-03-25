package engeto.projekt.ja.RestaurantManager;

import engeto.projekt.ja.CookBook.Dish;
import engeto.projekt.ja.OrderManager.OrderManager;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class RestaurantManager {

    private final List<OrderManager> orders;

    public RestaurantManager(List<OrderManager> orders) {
        this.orders = orders;
    }

    public int getNumberOfInProgressOrders() {
        return (int) orders.stream()
                .filter(order -> order.getFullfilmentTime() == null)
                .count();
    }

    public List<OrderManager> getOrdersSortByTime() {
        return orders.stream()
                .sorted((o1, o2) -> o1.getOrderedTime().compareTo(o2.getOrderedTime()))
                .collect(Collectors.toList());
    }

    public double getAverageProcessingTimeInMinutes() {
        List<OrderManager> processedOrders = orders.stream()
                .filter(order -> order.getFullfilmentTime() != null)
                .collect(Collectors.toList());

        if (processedOrders.isEmpty()) {
            return 0;
        }

        long totalProcessingTimeSeconds = processedOrders.stream()
                .mapToLong(order -> Duration.between(order.getOrderedTime(), order.getFullfilmentTime()).getSeconds())
                .sum();

        return totalProcessingTimeSeconds / (double) processedOrders.size() / 60.0;
    }

    public List<Dish> getOrderedDishesForToday() {
        LocalDate today = LocalDate.now();
        return orders.stream()
                .filter(order -> order.getOrderedTime().toLocalDate().equals(today))
                .map(OrderManager::getDish)
                .collect(Collectors.toList());
    }

    public String exportOrdersForTable(int tableNumber) {
        StringBuilder output = new StringBuilder();
        output.append("** Objednávky pro stůl č. ");
        output.append(String.format("%02d", tableNumber));
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
                output.append(order.getOrderedTime().format(DateTimeFormatter.ofPattern("HH:mm")));
                output.append("-");
                if (order.getFullfilmentTime() != null) {
                    output.append(order.getFullfilmentTime().format(DateTimeFormatter.ofPattern("HH:mm")));
                }
                output.append(order.isPaid() ? " zaplaceno\n" : "\n");
                itemNumber++;
            }
        }

        output.append("******");
        return output.toString();
    }
}
