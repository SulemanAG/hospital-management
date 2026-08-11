package hospital.interfaces;

import hospital.model.Patient;
import hospital.exception.InvalidInputException;

public interface Diagnosable {

    /**
     * Diagnoses a patient and updates their medical status.
     *
     * @param patient   The patient being evaluated.
     * @param diagnosis The medical diagnosis string.
     * @throws InvalidInputException If patient is null or diagnosis is invalid.
     */
    void diagnosePatient(Patient patient, String diagnosis) throws InvalidInputException;
}