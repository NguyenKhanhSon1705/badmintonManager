package com.badmintonManager.badmintonManager.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.badmintonManager.badmintonManager.models.BillDetailsModel;

@Repository
public interface IBillDetailsRepository extends JpaRepository<BillDetailsModel, Integer>{

}
