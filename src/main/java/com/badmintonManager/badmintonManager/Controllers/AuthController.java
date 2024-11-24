package com.badmintonManager.badmintonManager.Controllers;

import com.badmintonManager.badmintonManager.models.EmployeesModel;
import com.badmintonManager.badmintonManager.models.ResponseModel;
import com.badmintonManager.badmintonManager.services.interfaces.IAuthService;
import com.badmintonManager.badmintonManager.services.interfaces.IUserManager;
import com.badmintonManager.badmintonManager.utils.getCookies;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final IAuthService authService;
    private final IUserManager managerService;
    // @Autowired
    public AuthController(IAuthService authService, IUserManager managerService) {
        this.authService = authService;
        this.managerService = managerService;
    }

    @GetMapping("/register")
    public String showRegisterForm() {
        return "register"; // Tên file HTML (register.html) trong thư mục templates
    }

    @PostMapping("/register")
    public String register(@ModelAttribute EmployeesModel user, Model model) {
        user.setRole("user");
        ResponseModel result = authService.register(user);
        if (!result.getIsSuccess()) {
            model.addAttribute("error", result.getMessage());
            return "register";
        }
        model.addAttribute("message", result.getMessage());
        return "login"; // Chuyển đến trang login
    }

    @GetMapping("/login")
    public String showLoginForm( HttpServletRequest request) {
        String checkLogin = getCookies.getCookieValue(request, "username");

        if (checkLogin != null) {
            return "redirect:/manager/listuser";
        }
        return "login"; // Tên file HTML (login.html) trong thư mục templates
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, HttpServletResponse response,
            @RequestParam String password,
            Model model) {

        
        ResponseModel result = authService.login(username, password);
        if (!result.getIsSuccess()) {
            model.addAttribute("error", result.getMessage());
            return "login";
        }
        
        Cookie cookie = new Cookie("username", username);
        cookie.setPath("/");
        cookie.setMaxAge(24 * 60 * 60); // 1 ngày
        response.addCookie(cookie);
        
        EmployeesModel employees = managerService.findByUsername(username);

        Cookie role = new Cookie("roles", employees.getRole());
        role.setPath("/");
        role.setMaxAge(24 * 60 * 60); // 1 ngày
        response.addCookie(role);
        System.err.println("eeeee");

        System.err.println((String)employees.getRole() == "ADMIN");

        // model.addAttribute("user", result.getData());
        if(employees.getRole().equals("ADMIN")){
            return "redirect:/manager/listuser";
        }else{
            return "redirect:/";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpServletResponse response) {
        Cookie cookie = new Cookie("username", null); // Đặt giá trị null
        cookie.setPath("/");
        cookie.setMaxAge(0); // Hết hạn ngay lập tức
        response.addCookie(cookie);

        return "redirect:/login";
    }
}
