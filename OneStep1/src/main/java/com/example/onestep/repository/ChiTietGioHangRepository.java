package com.example.onestep.repository;

import com.example.onestep.entity.ChiTietGioHang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChiTietGioHangRepository extends JpaRepository<ChiTietGioHang, Integer> {
    // Lấy tất cả sản phẩm trong giỏ hàng theo ID giỏ hàng

}
