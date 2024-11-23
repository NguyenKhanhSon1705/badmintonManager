package com.badmintonManager.badmintonManager.services.interfaces;

import com.badmintonManager.badmintonManager.models.EmployeesModel;
import com.badmintonManager.badmintonManager.models.ResponseModel;

public interface IAuthService {
    ResponseModel register(EmployeesModel user);
    ResponseModel login(String username, String password);
}
