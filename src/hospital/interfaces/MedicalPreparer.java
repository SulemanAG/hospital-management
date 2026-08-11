package hospital.interfaces;

import hospital.model.Patient;
import hospital.model.MedicalReport;
import hospital.exception.InvalidInputException;

public interface MedicalPreparer {

    /**
     * Prepares a checkup report for a patient containing preliminary vitals.
     *
     * @param patient The patient being evaluated.
     * @param vitals  The recorded vital signs string.
     * @return A newly constructed MedicalReport instance.
     * @throws InvalidInputException If patient is null or vitals string is invalid.
     */
    MedicalReport prepareCheckupReport(Patient patient, String vitals) throws InvalidInputException;
}