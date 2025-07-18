package com.example.onestep.controller;

import com.example.onestep.dto.request.SanPhamDTO;
import com.example.onestep.dto.request.VoucherDTO;
import com.example.onestep.dto.response.ChiTietGioHangResponse;
import com.example.onestep.dto.response.SanPhamResponse;
import com.example.onestep.dto.response.VoucherResponse;
import com.example.onestep.service.ChiTietGioHangService;
import com.example.onestep.service.VoucherService;
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
@RequestMapping("/voucher")
public class VoucherController {
    @Autowired
    private VoucherService voucherService;

    @GetMapping("/hien-thi")
    public ResponseEntity<List<VoucherResponse>> getAll() {
        return ResponseEntity.ok(voucherService.getAll());
    }

    @GetMapping("/phan-trang")
    public ResponseEntity<Page<VoucherResponse>> phanTrang(@RequestParam(defaultValue = "0") Integer page) {
        Pageable pageable = PageRequest.of(page, 5);
        return ResponseEntity.ok(voucherService.phanTrang(pageable));
    }

    // 3. Thêm sản phẩm
    @PostMapping("/add")
    public ResponseEntity<VoucherResponse> add(@RequestBody @Valid VoucherDTO dto) {
        VoucherResponse response = voucherService.add(dto);
        return ResponseEntity.ok(response);
    }

    // 4. Cập nhật sản phẩm theo id
    @PutMapping("/update/{id}")
    public ResponseEntity<VoucherResponse> update(@PathVariable Integer id, @RequestBody @Valid VoucherDTO dto) {
        VoucherResponse updated = voucherService.update(id, dto);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updated);
    }

    // 5. Xoá sản phẩm theo id
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        voucherService.delete(id);
        return ResponseEntity.noContent().build(); // HTTP 204
    }

    // 6. Lấy chi tiết sản phẩm theo id
    @GetMapping("/{id}")
    public ResponseEntity<VoucherResponse> getById(@PathVariable Integer id) {
        Optional<VoucherResponse> optional = voucherService.getById(id);
        return optional.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
