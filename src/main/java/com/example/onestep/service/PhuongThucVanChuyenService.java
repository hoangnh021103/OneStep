package com.example.onestep.service;

import com.example.onestep.dto.request.PhuongThucVanChuyenDTO;
import com.example.onestep.dto.response.PhuongThucVanChuyenResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface PhuongThucVanChuyenService {
    List<PhuongThucVanChuyenResponse> getAll();

    Page<PhuongThucVanChuyenResponse> phanTrang(Pageable pageable);

    PhuongThucVanChuyenResponse add(PhuongThucVanChuyenDTO phuongThucVanChuyenDTO);

    PhuongThucVanChuyenResponse update(Integer id, PhuongThucVanChuyenDTO phuongThucVanChuyenDTO);

    void delete(Integer id);

    Optional<PhuongThucVanChuyenResponse> getById(Integer id);
}
