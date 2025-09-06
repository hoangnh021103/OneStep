package com.example.onestep.controller;

import com.example.onestep.dto.request.AnhSanPhamDTO;
import com.example.onestep.dto.response.AnhSanPhamResponse;
import com.example.onestep.service.AnhSanPhamService;
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
@RequestMapping("/anh-san-pham")
public class AnhSanPhamController {
    @Autowired
    private AnhSanPhamService anhSanPhamService;

    @GetMapping("/hien-thi")
    public ResponseEntity<List<AnhSanPhamResponse>> getAll() {
        return ResponseEntity.ok(anhSanPhamService.getAll());
    }

    @GetMapping("/phan-trang")
    public ResponseEntity<Page<AnhSanPhamResponse>> phanTrang(@RequestParam(defaultValue = "0") Integer page) {
        Pageable pageable = PageRequest.of(page, 5);
        return ResponseEntity.ok(anhSanPhamService.phanTrang(pageable));
    }

    @PostMapping("/add")
    public ResponseEntity<AnhSanPhamResponse> add(@RequestBody @Valid AnhSanPhamDTO dto) {
        AnhSanPhamResponse response = anhSanPhamService.add(dto);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<AnhSanPhamResponse> update(@PathVariable Integer id, @RequestBody @Valid AnhSanPhamDTO dto) {
        AnhSanPhamResponse updated = anhSanPhamService.update(id, dto);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        anhSanPhamService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AnhSanPhamResponse> getById(@PathVariable Integer id) {
        Optional<AnhSanPhamResponse> optional = anhSanPhamService.getById(id);
        return optional.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/san-pham/{sanPhamId}")
    public ResponseEntity<List<AnhSanPhamResponse>> getBySanPhamId(@PathVariable Integer sanPhamId) {
        return ResponseEntity.ok(anhSanPhamService.getBySanPhamId(sanPhamId));
    }
}
