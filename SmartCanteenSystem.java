import java.util.ArrayList;
import java.util.Scanner;

// -------------------- USER CLASS --------------------
class User {
    protected int userId;
    protected String name;

    public User(int userId, String name) {
        this.userId = userId;
        this.name = name;
    }
}

// -------------------- STUDENT CLASS --------------------
class Student extends User {
    public Student(int userId, String name) {
        super(userId, name);
    }
}

// -------------------- FOOD ITEM --------------------
class FoodItem {
    private int itemId;
    private String itemName;
    private double price;

    public FoodItem(int itemId, String itemName, double price) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.price = price;
    }

    public int getItemId() {
        return itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public double getPrice() {
        return price;
    }
}

// -------------------- ORDER CLASS --------------------
class Order {
    private Student student;
    private ArrayList<FoodItem> items = new ArrayList<>();

    public Order(Student student) {
        this.student = student;
    }

    public void addItem(FoodItem item) {
        items.add(item);
        System.out.println(item.getItemName() + " added to cart.");
    }

    public double calculateTotal() {
        double total = 0;
        for (FoodItem item : items) {
            total += item.getPrice();
        }
        return total;
    }

    public void showOrder() {
        if (items.isEmpty()) {
            System.out.println("Cart is empty.");
            return;
        }

        System.out.println("\n------ YOUR ORDER ------");
        for (FoodItem item : items) {
            System.out.println(item.getItemName() + " - Rs. " + item.getPrice());
        }

        System.out.println("------------------------");
        System.out.println("Total Amount: Rs. " + calculateTotal());
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public Student getStudent() {
        return student;
    }
}

// -------------------- PAYMENT INTERFACE --------------------
interface Payment {
    void pay(double amount);
}

// -------------------- PAYMENT METHODS --------------------
class UpiPayment implements Payment {
    public void pay(double amount) {
        System.out.println("Payment of Rs. " + amount + " done via UPI.");
    }
}

class CashPayment implements Payment {
    public void pay(double amount) {
        System.out.println("Please pay Rs. " + amount + " at the counter.");
    }
}

// -------------------- MAIN CLASS --------------------
public class SmartCanteenSystem {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("===== SMART CANTEEN SYSTEM =====");

        // Student Login
        System.out.print("Enter Student ID: ");
        int id = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter Student Name: ");
        String name = sc.nextLine();

        Student student = new Student(id, name);

        // Food Menu
        ArrayList<FoodItem> menu = new ArrayList<>();

        menu.add(new FoodItem(1, "Idli", 30));
        menu.add(new FoodItem(2, "Vada", 20));
        menu.add(new FoodItem(3, "Coffee", 15));
        menu.add(new FoodItem(4, "Dosa", 50));
        menu.add(new FoodItem(5, "Tea", 10));

        Order order = new Order(student);

        int choice;

        do {
            System.out.println("\n===== MENU =====");
            System.out.println("1. View Food Menu");
            System.out.println("2. Add Item to Cart");
            System.out.println("3. View Order");
            System.out.println("4. Make Payment");
            System.out.println("5. Exit");

            System.out.print("Enter your choice: ");
            choice = sc.nextInt();

            switch (choice) {

                case 1:
                    System.out.println("\n------ FOOD MENU ------");
                    for (FoodItem item : menu) {
                        System.out.println(
                                item.getItemId() + ". " +
                                item.getItemName() +
                                " - Rs. " + item.getPrice());
                    }
                    break;

                case 2:
                    System.out.print("Enter Item ID to add: ");
                    int itemChoice = sc.nextInt();

                    boolean found = false;

                    for (FoodItem item : menu) {
                        if (item.getItemId() == itemChoice) {
                            order.addItem(item);
                            found = true;
                            break;
                        }
                    }

                    if (!found) {
                        System.out.println("Invalid Item ID.");
                    }

                    break;

                case 3:
                    order.showOrder();
                    break;

                case 4:

                    if (order.isEmpty()) {
                        System.out.println("Your cart is empty.");
                        break;
                    }

                    order.showOrder();

                    System.out.println("\nChoose Payment Method");
                    System.out.println("1. UPI");
                    System.out.println("2. Cash");

                    System.out.print("Enter choice: ");
                    int payChoice = sc.nextInt();

                    Payment payment;

                    if (payChoice == 1) {
                        payment = new UpiPayment();
                    } else {
                        payment = new CashPayment();
                    }

                    payment.pay(order.calculateTotal());

                    System.out.println("\nOrder placed successfully!");
                    System.out.println("Collect your food during break without waiting in queue.");

                    break;

                case 5:
                    System.out.println("Thank you for using Smart Canteen System.");
                    break;

                default:
                    System.out.println("Invalid choice.");
            }

        } while (choice != 5);

        sc.close();
    }
}