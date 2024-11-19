package com.badmintonManager.badmintonManager.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.badmintonManager.badmintonManager.models.EmployeesModel;
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
    public EmployeesModel register(EmployeesModel user) {
        user.setPassword(user.getPassword());
        return repository.save(user);
    }

    @Override
    public EmployeesModel login(String username, String password) {

        EmployeesModel user = repository.findByUsername(username);

        if (user == null) {
            return null; // Trả về null nếu không tìm thấy user
        }
    
        // So sánh nội dung chuỗi
        if (!user.getPassword().equals(password)) {
            return null; // Trả về null nếu password không khớp
        }
    
        return user;
    }
}
