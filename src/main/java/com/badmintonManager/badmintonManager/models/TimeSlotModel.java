package com.badmintonManager.badmintonManager.models;

import jakarta.persistence.*;

import java.sql.Date;
import java.sql.Time;

@Entity
@Table(name = "time_slots")
public class TimeSlotModel {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "slot_id")
    private int slotId;

    @Column(name = "end_time", nullable = false)
    private Time endTime;

    @Column(name = "slot_date", nullable = false)
    private Date slotDate;

    @Column(name = "start_time", nullable = false)
    private Time startTime;

    @Column(name = "status", nullable = false, length = 50)
    private String status;

    @ManyToOne
    @JoinColumn(name = "court_id", nullable = false)
    private CourtsModel court;

    // Getters and Setters
    public int getSlotId() {
        return slotId;
    }

    public void setSlotId(Integer slotId) {
        this.slotId = slotId;
    }

    public Time getEndTime() {
        return endTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    public Date getSlotDate() {
        return slotDate;
    }

    public void setSlotDate(Date slotDate) {
        this.slotDate = slotDate;
    }

    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public CourtsModel getCourt() {
        return court;
    }

    public void setCourt(CourtsModel court) {
        this.court = court;
    }

    public TimeSlotModel(int slotId, Time endTime, Date slotDate, Time startTime, String status,
            CourtsModel court) {
        this.slotId = slotId;
        this.endTime = endTime;
        this.slotDate = slotDate;
        this.startTime = startTime;
        this.status = status;
        this.court = court;
    }

    public TimeSlotModel() {
    }
}
