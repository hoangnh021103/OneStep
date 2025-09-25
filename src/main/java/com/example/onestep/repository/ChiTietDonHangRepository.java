package com.example.onestep.repository;

import com.example.onestep.entity.ChiTietDonHang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ChiTietDonHangRepository extends JpaRepository<ChiTietDonHang, Integer> {
    
    @Query("SELECT ct FROM ChiTietDonHang ct " +
           "LEFT JOIN FETCH ct.chiTietSanPham ctsp " +
           "LEFT JOIN FETCH ctsp.sanPham sp " +
           "LEFT JOIN FETCH sp.thuongHieu th " +
           "LEFT JOIN FETCH sp.chatLieu cl " +
           "LEFT JOIN FETCH ctsp.kichCo kc " +
           "LEFT JOIN FETCH ctsp.mauSac ms " +
           "WHERE ct.donHang.id = :donHangId AND (ct.daXoa IS NULL OR ct.daXoa = 0)")
    List<ChiTietDonHang> findByDonHangId(@Param("donHangId") Integer donHangId);
    
    @Query("SELECT COALESCE(SUM(ct.soLuong), 0) FROM ChiTietDonHang ct WHERE ct.donHang.id = :donHangId AND (ct.daXoa IS NULL OR ct.daXoa = 0)")
    Integer countTotalQuantityByDonHangId(@Param("donHangId") Integer donHangId);
    
    @Query("SELECT COUNT(ct) FROM ChiTietDonHang ct WHERE ct.donHang.id = :donHangId AND (ct.daXoa IS NULL OR ct.daXoa = 0)")
    Integer countProductTypesByDonHangId(@Param("donHangId") Integer donHangId);
}