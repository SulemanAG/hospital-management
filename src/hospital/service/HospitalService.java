package hospital.service;

import hospital.exception.InvalidInputException;
import hospital.exception.UserNotFoundException;
import hospital.model.Department;
import hospital.model.Doctor;
import hospital.model.Employee;
import hospital.model.Nurse;
import hospital.model.Patient;
import hospital.model.Person;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class HospitalService implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<Person> persons;
    private List<Department> departments;
    private PharmacyService pharmacyService;
    private FinanceService financeService;

    public HospitalService() {
        this.persons = new ArrayList<>();
        this.departments = new ArrayList<>();
        this.pharmacyService = new PharmacyService();
        this.financeService = new FinanceService();
    }

    public HospitalService(List<Person> persons, List<Department> departments,
                           PharmacyService pharmacyService, FinanceService financeService) {
        this.persons = (persons != null) ? persons : new ArrayList<>();
        this.departments = (departments != null) ? departments : new ArrayList<>();
        this.pharmacyService = (pharmacyService != null) ? pharmacyService : new PharmacyService();
        this.financeService = (financeService != null) ? financeService : new FinanceService();
    }

    public void registerPerson(Person person) throws InvalidInputException {
        if (person == null) {
            throw new InvalidInputException("Cannot register a null person.");
        }
        persons.add(person);
        System.out.println("Successfully registered " + person.getRole() + ": " + person.getName());
    }

    public Person findPersonById(String id) throws UserNotFoundException {
        if (id == null || id.trim().isEmpty()) {
            throw new UserNotFoundException("Invalid ID provided for search.");
        }

        for (Person p : persons) {
            if (p instanceof Employee) {
                Employee emp = (Employee) p;
                if (emp.getEmployeeID().equalsIgnoreCase(id)) {
                    return p;
                }
            } else if (p instanceof Patient) {
                // If checking by name or ID
                if (p.getName().equalsIgnoreCase(id)) {
                    return p;
                }
            }
        }

        throw new UserNotFoundException("Person with ID/Name [" + id + "] not found.");
    }

    /**
     * Triage routing engine: Assigns care team based on emergency state.
     */
    public void processPatientCheckIn(Patient patient) throws InvalidInputException {
        if (patient == null) {
            throw new InvalidInputException("Patient cannot be null during check-in.");
        }

        Doctor assignedDoctor = null;
        Nurse assignedNurse = null;

        // Search for matching care team based on triage priority
        for (Person p : persons) {
            if (p instanceof Doctor && assignedDoctor == null) {
                Doctor doc = (Doctor) p;
                if (patient.isEmergency() && "Senior".equalsIgnoreCase(doc.getDoctorLevel()) && doc.isAvailable()) {
                    assignedDoctor = doc;
                } else if (!patient.isEmergency() && doc.isAvailable()) {
                    assignedDoctor = doc;
                }
            }
            if (p instanceof Nurse && assignedNurse == null) {
                assignedNurse = (Nurse) p;
            }
        }

        if (assignedDoctor != null) {
            patient.setAssignedDoctor(assignedDoctor);
            assignedDoctor.setAvailable(false); // Mark doctor as busy
        }
        if (assignedNurse != null) {
            patient.setAssignedNurse(assignedNurse);
        }

        patient.setStatus("CHECKED_IN");
        System.out.println("Patient [" + patient.getName() + "] checked in successfully.");
        if (patient.isEmergency()) {
            System.out.println("🚨 EMERGENCY BYPASS: Priority doctor assigned -> "
                    + (assignedDoctor != null ? assignedDoctor.getName() : "Pending Urgent Assignment"));
        }
    }

    // Getters and Setters
    public List<Person> getPersons() {
        return persons;
    }

    public void setPersons(List<Person> persons) {
        this.persons = persons;
    }

    public List<Department> getDepartments() {
        return departments;
    }

    public void setDepartments(List<Department> departments) {
        this.departments = departments;
    }

    public PharmacyService getPharmacyService() {
        return pharmacyService;
    }

    public void setPharmacyService(PharmacyService pharmacyService) {
        this.pharmacyService = pharmacyService;
    }

    public FinanceService getFinanceService() {
        return financeService;
    }

    public void setFinanceService(FinanceService financeService) {
        this.financeService = financeService;
    }
}