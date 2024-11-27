package com.badmintonManager.badmintonManager.services.interfaces;
import com.badmintonManager.badmintonManager.models.ResponseModel;
import com.badmintonManager.badmintonManager.models.ServicesModel;

public interface IServiceService {
    ResponseModel getAllServices();
    ResponseModel deleteServices(Integer id);
    ResponseModel createServices(ServicesModel services);
    ResponseModel updateServices(ServicesModel services);
    ServicesModel findById(Integer id);


}
