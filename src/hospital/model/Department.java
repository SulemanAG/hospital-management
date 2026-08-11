package hospital.model;

import hospital.exception.InvalidInputException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static hospital.model.Person.validateNonEmpty;

public class Department implements Serializable {
    private static final long serialVersionUID = 1L;

    private String departmentName;
    private List<Doctor> doctors = new ArrayList<>();
    private List<Nurse> nurses = new ArrayList<>();

    public Department() {
    }

    public Department(String departmentName) throws InvalidInputException {
        validateNonEmpty("Department Name", departmentName);
        this.departmentName = departmentName;
        this.doctors = new ArrayList<>();
        this.nurses = new ArrayList<>();
    }

    public Department(String departmentName, List<Doctor> doctors, List<Nurse> nurses)
            throws InvalidInputException {
        validateNonEmpty("Department Name", departmentName);

        this.departmentName = departmentName;
        this.doctors = (doctors != null) ? doctors : new ArrayList<>();
        this.nurses = (nurses != null) ? nurses : new ArrayList<>();
    }

    public void addDoctors(Doctor doctor) throws InvalidInputException {
        if (doctor == null) {
            throw new InvalidInputException("Cannot add a null Doctor to department.");
        }
        if (this.doctors == null) {
            this.doctors = new ArrayList<>();
        }
        this.doctors.add(doctor);
    }

    public void addNurses(Nurse nurse) throws InvalidInputException {
        if (nurse == null) {
            throw new InvalidInputException("Cannot add a null Nurse to department.");
        }
        if (this.nurses == null) {
            this.nurses = new ArrayList<>();
        }
        this.nurses.add(nurse);
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public List<Doctor> getDoctors() {
        return doctors;
    }

    public void setDoctors(List<Doctor> doctors) {
        this.doctors = doctors;
    }

    public List<Nurse> getNurses() {
        return nurses;
    }

    public void setNurses(List<Nurse> nurses) {
        this.nurses = nurses;
    }
}