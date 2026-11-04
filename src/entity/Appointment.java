
package entity;

import java.time.LocalDateTime;

public class Appointment {

    private int appointmentId;
    private Patient patient;
    private Doctor doctor;
    private LocalDateTime appointmentDateTime;
    private AppointmentStatus status;

    // Appointment status
    public enum AppointmentStatus {
        SCHEDULED,
        CONFIRMED,
        CANCELLED,
        COMPLETED
    }

    // Parameterized constructor
    public Appointment(Patient patient, Doctor doctor,
                       LocalDateTime appointmentDateTime) {

        setPatient(patient);
        setDoctor(doctor);
        setAppointmentDateTime(appointmentDateTime);

        this.status = AppointmentStatus.SCHEDULED;
    }

    // Getters
    public int getAppointmentId() {
        return appointmentId;
    }

    public Patient getPatient() {
        return patient;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public LocalDateTime getAppointmentDateTime() {
        return appointmentDateTime;
    }

    public AppointmentStatus getStatus() {
        return status;
    }

    // Setters
    public void setPatient(Patient patient) {
        if (patient != null) {
            this.patient = patient;
        } else {
            throw new IllegalArgumentException(
                    "Patient cannot be null"
            );
        }
    }

    public void setDoctor(Doctor doctor) {
        if (doctor != null) {
            this.doctor = doctor;
        } else {
            throw new IllegalArgumentException(
                    "Doctor cannot be null"
            );
        }
    }

    public void setAppointmentDateTime(LocalDateTime dateTime) {
        if (dateTime != null && dateTime.isAfter(LocalDateTime.now())) {
            this.appointmentDateTime = dateTime;
        } else {
            throw new IllegalArgumentException(
                    "Appointment must be scheduled in the future"
            );
        }
    }

    // Assign database-generated ID
    public void assignAppointmentId(int appointmentId) {
        if (appointmentId > 0) {
            this.appointmentId = appointmentId;
        } else {
            throw new IllegalArgumentException(
                    "Invalid Appointment ID"
            );
        }
    }

    // Confirm appointment
    public void confirm() {

        if (status != AppointmentStatus.SCHEDULED) {
            throw new IllegalStateException(
                    "Only scheduled appointments can be confirmed"
            );
        }

        if (!doctor.isAvailable(appointmentDateTime)) {
            throw new IllegalStateException(
                    "Doctor is not available"
            );
        }

        doctor.addToSchedule(this);
        patient.addVisit(this);

        status = AppointmentStatus.CONFIRMED;
    }

    // Cancel appointment
    public void cancel() {

        if (status == AppointmentStatus.COMPLETED) {
            throw new IllegalStateException(
                    "Completed appointment cannot be cancelled"
            );
        }

        if (status == AppointmentStatus.CANCELLED) {
            throw new IllegalStateException(
                    "Appointment is already cancelled"
            );
        }

        status = AppointmentStatus.CANCELLED;
    }

    // Reschedule appointment
    public void reschedule(LocalDateTime newSlot) {

        if (status != AppointmentStatus.SCHEDULED &&
                status != AppointmentStatus.CONFIRMED) {

            throw new IllegalStateException(
                    "Appointment cannot be rescheduled"
            );
        }

        if (newSlot == null || !newSlot.isAfter(LocalDateTime.now())) {
            throw new IllegalArgumentException(
                    "New appointment time must be in the future"
            );
        }

        if (!doctor.isAvailable(newSlot)) {
            throw new IllegalStateException(
                    "Doctor is not available at the new time"
            );
        }

        this.appointmentDateTime = newSlot;
    }
}
