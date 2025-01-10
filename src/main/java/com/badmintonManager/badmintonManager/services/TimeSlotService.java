package com.badmintonManager.badmintonManager.services;

import java.sql.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.badmintonManager.badmintonManager.models.TimeSlotModel;
import com.badmintonManager.badmintonManager.repositories.TimeSlotRepository;
import com.badmintonManager.badmintonManager.services.interfaces.ITimeSlotService;

@Service
public class TimeSlotService implements ITimeSlotService {

    private final TimeSlotRepository repository;

    public TimeSlotService(TimeSlotRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<TimeSlotModel> getAllTimeSlot() {
        List<TimeSlotModel> l = repository.findAll();
        return l;
    }

    @Override
    public List<TimeSlotModel> getTimeSlotByDay(Date day) {
        String dateString = "2024-12-19";
        day = Date.valueOf(dateString);
        List<TimeSlotModel> l = repository.findBySlotDate(day);
        
        return l;
    }

    @Override
    public boolean UpdateTimeSlot(TimeSlotModel timeSlotModel) {
        if(repository.findById(timeSlotModel.getSlotId()) != null)
        {
            if(timeSlotModel.getStatus().equals("available"))
            {
                timeSlotModel.setStatus("busy");
                repository.save(timeSlotModel);
                return true;
            }
            else
            return false;
        }
        else
        return false;
        
    }

    @Override
    public boolean updateTimeSlot(List<Integer> list) {
        List<TimeSlotModel> timeslots = repository.findAllById(list);
        for(TimeSlotModel item : timeslots){
            boolean updateTimeSlot = UpdateTimeSlot(item);
            if(!updateTimeSlot) return false;
        }
        return true;
    }
}
