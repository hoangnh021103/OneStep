package com.example.onestep.service;

import com.example.onestep.dto.request.ChiTietDonHangDTO;
import com.example.onestep.dto.response.ChiTietDonHangResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ChiTietDonHangService {
    List<ChiTietDonHangResponse> getAll();

    Page<ChiTietDonHangResponse> phanTrang(Pageable pageable);

    ChiTietDonHangResponse add(ChiTietDonHangDTO chiTietDonHangDTO);

    ChiTietDonHangResponse update(Integer id, ChiTietDonHangDTO chiTietDonHangDTO);

    void delete(Integer id);

    Optional<ChiTietDonHangResponse> getById(Integer id);
    
    // Lấy chi tiết đơn hàng theo ID đơn hàng
    List<ChiTietDonHangResponse> getByDonHangId(Integer donHangId);
    
    // Lấy chi tiết đơn hàng với đầy đủ thông tin sản phẩm
    List<Map<String, Object>> getByDonHangIdWithProductDetails(Integer donHangId);
}
