package com.badmintonManager.badmintonManager.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.badmintonManager.badmintonManager.models.EmployeesModel;

public interface IEmployeesRepository extends JpaRepository<EmployeesModel, Integer>  {
    EmployeesModel findByUsername(String username);
}
