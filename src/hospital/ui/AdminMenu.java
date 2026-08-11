package hospital.ui;

import hospital.exception.InvalidInputException;
import hospital.model.Department;
import hospital.model.Doctor;
import hospital.model.Employee;
import hospital.model.Nurse;
import hospital.model.Person;
import hospital.model.Pharmacist;
import hospital.service.HospitalService;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AdminMenu {

    public static void display(Scanner scanner, HospitalService hospitalService) {
        boolean exit = false;
        while (!exit) {
            System.out.println("\n=== ADMIN DASHBOARD ===");
            System.out.println("1. Register Doctor");
            System.out.println("2. Register Nurse");
            System.out.println("3. Register Pharmacist");
            System.out.println("4. Create Department");
            System.out.println("5. View All Registered Personnel");
            System.out.println("6. Deposit Funds to Treasury");
            System.out.println("7. Run Staff Payroll (Disburse Salaries)");
            System.out.println("8. Back to Main Menu");
            System.out.print("Select an option: ");

            String choice = scanner.nextLine();
            try {
                switch (choice) {
                    case "1":
                        registerDoctor(scanner, hospitalService);
                        break;
                    case "2":
                        registerNurse(scanner, hospitalService);
                        break;
                    case "3":
                        registerPharmacist(scanner, hospitalService);
                        break;
                    case "4":
                        createDepartment(scanner, hospitalService);
                        break;
                    case "5":
                        viewAllPersonnel(hospitalService);
                        break;
                    case "6":
                        depositTreasury(scanner, hospitalService);
                        break;
                    case "7":
                        runPayroll(hospitalService);
                        break;
                    case "8":
                        exit = true;
                        break;
                    default:
                        System.out.println("Invalid choice. Try again.");
                }
            } catch (Exception e) {
                System.out.println("⚠️ Error: " + e.getMessage());
            }
        }
    }

    private static void registerDoctor(Scanner scanner, HospitalService service) throws InvalidInputException {
        System.out.print("Enter Doctor Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Phone: ");
        String phone = scanner.nextLine();
        System.out.print("Enter Address: ");
        String address = scanner.nextLine();
        System.out.print("Enter Employee ID: ");
        String empId = scanner.nextLine();
        System.out.print("Enter Salary: ");
        double salary = Double.parseDouble(scanner.nextLine());
        System.out.print("Enter Department: ");
        String dept = scanner.nextLine();
        System.out.print("Enter Specialization: ");
        String spec = scanner.nextLine();
        System.out.print("Enter Doctor Level (Senior/Junior): ");
        String level = scanner.nextLine();

        Doctor doctor = new Doctor(name, phone, address, empId, salary, dept, spec, level, true);
        service.registerPerson(doctor);
    }

    private static void registerNurse(Scanner scanner, HospitalService service) throws InvalidInputException {
        System.out.print("Enter Nurse Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Phone: ");
        String phone = scanner.nextLine();
        System.out.print("Enter Address: ");
        String address = scanner.nextLine();
        System.out.print("Enter Employee ID: ");
        String empId = scanner.nextLine();
        System.out.print("Enter Salary: ");
        double salary = Double.parseDouble(scanner.nextLine());
        System.out.print("Enter Department: ");
        String dept = scanner.nextLine();
        System.out.print("Enter Ward: ");
        String ward = scanner.nextLine();
        System.out.print("Enter Shift (Day/Night): ");
        String shift = scanner.nextLine();

        Nurse nurse = new Nurse(name, phone, address, empId, salary, dept, ward, shift);
        service.registerPerson(nurse);
    }

    private static void registerPharmacist(Scanner scanner, HospitalService service) throws InvalidInputException {
        System.out.print("Enter Pharmacist Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Phone: ");
        String phone = scanner.nextLine();
        System.out.print("Enter Address: ");
        String address = scanner.nextLine();
        System.out.print("Enter Employee ID: ");
        String empId = scanner.nextLine();
        System.out.print("Enter Salary: ");
        double salary = Double.parseDouble(scanner.nextLine());
        System.out.print("Enter Department: ");
        String dept = scanner.nextLine();
        System.out.print("Enter License Number: ");
        String license = scanner.nextLine();

        Pharmacist pharmacist = new Pharmacist(name, phone, address, empId, salary, dept, license);
        service.registerPerson(pharmacist);
    }

    private static void createDepartment(Scanner scanner, HospitalService service) throws InvalidInputException {
        System.out.print("Enter Department Name: ");
        String name = scanner.nextLine();
        Department department = new Department(name);
        service.getDepartments().add(department);
        System.out.println("Department [" + name + "] created.");
    }

    private static void viewAllPersonnel(HospitalService service) {
        System.out.println("\n--- All Registered Persons ---");
        if (service.getPersons().isEmpty()) {
            System.out.println("No personnel registered yet.");
            return;
        }
        for (Person p : service.getPersons()) {
            p.displayDetails();
            System.out.println("-------------------------");
        }
    }

    private static void depositTreasury(Scanner scanner, HospitalService service) throws InvalidInputException {
        System.out.print("Enter Deposit Amount: $");
        double amount = Double.parseDouble(scanner.nextLine());
        service.getFinanceService().depositRevenue(amount);
    }

    private static void runPayroll(HospitalService service) throws Exception {
        List<Employee> employees = new ArrayList<>();
        for (Person p : service.getPersons()) {
            if (p instanceof Employee) {
                employees.add((Employee) p);
            }
        }
        service.getFinanceService().disburseSalaries(employees);
    }
}