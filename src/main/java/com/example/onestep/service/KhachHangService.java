package com.example.onestep.service;

import com.example.onestep.dto.request.KhachHangDTO;
import com.example.onestep.dto.request.SanPhamDTO;
import com.example.onestep.dto.response.DiaChiResponse;
import com.example.onestep.dto.response.KhachHangResponse;
import com.example.onestep.dto.response.SanPhamResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface KhachHangService {
    List<KhachHangResponse> getAll();

    Page<KhachHangResponse> phanTrang(Pageable pageable);

    KhachHangResponse add(KhachHangDTO khachHangDTO);

    KhachHangResponse update(Integer id, KhachHangDTO khachHangDTO);

    void delete(Integer id);

    Optional<KhachHangResponse> getById(Integer id);

    List<KhachHangResponse> timKiem(String keyword);
}
