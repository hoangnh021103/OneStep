package com.example.onestep.service;

import com.example.onestep.dto.response.KhachHangResponse;
import com.example.onestep.dto.response.PhuongThucThanhToanResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PhuongThucThanhToanService {
    List<PhuongThucThanhToanResponse> getAll();

    Page<PhuongThucThanhToanResponse> phanTrang(Pageable pageable);
}
