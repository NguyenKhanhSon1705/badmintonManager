package com.badmintonManager.badmintonManager.services.interfaces;

import com.badmintonManager.badmintonManager.models.EmployeesModel;
import com.badmintonManager.badmintonManager.models.ResponseModel;

public interface IUserManager {
    ResponseModel getAllUser();
    ResponseModel deleteUser(Integer id);
    ResponseModel updateUser(EmployeesModel user);
    EmployeesModel findById(Integer id);
    EmployeesModel findByUsername(String username);
}
