package com.badmintonManager.badmintonManager.models;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "bookings")  // Tên bảng trong cơ sở dữ liệu
public class BookingModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "court_details_id", nullable = false) // Khóa ngoại trỏ đến CourtDetailsModel
    private CourtDetailsModel courtDetails;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false) 
    private CustommerModel customer;

    @Column(name = "status", nullable = false)
    private boolean status;

    @Column(name = "created_at", nullable = false)
    
    private Date createdAt;

    // Constructors
    public BookingModel() {}

    public BookingModel(int id, CourtDetailsModel courtDetails, CustommerModel customer, boolean status, Date createdAt) {
        this.id = id;
        this.courtDetails = courtDetails;
        this.customer = customer;
        this.status = status;
        this.createdAt = createdAt;
    }

    // Getters và Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public CourtDetailsModel getCourtDetails() {
        return courtDetails;
    }

    public void setCourtDetails(CourtDetailsModel courtDetails) {
        this.courtDetails = courtDetails;
    }

    public CustommerModel getCustomer() {
        return customer;
    }

    public void setCustomer(CustommerModel customer) {
        this.customer = customer;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
