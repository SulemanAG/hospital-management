package hospital.model;

import hospital.exception.InvalidInputException;
import hospital.interfaces.MedicalPreparer;

import java.io.Serializable;
import java.util.UUID;

import static hospital.model.Person.validateNonEmpty;

public class Nurse extends Employee implements MedicalPreparer, Serializable {
    private static final long serialVersionUID = 1L;

    private String assignedWard;
    private String shiftTime;

    public Nurse() {
        super();
    }

    public Nurse(String assignedWard, String shiftTime) {
        super();
        this.assignedWard = assignedWard;
        this.shiftTime = shiftTime;
    }

    public Nurse(String name, String phoneNumber, String address,
                 String employeeID, double basicSalary, String department,
                 String assignedWard, String shiftTime)
            throws InvalidInputException {
        super(name, phoneNumber, address, employeeID, basicSalary, department);
        validateNonEmpty("Assigned Ward", assignedWard);
        validateNonEmpty("Shift Time", shiftTime);
        this.assignedWard = assignedWard;
        this.shiftTime = shiftTime;
    }

    @Override
    public String getRole() {
        return "Nurse";
    }

    @Override
    public void displayDetails() {
        System.out.println("--- Nurse Profile ---");
        System.out.println("Name: " + getName());
        System.out.println("Phone Number: " + getPhoneNumber());
        System.out.println("Address: " + getAddress());
        System.out.println("Employee ID: " + getEmployeeID());
        System.out.println("Department: " + getDepartment());
        System.out.println("Assigned Ward: " + getAssignedWard());
        System.out.println("Shift Timing: " + getShiftTime());
    }

    @Override
    public MedicalReport prepareCheckupReport(Patient patient, String vitals) throws InvalidInputException {
        if (patient == null) {
            throw new InvalidInputException("Patient cannot be null when preparing checkup report.");
        }
        validateNonEmpty("Vitals", vitals);

        // 1. Generate a unique report ID
        String reportID = "REP-" + UUID.randomUUID().toString().substring(0, 8);

        // 2. Instantiate a new MedicalReport object
        MedicalReport report = new MedicalReport(reportID, patient.getName(), vitals, "Pending Doctor Evaluation", null);

        // 3. Update patient's operational status
        patient.setStatus("UNDER_CHECKUP");
        patient.setAssignedNurse(this);

        System.out.println("Nurse " + getName() + " recorded vitals for Patient [" + patient.getName() + "]: " + vitals);

        return report;
    }

    public String getAssignedWard() {
        return assignedWard;
    }

    public void setAssignedWard(String assignedWard) {
        this.assignedWard = assignedWard;
    }

    public String getShiftTime() {
        return shiftTime;
    }

    public void setShiftTime(String shiftTime) {
        this.shiftTime = shiftTime;
    }
}