package com.example.onestep.service;

import com.example.onestep.dto.request.PhieuTraHangDTO;
import com.example.onestep.dto.request.SanPhamDTO;
import com.example.onestep.dto.response.KhachHangResponse;
import com.example.onestep.dto.response.PhieuTraHangResponse;
import com.example.onestep.dto.response.SanPhamResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface PhieuTraHangService {
    List<PhieuTraHangResponse> getAll();

    Page<PhieuTraHangResponse> phanTrang(Pageable pageable);

    PhieuTraHangResponse add(PhieuTraHangDTO phieuTraHangDTO);

    PhieuTraHangResponse update(Integer id, PhieuTraHangDTO phieuTraHangDTO);

    void delete(Integer id);

    Optional<PhieuTraHangResponse> getById(Integer id);
}
