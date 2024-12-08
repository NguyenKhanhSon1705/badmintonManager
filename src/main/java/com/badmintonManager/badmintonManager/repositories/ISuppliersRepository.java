package com.badmintonManager.badmintonManager.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.badmintonManager.badmintonManager.models.SuppliersModel;
@Repository
public interface ISuppliersRepository extends JpaRepository<SuppliersModel, Integer> {

}
