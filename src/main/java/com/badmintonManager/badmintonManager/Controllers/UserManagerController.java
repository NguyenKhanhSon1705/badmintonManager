package com.badmintonManager.badmintonManager.Controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.badmintonManager.badmintonManager.models.EmployeesModel;
import com.badmintonManager.badmintonManager.models.ResponseModel;
import com.badmintonManager.badmintonManager.services.interfaces.IUserManager;
import com.badmintonManager.badmintonManager.utils.getCookies;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/manager")
public class UserManagerController {
    private final IUserManager service;
    
    public UserManagerController(IUserManager service){
        this.service = service;
    }

    @GetMapping("/listuser")
    public String showRegisterForm(Model model, HttpServletRequest request) {
        String username = getCookies.getCookieValue(request,"username");
        if(username == null){
            return "redirect:/auth/login";
        }
        ResponseModel result = service.getAllUser();

        if (!result.getIsSuccess()) {
            model.addAttribute("error", result.getMessage());
        } else {
            @SuppressWarnings("unchecked")
            List<EmployeesModel> listuser = (List<EmployeesModel>) result.getData();
            model.addAttribute("listuser", listuser);
        }
        return "userManager";
    } 

    @GetMapping("/edit/{id}")
    public String editUserForm(@PathVariable("id") Integer id, Model model) {
        EmployeesModel user = service.findById(id);
       if (user == null) {
            model.addAttribute("error", "Người dùng không tồn tại!");
            return "redirect:/manager/listuser";
        }
        model.addAttribute("user", user);
        return "editUser";
    }

     @PostMapping("/edit")
    public String updateUser(@ModelAttribute("user") EmployeesModel user, Model model) {
        
        ResponseModel result = service.updateUser(user);
        if (!result.getIsSuccess()) {
            model.addAttribute("error", result.getMessage());
            return "editUser";
        }
        return "redirect:/manager/listuser";
    }

    @PostMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id, Model model) {
        ResponseModel result = service.deleteUser(id);
        if (!result.getIsSuccess()) {
            model.addAttribute("error", result.getMessage());
        }
        return "redirect:/manager/listuser";
    }
}
