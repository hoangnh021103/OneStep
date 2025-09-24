package com.example.onestep.controller;

import com.example.onestep.dto.request.SanPhamDTO;
import com.example.onestep.dto.request.VaiTroDTO;
import com.example.onestep.dto.response.ChiTietGioHangResponse;
import com.example.onestep.dto.response.SanPhamResponse;
import com.example.onestep.dto.response.VaiTroResponse;
import com.example.onestep.service.ChiTietGioHangService;
import com.example.onestep.service.VaiTroService;
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
@RequestMapping("/vai-tro")
public class VaiTroController {
    @Autowired
    private VaiTroService vaiTroService;

    @GetMapping("/hien-thi")
    public ResponseEntity<List<VaiTroResponse>> getAll() {
        return ResponseEntity.ok(vaiTroService.getAll());
    }

    @GetMapping("/phan-trang")
    public ResponseEntity<Page<VaiTroResponse>> phanTrang(@RequestParam(defaultValue = "0") Integer page) {
        Pageable pageable = PageRequest.of(page, 5);
        return ResponseEntity.ok(vaiTroService.phanTrang(pageable));
    }
    // 3. Thêm sản phẩm
    @PostMapping("/add")
    public ResponseEntity<VaiTroResponse> add(@RequestBody @Valid VaiTroDTO dto) {
        VaiTroResponse response = vaiTroService.add(dto);
        return ResponseEntity.ok(response);
    }

    // 4. Cập nhật sản phẩm theo id
    @PutMapping("/update/{id}")
    public ResponseEntity<VaiTroResponse> update(@PathVariable Integer id, @RequestBody @Valid VaiTroDTO dto) {
        VaiTroResponse updated = vaiTroService.update(id, dto);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updated);
    }

    // 5. Xoá sản phẩm theo id
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        vaiTroService.delete(id);
        return ResponseEntity.noContent().build(); // HTTP 204
    }

    // 6. Lấy chi tiết sản phẩm theo id
    @GetMapping("/{id}")
    public ResponseEntity<VaiTroResponse> getById(@PathVariable Integer id) {
        Optional<VaiTroResponse> optional = vaiTroService.getById(id);
        return optional.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
