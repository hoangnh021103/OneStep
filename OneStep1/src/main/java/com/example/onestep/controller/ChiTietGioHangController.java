package com.example.onestep.controller;

import com.example.onestep.dto.request.ChiTietGioHangDTO;
import com.example.onestep.dto.request.SanPhamDTO;
import com.example.onestep.dto.response.ChiTietGioHangResponse;
import com.example.onestep.dto.response.SanPhamResponse;
import com.example.onestep.service.ChiTietGioHangService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/chi-tiet-gio-hang")
public class ChiTietGioHangController {

    @Autowired
    private ChiTietGioHangService chiTietGioHangService;

    @GetMapping("/hien-thi")
    public ResponseEntity<List<ChiTietGioHangResponse>> getAll() {
        return ResponseEntity.ok(chiTietGioHangService.getAll());
    }

    @GetMapping("/phan-trang")
    public ResponseEntity<Page<ChiTietGioHangResponse>> phanTrang(@RequestParam(defaultValue = "0") Integer page) {
        Pageable pageable = PageRequest.of(page, 5);
        return ResponseEntity.ok(chiTietGioHangService.phanTrang(pageable));
    }

    // 3. Thêm sản phẩm
    @PostMapping("/add")
    public ResponseEntity<ChiTietGioHangResponse> add(@RequestBody @Valid ChiTietGioHangDTO dto) {
        ChiTietGioHangResponse response = chiTietGioHangService.add(dto);
        return ResponseEntity.ok(response);
    }

    // 4. Cập nhật sản phẩm theo id
    @PutMapping("/update/{id}")
    public ResponseEntity<ChiTietGioHangResponse> update(@PathVariable Integer id, @RequestBody @Valid ChiTietGioHangDTO dto) {
        ChiTietGioHangResponse updated = chiTietGioHangService.update(id, dto);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updated);
    }

    // 5. Xoá sản phẩm theo id
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        chiTietGioHangService.delete(id);
        return ResponseEntity.noContent().build(); // HTTP 204
    }

    // 6. Lấy chi tiết sản phẩm theo id
    @GetMapping("/{id}")
    public ResponseEntity<ChiTietGioHangResponse> getById(@PathVariable Integer id) {
        Optional<ChiTietGioHangResponse> optional = chiTietGioHangService.getById(id);
        return optional.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
