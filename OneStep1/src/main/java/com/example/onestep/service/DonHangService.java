package com.example.onestep.service;

import com.example.onestep.dto.response.DiaChiResponse;
import com.example.onestep.dto.response.DonHangResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface DonHangService {
    List<DonHangResponse> getAll();

    Page<DonHangResponse> phanTrang(Pageable pageable);
}
