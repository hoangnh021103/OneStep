package com.example.onestep.service;

import com.example.onestep.dto.response.KhachHangResponse;
import com.example.onestep.dto.response.PhieuTraHangResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PhieuTraHangService {
    List<PhieuTraHangResponse> getAll();

    Page<PhieuTraHangResponse> phanTrang(Pageable pageable);
}
