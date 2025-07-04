package com.example.onestep.service;

import com.example.onestep.dto.request.ChiTietGioHangDTO;
import com.example.onestep.dto.response.ChiTietGioHangResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ChiTietGioHangService {

    List<ChiTietGioHangResponse>getAll();

    Page<ChiTietGioHangResponse>phanTrang(Pageable pageable);

}
