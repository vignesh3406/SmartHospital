
package entity;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

public class Patient extends Person {

    private int patientId;
    private LocalDate dateOfBirth;
    private String bloodGroup;
    private String email;
    private List<Appointment> visitHistory;

    public Patient(String firstName, String lastName, String phone,
                   LocalDate dateOfBirth, String bloodGroup, String email) {

        super(firstName, lastName, phone);
        setDateOfBirth(dateOfBirth);
        setBloodGroup(bloodGroup);
        setEmail(email);
        this.visitHistory = new ArrayList<>();
    }

    // Getters
    public int getPatientId() {
        return patientId;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public String getEmail() {
        return email;
    }

    public List<Appointment> getVisitHistory() {
        return new ArrayList<>(visitHistory);
    }

    // Setters
    public void setDateOfBirth(LocalDate dateOfBirth) {
        if (dateOfBirth != null && !dateOfBirth.isAfter(LocalDate.now())) {
            this.dateOfBirth = dateOfBirth;
        } else {
            throw new IllegalArgumentException("Invalid date of birth");
        }
    }

    public void setBloodGroup(String bloodGroup) {
        if (bloodGroup != null &&
                bloodGroup.matches("(?i)(A|B|AB|O)[+-]")) {
            this.bloodGroup = bloodGroup.toUpperCase();
        } else {
            throw new IllegalArgumentException("Invalid blood group");
        }
    }

    public void setEmail(String email) {
        if (email != null &&
                email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            this.email = email;
        } else {
            throw new IllegalArgumentException("Invalid email address");
        }
    }

    // Business methods
    public void registerPatient() {
        System.out.println("Patient registered: " + getFullName());
    }

    public int getAge() {
        return Period.between(dateOfBirth, LocalDate.now()).getYears();
    }

    public void addVisit(Appointment appointment) {
        if (appointment == null) {
            throw new IllegalArgumentException("Appointment cannot be null");
        }

        visitHistory.add(appointment);
    }

    // Assign database-generated ID
    public void assignPatientId(int patientId) {
        if (patientId > 0) {
            this.patientId = patientId;
        } else {
            throw new IllegalArgumentException("Invalid Patient ID");
        }
    }
}
