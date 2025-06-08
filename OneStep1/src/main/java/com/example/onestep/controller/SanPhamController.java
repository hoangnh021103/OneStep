package com.example.onestep.controller;

import com.example.onestep.dto.request.SanPhamDTO;
import com.example.onestep.dto.response.SanPhamResponse;
import com.example.onestep.service.SanPhamService;
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
public class SanPhamController {

    @Autowired
    private SanPhamService sanPhamService;

    @GetMapping("/hien-thi")
    public ResponseEntity<List<SanPhamResponse>> getAll() {
        List<SanPhamResponse> list = sanPhamService.getAll();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/phan-trang")
    public ResponseEntity<Page<SanPhamResponse>> phanTrang(@RequestParam(defaultValue = "0") Integer page) {
        Pageable pageable = PageRequest.of(page, 5);
        Page<SanPhamResponse> paged = sanPhamService.phanTrang(pageable);
        return ResponseEntity.ok(paged);
    }

    @PostMapping("/add")
    public ResponseEntity<SanPhamResponse> add(@Valid @RequestBody SanPhamDTO req) {
        SanPhamResponse saved = sanPhamService.add(req);
        return ResponseEntity.ok(saved);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<SanPhamResponse> update(@PathVariable Integer id, @Valid @RequestBody SanPhamDTO req) {
        Optional<SanPhamResponse> updated = sanPhamService.update(id, req);
        return updated.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/xoa/{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id) {
        sanPhamService.delete(id);
        return ResponseEntity.ok("Xóa thành công");
    }

    @GetMapping("/tim-kiem")
    public ResponseEntity<List<SanPhamResponse>> search(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Integer trangThaiMin,
            @RequestParam(required = false) Integer trangThaiMax) {
        List<SanPhamResponse> result = sanPhamService.search(keyword, trangThaiMin, trangThaiMax);
        return ResponseEntity.ok(result);
    }
}
