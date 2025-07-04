package com.example.onestep.service;

import com.example.onestep.dto.response.KhachHangResponse;
import com.example.onestep.dto.response.LichSuDonHangResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface LichSuDonHangService {
    List<LichSuDonHangResponse> getAll();

    Page<LichSuDonHangResponse> phanTrang(Pageable pageable);
}
