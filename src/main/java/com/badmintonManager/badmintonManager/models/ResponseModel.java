package com.badmintonManager.badmintonManager.models;
public class ResponseModel {
    private String message;
    private Object data; // Để trả dữ liệu bất kỳ
    private int status;  // Mã trạng thái HTTP
    private boolean isSuccess;

    // Constructor
    public ResponseModel(String message, Object data, int status, boolean isSuccess) {
        this.message = message;
        this.data = data;
        this.status = status;
        this.isSuccess = isSuccess;
    }
    public boolean getIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(boolean IsSuccess) {
        this.isSuccess = IsSuccess;
    }
    // Getters và Setters
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
