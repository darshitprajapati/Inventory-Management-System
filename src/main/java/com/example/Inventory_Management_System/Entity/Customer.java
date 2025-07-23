package com.example.Inventory_Management_System.Entity;


import jakarta.persistence.*;

@Entity
@Table(name = "customer")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "ID")
    private int id;
//    @Column(name = "CustomerName")
    private String customerName;
//    @Column(name = "Mo_No")
    private int moNo;

    public Customer(int id, String customerName, int moNo) {
        this.id = id;
        this.customerName = customerName;
        this.moNo = moNo;
    }

    public Customer() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public int getMoNo() {
        return moNo;
    }

    public void setMoNo(int moNo) {
        this.moNo = moNo;
    }
}