package com.badmintonManager.badmintonManager.models;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "bill")
public class BillsModel {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bill_id") // Tên cột ánh xạ chính xác với DB
    private Integer billId;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "total_amount", nullable = false, precision = 10, scale = 3)
    private BigDecimal totalAmount;
    
    @Column(name = "court_id", nullable = false)
    private int courtId;
    
    @Column(name = "employeeid", nullable = false)
    private int employeeId;
    
    @Column(name = "code", nullable = true, length = 8)
    private String code;

    @OneToMany(mappedBy = "bill", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<BillDetailsModel> billDetails;
    
    private String employeeName;
    private String courtName;
    
    @Column(name = "checkin", nullable = true, length = 6)
    private Time checkin;
    
    @Column(name = "checkout", nullable = true, length = 6)
    private Time checkout;
    
    @Column(name = "status", nullable = true)
    private int status = 0;
    
	public Integer getBillId() {
		return billId;
	}
	public void setBillId(Integer billId) {
		this.billId = billId;
	}
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	public BigDecimal getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}
	public int getCourtId() {
		return courtId;
	}
	public void setCourtId(int courtId) {
		this.courtId = courtId;
	}
	public int getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}
	public List<BillDetailsModel> getBillDetails() {
		return billDetails;
	}
	public void setBillDetails(List<BillDetailsModel> billDetails) {
		this.billDetails = billDetails;
	}
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	public String getCourtName() {
		return courtName;
	}
	public void setCourtName(String courtName) {
		this.courtName = courtName;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Time getCheckin() {
		return checkin;
	}

	public void setCheckin(Time checkin) {
		this.checkin = checkin;
	}

	public Time getCheckout() {
		return checkout;
	}

	public void setCheckout(Time checkout) {
		this.checkout = checkout;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
}
