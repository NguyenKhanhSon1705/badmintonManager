package com.badmintonManager.badmintonManager.models;

import jakarta.persistence.*;

@Entity
@Table(name = "courts")
public class CourtsModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CourtId") // Ánh xạ chính xác với tên cột trong DB
    private int courtId;

    @Column(name = "CourtName", nullable = false, length = 100)
    private String courtName;

    @Column(name = "Status", nullable = false)
    private int status;

    // Getters and Setters
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
}
