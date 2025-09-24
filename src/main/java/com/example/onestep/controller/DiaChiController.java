package com.example.onestep.controller;

import com.example.onestep.dto.request.DiaChiDTO;
import com.example.onestep.dto.request.SanPhamDTO;
import com.example.onestep.dto.response.ChiTietGioHangResponse;
import com.example.onestep.dto.response.DiaChiResponse;
import com.example.onestep.dto.response.SanPhamResponse;
import com.example.onestep.service.ChiTietGioHangService;
import com.example.onestep.service.DiaChiService;
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
@RequestMapping("/dia-chi")
public class DiaChiController {
    @Autowired
    private DiaChiService diaChiService;

    @GetMapping("/hien-thi")
    public ResponseEntity<List<DiaChiResponse>> getAll() {
        return ResponseEntity.ok(diaChiService.getAll());
    }

    @GetMapping("/phan-trang")
    public ResponseEntity<Page<DiaChiResponse>> phanTrang(@RequestParam(defaultValue = "0") Integer page) {
        Pageable pageable = PageRequest.of(page, 5);
        return ResponseEntity.ok(diaChiService.phanTrang(pageable));
    }

    // 3. Thêm sản phẩm
    @PostMapping("/add")
    public ResponseEntity<DiaChiResponse> add(@RequestBody @Valid DiaChiDTO dto) {
        DiaChiResponse response = diaChiService.add(dto);
        return ResponseEntity.ok(response);
    }

    // 4. Cập nhật sản phẩm theo id
    @PutMapping("/update/{id}")
    public ResponseEntity<DiaChiResponse> update(@PathVariable Integer id, @RequestBody @Valid DiaChiDTO dto) {
        DiaChiResponse updated = diaChiService.update(id, dto);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updated);
    }

    // 5. Xoá sản phẩm theo id
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        diaChiService.delete(id);
        return ResponseEntity.noContent().build(); // HTTP 204
    }

    // 6. Lấy chi tiết sản phẩm theo id
    @GetMapping("/{id}")
    public ResponseEntity<DiaChiResponse> getById(@PathVariable Integer id) {
        Optional<DiaChiResponse> optional = diaChiService.getById(id);
        return optional.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
