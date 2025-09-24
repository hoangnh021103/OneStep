package com.example.onestep.controller;

import com.example.onestep.dto.request.PhuongThucThanhToanDTO;
import com.example.onestep.dto.request.SanPhamDTO;
import com.example.onestep.dto.response.ChiTietGioHangResponse;
import com.example.onestep.dto.response.PhuongThucThanhToanResponse;
import com.example.onestep.dto.response.SanPhamResponse;
import com.example.onestep.service.ChiTietGioHangService;
import com.example.onestep.service.PhuongThucThanhToanService;
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
@RequestMapping("/phuong-thuc-thanh-toan")
public class PhuongThucThanhToanController {
    @Autowired
    private PhuongThucThanhToanService phuongThucThanhToanService;

    @GetMapping("/hien-thi")
    public ResponseEntity<List<PhuongThucThanhToanResponse>> getAll() {
        return ResponseEntity.ok(phuongThucThanhToanService.getAll());
    }

    @GetMapping("/phan-trang")
    public ResponseEntity<Page<PhuongThucThanhToanResponse>> phanTrang(@RequestParam(defaultValue = "0") Integer page) {
        Pageable pageable = PageRequest.of(page, 5);
        return ResponseEntity.ok(phuongThucThanhToanService.phanTrang(pageable));
    }
    // 3. Thêm sản phẩm
    @PostMapping("/add")
    public ResponseEntity<PhuongThucThanhToanResponse> add(@RequestBody @Valid PhuongThucThanhToanDTO dto) {
        PhuongThucThanhToanResponse response = phuongThucThanhToanService.add(dto);
        return ResponseEntity.ok(response);
    }

    // 4. Cập nhật sản phẩm theo id
    @PutMapping("/update/{id}")
    public ResponseEntity<PhuongThucThanhToanResponse> update(@PathVariable Integer id, @RequestBody @Valid PhuongThucThanhToanDTO dto) {
        PhuongThucThanhToanResponse updated = phuongThucThanhToanService.update(id, dto);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updated);
    }

    // 5. Xoá sản phẩm theo id
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        phuongThucThanhToanService.delete(id);
        return ResponseEntity.noContent().build(); // HTTP 204
    }

    // 6. Lấy chi tiết sản phẩm theo id
    @GetMapping("/{id}")
    public ResponseEntity<PhuongThucThanhToanResponse> getById(@PathVariable Integer id) {
        Optional<PhuongThucThanhToanResponse> optional = phuongThucThanhToanService.getById(id);
        return optional.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
