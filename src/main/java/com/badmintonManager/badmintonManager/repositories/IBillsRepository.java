package com.badmintonManager.badmintonManager.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.badmintonManager.badmintonManager.models.BillsModel;

@Repository
public interface IBillsRepository extends JpaRepository<BillsModel, Integer>{
}
