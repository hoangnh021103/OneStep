package com.example.onestep.service;

import com.example.onestep.dto.request.PhuongThucThanhToanDTO;
import com.example.onestep.dto.request.SanPhamDTO;
import com.example.onestep.dto.response.KhachHangResponse;
import com.example.onestep.dto.response.PhuongThucThanhToanResponse;
import com.example.onestep.dto.response.SanPhamResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface PhuongThucThanhToanService {
    List<PhuongThucThanhToanResponse> getAll();

    Page<PhuongThucThanhToanResponse> phanTrang(Pageable pageable);

    PhuongThucThanhToanResponse add(PhuongThucThanhToanDTO phuongThucThanhToanDTO);

    PhuongThucThanhToanResponse update(Integer id, PhuongThucThanhToanDTO phuongThucThanhToanDTO);

    void delete(Integer id);

    Optional<PhuongThucThanhToanResponse> getById(Integer id);
}
