package com.example.onestep.service;

import com.example.onestep.dto.request.SanPhamDTO;
import com.example.onestep.dto.response.SanPhamResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface SanPhamService {

    List<SanPhamResponse> getAll();

    Page<SanPhamResponse> phanTrang(Pageable pageable);

    SanPhamResponse add(SanPhamDTO req);

    Optional<SanPhamResponse> update(Integer id, SanPhamDTO req);

    void delete(Integer id);

    List<SanPhamResponse> search(String keyword, Integer trangThaiMin, Integer trangThaiMax);


}
