
package entity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Clinic {

    private int clinicId;
    private String clinicName;
    private String address;
    private List<Doctor> doctors;


    // Parameterized constructor
    public Clinic(String clinicName, String address) {
        setClinicName(clinicName);
        setAddress(address);
        this.doctors = new ArrayList<>();
    }

    // Getters
    public int getClinicId() {
        return clinicId;
    }

    public String getClinicName() {
        return clinicName;
    }

    public String getAddress() {
        return address;
    }

    public List<Doctor> getDoctors() {
        return new ArrayList<>(doctors);
    }

    // Setters
    public void setClinicName(String clinicName) {
        if (clinicName != null && !clinicName.isBlank()) {
            this.clinicName = clinicName;
        } else {
            throw new IllegalArgumentException(
                    "Clinic name cannot be empty"
            );
        }
    }

    public void setAddress(String address) {
        if (address != null && !address.isBlank()) {
            this.address = address;
        } else {
            throw new IllegalArgumentException(
                    "Clinic address cannot be empty"
            );
        }
    }

    // Assign database-generated ID
    public void assignClinicId(int clinicId) {
        if (clinicId > 0) {
            this.clinicId = clinicId;
        } else {
            throw new IllegalArgumentException("Invalid Clinic ID");
        }
    }

    // Add doctor
    public void addDoctor(Doctor doctor) {

        if (doctor == null) {
            throw new IllegalArgumentException(
                    "Doctor cannot be null"
            );
        }

        if (doctors.contains(doctor)) {
            throw new IllegalArgumentException(
                    "Doctor already exists in this clinic"
            );
        }

        doctors.add(doctor);
    }

    // Find doctors by specialization
    public List<Doctor> findDoctorsBySpecialization(
            String specialization) {

        if (specialization == null || specialization.isBlank()) {
            throw new IllegalArgumentException(
                    "Specialization cannot be empty"
            );
        }

        return doctors.stream()
                .filter(doctor -> doctor.getSpecialization()
                        .equalsIgnoreCase(specialization))
                .collect(Collectors.toList());
    }

    // Check whether clinic can accept appointments
    public boolean canAcceptAppointments() {
        return !doctors.isEmpty();
    }
}
