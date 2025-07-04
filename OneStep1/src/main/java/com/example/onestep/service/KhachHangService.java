package com.example.onestep.service;

import com.example.onestep.dto.response.DiaChiResponse;
import com.example.onestep.dto.response.KhachHangResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface KhachHangService {
    List<KhachHangResponse> getAll();

    Page<KhachHangResponse> phanTrang(Pageable pageable);
}
