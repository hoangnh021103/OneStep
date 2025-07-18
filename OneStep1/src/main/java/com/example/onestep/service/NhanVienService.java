package com.example.onestep.service;

import com.example.onestep.dto.request.NhanVienDTO;
import com.example.onestep.dto.request.SanPhamDTO;
import com.example.onestep.dto.response.KhachHangResponse;
import com.example.onestep.dto.response.NhanVienResponse;
import com.example.onestep.dto.response.SanPhamResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface NhanVienService {
    List<NhanVienResponse> getAll();

    Page<NhanVienResponse> phanTrang(Pageable pageable);

    NhanVienResponse add(NhanVienDTO nhanVienDTO);

    NhanVienResponse update(Integer id, NhanVienDTO nhanVienDTO);

    void delete(Integer id);

    Optional<NhanVienResponse> getById(Integer id);
}
