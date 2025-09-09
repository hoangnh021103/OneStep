package com.example.onestep.service;

import com.example.onestep.dto.request.ChiTietSanPhamDTO;
import com.example.onestep.dto.request.ChiTietSanPhamSearchDTO;
import com.example.onestep.dto.response.ChiTietSanPhamResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ChiTietSanPhamService {
    List<ChiTietSanPhamResponse> getAll();
    Page<ChiTietSanPhamResponse> phanTrang(Pageable pageable);
    ChiTietSanPhamResponse add(ChiTietSanPhamDTO chiTietSanPhamDTO);
    ChiTietSanPhamResponse update(Integer id, ChiTietSanPhamDTO chiTietSanPhamDTO);
    void delete(Integer id);
    Optional<ChiTietSanPhamResponse> getById(Integer id);
    Page<ChiTietSanPhamResponse> timKiemChiTietSanPham(ChiTietSanPhamSearchDTO searchDTO, Pageable pageable);
    List<ChiTietSanPhamResponse> timKiemTheoTen(String tenSanPham);
    List<ChiTietSanPhamResponse> locTheoGia(Float giaMin, Float giaMax);

    List<ChiTietSanPhamResponse> locTheoMauSac(Integer mauSacId);
    List<ChiTietSanPhamResponse> locTheoKichCo(Integer kichCoId);

}
