package com.basics.model;

import jakarta.persistence.*;
import java.util.Date;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Represents a Scheduled Payment entity in the system.
 * Stores details about scheduled payments such as payee information and payment schedule.
 */
@Entity
@Table(name = "ScheduledPayments")
public class ScheduledPayment {

    @Id
    @Column(name = "scheduled_payment_id", nullable = false)
    private String scheduledPaymentId = UUID.randomUUID().toString(); // Unique scheduled payment ID generated using UUID

    @Column(name = "payee_name")
    private String payeeName; // Name of the payee

    @Column(name = "payee_address")
    private String payeeAddress; // Address of the payee

    @Column(name = "payee_bank_details")
    private String payeeBankDetails; // Bank details of the payee

    @Column(name = "payment_frequency")
    private String paymentFrequency; // Frequency of the scheduled payment (e.g., Weekly, Monthly)

    @Column(name = "scheduled_date")
    private Date scheduledDate; // Scheduled date of payment

    @Column(name = "amount")
    private double amount; // Amount to be paid in the scheduled payment

    @Column(name = "payment_mode")
    private String paymentMode; // Payment mode for the scheduled payment

    @Column(name = "payer_account_number")
    private String payerAccountNumber; // Payer's account number

    @ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.MERGE, CascadeType.REFRESH })
    @JoinColumn(name = "bill_id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) 
    private Bill bill; // Bill associated with the scheduled payment

    // Getters and Setters for ScheduledPayment class fields

    public String getScheduledPaymentId() {
        return scheduledPaymentId;
    }

    public void setScheduledPaymentId(String scheduledPaymentId) {
        this.scheduledPaymentId = scheduledPaymentId;
    }

    public String getPayeeName() {
        return payeeName;
    }

    public void setPayeeName(String payeeName) {
        this.payeeName = payeeName;
    }

    public String getPayeeAddress() {
        return payeeAddress;
    }

    public void setPayeeAddress(String payeeAddress) {
        this.payeeAddress = payeeAddress;
    }

    public String getPayeeBankDetails() {
        return payeeBankDetails;
    }

    public void setPayeeBankDetails(String payeeBankDetails) {
        this.payeeBankDetails = payeeBankDetails;
    }

    public String getPaymentFrequency() {
        return paymentFrequency;
    }

    public void setPaymentFrequency(String paymentFrequency) {
        this.paymentFrequency = paymentFrequency;
    }

    public Date getScheduledDate() {
        return scheduledDate;
    }

    public void setScheduledDate(Date scheduledDate) {
        this.scheduledDate = scheduledDate;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public String getPayerAccountNumber() {
        return payerAccountNumber;
    }

    public void setPayerAccountNumber(String payerAccountNumber) {
        this.payerAccountNumber = payerAccountNumber;
    }

    public Bill getBill() {
        return bill;
    }

    public void setBill(Bill bill) {
        this.bill = bill;
    }
}
