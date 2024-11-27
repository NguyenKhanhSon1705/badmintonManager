package com.badmintonManager.badmintonManager.services;

import java.util.List;

import org.springframework.stereotype.Service;
import com.badmintonManager.badmintonManager.models.ResponseModel;
import com.badmintonManager.badmintonManager.models.ServicesModel;
import com.badmintonManager.badmintonManager.repositories.IServiceRepository;
import com.badmintonManager.badmintonManager.services.interfaces.IServiceService;
@Service
public class ServiceService implements IServiceService {
    private final IServiceRepository repository;

    public ServiceService(IServiceRepository repository) {
        this.repository = repository;
    }

    @Override
    public ResponseModel getAllServices() {
        try{
            List<ServicesModel> services = repository.findAll();

            if (services.size() <= 0) {
                return new ResponseModel("Danh sách trống", null, 404, false);
            }
            return new ResponseModel("Danh sách sân", services, 200, true);
        }catch (Exception e){
            return new ResponseModel("Lỗi: " + e.getMessage() , null, 404, false);

        }
    }

    @Override
    public ResponseModel deleteServices(Integer id) {
        try {
            if (!repository.existsById(id)) {
                return new ResponseModel("Dịch vụ không tồn tại", null, 404, false);
            }
            repository.deleteById(id);
            return new ResponseModel("Xóa dịch vụ thành công", null, 200, true);
        } catch (Exception e) {
            return new ResponseModel("Xóa dịch vụ thất bại", null, 500, false);
        }
    }

    @Override
    public ResponseModel createServices(ServicesModel services) {
        try {
            repository.save(services);
            return new ResponseModel("Thêm mới thành công", null, 200, true);
        } catch (Exception e) {
            return new ResponseModel("Thêm mới thất bại", null, 500, false);
        }
    }

    @Override
    public ResponseModel updateServices(ServicesModel services) {
        try {
            repository.save(services);
            return new ResponseModel("Thêm mới thành công", null, 200, true);
        } catch (Exception e) {
            return new ResponseModel("Thêm mới thất bại", null, 500, false);
        }
    }

    @Override
    public ServicesModel findById(Integer id) {
        return repository.findById(id).orElse(null);
    }

}
