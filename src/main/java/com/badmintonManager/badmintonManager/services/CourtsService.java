package com.badmintonManager.badmintonManager.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.badmintonManager.badmintonManager.models.CourtsModel;
import com.badmintonManager.badmintonManager.models.EmployeesModel;
import com.badmintonManager.badmintonManager.models.ResponseModel;
import com.badmintonManager.badmintonManager.repositories.ICourtsRepository;
import com.badmintonManager.badmintonManager.services.interfaces.ICourtsService;

@Service
public class CourtsService implements ICourtsService {
    private final ICourtsRepository repository;

    @Autowired
    public CourtsService(ICourtsRepository repository) {
        this.repository = repository;
    }
    
    @Override
	public String getCourtNameById(Integer courtId) {
		return repository.findById(courtId).map(CourtsModel::getCourtName).orElse("Unknown Court");
	}
    
    public CourtsModel getCourtByName(String courtName) {
        return repository.findBycourtName(courtName); 
    }

    @Override
    public ResponseModel deleteCourts(Integer id) {
        try {
            if (!repository.existsById(id)) {
                return new ResponseModel("Sân không tồn tại", null, 404, false);
            }
            repository.deleteById(id);
            return new ResponseModel("Xóa thành công", null, 200, true);
        } catch (Exception e) {
            return new ResponseModel("Xóa thất bại", null, 500, false);
        }
    }

    @Override
    public CourtsModel findById(Integer id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public ResponseModel getAllCourts() {
        try{
            List<CourtsModel> courts = repository.findAll();

            if (courts.size() <= 0) {
                return new ResponseModel("Danh sách trống", null, 404, false);
            }
            return new ResponseModel("Danh sách sân", courts, 200, true);
        }catch (Exception e){
            return new ResponseModel("Lỗi: " + e.getMessage() , null, 404, false);

        }
    }
    @Override
    public  ResponseModel createCourts(CourtsModel courts){
        try {
            repository.save(courts);
            return new ResponseModel("Thêm mới thành công", null, 200, true);
        } catch (Exception e) {
            return new ResponseModel("Thêm mới thất bại", null, 500, false);
        }
    }

    @Override
    public ResponseModel updateCourts(CourtsModel courts) {
        try {
            repository.save(courts);
            return new ResponseModel("Cập nhật thành công", null, 200, true);
        } catch (Exception e) {
            return new ResponseModel("Cập nhật thất bại", null, 500, false);
        }
    }

}
