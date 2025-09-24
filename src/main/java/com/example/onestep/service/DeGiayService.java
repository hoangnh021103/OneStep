package com.example.onestep.service;

import com.example.onestep.dto.request.DeGiayDTO;
import com.example.onestep.dto.response.DeGiayResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface DeGiayService {
    List<DeGiayResponse> getAll();

    Page<DeGiayResponse> phanTrang(Pageable pageable);

    DeGiayResponse add(DeGiayDTO deGiayDTO);

    DeGiayResponse update(Integer id, DeGiayDTO deGiayDTO);

    void delete(Integer id);

    Optional<DeGiayResponse> getById(Integer id);
}
