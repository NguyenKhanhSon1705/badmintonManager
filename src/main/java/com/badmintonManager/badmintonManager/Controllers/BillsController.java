package com.badmintonManager.badmintonManager.Controllers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.badmintonManager.badmintonManager.models.BillsModel;
import com.badmintonManager.badmintonManager.models.CourtsModel;
import com.badmintonManager.badmintonManager.models.EmployeesModel;
import com.badmintonManager.badmintonManager.models.ResponseModel;
import com.badmintonManager.badmintonManager.models.ServicesModel;
import com.badmintonManager.badmintonManager.services.CourtsService;
import com.badmintonManager.badmintonManager.services.EmployeesService;
import com.badmintonManager.badmintonManager.services.ServiceService;
import com.badmintonManager.badmintonManager.services.interfaces.IBillsService;
import com.badmintonManager.badmintonManager.utils.getCookies;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/bill")
public class BillsController {
	private final IBillsService service;
	
	public BillsController(IBillsService service) {
        this.service = service;
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
    public String createBill(Model model) {
    	// Lấy dữ liệu từ các service
    	ResponseModel responseEmployees = employeeService.getAllEmployees();
        ResponseModel responseCourts = courtService.getAllCourts();
        ResponseModel responseServices = serviceService.getAllServices();

     // Kiểm tra và lấy dữ liệu thực tế từ ResponseModel
        if (responseEmployees.getIsSuccess()) {
            List<EmployeesModel> employees = (List<EmployeesModel>) responseEmployees.getData();
            model.addAttribute("employees", employees);
        } else {
            model.addAttribute("errorEmployees", responseEmployees.getMessage());
        }

        if (responseCourts.getIsSuccess()) {
            List<CourtsModel> courts = (List<CourtsModel>) responseCourts.getData();
            model.addAttribute("courts", courts);
        } else {
            model.addAttribute("errorCourts", responseCourts.getMessage());
        }

        if (responseServices.getIsSuccess()) {
            List<ServicesModel> services = (List<ServicesModel>) responseServices.getData();
            model.addAttribute("services", services);
        } else {
            model.addAttribute("errorServices", responseServices.getMessage());
        }
        
        // Lấy thời gian hiện tại và tạp mã hóa đơn random
        String currentDateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss dd/MM/yyyy"));
        String randomCode = "HD-" + UUID.randomUUID().toString().substring(0, 5).toUpperCase();


        // Truyền thời gian hiện tại và mã hóa đơn vào model
        model.addAttribute("currentDateTime", currentDateTime);
        model.addAttribute("randomCode", randomCode);

        return "addBill";
    }

    @PostMapping("/addBill")
    public String addBill(BillsModel bill, Model model) {
        try {
            service.createBills(bill);
            return "redirect:/bill/list";
        } catch (Exception e) {
            model.addAttribute("error", "Lỗi khi thêm hóa đơn: " + e.getMessage());
            return "addBill";
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
