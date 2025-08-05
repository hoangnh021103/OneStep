package com.example.onestep.repository;

import com.example.onestep.entity.DiaChi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiaChiRepository extends JpaRepository<DiaChi, Integer> {
}