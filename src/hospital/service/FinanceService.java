package hospital.service;

import hospital.exception.InsufficientFundsException;
import hospital.exception.InvalidInputException;
import hospital.model.Employee;

import java.io.Serializable;
import java.util.List;

public class FinanceService implements Serializable {
    private static final long serialVersionUID = 1L;

    private double hospitalTreasury;

    public FinanceService() {
        this.hospitalTreasury = 0.0;
    }

    public FinanceService(double hospitalTreasury) {
        this.hospitalTreasury = hospitalTreasury;
    }

    public void depositRevenue(double amount) throws InvalidInputException {
        if (amount <= 0.0) {
            throw new InvalidInputException("Deposit amount must be greater than zero.");
        }
        this.hospitalTreasury += amount;
        System.out.println("Successfully deposited $" + amount + " into Treasury. Current Treasury: $" + hospitalTreasury);
    }

    public void disburseSalaries(List<Employee> employees)
            throws InvalidInputException, InsufficientFundsException {
        if (employees == null || employees.isEmpty()) {
            throw new InvalidInputException("Employee list cannot be empty for payroll disbursement.");
        }

        // 1. Calculate total payroll requirement
        double totalPayroll = 0.0;
        for (Employee emp : employees) {
            if (emp != null) {
                totalPayroll += emp.getBasicSalary();
            }
        }

        // 2. Check if Treasury has sufficient funds
        if (totalPayroll > this.hospitalTreasury) {
            throw new InsufficientFundsException("Insufficient treasury funds for payroll! Total Required: $"
                    + totalPayroll + ", Available Treasury: $" + hospitalTreasury);
        }

        // 3. Deduct total payroll from treasury
        this.hospitalTreasury -= totalPayroll;

        // 4. Log disbursement confirmation
        System.out.println("=== Payroll Disbursement Summary ===");
        for (Employee emp : employees) {
            if (emp != null) {
                System.out.println("Paid $" + emp.getBasicSalary() + " to " + emp.getRole() + " [" + emp.getName() + "]");
            }
        }
        System.out.println("Total Payroll Paid: $" + totalPayroll);
        System.out.println("Remaining Treasury Balance: $" + hospitalTreasury);
    }

    public double getHospitalTreasury() {
        return hospitalTreasury;
    }

    public void setHospitalTreasury(double hospitalTreasury) {
        this.hospitalTreasury = hospitalTreasury;
    }
}