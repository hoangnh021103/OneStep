package com.example.onestep.controller;

import com.example.onestep.dto.request.NhanVienDTO;
import com.example.onestep.dto.response.NhanVienResponse;
import com.example.onestep.service.NhanVienService;
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
@RequestMapping("/nhan-vien")
public class NhanVienController {

    @Autowired
    private NhanVienService nhanVienService;

    // 1. Lấy toàn bộ danh sách nhân viên
    @GetMapping("/hien-thi")
    public ResponseEntity<List<NhanVienResponse>> getAll() {
        return ResponseEntity.ok(nhanVienService.getAll());
    }

    // 2. Phân trang danh sách nhân viên
    @GetMapping("/phan-trang")
    public ResponseEntity<Page<NhanVienResponse>> phanTrang(@RequestParam(defaultValue = "0") Integer page) {
        Pageable pageable = PageRequest.of(page, 5);
        return ResponseEntity.ok(nhanVienService.phanTrang(pageable));
    }

    // 3. Thêm nhân viên
    @PostMapping("/add")
    public ResponseEntity<NhanVienResponse> add(@RequestBody @Valid NhanVienDTO dto) {
        NhanVienResponse response = nhanVienService.add(dto);
        return ResponseEntity.ok(response);
    }

    // 4. Cập nhật nhân viên theo id
    @PutMapping("/update/{id}")
    public ResponseEntity<NhanVienResponse> update(@PathVariable Integer id,
                                                   @RequestBody @Valid NhanVienDTO dto) {
        NhanVienResponse updated = nhanVienService.update(id, dto);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updated);
    }

    // 5. Xoá nhân viên theo id
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        nhanVienService.delete(id);
        return ResponseEntity.noContent().build(); // HTTP 204
    }

    // 6. Lấy chi tiết nhân viên theo id
    @GetMapping("/{id}")
    public ResponseEntity<NhanVienResponse> getById(@PathVariable Integer id) {
        Optional<NhanVienResponse> optional = nhanVienService.getById(id);
        return optional.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
