package com.example.onestep.service;

import com.example.onestep.dto.request.DonHangDTO;
import com.example.onestep.dto.request.SanPhamDTO;
import com.example.onestep.dto.response.DiaChiResponse;
import com.example.onestep.dto.response.DonHangResponse;
import com.example.onestep.dto.response.SanPhamResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface DonHangService {
    List<DonHangResponse> getAll();

    Page<DonHangResponse> phanTrang(Pageable pageable);

    DonHangResponse add(DonHangDTO donHangDTO);

    DonHangResponse update(Integer id, DonHangDTO donHangDTO);

    void delete(Integer id);

    Optional<DonHangResponse> getById(Integer id);
}
