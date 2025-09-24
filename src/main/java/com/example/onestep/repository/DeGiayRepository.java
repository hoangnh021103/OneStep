package com.example.onestep.repository;

import com.example.onestep.entity.DeGiay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeGiayRepository extends JpaRepository<DeGiay, Integer> {

}
