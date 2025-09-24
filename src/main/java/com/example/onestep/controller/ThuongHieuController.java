package com.example.onestep.controller;

import com.example.onestep.dto.request.SanPhamDTO;
import com.example.onestep.dto.request.ThuongHieuDTO;
import com.example.onestep.dto.response.ChiTietGioHangResponse;
import com.example.onestep.dto.response.SanPhamResponse;
import com.example.onestep.dto.response.ThuongHieuResponse;
import com.example.onestep.service.ChiTietGioHangService;
import com.example.onestep.service.ThuongHieuService;
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
@RequestMapping("/thuong-hieu")
public class ThuongHieuController {
    @Autowired
    private ThuongHieuService thuongHieuService;

    @GetMapping("/hien-thi")
    public ResponseEntity<List<ThuongHieuResponse>> getAll() {
        return ResponseEntity.ok(thuongHieuService.getAll());
    }

    @GetMapping("/phan-trang")
    public ResponseEntity<Page<ThuongHieuResponse>> phanTrang(@RequestParam(defaultValue = "0") Integer page) {
        Pageable pageable = PageRequest.of(page, 5);
        return ResponseEntity.ok(thuongHieuService.phanTrang(pageable));
    }

    // 3. Thêm sản phẩm
    @PostMapping("/add")
    public ResponseEntity<ThuongHieuResponse> add(@RequestBody @Valid ThuongHieuDTO dto) {
        ThuongHieuResponse response = thuongHieuService.add(dto);
        return ResponseEntity.ok(response);
    }

    // 4. Cập nhật sản phẩm theo id
    @PutMapping("/update/{id}")
    public ResponseEntity<ThuongHieuResponse> update(@PathVariable Integer id, @RequestBody @Valid ThuongHieuDTO dto) {
        ThuongHieuResponse updated = thuongHieuService.update(id, dto);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updated);
    }

    // 5. Xoá sản phẩm theo id
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        thuongHieuService.delete(id);
        return ResponseEntity.noContent().build(); // HTTP 204
    }

    // 6. Lấy chi tiết sản phẩm theo id
    @GetMapping("/{id}")
    public ResponseEntity<ThuongHieuResponse> getById(@PathVariable Integer id) {
        Optional<ThuongHieuResponse> optional = thuongHieuService.getById(id);
        return optional.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
