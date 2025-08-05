package com.example.onestep.controller;

import com.example.onestep.dto.request.ChiTietSanPhamDTO;
import com.example.onestep.dto.request.ChiTietSanPhamSearchDTO;
import com.example.onestep.dto.response.ChiTietSanPhamResponse;
import com.example.onestep.service.ChiTietSanPhamService;
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
@RequestMapping("/chi-tiet-san-pham")
public class ChiTietSanPhamController {

    @Autowired
    private ChiTietSanPhamService chiTietSanPhamService;

    @GetMapping("/hien-thi")
    public ResponseEntity<List<ChiTietSanPhamResponse>> getAll() {
        List<ChiTietSanPhamResponse> list = chiTietSanPhamService.getAll();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/phan-trang")
    public ResponseEntity<Page<ChiTietSanPhamResponse>> phanTrang(@RequestParam(defaultValue = "0") Integer page) {
        Pageable pageable = PageRequest.of(page, 5);
        Page<ChiTietSanPhamResponse> paged = chiTietSanPhamService.phanTrang(pageable);
        return ResponseEntity.ok(paged);
    }

    @PostMapping("/add")
    public ResponseEntity<ChiTietSanPhamResponse> add(@RequestBody @Valid ChiTietSanPhamDTO dto) {
        ChiTietSanPhamResponse response = chiTietSanPhamService.add(dto);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ChiTietSanPhamResponse> update(@PathVariable Integer id, @RequestBody @Valid ChiTietSanPhamDTO dto) {
        ChiTietSanPhamResponse updated = chiTietSanPhamService.update(id, dto);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        chiTietSanPhamService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChiTietSanPhamResponse> getById(@PathVariable Integer id) {
        Optional<ChiTietSanPhamResponse> optional = chiTietSanPhamService.getById(id);
        return optional.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/tim-kiem")
    public ResponseEntity<Page<ChiTietSanPhamResponse>> timKiemChiTietSanPham(
            @RequestBody ChiTietSanPhamSearchDTO searchDTO,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ChiTietSanPhamResponse> result = chiTietSanPhamService.timKiemChiTietSanPham(searchDTO, pageable);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/tim-kiem-ten")
    public ResponseEntity<List<ChiTietSanPhamResponse>> timKiemTheoTen(@RequestParam String tenSanPham) {
        List<ChiTietSanPhamResponse> result = chiTietSanPhamService.timKiemTheoTen(tenSanPham);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/loc-theo-gia")
    public ResponseEntity<List<ChiTietSanPhamResponse>> locTheoGia(
            @RequestParam Float giaMin, 
            @RequestParam Float giaMax) {
        List<ChiTietSanPhamResponse> result = chiTietSanPhamService.locTheoGia(giaMin, giaMax);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/loc-theo-thuong-hieu")
    public ResponseEntity<List<ChiTietSanPhamResponse>> locTheoThuongHieu(@RequestParam Integer thuongHieuId) {
        List<ChiTietSanPhamResponse> result = chiTietSanPhamService.locTheoThuongHieu(thuongHieuId);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/loc-theo-mau-sac")
    public ResponseEntity<List<ChiTietSanPhamResponse>> locTheoMauSac(@RequestParam Integer mauSacId) {
        List<ChiTietSanPhamResponse> result = chiTietSanPhamService.locTheoMauSac(mauSacId);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/loc-theo-kich-co")
    public ResponseEntity<List<ChiTietSanPhamResponse>> locTheoKichCo(@RequestParam Integer kichCoId) {
        List<ChiTietSanPhamResponse> result = chiTietSanPhamService.locTheoKichCo(kichCoId);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/loc-theo-kieu-dang")
    public ResponseEntity<List<ChiTietSanPhamResponse>> locTheoKieuDang(@RequestParam Integer kieuDangId) {
        List<ChiTietSanPhamResponse> result = chiTietSanPhamService.locTheoKieuDang(kieuDangId);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/loc-theo-chat-lieu")
    public ResponseEntity<List<ChiTietSanPhamResponse>> locTheoChatLieu(@RequestParam Integer chatLieuId) {
        List<ChiTietSanPhamResponse> result = chiTietSanPhamService.locTheoChatLieu(chatLieuId);
        return ResponseEntity.ok(result);
    }
}
