package com.badmintonManager.badmintonManager.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.badmintonManager.badmintonManager.utils.getCookies;
import jakarta.servlet.http.HttpServletRequest;


@Controller
@RequestMapping("/admin")
public class AdminController {

    @GetMapping
    public String showAdminPage(Model model, HttpServletRequest request) {  
        String username = getCookies.getCookieValue(request, "username");
        if (username == null) {
            return "redirect:/auth/login";
        }
        return "admin";
    }
}