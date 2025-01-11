package com.badmintonManager.badmintonManager.Controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.badmintonManager.badmintonManager.models.CourtsModel;
import com.badmintonManager.badmintonManager.models.ResponseModel;
import com.badmintonManager.badmintonManager.services.interfaces.ICourtsService;
import com.badmintonManager.badmintonManager.utils.getCookies;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/courts")
public class CourtsController {
    private final ICourtsService service;

    public CourtsController(ICourtsService service) {
        this.service = service;
    }

    @GetMapping("/list")
    public String showRegisterForm(Model model, HttpServletRequest request) {
        String username = getCookies.getCookieValue(request, "username");
        if (username == null) {
            return "redirect:/auth/login";
        }
        model.addAttribute("username", username);
        ResponseModel result = service.getAllCourts();

        if (!result.getIsSuccess()) {
            model.addAttribute("error", result.getMessage());
        } else {
            @SuppressWarnings("unchecked")
            List<CourtsModel> listCourts = (List<CourtsModel>) result.getData();
            model.addAttribute("listCourts", listCourts);
        }
        return "courts";
    }

    @GetMapping("/create")
    public String createCourt() {
        return "addCourts";
    }

    @PostMapping("/add")
    public String addCourt(CourtsModel court, Model model) {
        try {
            service.createCourts(court);
            return "redirect:/courts/list"; // Sau khi thêm, chuyển hướng đến danh sách sân
        } catch (Exception e) {
            model.addAttribute("error", "Lỗi khi thêm sân: " + e.getMessage());
            return "addCourt"; // Nếu có lỗi, quay lại form thêm sân và hiển thị thông báo
        }
    }

    @GetMapping("/edit/{id}")
    public String editCourt(@PathVariable("id") int courtId, Model model) {
        // Lấy thông tin sân cầu lông từ database dựa trên courtId
        CourtsModel court = service.findById(courtId);

        if (court == null) {
            model.addAttribute("error", "Sân cầu lông không tồn tại");
            return "redirect:/courts/list";
        }

        // Thêm đối tượng court vào model để hiển thị trong view
        model.addAttribute("court", court);
        return "editCourt"; // Trả về view editCourt.html
    }

    @PostMapping("/edit/{id}")
    public String updateCourt(@PathVariable("id") int courtId, @ModelAttribute CourtsModel court, Model model) {
        // Cập nhật sân cầu lông trong database
        court.setCourtId(courtId);
        ResponseModel response = service.updateCourts(court);

        if (!response.getIsSuccess()) {
            model.addAttribute("error", response.getMessage());
            return "editCourt";
        }

        return "redirect:/courts/list"; // Sau khi sửa xong, chuyển hướng về danh sách
    }

    @PostMapping("/delete/{id}")
    public String deleteCourt(@PathVariable("id") Integer id, Model model) {
        ResponseModel result = service.deleteCourts(id);
        if (!result.getIsSuccess()) {
            model.addAttribute("error", result.getMessage());
        }
        return "redirect:/courts/list";
    }
}
