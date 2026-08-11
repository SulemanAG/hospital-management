package hospital.ui;

import hospital.exception.UserNotFoundException;
import hospital.model.Patient;
import hospital.model.Person;
import hospital.service.HospitalService;

import java.util.Scanner;

public class PatientMenu {

    public static void display(Scanner scanner, HospitalService hospitalService) {
        System.out.print("Enter Your Name: ");
        String name = scanner.nextLine();

        Patient patient;
        try {
            Person p = hospitalService.findPersonById(name);
            if (!(p instanceof Patient)) {
                System.out.println(" Record found is not a Patient.");
                return;
            }
            patient = (Patient) p;
        } catch (UserNotFoundException e) {
            System.out.println("Error " + e.getMessage());
            return;
        }

        boolean exit = false;
        while (!exit) {
            System.out.println("\n===  PATIENT PORTAL (" + patient.getName() + ") ===");
            System.out.println("1. View Profile & Medical Status");
            System.out.println("2. View Bill Balance & Payment Status");
            System.out.println("3. Process Bill Payment");
            System.out.println("4. Back to Main Menu");
            System.out.print("Select an option: ");

            String choice = scanner.nextLine();
            try {
                switch (choice) {
                    case "1":
                        patient.displayDetails();
                        break;
                    case "2":
                        System.out.println("Current Balance: $" + patient.getBalance());
                        System.out.println("Payment Status: " + patient.getPaymentStatus());
                        break;
                    case "3":
                        processPayment(scanner, hospitalService, patient);
                        break;
                    case "4":
                        exit = true;
                        break;
                    default:
                        System.out.println("Invalid choice.");
                }
            } catch (Exception e) {
                System.out.println("⚠️ Error: " + e.getMessage());
            }
        }
    }

    private static void processPayment(Scanner scanner, HospitalService service, Patient patient) throws Exception {
        System.out.print("Enter Payment Amount: $");
        double amount = Double.parseDouble(scanner.nextLine());

        patient.processPayment(amount);
        service.getFinanceService().depositRevenue(amount);
    }
}