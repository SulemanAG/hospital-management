package hospital.ui;

import hospital.exception.UserNotFoundException;
import hospital.model.Doctor;
import hospital.model.MedicalReport;
import hospital.model.Medicine;
import hospital.model.Patient;
import hospital.model.Person;
import hospital.service.HospitalService;

import java.util.Scanner;

public class DoctorMenu {

    public static void display(Scanner scanner, HospitalService hospitalService) {
        System.out.print("Enter Doctor Employee ID: ");
        String empId = scanner.nextLine();

        Doctor doctor;
        try {
            Person p = hospitalService.findPersonById(empId);
            if (!(p instanceof Doctor)) {
                System.out.println("ID exists but is not a Doctor.");
                return;
            }
            doctor = (Doctor) p;
        } catch (UserNotFoundException e) {
            System.out.println("Error " + e.getMessage());
            return;
        }

        boolean exit = false;
        while (!exit) {
            System.out.println("\n=== ️ DOCTOR DASHBOARD (Dr. " + doctor.getName() + ") ===");
            System.out.println("1. Diagnose Patient");
            System.out.println("2. Prescribe Medicine to Patient Report");
            System.out.println("3. Back to Main Menu");
            System.out.print("Select an option: ");

            String choice = scanner.nextLine();
            try {
                switch (choice) {
                    case "1":
                        diagnosePatient(scanner, hospitalService, doctor);
                        break;
                    case "2":
                        prescribeMedicine(scanner, hospitalService);
                        break;
                    case "3":
                        exit = true;
                        break;
                    default:
                        System.out.println(" Invalid choice.");
                }
            } catch (Exception e) {
                System.out.println("⚠️ Error: " + e.getMessage());
            }
        }
    }

    private static void diagnosePatient(Scanner scanner, HospitalService service, Doctor doctor) throws Exception {
        System.out.print("Enter Patient Name: ");
        String pName = scanner.nextLine();
        Person p = service.findPersonById(pName);
        if (!(p instanceof Patient)) {
            System.out.println(" Entity is not a Patient.");
            return;
        }
        Patient patient = (Patient) p;

        System.out.print("Enter Diagnosis Details: ");
        String diagnosis = scanner.nextLine();
        doctor.diagnosePatient(patient, diagnosis);
    }

    private static void prescribeMedicine(Scanner scanner, HospitalService service) throws Exception {
        System.out.print("Enter Medicine ID from Inventory: ");
        String medId = scanner.nextLine();
        Medicine medicine = service.getPharmacyService().findMedicineByID(medId);

        System.out.print("Enter Target Report ID: ");
        String reportId = scanner.nextLine();

        // Standard mock attachment to demonstration object
        MedicalReport report = new MedicalReport(reportId, "P-MOCK", "Vitals Normal", "Evaluation Pending", null);
        report.addPrescribedMedicine(medicine);
        System.out.println(" Prescribed " + medicine.getName() + " to Report " + reportId);
    }
}