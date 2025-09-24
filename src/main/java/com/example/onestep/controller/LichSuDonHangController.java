package com.example.onestep.controller;

import com.example.onestep.dto.request.LichSuDonHangDTO;
import com.example.onestep.dto.request.SanPhamDTO;
import com.example.onestep.dto.response.ChiTietGioHangResponse;
import com.example.onestep.dto.response.LichSuDonHangResponse;
import com.example.onestep.dto.response.SanPhamResponse;
import com.example.onestep.service.ChiTietGioHangService;
import com.example.onestep.service.LichSuDonHangService;
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
@RequestMapping("/lich-su-don-hang")
public class LichSuDonHangController {
    @Autowired
    private LichSuDonHangService lichSuDonHangService;

    @GetMapping("/hien-thi")
    public ResponseEntity<List<LichSuDonHangResponse>> getAll() {
        return ResponseEntity.ok(lichSuDonHangService.getAll());
    }

    @GetMapping("/phan-trang")
    public ResponseEntity<Page<LichSuDonHangResponse>> phanTrang(@RequestParam(defaultValue = "0") Integer page) {
        Pageable pageable = PageRequest.of(page, 5);
        return ResponseEntity.ok(lichSuDonHangService.phanTrang(pageable));
    }

    // 3. Thêm sản phẩm
    @PostMapping("/add")
    public ResponseEntity<LichSuDonHangResponse> add(@RequestBody @Valid LichSuDonHangDTO dto) {
        LichSuDonHangResponse response = lichSuDonHangService.add(dto);
        return ResponseEntity.ok(response);
    }

    // 4. Cập nhật sản phẩm theo id
    @PutMapping("/update/{id}")
    public ResponseEntity<LichSuDonHangResponse> update(@PathVariable Integer id, @RequestBody @Valid LichSuDonHangDTO dto) {
        LichSuDonHangResponse updated = lichSuDonHangService.update(id, dto);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updated);
    }

    // 5. Xoá sản phẩm theo id
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        lichSuDonHangService.delete(id);
        return ResponseEntity.noContent().build(); // HTTP 204
    }

    // 6. Lấy chi tiết sản phẩm theo id
    @GetMapping("/{id}")
    public ResponseEntity<LichSuDonHangResponse> getById(@PathVariable Integer id) {
        Optional<LichSuDonHangResponse> optional = lichSuDonHangService.getById(id);
        return optional.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
