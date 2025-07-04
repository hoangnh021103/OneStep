package com.example.onestep.controller;

import com.example.onestep.dto.response.ChiTietGioHangResponse;
import com.example.onestep.dto.response.VaiTroResponse;
import com.example.onestep.service.ChiTietGioHangService;
import com.example.onestep.service.VaiTroService;
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
}
