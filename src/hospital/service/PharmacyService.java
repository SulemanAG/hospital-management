package hospital.service;

import hospital.exception.InvalidInputException;
import hospital.exception.InsufficientStockException;
import hospital.exception.UserNotFoundException;
import hospital.model.MedicalReport;
import hospital.model.Medicine;
import hospital.model.Patient;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PharmacyService implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<Medicine> inventory;

    public PharmacyService() {
        this.inventory = new ArrayList<>();
    }

    public PharmacyService(List<Medicine> inventory) {
        if (inventory != null) {
            this.inventory = inventory;
        } else {
            this.inventory = new ArrayList<>();
        }
    }

    public void addMedicine(Medicine medicine) throws InvalidInputException {
        if (medicine == null) {
            throw new InvalidInputException("Cannot add a null medicine to inventory.");
        }
        inventory.add(medicine);
        System.out.println("Medicine [" + medicine.getName() + "] added to inventory.");
    }

    public Medicine findMedicineByID(String id) throws UserNotFoundException {
        if (id == null || id.trim().isEmpty()) {
            throw new UserNotFoundException("Invalid Medicine ID requested.");
        }

        for (Medicine medicine : inventory) {
            if (medicine.getMedicineID().equalsIgnoreCase(id)) {
                return medicine;
            }
        }

        throw new UserNotFoundException("Medicine with ID [" + id + "] not found in inventory.");
    }

    /**
     * Fulfills a prescription from a MedicalReport, reduces stock, and charges the patient.
     */
    public double dispensePrescription(MedicalReport report, Patient patient)
            throws UserNotFoundException, InsufficientStockException, InvalidInputException {
        if (report == null || patient == null) {
            throw new InvalidInputException("Report and Patient cannot be null during dispensing.");
        }

        List<Medicine> prescribed = report.getPrescribedMedicines();
        if (prescribed == null || prescribed.isEmpty()) {
            System.out.println("No medicines prescribed in report [" + report.getReportID() + "].");
            return 0.0;
        }

        double totalCost = 0.0;

        for (Medicine item : prescribed) {
            Medicine stockItem = findMedicineByID(item.getMedicineID());
            stockItem.reduceStock(1); // Reduce stock by 1 unit
            totalCost += stockItem.getPrice();
        }

        patient.addBillAmount(totalCost);
        patient.setStatus("FULFILLED");
        System.out.println("Prescription fulfilled for " + patient.getName() + ". Total charged: $" + totalCost);

        return totalCost;
    }

    public List<Medicine> getInventory() {
        return inventory;
    }

    public void setInventory(List<Medicine> inventory) {
        this.inventory = inventory;
    }
}