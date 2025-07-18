package com.example.onestep.service;

import com.example.onestep.dto.request.LichSuDonHangDTO;
import com.example.onestep.dto.request.SanPhamDTO;
import com.example.onestep.dto.response.KhachHangResponse;
import com.example.onestep.dto.response.LichSuDonHangResponse;
import com.example.onestep.dto.response.SanPhamResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface LichSuDonHangService {
    List<LichSuDonHangResponse> getAll();

    Page<LichSuDonHangResponse> phanTrang(Pageable pageable);

    LichSuDonHangResponse add(LichSuDonHangDTO lichSuDonHangDTO);

    LichSuDonHangResponse update(Integer id, LichSuDonHangDTO lichSuDonHangDTO);

    void delete(Integer id);

    Optional<LichSuDonHangResponse> getById(Integer id);
}
