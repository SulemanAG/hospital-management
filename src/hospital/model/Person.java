package hospital.model;
import hospital.exception.InvalidInputException;

import javax.management.relation.InvalidRoleInfoException;
import javax.print.DocFlavor;
import java.io.Serializable;

import static java.sql.Types.NULL;

public class Person implements Serializable{
    private static final long serialVersionUID=1L;


    protected String name;
    protected String phoneNumber;
    protected String address;

    public Person() {}

    public Person(String name, String  phoneNumber,
                   String address)
    {

        validateNonEmpty("Name",name);
        validatePhone(phoneNumber);
        validateNonEmpty("Address",address);
        this.name=name;
        this.phoneNumber=phoneNumber;
        this.address=address;
    }

    protected static void validateNonEmpty(String fieldName,String value)
                    throws InvalidInputException{
        if(value==null|| value.trim().isEmpty()){
            throw new InvalidInputException(fieldName+"cannot be empty");
        }
    }

    protected static void validatePhone(String phoneNumber)
            throws InvalidInputException{
        if(phoneNumber==null|| !String.valueOf(phoneNumber).matches("\\d{10}")){
            throw new InvalidInputException("Phone Number must be exactly 10 digits");
        }

    }

    protected static void validateDouble(double value) throws InvalidInputException {
        if (value < 0.0) {
            throw new InvalidInputException(value + " is Invalid");
        }
    }



    public String getRole(){
        return "Person";
    }
    public void displayDetails(){
        System.out.println("Name:"+getName());
        System.out.println("Phone number:"+getPhoneNumber());
        System.out.println("Address:"+getAddress());
    }
    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
    public String getPhoneNumber() {return phoneNumber;}
    public void setPhoneNumber(String phoneNumber) {this.phoneNumber = phoneNumber;}
    public String getAddress() {return address;}
    public void setAddress(String address) {this.address = address;}
}
