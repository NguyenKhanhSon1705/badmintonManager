package com.badmintonManager.badmintonManager.models;

import java.sql.Time;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
@Entity
@Table(name = "court_details")
public class CourtDetailsModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "court_id", nullable = false)
    private CourtsModel court; // Sửa tên biến từ `court_id` thành `court`

    @Column(nullable = true, name = "timer")
    private Time timer;

    @Column(nullable = true, name = "status")
    private int status;

    // Constructors
    public CourtDetailsModel() {}

    public CourtDetailsModel(int id, CourtsModel court, Time timer, int status) {
        this.id = id;
        this.court = court;
        this.timer = timer;
        this.status = status;
    }

    // Getters và Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public CourtsModel getCourt() {
        return court;
    }

    public void setCourt(CourtsModel court) {
        this.court = court;
    }

    public Time getTimer() {
        return timer;
    }

    public void setTimer(Time timer) {
        this.timer = timer;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
