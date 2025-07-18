package com.example.onestep.service;

import com.example.onestep.dto.request.KieuDangDTO;
import com.example.onestep.dto.request.SanPhamDTO;
import com.example.onestep.dto.response.KhachHangResponse;
import com.example.onestep.dto.response.KieuDangResponse;
import com.example.onestep.dto.response.SanPhamResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface KieuDangService {
    List<KieuDangResponse> getAll();

    Page<KieuDangResponse> phanTrang(Pageable pageable);

    KieuDangResponse add(KieuDangDTO kieuDangDTO);

    KieuDangResponse update(Integer id, KieuDangDTO kieuDangDTO);

    void delete(Integer id);

    Optional<KieuDangResponse> getById(Integer id);
}
