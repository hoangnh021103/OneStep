package com.example.onestep.service;

import com.example.onestep.dto.request.ChiTietGioHangDTO;
import com.example.onestep.dto.request.SanPhamDTO;
import com.example.onestep.dto.response.ChiTietGioHangResponse;
import com.example.onestep.dto.response.SanPhamResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ChiTietGioHangService {

    List<ChiTietGioHangResponse>getAll();

    Page<ChiTietGioHangResponse>phanTrang(Pageable pageable);

    ChiTietGioHangResponse add(ChiTietGioHangDTO chiTietGioHangDTO);

    ChiTietGioHangResponse update(Integer id, ChiTietGioHangDTO chiTietGioHangDTO);

    void delete(Integer id);

    Optional<ChiTietGioHangResponse> getById(Integer id);

}
