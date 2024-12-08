package com.badmintonManager.badmintonManager.models;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name = "Suppliers")
public class SuppliersModel {

    @Id
    @Column(name = "SupplierId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int supplierId;

    @Column(name = "SupplierName", nullable = false, length = 100)
    private String supplierName;

    @Column(name = "Address", length = 255)
    private String address;

    @Column(name = "Phone", length = 15, unique = true)
    private String phone;

    @Column(name = "Email", length = 100, unique = true)
    private String email;


    // Constructors
    public SuppliersModel() {
    }

    public SuppliersModel(String supplierName, String address, String phone, String email) {
        this.supplierName = supplierName;
        this.address = address;
        this.phone = phone;
        this.email = email;
    }

    // Getters and Setters
    public int getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(int supplierId) {
        this.supplierId = supplierId;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    
}
