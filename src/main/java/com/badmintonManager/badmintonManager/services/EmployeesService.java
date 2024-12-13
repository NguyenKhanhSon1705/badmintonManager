package com.badmintonManager.badmintonManager.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.badmintonManager.badmintonManager.models.EmployeesModel;
import com.badmintonManager.badmintonManager.repositories.IEmployeesRepository;
import com.badmintonManager.badmintonManager.services.interfaces.IEmployeesService;

@Service
public class EmployeesService implements IEmployeesService{
	@Autowired
    private IEmployeesRepository repository; // Repository kết nối với database
	
    public EmployeesService(IEmployeesRepository repository) {
		this.repository = repository;
	}

	@Override
	public String getEmployeeNameById(Integer employeeid) {
		return repository.findById(employeeid).map(EmployeesModel::getFullname).orElse("Unknown Employee");
	}


}
