
package entity;

import java.time.LocalDateTime;

public class Billing {

    private int billingId;
    private Appointment appointment;
    private double totalAmount;
    private LocalDateTime paymentDate;
    private PaymentStatus paymentStatus;
    private String paymentMethod;

    public enum PaymentStatus {
        PENDING,
        PAID,
        FAILED,
        REFUNDED
    }

    // Default constructor
    public Billing() {
        this.paymentStatus = PaymentStatus.PENDING;
    }

    // Parameterized constructor
    public Billing(Appointment appointment,
                   double totalAmount,
                   String paymentMethod) {

        setAppointment(appointment);
        setTotalAmount(totalAmount);
        setPaymentMethod(paymentMethod);

        this.paymentStatus = PaymentStatus.PENDING;
    }

    // Getters
    public int getBillingId() {
        return billingId;
    }

    public Appointment getAppointment() {
        return appointment;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public LocalDateTime getPaymentDate() {
        return paymentDate;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public String getPaymentMethod() {
        return paymentMethod;
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

    public void setTotalAmount(double totalAmount) {
        if (Double.isFinite(totalAmount) && totalAmount >= 0) {
            this.totalAmount = totalAmount;
        } else {
            throw new IllegalArgumentException(
                    "Invalid billing amount"
            );
        }
    }

    public void setPaymentMethod(String paymentMethod) {
        if (paymentMethod != null && !paymentMethod.isBlank()) {
            this.paymentMethod = paymentMethod;
        } else {
            throw new IllegalArgumentException(
                    "Payment method cannot be empty"
            );
        }
    }

    // Assign database-generated ID
    public void assignBillingId(int billingId) {
        if (billingId > 0) {
            this.billingId = billingId;
        } else {
            throw new IllegalArgumentException(
                    "Invalid Billing ID"
            );
        }
    }

    // Process payment
    public void processPayment() {

        if (paymentStatus != PaymentStatus.PENDING) {
            throw new IllegalStateException(
                    "Payment has already been processed"
            );
        }

        if (totalAmount <= 0) {
            throw new IllegalStateException(
                    "Billing amount must be greater than zero"
            );
        }

        paymentStatus = PaymentStatus.PAID;
        paymentDate = LocalDateTime.now();
    }

    // Check payment status
    public boolean isPaid() {
        return paymentStatus == PaymentStatus.PAID;
    }
}
