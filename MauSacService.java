package com.example.onestep.service;

import com.example.onestep.dto.response.KhachHangResponse;
import com.example.onestep.dto.response.MauSacResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MauSacService {
    List<MauSacResponse> getAll();

    Page<MauSacResponse> phanTrang(Pageable pageable);
}
