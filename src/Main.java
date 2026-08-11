import hospital.service.HospitalService;
import hospital.ui.AdminMenu;
import hospital.ui.DoctorMenu;
import hospital.ui.NurseMenu;
import hospital.ui.PatientMenu;
import hospital.ui.PharmacistMenu;
import hospital.util.DataManager;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        // Restore system state from file serialization
        HospitalService hospitalService = DataManager.loadData();
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        System.out.println("=================================================");
        System.out.println("🏥 WELCOME TO THE HOSPITAL MANAGEMENT SYSTEM 🏥");
        System.out.println("=================================================");

        while (!exit) {
            System.out.println("\n--- SELECT PORTAL ---");
            System.out.println("1. Admin / Management");
            System.out.println("2. Doctor Portal");
            System.out.println("3. Nurse Desk");
            System.out.println("4. Pharmacy Desk");
            System.out.println("5. Patient Portal");
            System.out.println("6. Save & Exit");
            System.out.print("Enter Choice: ");

            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    AdminMenu.display(scanner, hospitalService);
                    break;
                case "2":
                    DoctorMenu.display(scanner, hospitalService);
                    break;
                case "3":
                    NurseMenu.display(scanner, hospitalService);
                    break;
                case "4":
                    PharmacistMenu.display(scanner, hospitalService);
                    break;
                case "5":
                    PatientMenu.display(scanner, hospitalService);
                    break;
                case "6":
                    DataManager.saveData(hospitalService);
                    System.out.println(" Exiting system. Goodbye!");
                    exit = true;
                    break;
                default:
                    System.out.println(" Invalid selection. Please enter 1-6.");
            }
        }
        scanner.close();
    }
}