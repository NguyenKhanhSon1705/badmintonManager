package com.badmintonManager.badmintonManager.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.badmintonManager.badmintonManager.models.CourtsModel;
import com.badmintonManager.badmintonManager.models.EmployeesModel;
import com.badmintonManager.badmintonManager.models.ResponseModel;
import com.badmintonManager.badmintonManager.repositories.IEmployeesRepository;
import com.badmintonManager.badmintonManager.services.interfaces.IEmployeesService;

@Service
public class EmployeesService implements IEmployeesService{
	@Autowired
    private IEmployeesRepository repository;
	
    public EmployeesService(IEmployeesRepository repository) {
		this.repository = repository;
	}
    
    public EmployeesModel getEmployeeByUsername(String username) {
        return repository.findByUsername(username);
    }
    
    public EmployeesModel getEmployeeByName(String employeeName) {
        return repository.findByFullname(employeeName); 
    }

	@Override
	public String getEmployeeNameById(Integer employeeid) {
		return repository.findById(employeeid).map(EmployeesModel::getFullname).orElse("Unknown Employee");
	}

	@Override
    public ResponseModel getAllEmployees() {
        try{
            List<EmployeesModel> employees = repository.findAll();

            if (employees.size() <= 0) {
                return new ResponseModel("Danh sách trống", null, 404, false);
            }
            return new ResponseModel("Danh sách sân", employees, 200, true);
        }catch (Exception e){
            return new ResponseModel("Lỗi: " + e.getMessage() , null, 404, false);

        }
    }
}
