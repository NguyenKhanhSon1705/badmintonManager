package com.badmintonManager.badmintonManager.models;

import java.sql.Date;
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
    private Date createdAt;

    @Column(name = "total_amount", nullable = false)
    private Double totalAmount;
    
    @Column(name = "court_id", nullable = false)
    private int courtId;
    
    @Column(name = "employeeid", nullable = false)
    private int employeeId;

    @OneToMany(mappedBy = "bill", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<BillDetailsModel> billDetails;
    
    private String employeeName;
    private String courtName;
	public Integer getBillId() {
		return billId;
	}
	public void setBillId(Integer billId) {
		this.billId = billId;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public Double getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(Double totalAmount) {
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
}
