package com.badmintonManager.badmintonManager.Controllers;

import java.math.BigDecimal;
import java.security.Timestamp;
//import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.badmintonManager.badmintonManager.models.BillDetailsModel;
import com.badmintonManager.badmintonManager.models.BillsModel;
import com.badmintonManager.badmintonManager.models.CourtsModel;
import com.badmintonManager.badmintonManager.models.EmployeesModel;
import com.badmintonManager.badmintonManager.models.ResponseModel;
import com.badmintonManager.badmintonManager.models.ServicesModel;
import com.badmintonManager.badmintonManager.repositories.IBillDetailsRepository;
import com.badmintonManager.badmintonManager.services.CourtsService;
import com.badmintonManager.badmintonManager.services.EmployeesService;
import com.badmintonManager.badmintonManager.services.ServiceService;
import com.badmintonManager.badmintonManager.services.interfaces.IBillsService;
import com.badmintonManager.badmintonManager.utils.getCookies;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/bill")
public class BillsController {
	private final IBillsService service;
	private final IBillDetailsRepository detailservice;
	
	public BillsController(IBillsService service, IBillDetailsRepository detailservice) {
        this.service = service;
        this.detailservice = detailservice;
    }

	@Autowired
	private EmployeesService employeeService;
	
	@Autowired
	private CourtsService courtService;
	
	@Autowired
	private ServiceService serviceService;
	
    @GetMapping("/list")
    public String showRegisterForm(Model model, HttpServletRequest request) {
        String username = getCookies.getCookieValue(request, "username");
        if (username == null) {
            return "redirect:/auth/login";
        }
        model.addAttribute("username", username);
        ResponseModel result = service.getAllBills();

        if (!result.getIsSuccess()) {
            model.addAttribute("error", result.getMessage());
        } else {
            @SuppressWarnings("unchecked")
            List<BillsModel> listBills = (List<BillsModel>) result.getData();
            
            for (BillsModel bill : listBills) {
            	String employeeName = employeeService.getEmployeeNameById(bill.getEmployeeId());
                String courtName = courtService.getCourtNameById(bill.getCourtId());
                bill.setEmployeeName(employeeName);
                bill.setCourtName(courtName);
            }
            
            model.addAttribute("listBills", listBills);
        }
        return "bill";
    }

    @GetMapping("/create")
    public String createBill(Model model, HttpServletRequest request) {
    	
    	// Lấy tên tài khoản từ cookie
        String username = getCookies.getCookieValue(request, "username");
        if (username == null) {
            return "redirect:/auth/login";
        }

        // Lấy thông tin nhân viên dựa trên username
        EmployeesModel employee = employeeService.getEmployeeByUsername(username);
        if (employee == null) {
            model.addAttribute("error", "Không tìm thấy thông tin nhân viên.");
            return "redirect:/auth/login";
        }
        model.addAttribute("employeeName", employee.getFullname());

        // Lấy dữ liệu từ các service
        ResponseModel responseCourts = courtService.getAllCourts();
        ResponseModel responseServices = serviceService.getAllServices();

        if (responseCourts.getIsSuccess()) {
            model.addAttribute("courts", responseCourts.getData());
        } else {
            model.addAttribute("errorCourts", responseCourts.getMessage());
        }

        if (responseServices.getIsSuccess()) {
            model.addAttribute("services", responseServices.getData());
        } else {
            model.addAttribute("errorServices", responseServices.getMessage());
        }

        // Lấy thời gian hiện tại và tạo mã hóa đơn random
        String currentDateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String randomCode = "HD-" + UUID.randomUUID().toString().substring(0, 5).toUpperCase();

        model.addAttribute("currentDateTime", currentDateTime);
        model.addAttribute("randomCode", randomCode);

        return "addBill";
    }
    
    //gốc
    /*@PostMapping("/addBill")
    public ResponseEntity<?> addBill(@RequestBody BillsModel bill) {
        try {
            service.createBills(bill);
            return ResponseEntity.ok("Bill added successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error adding bill: " + e.getMessage());
        }
    }*/
    
    @PostMapping("/addBill")
    public String addBill(
        @RequestParam("currentDateTime") String currentDateTime,
        @RequestParam("courtName") String courtName,
        @RequestParam("totalAmount") BigDecimal totalAmount,
        @RequestParam("employeeName") String employeeName,
        @RequestParam("code") String code,
	    @RequestParam("serviceId") List<Integer> serviceId,
	    @RequestParam("quantity") List<Integer> quantities,
	    @RequestParam("price") List<BigDecimal> unitPrices){
        try {
            // Chuyển đổi currentDateTime từ String sang java.sql.Timestamp
            java.sql.Timestamp timestamp = java.sql.Timestamp.valueOf(currentDateTime);
            java.sql.Date createdAt = new java.sql.Date(timestamp.getTime());

            // Lấy mã nhân viên theo tên
            EmployeesModel employee = employeeService.getEmployeeByName(employeeName);
            if (employee == null) {
                return "redirect:/bill/addBill?error=true&message=Không tìm thấy nhân viên";
            }

            // Lấy mã sân theo tên
            CourtsModel court = courtService.getCourtByName(courtName);
            if (court == null) {
                return "redirect:/bill/addBill?error=true&message=Không tìm thấy sân";
            }

            // Tạo hóa đơn
            BillsModel bill = new BillsModel();
            bill.setCreatedAt(createdAt);
            bill.setCourtName(courtName);
            bill.setCourtId(court.getCourtId());
            bill.setTotalAmount(totalAmount);
            bill.setEmployeeId(employee.getEmployeeId());
            bill.setEmployeeName(employeeName);
            bill.setCode(code);

            // Lưu hóa đơn
            BillsModel savedBill = service.save(bill);
            
	         // Lưu chi tiết hóa đơn (danh sách dịch vụ)
	            for (int i = 0; i < serviceId.size(); i++) {
	                BillDetailsModel billDetail = new BillDetailsModel();
	                billDetail.setBill(savedBill);
	                billDetail.setService(serviceService.findById(serviceId.get(i)));
	                billDetail.setQuantity(quantities.get(i));
	                billDetail.setUnitprice(unitPrices.get(i));
	                detailservice.save(billDetail);
	            }
            
            return "redirect:/bill/list";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/bill/addBill?error=true";
        }
    }

    @GetMapping("/edit/{id}")
    public String editBill(@PathVariable("id") int billId, Model model) {
        BillsModel bill = service.findById(billId);

        if (bill == null) {
            model.addAttribute("error", "Hóa đơn không tồn tại");
            return "redirect:/bill/list";
        }

        // Thêm đối tượng court vào model để hiển thị trong view
        model.addAttribute("bill", bill);
        return "editBill";
    }

    @PostMapping("/edit/{id}")
    public String updateBill(@PathVariable("id") int billId, @ModelAttribute BillsModel bill, Model model) {
        bill.setCourtId(billId);
        ResponseModel response = service.updateBills(bill);

        if (!response.getIsSuccess()) {
            model.addAttribute("error", response.getMessage());
            return "editBill";
        }

        return "redirect:/bill/list";
    }

    @PostMapping("/delete/{id}")
    public String deleteBill(@PathVariable("id") Integer id, Model model) {
        ResponseModel result = service.deleteBills(id);
        if (!result.getIsSuccess()) {
            model.addAttribute("error", result.getMessage());
        }
        return "redirect:/bill/list";
    }

}
