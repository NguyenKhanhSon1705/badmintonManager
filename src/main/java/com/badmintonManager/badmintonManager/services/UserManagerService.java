package com.badmintonManager.badmintonManager.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.badmintonManager.badmintonManager.models.EmployeesModel;
import com.badmintonManager.badmintonManager.models.ResponseModel;
import com.badmintonManager.badmintonManager.repositories.IEmployeesRepository;
import com.badmintonManager.badmintonManager.services.interfaces.IUserManager;

@Service
public class UserManagerService implements IUserManager{
    private final IEmployeesRepository repository;
    
    public UserManagerService(IEmployeesRepository repository) {
        this.repository = repository;
    }


    public ResponseModel getAllUser(){
        List<EmployeesModel> users = repository.findAll();

        if(users.size() <= 0){
            return new ResponseModel("Danh sách trống", null, 404, false);
        }
        return new ResponseModel("Danh sách user", users, 200, true);
    }
    @Override
    public EmployeesModel findById(Integer id) {
        Optional<EmployeesModel> user = repository.findById(id);
        return user.orElse(null);
    }
    public EmployeesModel findByUsername(String username){
        EmployeesModel employee = repository.findByUsername(username);
        return employee;
    }

    @Override
    public ResponseModel updateUser(EmployeesModel user) {
        try {
            repository.save(user);
            return new ResponseModel("Cập nhật thành công", null, 200, true);
        } catch (Exception e) {
            return new ResponseModel("Cập nhật thất bại", null, 500, false);
        }
    }

    @Override
    public ResponseModel deleteUser(Integer id) {
        try {
            if (!repository.existsById(id)) {
                return new ResponseModel("Người dùng không tồn tại", null, 404, false);
            }
            repository.deleteById(id);
            return new ResponseModel("Xóa thành công", null, 200, true);
        } catch (Exception e) {
            return new ResponseModel("Xóa thất bại", null, 500, false);
        }
    }
}
