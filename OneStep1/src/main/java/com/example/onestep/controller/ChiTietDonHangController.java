package com.example.onestep.controller;

import com.example.onestep.dto.response.ChiTietDonHangResponse;
import com.example.onestep.dto.response.ChiTietGioHangResponse;
import com.example.onestep.service.ChiTietDonHangService;
import com.example.onestep.service.ChiTietGioHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/chi-tiet-don-hang")
public class ChiTietDonHangController {
    @Autowired
    private ChiTietDonHangService chiTietDonHangService;

    @GetMapping("/hien-thi")
    public ResponseEntity<List<ChiTietDonHangResponse>> getAll() {
        return ResponseEntity.ok(chiTietDonHangService.getAll());
    }

    @GetMapping("/phan-trang")
    public ResponseEntity<Page<ChiTietDonHangResponse>> phanTrang(@RequestParam(defaultValue = "0") Integer page) {
        Pageable pageable = PageRequest.of(page, 5);
        return ResponseEntity.ok(chiTietDonHangService.phanTrang(pageable));
    }
}
