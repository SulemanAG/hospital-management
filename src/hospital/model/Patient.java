package hospital.model;

import hospital.exception.InvalidInputException;
import hospital.interfaces.Payable;

import java.io.Serializable;

public class Patient extends Person implements Payable, Serializable {

    private static final long serialVersionUID = 1L;

    private String medicalHistory;
    private String status;
    private boolean isEmergency;
    private double billBalance;
    private Doctor assignedDoctor;
    private Nurse assignedNurse;

    public Patient() {
        super();
    }

    public Patient(String name, String phoneNumber, String address,
                   String medicalHistory, String status, boolean isEmergency,
                   double billBalance, Doctor assignedDoctor, Nurse assignedNurse)
            throws InvalidInputException {
        super(name, phoneNumber, address);
        validateNonEmpty("Medical History", medicalHistory);
        validateNonEmpty("status", status);
        validateDouble(billBalance);

        this.medicalHistory = medicalHistory;
        this.status = status;
        this.isEmergency = isEmergency;
        this.billBalance = billBalance;
        this.assignedDoctor = assignedDoctor;
        this.assignedNurse = assignedNurse;
    }

    @Override
    public String getRole() {
        return "Patient";
    }

    @Override
    public void displayDetails() {
        System.out.println("--- Patient Profile ---");
        System.out.println("Name: " + getName());
        System.out.println("Address: " + getAddress());
        System.out.println("Phone Number: " + getPhoneNumber());
        System.out.println("Status: " + getStatus());
        System.out.println("Emergency Case: " + (isEmergency ? "YES" : "NO"));
        System.out.println("Bill Balance: $" + billBalance);
        System.out.println("Assigned Doctor: " + (assignedDoctor != null ? "Dr. " + assignedDoctor.getName() : "Unassigned"));
        System.out.println("Assigned Nurse: " + (assignedNurse != null ? assignedNurse.getName() : "Unassigned"));
    }

    // Implemented Payable Interface Method
    @Override
    public double getBalance() {
        return billBalance;
    }

    // Implemented Payable Interface Method
    @Override
    public void processPayment(double amount) throws InvalidInputException {
        if (amount <= 0.0) {
            throw new InvalidInputException("Payment amount must be greater than zero.");
        }
        if (amount > billBalance) {
            throw new InvalidInputException("Payment amount ($" + amount + ") exceeds outstanding balance ($" + billBalance + ").");
        }

        this.billBalance -= amount;
        System.out.println("Payment of $" + amount + " processed successfully for " + getName());
    }

    // Implemented Payable Interface Method (Fixed spelling from getPayementStatus)
    @Override
    public String getPaymentStatus() {
        if (billBalance == 0.0) {
            return "PAID";
        } else {
            return "PENDING ($" + billBalance + ")";
        }
    }

    // Helper method to add new charges (pharmacy, admission fees)
    public void addBillAmount(double amount) throws InvalidInputException {
        if (amount < 0.0) {
            throw new InvalidInputException("Charge amount cannot be negative.");
        }
        this.billBalance += amount;
    }

    // Getters and Setters
    public String getMedicalHistory() {
        return medicalHistory;
    }

    public void setMedicalHistory(String medicalHistory) {
        this.medicalHistory = medicalHistory;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isEmergency() {
        return isEmergency;
    }

    public void setEmergency(boolean emergency) {
        isEmergency = emergency;
    }

    public double getBillBalance() {
        return billBalance;
    }

    public void setBillBalance(double billBalance) {
        this.billBalance = billBalance;
    }

    public Doctor getAssignedDoctor() {
        return assignedDoctor;
    }

    public void setAssignedDoctor(Doctor assignedDoctor) {
        this.assignedDoctor = assignedDoctor;
    }

    public Nurse getAssignedNurse() {
        return assignedNurse;
    }

    public void setAssignedNurse(Nurse assignedNurse) {
        this.assignedNurse = assignedNurse;
    }
}