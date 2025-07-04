package com.example.onestep.controller;

import com.example.onestep.dto.response.ChatLieuResponse;
import com.example.onestep.dto.response.ChiTietGioHangResponse;
import com.example.onestep.service.ChatLieuService;
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
@RequestMapping("/chat-lieu")
public class ChatLieuController {
    @Autowired
    private ChatLieuService chatLieuService;

    @GetMapping("/hien-thi")
    public ResponseEntity<List<ChatLieuResponse>> getAll() {
        return ResponseEntity.ok(chatLieuService.getAll());
    }

    @GetMapping("/phan-trang")
    public ResponseEntity<Page<ChatLieuResponse>> phanTrang(@RequestParam(defaultValue = "0") Integer page) {
        Pageable pageable = PageRequest.of(page, 5);
        return ResponseEntity.ok(chatLieuService.phanTrang(pageable));
    }
}
