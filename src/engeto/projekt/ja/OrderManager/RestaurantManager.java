package engeto.projekt.ja.OrderManager;

import java.time.Duration;
import java.util.List;

public class RestaurantManager {

    private List<OrderManager> orders;

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
        orders.sort((o1, o2) -> o1.getOrderedTime().compareTo
                (o2.getOrderedTime()));
        return orders;
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
}
