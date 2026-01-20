package com.hotwax.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private Integer customerId;

    @NotBlank(message = "First name is required")
    @Column(name = "first_name", length = 50, nullable = false)
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Column(name = "last_name", length = 50, nullable = false)
    private String lastName;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<ContactMech> contactMechanisms = new ArrayList<>();

    @OneToMany(mappedBy = "customer")
    @com.fasterxml.jackson.annotation.JsonIgnore
    private List<OrderHeader> orders = new ArrayList<>();

    public Customer() {
    }

    public Customer(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<ContactMech> getContactMechanisms() {
        return contactMechanisms;
    }

    public void setContactMechanisms(List<ContactMech> contactMechanisms) {
        this.contactMechanisms = contactMechanisms;
    }

    public List<OrderHeader> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderHeader> orders) {
        this.orders = orders;
    }
}