package com.badmintonManager.badmintonManager.services;

import java.util.List;

import org.springframework.stereotype.Service;
import com.badmintonManager.badmintonManager.models.ResponseModel;
import com.badmintonManager.badmintonManager.models.SuppliersModel;
import com.badmintonManager.badmintonManager.repositories.ISuppliersRepository;
import com.badmintonManager.badmintonManager.services.interfaces.ISuppliersService;

@Service
public class SuppliersService implements ISuppliersService {

    private final ISuppliersRepository repository;

    public SuppliersService(ISuppliersRepository repository) {
        this.repository = repository;
    }

    @Override
    public ResponseModel getAllSuppliers() {
        try {
            List<SuppliersModel> suppliers = repository.findAll();
            if (suppliers.isEmpty()) {
                return new ResponseModel("Danh sách nhà cung cấp trống", null, 404, false);
            }
            return new ResponseModel("Danh sách nhà cung cấp", suppliers, 200, true);
        } catch (Exception e) {
            return new ResponseModel("Lỗi: " + e.getMessage(), null, 500, false);
        }
    }

    @Override
    public ResponseModel deleteSupplier(Integer id) {
        try {
            if (!repository.existsById(id)) {
                return new ResponseModel("Nhà cung cấp không tồn tại", null, 404, false);
            }
            repository.deleteById(id);
            return new ResponseModel("Xóa nhà cung cấp thành công", null, 200, true);
        } catch (Exception e) {
            return new ResponseModel("Xóa nhà cung cấp thất bại: " + e.getMessage(), null, 500, false);
        }
    }

    @Override
    public ResponseModel createSupplier(SuppliersModel supplier) {
        try {
            repository.save(supplier);
            return new ResponseModel("Thêm nhà cung cấp thành công", null, 200, true);
        } catch (Exception e) {
            return new ResponseModel("Thêm nhà cung cấp thất bại: " + e.getMessage(), null, 500, false);
        }
    }

    @Override
    public ResponseModel updateSupplier(SuppliersModel supplier) {
        try {
            if (!repository.existsById(supplier.getSupplierId())) {
                return new ResponseModel("Nhà cung cấp không tồn tại", null, 404, false);
            }
            repository.save(supplier);
            return new ResponseModel("Cập nhật nhà cung cấp thành công", null, 200, true);
        } catch (Exception e) {
            return new ResponseModel("Cập nhật nhà cung cấp thất bại: " + e.getMessage(), null, 500, false);
        }
    }

    @Override
    public SuppliersModel findById(Integer id) {
        return repository.findById(id).orElse(null);
    }
}
