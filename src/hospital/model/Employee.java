package hospital.model;
import hospital.exception.InvalidInputException;
import java.io.Serializable;

public abstract class Employee extends Person {

    private static final long serialVersionUID=1L;

    protected String employeeID;
    protected double basicSalary;
    protected String department;

    public Employee(){
        super();
    }

    public Employee(String name,String phoneNumber,String address,
                    String employeeID,double basicSalary,String department)
        throws InvalidInputException{
        super(name,phoneNumber,address);
        validateNonEmpty("ID",employeeID);
        validateNonEmpty("department",department);
        validateDouble(basicSalary);

        this.employeeID=employeeID;
        this.basicSalary=basicSalary;
        this.department=department;
    }

    public String getEmployeeID() {return employeeID;}

    public void setEmployeeID(String employeeID) {this.employeeID = employeeID;}

    public double getBasicSalary() {return basicSalary;}

    public void setBasicSalary(double basicSalary) {this.basicSalary = basicSalary;}

    public String getDepartment() {return department;}

    public void setDepartment(String department) {this.department = department;}


}
