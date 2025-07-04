package com.example.onestep.controller;

import com.example.onestep.dto.response.ChiTietGioHangResponse;
import com.example.onestep.dto.response.KhachHangResponse;
import com.example.onestep.service.ChiTietGioHangService;
import com.example.onestep.service.KhachHangService;
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
@RequestMapping("/khach-hang")
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
}
