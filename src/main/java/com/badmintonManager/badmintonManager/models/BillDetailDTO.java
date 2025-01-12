package com.badmintonManager.badmintonManager.models;

public class BillDetailDTO {
	private int serviceId;
    private int quantity;
    private Double unitPrice;
    
	
    // Getters và Setters
	public int getServiceId() {
		return serviceId;
	}
	public void setServiceId(int serviceId) {
		this.serviceId = serviceId;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public Double getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}
    
}
