package com.example.onestep.service;

import com.example.onestep.dto.request.LichSuPhieuTraHangDTO;
import com.example.onestep.dto.request.SanPhamDTO;
import com.example.onestep.dto.response.KhachHangResponse;
import com.example.onestep.dto.response.LichSuPhieuTraHangResponse;
import com.example.onestep.dto.response.SanPhamResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface LichSuPhieuTraHangService {
    List<LichSuPhieuTraHangResponse> getAll();

    Page<LichSuPhieuTraHangResponse> phanTrang(Pageable pageable);

    LichSuPhieuTraHangResponse add(LichSuPhieuTraHangDTO lichSuPhieuTraHangDTO);

    LichSuPhieuTraHangResponse update(Integer id, LichSuPhieuTraHangDTO lichSuPhieuTraHangDTO);

    void delete(Integer id);

    Optional<LichSuPhieuTraHangResponse> getById(Integer id);
}
