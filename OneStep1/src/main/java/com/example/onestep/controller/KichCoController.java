package com.example.onestep.controller;

import com.example.onestep.dto.request.KichCoDTO;
import com.example.onestep.dto.request.SanPhamDTO;
import com.example.onestep.dto.response.ChiTietGioHangResponse;
import com.example.onestep.dto.response.KichCoResponse;
import com.example.onestep.dto.response.SanPhamResponse;
import com.example.onestep.service.ChiTietGioHangService;
import com.example.onestep.service.KichCoService;
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
@RequestMapping("/kich-co")
public class KichCoController {
    @Autowired
    private KichCoService kichCoService;

    @GetMapping("/hien-thi")
    public ResponseEntity<List<KichCoResponse>> getAll() {

        return ResponseEntity.ok(kichCoService.getAll());
    }

    @GetMapping("/phan-trang")
    public ResponseEntity<Page<KichCoResponse>> phanTrang(@RequestParam(defaultValue = "0") Integer page) {
        Pageable pageable = PageRequest.of(page, 5);
        return ResponseEntity.ok(kichCoService.phanTrang(pageable));
    }

    // 3. Thêm sản phẩm
    @PostMapping("/add")
    public ResponseEntity<KichCoResponse> add(@RequestBody @Valid KichCoDTO dto) {
        KichCoResponse response = kichCoService.add(dto);
        return ResponseEntity.ok(response);
    }

    // 4. Cập nhật sản phẩm theo id
    @PutMapping("/update/{id}")
    public ResponseEntity<KichCoResponse> update(@PathVariable Integer id, @RequestBody @Valid KichCoDTO dto) {
        KichCoResponse updated = kichCoService.update(id, dto);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updated);
    }

    // 5. Xoá sản phẩm theo id
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        kichCoService.delete(id);
        return ResponseEntity.noContent().build(); // HTTP 204
    }

    // 6. Lấy chi tiết sản phẩm theo id
    @GetMapping("/{id}")
    public ResponseEntity<KichCoResponse> getById(@PathVariable Integer id) {
        Optional<KichCoResponse> optional = kichCoService.getById(id);
        return optional.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
