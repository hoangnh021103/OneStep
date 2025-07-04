package com.example.onestep.service;

import com.example.onestep.dto.response.ChiTietSanPhamResponse;
import com.example.onestep.dto.response.DiaChiResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface DiaChiService {
    List<DiaChiResponse> getAll();

    Page<DiaChiResponse> phanTrang(Pageable pageable);
}
