package com.badmintonManager.badmintonManager.services.interfaces;

import java.sql.Date;
import java.util.List;

import com.badmintonManager.badmintonManager.models.TimeSlotModel;

public interface ITimeSlotService {
	List<TimeSlotModel> getAllTimeSlot();
    List<TimeSlotModel> getTimeSlotByDay(Date day);
    boolean UpdateTimeSlot(TimeSlotModel timeSlotModel);
    boolean updateTimeSlot(List<Integer> list);
    boolean InsertNewTimeSlotForAllCourts();


}
