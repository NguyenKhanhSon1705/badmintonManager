package com.badmintonManager.badmintonManager.models;


import jakarta.persistence.*;

@Entity
@Table(name = "menus")
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, unique = true)
    private String name; // Tên menu

    @Column(nullable = true)
    private String url; // Đường dẫn

    @Column(name = "icon", nullable = true)
    private String icon; // Biểu tượng của menu

    @Column(name = "sort_order", nullable = false)
    private int sortOrder; // Thứ tự sắp xếp

    @Column(name = "is_active", nullable = false)
    private boolean isActive = true; // Trạng thái kích hoạt

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Menu parentMenu; // Menu cha (nếu có)
}
