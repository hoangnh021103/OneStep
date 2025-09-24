package com.example.onestep.repository;

import com.example.onestep.entity.KhachHang;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface KhachHangRepository extends JpaRepository<KhachHang, Integer> {
    List<KhachHang> findByHoTenContainingIgnoreCaseOrEmailContainingIgnoreCaseOrSoDienThoaiContaining(
            String hoTen, String email, String soDienThoai);
}