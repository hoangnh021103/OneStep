package com.example.onestep.service;

import com.example.onestep.dto.request.SanPhamDTO;
import com.example.onestep.dto.request.ThanhToanDTO;
import com.example.onestep.dto.response.KhachHangResponse;
import com.example.onestep.dto.response.SanPhamResponse;
import com.example.onestep.dto.response.ThanhToanResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ThanhToanService {
    List<ThanhToanResponse> getAll();

    Page<ThanhToanResponse> phanTrang(Pageable pageable);

    ThanhToanResponse add(ThanhToanDTO thanhToanDTO);

    ThanhToanResponse update(Integer id, ThanhToanDTO thanhToanDTO);

    void delete(Integer id);

    Optional<ThanhToanResponse> getById(Integer id);
    ThanhToanResponse chonKhachHang(Integer hoaDonId, Integer khachHangId);


    ThanhToanResponse huyHoaDon(Integer hoaDonId);

    ThanhToanResponse applyDiscount(Integer hoaDonId, String code);

}
