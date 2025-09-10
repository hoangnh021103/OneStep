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
    public ResponseEntity<SanPhamResponse> add(@Valid SanPhamDTO dto) {
        SanPhamResponse response = sanPhamService.add(dto);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<SanPhamResponse> update(@PathVariable Integer id, @RequestBody @Valid SanPhamDTO dto) {
        SanPhamResponse updated = sanPhamService.update(id, dto);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        sanPhamService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<SanPhamResponse> getById(@PathVariable Integer id) {
        Optional<SanPhamResponse> optional = sanPhamService.getById(id);
        return optional.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
