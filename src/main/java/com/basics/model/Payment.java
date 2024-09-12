package com.basics.model;

import jakarta.persistence.*;
import java.util.Date;
import java.util.UUID;

/**
 * Represents a Payment entity in the system.
 * Stores details about payments such as the payment date, mode, and amount paid.
 */
@Entity
@Table(name = "payments")
public class Payment {

    @Id
    @Column(name = "payment_id", nullable = false)
    private String paymentId= UUID.randomUUID().toString(); // Unique payment ID

    @Column(name = "payment_date")
    private Date paymentDate; // Date of the payment

    @Column(name = "payment_mode")
    private String paymentMode; // Payment mode (e.g., Credit, Debit, etc.)

    @Column(name = "payer_account_number")
    private String payerAccountNumber; // Account number of the payer

    @Column(name = "amount_paid")
    private Float amountPaid; // Amount paid in this transaction

    @Column(name = "status")
    private String status; // Status of the payment (e.g., Successful, Pending)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bill_id", nullable = false)
    private Bill bill; // The bill this payment is associated with

    // Getters and Setters for Payment class fields
    
    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
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

    public Float getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(Float amountPaid) {
        this.amountPaid = amountPaid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Bill getBill() {
        return bill;
    }

    public void setBill(Bill bill) {
        this.bill = bill;
    }
}


