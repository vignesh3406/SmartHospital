
package entity;

import java.time.LocalDateTime;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class MedicalRecord {

    private int recordId;
    private Patient patient;
    private LocalDateTime createdAt;
    private List<MedicalEntry> treatmentNotes;

    // Inner class for individual medical entries
    public static class MedicalEntry {

        private String note;
        private LocalDateTime createdAt;

        public MedicalEntry(String note) {
            this.note = note;
            this.createdAt = LocalDateTime.now();
        }

        public String getNote() {
            return note;
        }

        public LocalDateTime getCreatedAt() {
            return createdAt;
        }
    }

    // Default constructor
    public MedicalRecord() {
        this.createdAt = LocalDateTime.now();
        this.treatmentNotes = new ArrayList<>();
    }

    // Parameterized constructor
    public MedicalRecord(Patient patient) {
        if (patient == null) {
            throw new IllegalArgumentException(
                    "Patient cannot be null"
            );
        }

        this.patient = patient;
        this.createdAt = LocalDateTime.now();
        this.treatmentNotes = new ArrayList<>();
    }

    // Getters
    public int getRecordId() {
        return recordId;
    }

    public Patient getPatient() {
        return patient;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public List<MedicalEntry> getTreatmentNotes() {
        return new ArrayList<>(treatmentNotes);
    }

    // Assign database-generated ID
    public void assignRecordId(int recordId) {
        if (recordId > 0) {
            this.recordId = recordId;
        } else {
            throw new IllegalArgumentException(
                    "Invalid Record ID"
            );
        }
    }

    // Add a new treatment entry
    public void addEntry(String note) {

        if (note == null || note.isBlank()) {
            throw new IllegalArgumentException(
                    "Treatment note cannot be empty"
            );
        }

        treatmentNotes.add(new MedicalEntry(note));
    }

    // Check whether an entry can be edited
    public boolean canEdit(MedicalEntry entry) {

        if (entry == null || !treatmentNotes.contains(entry)) {
            throw new IllegalArgumentException(
                    "Invalid medical entry"
            );
        }

        return Duration.between(
                entry.getCreatedAt(),
                LocalDateTime.now()
        ).toHours() < 24;
    }

    // Audit trail
    public void auditTrail() {

        System.out.println("Medical Record Audit Trail");

        for (MedicalEntry entry : treatmentNotes) {

            System.out.println(
                    "Date: " + entry.getCreatedAt()
            );

            System.out.println(
                    "Note: " + entry.getNote()
            );

            System.out.println("----------------------");
        }
    }

    // Append correction instead of modifying old note
    public void addCorrection(String correction) {

        if (correction == null || correction.isBlank()) {
            throw new IllegalArgumentException(
                    "Correction cannot be empty"
            );
        }

        addEntry("CORRECTION: " + correction);
    }
}
