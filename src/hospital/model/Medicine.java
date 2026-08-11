package hospital.model;

import hospital.exception.InvalidInputException;
import hospital.exception.InsufficientStockException;

import java.io.Serializable;

import static hospital.model.Person.validateDouble;
import static hospital.model.Person.validateNonEmpty;

public class Medicine implements Serializable {

    private static final long serialVersionUID = 1L;

    private String medicineID;
    private String name;
    private double price;
    private int stockQuantity;

    public Medicine() {
    }

    public Medicine(String medicineID, String name, double price, int stockQuantity)
            throws InvalidInputException {
        validateNonEmpty("Medicine ID", medicineID);
        validateNonEmpty("Medicine Name", name);
        validateDouble(price);
        if (stockQuantity < 0) {
            throw new InvalidInputException("Initial stock quantity cannot be negative.");
        }

        this.medicineID = medicineID;
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }

    public void reduceStock(int quantity) throws InsufficientStockException, InvalidInputException {
        if (quantity <= 0) {
            throw new InvalidInputException("Quantity to reduce must be greater than zero.");
        }
        if (quantity > this.stockQuantity) {
            throw new InsufficientStockException("Insufficient stock for " + name + ". Available: " + stockQuantity + ", Requested: " + quantity);
        }
        this.stockQuantity -= quantity;
    }

    public void addStock(int quantity) throws InvalidInputException {
        if (quantity <= 0) {
            throw new InvalidInputException("Quantity to add must be greater than zero.");
        }
        this.stockQuantity += quantity;
    }

    // Getters and Setters
    public String getMedicineID() {
        return medicineID;
    }

    public void setMedicineID(String medicineID) {
        this.medicineID = medicineID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }
}