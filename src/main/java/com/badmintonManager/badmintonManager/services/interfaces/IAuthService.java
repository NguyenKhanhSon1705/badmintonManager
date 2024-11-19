package com.badmintonManager.badmintonManager.services.interfaces;

import com.badmintonManager.badmintonManager.models.EmployeesModel;

public interface IAuthService {
    EmployeesModel register(EmployeesModel user);
    EmployeesModel login(String username, String password);
}
