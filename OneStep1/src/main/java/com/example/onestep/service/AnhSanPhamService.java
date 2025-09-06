package com.example.onestep.service;

import com.example.onestep.dto.request.AnhSanPhamDTO;
import com.example.onestep.dto.response.AnhSanPhamResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface AnhSanPhamService {
    List<AnhSanPhamResponse> getAll();

    Page<AnhSanPhamResponse> phanTrang(Pageable pageable);

    AnhSanPhamResponse add(AnhSanPhamDTO anhSanPhamDTO);

    AnhSanPhamResponse update(Integer id, AnhSanPhamDTO anhSanPhamDTO);

    void delete(Integer id);

    Optional<AnhSanPhamResponse> getById(Integer id);

    List<AnhSanPhamResponse> getBySanPhamId(Integer sanPhamId);
}
