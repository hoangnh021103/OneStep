package com.example.onestep.controller;

import com.example.onestep.dto.request.KhachHangDTO;
import com.example.onestep.dto.request.SanPhamDTO;
import com.example.onestep.dto.response.ChiTietGioHangResponse;
import com.example.onestep.dto.response.KhachHangResponse;
import com.example.onestep.dto.response.SanPhamResponse;
import com.example.onestep.service.ChiTietGioHangService;
import com.example.onestep.service.KhachHangService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/khach-hang")
@Validated
public class KhachHangController {
    @Autowired
    private KhachHangService khachHangService;

    @GetMapping("/hien-thi")
    public ResponseEntity<List<KhachHangResponse>> getAll() {
        return ResponseEntity.ok(khachHangService.getAll());
    }

    @GetMapping("/phan-trang")
    public ResponseEntity<Page<KhachHangResponse>> phanTrang(@RequestParam(defaultValue = "0") Integer page) {
        Pageable pageable = PageRequest.of(page, 5);
        return ResponseEntity.ok(khachHangService.phanTrang(pageable));
    }

    // 3. Thêm sản phẩm
    @PostMapping("/add")
    public ResponseEntity<KhachHangResponse> add(@RequestBody @Valid KhachHangDTO dto) {
        KhachHangResponse response = khachHangService.add(dto);
        return ResponseEntity.ok(response);
    }

    // 4. Cập nhật sản phẩm theo id
    @PutMapping("/update/{id}")
    public ResponseEntity<KhachHangResponse> update(@PathVariable @Min(value = 1, message = "ID phải là số dương") Integer id, @RequestBody @Valid KhachHangDTO dto) {
        KhachHangResponse updated = khachHangService.update(id, dto);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updated);
    }

    // 5. Xoá sản phẩm theo id
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable @Min(value = 1, message = "ID phải là số dương") Integer id) {
        khachHangService.delete(id);
        return ResponseEntity.noContent().build(); // HTTP 204
    }

    // 6. Lấy chi tiết sản phẩm theo id
    @GetMapping("/{id}")
    public ResponseEntity<KhachHangResponse> getById(@PathVariable @Min(value = 1, message = "ID phải là số dương") Integer id) {
        Optional<KhachHangResponse> optional = khachHangService.getById(id);
        return optional.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // 7. Tìm kiếm khách hàng theo từ khóa
    @GetMapping("/tim-kiem")
    public ResponseEntity<List<KhachHangResponse>> timKiem(@RequestParam String keyword) {
        return ResponseEntity.ok(khachHangService.timKiem(keyword));
    }
}
