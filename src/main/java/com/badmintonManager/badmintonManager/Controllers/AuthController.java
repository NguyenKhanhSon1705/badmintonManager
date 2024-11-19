package com.badmintonManager.badmintonManager.Controllers;

import com.badmintonManager.badmintonManager.models.EmployeesModel;
import com.badmintonManager.badmintonManager.services.interfaces.IAuthService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final IAuthService authService;

    @Autowired
    public AuthController(IAuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/register")
    public String showRegisterForm() {
        return "register"; // Tên file HTML (register.html) trong thư mục templates
    }

    @PostMapping("/register")
    public String register(@ModelAttribute EmployeesModel user, Model model) {
        authService.register(user);
        model.addAttribute("message", "Registration successful!");
        return "login"; // Chuyển đến trang login
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login"; // Tên file HTML (login.html) trong thư mục templates
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, Model model) {
        try {
            EmployeesModel user = authService.login(username, password);
            if (user != null) {
                model.addAttribute("user", user);
                return "redirect:/";
            } else {
                model.addAttribute("error", "Tài khoản hoặc mật khẩu không đúng");
                return "login";
            }
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "login";
        }

    }
}
