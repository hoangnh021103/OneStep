package com.example.onestep.controller;

import com.example.onestep.dto.request.MauSacDTO;
import com.example.onestep.dto.request.SanPhamDTO;
import com.example.onestep.dto.response.ChiTietGioHangResponse;
import com.example.onestep.dto.response.MauSacResponse;
import com.example.onestep.dto.response.SanPhamResponse;
import com.example.onestep.service.ChiTietGioHangService;
import com.example.onestep.service.MauSacService;
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
@RequestMapping("/mau-sac")
public class MauSacController {
    @Autowired
    private MauSacService mauSacService;

    @GetMapping("/hien-thi")
    public ResponseEntity<List<MauSacResponse>> getAll() {
        return ResponseEntity.ok(mauSacService.getAll());
    }

    @GetMapping("/phan-trang")
    public ResponseEntity<Page<MauSacResponse>> phanTrang(@RequestParam(defaultValue = "0") Integer page) {
        Pageable pageable = PageRequest.of(page, 5);
        return ResponseEntity.ok(mauSacService.phanTrang(pageable));
    }

    // 3. Thêm sản phẩm
    @PostMapping("/add")
    public ResponseEntity<MauSacResponse> add(@RequestBody @Valid MauSacDTO dto) {
        MauSacResponse response = mauSacService.add(dto);
        return ResponseEntity.ok(response);
    }

    // 4. Cập nhật sản phẩm theo id
    @PutMapping("/update/{id}")
    public ResponseEntity<MauSacResponse> update(@PathVariable Integer id, @RequestBody @Valid MauSacDTO dto) {
        MauSacResponse updated = mauSacService.update(id, dto);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updated);
    }

    // 5. Xoá sản phẩm theo id
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        mauSacService.delete(id);
        return ResponseEntity.noContent().build(); // HTTP 204
    }

    // 6. Lấy chi tiết sản phẩm theo id
    @GetMapping("/{id}")
    public ResponseEntity<MauSacResponse> getById(@PathVariable Integer id) {
        Optional<MauSacResponse> optional = mauSacService.getById(id);
        return optional.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
