package com.badmintonManager.badmintonManager.models;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "bill_detail")
public class BillDetailsModel {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "bill_id", nullable = false, referencedColumnName = "bill_id")
    @JsonManagedReference
    private BillsModel bill;
    
    @ManyToOne
    @JoinColumn(name = "Service_Id", nullable = true)
    @JsonManagedReference
    private ServicesModel service;

    @Column(name = "quantity", nullable = true)
    private int quantity;

    @Column(name = "unit_price", nullable = true, precision = 10, scale = 3)
    private BigDecimal unitprice;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public BillsModel getBill() {
		return bill;
	}

	public void setBill(BillsModel bill) {
		this.bill = bill;
	}

	public ServicesModel getService() {
		return service;
	}
	
	public void setService(ServicesModel service) {
		this.service = service;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getUnitprice() {
		return unitprice;
	}

	public void setUnitprice(BigDecimal unitprice) {
		this.unitprice = unitprice;
	}
}
