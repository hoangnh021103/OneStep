package com.example.onestep.service;

import com.example.onestep.dto.response.KhachHangResponse;
import com.example.onestep.dto.response.NhanVienResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface NhanVienService {
    List<NhanVienResponse> getAll();

    Page<NhanVienResponse> phanTrang(Pageable pageable);
}
