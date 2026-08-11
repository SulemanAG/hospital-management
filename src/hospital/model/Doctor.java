package hospital.model;

import hospital.interfaces.Diagnosable;
import hospital.exception.InvalidInputException;

import java.io.Serializable;

public class Doctor extends Employee implements Diagnosable, Serializable {

    private static final long serialVersionUID = 1L;

    private String specialization;
    private String doctorLevel;
    private boolean isAvailable;

    // Fixed default constructor syntax
    public Doctor() {
        super();
    }

    public Doctor(String name, String phoneNumber, String address,
                  String employeeID, double basicSalary,
                  String department, String specialization,
                  String doctorLevel, boolean isAvailable)
            throws InvalidInputException {
        super(name, phoneNumber, address, employeeID, basicSalary, department);
        validateNonEmpty("specialization", specialization);
        validateNonEmpty("Doctor Level", doctorLevel);

        this.specialization = specialization;
        this.doctorLevel = doctorLevel;
        this.isAvailable = isAvailable;
    }

    @Override
    public void displayDetails() {
        System.out.println("--- Doctor Profile ---");
        System.out.println("Doctor Name: " + getName());
        System.out.println("Specialization: " + getSpecialization());
        System.out.println("Level: " + getDoctorLevel()); // Fixed duplicate call
        System.out.println("Department: " + getDepartment());
        System.out.println("Address: " + getAddress());
        System.out.println("Phone Number: " + getPhoneNumber());
        System.out.println("Employee ID: " + getEmployeeID());
        System.out.println("Availability: " + (isAvailable ? "Available" : "Busy"));
    }

    @Override
    public String getRole() {
        return "Doctor";
    }

    // Implemented diagnosePatient method
    @Override
    public void diagnosePatient(Patient patient, String diagnosis) throws InvalidInputException {
        if (patient == null) {
            throw new InvalidInputException("Patient cannot be null for diagnosis.");
        }
        validateNonEmpty("diagnosis", diagnosis);

        // 1. Log the diagnosis action
        System.out.println("Dr. " + getName() + " diagnosed Patient [" + patient.getName() + "] with: " + diagnosis);

        // 2. Update patient's status to reflect ongoing treatment
        patient.setStatus("IN_TREATMENT");

        // 3. Attach diagnosis to patient's medical history or current report
        String updatedHistory = (patient.getMedicalHistory() == null || patient.getMedicalHistory().isEmpty())
                ? "Diagnosis: " + diagnosis + " (by Dr. " + getName() + ")"
                : patient.getMedicalHistory() + " | Diagnosis: " + diagnosis + " (by Dr. " + getName() + ")";
        patient.setMedicalHistory(updatedHistory);
    }

    public String getSpecialization() { return specialization; }

    public void setSpecialization(String specialization) { this.specialization = specialization; }

    public String getDoctorLevel() { return doctorLevel; }

    public void setDoctorLevel(String doctorLevel) { this.doctorLevel = doctorLevel; }

    public boolean isAvailable() { return isAvailable; }

    public void setAvailable(boolean available) { isAvailable = available; }
}