package com.example.onestep.service;

import com.example.onestep.dto.response.KhachHangResponse;
import com.example.onestep.dto.response.VaiTroResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface VaiTroService {
    List<VaiTroResponse> getAll();

    Page<VaiTroResponse> phanTrang(Pageable pageable);
}
