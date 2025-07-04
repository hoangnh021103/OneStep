package com.example.onestep.service;

import com.example.onestep.dto.response.KhachHangResponse;
import com.example.onestep.dto.response.LichSuPhieuTraHangResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface LichSuPhieuTraHangService {
    List<LichSuPhieuTraHangResponse> getAll();

    Page<LichSuPhieuTraHangResponse> phanTrang(Pageable pageable);
}
