package com.badmintonManager.badmintonManager.services.interfaces;
import com.badmintonManager.badmintonManager.models.ResponseModel;
import com.badmintonManager.badmintonManager.models.SuppliersModel;

public interface ISuppliersService {
    ResponseModel getAllSuppliers();
    ResponseModel deleteSupplier(Integer id);
    ResponseModel createSupplier(SuppliersModel Suppliers);
    ResponseModel updateSupplier(SuppliersModel Suppliers);
    SuppliersModel findById(Integer id);

}
