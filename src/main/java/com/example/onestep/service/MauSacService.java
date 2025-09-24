package com.example.onestep.service;

import com.example.onestep.dto.request.MauSacDTO;
import com.example.onestep.dto.request.SanPhamDTO;
import com.example.onestep.dto.response.KhachHangResponse;
import com.example.onestep.dto.response.MauSacResponse;
import com.example.onestep.dto.response.SanPhamResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface MauSacService {
    List<MauSacResponse> getAll();

    Page<MauSacResponse> phanTrang(Pageable pageable);

    MauSacResponse add(MauSacDTO mauSacDTO);

    MauSacResponse update(Integer id, MauSacDTO mauSacDTO);

    void delete(Integer id);

    Optional<MauSacResponse> getById(Integer id);
}
