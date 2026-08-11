package hospital.ui;

import hospital.exception.UserNotFoundException;
import hospital.model.MedicalReport;
import hospital.model.Nurse;
import hospital.model.Patient;
import hospital.model.Person;
import hospital.service.HospitalService;

import java.util.Scanner;

public class NurseMenu {

    public static void display(Scanner scanner, HospitalService hospitalService) {
        System.out.print("Enter Nurse Employee ID: ");
        String empId = scanner.nextLine();

        Nurse nurse;
        try {
            Person p = hospitalService.findPersonById(empId);
            if (!(p instanceof Nurse)) {
                System.out.println(" ID exists but is not a Nurse.");
                return;
            }
            nurse = (Nurse) p;
        } catch (UserNotFoundException e) {
            System.out.println("Error" + e.getMessage());
            return;
        }

        boolean exit = false;
        while (!exit) {
            System.out.println("\n=== NURSE DASHBOARD (" + nurse.getName() + ") ===");
            System.out.println("1. Check-In New Patient");
            System.out.println("2. Record Vitals & Prepare Checkup Report");
            System.out.println("3. Back to Main Menu");
            System.out.print("Select an option: ");

            String choice = scanner.nextLine();
            try {
                switch (choice) {
                    case "1":
                        checkInPatient(scanner, hospitalService);
                        break;
                    case "2":
                        recordVitals(scanner, hospitalService, nurse);
                        break;
                    case "3":
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

    private static void checkInPatient(Scanner scanner, HospitalService service) throws Exception {
        System.out.print("Enter Patient Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Phone: ");
        String phone = scanner.nextLine();
        System.out.print("Enter Address: ");
        String address = scanner.nextLine();
        System.out.print("Enter Medical History Summary: ");
        String history = scanner.nextLine();
        System.out.print("Is Emergency Case? (true/false): ");
        boolean isEmergency = Boolean.parseBoolean(scanner.nextLine());

        Patient patient = new Patient(name, phone, address, history, "CHECKED_IN", isEmergency, 0.0, null, null);
        service.registerPerson(patient);
        service.processPatientCheckIn(patient);
    }

    private static void recordVitals(Scanner scanner, HospitalService service, Nurse nurse) throws Exception {
        System.out.print("Enter Patient Name: ");
        String pName = scanner.nextLine();
        Person p = service.findPersonById(pName);
        if (!(p instanceof Patient)) {
            System.out.println("Entity is not a Patient.");
            return;
        }
        Patient patient = (Patient) p;

        System.out.print("Enter Vitals (e.g., BP: 120/80, Pulse: 72bpm, Temp: 98.6F): ");
        String vitals = scanner.nextLine();

        MedicalReport report = nurse.prepareCheckupReport(patient, vitals);
        System.out.println("Report Created! Report ID: " + report.getReportID());
    }
}