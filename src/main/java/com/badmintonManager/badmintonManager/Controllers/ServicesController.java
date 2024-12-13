package com.badmintonManager.badmintonManager.Controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.badmintonManager.badmintonManager.models.ServicesModel;
import com.badmintonManager.badmintonManager.models.ResponseModel;
import com.badmintonManager.badmintonManager.services.interfaces.IServiceService;
import com.badmintonManager.badmintonManager.utils.getCookies;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/services")
public class ServicesController {
    private final IServiceService service;

    public ServicesController(IServiceService service) {
        this.service = service;
    }

    @GetMapping("/list")
    public String showRegisterForm(Model model, HttpServletRequest request) {
        String username = getCookies.getCookieValue(request, "username");
        if (username == null) {
            return "redirect:/auth/login";
        }
        
        ResponseModel result = service.getAllServices();

        if (!result.getIsSuccess()) {
            model.addAttribute("error", result.getMessage());
        } else {
            @SuppressWarnings("unchecked")
            List<ServicesModel> listServices = (List<ServicesModel>) result.getData();
            model.addAttribute("services", listServices);
        }
        
        return "Services";
    }

    @GetMapping("/create")
    public String createService(HttpServletRequest request) {
        String username = getCookies.getCookieValue(request,"username");
        if (username == null) {
            return "redirect:/auth/login";
        }
        
        return "addServices";
    }

    @PostMapping("/add")
    public String addService(ServicesModel  sv, Model model) {
        try {
            
            service.createServices(sv);
            
            return "redirect:/services/list"; // Sau khi thêm, chuyển hướng đến danh sách dịch vụ
        } catch (Exception e) {
            model.addAttribute("error", "Lỗi khi thêm dịch vụ: " + e.getMessage());
            return "addService"; // Nếu có lỗi, quay lại form thêm dịch vụ và hiển thị thông báo
        }
    }

    @GetMapping("/edit/{id}")
    public String editService(@PathVariable("id") int serviceId, Model model) {
        // Lấy thông tin sân dịch vụ từ database dựa trên ServiceId
        ServicesModel sv = service.findById(serviceId);

        if (service == null) {
            model.addAttribute("error", "Dịch vụ không tồn tại");
            return "redirect:/services/list";
        }

        // Thêm đối tượng service vào model để hiển thị trong view
        model.addAttribute("service", sv);
        return "editService"; // Trả về view editService.html
    }

    @PostMapping("/edit/{id}")
    public String updateService(@PathVariable("id") int serviceId, @ModelAttribute ServicesModel sv, Model model) {
        // Cập nhật dịch vụ trong database
        sv.setServiceId(serviceId);
        ResponseModel response = service.updateServices(sv);

        if (!response.getIsSuccess()) {
            model.addAttribute("error", response.getMessage());
            return "editService";
        }

        return "redirect:/services/list"; // Sau khi sửa xong, chuyển hướng về danh sách
    }

    @PostMapping("/delete/{id}")
    public String deleteService(@PathVariable("id") Integer id, Model model) {
        ResponseModel result = service.deleteServices(id);
        if (!result.getIsSuccess()) {
            model.addAttribute("error", result.getMessage());
        }
        return "redirect:/services/list";
    }
}
