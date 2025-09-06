package com.example.onestep.service;

import com.example.onestep.dto.request.ChiTietPhieuTraHangDTO;
import com.example.onestep.dto.response.ChiTietPhieuTraHangResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ChiTietPhieuTraHangService {
    List<ChiTietPhieuTraHangResponse> getAll();

    Page<ChiTietPhieuTraHangResponse> phanTrang(Pageable pageable);

    ChiTietPhieuTraHangResponse add(ChiTietPhieuTraHangDTO chiTietPhieuTraHangDTO);

    ChiTietPhieuTraHangResponse update(Integer id, ChiTietPhieuTraHangDTO chiTietPhieuTraHangDTO);

    void delete(Integer id);

    Optional<ChiTietPhieuTraHangResponse> getById(Integer id);

    List<ChiTietPhieuTraHangResponse> getByPhieuTraHangId(Integer phieuTraHangId);

    List<ChiTietPhieuTraHangResponse> getByChiTietSanPhamId(Integer chiTietSanPhamId);
}
