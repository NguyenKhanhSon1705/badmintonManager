package com.badmintonManager.badmintonManager.services.interfaces;

import com.badmintonManager.badmintonManager.models.BillsModel;
import com.badmintonManager.badmintonManager.models.ResponseModel;

public interface IBillsService {
	ResponseModel getAllBills();
    ResponseModel deleteBills(Integer id);
    ResponseModel createBills(BillsModel bills);
    ResponseModel updateBills(BillsModel bills);
    BillsModel findById(Integer id);
    BillsModel save(BillsModel bill);
	//void saveBill(BillsModel billRequest);
}
