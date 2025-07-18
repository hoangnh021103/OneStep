package com.example.onestep.controller;

import com.example.onestep.dto.request.KieuDangDTO;
import com.example.onestep.dto.request.SanPhamDTO;
import com.example.onestep.dto.response.ChiTietGioHangResponse;
import com.example.onestep.dto.response.KieuDangResponse;
import com.example.onestep.dto.response.SanPhamResponse;
import com.example.onestep.service.ChiTietGioHangService;
import com.example.onestep.service.KieuDangService;
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
@RequestMapping("/kieu-dang")
public class KieuDangController {
    @Autowired
    private KieuDangService kieuDangService;

    @GetMapping("/hien-thi")
    public ResponseEntity<List<KieuDangResponse>> getAll() {
        return ResponseEntity.ok(kieuDangService.getAll());
    }

    @GetMapping("/phan-trang")
    public ResponseEntity<Page<KieuDangResponse>> phanTrang(@RequestParam(defaultValue = "0") Integer page) {
        Pageable pageable = PageRequest.of(page, 5);
        return ResponseEntity.ok(kieuDangService.phanTrang(pageable));
    }

    // 3. Thêm sản phẩm
    @PostMapping("/add")
    public ResponseEntity<KieuDangResponse> add(@RequestBody @Valid KieuDangDTO dto) {
        KieuDangResponse response = kieuDangService.add(dto);
        return ResponseEntity.ok(response);
    }

    // 4. Cập nhật sản phẩm theo id
    @PutMapping("/update/{id}")
    public ResponseEntity<KieuDangResponse> update(@PathVariable Integer id, @RequestBody @Valid KieuDangDTO dto) {
        KieuDangResponse updated = kieuDangService.update(id, dto);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updated);
    }

    // 5. Xoá sản phẩm theo id
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        kieuDangService.delete(id);
        return ResponseEntity.noContent().build(); // HTTP 204
    }

    // 6. Lấy chi tiết sản phẩm theo id
    @GetMapping("/{id}")
    public ResponseEntity<KieuDangResponse> getById(@PathVariable Integer id) {
        Optional<KieuDangResponse> optional = kieuDangService.getById(id);
        return optional.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
