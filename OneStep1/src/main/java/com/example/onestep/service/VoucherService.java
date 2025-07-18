package com.example.onestep.service;

import com.example.onestep.dto.request.SanPhamDTO;
import com.example.onestep.dto.request.VoucherDTO;
import com.example.onestep.dto.response.KhachHangResponse;
import com.example.onestep.dto.response.SanPhamResponse;
import com.example.onestep.dto.response.VoucherResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface VoucherService {
    List<VoucherResponse> getAll();

    Page<VoucherResponse> phanTrang(Pageable pageable);

    VoucherResponse add(VoucherDTO voucherDTO);

    VoucherResponse update(Integer id, VoucherDTO voucherDTO);

    void delete(Integer id);

    Optional<VoucherResponse> getById(Integer id);
}
