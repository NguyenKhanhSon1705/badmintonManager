package com.badmintonManager.badmintonManager.Controllers;

import com.badmintonManager.badmintonManager.models.EmployeesModel;
import com.badmintonManager.badmintonManager.models.ResponseModel;
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
        user.setRole("user");
        ResponseModel result = authService.register(user);
        if(!result.getIsSuccess()){
            model.addAttribute("error", result.getMessage());
            return "register";
        }
        model.addAttribute("message", result.getMessage());
        return "login"; // Chuyển đến trang login
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login"; // Tên file HTML (login.html) trong thư mục templates
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, Model model) {
        // try {
            ResponseModel result = authService.login(username, password);
            if(!result.getIsSuccess()){
                model.addAttribute("error", result.getMessage());
                return "login";
            }
            model.addAttribute("user", result.getData());
            return "redirect:/";
    }
}
