package com.badmintonManager.badmintonManager.models;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity(name = "services")
public class ServicesModel {
    @Id
    @Column(name="Service_Id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Tự động tăng giống như IDENTITY trong SQL Server
    private Integer serviceId;

    @Column(nullable = false, length = 100, name = "Service_Name")
    private String serviceName;

    @Column(nullable = false, name = "Price")
    private Double price;

    // Getters and Setters

    public Integer getServiceId() {
        return serviceId;
    }

    public void setServiceId(Integer serviceId) {
        this.serviceId = serviceId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
    
}
