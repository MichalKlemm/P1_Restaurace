package engeto.projekt.ja;

import engeto.projekt.ja.CookBook.Dish;
import engeto.projekt.ja.OrderManager.OrderManager;

import java.io.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DataManager {

    private static final String DISHES_FILE = "dishes.txt";
    private static final String ORDERS_FILE = "orders.txt";
    private static final Logger logger = Logger.getLogger(DataManager.class.getName());

    public static void createFilesIfNeeded(){
        try {
            File dishFile = new File(DISHES_FILE);
            File ordersFile = new File(ORDERS_FILE);

            if (!dishFile.exists()){
                boolean created = dishFile.createNewFile();
                if (created) {
                    System.out.println("Soubor dishes.txt byl vytvořen");
                } else {
                    System.err.println("Nepodařilo se vytvořit soubor dishes.txt");
                }
            }
            if (!ordersFile.exists()){
                boolean created = ordersFile.createNewFile();
                if (created) {
                    System.out.println("Soubor orders.txt byl vytvořen");
                } else {
                    System.err.println("Nepodařilo se vytvořit soubor orders.txt");
                }
            }
        } catch (IOException e){
            logger.log(Level.SEVERE, "Error při vytváření souborů jídel a objednávek !", e);
        }
    }

    public static void saveDishes(List<Dish> dishes){
        try (PrintWriter writer = new PrintWriter(new FileWriter(DISHES_FILE))){
            for (Dish dish : dishes){
                writer.println(dish.getTitle() + "; " + dish.getPrice() + "; "
                        + dish.getPreparationTime()
                        + "; " + dish.getImage());
            }
        } catch (IOException e){
            logger.log(Level.SEVERE, "Error při ukládání jídel do souboru !", e);
        }
    }

    public static List<Dish> loadDishes() {
        List<Dish> dishes = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(DISHES_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length == 4) {
                    String title = parts[0];
                    BigDecimal price;
                    int preparationTime;
                    String image;

                    try {
                        price = new BigDecimal(parts[1].trim());
                        if (price.compareTo(BigDecimal.ZERO) <= 0) {
                            continue;
                        }
                    } catch (NumberFormatException e) {
                        continue;
                    }

                    try {
                        preparationTime = Integer.parseInt(parts[2].trim());
                        if (preparationTime <= 0) {
                            continue;
                        }
                    } catch (NumberFormatException e) {
                        continue;
                    }

                    image = parts[3].trim();

                    Dish dish = new Dish(title, price, preparationTime, image);
                    dishes.add(dish);
                }
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error při načítání jídel ze souboru !", e);
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
            logger.log(Level.SEVERE, "Error při ukládání objednávek do souboru !", e);
        }
    }
    private static OrderManager getOrderManager(String[] parts, int tableNumber, Dish dish) {
        int quantity = Integer.parseInt(parts[2]);
        LocalDateTime orderedTime = LocalDateTime.parse(parts[3]);
        LocalDateTime fulfilmentTime = parts[4].equals("null") ? null : LocalDateTime.parse(parts[4]);
        boolean paid = Boolean.parseBoolean(parts[5]);
        OrderManager order = new OrderManager(tableNumber, dish, quantity, orderedTime,fulfilmentTime,paid);
        order.setOrderedTime(orderedTime);
        order.setFullfilmentTime(fulfilmentTime);
        order.setPaid(paid);
        return order;
    }

    public static List<OrderManager> loadOrders(List<Dish> dishes, int tableNumber) {
        List<OrderManager> orders = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(ORDERS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("; ");
                if (parts.length == 6) {
                    Dish dish = findDishByTitle(dishes, parts[1]);
                    OrderManager order = getOrderManager(parts, tableNumber, dish);
                    orders.add(order);
                } else {
                    logger.log(Level.WARNING, "Jídlo s názvem " + parts[1] + " nebylo nalezeno.");
                }
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error při načítání objednávek ze souboru !", e);
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
