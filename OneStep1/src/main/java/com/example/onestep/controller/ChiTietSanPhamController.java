package com.example.onestep.controller;

import com.example.onestep.dto.request.ChiTietSanPhamDTO;
import com.example.onestep.dto.response.ChiTietSanPhamResponse;
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
    public ResponseEntity<ChiTietSanPhamResponse> add(@Valid @RequestBody ChiTietSanPhamDTO req) {
        ChiTietSanPhamResponse saved = chiTietSanPhamService.add(req);
        return ResponseEntity.ok(saved);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ChiTietSanPhamResponse> update(@PathVariable Integer id, @Valid @RequestBody ChiTietSanPhamDTO req) {
        Optional<ChiTietSanPhamResponse> updated = chiTietSanPhamService.update(id, req);
        return updated.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/xoa/{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id) {
        chiTietSanPhamService.delete(id);
        return ResponseEntity.ok("Xóa thành công");
    }

    @GetMapping("/tim-kiem")
    public ResponseEntity<List<ChiTietSanPhamResponse>> search(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Integer trangThaiMin,
            @RequestParam(required = false) Integer trangThaiMax) {
        List<ChiTietSanPhamResponse> result = chiTietSanPhamService.search(keyword, trangThaiMin, trangThaiMax);
        return ResponseEntity.ok(result);
    }
}
