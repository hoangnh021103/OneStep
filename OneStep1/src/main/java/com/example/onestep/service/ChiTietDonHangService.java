package com.example.onestep.service;

import com.example.onestep.dto.response.ChatLieuResponse;
import com.example.onestep.dto.response.ChiTietDonHangResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ChiTietDonHangService {
    List<ChiTietDonHangResponse> getAll();

    Page<ChiTietDonHangResponse> phanTrang(Pageable pageable);
}
