package com.badmintonManager.badmintonManager;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BaseController {
    @GetMapping("/")
    public String sayHello(Model model) {
        // Thêm thông tin vào model để truyền cho view
        model.addAttribute("message", "Chào mừng bạn đến với Badminton Manager!");
        return "index";  // Trả về view 'index.html'
    }
    
}
