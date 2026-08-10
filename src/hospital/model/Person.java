package hospital.model;
import javax.management.relation.InvalidRoleInfoException;
import javax.print.DocFlavor;
import java.io.Serializable;

import static java.sql.Types.NULL;

abstract class Person implements Serializable{
    private static final long serialVersionUID=1L;

    protected Integer id;
    protected String name;
    protected Integer phoneNumber;
    protected String address;

    public Person() {}

    public Person(Integer id, String name, Integer phoneNumber,
                   String address)
    {

        validateNonEmpty("Name",name);
        validateInt(id);
        validatePhone(phoneNumber);
        validateNonempty("Address",address);
        this.id=id;
        this.name=name;
        this.phoneNumber=phoneNumber;
        this.address=address;
    }

    protected static void validateNonEmpty(String fieldName,String value)
                    throws InavlidInputException{
        if(value==null|| value.trim().isEmpty()){
            throw new InvalidInputException(fieldName+"cannot be empty");
        }
    }

    protected static void validatePhone(Integer phoneNumber)
            throws InvalidInputException{
        if(phoneNumber==NULL|| !phoneNumber.matches("\\d{10}")){
            throw new InvalidInputException("Phone Number must be exactly 10 digits");
        }

    }
    protected static void validateInt(Integer id)throws
            InvalidInputExceptoon
    {
        if(id==NULL|| !id.matches("-?\\d+(\\.\\d+)?")){
            throw new InvalidInputException("ID cannot be non-number");
        }
    }

    public Integer getId() {return id;}
    public void setId(Integer id) {this.id = id;}
    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
    public Integer getPhoneNumber() {return phoneNumber;}
    public void setPhoneNumber(Integer phoneNumber) {this.phoneNumber = phoneNumber;}
    public String getAddress() {return address;}
    public void setAddress(String address) {this.address = address;}
}
