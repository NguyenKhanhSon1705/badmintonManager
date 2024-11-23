package com.badmintonManager.badmintonManager.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.badmintonManager.badmintonManager.models.EmployeesModel;
import com.badmintonManager.badmintonManager.models.ResponseModel;
import com.badmintonManager.badmintonManager.repositories.IEmployeesRepository;
import com.badmintonManager.badmintonManager.services.interfaces.IAuthService;

@Service
public class AuthService implements IAuthService {

    private final IEmployeesRepository repository;

    @Autowired
    public AuthService(IEmployeesRepository repository) {
        this.repository = repository;
    }

    @Override
    public ResponseModel register(EmployeesModel user) {
        // Kiểm tra xem username đã tồn tại chưa
        EmployeesModel curUser = repository.findByUsername(user.getUsername());
        if (curUser != null) {
            return new ResponseModel("Username đã tồn tại", null, 400, false); // HTTP 400 - Bad Request
        }

        // Mã hóa mật khẩu (nếu cần thiết)
        user.setPassword(user.getPassword()); // Bạn có thể thay thế bằng logic mã hóa mật khẩu

        // Lưu người dùng mới
        repository.save(user);

        // Trả về phản hồi thành công
        return new ResponseModel("Đăng ký thành công", user, 201, true); // HTTP 201 - Created
    }

    @Override
    public ResponseModel login(String username, String password) {
        // Tìm người dùng theo username
        EmployeesModel user = repository.findByUsername(username);

        // Kiểm tra nếu user là null hoặc mật khẩu không đúng
        if (user == null || !user.getPassword().equals(password)) {
            return new ResponseModel("Tài khoản hoặc mật khẩu không đúng", null, 400, false);
        }

        // Nếu thông tin hợp lệ, trả về thành công
        return new ResponseModel("Đăng nhập thành công", user, 200, true);
    }

}
