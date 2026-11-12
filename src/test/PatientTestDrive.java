
package test;

import entity.Patient;
import java.time.LocalDate;

public class PatientTestDrive {

    public static void main(String[] args) {

        Patient patient = new Patient(
                "Vignesh",
                "K",
                "9876543210",
                LocalDate.of(2003, 5, 15),
                "O+",
                "vignesh@gmail.com"
        );

        System.out.println("Patient Name: " +
                patient.getFullName());

        System.out.println("Patient ID: " +
                patient.getPersonId());

        System.out.println("Patient Age: " +
                patient.getAge());

        System.out.println("Blood Group: " +
                patient.getBloodGroup());

        patient.registerPatient();
    }
}
