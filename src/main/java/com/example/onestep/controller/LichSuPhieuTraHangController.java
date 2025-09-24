package com.example.onestep.controller;

import com.example.onestep.dto.request.LichSuPhieuTraHangDTO;
import com.example.onestep.dto.request.SanPhamDTO;
import com.example.onestep.dto.response.ChiTietGioHangResponse;
import com.example.onestep.dto.response.LichSuPhieuTraHangResponse;
import com.example.onestep.dto.response.SanPhamResponse;
import com.example.onestep.service.ChiTietGioHangService;
import com.example.onestep.service.LichSuPhieuTraHangService;
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
@RequestMapping("/lich-su-phieu-tra-hang")
public class LichSuPhieuTraHangController {
    @Autowired
    private LichSuPhieuTraHangService lichSuPhieuTraHangService;

    @GetMapping("/hien-thi")
    public ResponseEntity<List<LichSuPhieuTraHangResponse>> getAll() {
        return ResponseEntity.ok(lichSuPhieuTraHangService.getAll());
    }

    @GetMapping("/phan-trang")
    public ResponseEntity<Page<LichSuPhieuTraHangResponse>> phanTrang(@RequestParam(defaultValue = "0") Integer page) {
        Pageable pageable = PageRequest.of(page, 5);
        return ResponseEntity.ok(lichSuPhieuTraHangService.phanTrang(pageable));
    }

    // 3. Thêm sản phẩm
    @PostMapping("/add")
    public ResponseEntity<LichSuPhieuTraHangResponse> add(@RequestBody @Valid LichSuPhieuTraHangDTO dto) {
        LichSuPhieuTraHangResponse response = lichSuPhieuTraHangService.add(dto);
        return ResponseEntity.ok(response);
    }

    // 4. Cập nhật sản phẩm theo id
    @PutMapping("/update/{id}")
    public ResponseEntity<LichSuPhieuTraHangResponse> update(@PathVariable Integer id, @RequestBody @Valid LichSuPhieuTraHangDTO dto) {
        LichSuPhieuTraHangResponse updated = lichSuPhieuTraHangService.update(id, dto);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updated);
    }

    // 5. Xoá sản phẩm theo id
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        lichSuPhieuTraHangService.delete(id);
        return ResponseEntity.noContent().build(); // HTTP 204
    }

    // 6. Lấy chi tiết sản phẩm theo id
    @GetMapping("/{id}")
    public ResponseEntity<LichSuPhieuTraHangResponse> getById(@PathVariable Integer id) {
        Optional<LichSuPhieuTraHangResponse> optional = lichSuPhieuTraHangService.getById(id);
        return optional.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
