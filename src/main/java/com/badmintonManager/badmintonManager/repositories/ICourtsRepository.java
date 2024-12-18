package com.badmintonManager.badmintonManager.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.badmintonManager.badmintonManager.models.CourtsModel;

public interface ICourtsRepository extends JpaRepository<CourtsModel, Integer>  {
	CourtsModel findBycourtName(String courtName);
}
