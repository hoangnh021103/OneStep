package com.example.onestep.service;

import com.example.onestep.dto.request.KichCoDTO;
import com.example.onestep.dto.request.SanPhamDTO;
import com.example.onestep.dto.response.KhachHangResponse;
import com.example.onestep.dto.response.KichCoResponse;
import com.example.onestep.dto.response.SanPhamResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface KichCoService {
    List<KichCoResponse> getAll();

    Page<KichCoResponse> phanTrang(Pageable pageable);

    KichCoResponse add(KichCoDTO kichCoDTO);

    KichCoResponse update(Integer id, KichCoDTO kichCoDTO);

    void delete(Integer id);

    Optional<KichCoResponse> getById(Integer id);
}
