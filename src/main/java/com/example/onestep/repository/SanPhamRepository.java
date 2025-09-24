package com.example.onestep.repository;

import com.example.onestep.entity.SanPham;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SanPhamRepository extends JpaRepository<SanPham, Integer> {
    
    // Lấy tất cả sản phẩm chưa bị xóa
    @Query("SELECT sp FROM SanPham sp WHERE sp.daXoa = 0")
    List<SanPham> findAllNotDeleted();
    

}

