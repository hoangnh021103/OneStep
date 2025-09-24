package com.example.onestep.service;

import com.example.onestep.dto.request.SanPhamDTO;
import com.example.onestep.dto.request.VaiTroDTO;
import com.example.onestep.dto.response.KhachHangResponse;
import com.example.onestep.dto.response.SanPhamResponse;
import com.example.onestep.dto.response.VaiTroResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface VaiTroService {
    List<VaiTroResponse> getAll();

    Page<VaiTroResponse> phanTrang(Pageable pageable);

    VaiTroResponse add(VaiTroDTO vaiTroDTO);

    VaiTroResponse update(Integer id, VaiTroDTO vaiTroDTO);

    void delete(Integer id);

    Optional<VaiTroResponse> getById(Integer id);
}
