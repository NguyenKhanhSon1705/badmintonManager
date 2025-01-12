package com.badmintonManager.badmintonManager.services;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.badmintonManager.badmintonManager.models.BillDetailDTO;
import com.badmintonManager.badmintonManager.models.BillDetailsModel;
import com.badmintonManager.badmintonManager.models.BillsModel;
import com.badmintonManager.badmintonManager.models.ResponseModel;
import com.badmintonManager.badmintonManager.models.ServicesModel;
import com.badmintonManager.badmintonManager.repositories.IBillDetailsRepository;
import com.badmintonManager.badmintonManager.repositories.IBillsRepository;
import com.badmintonManager.badmintonManager.repositories.IServiceRepository;
import com.badmintonManager.badmintonManager.services.interfaces.IBillsService;

import jakarta.transaction.Transactional;

@Service
public class BillsService implements IBillsService{
	private final IBillsRepository repository;
	private final IBillDetailsRepository detailrepository;
	private final IServiceRepository servicerepository;

	@Autowired
    public BillsService(IBillsRepository repository, IBillDetailsRepository detailrepository, IServiceRepository servicerepository) {
        this.repository = repository;
        this.detailrepository = detailrepository;
        this.servicerepository = servicerepository;
    }

    @Override
	public ResponseModel getAllBills() {
		try{
            List<BillsModel> bill = repository.findAll();

            if (bill.size() <= 0) {
                return new ResponseModel("Danh sách trống", null, 404, false);
            }
            return new ResponseModel("Danh sách sân", bill, 200, true);
        }catch (Exception e){
            return new ResponseModel("Lỗi: " + e.getMessage() , null, 404, false);

        }
	}

	@Override
	public ResponseModel deleteBills(Integer id) {
		try {
            if (!repository.existsById(id)) {
                return new ResponseModel("Hóa đơn không tồn tại", null, 404, false);
            }
            repository.deleteById(id);
            return new ResponseModel("Xóa thành công", null, 200, true);
        } catch (Exception e) {
            return new ResponseModel("Xóa thất bại", null, 500, false);
        }
	}

	@Override
	public ResponseModel createBills(BillsModel bill) {
		try {
            repository.save(bill);
            return new ResponseModel("Thêm mới thành công", null, 200, true);
        } catch (Exception e) {
            return new ResponseModel("Thêm mới thất bại", null, 500, false);
        }
	}

	@Override
	public ResponseModel updateBills(BillsModel bill) {
		try {
            repository.save(bill);
            return new ResponseModel("Cập nhật thành công", null, 200, true);
        } catch (Exception e) {
            return new ResponseModel("Cập nhật thất bại", null, 500, false);
        }
	}
	
	@Override
	public BillsModel save(BillsModel bill) {
        return repository.save(bill);
    }

	@Override
	public BillsModel findById(Integer id) {
		return repository.findById(id).orElse(null);
	}
}
