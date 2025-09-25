package com.example.onestep.repository;

import com.example.onestep.entity.DonHang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface DonHangRepository extends JpaRepository<DonHang, Integer> {
    
    // Query để load đầy đủ thông tin khách hàng - sắp xếp theo ngày cập nhật mới nhất
    @Query("SELECT dh FROM DonHang dh LEFT JOIN FETCH dh.khachHang WHERE dh.daXoa = 0 ORDER BY dh.ngayCapNhat DESC, dh.id DESC")
    List<DonHang> findAllWithKhachHang();
    
    // Query để load đầy đủ thông tin khách hàng với phân trang - sắp xếp theo ngày cập nhật mới nhất
    @Query(value = "SELECT dh FROM DonHang dh LEFT JOIN FETCH dh.khachHang WHERE dh.daXoa = 0 ORDER BY dh.ngayCapNhat DESC, dh.id DESC",
           countQuery = "SELECT COUNT(dh) FROM DonHang dh WHERE dh.daXoa = 0")
    org.springframework.data.domain.Page<DonHang> findAllWithKhachHangPaged(org.springframework.data.domain.Pageable pageable);
}