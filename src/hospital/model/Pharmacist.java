package hospital.model;


import hospital.exception.InvalidInputException;

import java.io.Serializable;

public class Pharmacist extends Employee implements Serializable {

    private static final long serialVersionUID=1L;

    private String licenseNumber;

    public Pharmacist(){
        super();
    }

    public Pharmacist(String name,String phoneNumber,String address,
                      String employeeID,double basicSalary,String department,
                      String licenseNumber)
        throws InvalidInputException{
        super(name, phoneNumber, address, employeeID, basicSalary, department);

        validateNonEmpty("licenseNumber",licenseNumber);

        this.licenseNumber= licenseNumber;

    }

    @Override
    public String getRole(){
        return "Pharmacist";
    }

    @Override
    public void displayDetails(){
        System.out.println("Name:"+getName());
        System.out.println("Phone number:"+getPhoneNumber());
        System.out.println("Address"+getAddress());
        System.out.println("Employee ID"+getEmployeeID());
        System.out.println("Salary "+getBasicSalary());
        System.out.println("License Number"+getLicenseNumber());
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }
}
