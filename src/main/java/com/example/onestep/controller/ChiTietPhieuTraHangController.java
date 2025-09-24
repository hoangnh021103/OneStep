package com.example.onestep.controller;

import com.example.onestep.dto.request.ChiTietPhieuTraHangDTO;
import com.example.onestep.dto.response.ChiTietPhieuTraHangResponse;
import com.example.onestep.service.ChiTietPhieuTraHangService;
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
@RequestMapping("/chi-tiet-phieu-tra-hang")
public class ChiTietPhieuTraHangController {
    @Autowired
    private ChiTietPhieuTraHangService chiTietPhieuTraHangService;

    @GetMapping("/hien-thi")
    public ResponseEntity<List<ChiTietPhieuTraHangResponse>> getAll() {
        return ResponseEntity.ok(chiTietPhieuTraHangService.getAll());
    }

    @GetMapping("/phan-trang")
    public ResponseEntity<Page<ChiTietPhieuTraHangResponse>> phanTrang(@RequestParam(defaultValue = "0") Integer page) {
        Pageable pageable = PageRequest.of(page, 5);
        return ResponseEntity.ok(chiTietPhieuTraHangService.phanTrang(pageable));
    }

    @PostMapping("/add")
    public ResponseEntity<ChiTietPhieuTraHangResponse> add(@RequestBody @Valid ChiTietPhieuTraHangDTO dto) {
        ChiTietPhieuTraHangResponse response = chiTietPhieuTraHangService.add(dto);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ChiTietPhieuTraHangResponse> update(@PathVariable Integer id, @RequestBody @Valid ChiTietPhieuTraHangDTO dto) {
        ChiTietPhieuTraHangResponse updated = chiTietPhieuTraHangService.update(id, dto);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        chiTietPhieuTraHangService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChiTietPhieuTraHangResponse> getById(@PathVariable Integer id) {
        Optional<ChiTietPhieuTraHangResponse> optional = chiTietPhieuTraHangService.getById(id);
        return optional.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/phieu-tra-hang/{phieuTraHangId}")
    public ResponseEntity<List<ChiTietPhieuTraHangResponse>> getByPhieuTraHangId(@PathVariable Integer phieuTraHangId) {
        return ResponseEntity.ok(chiTietPhieuTraHangService.getByPhieuTraHangId(phieuTraHangId));
    }

    @GetMapping("/chi-tiet-san-pham/{chiTietSanPhamId}")
    public ResponseEntity<List<ChiTietPhieuTraHangResponse>> getByChiTietSanPhamId(@PathVariable Integer chiTietSanPhamId) {
        return ResponseEntity.ok(chiTietPhieuTraHangService.getByChiTietSanPhamId(chiTietSanPhamId));
    }
}
