package com.badmintonManager.badmintonManager.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.badmintonManager.badmintonManager.models.ServicesModel;
@Repository
public interface IServiceRepository extends JpaRepository<ServicesModel, Integer> {

}
