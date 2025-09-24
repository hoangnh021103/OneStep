package com.example.onestep.repository;

import com.example.onestep.entity.PhuongThucVanChuyen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhuongThucVanChuyenRepository extends JpaRepository<PhuongThucVanChuyen, Integer> {

}
