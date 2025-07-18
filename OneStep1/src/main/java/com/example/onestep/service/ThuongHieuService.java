package com.example.onestep.service;

import com.example.onestep.dto.request.SanPhamDTO;
import com.example.onestep.dto.request.ThuongHieuDTO;
import com.example.onestep.dto.response.KhachHangResponse;
import com.example.onestep.dto.response.SanPhamResponse;
import com.example.onestep.dto.response.ThuongHieuResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ThuongHieuService {
    List<ThuongHieuResponse> getAll();

    Page<ThuongHieuResponse> phanTrang(Pageable pageable);

    ThuongHieuResponse add(ThuongHieuDTO thuongHieuDTO);

    ThuongHieuResponse update(Integer id, ThuongHieuDTO thuongHieuDTO);

    void delete(Integer id);

    Optional<ThuongHieuResponse> getById(Integer id);
}
