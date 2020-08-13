package com.cashregister.demo.customer;

import com.cashregister.demo.core.BaseEntity;
import com.cashregister.demo.transaction.Transaction;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class Customer extends BaseEntity {
    private String phoneNumber;
    private String lastName;
    @Column(unique = true)
    private String loyaltyNumber;

    protected Customer() { super(); }

    public Customer(String phoneNumber, String lastName, String loyaltyNumber) {
        this.phoneNumber = phoneNumber;
        this.lastName = lastName;
        this.loyaltyNumber = loyaltyNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLoyaltyNumber() {
        return loyaltyNumber;
    }

    public void setLoyaltyNumber(String loyaltyNumber) {
        this.loyaltyNumber = loyaltyNumber;
    }
}
