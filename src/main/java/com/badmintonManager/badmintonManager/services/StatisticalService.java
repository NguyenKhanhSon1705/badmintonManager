package com.badmintonManager.badmintonManager.services;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.badmintonManager.badmintonManager.models.BillsModel;
import com.badmintonManager.badmintonManager.models.StatisticalDTO;
import com.badmintonManager.badmintonManager.models.StatisticalFilterDTO;
import com.badmintonManager.badmintonManager.repositories.IBillDetailsRepository;
import com.badmintonManager.badmintonManager.repositories.IBillsRepository;
import com.badmintonManager.badmintonManager.services.interfaces.IStatisticalService;

@Service
public class StatisticalService implements IStatisticalService {
    private final IBillsRepository billRepo;
    private final IBillDetailsRepository billDetailsRepo;

    public StatisticalService(IBillsRepository billsRepository, IBillDetailsRepository billDetailsRepo) {
        this.billRepo = billsRepository;
        this.billDetailsRepo = billDetailsRepo;
    }

    @Override
    public StatisticalDTO getStatistical(StatisticalFilterDTO model) {
        System.err.println("ngày test");
        System.err.println(model.getTimeStart());
        System.err.println(model.getTimeEnd());

        List<BillsModel> unpaidBills = billRepo.findAll();
        BigDecimal total = unpaidBills.stream()
                .filter(bill -> bill.getStatus() == 1)
                .filter(bill -> {
                    LocalDateTime createdAt = bill.getCreatedAt(); // Thời gian tạo hóa đơn
                    LocalDateTime timeStart = model.getTimeStart(); // Thời gian bắt đầu
                    LocalDateTime timeEnd = model.getTimeEnd(); // Thời gian kết thúc

                    // Kiểm tra nếu createdAt nằm trong khoảng [timeStart, timeEnd]
                    return (createdAt.isEqual(timeStart) || createdAt.isAfter(timeStart)) &&
                            (createdAt.isEqual(timeEnd) || createdAt.isBefore(timeEnd));
                })
                .map(BillsModel::getTotalAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalpedding = unpaidBills.stream()
                .filter(bill -> bill.getStatus() == 0)
                .map(BillsModel::getTotalAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        int billPaymented =(int) unpaidBills.stream()
                .filter(bil -> bil.getStatus() == 1)
                .filter(bill -> {
                    LocalDateTime createdAt = bill.getCreatedAt(); // Thời gian tạo hóa đơn
                    LocalDateTime timeStart = model.getTimeStart(); // Thời gian bắt đầu
                    LocalDateTime timeEnd = model.getTimeEnd(); // Thời gian kết thúc

                    // Kiểm tra nếu createdAt nằm trong khoảng [timeStart, timeEnd]
                    return (createdAt.isEqual(timeStart) || createdAt.isAfter(timeStart)) &&
                            (createdAt.isEqual(timeEnd) || createdAt.isBefore(timeEnd));
                })
                .count();

        int billPendding = (int) unpaidBills.stream()
        .filter(bil -> bil.getStatus() == 0)
        .count();
        System.out.printf("chờ thanh toán");
        System.out.println(totalpedding);
        System.out.printf("Đã thanh toán");
        System.out.println(total);
        System.out.printf("Tổng hóa đơn đã thanh toán");
        System.out.println(billPaymented);
        System.out.printf("Tổng hóa đơn chờ thanh toán");
        System.out.println(billPendding);
        return new StatisticalDTO(total, totalpedding, billPaymented, billPendding);
    }

    // @Override
    // public
}
