package com.basics.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User {
	@Id
  @Column(name = "USERID")
  private String userId;

  @Column(name = "EMAIL", nullable = false, length = 100)
  private String email;

  @Column(name = "PANDETAILS", nullable = false, length = 10)
  private String panDetails;

  @Column(name = "BANKACCOUNTNUMBER", nullable = false, length = 18)
  private String bankAccountNumber;

  @Column(name = "IFSCCODE", nullable = false, length = 11)
  private String ifscCode;

  @Column(name = "BANKINGPARTNER", length = 100)
  private String bankingPartner;

  @Column(name = "PASSWORD", nullable = false, length = 255)
  private String password;

  //Constructors creation

  public User() {
  }

  public User(String email, String panDetails, String bankAccountNumber, 
              String ifscCode, String bankingPartner, String password) {
      this.email = email;
      this.panDetails = panDetails;
      this.bankAccountNumber = bankAccountNumber;
      this.ifscCode = ifscCode;
      this.bankingPartner = bankingPartner;
      this.password = password;
  }

  // Getters and Setters

  public String getUserId() {
      return userId;
  }

  public void setUserId(String userId) {
      this.userId = userId;
  }

  public String getEmail() {
      return email;
  }

  public void setEmail(String email) {
      this.email = email;
  }

  public String getPanDetails() {
      return panDetails;
  }

  public void setPanDetails(String panDetails) {
      this.panDetails = panDetails;
  }

  public String getBankAccountNumber() {
      return bankAccountNumber;
  }

  public void setBankAccountNumber(String bankAccountNumber) {
      this.bankAccountNumber = bankAccountNumber;
  }

  public String getIfscCode() {
      return ifscCode;
  }

  public void setIfscCode(String ifscCode) {
      this.ifscCode = ifscCode;
  }

  public String getBankingPartner() {
      return bankingPartner;
  }

  public void setBankingPartner(String bankingPartner) {
      this.bankingPartner = bankingPartner;
  }

  public String getPassword() {
      return password;
  }

  public void setPassword(String password) {
      this.password = password;
  }

  // toString method
  @Override
  public String toString() {
      return "User{" +
              "userId=" + userId +
              ", email='" + email + '\'' +
              ", panDetails='" + panDetails + '\'' +
              ", bankAccountNumber='" + bankAccountNumber + '\'' +
              ", ifscCode='" + ifscCode + '\'' +
              ", bankingPartner='" + bankingPartner + '\'' +
              ", password='" + password + '\'' +
              '}';
  }
}
