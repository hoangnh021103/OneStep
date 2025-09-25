package com.example.onestep.service;

import com.example.onestep.dto.request.ChiTietSanPhamDTO;
import com.example.onestep.dto.response.ChiTietSanPhamResponse;
import com.example.onestep.entity.ChiTietSanPham;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ChiTietSanPhamService {
    List<ChiTietSanPhamResponse> getAll();
    Page<ChiTietSanPhamResponse> phanTrang(Pageable pageable);
    ChiTietSanPhamResponse add(ChiTietSanPhamDTO chiTietSanPhamDTO);
    ChiTietSanPhamResponse update(Integer id, ChiTietSanPhamDTO chiTietSanPhamDTO);
    void delete(Integer id);
    Optional<ChiTietSanPhamResponse> getById(Integer id);

    List<ChiTietSanPhamResponse> getBySanPhamId(Integer sanPhamId);
    
    // Method để lấy ChiTietSanPham entity theo sanPhamId
    List<ChiTietSanPham> findBySanPhamIdEntity(Integer sanPhamId);

    // Các method mới cho bán hàng
    List<ChiTietSanPham> getAllForBanHang();
    List<ChiTietSanPham> searchForBanHang(String keyword);
    
    // Method để cập nhật số lượng tồn kho khi bán hàng
    boolean updateInventoryQuantity(Integer chiTietSanPhamId, Integer quantitySold);
    
    // Method để khôi phục số lượng tồn kho khi hủy đơn hàng
    boolean restoreInventoryQuantity(Integer chiTietSanPhamId, Integer quantityToRestore);
    
    // Method để kiểm tra số lượng tồn kho
    boolean checkInventoryQuantity(Integer chiTietSanPhamId, Integer quantityNeeded);
    
    // Method để lấy thông tin chi tiết tồn kho
    Map<String, Object> getInventoryInfo(Integer chiTietSanPhamId);
    
    // Method để tìm ChiTietSanPham theo mã sản phẩm
    List<ChiTietSanPham> findByMaCode(String maCode);

}
