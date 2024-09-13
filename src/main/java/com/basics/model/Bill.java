/*
 * Author: Hemalata L Chavan
 * Date: 03-Sept-2024
 * Description: The Bill class represents the details of a bill in the Bill Management System. It includes attributes
 * such as bill name, category, due date, amount, reminder frequency, and payment status. This class also defines
 * operations for adding, updating, and deleting bills.
 */

/*
 * Bill Category: ALL, DEBT_PAYMENTS, HOUSE_RENT, GROCERIES, INTERNET_CHARGES, RETIREMENT_CHARGES, CELL_PHONE_CHARGES
 * Bill Frequency: DAILY, WEEKLY, BIWEEKLY, MONTHLY, BIMONTHLY, QUARTERLY, SEMIANNUAL, ANNUAL; 
 * Bill Status: UPCOMING, PENDING, PAID
 */

package com.basics.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "bills1") 
public class Bill {

	@Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "bill_id", nullable = false, updatable = false)
	private String billId = UUID.randomUUID().toString();// Primary key, auto-generated

	@Column(name = "bill_name", nullable = false)
    private String billName;  // Name of the bill
	
	 @Column(name = "bill_category", nullable = false)
	 private String billCategory; // Category of the bill

	 @Temporal(TemporalType.DATE)
	 @Column(name = "due_date", nullable = false)
	 private Date dueDate;  // Due date for the bill payment

	 @Column(name = "amount", nullable = false)
	 private Float amount; // Amount to be paid
	 
	 @Column(name = "reminder_frequency")
	 private String reminderFrequency; // Frequency of reminders
    
	 @Column(name = "attachment")
	 private String attachment; // Any attachments related to the bill
    
	 @Column(name = "notes")
	 private String notes;  // Additional notes about the bill
    
	 @Column(name = "is_recurring")
	 private Boolean isRecurring = false;// Indicates if the bill is recurring
   
	 @Column(name = "payment_status")
	 private String paymentStatus; // Status of the bill payment (UPCOMING, PENDING, PAID)
    
	 @Column(name = "overdue_days", nullable = false)
	 private Integer overdueDays = 0; // Number of days the payment is overdue
	 
	 // Establish relationship with the User entity
	 @ManyToOne
	 @JoinColumn(name = "user_id")
	 private User user; // User who created/owns the bill
	    
	 // One Bill can have many Payments
	 @OneToMany(mappedBy = "bill", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	 private List<Payment> payments; // List of payments associated with the bill

	 // One Bill can have many Scheduled Payments
	 @OneToMany(mappedBy = "bill", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	 private List<ScheduledPayment> scheduledPayments; // List of scheduled payments for the bill

	 // One Bill can have many Reminder Settings
	 @OneToMany(mappedBy = "bill", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	 private List<ReminderSetting> reminderSettings; // List of reminder settings for the bill

    // Constructors
    public Bill() {
        // Default constructor
    }

    // Getters and Setters
    public String getId() {
        return billId;
    }

    public void setId(String id) {
        this.billId = billId;
    }

    public String getBillName() {
        return billName;
    }

    public void setBillName(String billName) {
        this.billName = billName;
    }

    public String getBillCategory() {
        return billCategory;
    }

    public void setBillCategory(String billCategory) {
        this.billCategory = billCategory;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public String getReminderFrequency() {
        return reminderFrequency;
    }

    public void setReminderFrequency(String reminderFrequency) {
        this.reminderFrequency = reminderFrequency;
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public boolean isRecurring() {
        return isRecurring;
    }

    public void setRecurring(boolean isRecurring) {
        this.isRecurring = isRecurring;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public int getOverdueDays() {
        return overdueDays;
    }

    public void setOverdueDays(int overdueDays) {
        this.overdueDays = overdueDays;
    }
    
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Payment> getPayments() {
        return payments;
    }

    public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }

    public List<ScheduledPayment> getScheduledPayments() {
        return scheduledPayments;
    }

    public void setScheduledPayments(List<ScheduledPayment> scheduledPayments) {
        this.scheduledPayments = scheduledPayments;
    }

    public List<ReminderSetting> getReminderSettings() {
        return reminderSettings;
    }

    public void setReminderSettings(List<ReminderSetting> reminderSettings) {
        this.reminderSettings = reminderSettings;
    }

//    @Override
//    public String toString() {
//        return "Bill ID: " + id + "\n" +
//               "Bill Name: " + billName + "\n" +
//               "Category: " + billCategory + "\n" +
//               "Due Date: " + dueDate + "\n" +
//               "Amount: " + amount + "\n" +
//               "Reminder Frequency: " + reminderFrequency + "\n" +
//               "Is Recurring: " + isRecurring + "\n" +
//               "Payment Status: " + paymentStatus + "\n" +
//               "Overdue Days: " + overdueDays + "\n" +
//               "-------------";
//    }

   
}
