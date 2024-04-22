import engeto.projekt.ja.DataManager;
import engeto.projekt.ja.CookBook.Dish;
import engeto.projekt.ja.OrderManager.OrderManager;
import engeto.projekt.ja.RestaurantManager.RestaurantManager;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Main app = new Main();
        app.start();
    }
    public void start(){
        DataManager dataManager = new DataManager();
        dataManager.createFilesIfNeeded();

        List<Dish> dishes = dataManager.loadDishes();
        List<OrderManager> orders = dataManager.loadOrders(dishes, 10);

        prepareTestData(dishes, orders);
        printManagementInfo(orders);

        dataManager.saveDishes(dishes);
        dataManager.saveOrders(orders);
    }

    private void prepareTestData(List<Dish> dishes, List<OrderManager> orders) {
        Dish dish1 = new Dish("Kuřecí řízek obalovaný 150g", BigDecimal.valueOf(120),
                150, "kuřecirizek.jpg");
        Dish dish2 = new Dish("Hranolky 150g", BigDecimal.valueOf(50),
                150, "hranolky.jpg");
        Dish dish3 = new Dish("Pstruh na víně 200g", BigDecimal.valueOf(180),
                200, "pstruh.jpg");
        Dish dish4 = new Dish("Kofola 0.5 l", BigDecimal.valueOf(25),
                500, "kofola.jpg");

        dishes.add(dish1);
        dishes.add(dish2);
        dishes.add(dish3);
        dishes.add(dish4);

        OrderManager order1 = new OrderManager(15, dish1, 2,
                LocalDateTime.of(2024, 3, 25, 12, 0),
                null, false);
        OrderManager order2 = new OrderManager(15, dish2, 2,
                LocalDateTime.of(2024, 3, 25, 12, 0),
                null, false);
        OrderManager order3 = new OrderManager(15, dish4, 2,
                LocalDateTime.of(2024, 3, 25, 12, 0),
                LocalDateTime.of(2024, 3, 25, 12, 5), true);
        OrderManager order4 = new OrderManager(2, dish1, 2,
                LocalDateTime.of(2024, 3, 25, 12, 0),
                null, false);

        orders.add(order1);
        orders.add(order2);
        orders.add(order3);
        orders.add(order4);
    }

    private BigDecimal calculateTotalBillForTable(int tableNumber, List<OrderManager> orders) {
        BigDecimal totalBill = BigDecimal.ZERO;
        for (OrderManager order : orders) {
            if (order.getTableNumber() == tableNumber) {
                totalBill = totalBill.add(order.getDish().getPrice().multiply(BigDecimal.valueOf(order.getQuantity())));
            }
        }
        return totalBill;
    }

    private void printManagementInfo(List<OrderManager> orders) {
        BigDecimal totalBillForTable15 = calculateTotalBillForTable(15, orders);
        System.out.println("Celková cena objednávky pro stůl č. 15: " + totalBillForTable15 + " Kč");
        System.out.println();

        RestaurantManager restaurantManager = new RestaurantManager(orders);

        int inProgressOrders = restaurantManager.getNumberOfInProgressOrders();
        System.out.println("Počet aktuálně rozpracovaných a nedokončených objednávek: " + inProgressOrders);

        List<Dish> orderedDishes = restaurantManager.getOrderedDishesForToday();
        System.out.println("Seznam jídel objednaných dnes:");
        for (Dish dish : orderedDishes) {
            System.out.println("- " + dish.getTitle());
        }

        String tableOrders15 = restaurantManager.exportOrdersForTable(15);
        System.out.println("Seznam objednávek pro stůl č. 15:\n" + tableOrders15);

        String tableOrders2 = restaurantManager.exportOrdersForTable(2);
        System.out.println("Seznam objednávek pro stůl č. 2:\n" + tableOrders2);

        double avgProcessingTime = restaurantManager.getAverageProcessingTimeInMinutes();
        System.out.println("Průměrná doba zpracování objednávek: " + avgProcessingTime + " minut");
    }
}