package com.badmintonManager.badmintonManager.Controllers;

import java.math.BigDecimal;
import java.security.Timestamp;
import java.sql.Time;
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
import org.springframework.web.bind.annotation.ResponseBody;

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
    
    @PostMapping("/addBill")
    public String addBill(
            @RequestParam("currentDateTime") String currentDateTime,
            @RequestParam("courtName") String courtName,
            @RequestParam("totalAmount") BigDecimal totalAmount,
            @RequestParam("courtFee") BigDecimal courtFee, // Thêm tham số courtFee
            @RequestParam("employeeName") String employeeName,
            @RequestParam("code") String code,
            @RequestParam("serviceId") List<Integer> serviceId,
            @RequestParam("quantity") List<Integer> quantities,
            @RequestParam("price") List<BigDecimal> unitPrices,
            @RequestParam("checkin") String checkin, 
            @RequestParam(value = "checkout", required = false) String checkout) {

        try {
            // Chuyển đổi currentDateTime từ String sang LocalDateTime
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime createdAt = LocalDateTime.parse(currentDateTime, formatter);
            Time checkinTime = Time.valueOf(checkin); // Chuyển đổi checkin thành Time

            // Nếu checkout không có giá trị, set nó thành null
            Time checkoutTime = (checkout != null) ? Time.valueOf(checkout) : null;

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
            
            court.setStatus(1);
            courtService.updateCourts(court);

         // Tổng tiền
            BigDecimal finalTotalAmount = totalAmount;

            // Tạo hóa đơn
            BillsModel bill = new BillsModel();
            bill.setCreatedAt(createdAt);
            bill.setCourtName(courtName);
            bill.setCourtId(court.getCourtId());
            bill.setTotalAmount(finalTotalAmount); // Cập nhật tổng tiền
            bill.setEmployeeId(employee.getEmployeeId());
            bill.setEmployeeName(employeeName);
            bill.setCode(code);
            bill.setCheckin(checkinTime);
            bill.setCheckout(checkoutTime);
            bill.setStatus(0);

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

        // Chuyển đổi thời gian tạo sang định dạng dd/MM/yyyy HH:mm:ss
        String formattedDate = bill.getCreatedAt().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));

        // Thêm đối tượng bill và thông tin chi tiết hóa đơn vào model
        model.addAttribute("bill", bill);
        model.addAttribute("billDetails", bill.getBillDetails());
        model.addAttribute("formattedDate", formattedDate);

        // Các thông tin khác cần hiển thị
        model.addAttribute("courts", courtService.getAllCourts().getData());
        model.addAttribute("services", serviceService.getAllServices().getData());

        return "editBill";
    }

    @PostMapping("/edit/{id}")
    public String updateBill(@PathVariable("id") int billId, 
                             @ModelAttribute BillsModel bill, 
                             @RequestParam("serviceId") List<Integer> serviceId, 
                             @RequestParam("quantity") List<Integer> quantities, 
                             @RequestParam("price") List<BigDecimal> unitPrices, 
                             Model model) {

        BillsModel existingBill = service.findById(billId);

        if (existingBill == null) {
            model.addAttribute("error", "Hóa đơn không tồn tại");
            return "redirect:/bill/list";
        }

        // Cập nhật thông tin hóa đơn chính
        existingBill.setTotalAmount(new BigDecimal(0)); // Đặt lại tổng số tiền
        for (int i = 0; i < serviceId.size(); i++) {
            // Tạo chi tiết hóa đơn mới cho dịch vụ
            BillDetailsModel billDetail = new BillDetailsModel();
            BillDetailsModel existingDetail = null;

            // Tìm chi tiết dịch vụ nếu đã tồn tại, nếu không thì tạo mới
            for (BillDetailsModel detail : existingBill.getBillDetails()) {
                if (detail.getService().getServiceId() == serviceId.get(i)) {
                    existingDetail = detail;
                    break;
                }
            }

            if (existingDetail != null) {
                // Cập nhật thông tin nếu dịch vụ đã tồn tại
                existingDetail.setQuantity(quantities.get(i));
                existingDetail.setUnitprice(unitPrices.get(i));
            } else {
                // Thêm mới nếu dịch vụ chưa tồn tại
                ServicesModel service = serviceService.findById(serviceId.get(i));
                billDetail.setBill(existingBill);
                billDetail.setService(service);
                billDetail.setQuantity(quantities.get(i));
                billDetail.setUnitprice(unitPrices.get(i));
                detailservice.save(billDetail);
            }

            // Cập nhật tổng tiền
            existingBill.setTotalAmount(existingBill.getTotalAmount().add(new BigDecimal(quantities.get(i)).multiply(unitPrices.get(i))));
        }

        // Cập nhật hóa đơn
        ResponseModel response = service.updateBills(existingBill);
        if (!response.getIsSuccess()) {
            model.addAttribute("error", response.getMessage());
            return "editBill";
        }

        return "redirect:/bill/list";
    }
    
    @PostMapping("/addBillWithPayment")
    public String addBillWithPayment(
        @RequestParam("currentDateTime") String currentDateTime,
        //@RequestParam("courtName") String courtName,
        @RequestParam("totalAmount") BigDecimal totalAmount,
        @RequestParam("courtFee") BigDecimal courtFee,
        @RequestParam("employeeName") String employeeName,
        @RequestParam("code") String code,
        @RequestParam(value = "serviceId", required = false) List<Integer> serviceId,
        @RequestParam(value = "quantity", required = false) List<Integer> quantities,
        @RequestParam(value = "price", required = false) List<BigDecimal> unitPrices,
        @RequestParam("checkin") String checkin,
        @RequestParam("checkout") String checkout) {
        
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime createdAt = LocalDateTime.parse(currentDateTime, formatter);
            Time checkinTime = Time.valueOf(checkin);
            Time checkoutTime = Time.valueOf(checkout);

            EmployeesModel employee = employeeService.getEmployeeByName(employeeName);
            if (employee == null) 
                return "redirect:/bill/addBill?error=true&message=Không tìm thấy nhân viên";

            String courtName = "Sân 1";
            int courtId = 1;
            
            CourtsModel court = courtService.getCourtById(courtId);
            if (court == null) 
                return "redirect:/bill/addBill?error=true&message=Không tìm thấy sân";

            // Cập nhật trạng thái sân thành không hoạt động
            court.setStatus(0);
            courtService.updateCourts(court);

            BillsModel bill = new BillsModel();
            bill.setCreatedAt(createdAt);
            bill.setCourtName(courtName);
            bill.setCourtId(courtId);
            bill.setTotalAmount(totalAmount);
            bill.setEmployeeId(employee.getEmployeeId());
            bill.setEmployeeName(employeeName);
            bill.setCode(code);
            bill.setCheckin(checkinTime);
            bill.setCheckout(checkoutTime);
            // Đặt trạng thái hóa đơn là đã thanh toán
            bill.setStatus(1);

            BillsModel savedBill = service.save(bill);

            if (serviceId != null && !serviceId.isEmpty()) {
                for (int i = 0; i < serviceId.size(); i++) {
                    BillDetailsModel billDetail = new BillDetailsModel();
                    billDetail.setBill(savedBill);
                    billDetail.setService(serviceService.findById(serviceId.get(i)));
                    billDetail.setQuantity(quantities.get(i));
                    billDetail.setUnitprice(unitPrices.get(i));
                    detailservice.save(billDetail);
                }
            }


            return "redirect:/bill/list";
        } catch (Exception e) {
            e.printStackTrace();
			return "redirect:/bill/addBill?error=true";
        }
    }

}
