package com.badmintonManager.badmintonManager.Controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.badmintonManager.badmintonManager.models.ResponseModel;
import com.badmintonManager.badmintonManager.models.SuppliersModel;
import com.badmintonManager.badmintonManager.services.interfaces.ISuppliersService;
import com.badmintonManager.badmintonManager.utils.getCookies;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/suppliers")
public class SuppliersController {
    private final ISuppliersService service;

    public SuppliersController(ISuppliersService service) {
        this.service = service;
    }

    @GetMapping("/list")
    public String showRegisterForm(Model model, HttpServletRequest request) {
        String username = getCookies.getCookieValue(request, "username");
        if (username == null) {
            return "redirect:/auth/login";
        }
        
        ResponseModel result = service.getAllSuppliers();

        if (!result.getIsSuccess()) {
            model.addAttribute("error", result.getMessage());
        } else {
            @SuppressWarnings("unchecked")
            List<SuppliersModel> listSuppliers = (List<SuppliersModel>) result.getData();
            model.addAttribute("listSuppliers", listSuppliers);
        }
        
        return "Suppliers";
    }

    @GetMapping("/create")
    public String createService(HttpServletRequest request) {
        String username = getCookies.getCookieValue(request,"username");
        if (username == null) {
            return "redirect:/auth/login";
        }
        
        return "addSuppliers";
    }

    @PostMapping("/add")
    public String addService(SuppliersModel  sv, Model model) {
        try {
            
            service.createSupplier(sv);
            
            return "redirect:/suppliers/list"; // Sau khi thêm, chuyển hướng đến danh sách sân
        } catch (Exception e) {
            model.addAttribute("error", "Lỗi khi thêm sân: " + e.getMessage());
            return "addSuppliers"; // Nếu có lỗi, quay lại form thêm sân và hiển thị thông báo
        }
    }

    @GetMapping("/edit/{id}")
    public String editService(@PathVariable("id") int serviceId, Model model) {

        SuppliersModel sv = service.findById(serviceId);
        if (service == null) {
            model.addAttribute("error", "Nhà cung cấp không tồn tại");
            return "redirect:/suppliers/list";
        }

        // Thêm đối tượng court vào model để hiển thị trong view
        model.addAttribute("supplier", sv);
        return "editSuppliers"; 
    }

    @PostMapping("/edit/{id}")
    public String updateCourt(@PathVariable("id") int serviceId, @ModelAttribute SuppliersModel sv, Model model) {
        // Cập nhật sân cầu lông trong database
        sv.setSupplierId(serviceId);
        ResponseModel response = service.updateSupplier(sv);

        if (!response.getIsSuccess()) {
            model.addAttribute("error", response.getMessage());
            return "editSuppliers";
        }

        return "redirect:/suppliers/list"; // Sau khi sửa xong, chuyển hướng về danh sách
    }

    @PostMapping("/delete/{id}")
    public String deleteService(@PathVariable("id") Integer id, Model model) {
        ResponseModel result = service.deleteSupplier(id);
        if (!result.getIsSuccess()) {
            model.addAttribute("error", result.getMessage());
        }
        return "redirect:/suppliers/list";
    }
}
