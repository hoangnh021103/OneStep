package com.example.onestep.repository;

import com.example.onestep.entity.AnhSanPham;
import com.example.onestep.entity.SanPham;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AnhSanPhamRepository extends JpaRepository<AnhSanPham, Integer> {

   
}
