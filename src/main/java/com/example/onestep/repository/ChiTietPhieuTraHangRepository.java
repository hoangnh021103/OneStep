package com.example.onestep.repository;

import com.example.onestep.entity.ChiTietPhieuTraHang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChiTietPhieuTraHangRepository extends JpaRepository<ChiTietPhieuTraHang, Integer> {

}
