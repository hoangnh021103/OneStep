package com.example.onestep.controller;

import com.example.onestep.dto.response.ChiTietGioHangResponse;
import com.example.onestep.dto.response.DiaChiResponse;
import com.example.onestep.service.ChiTietGioHangService;
import com.example.onestep.service.DiaChiService;
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
}
