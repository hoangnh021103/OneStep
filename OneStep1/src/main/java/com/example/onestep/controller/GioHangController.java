package com.example.onestep.controller;

import com.example.onestep.dto.request.GioHangDTO;
import com.example.onestep.dto.request.SanPhamDTO;
import com.example.onestep.dto.response.GioHangResponse;
import com.example.onestep.dto.response.SanPhamResponse;
import com.example.onestep.service.GioHangService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/gio-hang")
public class GioHangController {

    @Autowired
    private GioHangService gioHangService;

    @GetMapping("/hien-thi")
    public ResponseEntity<List<GioHangResponse>> getAll() {
        return ResponseEntity.ok(gioHangService.getAll());
    }

    @GetMapping("/phan-trang")
    public ResponseEntity<Page<GioHangResponse>> phanTrang(@RequestParam(defaultValue = "0") Integer page) {
        Pageable pageable = PageRequest.of(page, 5);
        return ResponseEntity.ok(gioHangService.phanTrang(pageable));
    }

    // 3. Thêm sản phẩm
    @PostMapping("/add")
    public ResponseEntity<GioHangResponse> add(@RequestBody @Valid GioHangDTO dto) {
        GioHangResponse response = gioHangService.add(dto);
        return ResponseEntity.ok(response);
    }

    // 4. Cập nhật sản phẩm theo id
    @PutMapping("/update/{id}")
    public ResponseEntity<GioHangResponse> update(@PathVariable Integer id, @RequestBody @Valid GioHangDTO dto) {
        GioHangResponse updated = gioHangService.update(id, dto);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updated);
    }

    // 5. Xoá sản phẩm theo id
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        gioHangService.delete(id);
        return ResponseEntity.noContent().build(); // HTTP 204
    }

    // 6. Lấy chi tiết sản phẩm theo id
    @GetMapping("/{id}")
    public ResponseEntity<GioHangResponse> getById(@PathVariable Integer id) {
        Optional<GioHangResponse> optional = gioHangService.getById(id);
        return optional.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

}
