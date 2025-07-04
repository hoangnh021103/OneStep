package com.example.onestep.controller;

import com.example.onestep.dto.response.ChiTietGioHangResponse;
import com.example.onestep.dto.response.LichSuPhieuTraHangResponse;
import com.example.onestep.service.ChiTietGioHangService;
import com.example.onestep.service.LichSuPhieuTraHangService;
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
@RequestMapping("/lich-su-phieu-tra-hang")
public class LichSuPhieuTraHangController {
    @Autowired
    private LichSuPhieuTraHangService lichSuPhieuTraHangService;

    @GetMapping("/hien-thi")
    public ResponseEntity<List<LichSuPhieuTraHangResponse>> getAll() {
        return ResponseEntity.ok(lichSuPhieuTraHangService.getAll());
    }

    @GetMapping("/phan-trang")
    public ResponseEntity<Page<LichSuPhieuTraHangResponse>> phanTrang(@RequestParam(defaultValue = "0") Integer page) {
        Pageable pageable = PageRequest.of(page, 5);
        return ResponseEntity.ok(lichSuPhieuTraHangService.phanTrang(pageable));
    }
}
