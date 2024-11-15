package com.badmintonManager.badmintonManager;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BaseController {
    private JdbcTemplate jdbcTemplate;
    public BaseController (JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @GetMapping("/")
    public String test(){
        try {
            // Query đơn giản để kiểm tra kết nối DB
            String sql = "SELECT 1";
            Integer result = jdbcTemplate.queryForObject(sql, Integer.class);
            return result != null ? "Kết nối thành công!" : "Lỗi kết nối!";
        } catch (Exception e) {
            return "Lỗi: " + e.getMessage();
        }
    }
}
