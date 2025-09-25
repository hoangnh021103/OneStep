package com.example.onestep.repository;

import com.example.onestep.entity.Voucher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface VoucherRepository extends JpaRepository<Voucher, Integer> {
    
    @Query("SELECT v FROM Voucher v WHERE " +
           "(v.daXoa IS NULL OR v.daXoa = 0) AND " +
           "(v.ngayBatDau IS NULL OR v.ngayBatDau <= :currentDate) AND " +
           "(v.ngayKetThuc IS NULL OR v.ngayKetThuc >= :currentDate) AND " +
           "(v.soLuong IS NULL OR v.soLuong > 0)")
    List<Voucher> findActiveVouchers(LocalDate currentDate);
    
    @Query("SELECT v FROM Voucher v WHERE v.ma = :ma AND " +
           "(v.daXoa IS NULL OR v.daXoa = 0) AND " +
           "(v.ngayBatDau IS NULL OR v.ngayBatDau <= :currentDate) AND " +
           "(v.ngayKetThuc IS NULL OR v.ngayKetThuc >= :currentDate) AND " +
           "(v.soLuong IS NULL OR v.soLuong > 0)")
    List<Voucher> findActiveVoucherByCode(String ma, LocalDate currentDate);
}
