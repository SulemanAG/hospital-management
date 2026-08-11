package hospital.interfaces;

import hospital.exception.InvalidInputException;

public interface Payable {

    /**
     * Retrieves the current unpaid balance.
     *
     * @return Current monetary balance.
     */
    double getBalance();

    /**
     * Processes a bill payment towards the balance.
     *
     * @param amount Monetary amount to pay.
     * @throws InvalidInputException If payment amount is negative, zero, or exceeds current balance.
     */
    void processPayment(double amount) throws InvalidInputException;

    /**
     * Returns the human-readable payment status.
     *
     * @return Payment status string (e.g., "PAID" or "PENDING: $X.XX").
     */
    String getPaymentStatus();
}