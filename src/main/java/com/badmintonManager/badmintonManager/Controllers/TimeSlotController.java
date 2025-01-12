package com.badmintonManager.badmintonManager.Controllers;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.badmintonManager.badmintonManager.Config.Config;
import com.badmintonManager.badmintonManager.models.PaymentDTO;
import com.badmintonManager.badmintonManager.services.TimeSlotService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class TimeSlotController {
    private final TimeSlotService service;

    public TimeSlotController(TimeSlotService service) {
        this.service = service;
    }

    @RequestMapping("/Homepage")
    public String homepage(Model model) {

        String dateString = "2025-01-12";
        Date day = Date.valueOf(dateString); 
        model.addAttribute("timeSlots", service.getTimeSlotByDay(day));
        return "timeslot";
    }

    @PostMapping("/bookTimeSlots")
    public String bookTimeSlots(@RequestParam("selectedSlots") List<Integer> selectedSlots, Model model, HttpServletRequest req) throws UnsupportedEncodingException {
        long amount  = 25000*100*selectedSlots.size();
        String vnp_TxnRef = Config.getRandomNumber(8);
        String vnp_TmnCode = Config.vnp_TmnCode; 
        String vnp_IpAddr = Config.getIpAddress(req);  
         
        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", Config.vnp_Version);
        vnp_Params.put("vnp_Command", Config.vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amount));
        vnp_Params.put("vnp_CurrCode", "VND");
        vnp_Params.put("vnp_BankCode", "NCB");
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", "Thanh toan dat san:" + vnp_TxnRef);      
        vnp_Params.put("vnp_Locale", "vn");
        vnp_Params.put("vnp_ReturnUrl", Config.vnp_ReturnUrl);
        vnp_Params.put("vnp_OrderType", Config.orderType);
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);


        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);
        
        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);
        
        List fieldNames = new ArrayList(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = (String) vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                //Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                query.append('=');            
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        String queryUrl = query.toString();
        String vnp_SecureHash = Config.hmacSHA512(Config.secretKey, hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        String paymentUrl = Config.vnp_PayUrl + "?" + queryUrl;
        PaymentDTO paymentDTO = new PaymentDTO();
        paymentDTO.setMessage("Succesfully");
        paymentDTO.setStatus("OK");
        paymentDTO.setUrl(paymentUrl);
        if (selectedSlots != null && !selectedSlots.isEmpty()) {
            boolean updateTimeSlot = service.updateTimeSlot(selectedSlots);
            if(!updateTimeSlot){
                model.addAttribute("message", "Sân này đã có người đặt rồi");
            }
            model.addAttribute("message", "Cập nhật trạng thái thành công!");
        } else {
            model.addAttribute("message", "Vui lòng chọn ít nhất một time slot.");
        }

        model.addAttribute("timeSlots", service.getTimeSlotByDay(null));
        return "redirect:" + paymentUrl;
    }
}
