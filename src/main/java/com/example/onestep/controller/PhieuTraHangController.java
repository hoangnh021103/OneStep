package com.example.onestep.controller;

import com.example.onestep.dto.request.PhieuTraHangDTO;
import com.example.onestep.dto.request.SanPhamDTO;
import com.example.onestep.dto.response.ChiTietGioHangResponse;
import com.example.onestep.dto.response.PhieuTraHangResponse;
import com.example.onestep.dto.response.SanPhamResponse;
import com.example.onestep.service.ChiTietGioHangService;
import com.example.onestep.service.PhieuTraHangService;
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
@RequestMapping("/phieu-tra-hang")
public class PhieuTraHangController {
    @Autowired
    private PhieuTraHangService phieuTraHangService;

    @GetMapping("/hien-thi")
    public ResponseEntity<List<PhieuTraHangResponse>> getAll() {
        return ResponseEntity.ok(phieuTraHangService.getAll());
    }

    @GetMapping("/phan-trang")
    public ResponseEntity<Page<PhieuTraHangResponse>> phanTrang(@RequestParam(defaultValue = "0") Integer page) {
        Pageable pageable = PageRequest.of(page, 5);
        return ResponseEntity.ok(phieuTraHangService.phanTrang(pageable));
    }

    // 3. Thêm sản phẩm
    @PostMapping("/add")
    public ResponseEntity<PhieuTraHangResponse> add(@RequestBody @Valid PhieuTraHangDTO dto) {
        PhieuTraHangResponse response = phieuTraHangService.add(dto);
        return ResponseEntity.ok(response);
    }

    // 4. Cập nhật sản phẩm theo id
    @PutMapping("/update/{id}")
    public ResponseEntity<PhieuTraHangResponse> update(@PathVariable Integer id, @RequestBody @Valid PhieuTraHangDTO dto) {
        PhieuTraHangResponse updated = phieuTraHangService.update(id, dto);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updated);
    }

    // 5. Xoá sản phẩm theo id
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        phieuTraHangService.delete(id);
        return ResponseEntity.noContent().build(); // HTTP 204
    }

    // 6. Lấy chi tiết sản phẩm theo id
    @GetMapping("/{id}")
    public ResponseEntity<PhieuTraHangResponse> getById(@PathVariable Integer id) {
        Optional<PhieuTraHangResponse> optional = phieuTraHangService.getById(id);
        return optional.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
