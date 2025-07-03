package com.example.onestep.repository;

import com.example.onestep.entity.KieuDang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KieuDangRepository extends JpaRepository<KieuDang, Integer> {
}
