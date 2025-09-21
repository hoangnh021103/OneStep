package com.example.onestep.repository;

import com.example.onestep.entity.ChiTietSanPham;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ChiTietSanPhamRepository extends JpaRepository<ChiTietSanPham, Integer> {

    // Lấy tất cả chi tiết sản phẩm theo mã sản phẩm
    @Query("SELECT c FROM ChiTietSanPham c WHERE c.sanPham.maSanPham = :sanPhamId AND c.daXoa = 0")
    List<ChiTietSanPham> findBySanPhamId(Integer sanPhamId);

    // Lấy tất cả chi tiết sản phẩm với thông tin đầy đủ cho bán hàng (bao gồm cả trangThai = 0)
    @Query("SELECT c FROM ChiTietSanPham c " +
           "JOIN FETCH c.sanPham sp " +
           "LEFT JOIN FETCH c.kichCo kc " +
           "LEFT JOIN FETCH c.mauSac ms " +
           "LEFT JOIN FETCH sp.thuongHieu th " +
           "LEFT JOIN FETCH sp.chatLieu cl " +
           "WHERE c.daXoa = 0 AND sp.daXoa = 0 " +
           "ORDER BY sp.tenSanPham, kc.ten, ms.ten")
    List<ChiTietSanPham> findAllForBanHang();

    // Tìm kiếm sản phẩm cho bán hàng
    @Query("SELECT c FROM ChiTietSanPham c " +
           "JOIN FETCH c.sanPham sp " +
           "LEFT JOIN FETCH c.kichCo kc " +
           "LEFT JOIN FETCH c.mauSac ms " +
           "LEFT JOIN FETCH sp.thuongHieu th " +
           "LEFT JOIN FETCH sp.chatLieu cl " +
           "WHERE c.daXoa = 0 AND sp.daXoa = 0 " +
           "AND (LOWER(sp.tenSanPham) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
           "OR LOWER(sp.maCode) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
           "OR CAST(sp.maSanPham AS STRING) LIKE CONCAT('%', :keyword, '%')) " +
           "ORDER BY sp.tenSanPham, kc.ten, ms.ten")
    List<ChiTietSanPham> searchForBanHang(@Param("keyword") String keyword);

}

