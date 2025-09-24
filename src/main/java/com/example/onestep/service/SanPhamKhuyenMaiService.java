package com.example.onestep.service;

import com.example.onestep.dto.request.SanPhamKhuyenMaiDTO;
import com.example.onestep.dto.response.SanPhamKhuyenMaiResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface SanPhamKhuyenMaiService {
    List<SanPhamKhuyenMaiResponse> getAll();

    Page<SanPhamKhuyenMaiResponse> phanTrang(Pageable pageable);

    SanPhamKhuyenMaiResponse add(SanPhamKhuyenMaiDTO sanPhamKhuyenMaiDTO);

    SanPhamKhuyenMaiResponse update(Integer id, SanPhamKhuyenMaiDTO sanPhamKhuyenMaiDTO);

    void delete(Integer id);

    Optional<SanPhamKhuyenMaiResponse> getById(Integer id);

    List<SanPhamKhuyenMaiResponse> getBySanPhamId(Integer sanPhamId);

    List<SanPhamKhuyenMaiResponse> getByVoucherId(Integer voucherId);
}
