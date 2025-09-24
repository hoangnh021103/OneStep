package com.example.onestep.service;

import com.example.onestep.dto.request.GioHangDTO;
import com.example.onestep.dto.request.SanPhamDTO;
import com.example.onestep.dto.response.GioHangResponse;
import com.example.onestep.dto.response.SanPhamResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface GioHangService {

    List<GioHangResponse>getAll();

    Page<GioHangResponse>phanTrang(Pageable pageable);

    GioHangResponse add(GioHangDTO gioHangDTO);

    GioHangResponse update(Integer id, GioHangDTO gioHangDTO);

    void delete(Integer id);

    Optional<GioHangResponse> getById(Integer id);
}
