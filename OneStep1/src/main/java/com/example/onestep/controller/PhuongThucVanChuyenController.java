package com.example.onestep.controller;

import com.example.onestep.dto.request.PhuongThucVanChuyenDTO;
import com.example.onestep.dto.response.PhuongThucVanChuyenResponse;
import com.example.onestep.service.PhuongThucVanChuyenService;
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
@RequestMapping("/phuong-thuc-van-chuyen")
public class PhuongThucVanChuyenController {
    @Autowired
    private PhuongThucVanChuyenService phuongThucVanChuyenService;

    @GetMapping("/hien-thi")
    public ResponseEntity<List<PhuongThucVanChuyenResponse>> getAll() {
        return ResponseEntity.ok(phuongThucVanChuyenService.getAll());
    }

    @GetMapping("/phan-trang")
    public ResponseEntity<Page<PhuongThucVanChuyenResponse>> phanTrang(@RequestParam(defaultValue = "0") Integer page) {
        Pageable pageable = PageRequest.of(page, 5);
        return ResponseEntity.ok(phuongThucVanChuyenService.phanTrang(pageable));
    }

    @PostMapping("/add")
    public ResponseEntity<PhuongThucVanChuyenResponse> add(@RequestBody @Valid PhuongThucVanChuyenDTO dto) {
        PhuongThucVanChuyenResponse response = phuongThucVanChuyenService.add(dto);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<PhuongThucVanChuyenResponse> update(@PathVariable Integer id, @RequestBody @Valid PhuongThucVanChuyenDTO dto) {
        PhuongThucVanChuyenResponse updated = phuongThucVanChuyenService.update(id, dto);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        phuongThucVanChuyenService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PhuongThucVanChuyenResponse> getById(@PathVariable Integer id) {
        Optional<PhuongThucVanChuyenResponse> optional = phuongThucVanChuyenService.getById(id);
        return optional.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
