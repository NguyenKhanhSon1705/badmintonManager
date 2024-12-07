package com.badmintonManager.badmintonManager.models;

import jakarta.persistence.*;

@Entity
@Table(name = "customers") // Tên bảng trong DB
public class CustommerModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id") // Ánh xạ cột "id"
    private int id;

    @Column(name = "name", nullable = false, length = 100) // Ánh xạ cột "name"
    private String name;

    @Column(name = "phone", nullable = false, length = 15) // Ánh xạ cột "phone"
    private String phone;

    // Constructors
    public CustommerModel() {
    }

    public CustommerModel(int id, String name, String phone) {
        this.id = id;
        this.name = name;
        this.phone = phone;
    }

    // Getters và Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
