package com.example.onestep.service;

import com.example.onestep.dto.request.NhanVienDTO;
import com.example.onestep.dto.response.NhanVienResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface NhanVienService {
    List<NhanVienResponse> getAll();
    Page<NhanVienResponse> phanTrang(Pageable pageable);
    Optional<NhanVienResponse> getById(Integer id);
    NhanVienResponse add(NhanVienDTO dto);
    NhanVienResponse update(Integer id, NhanVienDTO dto);
    void delete(Integer id);

}
