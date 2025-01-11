package com.badmintonManager.badmintonManager.Controllers;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.badmintonManager.badmintonManager.models.StatisticalDTO;
import com.badmintonManager.badmintonManager.models.StatisticalFilterDTO;
import com.badmintonManager.badmintonManager.services.interfaces.IStatisticalService;
import com.badmintonManager.badmintonManager.utils.getCookies;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/admin")
public class StatisticalController {
    private final IStatisticalService statisticalService;

    public StatisticalController(IStatisticalService service) {
        statisticalService = service;
    }

    @GetMapping("/Statistical")
    public String showRegisterForm(
            @RequestParam(required = false) String filter,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDateTime timeStart,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDateTime timeEnd,
            Model model) {
        // String username = getCookies.getCookieValue(request, "username");
        // if (username == null) {
        // return "redirect:/auth/login";
        // }
        // model.addAttribute("username", username);

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startOfDay = now.toLocalDate().atStartOfDay(); // 00:00 của ngày hiện tại
        LocalDateTime endOfDay = now;
        if(timeStart != null && timeEnd != null){
            startOfDay = timeStart;
            endOfDay = timeEnd;
        }
        else if (filter.contains(("today"))) {
            startOfDay = now.toLocalDate().atStartOfDay(); // 00:00 của ngày hiện tại
            endOfDay = now;
        }
        else if(filter.contains("yesterday")){
            startOfDay = now.minusDays(1).toLocalDate().atStartOfDay(); // 00:00 của ngày hôm qua
            endOfDay = now.minusDays(1);
        }
        else if(filter.contains("this_week")){
            startOfDay = now.minusDays(now.getDayOfWeek().getValue() - 1).toLocalDate().atStartOfDay(); // 00:00 của tuần này
            endOfDay = now;
        }
        StatisticalFilterDTO filters = new StatisticalFilterDTO(startOfDay, endOfDay);
        StatisticalDTO result = statisticalService.getStatistical(filters);
        model.addAttribute("result", result);

        return "admin";
    }
}
