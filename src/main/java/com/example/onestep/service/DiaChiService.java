package com.example.onestep.service;

import com.example.onestep.dto.request.DiaChiDTO;
import com.example.onestep.dto.request.SanPhamDTO;
import com.example.onestep.dto.response.ChiTietSanPhamResponse;
import com.example.onestep.dto.response.DiaChiResponse;
import com.example.onestep.dto.response.SanPhamResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface DiaChiService {
    List<DiaChiResponse> getAll();

    Page<DiaChiResponse> phanTrang(Pageable pageable);

    DiaChiResponse add(DiaChiDTO diaChiDTO);

    DiaChiResponse update(Integer id, DiaChiDTO diaChiDTO);

    void delete(Integer id);

    Optional<DiaChiResponse> getById(Integer id);
}
