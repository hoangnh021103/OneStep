package com.example.onestep.service;

import com.example.onestep.dto.response.KhachHangResponse;
import com.example.onestep.dto.response.KieuDangResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface KieuDangService {
    List<KieuDangResponse> getAll();

    Page<KieuDangResponse> phanTrang(Pageable pageable);
}
