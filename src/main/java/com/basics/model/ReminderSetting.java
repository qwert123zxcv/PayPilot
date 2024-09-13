package com.basics.model;

import jakarta.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "reminder_settings")
public class ReminderSetting {

	 @Id
	    @Column(name = "reminder_id") 
	    private String reminderId=UUID.randomUUID().toString();

	    @Column(name = "reminder_frequency")
	    private String reminderFrequency;

	    @Column(name = "starting_date")
	    @Temporal(TemporalType.DATE)
	    private Date startingDate;

	    @Column(name = "custom_message")
	    private String customMessage;

	    @Column(name = "notification_preference")
	    private String notificationPreference;

	    @Column(name = "is_recurring")
	    private Boolean isRecurring;


    // Define many-to-one relationship with Bill
    @ManyToOne
    @JoinColumn(name = "bill_id")  // Define foreign key relation
    private Bill bill;  // This creates the foreign key relationship with the Bill entity

    // Getters and Setters
    public String getId() {
        return reminderId;
    }

    public void setId(String reminderId) {
        this.reminderId = reminderId;
    }

    public String getReminderFrequency() {
        return reminderFrequency;
    }

    public void setReminderFrequency(String reminderFrequency) {
        this.reminderFrequency = reminderFrequency;
    }

    public Date getStartingDate() {
        return startingDate;
    }

    public void setStartingDate(Date startingDate) {
        this.startingDate = startingDate;
    }

    public String getCustomMessage() {
        return customMessage;
    }

    public void setCustomMessage(String customMessage) {
        this.customMessage = customMessage;
    }

    public String getNotificationPreference() {
        return notificationPreference;
    }

    public void setNotificationPreference(String notificationPreference) {
        this.notificationPreference = notificationPreference;
    }

    public Boolean getIsRecurring() {
        return isRecurring;
    }

    public void setIsRecurring(Boolean isRecurring) {
        this.isRecurring = isRecurring;
    }

    // Getter and Setter for Bill
    public Bill getBill() {
        return bill;
    }

    public void setBill(Bill bill) {
        this.bill = bill;
    }
}
