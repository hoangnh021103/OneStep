package com.example.onestep.controller;

import com.example.onestep.dto.request.GioHangDTO;
import com.example.onestep.dto.response.GioHangResponse;
import com.example.onestep.service.GioHangService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/gio-hang")
public class GioHangController {

    @Autowired
    private GioHangService gioHangService;

    @GetMapping("/hien-thi")
    public ResponseEntity<List<GioHangResponse>> getAll() {
        return ResponseEntity.ok(gioHangService.getAll());
    }

    @GetMapping("/phan-trang")
    public ResponseEntity<Page<GioHangResponse>> phanTrang(@RequestParam(defaultValue = "0") Integer page) {
        Pageable pageable = PageRequest.of(page, 5);
        return ResponseEntity.ok(gioHangService.phanTrang(pageable));
    }

}
