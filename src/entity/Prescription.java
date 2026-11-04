
package entity;

public class Prescription {

    private int prescriptionId;
    private Appointment appointment;
    private String medicationName;
    private String dosage;
    private int durationDays;
    private String instructions;

    // Parameterized constructor
    public Prescription(Appointment appointment,
                        String medicationName,
                        String dosage,
                        int durationDays,
                        String instructions) {

        setAppointment(appointment);
        setMedicationName(medicationName);
        setDosage(dosage);
        setDurationDays(durationDays);
        setInstructions(instructions);
    }

    // Getters
    public int getPrescriptionId() {
        return prescriptionId;
    }

    public Appointment getAppointment() {
        return appointment;
    }

    public String getMedicationName() {
        return medicationName;
    }

    public String getDosage() {
        return dosage;
    }

    public int getDurationDays() {
        return durationDays;
    }

    public String getInstructions() {
        return instructions;
    }

    // Setters
    public void setAppointment(Appointment appointment) {
        if (appointment != null) {
            this.appointment = appointment;
        } else {
            throw new IllegalArgumentException(
                    "Appointment cannot be null"
            );
        }
    }

    public void setMedicationName(String medicationName) {
        if (medicationName != null && !medicationName.isBlank()) {
            this.medicationName = medicationName;
        } else {
            throw new IllegalArgumentException(
                    "Medication name cannot be empty"
            );
        }
    }

    public void setDosage(String dosage) {
        if (dosage != null && !dosage.isBlank()) {
            this.dosage = dosage;
        } else {
            throw new IllegalArgumentException(
                    "Dosage cannot be empty"
            );
        }
    }

    public void setDurationDays(int durationDays) {
        if (durationDays > 0) {
            this.durationDays = durationDays;
        } else {
            throw new IllegalArgumentException(
                    "Duration must be greater than zero"
            );
        }
    }

    public void setInstructions(String instructions) {
        if (instructions != null && !instructions.isBlank()) {
            this.instructions = instructions;
        } else {
            throw new IllegalArgumentException(
                    "Instructions cannot be empty"
            );
        }
    }

    // Assign database-generated ID
    public void assignPrescriptionId(int prescriptionId) {
        if (prescriptionId > 0) {
            this.prescriptionId = prescriptionId;
        } else {
            throw new IllegalArgumentException(
                    "Invalid Prescription ID"
            );
        }
    }

    // Validate dosage
    public boolean validateDosage() {
        return dosage != null && !dosage.isBlank();
    }

    // Check medication interaction
    public boolean checkInteraction(Prescription active) {

        if (active == null) {
            return false;
        }

        // Basic duplicate medication check
        return this.medicationName.equalsIgnoreCase(
                active.getMedicationName()
        );
    }
}
