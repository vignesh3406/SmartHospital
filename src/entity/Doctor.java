
package entity;

import java.time.LocalDateTime;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Doctor extends Person {

    private int doctorId;
    private String specialization;
    private double consultationFee;
    private List<Appointment> schedule;


    // Parameterized constructor
    public Doctor(String firstName, String lastName, String phone,
                  String specialization, double consultationFee) {

        super(firstName, lastName, phone);
        setSpecialization(specialization);
        setConsultationFee(consultationFee);
        this.schedule = new ArrayList<>();
    }

    // Getters
    public int getDoctorId() {
        return doctorId;
    }

    public String getSpecialization() {
        return specialization;
    }

    public double getConsultationFee() {
        return consultationFee;
    }

    public List<Appointment> getSchedule() {
        return new ArrayList<>(schedule);
    }

    // Setters
    public void setSpecialization(String specialization) {
        if (specialization != null && !specialization.isBlank()) {
            this.specialization = specialization;
        } else {
            throw new IllegalArgumentException(
                    "Specialization cannot be empty"
            );
        }
    }

    public void setConsultationFee(double consultationFee) {
        if (Double.isFinite(consultationFee) && consultationFee >= 0) {
            this.consultationFee = consultationFee;
        } else {
            throw new IllegalArgumentException(
                    "Consultation fee cannot be negative or invalid"
            );
        }
    }

    // Assign database-generated ID
    public void assignDoctorId(int doctorId) {
        if (doctorId > 0) {
            this.doctorId = doctorId;
        } else {
            throw new IllegalArgumentException("Invalid Doctor ID");
        }
    }

    // Check doctor availability
    public boolean isAvailable(LocalDateTime slot) {

        if (slot == null || slot.isBefore(LocalDateTime.now())) {
            return false;
        }

        for (Appointment appointment : schedule) {
            if (appointment.getAppointmentDateTime().equals(slot)) {
                return false;
            }
        }

        return true;
    }

    // Add appointment to schedule
    public void addToSchedule(Appointment appointment) {

        if (appointment == null) {
            throw new IllegalArgumentException(
                    "Appointment cannot be null"
            );
        }

        if (!isAvailable(appointment.getAppointmentDateTime())) {
            throw new IllegalStateException(
                    "Doctor is not available at this time"
            );
        }

        schedule.add(appointment);
    }

    // Calculate weekly workload
    public int calculateWeeklyLoad() {

        LocalDateTime now = LocalDateTime.now();

        WeekFields weekFields = WeekFields.of(Locale.getDefault());

        int currentWeek = now.get(weekFields.weekOfWeekBasedYear());
        int currentYear = now.get(weekFields.weekBasedYear());

        int count = 0;

        for (Appointment appointment : schedule) {

            LocalDateTime appointmentDate =
                    appointment.getAppointmentDateTime();

            if (appointmentDate.get(weekFields.weekOfWeekBasedYear())
                    == currentWeek &&
                    appointmentDate.get(weekFields.weekBasedYear())
                            == currentYear) {

                count++;
            }
        }

        return count;
    }
}
