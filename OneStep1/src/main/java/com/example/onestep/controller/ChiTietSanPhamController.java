package com.example.onestep.controller;

import com.example.onestep.dto.request.ChiTietSanPhamDTO;
import com.example.onestep.dto.response.ChiTietSanPhamResponse;
import com.example.onestep.entity.ChiTietSanPham;
import com.example.onestep.repository.ChiTietSanPhamRepository;
import com.example.onestep.service.ChiTietSanPhamService;
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
@RequestMapping("/chi-tiet-san-pham")
public class ChiTietSanPhamController {

    @Autowired
    private ChiTietSanPhamService chiTietSanPhamService;
    @Autowired
    private ChiTietSanPhamRepository chiTietSanPhamRepository;

    @GetMapping("/hien-thi")
    public ResponseEntity<List<ChiTietSanPhamResponse>> getAll() {
        List<ChiTietSanPhamResponse> list = chiTietSanPhamService.getAll();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/phan-trang")
    public ResponseEntity<Page<ChiTietSanPhamResponse>> phanTrang(@RequestParam(defaultValue = "0") Integer page) {
        Pageable pageable = PageRequest.of(page, 5);
        Page<ChiTietSanPhamResponse> paged = chiTietSanPhamService.phanTrang(pageable);
        return ResponseEntity.ok(paged);
    }

    @PostMapping("/add")
    public ResponseEntity<ChiTietSanPhamResponse> add(@RequestBody @Valid ChiTietSanPhamDTO dto) {
        ChiTietSanPhamResponse response = chiTietSanPhamService.add(dto);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ChiTietSanPhamResponse> update(@PathVariable Integer id, @RequestBody @Valid ChiTietSanPhamDTO dto) {
        ChiTietSanPhamResponse updated = chiTietSanPhamService.update(id, dto);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        chiTietSanPhamService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChiTietSanPhamResponse> getById(@PathVariable Integer id) {
        Optional<ChiTietSanPhamResponse> optional = chiTietSanPhamService.getById(id);
        return optional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/hien-thi-theo-san-pham/{id}")
    public ResponseEntity<List<ChiTietSanPhamResponse>> getBySanPham(@PathVariable("id") Integer sanPhamId) {
        List<ChiTietSanPhamResponse> data = chiTietSanPhamService.getBySanPhamId(sanPhamId);

        return ResponseEntity.ok(data);
    }


}


