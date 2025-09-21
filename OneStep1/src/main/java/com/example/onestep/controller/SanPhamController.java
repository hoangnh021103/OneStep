package com.example.onestep.controller;

import com.example.onestep.dto.request.SanPhamDTO;
import com.example.onestep.dto.response.SanPhamResponse;
import com.example.onestep.dto.response.SanPhamBanHangResponse;
import com.example.onestep.service.SanPhamService;
import com.example.onestep.service.ChiTietSanPhamService;
import com.example.onestep.entity.ChiTietSanPham;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/san-pham")
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:5174"})
public class SanPhamController {

    @Autowired
    private SanPhamService sanPhamService;
    
    @Autowired
    private ChiTietSanPhamService chiTietSanPhamService;

    @GetMapping("/hien-thi")
    public ResponseEntity<List<SanPhamResponse>> getAll() {
        try {
            System.out.println("=== API /san-pham/hien-thi được gọi!");
            List<SanPhamResponse> sanPhams = sanPhamService.getAll();
            System.out.println("=== Tổng số sản phẩm trong DB: " + sanPhams.size());
            
            // Log chi tiết từng sản phẩm
            sanPhams.forEach(sp -> {
                System.out.println("=== Sản phẩm: ID=" + sp.getMaSanPham() + ", Tên=" + sp.getTenSanPham() + ", DaXoa=" + sp.getDaXoa());
            });
            
            return ResponseEntity.ok(sanPhams);
            
        } catch (Exception e) {
            System.err.println("=== ERROR in san-pham/hien-thi: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.ok(List.of());
        }
    }

    // API test để kiểm tra kết nối
    @GetMapping("/test")
    public ResponseEntity<String> testConnection() {
        return ResponseEntity.ok("Backend API hoạt động tốt! Thời gian: " + java.time.LocalDateTime.now());
    }


    @GetMapping("/phan-trang")
    public ResponseEntity<Page<SanPhamResponse>> phanTrang(@RequestParam(defaultValue = "0") Integer page) {
        Pageable pageable = PageRequest.of(page, 5);
        Page<SanPhamResponse> paged = sanPhamService.phanTrang(pageable);
        return ResponseEntity.ok(paged);
    }

    @PostMapping("/add")
    public ResponseEntity<SanPhamResponse> add(@Valid SanPhamDTO dto) {
        SanPhamResponse response = sanPhamService.add(dto);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<SanPhamResponse> update(@PathVariable Integer id, @Valid SanPhamDTO dto) {
        SanPhamResponse updated = sanPhamService.update(id, dto);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        sanPhamService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<SanPhamResponse> getById(@PathVariable Integer id) {
        return sanPhamService.getById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


    // API mới cho bán hàng - trả về sản phẩm kèm chi tiết
    @GetMapping("/ban-hang")
    public ResponseEntity<List<SanPhamBanHangResponse>> getSanPhamForBanHang() {
        try {
            List<ChiTietSanPham> chiTietSanPhams = chiTietSanPhamService.getAllForBanHang();
            System.out.println("=== DEBUG: Số lượng ChiTietSanPham: " + chiTietSanPhams.size());
            
            List<SanPhamBanHangResponse> responses = chiTietSanPhams.stream()
                .map(this::mapToSanPhamBanHangResponse)
                .toList();
            
            System.out.println("=== DEBUG: Số lượng response: " + responses.size());
            return ResponseEntity.ok(responses);
            
        } catch (Exception e) {
            System.err.println("=== ERROR: " + e.getMessage());
            return ResponseEntity.ok(List.of());
        }
    }

    private SanPhamBanHangResponse mapToSanPhamBanHangResponse(ChiTietSanPham chiTiet) {
        return SanPhamBanHangResponse.builder()
                .id(chiTiet.getMaChiTiet())
                .chiTietSanPhamId(chiTiet.getMaChiTiet())
                .maSanPham(chiTiet.getSanPham().getMaSanPham())
                .maCode(chiTiet.getSanPham().getMaCode())
                .tenSanPham(chiTiet.getSanPham().getTenSanPham())
                .duongDanAnh(chiTiet.getDuongDanAnh() != null ? chiTiet.getDuongDanAnh() : chiTiet.getSanPham().getDuongDanAnh())
                .giaBan(chiTiet.getGiaTien())
                .soLuongTon(chiTiet.getSoLuongTon())
                .tenKichThuoc(chiTiet.getKichCo() != null ? chiTiet.getKichCo().getTen() : "Không xác định")
                .tenMauSac(chiTiet.getMauSac() != null ? chiTiet.getMauSac().getTen() : "Không xác định")
                .tenThuongHieu(chiTiet.getSanPham().getThuongHieu() != null ? chiTiet.getSanPham().getThuongHieu().getTen() : "Không xác định")
                .tenChatLieu(chiTiet.getSanPham().getChatLieu() != null ? chiTiet.getSanPham().getChatLieu().getTen() : "Không xác định")
                .trangThai(chiTiet.getTrangThai())
                .build();
    }
}