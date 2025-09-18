package com.example.onestep.controller;

import com.example.onestep.dto.request.SanPhamDTO;
import com.example.onestep.dto.request.ThanhToanDTO;
import com.example.onestep.dto.response.ChiTietGioHangResponse;
import com.example.onestep.dto.response.SanPhamResponse;
import com.example.onestep.dto.response.ThanhToanResponse;
import com.example.onestep.service.ChiTietGioHangService;
import com.example.onestep.service.ThanhToanService;
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
@RequestMapping("/thanh-toan")
public class ThanhToanController {
    @Autowired
    private ThanhToanService thanhToanService;

    @GetMapping("/hien-thi")
    public ResponseEntity<List<ThanhToanResponse>> getAll() {
        return ResponseEntity.ok(thanhToanService.getAll());
    }

    @GetMapping("/phan-trang")
    public ResponseEntity<Page<ThanhToanResponse>> phanTrang(@RequestParam(defaultValue = "0") Integer page) {
        Pageable pageable = PageRequest.of(page, 5);
        return ResponseEntity.ok(thanhToanService.phanTrang(pageable));
    }

    // 3. Thêm sản phẩm
    @PostMapping("/add")
    public ResponseEntity<ThanhToanResponse> add(@RequestBody @Valid ThanhToanDTO dto) {
        ThanhToanResponse response = thanhToanService.add(dto);
        return ResponseEntity.ok(response);
    }

    // 4. Cập nhật sản phẩm theo id
    @PutMapping("/update/{id}")
    public ResponseEntity<ThanhToanResponse> update(@PathVariable Integer id, @RequestBody @Valid ThanhToanDTO dto) {
        ThanhToanResponse updated = thanhToanService.update(id, dto);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updated);
    }

    // 5. Xoá sản phẩm theo id
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        thanhToanService.delete(id);
        return ResponseEntity.noContent().build(); // HTTP 204
    }

    // 6. Lấy chi tiết sản phẩm theo id
    @GetMapping("/{id}")
    public ResponseEntity<ThanhToanResponse> getById(@PathVariable Integer id) {
        Optional<ThanhToanResponse> optional = thanhToanService.getById(id);
        return optional.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    // Thêm mới hóa đơn (hoặc thêm sản phẩm vào giỏ hàng)
    @PostMapping("/add-hoa-don")
    public ResponseEntity<ThanhToanResponse> addHoaDon(@RequestBody @Valid ThanhToanDTO dto) {
        return ResponseEntity.ok(thanhToanService.add(dto));
    }
    @PutMapping("/{hoaDonId}/chon-khach-hang/{khachHangId}")
    public ResponseEntity<ThanhToanResponse> chonKhachHang(
            @PathVariable Integer hoaDonId,
            @PathVariable Integer khachHangId) {
        return ResponseEntity.ok(thanhToanService.chonKhachHang(hoaDonId, khachHangId));
    }
    // 7. Hủy hóa đơn
    @PutMapping("/{hoaDonId}/huy")
    public ResponseEntity<ThanhToanResponse> huyHoaDon(@PathVariable Integer hoaDonId) {
        return ResponseEntity.ok(thanhToanService.huyHoaDon(hoaDonId));
    }
    @PutMapping("/{hoaDonId}/apply-discount")
    public ResponseEntity<ThanhToanResponse> applyDiscount(
            @PathVariable Integer hoaDonId,
            @RequestParam String code) {
        return ResponseEntity.ok(thanhToanService.applyDiscount(hoaDonId, code));
    }
    //8.
}
