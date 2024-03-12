package engeto.projekt.ja;

import engeto.projekt.ja.CookBook.Dish;
import engeto.projekt.ja.OrderManager.OrderManager;

import java.io.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DataManager {

    private static final String DISHES_FILE = "dishes.txt";
    private static final String ORDERS_FILE = "orders.txt";

    public static void saveDishes(List<Dish> dishes){
        try (PrintWriter writer = new PrintWriter(new FileWriter(DISHES_FILE))){
            for (Dish dish : dishes){
                writer.println(dish.getTitle() + "; " + dish.getPrice() + "; "
                        + dish.getPreparationTime()
                        + "; " + dish.getImage());
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public static List<Dish> loadDishes() {
        List<Dish> dishes = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(DISHES_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length == 4) {
                    Dish dish = new Dish(parts[0], BigDecimal.valueOf(Double.parseDouble(parts[1])), Integer.parseInt(parts[2]), parts[3]);
                    dishes.add(dish);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dishes;
    }

    public static void saveOrders(List<OrderManager> orders) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(ORDERS_FILE))) {
            for (OrderManager order : orders) {
                writer.println(order.getTableNumber() + "; " + order.getDish().getTitle() + "; "
                        + order.getQuantity() + "; " + order.getOrderedTime() + "; "
                        + order.getFullfilmentTime() + "; " + order.isPaid());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<OrderManager> loadOrders(List<Dish> dishes) {
        List<OrderManager> orders = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(ORDERS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("; ");
                if (parts.length == 6) {
                    int tableNumber = Integer.parseInt(parts[0]);
                    Dish dish = findDishByTitle(dishes, parts[1]);
                    int quantity = Integer.parseInt(parts[2]);
                    LocalDateTime orderedTime = LocalDateTime.parse(parts[3]);
                    LocalDateTime fulfilmentTime = parts[4].equals("null") ? null : LocalDateTime.parse(parts[4]);
                    boolean paid = Boolean.parseBoolean(parts[5]);
                    OrderManager order = new OrderManager(tableNumber, dish, quantity, orderedTime,fulfilmentTime,paid);
                    order.setOrderedTime(orderedTime);
                    order.setFullfilmentTime(fulfilmentTime);
                    order.setPaid(paid);
                    orders.add(order);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return orders;
    }

    private static Dish findDishByTitle(List<Dish> dishes, String title) {
        for (Dish dish : dishes) {
            if (dish.getTitle().equals(title)) {
                return dish;
            }
        }
        return null;
    }
}
