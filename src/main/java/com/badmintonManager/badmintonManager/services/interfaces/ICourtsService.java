package com.badmintonManager.badmintonManager.services.interfaces;

import com.badmintonManager.badmintonManager.models.CourtsModel;
import com.badmintonManager.badmintonManager.models.ResponseModel;

public interface ICourtsService {
    ResponseModel getAllCourts();
    ResponseModel deleteCourts(Integer id);
    ResponseModel createCourts(CourtsModel courts);
    ResponseModel updateCourts(CourtsModel courts);
    CourtsModel findById(Integer id);
}
