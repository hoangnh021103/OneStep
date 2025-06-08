package com.example.onestep.repository;
import com.example.onestep.entity.ChiTietSanPham;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChiTietSanPhamRepository extends JpaRepository<ChiTietSanPham, Integer> {
    // Bạn có thể thêm các phương thức truy vấn tùy chỉnh ở đây nếu cần
}
