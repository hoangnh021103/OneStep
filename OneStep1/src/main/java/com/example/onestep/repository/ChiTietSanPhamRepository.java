package com.example.onestep.repository;

import com.example.onestep.entity.ChiTietSanPham;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ChiTietSanPhamRepository extends JpaRepository<ChiTietSanPham, Integer> {

    // Lấy tất cả chi tiết sản phẩm theo mã sản phẩm
    @Query("SELECT c FROM ChiTietSanPham c WHERE c.sanPham.maSanPham = :sanPhamId AND c.daXoa = 0")
    List<ChiTietSanPham> findBySanPhamId(Integer sanPhamId);

}

