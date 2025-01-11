package com.badmintonManager.badmintonManager.Controllers;

import java.sql.Date;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.badmintonManager.badmintonManager.services.TimeSlotService;

@Controller
public class TimeSlotController {
	private final TimeSlotService service;

    public TimeSlotController(TimeSlotService service) {
        this.service = service;
    }

    @RequestMapping("/Homepage")
    public String homepage(Model model) {
        String dateString = "2025-01-09";
        Date day = Date.valueOf(dateString); 
        model.addAttribute("timeSlots", service.getTimeSlotByDay(day));
        return "timeslot";
    }

    @PostMapping("/bookTimeSlots")
    public String bookTimeSlots(@RequestParam("selectedSlots") List<Integer> selectedSlots, Model model) {
        if (selectedSlots != null && !selectedSlots.isEmpty()) {
            // Gọi service để cập nhật trạng thái cho các time slot được chọn
            boolean updateTimeSlot = service.updateTimeSlot(selectedSlots);
            if(!updateTimeSlot){
                model.addAttribute("message", "Sân này đã có người đặt rồi");
            }
            model.addAttribute("message", "Cập nhật trạng thái thành công!");
        } else {
            model.addAttribute("message", "Vui lòng chọn ít nhất một time slot.");
        }

        // Lấy lại danh sách time slots và hiển thị
        model.addAttribute("timeSlots", service.getTimeSlotByDay(null));
        return "timeslot";
    }
}
