package hospital.model;
import hospital.exception.InvalidInputException;

import javax.print.DocFlavor;
import javax.print.attribute.standard.NumberUp;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static hospital.model.Person.validateNonEmpty;

public class MedicalReport {
    private static final long serialVersionUID = 1L;

    private String reportID;
    private String patientID;
    private String vitals;
    private String diagnosis;
    List<Medicine> prescribedMedicines= new ArrayList<>();

    public MedicalReport(){

    }

    public MedicalReport(String reportID, String patientID,
                         String vitals,String diagnosis,
                         List<Medicine>prescribedMedicines)
        throws InvalidInputException{
        validateNonEmpty("Report ID",reportID);
        validateNonEmpty("patientID",patientID);
        validateNonEmpty("vitals",vitals);
        validateNonEmpty("diagnosis",diagnosis);

        this.reportID=reportID;
        this.patientID=patientID;
        this.vitals=vitals;
        this.diagnosis=diagnosis;
        this.prescribedMedicines=prescribedMedicines;
    }

    public  void addDiagnosis(String diagnosis)
            throws InvalidInputException{
        validateNonEmpty("Diagnosis",diagnosis);
        if(this.diagnosis==null||this.diagnosis.trim().isEmpty()){
            this.diagnosis=diagnosis;
        }
        else {
            this.diagnosis+=" | "+diagnosis;
        }
    }

    public void addPrescribedMedicine(Medicine medicine)
        throws InvalidInputException{
        if(medicine== null){
            throw new InvalidInputException("Medicine cannot be null");
        }
        if(this.prescribedMedicines==null){
            this.prescribedMedicines=new ArrayList<>();
        }
        this.prescribedMedicines.add(medicine);

    }

    public String getReportID() {
        return reportID;
    }

    public void setReportID(String reportID) {
        this.reportID = reportID;
    }

    public String getPatientID() {
        return patientID;
    }

    public void setPatientID(String patientID) {
        this.patientID = patientID;
    }

    public String getVitals() {
        return vitals;
    }

    public void setVitals(String vitals) {
        this.vitals = vitals;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public List<Medicine> getPrescribedMedicines() {
        return prescribedMedicines;
    }

    public void setPrescribedMedicines(List<Medicine> prescribedMedicines) {
        this.prescribedMedicines = prescribedMedicines;
    }
}
