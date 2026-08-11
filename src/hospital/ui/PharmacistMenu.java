package hospital.ui;

import hospital.model.Medicine;
import hospital.service.HospitalService;

import java.util.Scanner;

public class PharmacistMenu {

    public static void display(Scanner scanner, HospitalService hospitalService) {
        boolean exit = false;
        while (!exit) {
            System.out.println("\n=== PHARMACY DASHBOARD ===");
            System.out.println("1. Add Medicine to Stock");
            System.out.println("2. View Pharmacy Inventory");
            System.out.println("3. Back to Main Menu");
            System.out.print("Select an option: ");

            String choice = scanner.nextLine();
            try {
                switch (choice) {
                    case "1":
                        addMedicine(scanner, hospitalService);
                        break;
                    case "2":
                        viewInventory(hospitalService);
                        break;
                    case "3":
                        exit = true;
                        break;
                    default:
                        System.out.println(" Invalid choice.");
                }
            } catch (Exception e) {
                System.out.println(" Error: " + e.getMessage());
            }
        }
    }

    private static void addMedicine(Scanner scanner, HospitalService service) throws Exception {
        System.out.print("Enter Medicine ID: ");
        String id = scanner.nextLine();
        System.out.print("Enter Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Unit Price: $");
        double price = Double.parseDouble(scanner.nextLine());
        System.out.print("Enter Initial Stock Quantity: ");
        int stock = Integer.parseInt(scanner.nextLine());

        Medicine medicine = new Medicine(id, name, price, stock);
        service.getPharmacyService().addMedicine(medicine);
    }

    private static void viewInventory(HospitalService service) {
        System.out.println("\n--- Current Pharmacy Inventory ---");
        var inventory = service.getPharmacyService().getInventory();
        if (inventory.isEmpty()) {
            System.out.println("Inventory is empty.");
            return;
        }
        for (Medicine m : inventory) {
            System.out.println("ID: " + m.getMedicineID() + " | Name: " + m.getName()
                    + " | Price: $" + m.getPrice() + " | Stock: " + m.getStockQuantity());
        }
    }
}