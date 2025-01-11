package com.badmintonManager.badmintonManager.models;

import java.math.BigDecimal;

public class StatisticalDTO {
    private BigDecimal totalMoney;
    private BigDecimal totalMoneyPendding;
    private int totalPaymented;
    private int totalPeymentPendding;
    
    public StatisticalDTO(BigDecimal totalMoney, BigDecimal totalMoneyPendding, int totalPaymented,
            int totalPeymentPendding) {
        this.totalMoney = totalMoney;
        this.totalMoneyPendding = totalMoneyPendding;
        this.totalPaymented = totalPaymented;
        this.totalPeymentPendding = totalPeymentPendding;
    }
    public BigDecimal getTotalMoney() {
        return totalMoney;
    }
    public void setTotalMoney(BigDecimal totalMoney) {
        this.totalMoney = totalMoney;
    }
    public BigDecimal getTotalMoneyPendding() {
        return totalMoneyPendding;
    }
    public void setTotalMoneyPendding(BigDecimal totalMoneyPendding) {
        this.totalMoneyPendding = totalMoneyPendding;
    }
    public int getTotalPaymented() {
        return totalPaymented;
    }
    public void setTotalPaymented(int totalPaymented) {
        this.totalPaymented = totalPaymented;
    }
    public int getTotalPeymentPendding() {
        return totalPeymentPendding;
    }
    public void setTotalPeymentPendding(int totalPeymentPendding) {
        this.totalPeymentPendding = totalPeymentPendding;
    }
}
