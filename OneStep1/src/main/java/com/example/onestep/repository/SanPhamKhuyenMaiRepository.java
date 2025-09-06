package com.example.onestep.repository;

import com.example.onestep.entity.SanPhamKhuyenMai;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SanPhamKhuyenMaiRepository extends JpaRepository<SanPhamKhuyenMai, Integer> {

}
