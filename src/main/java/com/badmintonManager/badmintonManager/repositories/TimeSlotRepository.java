package com.badmintonManager.badmintonManager.repositories;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;

import com.badmintonManager.badmintonManager.models.TimeSlotModel;

@Repository
public interface TimeSlotRepository extends JpaRepository<TimeSlotModel,Integer> {
    
    //@Query(name="TimeSlotModel.findBySlotDate", value ="SELECT * FROM time_slots t WHERE t.slot_date = :day", nativeQuery = true)
    //List<TimeSlotModel> findBySlotDate(@Param("day") Date day); 

    List<TimeSlotModel> findBySlotDate(Date date);

    // @Procedure("GenerateTimeSlotsForAllCourts")
    // void InsertNewTimeSlotForAllCourts();
}
