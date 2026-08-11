package hospital.util;

import hospital.service.HospitalService;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class DataManager {

    private static final String FILE_PATH = "hospital_data.ser";

    /**
     * Saves the HospitalService state to disk via serialization.
     */
    public static void saveData(HospitalService hospitalService) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            oos.writeObject(hospitalService);
            System.out.println(" Hospital system data saved successfully to " + FILE_PATH);
        } catch (Exception e) {
            System.err.println("Error saving system data: " + e.getMessage());
        }
    }

    /**
     * Loads the HospitalService state from disk or returns a fresh instance if no file exists.
     */
    public static HospitalService loadData() {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            System.out.println("ℹNo previous database found. Initializing new Hospital Service state...");
            return new HospitalService();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_PATH))) {
            HospitalService service = (HospitalService) ois.readObject();
            System.out.println("Hospital system data restored successfully from " + FILE_PATH);
            return service;
        } catch (Exception e) {
            System.err.println("⚠️ Could not load data from file (" + e.getMessage() + "). Starting fresh...");
            return new HospitalService();
        }
    }
}