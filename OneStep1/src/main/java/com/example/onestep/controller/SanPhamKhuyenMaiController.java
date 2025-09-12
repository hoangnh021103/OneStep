package com.example.onestep.controller;

import com.example.onestep.dto.request.SanPhamKhuyenMaiDTO;
import com.example.onestep.dto.response.SanPhamKhuyenMaiResponse;
import com.example.onestep.service.SanPhamKhuyenMaiService;
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
@RequestMapping("/san-pham-khuyen-mai")
public class SanPhamKhuyenMaiController {

    @Autowired
    private SanPhamKhuyenMaiService sanPhamKhuyenMaiService;

    @GetMapping("/hien-thi")
    public ResponseEntity<List<SanPhamKhuyenMaiResponse>> getAll() {
        return ResponseEntity.ok(sanPhamKhuyenMaiService.getAll());
    }

    @GetMapping("/phan-trang")
    public ResponseEntity<Page<SanPhamKhuyenMaiResponse>> phanTrang(@RequestParam(defaultValue = "0") Integer page) {
        Pageable pageable = PageRequest.of(page, 5);
        return ResponseEntity.ok(sanPhamKhuyenMaiService.phanTrang(pageable));
    }

    @PostMapping("/add")
    public ResponseEntity<SanPhamKhuyenMaiResponse> add(@RequestBody @Valid SanPhamKhuyenMaiDTO dto) {
        SanPhamKhuyenMaiResponse response = sanPhamKhuyenMaiService.add(dto);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<SanPhamKhuyenMaiResponse> update(@PathVariable Integer id,
                                                           @RequestBody @Valid SanPhamKhuyenMaiDTO dto) {
        SanPhamKhuyenMaiResponse updated = sanPhamKhuyenMaiService.update(id, dto);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        sanPhamKhuyenMaiService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<SanPhamKhuyenMaiResponse> getById(@PathVariable Integer id) {
        Optional<SanPhamKhuyenMaiResponse> optional = sanPhamKhuyenMaiService.getById(id);
        return optional.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/san-pham/{sanPhamId}")
    public ResponseEntity<List<SanPhamKhuyenMaiResponse>> getBySanPhamId(@PathVariable Integer sanPhamId) {
        return ResponseEntity.ok(sanPhamKhuyenMaiService.getBySanPhamId(sanPhamId));
    }

    @GetMapping("/voucher/{voucherId}")
    public ResponseEntity<List<SanPhamKhuyenMaiResponse>> getByVoucherId(@PathVariable Integer voucherId) {
        return ResponseEntity.ok(sanPhamKhuyenMaiService.getByVoucherId(voucherId));
    }
}
