package com.badmintonManager.badmintonManager.models;
import java.math.BigDecimal;

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

    @Column(nullable = false, name = "Price", precision = 10, scale = 3)
    private BigDecimal price;

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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
    
}
