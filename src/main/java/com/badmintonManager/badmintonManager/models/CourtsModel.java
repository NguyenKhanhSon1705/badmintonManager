package com.badmintonManager.badmintonManager.models;

import java.util.List;

import jakarta.persistence.*;
@Entity
@Table(name = "courts")
public class CourtsModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "court_id") // Tên cột ánh xạ chính xác với DB
    private Integer courtId;

    @Column(name = "court_name", nullable = false, length = 100)
    private String courtName;

    @Column(name = "status", nullable = false)
    private int status;

    @OneToMany(mappedBy = "court", cascade = CascadeType.ALL)
    private List<CourtDetailsModel> courtDetails;

    // Getters và Setters
    public int getCourtId() {
        return courtId;
    }

    public void setCourtId(int courtId) {
        this.courtId = courtId;
    }

    public String getCourtName() {
        return courtName;
    }

    public void setCourtName(String courtName) {
        this.courtName = courtName;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<CourtDetailsModel> getCourtDetails() {
        return courtDetails;
    }

    public void setCourtDetails(List<CourtDetailsModel> courtDetails) {
        this.courtDetails = courtDetails;
    }
}