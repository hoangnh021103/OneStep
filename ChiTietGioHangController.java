package com.example.onestep.controller;

import com.example.onestep.dto.request.ChiTietGioHangDTO;
import com.example.onestep.dto.response.ChiTietGioHangResponse;
import com.example.onestep.service.ChiTietGioHangService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/chi-tiet-gio-hang")
public class ChiTietGioHangController {

    @Autowired
    private ChiTietGioHangService chiTietGioHangService;

    @GetMapping("/hien-thi")
    public ResponseEntity<List<ChiTietGioHangResponse>> getAll() {
        return ResponseEntity.ok(chiTietGioHangService.getAll());
    }

    @GetMapping("/phan-trang")
    public ResponseEntity<Page<ChiTietGioHangResponse>> phanTrang(@RequestParam(defaultValue = "0") Integer page) {
        Pageable pageable = PageRequest.of(page, 5);
        return ResponseEntity.ok(chiTietGioHangService.phanTrang(pageable));
    }
}
