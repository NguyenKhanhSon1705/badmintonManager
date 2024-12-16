package com.badmintonManager.badmintonManager.services.interfaces;


import com.badmintonManager.badmintonManager.models.EmployeesModel;
import com.badmintonManager.badmintonManager.models.ResponseModel;

public interface IEmployeesService {
	String getEmployeeNameById(Integer employeeid);
    ResponseModel getAllEmployees();
}
